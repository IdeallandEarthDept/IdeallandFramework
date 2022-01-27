package com.somebody.idlframewok.blocks.color16;

import com.somebody.idlframewok.blocks.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPantheon extends BlockBase {
    public BlockPantheon(String name, Material material) {
        super(name, material);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);
        if (entityIn instanceof EntityLivingBase)
        {
            ((EntityLivingBase) entityIn).getActivePotionEffects().clear();
        }
    }
}
