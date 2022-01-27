package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsFeelings {
    //note this is final value
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCreatureHurt(LivingDamageEvent evt) {
        EntityLivingBase livingBase = evt.getEntityLiving();
        if (!livingBase.world.isRemote && !evt.isCanceled())
        {
            if (EntityUtil.NON_HALF_CREATURE.apply(livingBase) && ! EntityUtil.isMechanical(livingBase))
            {
                if (!ModPotions.hasFeeling(livingBase))
                {
                    int fearLevel = (int) Math.floor(evt.getAmount() / (0.22f * livingBase.getMaxHealth())) - 1;
                    if (fearLevel >= 0)
                    {
                        EntityUtil.ApplyBuff(livingBase, ModPotions.FEAR, fearLevel, evt.getAmount() * 2f);
                    }
                    else {
                        EntityUtil.ApplyBuff(livingBase, ModPotions.ANGER, 0, evt.getAmount() * 5f);
                    }
                }
            }
        }
    }
}
