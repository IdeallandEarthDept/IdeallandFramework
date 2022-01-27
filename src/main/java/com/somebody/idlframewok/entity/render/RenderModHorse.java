package com.somebody.idlframewok.entity.render;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.entity.creatures.misc.EntityGhostHorse;
import com.google.common.collect.Maps;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class RenderModHorse extends RenderLiving<AbstractHorse>
{
    private static final Map< Class<?>, ResourceLocation> MAP = Maps. < Class<?>, ResourceLocation > newHashMap();
    private final float scale;

    public RenderModHorse(RenderManager p_i47212_1_)
    {
        this(p_i47212_1_, 1.0F);
    }

    public RenderModHorse(RenderManager p_i47213_1_, float p_i47213_2_)
    {
        super(p_i47213_1_, new ModelHorse(), 0.75F);
        this.scale = p_i47213_2_;
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(AbstractHorse entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(this.scale, this.scale, this.scale);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(AbstractHorse entity)
    {
        return MAP.get(entity.getClass());
    }

    static
    {
        MAP.put(EntityGhostHorse.class, new ResourceLocation(Idealland.MODID,"textures/entity/ghost_horse.png"));
    }
}
