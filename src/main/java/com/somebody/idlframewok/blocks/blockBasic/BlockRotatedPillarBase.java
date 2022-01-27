package com.somebody.idlframewok.blocks.blockBasic;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.init.RegistryHandler;
import com.somebody.idlframewok.util.IHasModel;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;

public class BlockRotatedPillarBase extends BlockRotatedPillar implements IHasModel {
    public BlockRotatedPillarBase(String name, Material materialIn) {
        super(materialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);

        ModBlocks.BLOCKS.add(this);
        addToItems();
    }

    public void addToItems()
    {
        RegistryHandler.addToItems(this);
    }
}
