package com.somebody.idlframewok.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class BiomeForDimOne extends Biome {

    protected static final WorldGenAbstractTree TREE = new WorldGenBigTree(false);

    public BiomeForDimOne() {
        super(new BiomeProperties("biome_one").setBaseHeight(-1.5f).setHeightVariation(1.2f).setTemperature(0.5f).setWaterColor(0xff3333));

        topBlock = Blocks.PUMPKIN.getDefaultState();
        fillerBlock = Blocks.GOLD_BLOCK.getDefaultState();

        decorator.coalGen = new WorldGenMinable(Blocks.PLANKS.getDefaultState(), 10);

        decorator.treesPerChunk = 2;

        this.spawnableCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();

       // this.spawnableCreatureList.add(new SpawnListEntry(EntityGhast.class, 5, 1,2));
    }

    public BiomeForDimOne(BiomeProperties properties) {
        super(properties);
    }


    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random random)
    {
        return TREE;
    }
}
