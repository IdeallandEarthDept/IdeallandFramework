package com.somebody.idlframewok.entity.projectiles.render.model;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBladeBulletA extends ModelBase {
	private final ModelRenderer base;
	private final ModelRenderer bone;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer bone2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;

	public ModelBladeBulletA() {
		textureWidth = 16;
		textureHeight = 16;

		base = new ModelRenderer(this);
		base.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(base, 0.0F, 3.1416F, 0.0F);
		base.cubeList.add(new ModelBox(base, 0, 0, 2.0F, -0.5F, 2.0F, 1, 1, 2, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -3.0F, -0.5F, 2.0F, 1, 1, 2, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -4.0F, -0.5F, 2.0F, 1, 1, 3, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, 3.0F, -0.5F, 2.0F, 1, 1, 3, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, -5.0F, -0.5F, 2.0F, 1, 1, 4, 0.0F, false));
		base.cubeList.add(new ModelBox(base, 0, 0, 4.0F, -0.5F, 2.0F, 1, 1, 4, 0.0F, false));

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		base.addChild(bone);
		setRotationAngle(bone, 0.0F, -0.3491F, 0.0F);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-1.0F, 0.65F, 1.1607F);
		bone.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.3491F, 0.0F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 0, 0.9397F, -0.7552F, -1.3214F, 6, 1, 2, 0.0F, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(1.0F, 0.0F, 1.1107F);
		bone.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.3491F, 0.0F, 0.0F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 0, -0.9397F, -0.7552F, -0.6786F, 6, 1, 2, 0.0F, false));

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(-5.5F, 0.0F, 2.0F);
		base.addChild(bone2);
		setRotationAngle(bone2, 0.0F, 0.3491F, 0.0F);
		

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-1.0F, 0.65F, 1.1607F);
		bone2.addChild(cube_r3);
		setRotationAngle(cube_r3, -0.3491F, 0.0F, 0.0F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 0, 0, 0.9397F, -0.7552F, -1.3214F, 6, 1, 2, 0.0F, false));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(1.0F, 0.0F, 1.1107F);
		bone2.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.3491F, 0.0F, 0.0F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 0, 0, -0.9397F, -0.7552F, -0.6786F, 6, 1, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale){
		//base.render(scale);
		base.renderWithRotation(scale);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}