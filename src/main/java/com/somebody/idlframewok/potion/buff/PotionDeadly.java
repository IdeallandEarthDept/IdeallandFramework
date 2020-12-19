package com.somebody.idlframewok.potion.buff;

import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nonnull;

public class PotionDeadly extends BasePotion {


    public PotionDeadly(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    @Override
    public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
        //do nothing
    }
}
