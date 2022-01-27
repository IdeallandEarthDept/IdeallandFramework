package com.somebody.idlframewok.entity.render;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.entity.creatures.mobs.skyland.EntityCatharVex;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderModVex extends RenderBiped<EntityModUnit> {
    private ResourceLocation RES_LOC;
    private ResourceLocation RES_LOC_CHARGE;
    private static final ResourceLocation VEX_TEXTURE = new ResourceLocation("textures/entity/illager/vex.png");
    private static final ResourceLocation VEX_CHARGING_TEXTURE = new ResourceLocation("textures/entity/illager/vex_charging.png");
    private int modelVersion;

    public RenderModVex(RenderManager p_i47190_1_) {
        super(p_i47190_1_, new ModelModVex(), 0.3F);
        this.addLayer(new LayerBipedArmor(this));
        this.modelVersion = ((ModelModVex)this.mainModel).getModelVersion();
        RES_LOC = VEX_TEXTURE;
        RES_LOC_CHARGE = VEX_CHARGING_TEXTURE;
    }

    public RenderModVex(RenderManager renderManagerIn, ResourceLocation RES_LOC, ResourceLocation RES_LOC_CHARGE) {
        this(renderManagerIn);
        this.RES_LOC = RES_LOC;
        this.RES_LOC_CHARGE = RES_LOC_CHARGE;
    }

    protected ResourceLocation getEntityTexture(EntityModUnit entity) {
        if (entity instanceof EntityCatharVex)
        {
            return ((EntityCatharVex) entity).isCharging() ? RES_LOC_CHARGE : RES_LOC;
        }
        else {
            return RES_LOC;
        }
    }

    public void doRender(EntityModUnit entity, double x, double y, double z, float entityYaw, float partialTicks) {
        int i = ((ModelModVex)this.mainModel).getModelVersion();
        if (i != this.modelVersion) {
            this.mainModel = new ModelModVex();
            this.modelVersion = i;
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected void preRenderCallback(EntityModUnit entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }
}

