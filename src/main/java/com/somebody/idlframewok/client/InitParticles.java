package com.somebody.idlframewok.client;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.client.particles.ParticleRedstonePlus;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.ParticleRedstone;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class InitParticles {

    private static Minecraft mc = Minecraft.getMinecraft();
    public static final Class<?>[] particleArgTypes = {String.class, int.class, boolean.class};

    public static EnumParticleTypes type1;

    public static void registerParticles() {
        String name = "test_particle";
        int id = EnumParticleTypes.values().length;
        IParticleFactory factory = new ParticleRedstonePlus.Factory();
        boolean renderOutside32 = false;//see RenderGlobal.spawnParticle0

        type1 = registerParticle(name, id, factory, renderOutside32);

        Class<?>[] argTypes = {String.class, int.class, boolean.class, int.class};

        EnumHelper.addEnum(EnumParticleTypes.class, "test_2", argTypes,
                "test_2", EnumParticleTypes.values().length, false, 0);

        Minecraft.getMinecraft().effectRenderer
                .registerParticle(EnumParticleTypes.values().length,
                        new ParticleRedstone.Factory());

        Idealland.Log("Register particles complete, now we have %s particle particleArgTypes", EnumParticleTypes.values().length);
    }

    private static EnumParticleTypes registerParticle(String name, int id, IParticleFactory factory, boolean renderOutside32) {
        EnumParticleTypes result = EnumHelper.addEnum(EnumParticleTypes.class,
                name,
                particleArgTypes, name, id, renderOutside32);

        mc.effectRenderer.registerParticle(id, factory);

        return result;
    }


    //for more, see methods with return type of
    // net.minecraft.client.renderer.texture.TextureAtlasSprite
    public static TextureAtlasSprite getSpriteFromBlockState(IBlockState state)
    {
        return mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
    }

    public static TextureAtlasSprite getSpriteFromItemAndMeta(Item itemIn, int meta) {
        return mc.getRenderItem().getItemModelMesher().getParticleIcon(itemIn, meta);
    }

    public static ITextureObject getTextureFromResLoc(ResourceLocation resource) {
        TextureManager textureManager = Minecraft.getMinecraft().renderEngine;
        return textureManager.getTexture(resource);
    }
}
