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

public class ModelCitadelTurret extends ModelBase {
    private final ModelRenderer root;
    private final ModelRenderer head;
    private final ModelRenderer barrel_base;
    private final ModelRenderer barrel_R;
    private final ModelRenderer barrel_L;
    private final ModelRenderer body;
    private final ModelRenderer basement;

    public ModelCitadelTurret() {
        textureWidth = 256;
        textureHeight = 256;

        root = new ModelRenderer(this);
        root.setRotationPoint(0.0F, 24.0F, 0.0F);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(head);
        head.cubeList.add(new ModelBox(head, 9, 0, -6.0F, -36.0F, -13.0F, 11, 8, 26, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 0, 13, -6.0F, -39.0F, 4.0F, 11, 3, 6, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 57, 0, -6.0F, -39.0F, -9.0F, 11, 3, 6, 0.0F, false));

        barrel_base = new ModelRenderer(this);
        barrel_base.setRotationPoint(-5.5F, -37.5F, 0.5F);
        head.addChild(barrel_base);
        barrel_base.cubeList.add(new ModelBox(barrel_base, 0, 4, 0.5F, -1.5F, -3.0F, 11, 3, 6, 0.0F, false));

        barrel_R = new ModelRenderer(this);
        barrel_R.setRotationPoint(-1.5F, 0.5F, 1.5F);
        barrel_base.addChild(barrel_R);
        barrel_R.cubeList.add(new ModelBox(barrel_R, 0, 0, -7.0F, -1.0F, -1.0F, 14, 2, 2, 0.0F, false));

        barrel_L = new ModelRenderer(this);
        barrel_L.setRotationPoint(-1.5F, 0.5F, -1.5F);
        barrel_base.addChild(barrel_L);
        barrel_L.cubeList.add(new ModelBox(barrel_L, 0, 0, -7.0F, -1.0F, -1.0F, 14, 2, 2, 0.0F, false));

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(body);
        body.cubeList.add(new ModelBox(body, 0, 34, -7.0F, -28.0F, -7.0F, 13, 14, 14, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 76, 21, 6.0F, -25.0F, -6.0F, 6, 13, 12, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 83, 0, 12.0F, -23.0F, -5.0F, 4, 11, 10, 0.0F, false));

        basement = new ModelRenderer(this);
        basement.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(basement);
        basement.cubeList.add(new ModelBox(basement, 0, 226, -8.0F, -14.0F, -8.0F, 16, 14, 16, 0.0F, false));
        basement.cubeList.add(new ModelBox(basement, 0, 203, -6.0F, -11.0F, 8.0F, 12, 11, 12, 0.0F, false));
        basement.cubeList.add(new ModelBox(basement, 0, 180, -6.0F, -11.0F, -20.0F, 12, 11, 12, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        root.render(scaleFactor);
    }

    static final float maxBarrelShrink = 0.2f;
    static final float barrelShrinkPeriod = (float) (2.25f / (2f * Math.PI));

    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

        float f = CommonFunctions.interpolateRotation(entitylivingbaseIn.prevRenderYawOffset,
                entitylivingbaseIn.renderYawOffset,
                partialTickTime);

        //The GLState will be transformed by (180 - f) in RenderLivingBase, which means the rotationY of root is untouched.
        //This f is not passed as an argument, so I need to get partialTickTime to calculate it myself.
        basement.rotateAngleY = -f * CommonDef.DEG_TO_RAD;
        body.rotateAngleY = basement.rotateAngleY;
    }

    //hurt = limbSwingAmount 1000~0, limbSwing 0~+2250
    //See RenderLivingBase::doRender
    //LimbSwingAmount for normal creatures is changed on EntityLivingBase.onLivingUpdate() -> travel
    //please note that the Entity::limbSwingAmount is not automatically synced from server to client.
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        head.rotateAngleY = (netHeadYaw - 90) * CommonDef.DEG_TO_RAD;
        if (swingProgress == 0) {
            barrel_R.offsetX = 0;
            barrel_L.offsetX = 0;
        } else {
            barrel_R.offsetX = (float) ((1 - Math.cos(swingProgress / barrelShrinkPeriod)) * maxBarrelShrink);
            barrel_L.offsetX = (float) ((1 - Math.sin(swingProgress / barrelShrinkPeriod)) * maxBarrelShrink);
        }

        barrel_base.rotateAngleZ = (-headPitch) * CommonDef.DEG_TO_RAD;
        //the netHeadYaw is interpolated[renderYawOffset] - interpolated[entitylivingbaseIn.rotationYawHead]
        //the two base rotationIn needs manual calculation.

        //This function does not provide partial tick argument, which is quite absurd.
        //Correlating code to pin its basement is written to setLivingAnimations to get that argument.
    }

}