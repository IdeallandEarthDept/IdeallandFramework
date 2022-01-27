package com.somebody.idlframewok.blocks.blockDungeon;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITriggerable {
    void triggerAt(World world, BlockPos pos);
}
