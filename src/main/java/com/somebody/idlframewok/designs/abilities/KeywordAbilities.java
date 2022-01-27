package com.somebody.idlframewok.designs.abilities;

import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class KeywordAbilities {
    public boolean checkIntimidation(EntityLivingBase hurtOne, Entity attacker) {
        if (attacker instanceof EntityLivingBase) {
            ItemStack stack = ((EntityLivingBase) attacker).getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            if (stack.getItem() == ModItems.INTIMIDATE_MASK) {
                return EntityUtil.isAlone(hurtOne.getEntityWorld(), hurtOne, 8f);
            }
        }

        return true;
    }

    @SubscribeEvent
    public void onKnockBack(LivingKnockBackEvent event) {
        EntityLivingBase hurtOne = event.getEntityLiving();
        if (checkIntimidation(hurtOne, event.getOriginalAttacker())) {
            event.setCanceled(true);
        }
    }
}
