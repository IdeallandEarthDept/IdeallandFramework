package com.somebody.idlframewok.debug;

import com.somebody.idlframewok.Idealland;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModTabTest extends CreativeTabs {

    final ItemStack iconStack;
    final String bgName;
    final boolean useDefaultBG;

    public ModTabTest(String label, ItemStack stack) {
        super(label);
        this.bgName = "items.png";
        this.iconStack = stack;
        //setBackgroundImageName(bgName);
        useDefaultBG = true;
    }

    public ModTabTest(String label, String bgName, ItemStack stack) {
        super(label);
        this.bgName = bgName;
        this.iconStack = stack;
        setBackgroundImageName(bgName);
        useDefaultBG = false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem() {
        return iconStack;
    }

    @SideOnly(Side.CLIENT)
    public net.minecraft.util.ResourceLocation getBackgroundImage()
    {
        return new net.minecraft.util.ResourceLocation(useDefaultBG ? "minecraft" : Idealland.MODID,"textures/gui/container/creative_inventory/tab_" + this.getBackgroundImageName());
    }
}
