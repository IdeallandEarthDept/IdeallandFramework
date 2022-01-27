package com.somebody.idlframewok.blocks.blockDungeon;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockLockAdjacentPassive extends BlockLockAdjacent {
    public BlockLockAdjacentPassive(String name, Material material) {
        super(name, material, true);
        alternative = this;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return false;
    }

    public void invertStatus(World world, BlockPos pos)
    {
        return;
    }

    public void setOn(boolean on, World world, BlockPos pos)
    {
        return;
    }
}
