package com.somebody.idlframewok.blocks.blockDungeon;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockLockBase extends BlockBase {

    public boolean isOn;
    BlockLockBase alternative;
    IBlockState stateUnlocked = Blocks.GLASS.getDefaultState();

    public BlockLockBase(String name, Material material, boolean isOn) {
        super(name, material);
        this.isOn = isOn;
        setLightLevel(isOn ? 0.5f : 0.0f);
        setHardness(-1);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);
    }

    public BlockLockBase setAlternative(BlockLockBase blockLockBase)
    {
        alternative = blockLockBase;
        blockLockBase.alternative = this;
        return this;
    }

    public abstract boolean isOk(World world, BlockPos pos);

    public void invertStatus(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockLockBase)
        {
            boolean isOnLocal = ((BlockLockBase) state.getBlock()).isOn;
            ((BlockLockBase) state.getBlock()).setOn(!isOnLocal, world, pos);
        }
    }

    public void setOn(boolean on, World world, BlockPos pos)
    {
        //Idealland.Log("Trying to set %s to %s", pos, on);
        Block blockLock = world.getBlockState(pos).getBlock();
        if (blockLock instanceof BlockLockBase)
        {
            if (((BlockLockBase) blockLock).isOn != on) {
                world.setBlockState(pos, alternative.getDefaultState());
            }
        }
    }

    public void unlock(World world, BlockPos pos)
    {
        world.setBlockState(pos, stateUnlocked);
    }


}
