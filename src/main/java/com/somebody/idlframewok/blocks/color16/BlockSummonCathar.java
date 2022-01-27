package com.somebody.idlframewok.blocks.color16;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSummonCathar extends BlockRuneStoneBase {
    public BlockSummonCathar(String name, Material material) {
        super(name, material);
        setTickRandomly(true);
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(worldIn, pos, state, random);
    }
}
