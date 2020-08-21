package com.deeplake.idealland.blocks.tileEntity.builder;

import com.deeplake.idealland.blocks.ModBlocks;
import com.deeplake.idealland.blocks.tileEntity.builder.builderAction.BuilderActionBase;
import com.deeplake.idealland.blocks.tileEntity.builder.builderAction.BuilderActionBlock;
import net.minecraft.block.BlockCrops;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.Vector;

public class TileEntityBuilderFarmSIN extends TileEntityBuilderFarm {

	public void InitTaskQueue(){
		super.InitTaskQueue();
		int radius = 4;
		BlockPos origin = new BlockPos(0,0,0);
		int maxAge = ((BlockCrops)Blocks.WHEAT).getMaxAge();

		for (int x = -radius; x <= radius; x++)
			for (int z = -radius; z <= radius; z++) {
				if (x != 0 || z != 0)
				{
					AddTaskBuild(origin.add(x ,0, z), ((BlockCrops)Blocks.WHEAT).withAge(maxAge), false);
				}
			}
	}

	static
	{
		register("idealland:builder.builder_farm", TileEntityBuilderFarmSIN.class);
	}
}
