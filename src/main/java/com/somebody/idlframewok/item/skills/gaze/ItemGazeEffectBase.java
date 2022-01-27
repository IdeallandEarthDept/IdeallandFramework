package com.somebody.idlframewok.item.skills.gaze;

import com.somebody.idlframewok.item.skills.ItemSkillEye;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public class ItemGazeEffectBase extends ItemSkillEye {

    public Potion potion;

    public ItemGazeEffectBase(String name, Potion potion) {
        super(name);
        //standard_dmg = 1f;
        this.potion = potion;
        showDuraDesc = true;
    }

    public float getDura(ItemStack stack)
    {
        SkillEyeArgs args = new SkillEyeArgs(stack);
        return getDura(args.timePlus + 1 + args.level / 5);
    }
    public float getVal(ItemStack stack)
    {
        SkillEyeArgs args = new SkillEyeArgs(stack);
        return getVal(args.damagePlus + args.level);
    }
//    public float getCoolDown(ItemStack stack) {
//        float result = -(IDLSkillNBT.getLevel(stack) - 1) * cool_down_reduce_per_lv + cool_down;
//        return result > 0.1f ? result : 0.1f; }

    @Override
    public void applyingEffects(EntityLivingBase source, EntityLivingBase target, SkillEyeArgs args, float modifier) {
        EntityUtil.ApplyBuff(target, potion, args.buffPlus, getDura(args.timePlus + 1 + args.level / 5) * modifier);
        //EntityUtil.ApplyBuff(target, MobEffects.MINING_FATIGUE, args.buffPlus, standard_dura * modifier);

        float dmg = getVal(args.damagePlus + args.level);
        if (dmg > 0)
        {
            target.attackEntityFrom(EntityUtil.attack(source).setMagicDamage(), dmg);
        }
    }

    @Override
    public DamageSource getDamageType(EntityLivingBase source, EntityLivingBase target, SkillEyeArgs args, float modifier)
    {
        if (potion == ModPotions.BURN)
        {
            return EntityUtil.attack(source).setMagicDamage().setFireDamage();
        }

        return super.getDamageType(source, target, args, modifier);
    }

}
