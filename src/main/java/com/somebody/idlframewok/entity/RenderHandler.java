package com.somebody.idlframewok.entity;

import com.somebody.idlframewok.IdlFramework;
import com.somebody.idlframewok.entity.creatures.moroon.EntityMoroonUnitBase;
import com.somebody.idlframewok.entity.creatures.render.*;
import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonUnitBase.class, RenderMoroonHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityIdlProjectile.class, renderManager -> new RenderBullet<>(renderManager, new ResourceLocation(IdlFramework.MODID,
                "textures/entity/projectiles/bullet_norm.png")));
    }
}
