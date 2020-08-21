package com.deeplake.idealland.entity;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.entity.creatures.buildings.EntityIdlBuildingBase;
import com.deeplake.idealland.entity.creatures.buildings.EntityIdlBuildingRoom;
import com.deeplake.idealland.entity.creatures.buildings.EntityIdlBuildingWhiteTower;
import com.deeplake.idealland.entity.creatures.buildings.EntityIdlBuildingXPWell;
import com.deeplake.idealland.entity.creatures.ideallandTeam.*;
import com.deeplake.idealland.entity.creatures.misc.*;
import com.deeplake.idealland.entity.creatures.moroon.*;
import com.deeplake.idealland.entity.projectiles.EntityIdlProjectile;
import com.deeplake.idealland.entity.projectiles.EntityMoroonBullet;
import com.deeplake.idealland.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import static com.deeplake.idealland.util.CommonDef.STANDARD_DUNGEON_MOB_RARITY;

public class ModEntityInit {
    private static int ENTITY_NEXT_ID = 1;
    public static void registerEntities()
    {
        //registerEntity("exp_one", EntityExpOne.class, 0xFF00FF, 0x00FF00);
        //registerEntity("turrent_one", EntityTurretPrototype.class, 0xFF00FF, 0x00cc00);
        //registerEntity("turrent_two", EntityTurretPrototype2.class,0xFF00FF, 0xFFFF00);

        registerEntity("eternal_soldier", EntityEternalZombie.class);
        registerEntity("hired_skeleton", EntityHiredSkeleton.class);

        registerEntity("skeleton_tower", EntitySpawnTower.class);
        registerEntity("moroon_tainter", EntityMoroonTainter.class,0xff00ff, 0x000033);
        registerEntity("moroon_tide_maker", EntityMoroonTideMaker.class,0xff00ff, 0x0000FF);
        registerEntity("moroon_orbital_beacon", EntityMoroonBombBeacon.class);
        registerEntity("moroon_bastion_walker", EntityMoroonBastionWalker.class);
        registerEntity("moroon_flick_fighter", EntityMoroonFlickFighter.class);
        registerEntity("moroon_blinding_assassin", EntityMorBlindingAssassin.class);
        registerEntity("moroon_sniper", EntityMoroonGhostArcher.class);
        registerEntity("moroon_martialist", EntityMoroonEliteMartialist.class);
        registerEntity("moroon_vampire", EntityMoroonVampire.class);
        registerEntity("moroon_mind_mage", EntityMoroonMindMage.class);
        registerEntity("moroon_standard_infantry", EntityMoroonStandardInfantry.class);
        registerEntity("moroon_boss_avatar_of_life", EntityBossLifeHorde.class);

        registerEntity("idealland_whitetower_core", EntityIDLWhiteTowerCore.class, ENTITY_NEXT_ID, 128, 0xeeee00, 0xffffff);
        registerEntity("idealland_electric_interferer", EntityIDLInterferer.class);
        registerEntity("idealland_builder_base", EntityIdlBuildingBase.class);
        registerEntity("idealland_builder_test_2", EntityIdlBuildingWhiteTower.class);
        registerEntity("idealland_builder_xp_well", EntityIdlBuildingXPWell.class);
        registerEntity("idealland_builder_room", EntityIdlBuildingRoom.class);

        registerEntity("idealland_xp_well", EntityIDLXPWell.class);
        registerEntity("idealland_turret_prototype", EntityIdlTurret.class);
        registerEntity("idealland_turret_aa", EntityIDLAATurret.class);
        registerEntity("idealland_radar_air", EntityAntiAirRadar.class);



        registerEntity("elk_33", Entity33Elk.class);

        registerEntity("bullet", EntityIdlProjectile.class);
        registerEntity("bullet_mor", EntityMoroonBullet.class);


        //Assign Dungeons
        DungeonHooks.addDungeonMob(EntityList.getKey(EntityMoroonTainter.class), STANDARD_DUNGEON_MOB_RARITY);
        DungeonHooks.addDungeonMob(EntityList.getKey(EntityMoroonBastionWalker.class), STANDARD_DUNGEON_MOB_RARITY >> 1);

        DataFixer datafixer = new DataFixer(1343);
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
}
