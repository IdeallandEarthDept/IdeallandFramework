package com.deeplake.idealland.init;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.world.biome.BiomeBlank;
import com.deeplake.idealland.world.biome.BiomeForDimOne;
import com.deeplake.idealland.world.biome.BiomeOne;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.lwjgl.Sys;

public class InitBiome {
    public  static  final Biome BIOME_ONE = new BiomeOne();
    public static void registerBiomes()
    {
        initBiome(BIOME_ONE, "biome_one", BiomeManager.BiomeType.WARM, Type.HILLS, Type.DENSE);
    }

    public static Biome initBiome(Biome biome, String name, BiomeManager.BiomeType biomeType, Type... type)
    {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        Idealland.LogWarning("Biome registered:%s", name);
        BiomeDictionary.addTypes(biome, type);
        BiomeManager.addBiome(biomeType, new BiomeEntry(biome, 10));
        BiomeManager.addSpawnBiome(biome);
        return biome;
    }

}
