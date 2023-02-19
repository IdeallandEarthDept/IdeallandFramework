package com.somebody.idlframework.entity.creatures.render;

import javax.annotation.Nullable;

import com.somebody.idlframework.entity.creatures.EntityModUnit;
import com.somebody.idlframework.entity.creatures.model.ModelTurretPrototype;
import com.somebody.idlframework.util.Reference;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTurret extends RenderLiving<EntityModUnit> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/turret_one.png");
    public RenderTurret(RenderManager manager){
        super(manager, new ModelTurretPrototype(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityModUnit entity) {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(EntityModUnit entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);

    }
}
