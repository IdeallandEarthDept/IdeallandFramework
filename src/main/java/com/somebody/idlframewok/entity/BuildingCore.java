package com.somebody.idlframewok.entity;

import com.somebody.idlframewok.blocks.tileEntity.builder.builderAction.*;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Vector;

public class BuildingCore {

    private World world;
    protected Vector<BuilderActionBase> list;

    protected int reserved_first_tasks_count = 0;//some has to be done before anything else, like clearing

    public float buildRatePerTick = 60f;
    public float curBuildCounter = - CommonDef.TICK_PER_SECOND * 3f * buildRatePerTick;

    private int curBuildActionIndex = 0;
    private boolean finished = false;

    private BuildingCore() {
    }

    public void ResetTasks()
    {
        reserved_first_tasks_count = 0;
        list.clear();
    }

    public void setSpeed(float buildSpeed)
    {
        buildRatePerTick = buildSpeed;
        curBuildCounter = - CommonDef.TICK_PER_SECOND * 3f * buildRatePerTick;
    }

    public BuildingCore(World world) {
        this.world = world;
        list = new Vector<>();
    }

    public void AddTaskSummon(BlockPos pos, Class<? extends Entity> creature)
    {
        list.add(new BuilderActionSummonEntity(pos, creature));
    }

    public void AddTaskBuild(BlockPos pos, IBlockState newState) {
        AddTaskBuild(pos, newState, false);
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
            list.add(new BuilderActionBlockBrutal(newState, pos));
        }
    }

    public void readFromNBT(NBTTagCompound compound) {
        //super.readFromNBT(compound);
        this.curBuildActionIndex = compound.getInteger(IDLNBTDef.CUR_TASK_INDEX);
        this.buildRatePerTick = compound.getFloat(IDLNBTDef.BUILD_SPEED);

        if (curBuildActionIndex >= list.size()) {
            finished = true;
        }
    }

//    public NBTTagCompound getUpdateTag()
//    {
//        return this.writeToNBT(new NBTTagCompound());
//    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        //super.writeToNBT(compound);
        compound.setInteger(IDLNBTDef.CUR_TASK_INDEX, this.curBuildActionIndex);
        compound.setFloat(IDLNBTDef.BUILD_SPEED, this.buildRatePerTick);
        return compound;
    }

    public BuilderActionBase getCurAction()
    {
        if (curBuildActionIndex >= list.size())
        {
            return list.get(list.size() - 1);
        }
        else {
            return list.get(curBuildActionIndex);
        }
    }

    public void update(BlockPos basePos) {
        boolean remote = world.isRemote;

        if (finished || remote) {
            return;
        }

        curBuildCounter += buildRatePerTick;
        while (curBuildCounter > 1){
            curBuildCounter--;

            BuilderActionBase action = list.get(curBuildActionIndex);
            if (action != null){
                boolean success = action.Execute(world, basePos);
                if (success) {
                    curBuildActionIndex++;
                    if (curBuildActionIndex >= list.size()) {
                        finished = true;
                        OnFinished(basePos);
                        return;
                    }
                }
                else {
                    return;
                }
            }
        }
    }

    public float GetProgress() {
        return MathHelper.clamp((float) curBuildActionIndex / list.size(), 0f, 1f) ;
    }

    public void OnFinished(BlockPos basePos)
    {
        world.playSound(basePos.getX(), basePos.getY(), basePos.getZ(), SoundEvents.BLOCK_PISTON_EXTEND, null, 1f, 1f, true);
    }

    //some helper
    public void AddTaskFillWithBlockCentered(BlockPos origin, int rangeX, int rangeY, int rangeZ, IBlockState newState) {
        AddTaskFillWithBlockCentered(origin, rangeX, rangeY, rangeZ, newState, false);
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
        AddTaskBuildWallWithBlockCentered(origin, rangeX, height, rangeZ, newState, false);
    }

    public void AddTaskBuildWallWithBlockCentered(BlockPos origin, int rangeX, int height, int rangeZ, IBlockState newState, boolean isSafe) {
        for(int x = -rangeX;
            x <=rangeX;x++) {
            for (int y = height - 1; y >= 0; y--) {
                for (int z = -rangeZ; z <= rangeZ; z++) {
                    AddTaskBuild(origin.add(x, y, z), newState, isSafe);
                }
            }
        }
    }
}
