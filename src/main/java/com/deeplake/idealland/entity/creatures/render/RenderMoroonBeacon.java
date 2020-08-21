package com.deeplake.idealland.entity.creatures.render;

import com.deeplake.idealland.entity.creatures.misc.EntityTurretPrototype;
import com.deeplake.idealland.entity.creatures.model.ModelMoroonBeacon;
import com.deeplake.idealland.entity.creatures.model.ModelTurretPrototype;
import com.deeplake.idealland.entity.creatures.moroon.EntityMoroonBombBeacon;
import com.deeplake.idealland.util.Reference;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderMoroonBeacon extends RenderLiving<EntityMoroonBombBeacon> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/entity/moroon_beacon.png");
    public RenderMoroonBeacon(RenderManager manager){
        super(manager, new ModelMoroonBeacon(), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMoroonBombBeacon entity) {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(EntityMoroonBombBeacon entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);

    }
}
