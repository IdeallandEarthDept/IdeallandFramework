package com.somebody.idlframewok.entity.render.model;// Made with Blockbench 4.0.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelDigDamper extends ModelBase {
    private final ModelRenderer axis;
    private final ModelRenderer axis2;
    private final ModelRenderer bb_main;

    public ModelDigDamper() {
        textureWidth = 32;
        textureHeight = 16;

        axis = new ModelRenderer(this);
        axis.setRotationPoint(0.0F, 4.0F, 0.0F);
        axis.cubeList.add(new ModelBox(axis, 0, 0, 3.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F, false));

        axis2 = new ModelRenderer(this);
        axis2.setRotationPoint(0.0F, -7.0F, 0.0F);
        axis2.cubeList.add(new ModelBox(axis2, 0, 0, -4.0F, -4.0F, 3.0F, 8, 8, 8, 0.0F, false));

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -8.0F, -16.0F, -8.0F, 16, 16, 16, 0.0F, false));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -4.0F, -47.0F, -4.0F, 8, 8, 8, 0.0F, false));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -3.0F, -43.0F, -3.0F, 6, 27, 6, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        axis.render(f5);
        axis2.render(f5);
        bb_main.render(f5);
    }

    float rotateSpeed = 0.017453292F * 10.1f;

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        axis.rotateAngleY = ageInTicks * rotateSpeed;
        axis2.rotateAngleY = -ageInTicks * rotateSpeed;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

        float f = CommonFunctions.interpolateRotation(entitylivingbaseIn.prevRenderYawOffset,
                entitylivingbaseIn.renderYawOffset,
                partialTickTime);

        //The GLState will be transformed by (180 - f) in RenderLivingBase, which means the rotationY of root is untouched.
        //This f is not passed as an argument, so I need to get partialTickTime to calculate it myself.
        bb_main.rotateAngleY = -f * CommonDef.DEG_TO_RAD;
    }
}