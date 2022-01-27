package com.somebody.idlframewok.blocks.blockDungeon.gargoyle;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGargoyleBody extends BlockGargoyleBase {

    public BlockGargoyleBody(String name, Material material) {
        super(name, material);
    }

    public void trigger(World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
            return;
        }

        BlockPos.MutableBlockPos pointer = new BlockPos.MutableBlockPos(pos);
        while (true) {
            pointer.move(EnumFacing.UP);
            IBlockState state = world.getBlockState(pointer);
            if (state.getBlock() instanceof BlockGargoyleBody) {
                //continue until a head is found
            } else if (state.getBlock() instanceof BlockGargoyleHead) {
                BlockGargoyleHead headBlock = (BlockGargoyleHead) state.getBlock();
                headBlock.trigger(world, pointer);
                break;
            } else {
                //do nothing
                break;
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (!worldIn.isRemote) {
            if (worldIn.isBlockPowered(pos)) {
                trigger(worldIn, pos);
            }
        }
    }

//    @Override
//    public boolean getWeakChanges(IBlockAccess world, BlockPos pos) {
//        return true;
//    }
//
//    @Override
//    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
//        super.onNeighborChange(world, pos, neighbor);
//    }
}
