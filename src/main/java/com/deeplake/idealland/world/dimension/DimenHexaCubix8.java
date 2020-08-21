package com.deeplake.idealland.world.dimension;

import com.deeplake.idealland.init.InitBiome;
import com.deeplake.idealland.init.InitDimension;
import com.deeplake.idealland.world.dimension.hexcube.ChunkGeneratorHexCube16;
import com.deeplake.idealland.world.dimension.hexcube.ChunkGeneratorHexCubeBase;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class DimenHexaCubix8 extends WorldProvider {

    public DimenHexaCubix8() {
        this.biomeProvider = new BiomeProviderSingle(InitBiome.BIOME_FOR_DIM_ONE);
        hasSkyLight = false;
    }

    @Override
    public DimensionType getDimensionType() {
        return InitDimension.DIM_TWO;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        ChunkGeneratorHexCubeBase gen = new ChunkGeneratorHexCubeBase( world, true, world.getSeed());
                gen.setSize(16,8,8);
        return gen;
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
