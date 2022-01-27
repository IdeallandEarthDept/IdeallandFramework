package com.somebody.idlframewok.init;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTabsList {
    //public static final ModTabTest MOD_TAB_TEST = new ModTabTest("testTest", "test_gui.png", new ItemStack(ModItems.MOROON_RIFLE));
    //public static final ModTabTest MOD_TAB_TEST_1 = new ModTabTest("testTest2");

	public static final CreativeTabs IDL_MISC = new CreativeTabs(CreativeTabs.getNextID(), "ideallandMiscTab")
	{
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.skillSJTeleport2);
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

    public static final CreativeTabs IDL_WORLD = new CreativeTabs(CreativeTabs.getNextID(), "ideallandWorldTab")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModBlocks.STAR_BLOCK);
        }
    };

    public static final CreativeTabs IDL_CIRCUIT = new CreativeTabs(CreativeTabs.getNextID(), "ideallandCircuitTab") {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.GARGOYLE_BODY);
        }
    };

    public static final CreativeTabs IDL_DUNGEON = new CreativeTabs(CreativeTabs.getNextID(), "ideallandDungeonTab")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModBlocks.LOCK_ADJ_ON);
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

    public static final CreativeTabs IDL_TCG = new CreativeTabs(CreativeTabs.getNextID(), "ideallandTCGTab")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.TCG_SET_1[18]);
        }
    };
}
