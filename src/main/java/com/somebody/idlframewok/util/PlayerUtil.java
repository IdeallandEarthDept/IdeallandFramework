package com.somebody.idlframewok.util;

import com.somebody.idlframewok.Idealland;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;

public class PlayerUtil {

    public static int FindItemInIvtrGeneralized(EntityPlayer player, Class<? extends Item> itemClass)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            {
                //itemClass.getClass();
                if (itemClass.isAssignableFrom(itemstack.getItem().getClass()))
                {
                    return i;
                }
            }
        }
        return -1;
    }

    public static ItemStack FindStackInIvtrGeneralized(EntityPlayer player, Class<? extends Item> itemClass)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            {
                //itemClass.getClass();
                if (itemClass.isAssignableFrom(itemstack.getItem().getClass()))
                {
                    return itemstack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public static int FindItemInIvtr(EntityPlayer player, Item item)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            {
                if (itemstack.getItem() == item)
                {
                    return i;
                }
            }
        }
        return -1;
    }

    public static ItemStack FindStackInIvtr(EntityPlayer player, Item item)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            {
                if (itemstack.getItem() == item)
                {
                    return itemstack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public static boolean isCreative(EntityPlayer player)
    {
        return player.capabilities.isCreativeMode;
    }

    public static void giveToPlayer(EntityPlayer player, Item item, int count) {
        giveToPlayer(player, item, 0, count);
    }

    public static void giveToPlayer(EntityPlayer player, Item item, int meta, int count) {
        int max = item.getItemStackLimit();
        while (count > max) {
            ItemStack stack = new ItemStack(item, max, meta);
            if (!player.addItemStackToInventory(stack)) {
                player.dropItem(stack, false);
            }
            count -= max;
        }

        ItemStack stack = new ItemStack(item, count, meta);
        if (!player.addItemStackToInventory(stack)) {
            player.dropItem(stack, false);
        }
    }

    public static boolean giveToPlayer(EntityPlayer player, ItemStack stack)
    {
        boolean result = player.addItemStackToInventory(stack);
        if (!result)
        {
            player.dropItem(stack, false);
        }
        return result;
    }

    public static void TryGrantAchv(EntityPlayer player, String key)
    {
        if (player instanceof EntityPlayerMP)
        {
            EntityPlayerMP playerMP = ((EntityPlayerMP) player);
            Advancement advancement = playerMP.getServerWorld().getAdvancementManager().getAdvancement(new ResourceLocation(Idealland.MODID, key));

            //String achvName = GetAchvName(key);
            //playerMP.getStatFile().unlockAchievement(this.gameController.player, statbase, k);
        }

        //todo
    }

    public static void setCoolDown(EntityPlayer player, EnumHand hand)
    {
        player.getCooldownTracker().setCooldown(player.getHeldItem(hand).getItem(), CommonDef.TICK_PER_SECOND);
    }

    //not intended to decrease
    public static boolean addFoodLevel(EntityPlayer player, int value)
    {
        FoodStats stats = player.getFoodStats();
        if (stats.needFood())
        {
            stats.setFoodLevel(stats.getFoodLevel() + value);
            return true;
        }
        else {
            return false;
        }
    }
}
