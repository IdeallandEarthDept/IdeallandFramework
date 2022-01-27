package com.somebody.idlframewok.blocks.tileEntity.builder;

import com.somebody.idlframewok.blocks.furnitures.BlockExtractionDoorTest;
import com.somebody.idlframewok.blocks.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.Vector;

import static com.somebody.idlframewok.blocks.furnitures.BlockExtractionDoorTest.FACING;

public class TileEntityBuilderHouse extends TileEntityBuilderBase {

	public void InitTaskQueue(){
		list = new Vector<>();
		int floorReach = 3;//radius - 1
		int wallHeight = 3;
		int windowHeight = 1;
		int windowWidth = 1;

		BlockPos origin = new BlockPos(0,0,0);

		IBlockState floorMaterial =  Blocks.DOUBLE_STONE_SLAB.getDefaultState();
		IBlockState wallMaterial =  Blocks.CONCRETE.getDefaultState();
		IBlockState roofMaterial =  Blocks.CONCRETE.getDefaultState();
		IBlockState windowMaterial =  Blocks.GLASS.getDefaultState();
		IBlockState lightMaterial = ModBlocks.IDEALLAND_LIGHT_BASIC.getDefaultState();

		//Space Inside
		AddTaskFillWithBlockCentered(origin.add(0,floorReach,0), floorReach, 3, floorReach, Blocks.AIR.getDefaultState());
		//Floor
		AddTaskFillWithBlockCentered(origin.add(0,0,0), floorReach, 0, floorReach, floorMaterial);
		AddTaskFillWithBlockCentered(origin.add(0,-1,0), floorReach + 1, 0, floorReach + 1,floorMaterial);
		//Window
		AddTaskBuildWallWithBlockCentered(origin.add(floorReach,1,0), 0, wallHeight, floorReach,wallMaterial);
		AddTaskBuildWallWithBlockCentered(origin.add(-floorReach,1,0), 0, wallHeight, floorReach, wallMaterial);
		AddTaskBuildWallWithBlockCentered(origin.add(0,1,floorReach), floorReach, wallHeight, 0, wallMaterial);
		AddTaskBuildWallWithBlockCentered(origin.add(0,1,-floorReach), floorReach, wallHeight, 0, wallMaterial);
		//Roof
		AddTaskFillWithBlockCentered(origin.add(0,wallHeight + 1,0), floorReach, 0, floorReach, roofMaterial);
		//Windows
		AddTaskBuildWallWithBlockCentered(origin.add(floorReach,2,0), 0, windowHeight, 1, windowMaterial);
		AddTaskBuildWallWithBlockCentered(origin.add(-floorReach,2,0), 0, windowHeight, 1, windowMaterial);
		AddTaskBuildWallWithBlockCentered(origin.add(0,2,floorReach), 1, windowHeight, 0, windowMaterial);
		AddTaskBuildWallWithBlockCentered(origin.add(0,2,-floorReach), 1, windowHeight, 0, windowMaterial);

		//Light
		AddTaskBuild(origin.add(0,wallHeight + 1,0), lightMaterial);

		//furnitures
		AddTaskBuild(origin.add(floorReach - 1,1,floorReach - 1), Blocks.CRAFTING_TABLE.getDefaultState());
		AddTaskBuild(origin.add(floorReach - 1,2,floorReach - 1), Blocks.FURNACE.getDefaultState());

		AddTaskBuild(origin.add(1 - floorReach ,1,floorReach - 1), Blocks.CHEST.getDefaultState());
		AddTaskBuild(origin.add(1 - floorReach ,1,floorReach - 2), Blocks.CHEST.getDefaultState());

		//Door
		AddTaskBuild(origin.add(0 ,1,floorReach), ModBlocks.EXTRACTION_DOOR.getDefaultState().withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.EAST));
		AddTaskBuild(origin.add(0 ,2,floorReach), ModBlocks.EXTRACTION_DOOR.getDefaultState().withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.EAST));
	}

	static
	{
		register("idlframewok:builder.builder_one", TileEntityBuilderHouse.class);
	}
}
