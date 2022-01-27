package com.somebody.idlframewok.blocks.tesr;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created using Tabula 7.1.0
 */
@SideOnly(Side.CLIENT)
public class ModelChestCustom extends ModelBase {
    public ModelRenderer handle;
    public ModelRenderer lid;
    public ModelRenderer bottom;

    public ModelChestCustom() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.handle = new ModelRenderer(this, 0, 0);
        this.handle.setRotationPoint(8.0F, 7.0F, 15.0F);
        this.handle.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
        this.lid = new ModelRenderer(this, 0, 0);
        this.lid.setRotationPoint(1.0F, 7.0F, 15.0F);
        this.lid.addBox(0.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F);
        this.bottom = new ModelRenderer(this, 0, 19);
        this.bottom.setRotationPoint(1.0F, 6.0F, 1.0F);
        this.bottom.addBox(0.0F, 0.0F, 0.0F, 14, 10, 14, 0.0F);
    }

    public void renderAll()
    {
        handle.rotateAngleX = lid.rotateAngleX;
        lid.render(0.0625f);
        handle.render(0.0625f);
        bottom.render(0.0625f);
    }
}
