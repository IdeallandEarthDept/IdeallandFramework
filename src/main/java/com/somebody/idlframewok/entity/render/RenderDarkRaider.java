package com.somebody.idlframewok.entity.render;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.entity.creatures.mobs.EntityDarkRaider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;

public class RenderDarkRaider extends RenderModUnit {
    public RenderDarkRaider(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    protected void preRenderCallback(EntityModUnit entitylivingbaseIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        if (entitylivingbaseIn instanceof EntityDarkRaider) {
            float ratio = 1 - ((EntityDarkRaider) entitylivingbaseIn).getShieldPecentage();
            GlStateManager.color(ratio, ratio, ratio);
        }
    }

    //maybe use net.minecraftforge.client.event.RenderLivingEvent.Pre?
}
