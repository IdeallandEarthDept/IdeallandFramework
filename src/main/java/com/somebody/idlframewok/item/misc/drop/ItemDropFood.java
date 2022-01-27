package com.somebody.idlframewok.item.misc.drop;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDropFood extends ItemDropBase {
    public ItemDropFood(String name, float amount) {
        super(name, amount);
    }

    @Override
    public void onConsume(ItemStack stack, World worldIn, EntityPlayer entityIn) {
        entityIn.getFoodStats().addStats((int) amount, amount);
    }
}
