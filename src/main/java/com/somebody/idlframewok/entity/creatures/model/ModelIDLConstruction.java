package com.somebody.idlframewok.entity.creatures.model;

import com.somebody.idlframewok.entity.creatures.buildings.EntityIdlBuildingBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

/**
 * ModelIDLConstruction - TaoismDeepLake
 * Created using Tabula 7.1.0
 */
public class ModelIDLConstruction extends ModelBase {
    public ModelRenderer Pillar;
    public ModelRenderer Floor;
    public ModelRenderer Target;
    public ModelRenderer CornerBoard;
    public ModelRenderer CornerBoard_1;
    public ModelRenderer CornerBoard_2;
    public ModelRenderer CornerBoard_3;

    public ModelIDLConstruction() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Pillar = new ModelRenderer(this, 0, 0);
        this.Pillar.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.Pillar.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F);
        this.CornerBoard = new ModelRenderer(this, 44, 0);
        this.CornerBoard.setRotationPoint(8.0F, 23.5F, -8.0F);
        this.CornerBoard.addBox(0.0F, 0.0F, -1.0F, 4, 1, 2, 0.0F);
        this.setRotateAngle(CornerBoard, 0.0F, 0.7853981633974483F, 0.0F);
        this.Target = new ModelRenderer(this, 32, 0);
        this.Target.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Target.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
        this.CornerBoard_2 = new ModelRenderer(this, 46, 6);
        this.CornerBoard_2.setRotationPoint(-8.0F, 23.5F, 8.0F);
        this.CornerBoard_2.addBox(0.0F, 0.0F, -1.0F, 4, 1, 2, 0.0F);
        this.setRotateAngle(CornerBoard_2, 0.0F, -2.356194490192345F, 0.0F);
        this.Floor = new ModelRenderer(this, 8, 0);
        this.Floor.setRotationPoint(0.0F, 23.5F, 0.0F);
        this.Floor.addBox(-4.0F, 0.0F, -4.0F, 8, 1, 8, 0.0F);
        this.CornerBoard_1 = new ModelRenderer(this, 48, 3);
        this.CornerBoard_1.setRotationPoint(8.0F, 23.5F, 8.0F);
        this.CornerBoard_1.addBox(0.0F, 0.0F, -1.0F, 4, 1, 2, 0.0F);
        this.setRotateAngle(CornerBoard_1, 0.0F, -0.7853981633974483F, 0.0F);
        this.CornerBoard_3 = new ModelRenderer(this, 0, 9);
        this.CornerBoard_3.setRotationPoint(-8.0F, 23.5F, -8.0F);
        this.CornerBoard_3.addBox(0.0F, 0.0F, -1.0F, 4, 1, 2, 0.0F);
        this.setRotateAngle(CornerBoard_3, 0.0F, 2.356194490192345F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.Pillar.offsetX, this.Pillar.offsetY, this.Pillar.offsetZ);
        GlStateManager.translate(this.Pillar.rotationPointX * f5, this.Pillar.rotationPointY * f5, this.Pillar.rotationPointZ * f5);
        GlStateManager.scale(1.0D, 4.0D, 1.0D);
        GlStateManager.translate(-this.Pillar.offsetX, -this.Pillar.offsetY, -this.Pillar.offsetZ);
        GlStateManager.translate(-this.Pillar.rotationPointX * f5, -this.Pillar.rotationPointY * f5, -this.Pillar.rotationPointZ * f5);
        this.Pillar.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.CornerBoard.offsetX, this.CornerBoard.offsetY, this.CornerBoard.offsetZ);
        GlStateManager.translate(this.CornerBoard.rotationPointX * f5, this.CornerBoard.rotationPointY * f5, this.CornerBoard.rotationPointZ * f5);
        GlStateManager.scale(4.0D, 1.0D, 4.0D);
        GlStateManager.translate(-this.CornerBoard.offsetX, -this.CornerBoard.offsetY, -this.CornerBoard.offsetZ);
        GlStateManager.translate(-this.CornerBoard.rotationPointX * f5, -this.CornerBoard.rotationPointY * f5, -this.CornerBoard.rotationPointZ * f5);
        this.CornerBoard.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.Target.offsetX, this.Target.offsetY, this.Target.offsetZ);
        GlStateManager.translate(this.Target.rotationPointX * f5, this.Target.rotationPointY * f5, this.Target.rotationPointZ * f5);
        GlStateManager.scale(5.0D, 5.0D, 5.0D);
        GlStateManager.translate(-this.Target.offsetX, -this.Target.offsetY, -this.Target.offsetZ);
        GlStateManager.translate(-this.Target.rotationPointX * f5, -this.Target.rotationPointY * f5, -this.Target.rotationPointZ * f5);
        this.Target.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.CornerBoard_2.offsetX, this.CornerBoard_2.offsetY, this.CornerBoard_2.offsetZ);
        GlStateManager.translate(this.CornerBoard_2.rotationPointX * f5, this.CornerBoard_2.rotationPointY * f5, this.CornerBoard_2.rotationPointZ * f5);
        GlStateManager.scale(4.0D, 1.0D, 4.0D);
        GlStateManager.translate(-this.CornerBoard_2.offsetX, -this.CornerBoard_2.offsetY, -this.CornerBoard_2.offsetZ);
        GlStateManager.translate(-this.CornerBoard_2.rotationPointX * f5, -this.CornerBoard_2.rotationPointY * f5, -this.CornerBoard_2.rotationPointZ * f5);
        this.CornerBoard_2.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.Floor.offsetX, this.Floor.offsetY, this.Floor.offsetZ);
        GlStateManager.translate(this.Floor.rotationPointX * f5, this.Floor.rotationPointY * f5, this.Floor.rotationPointZ * f5);
        GlStateManager.scale(2.5D, 1.0D, 2.5D);
        GlStateManager.translate(-this.Floor.offsetX, -this.Floor.offsetY, -this.Floor.offsetZ);
        GlStateManager.translate(-this.Floor.rotationPointX * f5, -this.Floor.rotationPointY * f5, -this.Floor.rotationPointZ * f5);
        this.Floor.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.CornerBoard_1.offsetX, this.CornerBoard_1.offsetY, this.CornerBoard_1.offsetZ);
        GlStateManager.translate(this.CornerBoard_1.rotationPointX * f5, this.CornerBoard_1.rotationPointY * f5, this.CornerBoard_1.rotationPointZ * f5);
        GlStateManager.scale(4.0D, 1.0D, 4.0D);
        GlStateManager.translate(-this.CornerBoard_1.offsetX, -this.CornerBoard_1.offsetY, -this.CornerBoard_1.offsetZ);
        GlStateManager.translate(-this.CornerBoard_1.rotationPointX * f5, -this.CornerBoard_1.rotationPointY * f5, -this.CornerBoard_1.rotationPointZ * f5);
        this.CornerBoard_1.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.CornerBoard_3.offsetX, this.CornerBoard_3.offsetY, this.CornerBoard_3.offsetZ);
        GlStateManager.translate(this.CornerBoard_3.rotationPointX * f5, this.CornerBoard_3.rotationPointY * f5, this.CornerBoard_3.rotationPointZ * f5);
        GlStateManager.scale(4.0D, 1.0D, 4.0D);
        GlStateManager.translate(-this.CornerBoard_3.offsetX, -this.CornerBoard_3.offsetY, -this.CornerBoard_3.offsetZ);
        GlStateManager.translate(-this.CornerBoard_3.rotationPointX * f5, -this.CornerBoard_3.rotationPointY * f5, -this.CornerBoard_3.rotationPointZ * f5);
        this.CornerBoard_3.render(f5);
        GlStateManager.popMatrix();
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    int timeToReady = TICK_PER_SECOND;
    float maxAngle = 3.14159f/2f;

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        this.Target.rotateAngleY = ageInTicks  * 5f;
        if (entityIn instanceof EntityIdlBuildingBase)
        {
            EntityIdlBuildingBase buildingBase = (EntityIdlBuildingBase) entityIn;
            Vec3d vec = buildingBase.getCurBuildingVector().rotateYaw(entityIn.rotationYaw);
            float offset = 0f;
            this.Target.offsetX = (float) (vec.x + offset);
            this.Target.offsetY = (float) (-vec.y + offset);
            this.Target.offsetZ = (float) (vec.z + offset);

//            if (ageInTicks <= timeToReady) {
//                CornerBoard.rotateAngleZ   = -maxAngle * (ageInTicks / timeToReady);
//                CornerBoard_1.rotateAngleZ = maxAngle * (ageInTicks / timeToReady);
//                CornerBoard_2.rotateAngleZ = -maxAngle * (ageInTicks / timeToReady);
//                CornerBoard_3.rotateAngleZ = maxAngle * (ageInTicks / timeToReady);
//
//                CornerBoard.rotateAngleX   = -maxAngle * (ageInTicks / timeToReady);
//                CornerBoard_1.rotateAngleX = maxAngle * (ageInTicks / timeToReady);
//                CornerBoard_2.rotateAngleX = -maxAngle * (ageInTicks / timeToReady);
//                CornerBoard_3.rotateAngleX = maxAngle * (ageInTicks / timeToReady);
//            }
//            else {
//                CornerBoard.rotateAngleZ   = maxAngle;
//                CornerBoard_1.rotateAngleZ = maxAngle;
//                CornerBoard_2.rotateAngleZ = maxAngle;
//                CornerBoard_3.rotateAngleZ = maxAngle;
//                CornerBoard.rotateAngleX   = maxAngle;
//                CornerBoard_1.rotateAngleX = maxAngle;
//                CornerBoard_2.rotateAngleX = maxAngle;
//                CornerBoard_3.rotateAngleX = maxAngle;
//            }
        }
        //this.Target.offsetX =
    }
}
