package com.deeplake.idealland.blocks;

import java.util.ArrayList;
import java.util.List;

import com.deeplake.idealland.blocks.BlockBase;
import com.deeplake.idealland.blocks.Furnitures.BlockExtractionDoorTest;

import com.deeplake.idealland.blocks.blockBasic.*;
import com.deeplake.idealland.blocks.blockMoroon.BlockMoroonBase;
import com.deeplake.idealland.blocks.builder.BlockBrickPlacer;
import com.deeplake.idealland.blocks.builder.BlockBuilderBase;
import com.deeplake.idealland.blocks.builder.BlockBuilderHouse;
import com.deeplake.idealland.blocks.builder.BlockBuilderOne;
import com.deeplake.idealland.blocks.tileEntity.builder.TileEntityBuilderFarm;
import com.deeplake.idealland.blocks.tileEntity.builder.TileEntityBuilderFarmSIN;
import com.deeplake.idealland.blocks.tileEntity.orbs.TileEntityDeArrowOrb;
import com.deeplake.idealland.blocks.tileEntity.orbs.TileEntityDeWaterOrb;
import com.deeplake.idealland.init.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	/*
	 * To add a block, put a line here,
	 * -Create a json at assets.eo.blockstates
	 * -Create a json at assets.eo.models.block
	 * -Create a json at assets.eo.models.item
	 * -Add corresponding texture png
	 */
	public static final BlockNullifyOrb NULLIFY_ORB = new BlockNullifyOrb("nullify_orb", Material.IRON);
	public static final BlockNullifyOrb NULLIFY_ORB_MOR = new BlockNullifyOrb("nullify_orb_mor", Material.IRON).setAdvanced(true);
	public static final BlockDeboomOrb DEBOOM_ORB = new BlockDeboomOrb("de_boom_orb",Material.IRON);
	public static final BlockEarthMender EARTH_MENDER = new BlockEarthMender("earth_mender_basic",Material.IRON);
	public static final BlockGeneralOrb DE_ARROW_ORB = new BlockGeneralOrb("de_arrow_orb",Material.IRON, TileEntityDeArrowOrb.class);
	public static final BlockGeneralOrb DE_WATER_ORB = new BlockGeneralOrb("de_water_orb",Material.IRON,  TileEntityDeWaterOrb.class);


	public static final IdeallandLight IDEALLAND_LIGHT_BASIC = new IdeallandLight("idealland_light_basic",Material.IRON);
	public static final BlockExtractionDoorTest EXTRACTION_DOOR = new BlockExtractionDoorTest("idealland_extraction_door",Material.IRON);

	public static final Block GRID_BLOCK_1 = new BlockBase("test", Material.CLAY).setCreativeTab(ModCreativeTab.IDL_BUILDING).setHardness(15f);
	public static final Block GRID_BLOCK_2 = new BlockBase("grid_dark_2", Material.CLAY).setCreativeTab(ModCreativeTab.IDL_BUILDING).setHardness(15f);
	public static final Block GRID_LAMP = new BlockBase("grid_lamp", Material.CLAY).setCreativeTab(ModCreativeTab.IDL_BUILDING).setHardness(15f).setLightLevel(1f);
	public static final Block GRID_NORMAL = new BlockBase("grid_normal", Material.CLAY).setCreativeTab(ModCreativeTab.IDL_BUILDING).setHardness(15f);

	public static final Block CONSTRUCTION_SITE = new BlockBase("construction_site", Material.GROUND).setCreativeTab(ModCreativeTab.IDL_BUILDING).setHardness(15f);
	public static final Block IDL_GLASS = new ModBlockGlassBase("idl_glass", Material.GLASS).setCreativeTab(ModCreativeTab.IDL_BUILDING).setHardness(1f).setLightOpacity(0).setLightLevel(1f);

	public static final BlockBuilderOne BUILDER_ONE = new BlockBuilderOne("builder_one", Material.CLAY);
	public static final BlockBuilderHouse BUILDER_HOUSE = new BlockBuilderHouse("builder_house", Material.CLAY);
	public static final BlockBuilderBase BUILDER_FARM = new BlockBuilderBase("builder_farm", Material.CLAY, TileEntityBuilderFarm.class);
	public static final BlockBuilderBase BUILDER_FARM_SIN = new BlockBuilderBase("builder_farm_sin", Material.CLAY, TileEntityBuilderFarmSIN.class);

	public static final BlockBrickPlacer BRICK_FENCE = new BlockBrickPlacer("fence_brick", Material.CLAY, Blocks.BRICK_BLOCK);
	//public static final Block BUFF_BLOCK_1 = new BuffBlock("buff_block_1", Material.IRON);
	
//	public static final DivineOre DIVINE_ORE = new DivineOre("divine_ore", Material.ROCK);
//	public static final PureOre PURE_ORE = new PureOre("pure_ore", Material.ROCK);
	//public static final BlockEarthMender EARTH_MENDER = new BlockEarthMender("earth_mender_basic", Material.ROCK);
	
	public static final BlockMoroonBase MORON_BLOCK = new BlockMoroonBase("moroon_block", Material.GROUND);
}
