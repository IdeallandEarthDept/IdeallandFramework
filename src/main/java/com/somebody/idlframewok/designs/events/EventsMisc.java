package com.somebody.idlframewok.designs.events;

import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsMisc {
    @SubscribeEvent
    public static void onCreatureHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        if (world.isRemote)
        {
            return;
        }

        if (hurtOne.getActivePotionEffect(ModPotions.INVINCIBLE) != null)
        {
            evt.setCanceled(true);
            return;
        }

        Entity trueSource = evt.getSource().getTrueSource();
        if (trueSource instanceof EntityLivingBase) {
            EntityLivingBase sourceCreature = (EntityLivingBase) trueSource;
            //blood paper(not potion related)
            ItemStack stackInHand = sourceCreature.getHeldItemMainhand();

            if (sourceCreature instanceof EntityPlayer &&
                    stackInHand.getItem() == Items.PAPER) {
                EntityPlayer player = (EntityPlayer) sourceCreature;
                stackInHand.shrink(1);

                player.addItemStackToInventory(new ItemStack(ModItems.PAPER_BLOOD));
            }
        }
    }
}
