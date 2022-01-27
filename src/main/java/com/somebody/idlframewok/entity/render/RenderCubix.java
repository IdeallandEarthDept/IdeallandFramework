package com.somebody.idlframewok.entity.render;

import com.somebody.idlframewok.entity.creatures.cubix.EntityCubix;
import com.somebody.idlframewok.entity.render.model.ModelCubix1;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderCubix extends RenderLiving<EntityCubix> {
    public static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(Reference.MOD_ID + ":textures/entity/cubix_1_texturemap.png");
    public static ResourceLocation texture;

    public RenderCubix(RenderManager manager){
        super(manager, new ModelCubix1(), 0.5F);
        texture = TEXTURE_DEFAULT;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityCubix entity) {
        return texture;
    }

    @Override
    protected void applyRotations(EntityCubix entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);

    }
}
