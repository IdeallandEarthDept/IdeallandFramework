package com.somebody.idlframewok.world.worldgen.ocean;

import com.somebody.idlframewok.init.ModConfig;
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

public class ModGenWaterPillar implements IWorldGenerator {

    public ModGenWaterPillar() {

    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        if (random.nextFloat() > ModConfig.WORLD_GEN_CONF.SPINE_CHANCE * 4 || random.nextFloat() < ModConfig.WORLD_GEN_CONF.SPINE_CHANCE )
        {
            return;
        }

        BlockPos pos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        Biome biome = world.getBiome(pos);

        if (BiomeManager.oceanBiomes.contains(biome))
        {
            //avoid chunk border
            BlockPos posGen = pos.add(random.nextInt(CHUNK_SIZE - 2) + CHUNK_SIZE, 0, random.nextInt(CHUNK_SIZE - 2) + CHUNK_SIZE);

            int seaLevel = world.getSeaLevel();
//            int buildHeightLimit = (world.getActualHeight() - seaLevel) / 3;//water shouldn't be higher than spines

            if (world.getBlockState(posGen.add(0, seaLevel, 0)).getBlock() != Blocks.WATER) {
                //don't spawn it on shore.
                return;
            }

            int yMax = seaLevel + 6 + random.nextInt(3);// random.nextInt(buildHeightLimit);

            for (int y = seaLevel; y < yMax; y++)
            {
                CreateLogAt(random, world, posGen.add(0,y,0), EnumFacing.UP);
                CreateLogAt(random, world, posGen.add(-1,y,0), EnumFacing.UP);
                CreateLogAt(random, world, posGen.add(1,y,0), EnumFacing.UP);
                CreateLogAt(random, world, posGen.add(0,y,-1), EnumFacing.UP);
                CreateLogAt(random, world, posGen.add(0,y,1), EnumFacing.UP);
            }
        }
    }

    void CreateLogAt(Random random, World worldIn, BlockPos pos, EnumFacing facing)
    {
        if (worldIn.getBlockState(pos).getBlock() == Blocks.AIR)
        {
            worldIn.setBlockState(pos, Blocks.WATER.getDefaultState(), 2);
        }
    }
}
