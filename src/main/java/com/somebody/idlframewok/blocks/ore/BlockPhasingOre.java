package com.somebody.idlframewok.blocks.ore;

import com.somebody.idlframewok.blocks.blockBasic.BlockOreBase;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPhasingOre extends BlockOreBase {
    public static final PropertyInteger PHASE_STATE = PropertyInteger.create(IDLNBTDef.STATE, 0, 1);

    public static final int PHASE_HIDDEN = 0;
    public static final int PHASE_ENABLED = 1;

    public IBlockState PRETEND_BLOCK = Blocks.STONE.getDefaultState();
    public IBlockState ACTIVE_BLOCK = Blocks.IRON_ORE.getDefaultState();

    //If the ACTIVE_BLOCK is just a dummy, not deciding the drop.
    boolean ignoreActiveBlockDrop = true;

    public BlockPhasingOre(String name) {
        super(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(PHASE_STATE, 0));

    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return getBlockAfterPretend(blockState).getBlockHardness(getStateAfterPretend(blockState), worldIn, pos);
    }

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public int getMetaFromState(IBlockState state) {
        return state.getValue(PHASE_STATE);
    }

    boolean isActive(IBlockState state) {
        return state.getValue(PHASE_STATE) == PHASE_ENABLED;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, PHASE_STATE);
    }

    public IBlockState getStateAfterPretend(IBlockState state) {
        if (state.getBlock() == this) {
            if (state.getValue(PHASE_STATE) == PHASE_HIDDEN) {
                return PRETEND_BLOCK;
            } else {
                return ACTIVE_BLOCK;
            }
        } else {
            return state;
        }
    }

    public Block getBlockAfterPretend(IBlockState state) {
        if (state.getBlock() == this) {
            if (state.getValue(PHASE_STATE) == PHASE_HIDDEN) {
                return PRETEND_BLOCK.getBlock();
            } else {
                return ACTIVE_BLOCK.getBlock();
            }
        } else {
            return state.getBlock();
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (state.getBlock() == this) {
            if (state.getValue(PHASE_STATE) == PHASE_ENABLED && ignoreActiveBlockDrop) {
                return ItemStack.EMPTY.getItem();
            }
            return getBlockAfterPretend(state).getItemDropped(getStateAfterPretend(state), rand, fortune);
        }
        //If you call this with a non-this-block state, will cause dead-loop if unhandled!
        return super.getItemDropped(state, rand, fortune);
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (state.getBlock() == this) {
            if (state.getValue(PHASE_STATE) == PHASE_ENABLED && ignoreActiveBlockDrop) {
                //manual drop
                spawnAsEntity(worldIn, pos, new ItemStack(getItemDropped(state, worldIn.rand, fortune), 1, this.damageDropped(state)));
                return;
            }
            getBlockAfterPretend(state).dropBlockAsItemWithChance(worldIn, pos, getStateAfterPretend(state), chance, fortune);
        } else {
            super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
        }
    }

//    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
//    {
//        Random rand = world instanceof World ? ((World)world).rand : RANDOM;
//
//        int count = quantityDropped(state, fortune, rand);
//        for (int i = 0; i < count; i++)
//        {
//            Item item = this.getItemDropped(state, rand, fortune);
//            if (item != Items.AIR)
//            {
//                drops.add(new ItemStack(item, 1, this.damageDropped(state)));
//            }
//        }
//    }

    //    @Override
//    public int quantityDropped(Random random) {
//        return getBlockAfterPretend(state).quantityDropped(random);
//    }
//
//    @Override
//    public int quantityDroppedWithBonus(int fortune, Random random) {
//        return getBlockAfterPretend(state).quantityDroppedWithBonus(fortune, random);
//    }
}
