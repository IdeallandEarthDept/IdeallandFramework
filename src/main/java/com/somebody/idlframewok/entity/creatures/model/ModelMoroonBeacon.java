package com.somebody.idlframewok.entity.creatures.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * MoroonBeacon - TaoismDeeplake
 * Created using Tabula 7.1.0
 */
public class ModelMoroonBeacon extends ModelBase {
    public ModelRenderer CenterPillar;
    public ModelRenderer BuildBase;
    public ModelRenderer RotationBase;
    public ModelRenderer RotationBase_1;
    public ModelRenderer SidePillar;
    public ModelRenderer SidePillar_1;
    public ModelRenderer SidePillar_2;
    public ModelRenderer SidePillar_3;
    public ModelRenderer SidePillar_4;
    public ModelRenderer SidePillar_5;

    public ModelMoroonBeacon() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.SidePillar = new ModelRenderer(this, 0, 0);
        this.SidePillar.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.SidePillar.addBox(-1.0F, 1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.SidePillar_5 = new ModelRenderer(this, 0, 0);
        this.SidePillar_5.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.SidePillar_5.addBox(-1.0F, -1.0F, -7.0F, 2, 2, 14, 0.0F);
        this.SidePillar_1 = new ModelRenderer(this, 0, 0);
        this.SidePillar_1.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.SidePillar_1.addBox(-1.0F, 1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.RotationBase_1 = new ModelRenderer(this, 0, 0);
        this.RotationBase_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RotationBase_1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.SidePillar_3 = new ModelRenderer(this, 0, 0);
        this.SidePillar_3.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.SidePillar_3.addBox(-1.0F, 1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.RotationBase = new ModelRenderer(this, 0, 0);
        this.RotationBase.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.RotationBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.SidePillar_2 = new ModelRenderer(this, 0, 0);
        this.SidePillar_2.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.SidePillar_2.addBox(-1.0F, -1.0F, -7.0F, 2, 2, 14, 0.0F);
        this.SidePillar_4 = new ModelRenderer(this, 0, 0);
        this.SidePillar_4.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.SidePillar_4.addBox(-1.0F, 1.0F, -1.0F, 2, 8, 2, 0.0F);
        this.CenterPillar = new ModelRenderer(this, 0, 0);
        this.CenterPillar.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.CenterPillar.addBox(-2.0F, -8.0F, -2.0F, 4, 32, 4, 0.0F);
        this.BuildBase = new ModelRenderer(this, 0, 0);
        this.BuildBase.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.BuildBase.addBox(-4.0F, -0.5F, -4.0F, 8, 1, 8, 0.0F);
        this.RotationBase.addChild(this.SidePillar);
        this.RotationBase_1.addChild(this.SidePillar_5);
        this.RotationBase.addChild(this.SidePillar_1);
        this.RotationBase_1.addChild(this.SidePillar_3);
        this.RotationBase.addChild(this.SidePillar_2);
        this.RotationBase_1.addChild(this.SidePillar_4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.RotationBase_1.render(f5);
        this.RotationBase.render(f5);
        this.CenterPillar.render(f5);
        this.BuildBase.render(f5);
    }


    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {

        this.RotationBase.rotateAngleY = (float) (Math.sin(ageInTicks * 0.017453292F) * 5f);
        this.RotationBase_1.rotateAngleY = ageInTicks * 0.017453292F * -10.1f;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
