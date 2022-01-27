package com.somebody.idlframewok.item.armor;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.item.ModItems;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.ItemStack;

public class ItemHorseArmor extends ItemBase {
    public ItemHorseArmor(String name) {
        super(name);
    }

    @Override
    public HorseArmorType getHorseArmorType(ItemStack stack) {
        return HorseArmorType.GOLD;
    }
}
