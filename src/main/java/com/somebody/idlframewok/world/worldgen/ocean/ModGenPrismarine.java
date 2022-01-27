package com.somebody.idlframewok.world.worldgen.ocean;

import net.minecraft.block.BlockPrismarine;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class ModGenPrismarine implements IWorldGenerator {

    public ModGenPrismarine() {

    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        BlockPos pos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        Biome biome = world.getBiome(pos);

        int densityPerChunk = 4;
        int buildHeight = 3;

        float lightedChance = 0.1f;

        if (BiomeManager.oceanBiomes.contains(biome))
        {
            for (int i = 1; i <= densityPerChunk; i++)
            {
                //avoid chunk border
                BlockPos posGen = pos.add(random.nextInt(CHUNK_SIZE - 1) + 1, 0, random.nextInt(CHUNK_SIZE - 1) + 1) ;

                int yGround = world.getSeaLevel() - 3;
                while (world.getBlockState(posGen.add(0, yGround, 0)).getBlock() == Blocks.WATER)
                {
                    yGround--;
                }

                yGround++;
                //Idealland.Log("posGen = %s, baseLevel = %s", posGen, yGround);

                int yMax = yGround + 1 + random.nextInt(buildHeight);

                for (int y = yGround; y < yMax; y++)
                {
                    CreateLogAt(random, world, posGen.add(0,y,0), EnumFacing.UP);
                }

                if (random.nextFloat() < lightedChance)
                {
                    CreateLightAt(random, world, posGen.add(0,yMax,0), EnumFacing.UP);
                }
            }
        }
    }

    void CreateLogAt(Random random, World worldIn, BlockPos pos, EnumFacing facing)
    {
        //if (worldIn.getBlockState(pos).getBlock() == Blocks.WATER)
        {
            worldIn.setBlockState(pos, Blocks.PRISMARINE.getDefaultState().withProperty(BlockPrismarine.VARIANT, BlockPrismarine.EnumType.BRICKS), 2);
        }
    }

    void CreateLightAt(Random random, World worldIn, BlockPos pos, EnumFacing facing)
    {
        //if (worldIn.getBlockState(pos).getBlock() == Blocks.WATER)
        {
            worldIn.setBlockState(pos, Blocks.SEA_LANTERN.getDefaultState(), 2);
        }
    }
}
