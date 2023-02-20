package com.somebody.idlframewok.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class EffectTuple
{
    public float chance = 0.1f;
    public Potion potion = MobEffects.ABSORPTION;
    public int length = 200;

    public EffectTuple(float chance, Potion potion) {
        this(chance, potion, 200);
    }

    public EffectTuple(float chance, Potion potion, int length) {
        this.chance = chance;
        this.potion = potion;
        this.length = length;
    }

    public void AttemptBuffWithLevel(EntityLivingBase livingBase, int level)
    {
        if (livingBase.getRNG().nextFloat() < chance)
        {
            ApplyBuffWithLevel(livingBase, level);
        }
    }

    public void ApplyBuffWithLevel(EntityLivingBase livingBase, int level)
    {
        livingBase.addPotionEffect(new PotionEffect(potion, length, level, false, false));
    }

}
