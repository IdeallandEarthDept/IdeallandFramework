package com.somebody.idlframewok.entity.creatures.buildings;

import com.somebody.idlframewok.blocks.tileEntity.builder.builderAction.BuilderActionBase;
import com.somebody.idlframewok.entity.BuildingCore;
import com.somebody.idlframewok.entity.creatures.EntityModUnit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityIdlBuildingBase extends EntityModUnit {
    BuildingCore buildingCore;
    protected boolean suicide_after_finish = true;
    public EntityIdlBuildingBase(World worldIn) {
        super(worldIn);

        buildingCore = new BuildingCore(worldIn);
        InitTaskQueue();
        setAttr(1, 0, 0, 8, 10);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
        setBuilding();
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {


        return super.onInitialSpawn(difficulty, livingdata);
    }

    public Vec3d getCurBuildingVector()
    {
        BuilderActionBase action =  buildingCore.getCurAction();
        if (action == null)
        {
            return Vec3d.ZERO;
        }else {
            Vec3d result = action.getRelativePos();
            return result==null ? Vec3d.ZERO : result;
        }
    }

    void InitTaskQueue()
    {
        AddTaskBuild(getPosition().add(0,-1,0), Blocks.BRICK_BLOCK.getDefaultState());
        //AddTaskBuildWallWithBlockCentered(origin.add(-floorReach,2,0), 0, windowHeight, 1, windowMaterial);
    }

    public void AddTaskBuild(BlockPos pos, IBlockState newState) {
        buildingCore.AddTaskBuild(pos, newState, true);
    }

    public void AddTaskBuild(BlockPos pos, IBlockState newState, boolean isSafe) {
        buildingCore.AddTaskBuild(pos, newState, isSafe);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        buildingCore.update(this.getPosition());

        if (!world.isRemote && suicide_after_finish && buildingCore.GetProgress() >= 1f)
        {
            attackEntityFrom(DamageSource.OUT_OF_WORLD, getMaxHealth() * 100f);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        buildingCore.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound result = super.writeToNBT(compound);
        result = buildingCore.writeToNBT(result);
        return result;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }


}
