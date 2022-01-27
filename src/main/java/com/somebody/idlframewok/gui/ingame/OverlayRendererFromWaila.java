package com.somebody.idlframewok.gui.ingame;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

//not used. for references only
public class OverlayRendererFromWaila {
    protected static boolean hasLight;
    protected static boolean hasDepthTest;
    protected static boolean hasLight0;
    protected static boolean hasLight1;
    protected static boolean hasRescaleNormal;
    protected static boolean hasColorMaterial;
    protected static boolean depthMask;
    protected static int depthFunc;

    public static void renderOverlay() {

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen != null || mc.world == null)
            return;

        if (!Minecraft.isGuiEnabled())
            return;

        //renderOverlay(WailaTickHandler.instance().tooltip);

    }

    public static void renderOverlay(int verbose) {
        Minecraft.getMinecraft().mcProfiler.startSection("Waila Overlay");
        GlStateManager.pushMatrix();
        saveGLState();

        //GlStateManager.scale(OverlayConfig.scale, OverlayConfig.scale, 1.0F);

        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();

        //WailaRenderEvent.Pre event = new WailaRenderEvent.Pre(DataAccessorCommon.instance, tooltip.x, tooltip.y, tooltip.w, tooltip.h);
        //if (MinecraftForge.EVENT_BUS.post(event)) {
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableRescaleNormal();
            loadGLState();
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
            //return;
        //}

        //drawTooltipBox(event.getX(), event.getY(), event.getWidth(), event.getHeight(), OverlayConfig.bgcolor, OverlayConfig.gradient1, OverlayConfig.gradient2);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        //tooltip.draw();
        GlStateManager.disableBlend();

        //tooltip.draw2nd();

//        if (tooltip.hasIcon())
//            RenderHelper.enableGUIStandardItemLighting();

        GlStateManager.enableRescaleNormal();
//        if (tooltip.hasIcon() && !tooltip.stack.isEmpty())
//            renderStack(event.getX() + 5, event.getY() + event.getHeight() / 2 - 8, tooltip.stack);

        //MinecraftForge.EVENT_BUS.post(new WailaRenderEvent.Post(event.getX(), event.getY(), event.getWidth(), event.getHeight()));

        loadGLState();
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
        Minecraft.getMinecraft().mcProfiler.endSection();
    }

    public static void saveGLState() {
        hasLight = GL11.glGetBoolean(GL11.GL_LIGHTING);
        hasLight0 = GL11.glGetBoolean(GL11.GL_LIGHT0);
        hasLight1 = GL11.glGetBoolean(GL11.GL_LIGHT1);
        hasDepthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST);
        hasRescaleNormal = GL11.glGetBoolean(GL12.GL_RESCALE_NORMAL);
        hasColorMaterial = GL11.glGetBoolean(GL11.GL_COLOR_MATERIAL);
        depthFunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC);
        depthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
        GL11.glPushAttrib(GL11.GL_CURRENT_BIT); // Leave me alone :(
    }

    public static void loadGLState() {
        GlStateManager.depthMask(depthMask);
        GlStateManager.depthFunc(depthFunc);
        if (hasLight)
            GlStateManager.enableLighting();
        else
            GlStateManager.disableLighting();

        if (hasLight0)
            GlStateManager.enableLight(0);
        else
            GlStateManager.disableLight(0);

        if (hasLight1)
            GlStateManager.enableLight(1);
        else
            GlStateManager.disableLight(1);

        if (hasDepthTest)
            GlStateManager.enableDepth();
        else
            GlStateManager.disableDepth();
        if (hasRescaleNormal)
            GlStateManager.enableRescaleNormal();
        else
            GlStateManager.disableRescaleNormal();
        if (hasColorMaterial)
            GlStateManager.enableColorMaterial();
        else
            GlStateManager.disableColorMaterial();

        GlStateManager.popAttrib();
        //GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawTooltipBox(int x, int y, int w, int h, int bg, int grad1, int grad2) {
        //int bg = 0xf0100010;
        drawGradientRect(x + 1, y, w - 1, 1, bg, bg);
        drawGradientRect(x + 1, y + h, w - 1, 1, bg, bg);
        drawGradientRect(x + 1, y + 1, w - 1, h - 1, bg, bg);//center
        drawGradientRect(x, y + 1, 1, h - 1, bg, bg);
        drawGradientRect(x + w, y + 1, 1, h - 1, bg, bg);
        //int grad1 = 0x505000ff;
        //int grad2 = 0x5028007F;
        drawGradientRect(x + 1, y + 2, 1, h - 3, grad1, grad2);
        drawGradientRect(x + w - 1, y + 2, 1, h - 3, grad1, grad2);

        drawGradientRect(x + 1, y + 1, w - 1, 1, grad1, grad1);
        drawGradientRect(x + 1, y + h - 1, w - 1, 1, grad2, grad2);
    }

    //original: draw
    public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float zLevel = 0.0F;

        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos((double) (left + right), (double) top, (double) zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos((double) left, (double) top, (double) zLevel).color(f1, f2, f3, f).endVertex();
        buffer.pos((double) left, (double) (top + bottom), (double) zLevel).color(f5, f6, f7, f4).endVertex();
        buffer.pos((double) (left + right), (double) (top + bottom), (double) zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
