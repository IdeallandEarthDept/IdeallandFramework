package com.somebody.idlframewok.world.dimension.hexcube;

import com.somebody.idlframewok.world.dimension.hexcube.structure.*;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class ChunkGeneratorHexCube16 implements IChunkGenerator {

    private static final int heightLimit = 255;
    //private static final int yNoGateLimit = 255;

    private final World world;
    private final boolean generateStructures;
    private final Random rand;


    public ChunkGeneratorHexCube16(World world, boolean generate, long seed) {
        this.world = world;
        this.generateStructures = generate;
        this.rand = new Random(seed);
        world.setSeaLevel(63);
    }

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
            for (int y = 0; y < heightLimit; y+=CHUNK_SIZE) {
                genCubeHalf(primer, x, y, z);
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

    void genCubeHalf(ChunkPrimer primer, int x, int y, int z)
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

        IBlockState wallState =  getWall(x, y, z);
        for (int dx = 0; dx < CHUNK_SIZE; dx++)
        {
            for (int dy = 0; dy < CHUNK_SIZE; dy++)
            {
                for (int dz = 0; dz < CHUNK_SIZE; dz++)
                {
                    //BlockPos curPos = new BlockPos(x+dx, y+dy, z+dz);

                    if (dx==0 || dy==0 || dz==0)
                    {
                        primer.setBlockState(dx, y+dy, dz,
                                wallState);
                    }
                }
            }
        }

        genDoorAndLight(primer,x, y,z, hasDoorX, hasDoorY, hasDoorZ, hasLight);

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

    private void genDoorAndLight(ChunkPrimer primer, int x, int y, int z, boolean hasDoorX, boolean hasDoorY, boolean hasDoorZ, boolean hasLight) {
        int xL = 7;
        int xR = 9;

        int y1 = 1;
        int y2 = 3;

        EnumDyeColor color = getColorForPos(x, y, z);

        IBlockState doorStateXZ=Blocks.STAINED_GLASS_PANE.getDefaultState().withProperty(BlockColored.COLOR, color);
        if (hasDoorX)
        {
            for (int dx = xL; dx<=xR; dx++)
            {
                for (int dy = y1; dy<=y2; dy++)
                {
                    primer.setBlockState(dx, y+dy, 0,
                            doorStateXZ);
                }
            }
        }

        if (hasDoorZ)
        {
            for (int dz = xL; dz<=xR; dz++)
            {
                for (int dy = y1; dy<=y2; dy++)
                {
                    primer.setBlockState(0, y+dy, dz,
                            doorStateXZ);
                }
            }
        }

        IBlockState doorStateY =Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, color);

        if (hasDoorY)
        {
            for (int dz = xL; dz<=xR; dz++)
            {
                for (int dx = xL; dx<=xR; dx++)
                {
                    primer.setBlockState(dx, y, dz,
                            doorStateY);
                }
            }
        }

        if (hasLight)
        {
            int min = 1;
            int max = CHUNK_SIZE -1;
            primer.setBlockState(min, y+min, min, Blocks.LIT_REDSTONE_LAMP.getDefaultState());
            primer.setBlockState(min, y+min, max, Blocks.LIT_REDSTONE_LAMP.getDefaultState());
            primer.setBlockState(max, y+min, min, Blocks.LIT_REDSTONE_LAMP.getDefaultState());
            primer.setBlockState(max, y+min, max, Blocks.LIT_REDSTONE_LAMP.getDefaultState());
            primer.setBlockState(min, y+max, min, Blocks.LIT_REDSTONE_LAMP.getDefaultState());
            primer.setBlockState(min, y+max, max, Blocks.LIT_REDSTONE_LAMP.getDefaultState());
            primer.setBlockState(max, y+max, min, Blocks.LIT_REDSTONE_LAMP.getDefaultState());
            primer.setBlockState(max, y+max, max, Blocks.LIT_REDSTONE_LAMP.getDefaultState());
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
            abyte[i] = (byte)Biome.getIdForBiome(Biomes.EXTREME_HILLS);
            //abyte[i] = (byte)Biome.getIdForBiome(InitBiome.BIOME_ONE);
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    enum EnumRoomType{
        EMPTY,
        TREASURE,
        SOIL,
        WOOD,
        TORCH
    }

    static GenCubeBase genCubeTreasure = new GenCubeTreasure(true);
    static GenCubeBase genCubeSoil = new GenCubeSoilRoom(true);
    static GenCubeBase genCubeWood = new GenCubeWoodRoom(true);
    static GenCubeBase genCubeTorch = new GenCubeTorchRoom(true);

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
        if (!chunkUsed(chunkX, chunkZ))
        {
            return;
        }

        net.minecraft.block.BlockFalling.fallInstantly = true;
        int x = chunkX * 16;
        int z = chunkZ * 16;

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, chunkX, chunkZ, false);

        for (int y = 0; y < heightLimit; y+=CHUNK_SIZE) {

            EnumRoomType type = getRoomType(x,y,z);

            setSeedFor(x, y, z);
            boolean hasDoorX = rand.nextBoolean();
            boolean hasDoorY = y != 0 && rand.nextBoolean();//wont fall to void
            boolean hasDoorZ = rand.nextBoolean();
            boolean hasLight = hasLight(x,y,z);

            GenCubeBase gen = null;

            switch (type)
            {
                case EMPTY:
                    break;
                case TREASURE:
                    gen = genCubeTreasure;
                    break;
                case SOIL:
                    gen = genCubeSoil;
                    break;
                case WOOD:
                    gen = genCubeWood;
                    break;
                case TORCH:
                    gen = genCubeTorch;
                    break;
                default:
                    break;
            }

            if (gen != null)
            {
                gen.setHasDoorXYZ(hasDoorX, hasDoorY, hasDoorZ);
                gen.setHasLightXYZ(hasLight);
                gen.generate(world, rand, new BlockPos(x, y, z));
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
