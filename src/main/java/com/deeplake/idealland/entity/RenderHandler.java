package com.deeplake.idealland.entity;

import com.deeplake.idealland.IdlFramework;
import com.deeplake.idealland.entity.creatures.moroon.EntityMoroonUnitBase;
import com.deeplake.idealland.entity.creatures.render.*;
import com.deeplake.idealland.entity.projectiles.EntityIdlProjectile;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonUnitBase.class, RenderMoroonHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityIdlProjectile.class, renderManager -> new RenderBullet<>(renderManager, new ResourceLocation(IdlFramework.MODID,
                "textures/entity/projectiles/bullet_norm.png")));
    }
}
