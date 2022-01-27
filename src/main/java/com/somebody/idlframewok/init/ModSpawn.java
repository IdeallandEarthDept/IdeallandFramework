package com.somebody.idlframewok.init;

import com.somebody.idlframewok.entity.creatures.misc.EntityAutumnRiderGroup;
import com.somebody.idlframewok.entity.creatures.misc.EntityShadeOtherworld;
import com.somebody.idlframewok.entity.creatures.mobs.elemental.EntityEarthlin;
import com.somebody.idlframewok.entity.creatures.mobs.EntityIdentityThief;
import com.somebody.idlframewok.entity.creatures.mobs.skyland.*;
import com.somebody.idlframewok.entity.creatures.moroon.*;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModSpawn {

    /**
     * Register Mobs based on Biome sub Types
     */
    public static void registerSpawnList() {
        Map<Type, Set<Biome>> biomeMap = buildBiomeListByType();

        addNormalSpawn(biomeMap);
        addHumidSpawn(biomeMap);
        addOpenGroundSpawn(biomeMap);
    }

    //Mob
    public static void add(Biome biome, int weight, Class<? extends EntityLiving> entityclassIn, int groupCountMin, int groupCountMax) {
        if (weight > 0) {
            biome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(entityclassIn, weight, groupCountMin, groupCountMax));
        }
    }

    public static void add_friendly(Biome biome, int weight, Class<? extends EntityLiving> entityclassIn, int groupCountMin, int groupCountMax) {
        if (weight > 0) {
            biome.getSpawnableList(EnumCreatureType.CREATURE).add(new Biome.SpawnListEntry(entityclassIn, weight, groupCountMin, groupCountMax));
        }
    }

    public static void add_water(Biome biome, int weight, Class<? extends EntityLiving> entityclassIn, int groupCountMin, int groupCountMax) {
        if (weight > 0) {
            biome.getSpawnableList(EnumCreatureType.WATER_CREATURE).add(new Biome.SpawnListEntry(entityclassIn, weight, groupCountMin, groupCountMax));
        }
    }

    private static Map<Type, Set<Biome>> buildBiomeListByType() {
        Map<Type, Set<Biome>> biomesAndTypes = new HashMap<>();

        for (Biome biome : Biome.REGISTRY) {
            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);
            for (BiomeDictionary.Type type : types) {
                if (!biomesAndTypes.containsKey(type)) {
                    biomesAndTypes.put(type, new HashSet<>());
                }

                biomesAndTypes.get(type).add(biome);
            }
        }

        //Biome[] oceanBiomes = BiomeManager.oceanBiomes.toArray(new Biome[0]);
        //Idealland.LogWarning("Adding again!");
        //EntityRegistry.addSpawn(EntityMoroonSquid.class, 100, 1, 5, EnumCreatureType.WATER_CREATURE, oceanBiomes);

        return biomesAndTypes;
    }

    private static void addNormalSpawn(Map<Type, Set<Biome>> biomeMap) {
        for (Biome biome : Biome.REGISTRY) {
            add(biome, ModConfig.SPAWN_CONF.SPAWN_TAINTER, EntityMoroonTainter.class, 1, 4);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_M_O_B, EntityMoroonBombBeacon.class, 1, 1);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_BASTION, EntityMoroonBastionWalker.class, 1,1);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_FLICKER, EntityMoroonFlickFighter.class, 1,1);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_ASSASSIN, EntityMorBlindingAssassin.class, 1,1);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_SNIPER, EntityMoroonGhostArcher.class, 1,1);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_MARTIAL, EntityMoroonEliteMartialist.class, 1,1);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_VAMPIRE, EntityMoroonVampire.class, 1,1);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_MIND_MAGE, EntityMoroonMindMage.class, 1,1);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_ID_THIEF, EntityIdentityThief.class, 1,1);

            add(biome, ModConfig.SPAWN_CONF.SPAWN_STONE_ELEM, EntityStoneElemental.class, 1,4);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_STONE_ELEM, EntityDarkElemental.class, 1,2);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_STONE_ELEM, EntityPeaceKeeper.class, 1,4);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_STONE_ELEM, EntityWarManiac.class, 1,4);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_EARTHLIN, EntityEarthlin.class, 1,4);

            add(biome, ModConfig.SPAWN_CONF.SPAWN_SHADE_OTHERWORLD, EntityShadeOtherworld.class, 1,1);
            add(biome, ModConfig.SPAWN_CONF.SPAWN_VANILLA_MOB_ZEALOT, EntityVanillaZealot.class, 4,8);
//            if (BiomeManager.oceanBiomes.contains(biome))
//            {
//                //todo find why creatures can't generate
//                //Idealland.Log("register ocean:" + biome.getBiomeName());
//              //  add_water(biome, ModConfig.SPAWN_CONF.SPAWN_MOR_SQUID, EntityMoroonSquid.class, 4,4);
//                add_water(biome, 15, EntitySquidBase.class, 1,4);
//            }
//            add_water(biome, 80, EntityVanSquid.class, 1,4);
        }
    }

    private static void addOpenGroundSpawn(Map<Type, Set<Biome>> biomeMap) {
        for (Biome biome : Biome.REGISTRY) {
            if (!BiomeDictionary.hasType(biome, Type.DENSE)) {
                add(biome, ModConfig.SPAWN_CONF.SPAWN_SKELETON_TOWER, EntitySpawnTower.class, 1, 1);
                add(biome, ModConfig.SPAWN_CONF.SPAWN_SHADE_OTHERWORLD, EntityAutumnRiderGroup.class, 1,1);
            }
        }
    }

    private static void addHumidSpawn(Map<Type, Set<Biome>> biomeMap) {
        for (Biome biome : Biome.REGISTRY) {
            if (BiomeDictionary.hasType(biome, Type.WET) || (BiomeDictionary.hasType(biome, Type.WATER)))
            {
                add(biome, ModConfig.SPAWN_CONF.SPAWN_TIDE_MAKER, EntityMoroonTideMaker.class, 1, 1);
            }
        }
    }

    private static void addNetherSPAWN(Map<Type, Set<Biome>> biomeMap) {
        /*
         * NETHER
         */
//        for (Biome biome : biomeMap.get(Type.NETHER)) {
//            add(10, EntityMoroonTainter.class, 1, 4, biome);
//        }
    }

//    /**
//     * Bridge Method for simpler spawning registry
//     *
//     * @param weight        Spawn rate
//     * @param entityclassIn Entity
//     * @param groupCountMin Minimum amount (always 1)
//     * @param groupCountMax Maximum amount (depreciated due to chunk limits)
//     * @param biome         Biome
//     */
//    public static void add(int weight, Class<? extends EntityLiving> entityclassIn, int groupCountMin, int groupCountMax, Biome biome) {
//        if (weight > 0) {
//            biome.getSpawnableList(EnumCreatureType.MONSTER).add(new Biome.SpawnListEntry(entityclassIn, weight, groupCountMin, groupCountMax));
//        }
//    }


}
