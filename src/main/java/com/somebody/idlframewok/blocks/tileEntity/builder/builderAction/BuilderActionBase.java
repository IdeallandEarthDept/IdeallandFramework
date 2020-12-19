package com.somebody.idlframewok.blocks.tileEntity.builder.builderAction;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BuilderActionBase {

    Vec3d relativePos;
    public boolean complete = false;

    BuilderActionBase(float x, float y, float z){
        SetRelativePos(x,y,z);
    }

    BuilderActionBase(BlockPos blockPos){
        SetRelativePos(blockPos.getX(),blockPos.getY(),blockPos.getZ());
    }

    public boolean IsComplete(){
        return complete;
    }

    public boolean Execute(World world, BlockPos ori_pos){
        complete = true;
        return true;
    }

    public void SetRelativePos(float x, float y, float z){
        relativePos = new Vec3d(x,y,z);
    }

    public Vec3d getRelativePos()
    {
        return relativePos;
    }
}
