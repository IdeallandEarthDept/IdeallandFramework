package com.somebody.idlframewok.item.misc.drop;

import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemDropBase extends ItemBase {
    public final float amount;
    public ItemDropBase(String name, float amount) {
        super(name);
        this.amount = amount;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && entityIn instanceof EntityPlayer)
        {
            onConsume(stack,worldIn, (EntityPlayer) entityIn);
            stack.shrink(1);
        }
    }

    public abstract void onConsume(ItemStack stack, World worldIn, EntityPlayer entityIn);
}
