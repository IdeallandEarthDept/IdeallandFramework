package com.somebody.idlframewok.blocks.blockDungeon.traps;

import com.somebody.idlframewok.designs.combat.ModDamageSourceList;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDungeonTrapSpike extends BlockDungeonTrapBase {
    float damage = 2f;
    float posion = -1f;
    public BlockDungeonTrapSpike(String name, Material material, float damage) {
        super(name, material);
        this.damage = damage;
    }

    public BlockDungeonTrapSpike setPosion(float posion) {
        this.posion = posion;
        return this;
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

            if (hurtOne.hurtResistantTime > 0 || hurtOne.getIsInvulnerable())
            {
                return;
            }
            worldIn.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 1f, 2f);

            DamageSource damageSource = ModDamageSourceList.TRAP;
//            if (hurtOne.getItemStackFromSlot(EntityEquipmentSlot.FEET).isEmpty())
//            {
//                //you would better wear your boots.
//                damageSource.setDamageBypassesArmor();//not the correct way to handle the damage
//            }

            hurtOne.attackEntityFrom(damageSource, damage);
            if (posion > 0 && hurtOne.getActivePotionEffect(MobEffects.POISON) != null)
            {
                EntityUtil.ApplyBuff(hurtOne, MobEffects.POISON, 0, posion);
            }
        }

    }


}
