package com.somebody.idlframewok.blocks.blockBasic;

import com.somebody.idlframewok.blocks.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockSkylandPebble extends BlockBase {
    AxisAlignedBB AABB = new AxisAlignedBB(0.5-0.125D, 0.5-0.25D, 0.5-0.125D,0.5+0.125D, 0.5+0.25D, 0.5+0.125D);
    public BlockSkylandPebble(String name, Material material) {
        super(name, material);
        setLightOpacity(0);
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean causesSuffocation(IBlockState state)
    {
        return false;
    }

    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }

    @Deprecated
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
