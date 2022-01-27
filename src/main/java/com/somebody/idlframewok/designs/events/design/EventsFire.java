package com.somebody.idlframewok.designs.events.design;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventsFire {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (world.isRemote || evt.isCanceled())
        {
            return;
        }

        //Flame lit
        EntityLivingBase hurtOne = evt.getEntityLiving();
        Entity trueSource = evt.getSource().getTrueSource();
        if (trueSource instanceof EntityLivingBase) {
            EntityLivingBase sourceCreature = (EntityLivingBase) trueSource;
            ItemStack stackInHand = sourceCreature.getHeldItemMainhand();
            Item itemInHand = stackInHand.getItem();
            if (itemInHand == Item.getItemFromBlock(Blocks.TORCH) ||
                    itemInHand == Items.FLINT_AND_STEEL) {
                hurtOne.setFire(3);
            }
        }
    }
}
