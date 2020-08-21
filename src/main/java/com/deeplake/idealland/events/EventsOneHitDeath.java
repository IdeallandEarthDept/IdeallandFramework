package com.deeplake.idealland.events;

import com.deeplake.idealland.util.Reference;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.deeplake.idealland.init.ModConfig.ONE_HIT_CURSE;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsOneHitDeath {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        if (!evt.getEntityLiving().world.isRemote && ONE_HIT_CURSE.ONE_HIT_CURSE_ACTIVE)
        {
            evt.setAmount(evt.getAmount() * 128f);
        }

    }
}
