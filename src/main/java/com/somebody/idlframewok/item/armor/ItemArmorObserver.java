package com.somebody.idlframewok.item.armor;

import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.item.ItemArmorBase;
import com.somebody.idlframewok.item.skills.classfit.IClassBooster;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemArmorObserver extends ItemArmorBase implements IClassBooster, IWIP {

    public ItemArmorObserver(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        logNBT = true;
    }
}
