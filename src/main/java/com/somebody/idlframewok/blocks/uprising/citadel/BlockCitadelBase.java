package com.somebody.idlframewok.blocks.uprising.citadel;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockCitadelBase extends BlockBase {
    public BlockCitadelBase(String name, Material material) {
        super(name, material);
        setHardness(-1f);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);
    }

    public abstract void tryActivate(EntityPlayer player, World world, BlockPos pos);
}
