package com.somebody.idlframewok.entity.render.model;// Made with Blockbench 4.0.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import static java.lang.Math.sin;

public class ModelMiddleTurret extends ModelBase {
    private final ModelRenderer head;
    private final ModelRenderer barrel_holder;
    private final ModelRenderer barrel_recoiler;
    private final ModelRenderer barrel_L;
    private final ModelRenderer barrel_L_r1;
    private final ModelRenderer barrel_R;
    private final ModelRenderer barrel_R_r1;
    private final ModelRenderer basement;

    public ModelMiddleTurret() {
        textureWidth = 128;
        textureHeight = 128;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 24.0F, 0.0F);
        head.cubeList.add(new ModelBox(head, 0, 0, -6.5F, -20.0F, -2.5F, 13, 4, 5, 0.0F, false));

        barrel_holder = new ModelRenderer(this);
        barrel_holder.setRotationPoint(0.0F, -20.5F, 1.5F);
        head.addChild(barrel_holder);


        barrel_recoiler = new ModelRenderer(this);
        barrel_recoiler.setRotationPoint(0.0F, 0.0F, 0.0F);
        barrel_holder.addChild(barrel_recoiler);
        barrel_recoiler.cubeList.add(new ModelBox(barrel_recoiler, 0, 9, -3.0F, -1.5F, -6.5F, 6, 3, 7, 0.0F, false));

        barrel_L = new ModelRenderer(this);
        barrel_L.setRotationPoint(-1.0F, 20.5F, -1.5F);
        barrel_recoiler.addChild(barrel_L);


        barrel_L_r1 = new ModelRenderer(this);
        barrel_L_r1.setRotationPoint(-0.5F, -20.9142F, 5.5F);
        barrel_L.addChild(barrel_L_r1);
        setRotationAngle(barrel_L_r1, 0.0F, 0.0F, 0.7854F);
        barrel_L_r1.cubeList.add(new ModelBox(barrel_L_r1, 0, 19, -0.5F, -0.5F, -5.5F, 1, 1, 11, 0.0F, false));

        barrel_R = new ModelRenderer(this);
        barrel_R.setRotationPoint(2.0F, 20.5F, -1.5F);
        barrel_recoiler.addChild(barrel_R);


        barrel_R_r1 = new ModelRenderer(this);
        barrel_R_r1.setRotationPoint(-0.5F, -20.9142F, 5.5F);
        barrel_R.addChild(barrel_R_r1);
        setRotationAngle(barrel_R_r1, 0.0F, 0.0F, 0.7854F);
        barrel_R_r1.cubeList.add(new ModelBox(barrel_R_r1, 0, 19, -0.5F, -0.5F, -5.5F, 1, 1, 11, 0.0F, false));

        basement = new ModelRenderer(this);
        basement.setRotationPoint(0.0F, 24.0F, 0.0F);
        basement.cubeList.add(new ModelBox(basement, 0, 115, -2.5F, -8.0F, -2.5F, 5, 8, 5, 0.0F, false));
        basement.cubeList.add(new ModelBox(basement, 20, 116, -2.0F, -16.0F, -2.0F, 4, 8, 4, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        head.render(f5);
        basement.render(f5);
    }

    static final float maxBarrelShrink = 0.1f;
    static final float barrelShrinkPeriod = (float) (2f / (2f * Math.PI));

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

        float f = CommonFunctions.interpolateRotation(entitylivingbaseIn.prevRenderYawOffset,
                entitylivingbaseIn.renderYawOffset,
                partialTickTime);

        //The GLState will be transformed by (180 - f) in RenderLivingBase, which means the rotationY of root is untouched.
        //This f is not passed as an argument, so I need to get partialTickTime to calculate it myself.
        basement.rotateAngleY = -f * CommonDef.DEG_TO_RAD;
    }

    //hurt = limbSwingAmount 1000~0, limbSwing 0~+2250
    //See RenderLivingBase::doRender
    //LimbSwingAmount for normal creatures is changed on EntityLivingBase.onLivingUpdate() -> travel
    //please note that the Entity::limbSwingAmount is not automatically synced from server to client.
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        head.rotateAngleY = (netHeadYaw + 180) * CommonDef.DEG_TO_RAD;

        barrel_holder.rotateAngleX = -headPitch * CommonDef.DEG_TO_RAD;
        barrel_recoiler.offsetZ = -(float) ((sin(swingProgress / barrelShrinkPeriod)) * maxBarrelShrink);
        //the netHeadYaw is interpolated[renderYawOffset] - interpolated[entitylivingbaseIn.rotationYawHead]
        //the two base rotationIn needs manual calculation.

        //This function does not provide partial tick argument, which is quite absurd.
        //Correlating code to pin its basement is written to setLivingAnimations to get that argument.
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}