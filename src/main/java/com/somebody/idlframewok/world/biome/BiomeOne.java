package com.somebody.idlframewok.world.biome;

import com.somebody.idlframewok.blocks.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BiomeOne extends BiomeBase {

    //protected static final WorldGenAbstractTree TREE = new WorldGenBigTree(false);

    public BiomeOne() {
        super(new BiomePropertiesModified("biome_one").setBaseHeight(-1.5f).setHeightVariation(0.4f).setTemperature(0.5f).setWaterColor(0xff3333));

        decorator.coalGen = new WorldGenMinable(Blocks.PLANKS.getDefaultState(), 10);

        decorator.treesPerChunk = 2;

//        this.spawnableCreatureList.clear();
//        this.spawnableCaveCreatureList.clear();
//        this.spawnableMonsterList.clear();
//        this.spawnableWaterCreatureList.clear();
//
//        this.spawnableCreatureList.add(new SpawnListEntry(EntityGhast.class, 5, 1,2));
    }

    public BiomeOne(BiomePropertiesModified properties) {
        super(properties);
    }


//    @Override
//    public WorldGenAbstractTree getRandomTreeFeature(Random random)
//    {
//        return TREE;
//    }
}
