package com.somebody.idlframewok.item.misc.drop;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDropHeart extends ItemDropBase {
    public ItemDropHeart(String name, float amount) {
        super(name, amount);
    }

    @Override
    public void onConsume(ItemStack stack, World worldIn, EntityPlayer entityIn) {
        entityIn.heal(amount);
    }
}
