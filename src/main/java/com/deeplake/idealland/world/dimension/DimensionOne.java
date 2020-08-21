package com.deeplake.idealland.world.dimension;

import com.deeplake.idealland.init.InitBiome;
import com.deeplake.idealland.init.InitDimension;
import com.deeplake.idealland.world.dimension.hexcube.ChunkGeneratorHexCube16;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionOne extends WorldProvider {

    public DimensionOne() {
        this.biomeProvider = new BiomeProviderSingle(InitBiome.BIOME_ONE);
        hasSkyLight = false;
    }

    @Override
    public DimensionType getDimensionType() {
        return InitDimension.DIM_ONE;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorHexCube16( world, true, world.getSeed());
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }


}
