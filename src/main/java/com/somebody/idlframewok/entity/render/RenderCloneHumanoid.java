package com.somebody.idlframewok.entity.render;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.entity.creatures.mobs.IPlayerDoppleganger;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

@SideOnly(Side.CLIENT)
public class RenderCloneHumanoid extends RenderBiped<EntityModUnit> {
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation(  ":textures/entity/steve.png");

    public RenderCloneHumanoid(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelPlayer(0f, false), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this);
        this.addLayer(layerbipedarmor);
    }

    public RenderCloneHumanoid(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
        super(renderManagerIn, modelBipedIn, shadowSize);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityModUnit entity)
    {
        ResourceLocation resourceLocation = getLocationSkin(entity);

        return resourceLocation == null ? DEFAULT_RES_LOC : resourceLocation;
    }

    public ResourceLocation getLocationSkin(EntityModUnit entity)
    {
        ResourceLocation resourceLocation = null;
        EntityPlayerSP playerSP = null;
        UUID uuid = null;
        if (entity instanceof IPlayerDoppleganger)
        {
            IPlayerDoppleganger doppleganger = (IPlayerDoppleganger) entity;
            playerSP = (EntityPlayerSP)(doppleganger.getPlayer());
            if (playerSP == null)
            {
                uuid = doppleganger.getPlayerUUID();
            }
        }
        if (playerSP != null)
        {
            if (playerSP.isPlayerInfoSet())
            {
                resourceLocation = playerSP.getLocationSkin();
            }
        }
        else if (uuid != null){
            resourceLocation = DefaultPlayerSkin.getDefaultSkin(uuid);
        }
        return resourceLocation;
    }
}
