//package com.somebody.idlframewok.world.dimension;
//
//import com.somebody.idlframewok.init.InitDimension;
//import com.somebody.idlframewok.world.dimension.hexcube.ChunkGeneratorHexCubeBase;
//import net.minecraft.init.Biomes;
//import net.minecraft.world.DimensionType;
//import net.minecraft.world.WorldProvider;
//import net.minecraft.world.biome.BiomeProviderSingle;
//import net.minecraft.world.gen.IChunkGenerator;
//
//public class DimenHexaCubix8 extends WorldProvider {
//
//    public DimenHexaCubix8() {
//    }
//
//    protected void init()
//    {
//        this.biomeProvider = new BiomeProviderSingle(Biomes.VOID);
//        hasSkyLight = false;
//    }
//
//    @Override
//    public DimensionType getDimensionType() {
//        return InitDimension.DIM_TWO;
//    }
//
//    @Override
//    public IChunkGenerator createChunkGenerator() {
//        ChunkGeneratorHexCubeBase gen = new ChunkGeneratorHexCubeBase( world, true, world.getSeed(), world.getWorldInfo().getGeneratorOptions());
//                gen.setSize(8,8,8);
//        return gen;
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
//}
