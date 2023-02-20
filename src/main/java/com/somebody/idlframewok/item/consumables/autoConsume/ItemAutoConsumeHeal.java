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
    public void OnConsume(ItemStack stack, World worldIn, EntityLivingBase entityIn, int itemSlot, boolean isSelected) {
        super.OnConsume(stack, worldIn, entityIn, itemSlot, isSelected);
        entityIn.heal(healAmount);
    }
}
