package com.somebody.idlframewok.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEnchantmentPact extends ModEnchantmentBase {
    Class<?extends EntityLivingBase> clazz;

    public ModEnchantmentPact(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots, Class<?extends EntityLivingBase> clazz) {
        super(name, rarityIn, typeIn, slots);
        this.clazz = clazz;
        //CommonFunctions.addToEventBus(this);
    }

    //plan is discarded as the event is not cancelable.

    @SubscribeEvent
    public void onSetAttackTarget(LivingSetAttackTargetEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();
        World world = livingBase.getEntityWorld();

        if (world.isRemote || event.getTarget() == null || event.isCanceled())
        {
            return;
        }

        if (appliedOnCreature(event.getTarget()) && clazz.isAssignableFrom(livingBase.getClass()))
        {
            //event.setCanceled(true);
        }
    }

}
