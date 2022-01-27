package com.somebody.idlframewok.entity;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

///give @p minecraft:mob_spawner 1 0 {BlockEntityTag:{SpawnData:{id:"minecraft:skeleton"},SpawnPotentials:[{Weight:1,Entity:{id:"minecraft:skeleton"}}]}}
///give @p minecraft:mob_spawner 1 0 {BlockEntityTag:{SpawnData:{id:"idlframewok:vanilla_zealot"},SpawnPotentials:[{Weight:1,Entity:{id:"idlframewok:vanilla_zealot"}}]}}
public class ModEntityInit {
    private static int ENTITY_NEXT_ID = 1;
    public static void registerEntities()
    {
        //registerEntity("exp_one", EntityExpOne.class, 0xFF00FF, 0x00FF00);

//        registerEntity("dmgod_pillar", EntityDemonGodPillar.class);

        ///give @p minecraft:mob_spawner 1 0 {BlockEntityTag:{SpawnData:{id:"idlframewok:moroon_standard_infantry_sp"},SpawnPotentials:[{Weight:1,Entity:{id:"idlframewok:moroon_standard_infantry_sp"}}]}}

        //Assign Dungeons
//        DungeonHooks.addDungeonMob(EntityList.getKey(EntityMoroonStandardInfantrySpawner.class), STANDARD_DUNGEON_MOB_RARITY >> 1);

        //DataFixer datafixer = new DataFixer(1343);
    }

    private  static  void registerEntity(String name, Class<? extends Entity> entity)
    {
        registerEntity(name, entity, ENTITY_NEXT_ID, 50, 0xff00ff, 0x000000);
    }

    private  static  void registerEntity(String name, Class<? extends Entity> entity, int color1, int color2)
    {
        registerEntity(name, entity, ENTITY_NEXT_ID, 50, color1, color2);
    }

    private  static  void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2){
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name),
                entity,
                name,
                id,
                Idealland.instance,
                range,
                1,
                true,
                color1, color2
                );
        ENTITY_NEXT_ID++;
    }

    private  static  void registerEntityNoEgg(String name, Class<? extends Entity> entity)
    {
        registerEntityNoEgg(name, entity, ENTITY_NEXT_ID, 50);
    }

    private  static  void registerEntityNoEgg(String name, Class<? extends Entity> entity, int id, int range){
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name),
                entity,
                name,
                id,
                Idealland.instance,
                range,
                1,
                true
        );
        ENTITY_NEXT_ID++;
    }
}
