package com.somebody.idlframewok.world.dimension.universal.chunk;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.util.WorldGenUtil;
import com.somebody.idlframewok.world.WorldChunkBase;
import com.somebody.idlframewok.world.biome.BiomeFlesh;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;
import static com.somebody.idlframewok.util.CommonDef.MAX_BUILD_HEIGHT;

public class ChunkFleshLand extends WorldChunkBase {

    Random rand;

    //need to do this for different biome
    public IBlockState WATER;
    public IBlockState topBlock;
    public IBlockState NETHER_RACK = Blocks.NETHERRACK.getDefaultState();
    public IBlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
    public IBlockState WART_PLANT = Blocks.NETHER_WART.getDefaultState();
    public IBlockState BONE_X = Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.X);
    public IBlockState BONE_Y = Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);
    public IBlockState BONE_Z = Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Z);

    public IBlockState SKIN = ModBlocks.FLESH_BLOCK_0.getDefaultState();
    public IBlockState SKIN_SCAR = ModBlocks.FLESH_BLOCK_SCAR.getDefaultState();
    public IBlockState SKIN_BRAIN = ModBlocks.FLESH_BLOCK_BRAIN.getDefaultState();
    public IBlockState SKIN_EYE = ModBlocks.FLESH_BLOCK_EYE.getDefaultState();
    public IBlockState SKIN_MOUTH = ModBlocks.FLESH_BLOCK_MOUTH.getDefaultState();

    public IBlockState VEIN = Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED);
    public IBlockState LAVA = Blocks.FLOWING_LAVA.getDefaultState();

    public IBlockState SLIME = Blocks.SLIME_BLOCK.getDefaultState();

    public IBlockState GROUND = Blocks.GRASS.getDefaultState();
    public IBlockState GRASS = Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);

    BiomeProvider provider;
    World world;

    public ChunkFleshLand(World worldIn, Random rand) {
        world = worldIn;
        provider = worldIn.getBiomeProvider();
        WATER = Blocks.WATER.getDefaultState();
        topBlock = Blocks.NETHER_WART_BLOCK.getDefaultState();
        this.rand = rand;
    }

    @Override
    public void buildChunk(int x, int z, Chunk chunk) {
        //Idealland.Log("Building chunk: %d,%d", x, z);

        //todo: mouth traps

        int seaLevel = world.getSeaLevel();
        Biome mainBiome = chunk.getBiome(new BlockPos(7,7,7), provider);
        if (mainBiome instanceof BiomeFlesh)
        {
            for (int _y = 1; _y < seaLevel; _y++) {
                //body sea
                for (int _x = 0; _x < CHUNK_SIZE; _x++)
                {
                    for (int _z = 0; _z < CHUNK_SIZE; _z++)
                    {
                        WorldGenUtil.setBlockState(chunk, _x, _y, _z, getFillerBlock(x, z, _y, chunk));
                    }
                }

                //bone rack
                WorldGenUtil.setBlockState(chunk, 1, _y, 1, NETHER_RACK);
                if ((_y & 1) == 0)
                {
                    WorldGenUtil.setBlockState(chunk, 0, _y, 1, NETHER_RACK);
                    WorldGenUtil.setBlockState(chunk, 2, _y, 1, NETHER_RACK);
                    WorldGenUtil.setBlockState(chunk, 1, _y, 0, NETHER_RACK);
                    WorldGenUtil.setBlockState(chunk, 1, _y, 2, NETHER_RACK);
                }
            }

            //blood vine
            for (int baseHeight = 18; baseHeight <= seaLevel - 8; baseHeight += 16)
            {
                //Idealland.Log("Gen: height = %d, seaLevel - 16 = %d", baseHeight, seaLevel - 16);
                for (int len = 0; len < CHUNK_SIZE; len++)
                {
                    int baseHori = 7;
                    int h2 = baseHeight - 8;
                    for (int _dh = -1; _dh <= + 1; _dh++)
                    {
                        for (int _dv = - 1; _dv <= + 2; _dv++) {
                            //two vein, one X, one Z
                            WorldGenUtil.setBlockState(chunk, baseHori + _dh, baseHeight + _dv, len, VEIN);
                            WorldGenUtil.setBlockState(chunk, len, h2 + _dv, baseHori + _dh, VEIN);
                        }
                    }
                    //blood lava, or lymph lava?
                    WorldGenUtil.setBlockState(chunk, baseHori, baseHeight, len, WATER);
                    WorldGenUtil.setBlockState(chunk, baseHori, baseHeight+1, len, WATER);
                    WorldGenUtil.setBlockState(chunk, len, h2, baseHori, WATER);
                    WorldGenUtil.setBlockState(chunk, len, h2+1, baseHori, WATER);
                }
            }

            //surface
            for (int _x = 0; _x < CHUNK_SIZE; _x++)
            {
                //skin
                boolean thick = rand.nextFloat() < 0.5f;
                int base = thick ? seaLevel+1 : seaLevel;
                boolean wart = rand.nextFloat() < 0.3f;

                for (int _z = 0; _z < CHUNK_SIZE; _z++)
                {
                    if (thick)
                    {
                        WorldGenUtil.setBlockState(chunk, _x, base - 1, _z, topBlock);
                    }

                    if (rand.nextFloat() < 0.2f)
                    {
                        if (wart)
                        {
                            //soul sand as hair
                            WorldGenUtil.setBlockState(chunk, _x, base, _z, SOUL_SAND);
                            WorldGenUtil.setBlockState(chunk, _x, base+1, _z, WART_PLANT);
                        }
                        else {
                            WorldGenUtil.setBlockState(chunk, _x, base, _z, GROUND);
                            WorldGenUtil.setBlockState(chunk, _x, base+1, _z, GRASS);
                        }
                    }
                    else {
                        //nether_wart block
                        WorldGenUtil.setBlockState(chunk, _x, base, _z, topBlock);
                    }

                    //bone grid
                    if ((_x & 2) + (_z & 2) != 0)
                    {
                        WorldGenUtil.setBlockState(chunk, _x, seaLevel-1, _z, NETHER_RACK);
                    }
                }
            }

            //world spine
            if (rand.nextFloat() < 0.1f)
            {
                buildPillar(x, z, chunk);
            }
        }
    }

    void buildPillar(int xC, int zC,  Chunk chunk) {
        int centerX = 7;
        int centerZ = 7;
        int height = world.getSeaLevel() + (rand.nextInt(MAX_BUILD_HEIGHT - world.getSeaLevel()));
        int secondaryHeight = height - 5;

        //skin
        int dm = 2;//diameter

        for (int _y = 1; _y < height; _y++) {
            for (int _x = centerX - 2; _x <= centerX + 2; _x++)
            {
                for (int _z = centerZ - 2; _z <= centerZ + 2; _z++)
                {
                    //blood
                    WorldGenUtil.setBlockState(chunk, _x, _y, _z, WATER);
                }
            }

            //bone
            WorldGenUtil.setBlockState(chunk, centerX, _y, centerZ, BONE_Y);
            if ((_y & 1) == 0 && (_y < secondaryHeight))
            {
                WorldGenUtil.setBlockState(chunk, centerX-1, _y, centerZ, BONE_X);
                WorldGenUtil.setBlockState(chunk, centerX+1, _y, centerZ, BONE_X);
                WorldGenUtil.setBlockState(chunk, centerX, _y, centerZ-1, BONE_Z);
                WorldGenUtil.setBlockState(chunk, centerX, _y, centerZ+1, BONE_Z);
            }


            for (int d = -dm; d <= dm; d++)
            {
                WorldGenUtil.setBlockState(chunk, centerX+d, _y, centerZ+dm, getSkinBlock());
                WorldGenUtil.setBlockState(chunk, centerX+d, _y, centerZ-dm, getSkinBlock());
                WorldGenUtil.setBlockState(chunk, centerX-dm, _y, centerZ+d, getSkinBlock());
                WorldGenUtil.setBlockState(chunk, centerX+dm, _y, centerZ+d, getSkinBlock());
            }
        }

        for (int _x = centerX - dm + 1; _x <= centerX + dm - 1; _x++)
        {
            for (int _z = centerZ - dm + 1; _z <= centerZ + dm - 1; _z++)
            {
                WorldGenUtil.setBlockState(chunk, _x, height, _z, SKIN);
            }
        }
    }

    IBlockState getSkinBlock()
    {
        int type = rand.nextInt(100);
        if (type < 5)
        {
            return SKIN_EYE;
        } else if (type < 10)
        {
            return SKIN_MOUTH;
        }
        else if (type < 25)
        {
            return SKIN_SCAR;
        }
        else {
            return SKIN;
        }
    }

    IBlockState getFillerBlock(int xC, int zC, int y,  Chunk chunk)
    {
        if (y < 10)
        {
            return SKIN_BRAIN;
        }

        if (rand.nextInt(y - 8) == 0)
        {
            return SLIME;
        }

        return WATER;
    }

    IBlockState getFillerBlockTop(int xC, int zC, int y, boolean isLand,  Chunk chunk)
    {
        return topBlock;
    }
}
