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

	public static final Block GRID_BLOCK_1 = new BlockBase("test", Material.CLAY).setCreativeTab(ModCreativeTab.IDL_BUILDING).setHardness(15f);
}
