package com.somebody.idlframewok.blocks.blockDungeon.traps;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import net.minecraft.block.material.Material;

public class BlockDungeonTrapBase extends BlockBase {
    //note that those blocks won't hurt you in peaceful difficulties
    public BlockDungeonTrapBase(String name, Material material) {
        super(name, material);
        setCreativeTab(ModCreativeTabsList.IDL_DUNGEON);
    }
}
