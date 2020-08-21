package com.deeplake.idealland.item.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionBase extends Potion {

    //todo: add a registerSpawnList for this

    public PotionBase(String name, boolean badEffect, int color, int iconIndex) {
        super(badEffect, color);
        setRegistryName(name);
        setPotionName("idealland.potion." + name);
    }

    public boolean hasEffect(EntityLivingBase entity) {
        return hasEffect(entity, this);
    }

    public boolean hasEffect(EntityLivingBase entity, Potion potion) {
        return entity.getActivePotionEffect(potion) != null;
    }


}
