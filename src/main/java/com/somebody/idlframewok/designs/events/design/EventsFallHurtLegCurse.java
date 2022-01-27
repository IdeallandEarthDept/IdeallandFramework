package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.util.Reference;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsFallHurtLegCurse {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        if (!evt.getEntityLiving().world.isRemote && !evt.isCanceled())
        {
            if (evt.getSource() == DamageSource.FALL)
            {
                float damage = evt.getAmount();
                evt.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (int) (TICK_PER_SECOND * evt.getAmount()), damage >= 5f ? 1 : 0));
            }
        }
    }
}
