package com.somebody.idlframewok.blocks.tileEntity;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityTrafficLight extends TileEntity implements ITickable {
//    final static int HALF_PERIOD = ModConfig.DEBUG_CONF.TRAFFIC_LIGHT_PERIOD_HALF_SECOND * CommonDef.TICK_PER_SECOND;
//    final static int PERIOD = 2 * HALF_PERIOD;
    boolean needInit = true;

    final IBlockState STATE_X = ModBlocks.TRAFFIC_LIGHT.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.X);
    final IBlockState STATE_Z = ModBlocks.TRAFFIC_LIGHT.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Z);
    final IBlockState STATE_Y = ModBlocks.TRAFFIC_LIGHT.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);

    public static int getHalfPeriod() {
        return ModConfig.DEBUG_CONF.TRAFFIC_LIGHT_PERIOD_HALF_SECOND * CommonDef.TICK_PER_SECOND;
    }

    public static int getPERIOD() {
        return 2 * getHalfPeriod();
    }

    protected void setWorldCreate(World worldIn)
    {
        this.setWorld(worldIn);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        needInit = false;
    }

    void checkUpdate()
    {
        //Idealland.Log("Traffic light updating");
        long tick = world.getTotalWorldTime() % getPERIOD();
        int halfPeriod = getHalfPeriod();
        IBlockState state = world.getBlockState(pos);
        if (tick < CommonDef.TICK_PER_SECOND || (tick >= halfPeriod && tick < halfPeriod + CommonDef.TICK_PER_SECOND))
        {//yellow
            if (state != STATE_Y)
            {
                world.setBlockState(pos, STATE_Y);
            }
        } else if (tick < halfPeriod)
        {
            if (state != STATE_X)
            {
                world.setBlockState(pos, STATE_X);
            }
        }
        else {
            if (state != STATE_Z)
            {
                world.setBlockState(pos, STATE_Z);
            }
        }
    }

    @Override
    public void update() {
        if (!world.isRemote && CommonFunctions.isSecondTick(world))
        {
            long tick = world.getTotalWorldTime() % getPERIOD();
            int halfPeriod = getHalfPeriod();

            //keyframe optimize
            if (needInit ||tick == 0 || tick == halfPeriod ||
                    tick == CommonDef.TICK_PER_SECOND || tick == CommonDef.TICK_PER_SECOND + halfPeriod)
            {
                checkUpdate();
                needInit = false;
            }

        }
    }
}
