package com.somebody.idlframewok.blocks.tileEntity.builder;

import com.somebody.idlframewok.blocks.tileEntity.builder.builderAction.BuilderActionBase;
import com.somebody.idlframewok.blocks.tileEntity.builder.builderAction.BuilderActionBlock;
import com.somebody.idlframewok.blocks.tileEntity.builder.builderAction.BuilderActionBlockSafe;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.Vector;

public class TileEntityBuilderBase extends TileEntity implements ITickable {
	private SoundEvent onLoadSound = SoundEvents.BLOCK_NOTE_CHIME;

	protected SoundEvent buildSound = SoundEvents.BLOCK_NOTE_CHIME;
	protected SoundEvent finishSound = SoundEvents.BLOCK_NOTE_XYLOPHONE;
	protected SoundEvent failSound = SoundEvents.BLOCK_LEVER_CLICK;

	protected Vector<BuilderActionBase> list;

	protected int reserved_first_tasks_count = 0;//some has to be done before anything else, like clearing

	@Override
	public void onLoad()
    {
    	super.onLoad();
		Init();
    }

	void Init()
	{
		InitTaskQueue();
	}

	public void PlaySound(SoundEvent ev)
	{
		world.playSound(null, this.pos, ev, SoundCategory.BLOCKS, 0.3F, 1f);
	}


	public void CreateParticles()
	{
//		if (world.isRemote && isReady)
//		{
//			Random random = new Random();
//			Vec3d myPos = GetPosInFloat();
//			float range = 2f;
//			float x = (random.nextFloat() - 0.5f) * range;
//			float y = (random.nextFloat() - 0.5f) * range;
//			float z = (random.nextFloat() - 0.5f) * range;
//			float vFactor = -1f;
//
//			world.spawnParticle(particleType, myPos.x + x, myPos.y + y, myPos.z + z, x * vFactor, y * vFactor, z * vFactor);
//		}
	}

	//building

	public float buildRatePerTick = 1f;
	public float curBuildCounter = - CommonDef.TICK_PER_SECOND * 3;

	private int curBuildActionIndex = 0;
	private boolean finished = false;

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.curBuildActionIndex = compound.getInteger(IDLNBTDef.CUR_TASK_INDEX);
		this.buildRatePerTick = compound.getFloat(IDLNBTDef.BUILD_SPEED);
		if (list == null){
			InitTaskQueue();
		}

		if (curBuildActionIndex >= list.size()) {
			finished = true;
			return;
		}
	}

	public NBTTagCompound getUpdateTag()
	{
		return this.writeToNBT(new NBTTagCompound());
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger(IDLNBTDef.CUR_TASK_INDEX, this.curBuildActionIndex);
		compound.setFloat(IDLNBTDef.BUILD_SPEED, this.buildRatePerTick);
		return compound;
	}

	protected void OnFinished(){
		world.setBlockState(pos, Blocks.AIR.getDefaultState());
		invalidate();
	}

	@Override
	public void update() {
		if (finished || world.isRemote) {
			return;
		}

		curBuildCounter += buildRatePerTick;
		while (curBuildCounter > 1){
			curBuildCounter -= 1;

			BuilderActionBase action = list.get(curBuildActionIndex);
			if (action != null){
				boolean success = action.Execute(world, this.pos);
				if (success) {
					curBuildActionIndex++;
					if (curBuildActionIndex >= list.size()) {
						finished = true;
						PlaySound(finishSound);
						OnFinished();
						return;
					}

					if (world.getTotalWorldTime() % CommonDef.TICK_PER_SECOND == 0) {
						PlaySound(buildSound);
					}
				}
				else {
					if (world.getTotalWorldTime() % CommonDef.TICK_PER_SECOND == 0) {
						PlaySound(failSound);
					}
					return;
				}
			}
		}
	}

	public float GetProgress() {
		return MathHelper.clamp(curBuildActionIndex / list.size(), 0f, 1f) ;
	}

	public void InitTaskQueue(){
//		int radius = 10;
//		list = new Vector<BuilderActionBase>();
//		for (int x = -radius; x <= radius; x++)
//			for (int z = -radius; z <= radius; z++) {
//				list.add(new BuilderActionBlock(ModBlocks.CONSTRUCTION_SITE, x,-1,z));
//			}
	}

	//some helper
	public void AddTaskFillWithBlockCentered(BlockPos origin, int rangeX, int rangeY, int rangeZ, IBlockState newState) {
		AddTaskFillWithBlockCentered(origin, rangeX, rangeY, rangeZ, newState, true);
	}

	public void AddTaskFillWithBlockCentered(BlockPos origin, int rangeX, int rangeY, int rangeZ, IBlockState newState, boolean isSafe) {
		for(int x = -rangeX;
			x <=rangeX;x++) {
			for (int y = -rangeY; y <= rangeY; y++) {
				for (int z = -rangeZ; z <= rangeZ; z++) {
					AddTaskBuild(origin.add(x, y, z), newState, isSafe);
				}
			}
		}
	}

	public void AddTaskBuildWallWithBlockCentered(BlockPos origin, int rangeX, int height, int rangeZ, IBlockState newState) {
		AddTaskBuildWallWithBlockCentered(origin, rangeX, height, rangeZ, newState, true);
	}

	public void AddTaskBuildWallWithBlockCentered(BlockPos origin, int rangeX, int height, int rangeZ, IBlockState newState, boolean isSafe) {
		for(int x = -rangeX;
			x <=rangeX;x++) {
			for (int y = 0; y < height; y++) {
				for (int z = -rangeZ; z <= rangeZ; z++) {
					AddTaskBuild(origin.add(x, y, z), newState, isSafe);
				}
			}
		}
	}

	public void AddTaskBuild(BlockPos pos, IBlockState newState) {
		AddTaskBuild(pos, newState, true);
	}

	public void AddTaskBuild(BlockPos pos, IBlockState newState, boolean isSafe){
		if (isSafe) {
			if (newState.getBlock() == Blocks.AIR) {
				list.add(reserved_first_tasks_count, new BuilderActionBlock(newState, pos));
				reserved_first_tasks_count++;
			}else {
				list.add(reserved_first_tasks_count, new BuilderActionBlock(Blocks.BRICK_BLOCK, pos));
				list.add(new BuilderActionBlockSafe(newState, pos));
			}
		} else {
			list.add(new BuilderActionBlock(newState, pos));
		}
	}

//	static
//	{
//		registerSpawnList("idlframewok:builder.builder_one", TileEntityBuilderBase.class);
//	}
}
