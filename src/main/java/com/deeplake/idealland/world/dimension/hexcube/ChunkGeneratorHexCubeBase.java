package com.deeplake.idealland.world.dimension.hexcube;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.blocks.ModBlocks;
import com.deeplake.idealland.init.InitBiome;
import com.deeplake.idealland.world.dimension.hexcube.structure.*;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import sun.security.ssl.Debug;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.deeplake.idealland.util.CommonDef.CHUNK_SIZE;

public class ChunkGeneratorHexCubeBase implements IChunkGenerator {

    private static final int heightLimit = 255;
    //private static final int yNoGateLimit = 255;

    private final World world;
    private final boolean generateStructures;
    private final Random rand;

    protected int sizeX = CHUNK_SIZE;
    protected int sizeY = CHUNK_SIZE;
    protected int sizeZ = CHUNK_SIZE;

    public ChunkGeneratorHexCubeBase(World world, boolean generate, long seed) {
        this.world = world;
        this.generateStructures = generate;
        this.rand = new Random(seed);
        world.setSeaLevel(63);
    }

    public void setSize(int sizeX, int sizeY, int sizeZ)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;

        for (GenCubeBase gen:
                genCubes)
        {
            gen.setSize(sizeX, sizeY, sizeZ);
        }
    }

    GenCubeBase[] genCubes = {
            new GenCubeTreasure(true),
            new GenCubeSoilRoom(true),
            new GenCubeWoodRoom(true),
            new GenCubeTorchRoom(true)
    };

    private static final EnumDyeColor[] colorByDiff =
            {
                    EnumDyeColor.WHITE,//0
                    EnumDyeColor.LIME,//1
                    EnumDyeColor.CYAN,//3
                    EnumDyeColor.GRAY,//4
                    EnumDyeColor.YELLOW,//5
                    EnumDyeColor.PINK,//7
                    EnumDyeColor.ORANGE,//6
                    EnumDyeColor.RED,//8
            };

    public IBlockState getWall(int x, int y, int z)
    {
        float difficulty = HexCubeHelper.getDifficulty(x,y,z);

        //白-浅绿-浅蓝-浅黄-橙-浅红-红
        //黑曜石
        int diffInt = (int) difficulty;
        if (diffInt <= colorByDiff.length)
        {
            EnumDyeColor color = getColorForPos(x, y, z);

            return Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, color);
        }

        return Blocks.OBSIDIAN.getDefaultState();
    }

    EnumDyeColor getColorForPos(int x, int y, int z)
    {
        float difficulty = HexCubeHelper.getDifficulty(x,y,z);

        //白-浅绿-浅蓝-浅黄-橙-浅红-红
        //黑曜石
        int diffInt = (int) difficulty;

        return colorByDiff[diffInt % colorByDiff.length];
    }


    public IBlockState getWall()
    {
        return getWall(0,0,0);
    }

    float getLightChance(int x, int y, int z)
    {
        return Math.max(1f - ((x+z)>>4) * 0.01f - (y>>4) * 0.1f, 0.01f);
    }

    boolean chunkUsed(int x, int z)
    {
        return (x != 0 && z != 0);
    }

    public void buildChunk(int x, int z, ChunkPrimer primer) {

        if (chunkUsed(x, z)) {
            for (int y = 0; y < heightLimit; y+=sizeY) {
                for (int xBase = 0; xBase < CHUNK_SIZE; xBase += sizeX)
                    for (int zBase = 0; zBase < CHUNK_SIZE; zBase += sizeZ)
                    {
                        genCubeHalf(primer, x , y, z, xBase, zBase);
                    }
            }
        }

        if (this.generateStructures)
        {

        }

        GenerateFloor(primer);
    }

    private void GenerateFloor(ChunkPrimer primer) {

        for (int dx = 0; dx < CHUNK_SIZE; dx++)
        {
            //for (int dy = 0; dy < CommonDef.CHUNK_SIZE; dy++)
            {
                for (int dz = 0; dz < CHUNK_SIZE; dz++)
                {
                    //BlockPos curPos = new BlockPos(x+dx, y+dy, z+dz);
                    primer.setBlockState(dx, 0, dz,
                            Blocks.BEDROCK.getDefaultState());
//                    primer.setBlockState(dx, heightLimit, dz,
//                            Blocks.BEDROCK.getDefaultState());

                }
            }
        }
    }

    void setSeedFor(int x, int y, int z)
    {
        rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L + (long)y * 438951276L);
    }

    public boolean hasLight(int x, int y, int z)
    {
        setSeedFor(x, y, z);
        return rand.nextFloat() < getLightChance(x, y, z);
    }

    void genCubeHalf(ChunkPrimer primer, int x, int y, int z, int xBase, int zBase)
    {
        setSeedFor(x, y, z);
        boolean hasDoorX = rand.nextBoolean();
        boolean hasDoorY = y != 0 && rand.nextBoolean();//wont fall to void
        boolean hasDoorZ = rand.nextBoolean();

        //Make sure at least one door
        if (!(hasDoorX || hasDoorY || hasDoorZ))
        {
            if (rand.nextBoolean()) {
                hasDoorX = true;
            } else
            {
                hasDoorZ = true;
            }
        }

        boolean hasLight = hasLight(x, y, z);

        IBlockState wallState = getWall(x, y, z);
        for (int dx = 0; dx < sizeX; dx++)
        {
            for (int dy = 0; dy < sizeY; dy++)
            {
                for (int dz = 0; dz < sizeZ; dz++)
                {
                    //BlockPos curPos = new BlockPos(x+dx, y+dy, z+dz);

                    if (dx==0 || dy==0 || dz==0)
                    {
                        primer.setBlockState(dx + xBase, y+dy, dz + zBase,
                                wallState);
                    }
                }
            }
        }

        genDoorAndLight(primer,x, y, z, xBase, zBase, hasDoorX, hasDoorY, hasDoorZ, hasLight);

//            BlockPos boxFace = new BlockPos(15,15,15);
//            primer.setBlockState(2, 2, 2,
//                    Blocks.CHEST.correctFacing(world, boxFace, Blocks.CHEST.getDefaultState()));
//                                        TileEntity tileentity1 = world.getTileEntity(blockpos2);
//
//                                        if (tileentity1 instanceof TileEntityChest)
//                                        {
//                                            ((TileEntityChest)tileentity1).setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
//                                        }

    }

    private void genDoorAndLight(ChunkPrimer primer, int x, int y, int z, int xBase, int zBase, boolean hasDoorX, boolean hasDoorY, boolean hasDoorZ, boolean hasLight) {
        int doorSize = 1;
        int doorHeight = 3;

        int xL = ((sizeX + 1) >> 1) - doorSize;
        int xR = xL + doorSize + doorSize;

        int zL = ((sizeZ + 1) >> 1) - doorSize;
        int zR = zL + doorSize + doorSize;

        int y1 = 1;
        int y2 = doorHeight;

        EnumDyeColor color = getColorForPos(x, y, z);

        IBlockState doorStateXZ=Blocks.STAINED_GLASS_PANE.getDefaultState().withProperty(BlockColored.COLOR, color);

        if (hasDoorX) {
            for (int dx = xL; dx <= xR; dx++) {
                for (int dy = y1; dy <= y2; dy++) {
                        primer.setBlockState(dx + xBase, y + dy, zBase,
                                doorStateXZ);
                }
            }
        }

        if (hasDoorZ)
        {
            for (int dz = zL; dz<=zR; dz++)
            {
                for (int dy = y1; dy<=y2; dy++)
                {
                    primer.setBlockState(xBase, y+dy, dz + zBase,
                            doorStateXZ);
                }
            }
        }

        IBlockState doorStateY =Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, color);

        if (hasDoorY)
        {
            for (int dz = zL; dz<=zR; dz++)
            {
                for (int dx = xL; dx<=xR; dx++)
                {
                    primer.setBlockState(dx + xBase, y, dz + zBase,
                            doorStateY);
                }
            }
        }

        if (hasLight)
        {
            int min = 1;
            int max = sizeX -1;

            int minZ = 1;
            int maxZ = sizeZ - 1;

            int minY = 1;
            int maxY = sizeY - 1;
            primer.setBlockState(min + xBase, y+minY, minZ + zBase, ModBlocks.GRID_LAMP.getDefaultState());
            primer.setBlockState(min + xBase, y+minY, maxZ + zBase, ModBlocks.GRID_LAMP.getDefaultState());
            primer.setBlockState(max + xBase, y+minY, minZ + zBase, ModBlocks.GRID_LAMP.getDefaultState());
            primer.setBlockState(max + xBase, y+minY, maxZ + zBase, ModBlocks.GRID_LAMP.getDefaultState());
            primer.setBlockState(min + xBase, y+maxY, minZ + zBase, ModBlocks.GRID_LAMP.getDefaultState());
            primer.setBlockState(min + xBase, y+maxY, maxZ + zBase, ModBlocks.GRID_LAMP.getDefaultState());
            primer.setBlockState(max + xBase, y+maxY, minZ + zBase, ModBlocks.GRID_LAMP.getDefaultState());
            primer.setBlockState(max + xBase, y+maxY, maxZ + zBase, ModBlocks.GRID_LAMP.getDefaultState());
        }
    }

    //infrastructure

    @Override
    public Chunk generateChunk(int x, int z) {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        buildChunk(x,z,chunkprimer);

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = (byte)Biome.getIdForBiome(InitBiome.BIOME_CUBE);
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    enum EnumRoomType{
        TREASURE,
        SOIL,
        WOOD,
        TORCH,
        EMPTY,
    }


    EnumRoomType getRoomType(int x, int y, int z)
    {
        setSeedFor(x, y, z);
        return EnumRoomType.values()[rand.nextInt(EnumRoomType.values().length)];
//        if (rand.nextFloat() < 0.05f)
//        {
//            return EnumRoomType.TREASURE;
//        }
//
//        if (rand.nextFloat() < 0.05f)
//        {
//            return EnumRoomType.SOIL;
//        }

        //return EnumRoomType.EMPTY;
    }


    @Override
    public void populate(int chunkX, int chunkZ) {
        if (!chunkUsed(chunkX, chunkZ)) {
            return;
        }

        net.minecraft.block.BlockFalling.fallInstantly = true;
        int x = chunkX * 16;
        int z = chunkZ * 16;

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, chunkX, chunkZ, false);

        for (int xBase = 0; xBase < CHUNK_SIZE; xBase += sizeX) {
            for (int zBase = 0; zBase < CHUNK_SIZE; zBase += sizeZ) {
                for (int y = 0; y < heightLimit; y += sizeY) {

                    EnumRoomType type = getRoomType(x + xBase, y, z + zBase);

                    setSeedFor(x+ xBase, y, z + zBase);
                    boolean hasDoorX = rand.nextBoolean();
                    boolean hasDoorY = y != 0 && rand.nextBoolean();//wont fall to void
                    boolean hasDoorZ = rand.nextBoolean();
                    boolean hasLight = hasLight(x + xBase, y, z + zBase);

                    GenCubeBase gen = null;

                    int typeIndex =type.ordinal();
                    if (typeIndex >= 0 && typeIndex < genCubes.length)
                    {
                        gen = genCubes[typeIndex];
                    }

                    if (gen != null) {
                        gen.setHasDoorXYZ(hasDoorX, hasDoorY, hasDoorZ);
                        gen.setHasLightXYZ(hasLight);
                        gen.generate(world, rand, new BlockPos(x + xBase, y, z + zBase));
                    }
                }
            }
        }

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, chunkX, chunkZ, false);
        net.minecraft.block.BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}
