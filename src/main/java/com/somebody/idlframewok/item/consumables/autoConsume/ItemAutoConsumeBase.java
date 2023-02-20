package com.somebody.idlframewok.item.consumables.autoConsume;

import com.somebody.idlframewok.item.consumables.ItemConsumableBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAutoConsumeBase extends ItemConsumableBase {
    public ItemAutoConsumeBase(String name) {
        super(name);
    }

    public void OnConsume(ItemStack stack, World worldIn, EntityLivingBase entityIn, int itemSlot, boolean isSelected)
    {
        //do something
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        OnConsume(stack, worldIn, (EntityLivingBase) entityIn, itemSlot, isSelected);

        stack.shrink(1);
    }
}
