package com.somebody.idlframewok.init;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.world.biome.*;
import com.somebody.idlframewok.world.biome.godlands.BiomeFlameTrapTower;
import com.somebody.idlframewok.world.biome.godlands.skylands.BiomeSkylandBase;
import com.somebody.idlframewok.world.types.layer.GenLayerUniv;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class InitBiome {
    public static final BiomeBase BIOME_CUBE = new BiomeBlank("hex_cube");
    public static final BiomeBase BIOME_ONE = new BiomeOne();
    public static final BiomeBase BIOME_MOROON_BASE = new BiomeMoroon();
    public static final BiomeBase BIOME_FOR_DIM_ONE = new BiomeForDimOne();
    public static final BiomeBase BIOME_FLESH = new BiomeFlesh();
    public static final BiomeBase BIOME_FIRE_TRAP_TOWER = new BiomeFlameTrapTower();

    public static final BiomeSkylandBase[] BIOME_SKYLANDS = new BiomeSkylandBase[16];
    public static void registerBiomes()
    {
        initBiome(BIOME_CUBE, Type.SPOOKY, Type.MAGICAL);
        initBiome(BIOME_ONE, Type.HILLS, Type.DENSE);
        initBiome(BIOME_FOR_DIM_ONE, Type.SPOOKY, Type.DENSE);
        initBiome(BIOME_MOROON_BASE,  Type.SPOOKY, Type.DENSE);

        for (int i = 0; i < CHUNK_SIZE; i++)
        {
            BIOME_SKYLANDS[i] = new BiomeSkylandBase(i);
            initBiome(BIOME_SKYLANDS[i], Type.MAGICAL);
        }

        initBiome(BIOME_FLESH, Type.SPOOKY, Type.MAGICAL);

        initBiome(BIOME_FIRE_TRAP_TOWER, Type.HOT, Type.MAGICAL);


        GenLayerUniv.initGenLayerUniv();
    }

    static void registerToOverworld(BiomeBase biome, BiomeManager.BiomeType biomeType, int weight)
    {
        BiomeManager.addBiome(biomeType, new BiomeEntry(biome, weight));
        Idealland.Log("Biome registered to overworld:%s", biome.getAccessibleName());
    }

    public static BiomeBase initBiome(BiomeBase biome, Type... type)
    {
        String name = biome.getAccessibleName();
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeDictionary.addTypes(biome, type);
        BiomeManager.addSpawnBiome(biome);
        Idealland.Log("Biome registered:%s", name);
        return biome;
    }

}
