package com.deeplake.idealland.entity;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.entity.creatures.buildings.EntityIdlBuildingBase;
import com.deeplake.idealland.entity.creatures.ideallandTeam.EntityIdlTurret;
import com.deeplake.idealland.entity.creatures.misc.Entity33Elk;
import com.deeplake.idealland.entity.creatures.misc.EntityExpOne;
import com.deeplake.idealland.entity.creatures.misc.EntityTurretPrototype2;
import com.deeplake.idealland.entity.creatures.moroon.EntityMoroonBombBeacon;
import com.deeplake.idealland.entity.creatures.moroon.EntityMoroonUnitBase;
import com.deeplake.idealland.entity.creatures.render.*;
import com.deeplake.idealland.entity.projectiles.EntityIdlProjectile;
import com.deeplake.idealland.entity.projectiles.EntityMoroonBullet;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonUnitBase.class, RenderMoroonHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonBombBeacon.class, RenderMoroonBeacon::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityExpOne.class, RenderExpOne::new);

        //RenderingRegistry.registerEntityRenderingHandler(EntityTurretPrototype.class, manager -> new RenderTurret(manager));

        //formal turret
        RenderingRegistry.registerEntityRenderingHandler(EntityIdlTurret.class, RenderTurret::new);

        RenderingRegistry.registerEntityRenderingHandler(Entity33Elk.class, manager -> new RenderMoroonHumanoid(manager));

        RenderingRegistry.registerEntityRenderingHandler(EntityTurretPrototype2.class, RenderTurret2::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityIdlProjectile.class, renderManager -> new RenderBullet<>(renderManager, new ResourceLocation(Idealland.MODID,
                "textures/entity/projectiles/bullet_norm.png")));

        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonBullet.class, renderManager -> new RenderBullet<>(renderManager, new ResourceLocation(Idealland.MODID,
                "textures/entity/projectiles/bullet_mor.png")));

        RenderingRegistry.registerEntityRenderingHandler(EntityIdlBuildingBase.class, RenderConstruction::new);



//        RenderingRegistry.registerEntityRenderingHandler(EntityIdlProjectile.class, new IRenderFactory<EntityIdlProjectile>()
//        {
//            @Override
//            public Render<? super EntityIdlProjectile> createRenderFor(RenderManager manager){
//                return new RenderSnowball(manager, Items.APPLE);
//            }
//        });
//        RenderingRegistry.registerEntityRenderingHandler(EntityIdlProjectile.class, new RenderSnowball(manager, Items.APPLE));
//        RenderingRegistry.registerEntityRenderingHandler(EntityLimoniteBullet.class, renderManager -> new LimoniteBulletRenderer(renderManager, new ResourceLocation("aoa3", "textures/entities/projectiles/bullets/limonite_bullet.png")));
    }
}
