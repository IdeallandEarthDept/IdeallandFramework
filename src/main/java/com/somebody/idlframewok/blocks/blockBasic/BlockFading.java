package com.somebody.idlframewok.blocks.blockBasic;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class BlockFading extends BlockBase {
    public BlockFading(String name, Material material) {
        super(name, material);
        setTickRandomly(true);
    }

    @Override
    public int tickRate(World worldIn) {
        return TICK_PER_SECOND;
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(worldIn, pos, state, random);
        if (!worldIn.isRemote)
        //while (random.nextFloat() < 0.1f)
        {
            WorldGenUtil.setBlockState(worldIn, pos, WorldGenUtil.AIR);
        }
    }
}
