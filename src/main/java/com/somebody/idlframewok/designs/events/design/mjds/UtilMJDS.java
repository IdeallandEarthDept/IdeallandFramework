package com.somebody.idlframewok.designs.events.design.mjds;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.blocks.blockDungeon.BlockMJDS;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UtilMJDS {
    public static BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity) {
        return new BlockPos(entity.posX, entity.getEntityBoundingBox().minY - 0.5000001D, entity.posZ);
    }

    public static boolean isInMJDS(Entity entity) {
        Block block = entity.world.getBlockState(getBlockPosBelowThatAffectsMyMovement(entity)).getBlock();
        return block instanceof BlockMJDS;
    }

    public static boolean isWithOffsetMJDS(World world, BlockPos pos, Entity entity) {
        return world.getBlockState(getBlockPosBelowThatAffectsMyMovement(entity).add(pos)).getBlock() instanceof BlockMJDS;
    }

    public static float getJumpFactor(IBlockState state) {
        Block blockBase = state.getBlock();
        if (blockBase instanceof BlockBase) {
            return ((BlockBase) blockBase).getJumpFactor();
        }
        return 1f;
    }
}
