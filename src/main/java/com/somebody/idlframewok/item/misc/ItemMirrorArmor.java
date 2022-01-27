package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.enchantments.ModEnchantmentInit;
import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class ItemMirrorArmor extends ItemBase {
    public ItemMirrorArmor(String name) {
        super(name);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (!playerIn.world.isRemote) {
            for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
                if (slot != EntityEquipmentSlot.MAINHAND && slot != EntityEquipmentSlot.OFFHAND) {
                    ItemStack newStack = target.getItemStackFromSlot(slot);
                    if (!newStack.isEmpty() && newStack.getItem().isDamageable()) {
                        ItemStack curWearing = playerIn.getItemStackFromSlot(slot);
                        if (!curWearing.isEmpty()) {
                            PlayerUtil.giveToPlayer(playerIn, curWearing);
                            playerIn.setItemStackToSlot(slot, ItemStack.EMPTY);
                        }

                        if (ModEnchantmentInit.FADING.getEnchantmentLevel(stack) == 0)
                        {
                            newStack.addEnchantment(ModEnchantmentInit.FADING, 1);
                        }

                        playerIn.setItemStackToSlot(slot, newStack);
                    }
                }
            }

            playerIn.getCooldownTracker().setCooldown(this, TICK_PER_SECOND);
        }

        return true;
    }
}
