package com.somebody.idlframewok.world.biome;

import com.somebody.idlframewok.blocks.ModBlocks;

public class BiomeBlank extends BiomeBase {

    public BiomeBlank(String name) {
        super(new BiomePropertiesModified(name).setBaseHeight(0f).setHeightVariation(0.5f).setTemperature(0.5f).setWaterColor(0x0000ff));

    }

    public BiomeBlank(BiomePropertiesModified properties) {
        super(properties);
    }
}
