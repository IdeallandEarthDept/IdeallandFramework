package com.somebody.idlframewok.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.Potion;

public class ModEnchantmentHaloFlame extends ModEnchantmentHalo {
    public ModEnchantmentHaloFlame(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots, Potion potion) {
        super(name, rarityIn, typeIn, slots, potion);
    }

    public void applyEffect(EntityLivingBase source, EntityLivingBase target)
    {
        target.setFire(2);
    }
}
