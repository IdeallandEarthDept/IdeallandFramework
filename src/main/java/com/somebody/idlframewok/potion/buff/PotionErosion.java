package com.somebody.idlframewok.potion.buff;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class PotionErosion extends BaseSimplePotion {
    public PotionErosion(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public void performEffect(@Nonnull EntityLivingBase living, int amplified) {
        amplified ++;//buff starts from lv 0
        for (EntityEquipmentSlot slot:
                EntityEquipmentSlot.values()) {
            DamageItemInSlot(slot, living, amplified);
        }
    }

    public void DamageItemInSlot(EntityEquipmentSlot slot, EntityLivingBase livingBase, int amount)
    {
        ItemStack stack = livingBase.getItemStackFromSlot(slot);
        stack.damageItem(amount, livingBase);
    }

}
