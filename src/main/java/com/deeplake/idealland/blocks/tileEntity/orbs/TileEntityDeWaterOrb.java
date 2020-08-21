package com.deeplake.idealland.blocks.tileEntity.orbs;

import com.deeplake.idealland.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//"Tonation Orb" de-detonation orb
public class TileEntityDeWaterOrb extends TileEntityOrbBase implements ITickable {

	@Override
	public void update() {
		super.update();
		RemoveFire();
	}

	void RemoveFire()
	{
		BlockPos origin = getPos();
		BlockPos target;

		World worldIn = world;

		float rangeX =getRange();

		for (int x = (int) -rangeX; x <= rangeX; x++){
			for (int y = (int) -rangeX; y <= rangeX; y++){
				for (int z = (int) -rangeX; z <= rangeX; z++){
					target = origin.add(x,y,z);
					IBlockState targetBlock = worldIn.getBlockState(target);
					Block type =  targetBlock.getBlock();

					if (type == Blocks.WATER || type == Blocks.FLOWING_WATER || type == Blocks.ICE)
					{
						if (type == Blocks.FLOWING_WATER )
						{
							worldIn.setBlockState(target, ModBlocks.CONSTRUCTION_SITE.getDefaultState());
						}
						else {
							worldIn.setBlockState(target, Blocks.AIR.getDefaultState());
						}
					}
				}
			}
		}
	}

	static
	{
		register("idealland:de_water_orb", TileEntityDeWaterOrb.class);
	}
}
