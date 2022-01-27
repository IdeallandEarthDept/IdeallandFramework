package com.somebody.idlframewok.entity.render;

import com.somebody.idlframewok.entity.creatures.buildings.uprising.EntityCitadelTurret;
import com.somebody.idlframewok.entity.render.model.ModelCitadelTurret;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderCitadelTurret extends RenderLiving<EntityCitadelTurret> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/uprising/citadel_turret_1.png");

    public RenderCitadelTurret(RenderManager manager) {
        super(manager, new ModelCitadelTurret(), 4F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityCitadelTurret entity) {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(EntityCitadelTurret entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }

    //set the scale here
    @Override
    protected void preRenderCallback(EntityCitadelTurret entitylivingbaseIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        GlStateManager.scale(3f, 3f, 3f);
        entitylivingbaseIn.getFaction().applyColor();
    }
}
