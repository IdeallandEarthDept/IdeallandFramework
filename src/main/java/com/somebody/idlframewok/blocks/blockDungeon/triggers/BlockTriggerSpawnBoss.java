package com.somebody.idlframewok.blocks.blockDungeon.triggers;

import com.somebody.idlframewok.entity.creatures.mobs.boss.EntityBossBase;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTriggerSpawnBoss extends BlockTriggerSpawn {
    public BlockTriggerSpawnBoss(String name, Material material) {
        super(name, material);
    }

    //this will restrict to only one boss per room.

    @Override
    public void triggerAt(World worldIn, BlockPos pos) {
        if (!worldIn.isRemote) {
            if (EntityUtil.getEntitiesWithinAABB(worldIn, EntityBossBase.class, pos, 16f, EntityUtil.ALL_ALIVE).size() == 0) {
                super.triggerAt(worldIn, pos);
            }
        } else {
            super.triggerAt(worldIn, pos);
        }
    }
}
