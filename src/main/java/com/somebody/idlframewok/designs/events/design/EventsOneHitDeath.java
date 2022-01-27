package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.Reference;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;



@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsOneHitDeath {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        if (!evt.getEntityLiving().world.isRemote && ModConfig.CURSE_CONF.ONE_HIT_CURSE_ACTIVE)
        {
            evt.setAmount(evt.getAmount() * 128f);
        }
    }
}
