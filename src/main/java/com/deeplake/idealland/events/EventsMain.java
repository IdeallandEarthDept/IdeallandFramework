package com.deeplake.idealland.events;

import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.init.ModConfig;
import com.deeplake.idealland.util.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsMain {

    @SubscribeEvent
    public static void onLootTableLoad(final LootTableLoadEvent ev) {
        switch (ev.getName().toString()) {
            case "minecraft:chests/stronghold_corridor":
            case "minecraft:chests/simple_dungeon":
            case "minecraft:chests/igloo_chest":
            case "minecraft:chests/abandoned_mineshaft":
            case "minecraft:chests/stronghold_crossing":
            case "minecraft:chests/jungle_temple":
            case "minecraft:chests/desert_pyramid":

            case "minecraft:chests/village_blacksmith":
            case "minecraft:chests/woodland_mansion":
            case "minecraft:chests/spawn_bonus_chest":
                //Idealland.Log(ev.getName().getResourcePath());
                break;
            default:
                break;
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent e) {
        if (e.player instanceof EntityPlayerMP) {
           //Serverside
            CommonFunctions.BroadCastByKey(ModConfig.GeneralConf.WELCOME_MSG);
        }
    }


}
