package com.somebody.idlframewok.entity.creatures.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * TurrentPrototype - TaoismDeepLake
 * Created using Tabula 7.1.0
 */
public class ModelTurretPrototype extends ModelBase {
    public ModelRenderer BaseHead;
    public ModelRenderer BaseRoot;
    public ModelRenderer BarrelLookBase;
    public ModelRenderer BodyL;
    public ModelRenderer HeadR;
    public ModelRenderer Barrel;

    public ModelTurretPrototype() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.Barrel = new ModelRenderer(this, 10, 17);
        this.Barrel.mirror = true;
        this.Barrel.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Barrel.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 32, 0.0F);
        this.BarrelLookBase = new ModelRenderer(this, 45, 0);
        this.BarrelLookBase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BarrelLookBase.addBox(-4.0F, -2.0F, -2.0F, 8, 4, 4, 0.0F);
        this.HeadR = new ModelRenderer(this, 0, 17);
        this.HeadR.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.HeadR.addBox(3.0F, -8.0F, -8.0F, 5, 16, 16, 0.0F);
        this.BaseHead = new ModelRenderer(this, 0, 0);
        this.BaseHead.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.BaseHead.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.BodyL = new ModelRenderer(this, 60, 0);
        this.BodyL.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyL.addBox(-8.0F, -8.0F, -8.0F, 5, 16, 16, 0.0F);
        this.BaseRoot = new ModelRenderer(this, 0, 0);
        this.BaseRoot.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.BaseRoot.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 4, 0.0F);
        this.BarrelLookBase.addChild(this.Barrel);
        this.BaseHead.addChild(this.BarrelLookBase);
        this.BaseHead.addChild(this.HeadR);
        this.BaseHead.addChild(this.BodyL);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.BaseHead.render(scale);
        this.BaseRoot.render(scale);
//        this.BaseHead.renderWithRotation(scale);
//        this.BaseRoot.renderWithRotation(scale);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.BarrelLookBase.offsetZ = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
        this.BaseHead.rotateAngleY = netHeadYaw * 0.017453292F;
        this.BarrelLookBase.rotateAngleX = 3.14F + headPitch * 0.017453292F;
    }
}
