package com.somebody.idlframewok.world.dimension.hexcube;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.init.InitBiome;
import com.somebody.idlframewok.world.dimension.ChunkGenBase;
import com.somebody.idlframewok.world.dimension.hexcube.structure.*;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class ChunkGeneratorHexCubeBase extends ChunkGenBase {
    //private static final int yNoGateLimit = 255;
    protected int sizeX = CHUNK_SIZE;
    protected int sizeY = CHUNK_SIZE;
    protected int sizeZ = CHUNK_SIZE;

    public ChunkGeneratorHexCubeBase(World world, boolean generate, long seed, String options) {
        super(world, seed, generate, options);
        singleBiome = InitBiome.BIOME_CUBE;
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
            new GenCubeTorchRoom(true),
            new GenCubeSpawner(true),
            new GenCubeWaterFilledRoom(true),
            new GenCubeTeleporterRoom(true)
    };

    enum EnumRoomType{
        TREASURE,
        SOIL,
        WOOD,
        TORCH,
        SPAWNER,
        WATER_FILLED,
        EMPTY,
        EMPTY2,
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

//    boolean chunkUsed(int x, int z)
//    {
//        return (x != 0 && z != 0);
//    }
    boolean chunkUsed(int x, int z)
    {
        return true;
    }

    public void buildChunkPrimier(int x, int z, ChunkPrimer primer) {

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

        super.buildChunkPrimier(x, z, primer);
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

    EnumRoomType getRoomType(int x, int y, int z)
    {
        setSeedFor(x, y, z);
        return EnumRoomType.values()[rand.nextInt(EnumRoomType.values().length)];
    }

    @Override
    public void populate(int chunkX, int chunkZ) {
        if (!chunkUsed(chunkX, chunkZ)) {
            return;
        }

        net.minecraft.block.BlockFalling.fallInstantly = true;
        int x = chunkX * CHUNK_SIZE;
        int z = chunkZ * CHUNK_SIZE;

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

        Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
        chunk.enqueueRelightChecks();
    }
}
