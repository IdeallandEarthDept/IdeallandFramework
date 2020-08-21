package com.deeplake.idealland.events;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.init.ModConfig;
import com.deeplake.idealland.potion.ModPotions;
import com.deeplake.idealland.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsPlayer {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCreatureDied(final LivingDeathEvent ev) {
        if (ev.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) ev.getEntity();
            Idealland.Log(player.getDisplayNameString() + " died at " + player.getPosition());

            if (player instanceof EntityPlayerMP)
            {
                CommonFunctions.SendMsgToPlayerStyled((EntityPlayerMP) player, "idealland.death_report", TextFormatting.YELLOW,
                        player.getDisplayNameString(),  player.getPosition());
            }
        }

        Entity trueSource = ev.getSource().getTrueSource();

        if (trueSource != null)
        {
            if (trueSource instanceof EntityPlayer) {

                if (!ModConfig.SPAWN_CONF.SPAWN_TAINTER_REQ_BUFF)
                {
                    return;
                }

                //Moroon Notice System
                int morPeriod = ModConfig.SPAWN_CONF.INVASION_PERIOD;
                int warnPhase = ModConfig.SPAWN_CONF.INVASION_FORCAST;
                //NBTTagCompound playerData = trueSource.getEntityData();
                //NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
                //NBTTagCompound idl_data = getTagSafe(data, IDEALLAND);

                EntityPlayer player = (EntityPlayer) trueSource;
                int killCount = IDLNBT.getPlayerIdeallandIntSafe(player, KILL_COUNT);
                int killMor = IDLNBT.getPlayerIdeallandIntSafe(player, KILL_COUNT_MOR);
                int morNotice = IDLNBT.getPlayerIdeallandIntSafe(player, MOR_INTEREST);
                boolean isNoticeIncreased = false;
                if (!player.world.isRemote)
                {
                    IDLNBT.setPlayerIdeallandTagSafe(player, KILL_COUNT, ++killCount);

                    if (EntityUtil.isMoroonTeam(ev.getEntityLiving() ))
                    {
                        morNotice++;
                        isNoticeIncreased = true;
                        IDLNBT.setPlayerIdeallandTagSafe(player, KILL_COUNT_MOR, ++killMor);
                        IDLNBT.setPlayerIdeallandTagSafe(player, MOR_INTEREST, morNotice);
//                        if (killMor % 50 == 0){ CommonFunctions.SafeSendMsgToPlayer(player, "idealland.mor_kill_count_notify", killMor);}
                    }
                    else {
                        if (player.getRNG().nextFloat() < ModConfig.SPAWN_CONF.KILL_NON_MOROON_INTEREST)
                        {
                            morNotice++;
                            isNoticeIncreased = true;
                            IDLNBT.setPlayerIdeallandTagSafe(player, MOR_INTEREST, morNotice);
                        }
                    }
                    //Idealland.Log(String.format("killed %s, Killcount =  %d, %d", ev.getEntity().getName(), killCount, killMor));
                }
//              if (killCount % 50 == 45){if (!player.world.isRemote){CommonFunctions.SafeSendMsgToPlayer(player, "idealland.kill_count_herald");}}
                if (isNoticeIncreased && !player.world.isRemote)
                {
                    if (morNotice % morPeriod == 0)
                    {
                        CommonFunctions.SafeSendMsgToPlayer(player, "idealland.moroon_incoming", TextFormatting.RED);
                        //todo: time made configurable
                        player.addPotionEffect(new PotionEffect(ModPotions.NOTICED_BY_MOR, ModConfig.SPAWN_CONF.INVASION_LENGTH * TICK_PER_SECOND, 0));
                    } else if (morNotice % morPeriod == warnPhase)
                    {
                        CommonFunctions.SafeSendMsgToPlayer(player, "idealland.moroon_incoming_herald",  TextFormatting.YELLOW);
                        //player.addPotionEffect(new PotionEffect(ModPotions.NOTICED_BY_MOR, 60 * TICK_PER_SECOND, 0));
                    }
                }


            }
        }
    }



//    @SubscribeEvent
//    public static void onPlayerAttacked(final LivingAttackEvent ev) {
//        if (ev.getSource().getTrueSource() instanceof EntityPlayer) {
//            EntityPlayer player = (EntityPlayer) ev.getSource().getTrueSource();
//            Idealland.Log(player.getHeldItemMainhand().getItem().toString());
//        }
//    }

//    @SubscribeEvent
//    public void onPlayerBeAttacked(final LivingAttackEvent ev) {
//        if (ev.getEntity() instanceof EntityPlayer) {
//
//        }
//    }
//
//
//    //damage not cut. should handle damage reduction here
//    @SubscribeEvent
//    public void onPlayerHurt(final LivingHurtEvent ev) {
//        if (ev.getEntityLiving() instanceof EntityPlayer) {
//
//        }
//        else if (ev.getSource().getTrueSource() instanceof EntityPlayer) {
//
//        }
//    }
//
//    //should not change the damage here
//    @SubscribeEvent
//    public void onPlayerDamaged(final LivingDamageEvent ev) {
////        At this point armor, potion and absorption modifiers have already been applied to damage - this is FINAL value.<br>
////                * Also note that appropriate resources (like armor durability and absorption extra hearths) have already been consumed.<br
////Cancelable
//        if (ev.getEntityLiving() instanceof EntityPlayer) {
//
//        }
//    }

//    @SubscribeEvent
//    public void onRecvChat(final ClientChatReceivedEvent ev) {
////        At this point armor, potion and absorption modifiers have already been applied to damage - this is FINAL value.<br>
////                * Also note that appropriate resources (like armor durability and absorption extra hearths) have already been consumed.<br
////Cancelable
//        if (SHIELD_CHUNK_MESSAGES && ev.getMessage().getFormattedText().contains("Chunks"))
//        {
//            ev.setCanceled(true);
//        }
//    }

    //PlayerBrewedPotionEvent
    //HarvestCheck
    //PlayerDestroyItemEvent
    //EntityItemPickupEvent
    //attackTargetEntityWithCurrentItem todo:critical
}
