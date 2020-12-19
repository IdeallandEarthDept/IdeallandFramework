package com.somebody.idlframewok.blocks.tileEntity.orbs;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class TileEntityEarthMender extends TileEntityOrbBase implements ITickable {

	int depth = 4;
	int range = 5;//0 is self. this is radius

	@Override
	public void update() {
		super.update();

		World worldIn = this.world;
		if (worldIn.isRemote) {
			return;
		}
		if (worldIn.getTotalWorldTime() % 3L == 0L)
        {
			IBlockState block =  Blocks.DIRT.getDefaultState();

			int diameter = 2 * range + 1;
			int zFactor = diameter;
			int slowDownFactor = 5;

			long worldTime = worldIn.getTotalWorldTime() / slowDownFactor;

			int dx = (int) (worldTime % diameter) - range;
			int dz = (int) (worldTime / zFactor % (diameter)) - range;

			BlockPos targetPos = pos.add(dx, -depth, dz);

			boolean isDownEmpty = IsBlockPosEmpty(worldIn, targetPos.down());
			boolean isCenterEmpty = IsBlockPosEmpty(worldIn, targetPos);

			for(int y = 1; y <= depth; y++)
			{
				boolean[] nearbyOccupied = new boolean[4];
				nearbyOccupied[0] = !IsBlockPosEmpty(worldIn, targetPos.east());
				nearbyOccupied[1] = !IsBlockPosEmpty(worldIn, targetPos.south());
				nearbyOccupied[2] = !IsBlockPosEmpty(worldIn, targetPos.west());
				nearbyOccupied[3] = !IsBlockPosEmpty(worldIn, targetPos.north());

				boolean putDown = false;

				if (!isDownEmpty && isCenterEmpty)
				{
					//check the four directions
					for (int i = 0; i <= 3; i++)
					{
						if (nearbyOccupied[i] && nearbyOccupied[(i + 1)%4])
						{
							//	At first I want to make the block imitate a nearby block
							//but soon I found this will allow players to get ores indefinitely.
							worldIn.setBlockState(targetPos, Blocks.DIRT.getDefaultState());
							putDown = true;
							break;
						}
					}
				}

				targetPos = targetPos.up();
				isDownEmpty = isCenterEmpty && !putDown ;
				isCenterEmpty = IsBlockPosEmpty(worldIn, targetPos);
			}
        }
	}

	private boolean IsBlockStateEmpty(World worldIn, IBlockState blockState) {
		boolean result = blockState.getBlock() == Blocks.AIR;
		return result;
	}

	private boolean IsBlockPosEmpty(World worldIn, BlockPos targetPos) {
		IBlockState block = worldIn.getBlockState(targetPos);
		boolean result = (block.getBlock() == Blocks.AIR) ||
				(block.getBlock().isReplaceable(worldIn, targetPos));
		//&& block.isOpaqueCube();
		return result;
	}

//	@Override
//	public boolean receiveClientEvent(int event, int param) {
//		return true;
//	}

//	//No Use
//	@SubscribeEvent
//	public void onSpawn(LivingSpawnEvent.CheckSpawn event) {
//		IdlFramework.Log("Spawning:"+event.getEntityLiving().getName());
//		int range = NULLIFY_DISTANCE;
//		if(event.getResult() != Event.Result.ALLOW && event.getEntityLiving() instanceof IMob) {
//			AxisAlignedBB aabb = new AxisAlignedBB(event.getX() - NULLIFY_DISTANCE, event.getY() - NULLIFY_DISTANCE, event.getZ() - NULLIFY_DISTANCE, event.getX() + NULLIFY_DISTANCE, event.getY() + NULLIFY_DISTANCE, event.getZ() + NULLIFY_DISTANCE);
//			if (aabb.contains(new Vec3d(this.pos.getX(), this.pos.getY(), this.pos.getZ())))
//			{
//				event.setResult(Event.Result.DENY);
//				IdlFramework.Log("Stopped spawning:"+event.getEntityLiving().getName());
//				return;
//			}
//		}
//	}
	
	static 
	{
		register("idlframewok:earth_mender_basic", TileEntityEarthMender.class);
	}

}
