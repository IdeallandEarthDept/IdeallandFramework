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
            return new ItemStack(ModItems.GUA[0]);
        }
    };
    
    public static final CreativeTabs IDL_BUILDING = new CreativeTabs(CreativeTabs.getNextID(), "ideallandFurnitureTab")
	{
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModBlocks.EXTRACTION_DOOR);
        }
    };

    public static final CreativeTabs IDL_BUILDER = new CreativeTabs(CreativeTabs.getNextID(), "ideallandBuilderTab")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModBlocks.CONSTRUCTION_SITE);
        }
    };

    public static final CreativeTabs IDL_SKILL = new CreativeTabs(CreativeTabs.getNextID(), "ideallandSkillsTab")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.skillFireBlast);
        }
    };

    public static final CreativeTabs IDL_FADE = new CreativeTabs(CreativeTabs.getNextID(), "ideallandFadeTab")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.FADE_CHAIN[1]);
        }
    };
}
