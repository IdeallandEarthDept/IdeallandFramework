package com.somebody.idlframewok.entity.projectiles.render;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.entity.projectiles.render.model.ModelBladeBullet;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.nio.FloatBuffer;

public class RenderBladeBullet<T extends Entity> extends Render<T> {
    public static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Reference.MOD_ID + ":textures/entity/projectiles/idl_blade_bullet.png");

    protected boolean renderMarker;
    private static final DynamicTexture TEXTURE_BRIGHTNESS = new DynamicTexture(16, 16);
    protected FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
    protected ModelBase mainModel;

    public RenderBladeBullet(RenderManager manager){
        super(manager);
        mainModel =  new ModelBladeBullet();
    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        boolean flag = this.isVisible(entitylivingbaseIn);
        boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player);

        if (flag || flag1)
        {
            if (!this.bindEntityTexture(entitylivingbaseIn))
            {
                return;
            }

            if (flag1)
            {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }

            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

            if (flag1)
            {
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
        }
    }

    protected boolean isVisible(T p_193115_1_)
    {
        return !p_193115_1_.isInvisible() || this.renderOutlines;
    }

    /**
     * Returns a rotationIn angle that is inbetween two other rotationIn angles. par1 and par2 are the angles between which
     * to interpolate, par3 is probably a float between 0.0 and 1.0 that tells us where "between" the two angles we are.
     * Example: par1 = 30, par2 = 50, par3 = 0.5, then return = 40
     */
    protected float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks)
    {
        float f;

        for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F)
        {
            ;
        }

        while (f >= 180.0F)
        {
            f -= 360.0F;
        }

        return prevYawOffset + partialTicks * f;
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt(T entityLivingBaseIn, double x, double y, double z)
    {
        GlStateManager.translate((float)x, (float)y, (float)z);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(T livingBase, float partialTicks)
    {
        return (float)livingBase.ticksExisted + partialTicks;
    }

    protected void applyRotations(T entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
    }

    public float prepareScale(T entitylivingbaseIn, float partialTicks)
    {
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        //this.preRenderCallback(entitylivingbaseIn, partialTicks);
        float f = 0.0625F;
        GlStateManager.translate(0.0F, -1.501F, 0.0F);
        return 0.0625F;
    }

    protected boolean setDoRenderBrightness(T entityLivingBaseIn, float partialTicks)
    {
        return this.setBrightness(entityLivingBaseIn, partialTicks, true);
    }

    static
    {
        int[] aint = TEXTURE_BRIGHTNESS.getTextureData();

        for (int i = 0; i < 256; ++i)
        {
            aint[i] = -1;
        }

        TEXTURE_BRIGHTNESS.updateDynamicTexture();
    }

    /**
     * Gets an RGBA int color multiplier to apply.
     */
    protected int getColorMultiplier(T entitylivingbaseIn, float lightBrightness, float partialTickTime)
    {
        return 0;
    }


    protected boolean setBrightness(T entitylivingbaseIn, float partialTicks, boolean combineTextures)
    {
        float f = entitylivingbaseIn.getBrightness();
        int i = this.getColorMultiplier(entitylivingbaseIn, f, partialTicks);
        boolean flag = (i >> 24 & 255) > 0;
        boolean flag1 = false; //entitylivingbaseIn.hurtTime > 0 || entitylivingbaseIn.deathTime > 0;

        if (!flag && !flag1)
        {
            return false;
        }
        else if (!flag && !combineTextures)
        {
            return false;
        }
        else
        {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            this.brightnessBuffer.position(0);

            if (flag1)
            {
                this.brightnessBuffer.put(1.0F);
                this.brightnessBuffer.put(0.0F);
                this.brightnessBuffer.put(0.0F);
                this.brightnessBuffer.put(0.3F);
            }
            else
            {
                float f1 = (float)(i >> 24 & 255) / 255.0F;
                float f2 = (float)(i >> 16 & 255) / 255.0F;
                float f3 = (float)(i >> 8 & 255) / 255.0F;
                float f4 = (float)(i & 255) / 255.0F;
                this.brightnessBuffer.put(f2);
                this.brightnessBuffer.put(f3);
                this.brightnessBuffer.put(f4);
                this.brightnessBuffer.put(1.0F - f1);
            }

            this.brightnessBuffer.flip();
            GlStateManager.glTexEnv(8960, 8705, this.brightnessBuffer);
            GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(TEXTURE_BRIGHTNESS.getGlTextureId());
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            return true;
        }
    }

    protected void unsetBrightness()
    {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.disableTexture2D();
        GlStateManager.bindTexture(0);
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }


    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
       // if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<T>(entity, this, partialTicks, x, y, z))) return;
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        try
        {
            //float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            //float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float pitch = entity.rotationPitch;//entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            this.renderLivingAt(entity, x, y, z);
            float f8 = this.handleRotationFloat(entity, partialTicks);
            //float f8 = this.handleRotationFloat(entity, partialTicks);
            this.applyRotations(entity, f8, entityYaw, partialTicks);
            float f4 = this.prepareScale(entity, partialTicks);
            float f5 = 0.0F;
            float f6 = 0.0F;

            GlStateManager.enableAlpha();
            //this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
            this.mainModel.setRotationAngles(f6, f5, f8, entityYaw, pitch, f4, entity);

            if (this.renderOutlines)
            {
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entity));

                if (!this.renderMarker)
                {
                    //Idealland.Log("B: yaw = %f", entityYaw);
                    this.renderModel(entity, f6, f5, f8, entityYaw, pitch, f4);
                }
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }
            else
            {
                boolean flag = this.setDoRenderBrightness(entity, partialTicks);
                //Idealland.Log("A: yaw = %f", entityYaw);
                this.renderModel(entity, f6, f5, f8, entityYaw, pitch, f4);

                if (flag)
                {
                    this.unsetBrightness();
                }

                GlStateManager.depthMask(true);

//                if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator())
//                {
//                    this.renderLayers(entity, f6, f5, partialTicks, f8, f2, pitch, f4);
//                }
            }

            GlStateManager.disableRescaleNormal();
        }
        catch (Exception exception)
        {
            Idealland.LogWarning("Couldn't render entity", (Throwable)exception);
        }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        //net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<T>(entity, this, partialTicks, x, y, z));
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return TEXTURE_DEFAULT;
    }

//    protected void renderLayers(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn)
//    {
//        for (LayerRenderer<T> layerrenderer : this.layerRenderers)
//        {
//            boolean flag = this.setBrightness(entitylivingbaseIn, partialTicks, layerrenderer.shouldCombineTextures());
//            layerrenderer.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleIn);
//
//            if (flag)
//            {
//                this.unsetBrightness();
//            }
//        }
//    }
}
