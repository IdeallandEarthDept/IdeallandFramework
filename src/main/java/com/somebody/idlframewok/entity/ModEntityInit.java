package com.somebody.idlframewok.entity;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.entity.creatures.buildings.EntityIdlBuildingBase;
import com.somebody.idlframewok.entity.creatures.buildings.EntityIdlBuildingRoom;
import com.somebody.idlframewok.entity.creatures.buildings.EntityIdlBuildingWhiteTower;
import com.somebody.idlframewok.entity.creatures.buildings.EntityIdlBuildingXPWell;
import com.somebody.idlframewok.entity.creatures.buildings.mobbuilding.EntityStopDigger;
import com.somebody.idlframewok.entity.creatures.buildings.mobbuilding.EntityStopPlacer;
import com.somebody.idlframewok.entity.creatures.buildings.uprising.EntityCitadelTurret;
import com.somebody.idlframewok.entity.creatures.buildings.uprising.EntityMediumTurret;
import com.somebody.idlframewok.entity.creatures.cubix.EntityCubix;
import com.somebody.idlframewok.entity.creatures.ideallandTeam.*;
import com.somebody.idlframewok.entity.creatures.misc.*;
import com.somebody.idlframewok.entity.creatures.misc.mentor.*;
import com.somebody.idlframewok.entity.creatures.mobs.*;
import com.somebody.idlframewok.entity.creatures.mobs.boss.EntityBossBase;
import com.somebody.idlframewok.entity.creatures.mobs.boss.EntityBossFireTowerTrapOnly;
import com.somebody.idlframewok.entity.creatures.mobs.dungeon.EntityDungeonSentry;
import com.somebody.idlframewok.entity.creatures.mobs.dungeon.EntityFireSentry;
import com.somebody.idlframewok.entity.creatures.mobs.elemental.EntityEarthlin;
import com.somebody.idlframewok.entity.creatures.mobs.elemental.EntityGoldGolem;
import com.somebody.idlframewok.entity.creatures.mobs.gargoyle.EntityBasicGargoyle;
import com.somebody.idlframewok.entity.creatures.mobs.skyland.*;
import com.somebody.idlframewok.entity.creatures.moroon.*;
import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import com.somebody.idlframewok.entity.projectiles.EntityMoroonBullet;
import com.somebody.idlframewok.entity.projectiles.EntityProjectileSensorBlast;
import com.somebody.idlframewok.entity.projectiles.casting.EntityDelayBoomCast;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import static com.somebody.idlframewok.util.CommonDef.STANDARD_DUNGEON_MOB_RARITY;

///give @p minecraft:mob_spawner 1 0 {BlockEntityTag:{SpawnData:{id:"minecraft:skeleton"},SpawnPotentials:[{Weight:1,Entity:{id:"minecraft:skeleton"}}]}}
///give @p minecraft:mob_spawner 1 0 {BlockEntityTag:{SpawnData:{id:"idlframewok:vanilla_zealot"},SpawnPotentials:[{Weight:1,Entity:{id:"idlframewok:vanilla_zealot"}}]}}
public class ModEntityInit {
    private static int ENTITY_NEXT_ID = 1;
    public static void registerEntities()
    {
        //registerEntity("exp_one", EntityExpOne.class, 0xFF00FF, 0x00FF00);
        //registerEntity("turrent_one", EntityTurretPrototype.class, 0xFF00FF, 0x00cc00);
        //registerEntity("turrent_two", EntityTurretPrototype2.class,0xFF00FF, 0xFFFF00);

        registerEntity("eternal_soldier", EntityEternalZombie.class);
        registerEntity("hired_skeleton", EntityHiredSkeleton.class);
        registerEntity("clone_player", EntityPlayerClone.class);
        registerEntity("identity_thief", EntityIdentityThief.class);

        registerEntity("skeleton_tower", EntitySpawnTower.class);
        registerEntity("moroon_tainter", EntityMoroonTainter.class,0xff00ff, 0x000033);
        registerEntity("moroon_tainter_sp", EntityMorTainterSP.class,0xff00ff, 0x000033);
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
        registerEntity("moroon_standard_infantry_sp", EntityMoroonStandardInfantrySpawner.class);
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

        registerEntity("cubix_1", EntityCubix.class);
        registerEntity("squid_base", EntitySquidBase.class);
        registerEntity("squid_moroon", EntityMoroonSquid.class);

        registerEntity("elk_33", Entity33Elk.class);
        registerEntity("dummy", EntityDummy.class, 0xeeee00, 0xffffff);

        registerEntityNoEgg("bullet", EntityIdlProjectile.class);
        registerEntityNoEgg("bullet_sensor", EntityProjectileSensorBlast.class);
        registerEntityNoEgg("bullet_mor", EntityMoroonBullet.class);
        registerEntityNoEgg("bullet_blade", EntityProjectileBlade.class);

        registerEntityNoEgg("cast_delay_boom", EntityDelayBoomCast.class);
        registerEntity("test_boss", EntityBossBase.class);

        registerEntity("mentor_crit", EntityMentorCrit.class);
        registerEntity("mentor_crit_c", EntityMentorCritChance.class);
        registerEntity("mentor_eyes", EntityMentorEye.class);
        registerEntity("mentor_fire", EntityMentorFire.class);
        registerEntity("mentor_haste", EntityMentorHaste.class);
        registerEntity("mentor_life", EntityMentorLife.class);
        registerEntity("mentor_speed", EntityMentorSpeed.class);
        registerEntity("mentor_stalk", EntityMentorStalk.class);

        registerEntity("stone_elemental", EntityStoneElemental.class);
        registerEntity("dark_elemental", EntityDarkElemental.class);
        registerEntity("peace_keeper", EntityPeaceKeeper.class);
        registerEntity("war_maniac", EntityWarManiac.class);
        registerEntity("earthlin", EntityEarthlin.class);
        registerEntity("gold_golem", EntityGoldGolem.class);

        registerEntity("shade_otherworld", EntityShadeOtherworld.class);

        registerEntity("vanilla_zealot", EntityVanillaZealot.class);
        registerEntity("cathar_vex", EntityCatharVex.class);

        registerEntity("ghost_horse", EntityGhostHorse.class);
        registerEntity("autumn_visitor", EntityAutumnVisitor.class);
        registerEntity("autumn_rider", EntityAutumnRiderGroup.class);

        registerEntityNoEgg("alterego_skill", EntityAlterego.class);

        registerEntity("dungeon_sentry", EntityDungeonSentry.class);
        registerEntity("fire_sentry", EntityFireSentry.class);
        registerEntity("fire_boss_trap_only", EntityBossFireTowerTrapOnly.class);

        registerEntity("pyro_creeper", EntityCyroCreeper.class);
        registerEntity("cyro_creeper", EntityPyroCreeper.class);

        registerEntity("gargoyle_base", EntityBasicGargoyle.class);

        registerEntity("citadel_turret", EntityCitadelTurret.class);
        registerEntity("medium_turret", EntityMediumTurret.class);

        registerEntity("dark_raider", EntityDarkRaider.class);

        registerEntity("hill_jumper", EntityJumper.class);
        registerEntity("damp_dig", EntityStopDigger.class);
        registerEntity("damp_place", EntityStopPlacer.class);

        registerEntity("dmgod_pillar", EntityDemonGodPillar.class);
        ///give @p minecraft:mob_spawner 1 0 {BlockEntityTag:{SpawnData:{id:"idlframewok:moroon_standard_infantry_sp"},SpawnPotentials:[{Weight:1,Entity:{id:"idlframewok:moroon_standard_infantry_sp"}}]}}

        //Assign Dungeons
        DungeonHooks.addDungeonMob(EntityList.getKey(EntityMoroonStandardInfantrySpawner.class), STANDARD_DUNGEON_MOB_RARITY >> 1);
        DungeonHooks.addDungeonMob(EntityList.getKey(EntityMoroonVampire.class), STANDARD_DUNGEON_MOB_RARITY >> 1);
        DungeonHooks.addDungeonMob(EntityList.getKey(EntityMorTainterSP.class), STANDARD_DUNGEON_MOB_RARITY);
        DungeonHooks.addDungeonMob(EntityList.getKey(EntityVanillaZealot.class), STANDARD_DUNGEON_MOB_RARITY);
        DungeonHooks.addDungeonMob(EntityList.getKey(EntityEarthlin.class), STANDARD_DUNGEON_MOB_RARITY);

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
