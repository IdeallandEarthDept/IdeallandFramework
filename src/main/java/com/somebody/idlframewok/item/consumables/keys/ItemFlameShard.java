package com.somebody.idlframewok.item.consumables.keys;

import com.somebody.idlframewok.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFlameShard extends ItemBlockConverterBase {

    public ItemFlameShard(String name, int range) {
        super(name, range);
    }

    public boolean handleBlock(World world, BlockPos pos, Block block) {
        if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
            if (!world.isRemote) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
            return true;
        } else if (block == Blocks.ICE || block == Blocks.FROSTED_ICE || block == Blocks.PACKED_ICE) {
            if (!world.isRemote) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
            return true;
        } else if (block == ModBlocks.INDES_ICE) {
            if (!world.isRemote) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
            return true;
        } else if (block == Blocks.LOG) {
            if (!world.isRemote) {
                world.setBlockState(pos, Blocks.COAL_ORE.getDefaultState());
            }
            return true;
        } else {
            return false;
        }
    }

}
