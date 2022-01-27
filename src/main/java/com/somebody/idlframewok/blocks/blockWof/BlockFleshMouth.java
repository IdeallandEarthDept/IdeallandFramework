package com.somebody.idlframewok.blocks.blockWof;

import com.somebody.idlframewok.designs.combat.ModDamageSourceList;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFleshMouth extends BlockFlesh {
    public BlockFleshMouth(String name, Material material) {
        super(name, material);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);
        if (worldIn.isRemote)
        {
            entityIn.attackEntityFrom(ModDamageSourceList.FLESH, 2f);
        }
    }
}
