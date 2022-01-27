package com.somebody.idlframewok.world.worldgen.ore;

import com.somebody.idlframewok.util.CommonDef;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class GenOre implements IWorldGenerator {
    final IBlockState state;
    final int minY, maxY;
    final int count;
    WorldGenMinable genMinable;

    public GenOre(IBlockState state, int minY, int maxY, int count) {
        this.state = state;
        this.minY = minY;
        this.maxY = maxY;
        this.count = count;

        genMinable = new WorldGenMinable(state, count);
    }

    protected BlockPos getPosGenerate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        return new BlockPos(chunkX * CommonDef.CHUNK_SIZE + CommonDef.CHUNK_CENTER_INT, random.nextInt(maxY - minY) + minY, chunkZ * CommonDef.CHUNK_SIZE + CommonDef.CHUNK_CENTER_INT);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        genMinable.generate(world, random, getPosGenerate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider));
    }
}
