package com.somebody.idlframewok.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ModEnchantmentGoldShield extends ModEnchantmentBase {
    public ModEnchantmentGoldShield(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(name, rarityIn, typeIn, slots);
    }

    //todo: consumes item in your invetory to prevent damage

//    private ItemStack findAmmo(EntityPlayer player, Item item)
//    {
//        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
//        {
//            return player.getHeldItem(EnumHand.OFF_HAND);
//        }
//        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
//        {
//            return player.getHeldItem(EnumHand.MAIN_HAND);
//        }
//        else
//        {
//            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
//            {
//                ItemStack itemstack = player.inventory.getStackInSlot(i);
//
//                if (this.isArrow(itemstack))
//                {
//                    return itemstack;
//                }
//            }
//
//            return ItemStack.EMPTY;
//        }
//    }
}
