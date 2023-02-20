package com.somebody.idlframewok.entity.creatures.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelCow - Either Mojang or a mod author
 * Created using Tabula 7.1.0
 */
public class ModelCentaur extends ModelBase {
    public ModelRenderer cow_body;
    public ModelRenderer cow_belly;
    public ModelRenderer cow_leg_2_L;
    public ModelRenderer cow_leg_1_L;
    public ModelRenderer cow_leg_2_R;
    public ModelRenderer cow_leg_1_R;
    public ModelRenderer VillagerHead;
    public ModelRenderer VillagerRHand;
    public ModelRenderer VillagerLHand;
    public ModelRenderer VillagerCenterHand;
    public ModelRenderer VillagerChest;
    public ModelRenderer VillagerCoat;
    public ModelRenderer VillagerNose;

    public ModelCentaur() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.VillagerLHand = new ModelRenderer(this, 44, 22);
        this.VillagerLHand.setRotationPoint(0.20000000000000004F, -12.0F, -5.799999999999996F);
        this.VillagerLHand.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(VillagerLHand, -0.7499679795819634F, 0.0F, 0.0F);
        this.VillagerCoat = new ModelRenderer(this, 0, 38);
        this.VillagerCoat.setRotationPoint(0.20000000000000004F, -15.0F, -4.799999999999999F);
        this.VillagerCoat.addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, 0.5F);
        this.VillagerNose = new ModelRenderer(this, 24, 0);
        this.VillagerNose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.VillagerNose.addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, 0.0F);
        this.VillagerRHand = new ModelRenderer(this, 44, 22);
        this.VillagerRHand.setRotationPoint(0.20000000000000004F, -12.0F, -5.799999999999996F);
        this.VillagerRHand.addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(VillagerRHand, -0.7499679795819634F, 0.0F, 0.0F);
        this.cow_belly = new ModelRenderer(this, 52, 0);
        this.cow_belly.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.cow_belly.addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1, 0.0F);
        this.setRotateAngle(cow_belly, 1.5707963267948966F, 0.0F, 0.0F);
        this.VillagerCenterHand = new ModelRenderer(this, 40, 38);
        this.VillagerCenterHand.setRotationPoint(0.20000000000000004F, -12.0F, -5.799999999999996F);
        this.VillagerCenterHand.addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, 0.0F);
        this.setRotateAngle(VillagerCenterHand, -0.7499679795819634F, 0.0F, 0.0F);
        this.VillagerHead = new ModelRenderer(this, 0, 0);
        this.VillagerHead.setRotationPoint(0.20000000000000004F, -15.0F, -4.799999999999999F);
        this.VillagerHead.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.cow_body = new ModelRenderer(this, 18, 4);
        this.cow_body.setRotationPoint(0.0F, 5.0F, 2.0F);
        this.cow_body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
        this.setRotateAngle(cow_body, 1.5707963267948966F, 0.0F, 0.0F);
        this.cow_leg_2_L = new ModelRenderer(this, 0, 16);
        this.cow_leg_2_L.setRotationPoint(4.0F, 12.0F, 7.0F);
        this.cow_leg_2_L.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.VillagerChest = new ModelRenderer(this, 16, 20);
        this.VillagerChest.setRotationPoint(0.20000000000000004F, -15.0F, -4.799999999999999F);
        this.VillagerChest.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.cow_leg_1_L = new ModelRenderer(this, 0, 16);
        this.cow_leg_1_L.setRotationPoint(4.0F, 12.0F, -6.0F);
        this.cow_leg_1_L.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.cow_leg_2_R = new ModelRenderer(this, 0, 16);
        this.cow_leg_2_R.setRotationPoint(-4.0F, 12.0F, 7.0F);
        this.cow_leg_2_R.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.cow_leg_1_R = new ModelRenderer(this, 0, 16);
        this.cow_leg_1_R.setRotationPoint(-4.0F, 12.0F, -6.0F);
        this.cow_leg_1_R.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.VillagerHead.addChild(this.VillagerNose);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.VillagerLHand.render(f5);
        this.VillagerCoat.render(f5);
        this.VillagerRHand.render(f5);
        this.cow_belly.render(f5);
        this.VillagerCenterHand.render(f5);
        this.VillagerHead.render(f5);
        this.cow_body.render(f5);
        this.cow_leg_2_L.render(f5);
        this.VillagerChest.render(f5);
        this.cow_leg_1_L.render(f5);
        this.cow_leg_2_R.render(f5);
        this.cow_leg_1_R.render(f5);
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
        this.cow_leg_1_L.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.cow_leg_2_L.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        this.cow_leg_1_R.rotateAngleX = - MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.cow_leg_2_R.rotateAngleX = - MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        this.VillagerHead.rotateAngleY = netHeadYaw * 0.017453292F;
        this.VillagerHead.rotateAngleX = headPitch * 0.017453292F;
        //super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    }
}
