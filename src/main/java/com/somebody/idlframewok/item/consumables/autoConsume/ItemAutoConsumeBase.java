package com.somebody.idlframewok.item.consumables.autoConsume;

import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAutoConsumeBase extends ItemBase {
    public ItemAutoConsumeBase(String name) {
        super(name);
    }

    public void onConsume(ItemStack stack, World worldIn, EntityLivingBase entityIn, int itemSlot, boolean isSelected)
    {
        //do something
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        onConsume(stack, worldIn, (EntityLivingBase) entityIn, itemSlot, isSelected);

        stack.shrink(1);
    }
}
