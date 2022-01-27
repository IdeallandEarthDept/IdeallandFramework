//package com.somebody.idlframewok.world.dimension;
//
//import com.somebody.idlframewok.init.InitBiome;
//import com.somebody.idlframewok.init.InitDimension;
//import com.somebody.idlframewok.world.dimension.hexcube.ChunkGeneratorHexCube16;
//import net.minecraft.world.DimensionType;
//import net.minecraft.world.WorldProvider;
//import net.minecraft.world.biome.BiomeProviderSingle;
//import net.minecraft.world.gen.IChunkGenerator;
//
//public class DimensionOne extends WorldProvider {
//
//    public DimensionOne() {
//
//    }
//
//    protected void init()
//    {
//        this.biomeProvider = new BiomeProviderSingle(InitBiome.BIOME_FOR_DIM_ONE);
//        hasSkyLight = false;
//    }
//
//    @Override
//    public DimensionType getDimensionType() {
//        return InitDimension.DIM_ONE;
//    }
//
//    @Override
//    public IChunkGenerator createChunkGenerator() {
//        return new ChunkGeneratorHexCube16( world, true, world.getSeed());
//    }
//
//    @Override
//    public boolean canRespawnHere() {
//        return false;
//    }
//
//    @Override
//    public boolean isSurfaceWorld() {
//        return false;
//    }
//
//
//}
