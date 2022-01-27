package com.somebody.idlframewok.item.consumables.keys;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemIceShard extends ItemBlockConverterBase {

    public ItemIceShard(String name, int range) {
        super(name, range);
    }

    public boolean handleBlock(World world, BlockPos pos, Block block) {
        if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
            if (!world.isRemote) {
                world.setBlockState(pos, Blocks.ICE.getDefaultState());
            }
            return true;
        } else if (block == Blocks.FIRE) {
            if (!world.isRemote) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
            return true;
        } else if (block == Blocks.MAGMA) {
            if (!world.isRemote) {
                world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
            }
            return true;
        } else if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
            if (!world.isRemote) {
                world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
            }
            return true;
        } else {
            return false;
        }
    }

}
