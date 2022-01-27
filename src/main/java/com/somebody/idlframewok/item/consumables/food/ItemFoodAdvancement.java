package com.somebody.idlframewok.item.consumables.food;

import com.somebody.idlframewok.advancements.ModAdvancementsInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

//eating will grant an advancement
public class ItemFoodAdvancement extends ItemFoodBase {
    String advancement;

    public ItemFoodAdvancement(String name, String advancement, int amount, float saturation, boolean isWolfFood) {
        super(name, amount, saturation, isWolfFood);
        setAlwaysEdible();
        this.advancement = advancement;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);
        if (!worldIn.isRemote) {
            ModAdvancementsInit.giveAdvancement(player, advancement);
        }
    }
}
