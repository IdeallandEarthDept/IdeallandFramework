package com.somebody.idlframewok.entity.creatures.render;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMoroonHumanoid extends RenderBiped<EntityModUnit> {
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation(Reference.MOD_ID + ":textures/entity/moroon_humanoid.png");

    public RenderMoroonHumanoid(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelPlayer(1f, false), 0.5F);
        this.addLayer(new LayerBipedArmor(this));
        //this.addLayer(new LayerHeldItem(this));
        //this.addLayer(new LayerArrow(this));
//        this.addLayer(new LayerDeadmau5Head(this));
//        this.addLayer(new LayerCape(this));
//        this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
        //this.addLayer(new LayerElytra(this));
        //this.addLayer(new LayerEntityOnShoulder(renderManager));
    }


    public RenderMoroonHumanoid(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
        super(renderManagerIn, modelBipedIn, shadowSize);
//        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
//        {
//            protected void initArmor()
//            {
//                this.modelLeggings = new ModelZombie(0.5F, true);
//                this.modelArmor = new ModelZombie(1.0F, true);
//            }
//        };
//        this.addLayer(layerbipedarmor);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityModUnit entity)
    {
        return DEFAULT_RES_LOC;
    }
}
