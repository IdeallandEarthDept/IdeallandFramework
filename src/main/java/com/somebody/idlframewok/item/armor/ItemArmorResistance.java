package com.somebody.idlframewok.item.armor;

import com.somebody.idlframewok.designs.EnumDamageResistance;
import com.somebody.idlframewok.designs.EnumDamageType;
import com.somebody.idlframewok.item.ItemArmorBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;

public class ItemArmorResistance extends ItemArmorBase {
    public ItemArmorResistance(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
    }

    public EnumDamageResistance getResistance(DamageSource source)
    {
        return EnumDamageResistance.NONE;
    }

    public EnumDamageResistance getResistance(EnumDamageType enumDamageType)
    {
        return EnumDamageResistance.NONE;
    }
}
