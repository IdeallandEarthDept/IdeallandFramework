package com.somebody.idlframewok.entity.render.model;// Made with Blockbench 4.0.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDemonGodPillar extends ModelBase {
    private final ModelRenderer base;
    private final ModelRenderer ground;
    private final ModelRenderer main;
    private final ModelRenderer segment1;
    private final ModelRenderer side1;
    private final ModelRenderer skin;
    private final ModelRenderer Main_r1;
    private final ModelRenderer eye_base;
    private final ModelRenderer side2;
    private final ModelRenderer skin2;
    private final ModelRenderer Main_r2;
    private final ModelRenderer eye_base2;
    private final ModelRenderer side3;
    private final ModelRenderer skin3;
    private final ModelRenderer Main_r3;
    private final ModelRenderer eye_base3;
    private final ModelRenderer side4;
    private final ModelRenderer skin4;
    private final ModelRenderer Main_r4;
    private final ModelRenderer eye_base4;

    static float maxEyeRotateY = (float) Math.PI;
    static float maxRotationSkin = 15f * CommonDef.DEG_TO_RAD;
    static float maxEyeMovement = 8 / 16f;
    static float deg_90 = -(float) (Math.PI / 2f);
    static float deg_180 = (float) Math.PI;
    static float deg_270 = -deg_90;

    public void setGroundVisible(boolean value) {
        ground.isHidden = !value;
    }

    public ModelDemonGodPillar() {
        textureWidth = 64;
        textureHeight = 64;

        base = new ModelRenderer(this);
        base.setRotationPoint(0.0F, 24.0F, 0.0F);


        ground = new ModelRenderer(this);
        ground.setRotationPoint(0.0F, 0.0F, 0.0F);
        base.addChild(ground);
        ground.cubeList.add(new ModelBox(ground, 0, 38, -9.0F, -3.0F, -9.0F, 18, 8, 18, 0.0F, false));

        main = new ModelRenderer(this);
        main.setRotationPoint(0.0F, 0.0F, 0.0F);
        base.addChild(main);


        segment1 = new ModelRenderer(this);
        segment1.setRotationPoint(0.0F, 0.0F, 0.0F);
        main.addChild(segment1);
        segment1.cubeList.add(new ModelBox(segment1, 24, 38, -5.0F, -19.0F, -5.0F, 10, 16, 10, 0.0F, false));

        side1 = new ModelRenderer(this);
        side1.setRotationPoint(0.0F, 0.0F, 0.0F);
        segment1.addChild(side1);


        skin = new ModelRenderer(this);
        skin.setRotationPoint(-3.0F, -3.0F, -9.0F);
        side1.addChild(skin);


        Main_r1 = new ModelRenderer(this);
        Main_r1.setRotationPoint(-1.5858F, -9.0F, 4.1005F);
        skin.addChild(Main_r1);
        setRotationAngle(Main_r1, 0.0F, -0.3054F, 0.0F);
        Main_r1.cubeList.add(new ModelBox(Main_r1, 0, 0, -1.6273F, -9.0F, -2.4375F, 2, 18, 12, 0.0F, false));

        eye_base = new ModelRenderer(this);
        eye_base.setRotationPoint(0.0F, 0.0F, 3.0F);
        side1.addChild(eye_base);
        eye_base.cubeList.add(new ModelBox(eye_base, 0, 0, -6.0F, -6.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base.cubeList.add(new ModelBox(eye_base, 0, 0, -6.0F, -18.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base.cubeList.add(new ModelBox(eye_base, 0, 0, -6.0F, -14.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base.cubeList.add(new ModelBox(eye_base, 0, 0, -6.0F, -10.0F, -1.5F, 3, 3, 3, 0.0F, false));

        side2 = new ModelRenderer(this);
        side2.setRotationPoint(0.0F, 0.0F, 0.0F);
        segment1.addChild(side2);
        setRotationAngle(side2, 0.0F, -1.5708F, 0.0F);


        skin2 = new ModelRenderer(this);
        skin2.setRotationPoint(-3.0F, -3.0F, -9.0F);
        side2.addChild(skin2);


        Main_r2 = new ModelRenderer(this);
        Main_r2.setRotationPoint(-1.5858F, -9.0F, 4.1005F);
        skin2.addChild(Main_r2);
        setRotationAngle(Main_r2, 0.0F, -0.3054F, 0.0F);
        Main_r2.cubeList.add(new ModelBox(Main_r2, 0, 0, -1.6273F, -9.0F, -2.4375F, 2, 18, 12, 0.0F, false));

        eye_base2 = new ModelRenderer(this);
        eye_base2.setRotationPoint(0.0F, 0.0F, 3.0F);
        side2.addChild(eye_base2);
        eye_base2.cubeList.add(new ModelBox(eye_base2, 0, 0, -6.0F, -6.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base2.cubeList.add(new ModelBox(eye_base2, 0, 0, -6.0F, -18.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base2.cubeList.add(new ModelBox(eye_base2, 0, 0, -6.0F, -14.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base2.cubeList.add(new ModelBox(eye_base2, 0, 0, -6.0F, -10.0F, -1.5F, 3, 3, 3, 0.0F, false));

        side3 = new ModelRenderer(this);
        side3.setRotationPoint(0.0F, 0.0F, 0.0F);
        segment1.addChild(side3);
        setRotationAngle(side3, 0.0F, 3.1416F, 0.0F);


        skin3 = new ModelRenderer(this);
        skin3.setRotationPoint(-3.0F, -3.0F, -9.0F);
        side3.addChild(skin3);


        Main_r3 = new ModelRenderer(this);
        Main_r3.setRotationPoint(-1.5858F, -9.0F, 4.1005F);
        skin3.addChild(Main_r3);
        setRotationAngle(Main_r3, 0.0F, -0.3054F, 0.0F);
        Main_r3.cubeList.add(new ModelBox(Main_r3, 0, 0, -1.6273F, -9.0F, -2.4375F, 2, 18, 12, 0.0F, false));

        eye_base3 = new ModelRenderer(this);
        eye_base3.setRotationPoint(0.0F, 0.0F, 3.0F);
        side3.addChild(eye_base3);
        eye_base3.cubeList.add(new ModelBox(eye_base3, 0, 0, -6.0F, -6.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base3.cubeList.add(new ModelBox(eye_base3, 0, 0, -6.0F, -18.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base3.cubeList.add(new ModelBox(eye_base3, 0, 0, -6.0F, -14.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base3.cubeList.add(new ModelBox(eye_base3, 0, 0, -6.0F, -10.0F, -1.5F, 3, 3, 3, 0.0F, false));

        side4 = new ModelRenderer(this);
        side4.setRotationPoint(0.0F, 0.0F, 0.0F);
        segment1.addChild(side4);
        setRotationAngle(side4, 0.0F, 1.5708F, 0.0F);


        skin4 = new ModelRenderer(this);
        skin4.setRotationPoint(-3.0F, -3.0F, -9.0F);
        side4.addChild(skin4);


        Main_r4 = new ModelRenderer(this);
        Main_r4.setRotationPoint(-1.5858F, -9.0F, 4.1005F);
        skin4.addChild(Main_r4);
        setRotationAngle(Main_r4, 0.0F, -0.3054F, 0.0F);
        Main_r4.cubeList.add(new ModelBox(Main_r4, 0, 0, -1.6273F, -9.0F, -2.4375F, 2, 18, 12, 0.0F, false));

        eye_base4 = new ModelRenderer(this);
        eye_base4.setRotationPoint(0.0F, 0.0F, 3.0F);
        side4.addChild(eye_base4);
        eye_base4.cubeList.add(new ModelBox(eye_base4, 0, 0, -6.0F, -6.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base4.cubeList.add(new ModelBox(eye_base4, 0, 0, -6.0F, -18.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base4.cubeList.add(new ModelBox(eye_base4, 0, 0, -6.0F, -14.0F, -1.5F, 3, 3, 3, 0.0F, false));
        eye_base4.cubeList.add(new ModelBox(eye_base4, 0, 0, -6.0F, -10.0F, -1.5F, 3, 3, 3, 0.0F, false));
    }

    public void setEyeOpenState(float value) {
        value = CommonFunctions.clamp(value, 0f, 1f);
        float skinRotationInDeg = -maxRotationSkin * value;

        float firstEyeBaseFactor = ModConfig.DEBUG_CONF.FLOAT_1;

        skin.rotateAngleY = -maxRotationSkin * ((1 - firstEyeBaseFactor) * value + firstEyeBaseFactor);
        skin2.rotateAngleY = skinRotationInDeg;// + deg_90;
        skin3.rotateAngleY = skinRotationInDeg;// + deg_180;
        skin4.rotateAngleY = skinRotationInDeg;// + deg_270;

        float eyeMovement = -maxEyeMovement * value;
        eye_base.offsetX = eyeMovement;
        eye_base2.offsetX = eyeMovement;
        eye_base3.offsetX = eyeMovement;
        eye_base4.offsetX = eyeMovement;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setEyeOpenState((float) Math.sin(ageInTicks / 3f));
//		eye_base.rotateAngleY = (float) (maxEyeRotateY * Math.sin(ageInTicks));
//		eye_base2.rotateAngleY = (float) (maxEyeRotateY * Math.sin(ageInTicks+1));
//		eye_base3.rotateAngleY = (float) (maxEyeRotateY * Math.sin(ageInTicks+2));
//		eye_base4.rotateAngleY = (float) (maxEyeRotateY * Math.sin(ageInTicks+3));
        base.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}