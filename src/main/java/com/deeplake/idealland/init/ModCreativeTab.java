package com.deeplake.idealland.init;

import com.deeplake.idealland.blocks.ModBlocks;
import com.deeplake.idealland.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTab {
	public static final CreativeTabs IDL_MISC = new CreativeTabs(CreativeTabs.getNextID(), "ideallandMiscTab")
	{
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(Items.QUARTZ);
        }
    };
}
