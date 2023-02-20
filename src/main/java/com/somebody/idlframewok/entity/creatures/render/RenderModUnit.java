package com.somebody.idlframewok.entity.creatures.render;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderModUnit extends RenderBiped<EntityModUnit> {
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation("textures/entity/steve.png");

    public RenderModUnit(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelZombie(), 0.5F);
    }


    public RenderModUnit(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
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
