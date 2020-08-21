package com.deeplake.idealland.world.biome;

import com.deeplake.idealland.blocks.ModBlocks;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class BiomeBlank extends Biome {

    public BiomeBlank(String name) {
        super(new BiomeProperties(name).setBaseHeight(0f).setHeightVariation(0.5f).setTemperature(0.5f).setWaterColor(0x0000ff));

        topBlock = ModBlocks.GRID_NORMAL.getDefaultState();
        fillerBlock =  ModBlocks.GRID_BLOCK_2.getDefaultState();
    }

    public BiomeBlank(BiomeProperties properties) {
        super(properties);
    }
}
