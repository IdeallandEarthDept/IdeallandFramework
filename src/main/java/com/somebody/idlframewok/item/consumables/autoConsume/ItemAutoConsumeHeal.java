package com.somebody.idlframewok.item.consumables.autoConsume;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAutoConsumeHeal extends ItemAutoConsumeBase {
    float healAmount = 2f;
    public ItemAutoConsumeHeal(String name) {
        super(name);
    }

    @Override
    public void onConsume(ItemStack stack, World worldIn, EntityLivingBase entityIn, int itemSlot, boolean isSelected) {
        super.onConsume(stack, worldIn, entityIn, itemSlot, isSelected);
        entityIn.heal(healAmount);
    }
}
