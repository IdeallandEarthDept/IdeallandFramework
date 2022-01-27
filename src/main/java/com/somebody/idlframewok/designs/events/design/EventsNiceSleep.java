package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsNiceSleep {
    //PlayerWakeUpEvent
    //PlayerSleepInBedEvent
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onAwake(PlayerWakeUpEvent evt) {

        EntityPlayer player = evt.getEntityPlayer();
        World world = player.getEntityWorld();
        if (!world.isRemote)
        {
            if (!ModConfig.CURSE_CONF.IMPROVED_SLEEP_ACTIVE) {
                return;
            }

            if (MobEffects.MINING_FATIGUE == null || player == null)
            {
                //not initialized ?
                return;
            }
            EntityUtil.ApplyBuff(player, MobEffects.MINING_FATIGUE, 2, 5f);
            EntityUtil.ApplyBuff(player, MobEffects.WEAKNESS, 2, 5f);
            EntityUtil.ApplyBuff(player, MobEffects.SLOWNESS, 2, 3f);

            if (evt.shouldSetSpawn())
            {
                EntityUtil.ApplyBuff(player, MobEffects.HASTE, 0, 120f);
                EntityUtil.ApplyBuff(player, MobEffects.SPEED, 0, 10f);

                CommonFunctions.SafeSendMsgToPlayer(player, "idlframewok.msg.nice_sleep");
            }
            else {
                EntityUtil.ApplyBuff(player, ModPotions.BERSERK, 1, 10f);
                CommonFunctions.SafeSendMsgToPlayer(player, "idlframewok.msg.bad_sleep");
            }
            //Idealland.Log("%s Wake: immediate=%s, setspawn=%s", player.getDisplayNameString(), evt.wakeImmediately(), evt.shouldSetSpawn());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onSleep(PlayerSleepInBedEvent evt) {
        EntityPlayer player = evt.getEntityPlayer();
        World world = player.getEntityWorld();
        if (!world.isRemote)
        {
//            EntityUtil.ApplyBuff(player, MobEffects.MINING_FATIGUE, 2, 5f);
//            EntityUtil.ApplyBuff(player, MobEffects.WEAKNESS, 2, 5f);
//            EntityUtil.ApplyBuff(player, MobEffects.SLOWNESS, 2, 3f);
//
//            if (evt.shouldSetSpawn())
//            {
//                EntityUtil.ApplyBuff(player, MobEffects.HASTE, 0, 120f);
//                EntityUtil.ApplyBuff(player, MobEffects.SPEED, 0, 10f);
//
//                CommonFunctions.SafeSendMsgToPlayer(player, "idlframewok.msg.nice_sleep");
//            }
//            else {
//                EntityUtil.ApplyBuff(player, ModPotions.BERSERK, 1, 5f);
//                CommonFunctions.SafeSendMsgToPlayer(player, "idlframewok.msg.bad_sleep");
//            }
//            Idealland.Log("%s Wake: immediate=%s, setspawn=%s", player.getDisplayNameString(), evt.wakeImmediately(), evt.shouldSetSpawn());
        }
    }
}
