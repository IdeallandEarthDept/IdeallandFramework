package com.somebody.idlframewok.debug;

import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class TestAOA3 {

        @SideOnly(Side.CLIENT)
        @SubscribeEvent
        public static void renderIcons(final RenderLivingEvent.Specials.Pre ev) {
//            EntityLivingBase entity = ev.getEntity();
//
//            if (entity.posX == 0 && entity.posY == 0 && entity.posZ == 0)
//                return;
//
//            int size = 1;
//            float yOffset = 0;
//            float xOffset = Math.max(-5, -size) * 0.45f / 2.0f + 0.2f;
//            float rowMax = 0.875f;
//
//
//            renderIcon(new ResourceLocation(Idealland.MODID, "textures/block/builder_farm.png"), xOffset, yOffset, ev);
//
//            xOffset += 0.45f;
//
//            if (xOffset > rowMax) {
//                yOffset += 0.45f;
//                xOffset = Math.max(-5, -size) * 0.45f / 2.0f + 0.2f;
//            }

        }

        private static void renderIcon(ResourceLocation texture, float xOffset, float yOffset, RenderLivingEvent.Specials.Pre event, String... msg) {
            GlStateManager.pushMatrix();
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            Tessellator tess = Tessellator.getInstance();
            BufferBuilder buffer = tess.getBuffer();
            Minecraft mc = Minecraft.getMinecraft();

            GlStateManager.translate(event.getX(), 0.2d + event.getY() + event.getEntity().getEntityBoundingBox().maxY - event.getEntity().getEntityBoundingBox().minY, event.getZ());
            GlStateManager.rotate(180f - mc.getRenderManager().playerViewY, 0, 1, 0);
            GlStateManager.rotate(-mc.getRenderManager().playerViewX, 1, 0, 0);
            GlStateManager.translate(xOffset, yOffset, 0);
            GlStateManager.scale(0.45f, 0.45f, 0.45f);
            GL11.glDisable(2896);
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
            buffer.pos(-0.5d, -0.25d, 0).tex(0, 1).normal(0.0f, 1.0f, 0.0f).endVertex();
            buffer.pos(0.5d, -0.25d, 0).tex(1, 1).normal(0.0f, 1.0f, 0.0f).endVertex();
            buffer.pos(0.5d, 0.75d, 0).tex(1, 0).normal(0.0f, 1.0f, 0.0f).endVertex();
            buffer.pos(-0.5d, 0.75d, 0).tex(0, 0).normal(0.0f, 1.0f, 0.0f).endVertex();
            tess.draw();
            GlStateManager.popMatrix();
        }

}
