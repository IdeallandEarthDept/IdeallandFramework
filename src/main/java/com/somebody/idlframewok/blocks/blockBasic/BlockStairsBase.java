package com.somebody.idlframewok.blocks.blockBasic;

import com.somebody.idlframewok.init.RegistryHandler;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockStairsBase extends BlockStairs implements IHasModel {
    public BlockStairsBase(String name, Block base) {
        super(base.getDefaultState());
        CommonFunctions.init(this, name);
        RegistryHandler.addToItems(this);
    }
}
