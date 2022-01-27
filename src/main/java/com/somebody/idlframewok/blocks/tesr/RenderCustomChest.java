package com.somebody.idlframewok.blocks.tesr;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.tileEntity.TileEntityChestCustom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCustomChest extends TileEntitySpecialRenderer<TileEntityChestCustom> {
    static final ResourceLocation TEXTURE = new ResourceLocation(Idealland.MODID, "textures/blocks/chest_custom.png");
    final ModelChestCustom MODEL = new ModelChestCustom();

    float prevAngle = 0f;
    float lidAngle = 0f;

    @Override
    public void render(TileEntityChestCustom tileEntityChestCustom, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        int rotationType = 0;

        //if you render this from inventory, te is null
        if (tileEntityChestCustom != null)
        {
            prevAngle = tileEntityChestCustom.prevLidAngle;
            lidAngle = tileEntityChestCustom.lidAngle;

            if (tileEntityChestCustom.hasWorld())
            {
                Block block = tileEntityChestCustom.getBlockType();
                rotationType = tileEntityChestCustom.getBlockMetadata();

                if (block instanceof BlockChest && rotationType == 0)
                {
                    rotationType = tileEntityChestCustom.getBlockMetadata();
                }
            }
            else
            {
                rotationType = 0;
            }
        }

        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);

        ModelChestCustom model = MODEL;

        model.handle.isHidden = true;

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else this.bindTexture(TEXTURE);

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        int j = 0;

        if (rotationType == 2)
        {
            j = 180;
        }

        if (rotationType == 3)
        {
            j = 0;
        }

        if (rotationType == 4)
        {
            j = 90;
        }

        if (rotationType == 5)
        {
            j = -90;
        }
        GlStateManager.rotate((float)j + 180f, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);

        float f = prevAngle + (lidAngle - prevAngle) * partialTicks;
        f = 1.0F - f;
        f = 1.0F - f * f * f;
        model.lid.rotateAngleY = -(f * ((float)Math.PI / 2F));
        //model.lid. -= f * 0.3f;
        model.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
