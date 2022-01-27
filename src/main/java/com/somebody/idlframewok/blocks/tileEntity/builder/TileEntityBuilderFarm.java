package com.somebody.idlframewok.blocks.tileEntity.builder;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.blocks.tileEntity.builder.builderAction.BuilderActionBase;
import com.somebody.idlframewok.blocks.tileEntity.builder.builderAction.BuilderActionBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.Vector;

public class TileEntityBuilderFarm extends TileEntityBuilderBase {

	public void InitTaskQueue(){
		int radius = 4;
		BlockPos origin = new BlockPos(0,0,0);
		list = new Vector<BuilderActionBase>();

		for (int x = -radius; x <= radius; x++)
			for (int z = -radius; z <= radius; z++) {
				AddTaskBuild(origin.add(x ,-2, z), ModBlocks.GRID_NORMAL.getDefaultState());
			}

		radius = 1;

		for (int x = -radius; x <= radius; x++)
			for (int z = -radius; z <= radius; z++) {
				AddTaskBuild(origin.add(x ,-1, z), ModBlocks.CONSTRUCTION_SITE.getDefaultState());
			}

		list.add(new BuilderActionBlock(Blocks.WATER, 0,-1,0));

		radius = 4;

		for (int x = -radius; x <= radius; x++)
			for (int z = -radius; z <= radius; z++) {
				if (x != 0 || z != 0)
				{
					AddTaskBuild(origin.add(x ,-1, z), Blocks.FARMLAND.getDefaultState());
				}
			}
	}

	static
	{
		register("idlframewok:builder.builder_farm", TileEntityBuilderFarm.class);
	}
}
