package com.somebody.idlframewok.blocks.ore;

import com.somebody.idlframewok.item.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRainOre extends BlockTickMorphOre {
    public BlockRainOre(String name) {
        super(name);
        setTickRandomly(true);
    }

    @Override
    public boolean shouldActivate(World worldIn, BlockPos pos, IBlockState state) {
        return worldIn.isRaining() && worldIn.getBiome(pos).canRain();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (state.getBlock() == this && isActive(state)) {
            return ModItems.RAIN_RUNE;
        }
        //If you call this with a non-this-block state, will cause dead-loop if unhandled!
        return super.getItemDropped(state, rand, fortune);
    }
}
