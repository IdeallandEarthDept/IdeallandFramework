package com.somebody.idlframewok.entity;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.entity.creatures.buildings.EntityIdlBuildingBase;
import com.somebody.idlframewok.entity.creatures.buildings.mobbuilding.EntityStopDigger;
import com.somebody.idlframewok.entity.creatures.buildings.mobbuilding.EntityStopPlacer;
import com.somebody.idlframewok.entity.creatures.buildings.uprising.EntityCitadelTurret;
import com.somebody.idlframewok.entity.creatures.buildings.uprising.EntityMediumTurret;
import com.somebody.idlframewok.entity.creatures.cubix.EntityCubix;
import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityAlterego;
import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityIdlTurret;
import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityPlayerClone;
import com.somebody.idlframewok.entity.creatures.misc.*;
import com.somebody.idlframewok.entity.creatures.misc.mentor.EntityMentorBase;
import com.somebody.idlframewok.entity.creatures.mobs.*;
import com.somebody.idlframewok.entity.creatures.mobs.dungeon.EntityDungeonSentry;
import com.somebody.idlframewok.entity.creatures.mobs.elemental.EntityEarthlin;
import com.somebody.idlframewok.entity.creatures.mobs.gargoyle.EntityGargoyleBase;
import com.somebody.idlframewok.entity.creatures.mobs.skyland.*;
import com.somebody.idlframewok.entity.creatures.moroon.EntityMoroonBombBeacon;
import com.somebody.idlframewok.entity.creatures.moroon.EntityMoroonUnitBase;
import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import com.somebody.idlframewok.entity.projectiles.EntityMoroonBullet;
import com.somebody.idlframewok.entity.projectiles.casting.EntityCastingBase;
import com.somebody.idlframewok.entity.projectiles.render.RenderBladeBullet;
import com.somebody.idlframewok.entity.render.*;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.renderer.entity.RenderSquid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonUnitBase.class, renderManager -> new RenderHumanoid(renderManager, "moroon_humanoid"));

        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonBombBeacon.class, RenderMoroonBeacon::new);

//        RenderingRegistry.registerEntityRenderingHandler(EntityExpOne.class, RenderExpOne::new);

        //RenderingRegistry.registerEntityRenderingHandler(EntityTurretPrototype.class, manager -> new RenderTurret(manager));

        //formal turret
        RenderingRegistry.registerEntityRenderingHandler(EntityIdlTurret.class, RenderTurret::new);

        RenderingRegistry.registerEntityRenderingHandler(Entity33Elk.class, RenderHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerClone.class, RenderCloneHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityIdentityThief.class, RenderCloneHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityTurretPrototype2.class, RenderTurret2::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityIdlProjectile.class, renderManager -> new RenderBullet<>(renderManager, new ResourceLocation(Idealland.MODID,
                "textures/entity/projectiles/bullet_norm.png")));

        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonBullet.class, renderManager -> new RenderBullet<>(renderManager, new ResourceLocation(Idealland.MODID,
                "textures/entity/projectiles/bullet_mor.png")));

        RenderingRegistry.registerEntityRenderingHandler(EntityProjectileBlade.class, RenderBladeBullet::new);

        //RenderingRegistry.registerEntityRenderingHandler(EntityProjectileBlade.class, RenderTurret::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityIdlBuildingBase.class, RenderConstruction::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityCubix.class, RenderCubix::new);

        RenderingRegistry.registerEntityRenderingHandler(EntitySquidBase.class, renderManager -> new RenderModSquid<>(renderManager, new ResourceLocation(Idealland.MODID,
                "textures/entity/squid.png")));

        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonSquid.class, renderManager -> new RenderModSquid<>(renderManager, new ResourceLocation(Idealland.MODID,
                "textures/entity/squid_moroon.png")));

        RenderingRegistry.registerEntityRenderingHandler(EntityVanSquid.class, RenderSquid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityCastingBase.class, RenderNone::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityCitadelTurret.class, RenderCitadelTurret::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityMentorBase.class, renderManager -> new RenderHumanoid(renderManager, "skin_mentor"));

        RenderingRegistry.registerEntityRenderingHandler(EntityStoneElemental.class, renderManager -> new RenderHumanoid(renderManager, "stone_elemental"));

        RenderingRegistry.registerEntityRenderingHandler(EntityShadeOtherworld.class, renderManager -> new RenderHumanoid(renderManager, "skin_shade"));

        RenderingRegistry.registerEntityRenderingHandler(EntityDarkElemental.class, renderManager -> new RenderHumanoid(renderManager, "skin_dark_elemental"));

        RenderingRegistry.registerEntityRenderingHandler(EntityVanillaZealot.class, renderManager -> new RenderHumanoid(renderManager, "skin_vanilla_zealot"));

        RenderingRegistry.registerEntityRenderingHandler(EntityAutumnVisitor.class, renderManager -> new RenderHumanoid(renderManager, "autumn_visitor"));

        RenderingRegistry.registerEntityRenderingHandler(EntityPeaceKeeper.class, renderManager -> new RenderHumanoid(renderManager, "peace_keeper"));

        RenderingRegistry.registerEntityRenderingHandler(EntityWarManiac.class, renderManager -> new RenderHumanoid(renderManager, "war_maniac"));

        RenderingRegistry.registerEntityRenderingHandler(EntityEarthlin.class, renderManager -> new RenderHumanoid(renderManager, "earthlin"));

        RenderingRegistry.registerEntityRenderingHandler(EntityGhostHorse.class, RenderModHorse::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityAutumnRiderGroup.class, RenderNone::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityAlterego.class, RenderCloneHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityDungeonSentry.class, RenderHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityCyroCreeper.class, RenderCloneHumanoid::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPyroCreeper.class, RenderExploder::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityGargoyleBase.class, renderManager -> new RenderHumanoid(renderManager, "skin_gargoyle"));
//        RenderingRegistry.registerEntityRenderingHandler(EntityBossBase.class, renderManager -> new RenderHumanoid(renderManager, "skin_gargoyle"));
        RenderingRegistry.registerEntityRenderingHandler(EntityDemonGodPillar.class, renderManager -> new RenderDemonGodPillar(renderManager));

        RenderingRegistry.registerEntityRenderingHandler(EntityMediumTurret.class, RenderMediumTurret::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDarkRaider.class, RenderDarkRaider::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityStopDigger.class, RenderDigDamper::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityStopPlacer.class, RenderDigDamper::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityCatharVex.class, renderManager -> new RenderModVex(renderManager,
                new ResourceLocation(Reference.MOD_ID + ":textures/entity/cathar_vex.png"),
                new ResourceLocation(Reference.MOD_ID + ":textures/entity/cathar_vex_charging.png")));
    }
}
