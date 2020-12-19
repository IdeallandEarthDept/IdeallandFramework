package com.somebody.idlframewok.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ModEnchantmentProtection extends ModEnchantmentBase {
    public ModEnchantmentProtection(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(name, rarityIn, typeIn, slots);
    }

    //damage *= this
    @Override
    public float getValue(int level) {
        return 1f / (1 + (float)level / 2f);
    }
}
