package com.somebody.idlframewok.item.consumables.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDigestFood extends ItemFoodBase {
    public ItemDigestFood(String name, int amount, float saturation, boolean isWolfFood) {
        super(name, amount, saturation, isWolfFood);
        setAlwaysEdible();
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        super.onFoodEaten(stack, worldIn, player);
        if (!worldIn.isRemote)
        {
            player.getFoodStats().setFoodLevel(10);
        }
    }
}
