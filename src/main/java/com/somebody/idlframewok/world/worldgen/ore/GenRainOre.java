package com.somebody.idlframewok.world.worldgen.ore;

import com.somebody.idlframewok.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.Random;

public class GenRainOre extends GenOre {
    public GenRainOre(IBlockState state, int minY, int maxY, int count) {
        super(state, minY, maxY, count);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        BlockPos pos = getPosGenerate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        if (WorldUtil.isWaterRainable(world, pos)) {
            genMinable.generate(world, random, pos);
        }
    }
}
