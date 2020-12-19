package com.somebody.idlframewok.item.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class PotionBase extends Potion {

    //todo: add a registerSpawnList for this

    public PotionBase(String name, boolean badEffect, int color, int iconIndex) {
        super(badEffect, color);
        setRegistryName(name);
        setPotionName("idlframewok.potion." + name);
    }

    public boolean hasEffect(EntityLivingBase entity) {
        return hasEffect(entity, this);
    }

    public boolean hasEffect(EntityLivingBase entity, Potion potion) {
        return entity.getActivePotionEffect(potion) != null;
    }


}
