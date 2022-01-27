package com.somebody.idlframewok.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ModEnchantmentProtectionClassical extends ModEnchantmentBase {
    public ModEnchantmentProtectionClassical(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(name, rarityIn, typeIn, slots);
        setValue(0.06f,0.06f);
    }
}
