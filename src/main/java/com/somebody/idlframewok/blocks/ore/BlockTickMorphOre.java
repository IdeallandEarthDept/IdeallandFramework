package com.somebody.idlframewok.blocks.ore;

import com.somebody.idlframewok.item.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockTickMorphOre extends BlockPhasingOre {
    public BlockTickMorphOre(String name) {
        super(name);
    }

    //Moon phase? Thunder ? Rain ? Anything by time.
    public abstract boolean shouldActivate(World worldIn, BlockPos pos, IBlockState state);

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        tryChange(worldIn, pos, state);
        super.randomTick(worldIn, pos, state, random);
    }

    public void tryChange(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            if (shouldActivate(worldIn, pos, state)) {
                if (!isActive(state)) {
                    worldIn.setBlockState(pos, state.withProperty(PHASE_STATE, PHASE_ENABLED));
                }
            } else {
                if (isActive(state)) {
                    worldIn.setBlockState(pos, state.withProperty(PHASE_STATE, PHASE_ENABLED));
                }
            }
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        tryChange(worldIn, pos, state);
        super.onBlockAdded(worldIn, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (state.getBlock() == this && isActive(state)) {
//            return ModItems.RAIN_RUNE;
        }
        //If you call this with a non-this-block state, will cause dead-loop if unhandled!
        return super.getItemDropped(state, rand, fortune);
    }
}
