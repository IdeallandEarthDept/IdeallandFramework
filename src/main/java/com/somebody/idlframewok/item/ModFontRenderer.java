package com.somebody.idlframewok.item;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ModFontRenderer extends FontRenderer {
    public ModFontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
        super(gameSettingsIn, location, textureManagerIn, unicode);
    }

    float modifier = 0;
    float modifierG = 0;
    float modifierB = 0;

    public void colorCodeSyncWithWorld(World world) {
        modifier = (world.getTotalWorldTime() % 255) / 255f;
        modifierG = ((world.getTotalWorldTime() + 255 / 3) % 255) / 255f;
        modifierB = ((world.getTotalWorldTime() + 255 * 2 / 3) % 255) / 255f;
    }

    @Override
    protected void setColor(float r, float g, float b, float a) {
        super.setColor(r * modifier, g * modifierG, b * modifierB, a);
    }
}
