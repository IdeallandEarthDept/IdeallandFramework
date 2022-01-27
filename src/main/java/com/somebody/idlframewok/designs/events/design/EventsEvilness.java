package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

import static com.somebody.idlframewok.util.MessageDef.*;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.EVILNESS;
import static net.minecraftforge.fml.common.gameevent.TickEvent.Type.WORLD;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsEvilness {
    final static int DAY_BEGIN = 0;//it's like 6am in the morning, but actually tick 0

    @SubscribeEvent
    static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        World world = event.world;
        if (world.isRemote || !ModConfig.CURSE_CONF.RAIN_CURSE_ACTIVE)
        {
            return;
        }

        if (event.type == WORLD) {
            if (world.getWorldTime() % CommonDef.TICK_PER_DAY == DAY_BEGIN)
            {

                List<EntityPlayer> livingBases = world.getEntities(EntityPlayer.class, EntityUtil.UNDER_SKY);
                for (EntityPlayer player :
                        livingBases) {
                    int evilness = IDLNBTUtil.GetIntAuto(player, EVILNESS, 0);
                    if (evilness > 0)
                    {
                        if (world.rand.nextFloat() < 0.25f) {
                            IDLNBTUtil.addIntAuto(player, EVILNESS, -1);
                            if (evilness == 1) {
                                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GREEN, player, TextFormatting.BOLD + MSG_END_EVIL);
                            } else {
                                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, player, MSG_FADE_EVIL_FAIL);
                            }
                        }
                        else {
                            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GREEN, player, MSG_FADE_EVIL);
                        }
                    }
                }
            }
            //world.setRainStrength(1.0f);
        }
    }
}
