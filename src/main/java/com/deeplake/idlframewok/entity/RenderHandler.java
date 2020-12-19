package com.deeplake.idlframewok.entity;

import com.deeplake.idlframewok.IdlFramework;
import com.deeplake.idlframewok.entity.creatures.moroon.EntityMoroonUnitBase;
import com.deeplake.idlframewok.entity.creatures.render.*;
import com.deeplake.idlframewok.entity.projectiles.EntityIdlProjectile;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMoroonUnitBase.class, RenderMoroonHumanoid::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityIdlProjectile.class, renderManager -> new RenderBullet<>(renderManager, new ResourceLocation(IdlFramework.MODID,
                "textures/entity/projectiles/bullet_norm.png")));
    }
}
