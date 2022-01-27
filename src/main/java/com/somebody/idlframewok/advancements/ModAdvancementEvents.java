package com.somebody.idlframewok.advancements;

import com.somebody.idlframewok.Idealland;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ModAdvancementEvents {

    @SubscribeEvent
    static void onLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        ModAdvancementsInit.giveAdvancement(event.player, AdvancementKeys.ROOT_M);
        ModAdvancementsInit.giveAdvancement(event.player, AdvancementKeys.ROOT_S);
    }
}
