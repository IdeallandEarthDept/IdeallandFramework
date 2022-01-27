package com.somebody.idlframewok.entity.render.model;

import com.somebody.idlframewok.entity.creatures.cubix.EntityCubix;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * cubix_1 - TaoismDeepLake
 * Created using Tabula 7.1.0
 */
public class ModelCubix1 extends ModelBase {
    public double[] modelScale = new double[] { 0.5D, 0.5D, 0.5D };
    public ModelRenderer cub1;
    public ModelRenderer cub1_1;

    public ModelCubix1() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.cub1 = new ModelRenderer(this, 0, 0);
        this.cub1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.cub1.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.cub1_1 = new ModelRenderer(this, 0, 16);
        this.cub1_1.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.cub1_1.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.pushMatrix();
        GlStateManager.scale(1D / modelScale[0], 1D / modelScale[1], 1D / modelScale[2]);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.cub1.offsetX, this.cub1.offsetY, this.cub1.offsetZ);
        GlStateManager.translate(this.cub1.rotationPointX * f5, this.cub1.rotationPointY * f5, this.cub1.rotationPointZ * f5);
        GlStateManager.scale(0.8D, 0.8D, 0.8D);
        GlStateManager.translate(-this.cub1.offsetX, -this.cub1.offsetY, -this.cub1.offsetZ);
        GlStateManager.translate(-this.cub1.rotationPointX * f5, -this.cub1.rotationPointY * f5, -this.cub1.rotationPointZ * f5);
        this.cub1.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.cub1_1.offsetX, this.cub1_1.offsetY, this.cub1_1.offsetZ);
        GlStateManager.translate(this.cub1_1.rotationPointX * f5, this.cub1_1.rotationPointY * f5, this.cub1_1.rotationPointZ * f5);
        GlStateManager.scale(0.8D, 0.8D, 0.8D);
        GlStateManager.translate(-this.cub1_1.offsetX, -this.cub1_1.offsetY, -this.cub1_1.offsetZ);
        GlStateManager.translate(-this.cub1_1.rotationPointX * f5, -this.cub1_1.rotationPointY * f5, -this.cub1_1.rotationPointZ * f5);
        this.cub1_1.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    /**
     * This is a helper function from Tabula to set the rotationIn of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public float omega = 0.017453292F * 10.1f;
    public float theta = 0f;

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        //float healthFactor = 1f;
        EntityLivingBase cubix = (EntityCubix) entityIn;

        theta += omega *(limbSwingAmount + 1f) * (2 - cubix.getHealth() / cubix.getMaxHealth());
        theta %= 6.283f;

        this.cub1.rotateAngleY = theta;
        this.cub1_1.rotateAngleY = -theta;
    }
}
