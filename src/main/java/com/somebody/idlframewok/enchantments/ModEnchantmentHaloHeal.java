package com.somebody.idlframewok.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.Potion;

public class ModEnchantmentHaloHeal extends ModEnchantmentHalo {
    public ModEnchantmentHaloHeal(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots, Potion potion) {
        super(name, rarityIn, typeIn, slots, potion);
    }

    public void applyEffect(EntityLivingBase source, EntityLivingBase target)
    {
        target.heal(getLevelOnCreature(source));
    }
}
