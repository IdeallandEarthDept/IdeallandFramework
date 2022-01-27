package com.somebody.idlframewok.blocks.blockDungeon.traps;

import com.somebody.idlframewok.init.ModConfig;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockDungeonTrapFlame extends BlockDungeonTrapBase {
    float seconds = 2f;
    public BlockDungeonTrapFlame(String name, Material material, float damage) {
        super(name, material);
        this.seconds = damage;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);

        if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase hurtOne = (EntityLivingBase) entityIn;
            if (!ModConfig.GeneralConf.TRAP_HURT_INTACT_MOB &&
                    hurtOne instanceof EntityLiving && hurtOne.getHealth() >= hurtOne.getMaxHealth())
            {
                //wont hurt intact mobs
                return;
            }

            entityIn.setFire((int) seconds);
            Vec3d pos1 = entityIn.getPositionVector().addVector(0.5-((EntityLivingBase) entityIn).getRNG().nextFloat(),
                    0,
                    0.5-((EntityLivingBase) entityIn).getRNG().nextFloat());
            worldIn.spawnParticle(EnumParticleTypes.FLAME, pos1.x, pos1.y, pos1.z, 0,1,0);

            worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 2f);
        }

    }
}
