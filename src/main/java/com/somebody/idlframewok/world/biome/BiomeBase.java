package com.somebody.idlframewok.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class BiomeBase extends Biome {

    String accessibleName = "";
    boolean genLakes = true;
    boolean genDungeons = true;
    boolean doDecorate = true;
    boolean genInitAnimals = true;
    boolean genIce = true;

    public BiomeBase(BiomePropertiesModified properties) {
        super(properties);
        accessibleName = properties.accessibleName;

//        this.decorator.treesPerChunk = 1;
//        this.decorator.flowersPerChunk = 5;
//        this.decorator.grassPerChunk = 1;
//        this.decorator.waterlilyPerChunk = 1;
//        this.decorator.deadBushPerChunk = 1;
//        this.decorator.mushroomsPerChunk = 1;
//        this.decorator.reedsPerChunk = 1;
//        this.decorator.cactiPerChunk = 1;
    }

    public String getAccessibleName() {
        return accessibleName;
    }

    //    @SideOnly(Side.CLIENT)
//    public final String getBiomeName()
//    {
//        throw new Exception("Don't use this stupid Client only final call. Use getAccessibleName instead").
//        return "ss";
//    }

    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        super.decorate(worldIn, rand, pos);
    }

    public static class BiomePropertiesModified extends BiomeProperties{
        //making this public
        public String accessibleName = "";
        public BiomePropertiesModified(String nameIn) {
            super(nameIn);
            accessibleName = nameIn;//stupid client only private getter
        }

        public BiomePropertiesModified setTemperature(float temperatureIn)
        {
            if (temperatureIn > 0.1F && temperatureIn < 0.2F)
            {
                throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
            }
            else
            {
                super.setTemperature(temperatureIn);
                return this;
            }
        }

        public BiomePropertiesModified setRainfall(float rainfallIn)
        {
            super.setRainfall(rainfallIn);
            return this;
        }

        public BiomePropertiesModified setBaseHeight(float baseHeightIn)
        {
            super.setBaseHeight(baseHeightIn);
            return this;
        }

        public BiomePropertiesModified setHeightVariation(float heightVariationIn)
        {
            super.setHeightVariation(heightVariationIn);
            return this;
        }

        public BiomePropertiesModified setRainDisabled()
        {
            super.setRainDisabled();
            return this;
        }

        public BiomePropertiesModified setSnowEnabled()
        {
            super.setSnowEnabled();
            return this;
        }

        public BiomePropertiesModified setWaterColor(int waterColorIn)
        {
            super.setWaterColor(waterColorIn);
            return this;
        }

        public BiomePropertiesModified setBaseBiome(String nameIn)
        {
            super.setBaseBiome(nameIn);
            return this;
        }
    }

    //get set

    public boolean isGenLakes() {
        return genLakes;
    }

    public void setGenLakes(boolean genLakes) {
        this.genLakes = genLakes;
    }

    public boolean isGenDungeons() {
        return genDungeons;
    }

    public void setGenDungeons(boolean genDungeons) {
        this.genDungeons = genDungeons;
    }

    public boolean isDoDecorate() {
        return doDecorate;
    }

    public void setDoDecorate(boolean doDecorate) {
        this.doDecorate = doDecorate;
    }

    public boolean isGenInitAnimals() {
        return genInitAnimals;
    }

    public void setGenInitAnimals(boolean genInitAnimals) {
        this.genInitAnimals = genInitAnimals;
    }

    public boolean isGenIce() {
        return genIce;
    }

    public void setGenIce(boolean genIce) {
        this.genIce = genIce;
    }
}
