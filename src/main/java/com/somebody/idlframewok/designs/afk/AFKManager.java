package com.somebody.idlframewok.designs.afk;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.advancements.ModAdvancementsInit;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.Calendar;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class AFKManager {
    static final String LAST_LOGIN = "last_login";

    static final String MSG_ERROR_TIME = "msg.afk.error_time";
    static final String MSG_COUNT_START = "msg.afk.count_start";
    static final String MSG_AWARD_EXTRACT = "msg.afk.award_extract";
    static final String MSG_AWARD_NONE = "msg.afk.award_none";


    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!ModConfig.idleConf.IDLE_ENABLED) {
            return;
        }

        if (!event.player.getEntityWorld().isRemote) {
            EntityPlayer player = event.player;
            Calendar calendar = player.getEntityWorld().getCurrentDate();
            long now = calendar.getTimeInMillis();
            long timeStamp = IDLNBTUtil.getPlayerIdeallandLongSafe(player, LAST_LOGIN);

            if (timeStamp == 0) {
                IDLNBTUtil.setPlayerIdeallandTagSafe(player, LAST_LOGIN, now);
                CommonFunctions.SafeSendMsgToPlayer(player, MSG_COUNT_START);
                //System loaded.
            } else {
                long deltaHours = now - timeStamp;
                Idealland.Log("Delta time: %d", deltaHours);
                if (deltaHours < 0) {
                    //Wrong time. Player cheated.
                    CommonFunctions.SafeSendMsgToPlayer(player, MSG_ERROR_TIME);
                    return;
                } else {
                    deltaHours /= getMillisecPerHour();
                    if (deltaHours >= getMaxHours()) {
                        //cannot accumulate indefinitely.
                        deltaHours = deltaHours;
                    }

                    giveAwayAward(player, Math.toIntExact(deltaHours));

                    long newTimeStamp = now - (now % getMillisecPerHour());
                    IDLNBTUtil.setPlayerIdeallandTagSafe(player, LAST_LOGIN, newTimeStamp);
                }
            }
        }
    }

    static void giveAwayAward(EntityPlayer player, int deltaHours) {
        //Send message
        if (deltaHours == 0) {
            CommonFunctions.SafeSendMsgToPlayer(player, MSG_AWARD_NONE);
        } else {
            CommonFunctions.SafeSendMsgToPlayer(player, MSG_AWARD_EXTRACT, deltaHours);
            if (ModAdvancementsInit.hasAdvancement(player, "story/mine_diamond")) {
                if (ModAdvancementsInit.hasAdvancement(player, "story/shiny_gear")) {
                    PlayerUtil.giveToPlayer(player, Items.DIAMOND, deltaHours);
                } else {
                    PlayerUtil.giveToPlayer(player, Items.DIAMOND, player.getRNG().nextInt(deltaHours + 1));
                }
            }

            if (ModAdvancementsInit.hasAdvancement(player, "story/mine_stone")) {
                PlayerUtil.giveToPlayer(player, Item.getItemFromBlock(Blocks.COBBLESTONE), deltaHours);
            }

            if (ModAdvancementsInit.hasAdvancement(player, "story/smelt_iron")) {
                if (ModAdvancementsInit.hasAdvancement(player, "story/iron_tools") &&
                        ModAdvancementsInit.hasAdvancement(player, "story/obtain_armor")
                ) {
                    PlayerUtil.giveToPlayer(player, Items.IRON_INGOT, deltaHours);
                } else {
                    PlayerUtil.giveToPlayer(player, Items.IRON_NUGGET, deltaHours * 2);
                }
            }

            if (ModAdvancementsInit.hasAdvancement(player, "adventure/shoot_arrow")) {
                PlayerUtil.giveToPlayer(player, Items.ARROW, deltaHours * 2);
            }

            if (ModAdvancementsInit.hasAdvancement(player, "nether/obtain_blaze_rod")) {
                PlayerUtil.giveToPlayer(player, Items.BLAZE_POWDER, deltaHours);
            }

            if (ModAdvancementsInit.hasAdvancement(player, "husbandry/plant_seed")) {
                PlayerUtil.giveToPlayer(player, Items.WHEAT, deltaHours);
            }

            if (ModAdvancementsInit.hasAdvancement(player, "story/mine_stone")) {
                PlayerUtil.giveToPlayer(player, Item.getItemFromBlock(Blocks.COBBLESTONE), deltaHours);
            }

            if (ModAdvancementsInit.hasAdvancement(player, "story/enter_the_end")) {
                PlayerUtil.giveToPlayer(player, Items.ENDER_PEARL, deltaHours);
            }

            if (ModAdvancementsInit.hasAdvancement(player, "story/form_obsidian")) {
                PlayerUtil.giveToPlayer(player, Item.getItemFromBlock(Blocks.OBSIDIAN), deltaHours);
            }
        }
    }

    public static long getMillisecPerHour() {
        return ModConfig.idleConf.PERIOD_LENGTH;
    }

    public static int getMaxHours() {
        return ModConfig.idleConf.MAX_STACK_COUNT;
    }
}
