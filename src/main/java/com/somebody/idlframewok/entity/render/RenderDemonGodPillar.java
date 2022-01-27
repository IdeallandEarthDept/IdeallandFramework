package com.somebody.idlframewok.entity.render;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.entity.render.model.ModelDemonGodPillar;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderDemonGodPillar extends RenderLiving<EntityModUnit> {
    public static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Reference.MOD_ID + ":textures/entity/dmgod_skin.png");
    public static ResourceLocation texture;

    static float scale_diminish_ratio = 0.9f;

    public RenderDemonGodPillar(RenderManager manager) {
        super(manager, new ModelDemonGodPillar(), 2F);
        texture = TEXTURE_DEFAULT;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityModUnit entity) {
        return texture;
    }

    @Override
    protected void applyRotations(EntityModUnit entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);

    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(EntityModUnit entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean flag = this.isVisible(entitylivingbaseIn);
        boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player);

        if (flag || flag1) {
            if (!this.bindEntityTexture(entitylivingbaseIn)) {
                return;
            }

            if (flag1) {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }

            float scale = 3f;

            GlStateManager.scale(scale, scale, scale);

            ((ModelDemonGodPillar) this.mainModel).setGroundVisible(true);
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            ((ModelDemonGodPillar) this.mainModel).setGroundVisible(false);

            for (int i = 0; i <= 7; i++) {
                GlStateManager.scale(scale_diminish_ratio, scale_diminish_ratio, scale_diminish_ratio);
                GlStateManager.translate(0f, -1f, 0f);
                GlStateManager.rotate(15f, 0f, 1f, 0f);
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            }

            if (flag1) {
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
        }
    }
}
