package com.somebody.idlframewok.blocks.blockDungeon.gargoyle;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.enumGroup.EnumHarvestLevel;
import net.minecraft.block.material.Material;

public class BlockGargoyleBase extends BlockBase {
    public BlockGargoyleBase(String name, Material material) {
        super(name, material);
        setHardness(1000f);
        setHarvestThis(IDLNBTDef.TOOL_PICKAXE, EnumHarvestLevel.IRON.level);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);
    }
}
