package com.somebody.idlframewok.entity.render;

import com.somebody.idlframewok.entity.creatures.buildings.uprising.EntityMediumTurret;
import com.somebody.idlframewok.entity.render.model.ModelMiddleTurret;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderMediumTurret extends RenderLiving<EntityMediumTurret> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/uprising/middle_turret.png");

    public RenderMediumTurret(RenderManager manager) {
        super(manager, new ModelMiddleTurret(), 1F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMediumTurret entity) {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(EntityMediumTurret entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }

    //set the scale here
    @Override
    protected void preRenderCallback(EntityMediumTurret entitylivingbaseIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
        entitylivingbaseIn.getFaction().applyColor();
        GlStateManager.scale(1.5f, 1.5f, 1.5f);
    }

    //dunno how to use.
//    @Override
//    protected int getColorMultiplier(EntityMediumTurret entitylivingbaseIn, float lightBrightness, float partialTickTime) {
//        switch (entitylivingbaseIn.getFaction()) {
//            case PLAYER:
//                break;
//            case IDEALLAND:
//                break;
//            case MOB_VANILLA:
//            case MOB_VAN_ZOMBIE:
//                return CommonDef.TeamColor.MOB;
//            case MOROON:
//                return CommonDef.TeamColor.MOROON;
//            case CRITTER:
//                break;
//        }
//
//        return super.getColorMultiplier(entitylivingbaseIn, lightBrightness, partialTickTime);
//    }
}

