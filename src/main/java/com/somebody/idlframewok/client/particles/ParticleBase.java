package com.somebody.idlframewok.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleBase extends Particle {

    TextureManager manager = Minecraft.getMinecraft().getTextureManager();
    ResourceLocation location;
    final float sizeHalf = 0.125F;

    public ParticleBase(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);

        //Barrier:
        //this.setParticleTexture(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(itemIn));

        //Breaking:
        //this.setParticleTexture(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(itemIn, meta));

        //Digging:
        //this.setParticleTexture(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
            return new ParticleBase(worldIn, xCoordIn, yCoordIn, zCoordIn, (float) xSpeedIn, (float) ySpeedIn, (float) zSpeedIn);
        }
    }
    //--
    /**
     * Renders the particle
     */

    //tessellator.draw() called in particle manager.
    //not a draw call per particle
//    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
//    {
//        float f = (float)this.particleTextureIndexX / 16.0F;
//        float f1 = f + 0.0624375F;
//        float f2 = (float)this.particleTextureIndexY / 16.0F;
//        float f3 = f2 + 0.0624375F;
//        float f4 = 0.1F * this.particleScale;
//
//        if (this.particleTexture != null)
//        {
//            f = this.particleTexture.getMinU();
//            f1 = this.particleTexture.getMaxU();
//            f2 = this.particleTexture.getMinV();
//            f3 = this.particleTexture.getMaxV();
//        }
//
//        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
//        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
//        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
//        int i = this.getBrightnessForRender(partialTicks);
//        int j = i >> 16 & 65535;
//        int k = i & 65535;
//        Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};
//
//        if (this.particleAngle != 0.0F)
//        {
//            float f8 = this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
//            float f9 = MathHelper.cos(f8 * 0.5F);
//            float f10 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.x;
//            float f11 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.y;
//            float f12 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.z;
//            Vec3d vec3d = new Vec3d((double)f10, (double)f11, (double)f12);
//
//            for (int l = 0; l < 4; ++l)
//            {
//                avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double)(f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale((double)(2.0F * f9)));
//            }
//        }
//
//        buffer.pos((double)f5 + avec3d[0].x, (double)f6 + avec3d[0].y, (double)f7 + avec3d[0].z).tex((double)f1, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
//        buffer.pos((double)f5 + avec3d[1].x, (double)f6 + avec3d[1].y, (double)f7 + avec3d[1].z).tex((double)f1, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
//        buffer.pos((double)f5 + avec3d[2].x, (double)f6 + avec3d[2].y, (double)f7 + avec3d[2].z).tex((double)f, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
//        buffer.pos((double)f5 + avec3d[3].x, (double)f6 + avec3d[3].y, (double)f7 + avec3d[3].z).tex((double)f, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
//
//        //
//        buffer
//                .pos((double)f5 + avec3d[0].x, (double)f6 + avec3d[0].y, (double)f7 + avec3d[0].z)
//                .tex((double)f1, (double)f3)
//                .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
//                .lightmap(j, k)
//                .endVertex();
//
//    }

    private static final VertexFormat VERTEX_FORMAT =
            (new VertexFormat())
                    .addElement(DefaultVertexFormats.POSITION_3F)
                    .addElement(DefaultVertexFormats.TEX_2F)
                    .addElement(DefaultVertexFormats.COLOR_4UB)
                    .addElement(DefaultVertexFormats.TEX_2S)
                    .addElement(DefaultVertexFormats.NORMAL_3B)
                    .addElement(DefaultVertexFormats.PADDING_1B);

//    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
//    {
//        int maxFrame = 15;
//        int animFrame = maxFrame;//0~15
//        int size = 1;
//
//        int frameCountRow = 4;
//
//        final float quarter = 0.24975F;
//
//        if (animFrame <= maxFrame)
//        {
//            manager.bindTexture(location);
//            float xRatioOnTexture = (float)(animFrame % frameCountRow) / ((float)frameCountRow);
//            float xEndOnTexture = xRatioOnTexture + quarter;//0.25 * 0.999
//            float yRatioOnTexture = (float)(animFrame / frameCountRow) / ((float)frameCountRow);
//            float yEndOnTexture = yRatioOnTexture + quarter;
//
//            float diameter = 2.0F * size;
//
//            final double vx = this.posX - this.prevPosX;
//            final double vy = this.posY - this.prevPosY;
//            final double vz = this.posZ - this.prevPosZ;
//
//            float playerRelX = (float)(this.prevPosX + vx * (double)partialTicks - interpPosX);
//            float playerRelY = (float)(this.prevPosY + vy * (double)partialTicks - interpPosY);
//            float playerRelZ = (float)(this.prevPosZ + vz * (double)partialTicks - interpPosZ);
//
//            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//            GlStateManager.disableLighting();
//            RenderHelper.disableStandardItemLighting();
//            buffer.begin(7, VERTEX_FORMAT);
//            buffer.pos((double)(playerRelX - rotationX * diameter - rotationXY * diameter), (double)(playerRelY - rotationZ * diameter), (double)(playerRelZ - rotationYZ * diameter - rotationXZ * diameter)).tex((double)xEndOnTexture, (double)yEndOnTexture).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
//            buffer.pos((double)(playerRelX - rotationX * diameter + rotationXY * diameter), (double)(playerRelY + rotationZ * diameter), (double)(playerRelZ - rotationYZ * diameter + rotationXZ * diameter)).tex((double)xEndOnTexture, (double)yRatioOnTexture).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
//            buffer.pos((double)(playerRelX + rotationX * diameter + rotationXY * diameter), (double)(playerRelY + rotationZ * diameter), (double)(playerRelZ + rotationYZ * diameter + rotationXZ * diameter)).tex((double)xRatioOnTexture, (double)yRatioOnTexture).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
//            buffer.pos((double)(playerRelX + rotationX * diameter - rotationXY * diameter), (double)(playerRelY - rotationZ * diameter), (double)(playerRelZ + rotationYZ * diameter - rotationXZ * diameter)).tex((double)xRatioOnTexture, (double)yEndOnTexture).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
//            Tessellator.getInstance().draw();
//            GlStateManager.enableLighting();
//        }
//    }

    //to see relative particles, see ParticleBreaking; for global ones, see ParticleExplosionLarge
    public void renderParticleBreaking(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        //Jitter is randFloat()*3
        float f = ((float) this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
        float f1 = f + 0.015609375F;
        float f2 = ((float) this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
        float f3 = f2 + 0.015609375F;
        float f4 = 0.1F * this.particleScale;

        if (this.particleTexture != null) {
            //converts to 0~1
            f = this.particleTexture.getInterpolatedU((double) (this.particleTextureJitterX / 4.0F * 16.0F));
            f1 = this.particleTexture.getInterpolatedU((double) ((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
            f2 = this.particleTexture.getInterpolatedV((double) (this.particleTextureJitterY / 4.0F * 16.0F));
            f3 = this.particleTexture.getInterpolatedV((double) ((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
        }

        //inter pos is of player
        float f5 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
        float f6 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
        float f7 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);
        int i = this.getBrightnessForRender(partialTicks);//(0~16)<<4.  0x[Sky,16bit][Block,16bit]
        int j = i >> 16 & 0xffff;//light map high
        int k = i & 0xffff;//light map low
        buffer.pos((double) (f5 - rotationX * f4 - rotationXY * f4), (double) (f6 - rotationZ * f4), (double) (f7 - rotationYZ * f4 - rotationXZ * f4)).tex((double) f, (double) f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
        buffer.pos((double) (f5 - rotationX * f4 + rotationXY * f4), (double) (f6 + rotationZ * f4), (double) (f7 - rotationYZ * f4 + rotationXZ * f4)).tex((double) f, (double) f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
        buffer.pos((double) (f5 + rotationX * f4 + rotationXY * f4), (double) (f6 + rotationZ * f4), (double) (f7 + rotationYZ * f4 + rotationXZ * f4)).tex((double) f1, (double) f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
        buffer.pos((double) (f5 + rotationX * f4 - rotationXY * f4), (double) (f6 - rotationZ * f4), (double) (f7 + rotationYZ * f4 - rotationXZ * f4)).tex((double) f1, (double) f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
    }

    /**
     * Renders the particle when you are using Layer 3 (or anything that's not 0&1)
     */
    public void renderParticleManual(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float posArg = 0;
        posArg = posArg * posArg;
        float fadeFactor = 2.0F - posArg * 2.0F;

        if (fadeFactor > 1.0F) {
            fadeFactor = 1.0F;
        }

        fadeFactor = fadeFactor * 0.2F;
        GlStateManager.disableLighting();

        //offset between posNextLogical and posPartialTick
        float offX = (float) (this.posX - interpPosX);
        float offY = (float) (this.posY - interpPosY);
        float offZ = (float) (this.posZ - interpPosZ);

        float brightness = this.world.getLightBrightness(new BlockPos(this.posX, this.posY, this.posZ));

        manager.bindTexture(location);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

        buffer.pos((double) (offX - sizeHalf), (double) offY, (double) (offZ + sizeHalf)).tex(0.0D, 1.0D).color(brightness, brightness, brightness, fadeFactor).endVertex();
        buffer.pos((double) (offX + sizeHalf), (double) offY, (double) (offZ + sizeHalf)).tex(1.0D, 1.0D).color(brightness, brightness, brightness, fadeFactor).endVertex();
        buffer.pos((double) (offX + sizeHalf), (double) offY, (double) (offZ - sizeHalf)).tex(1.0D, 0.0D).color(brightness, brightness, brightness, fadeFactor).endVertex();
        buffer.pos((double) (offX - sizeHalf), (double) offY, (double) (offZ - sizeHalf)).tex(0.0D, 0.0D).color(brightness, brightness, brightness, fadeFactor).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
    }

    /**
     * Retrieve what effect layer (what texture) the particle should be rendered with. 0 for the particle sprite sheet,
     * 1 for the main Texture atlas, and 3 for a custom texture
     * 3 : explosionLarge(&Huge), footsteps, ItemPickup, MobAppearance(Guardian), SweepAttack,
     * 1 : Barrier, Digging, Breaking
     */
    public int getFXLayer() {
        return 1;
    }

//    switch (fxLayer)
//    {
//        case 0:
//        default:
//            this.renderer.bindTexture(PARTICLE_TEXTURES);
//            break;
//        case 1:
//            this.renderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//    }


//    /**
//     * Sets the texture used by the particle.
//     */
//    public void setParticleTexture(TextureAtlasSprite texture)
//    {
//        int i = this.getFXLayer();
//
//        if (i == 1)
//        {
//            this.particleTexture = texture;
//        }
//        else
//        {
//            throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
//        }
//    }
}
