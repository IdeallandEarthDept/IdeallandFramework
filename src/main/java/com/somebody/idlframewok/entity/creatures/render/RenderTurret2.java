package com.somebody.idlframewok.entity.creatures.render;

import com.somebody.idlframewok.entity.creatures.misc.EntityTurretPrototype2;
import com.somebody.idlframewok.entity.creatures.model.ModelTurretPrototype;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderTurret2 extends RenderLiving<EntityTurretPrototype2> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/turret_one.png");
    public RenderTurret2(RenderManager manager){
        super(manager, new ModelTurretPrototype(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityTurretPrototype2 entity) {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(EntityTurretPrototype2 entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}
