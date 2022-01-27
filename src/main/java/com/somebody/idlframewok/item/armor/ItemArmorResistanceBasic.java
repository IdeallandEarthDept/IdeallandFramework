package com.somebody.idlframewok.item.armor;

import com.somebody.idlframewok.designs.EnumDamageResistance;
import com.somebody.idlframewok.designs.EnumDamageType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemArmorResistanceBasic extends ItemArmorResistance {
    EnumDamageType damageType;
    EnumDamageResistance resistance;
    public ItemArmorResistanceBasic(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, EnumDamageType enumDamageType, EnumDamageResistance resistance) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        this.damageType = enumDamageType;
        this.resistance = resistance;
    }

    @Override
    public EnumDamageResistance getResistance(EnumDamageType enumDamageType) {
        if (enumDamageType == damageType)
        {
            return resistance;
        }
        return super.getResistance(enumDamageType);
    }
}
