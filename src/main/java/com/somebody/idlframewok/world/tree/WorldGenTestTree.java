package com.somebody.idlframewok.world.tree;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

//just gen it in worldGenNew
public class WorldGenTestTree extends WorldGenAbstractTree {
    public WorldGenTestTree(boolean notify) {
        super(notify);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        return false;
    }
}
