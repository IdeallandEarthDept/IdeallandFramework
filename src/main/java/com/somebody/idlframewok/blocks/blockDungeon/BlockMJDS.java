package com.somebody.idlframewok.blocks.blockDungeon;

import com.somebody.idlframewok.blocks.BlockBase;
import net.minecraft.block.material.Material;

public class BlockMJDS extends BlockBase {
    public static final float JUMP_FACTOR_MJDS = (float) Math.sqrt(6);

    public BlockMJDS(String name, Material material) {
        super(name, material);
        setBlockUnbreakable();
        setJumpFactor(JUMP_FACTOR_MJDS);
    }
}
