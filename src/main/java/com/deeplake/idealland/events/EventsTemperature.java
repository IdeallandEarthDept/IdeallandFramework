package com.deeplake.idealland.events;

import com.deeplake.idealland.init.ModConfig;
import com.deeplake.idealland.util.*;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.idealland.util.IDLNBT.getPlayerIdeallandBoolSafe;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsTemperature {

    public static float TemperatureToReadable(float temperature)
    {
        return temperature * 30f;
    }

    public static String TemperatureForDisplay(float temperature)
    {
        return String.valueOf(TemperatureToReadable(temperature));
    }


    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;

        if (!ModConfig.TEMPERATURE_CONF.TEMPERATURE_ENABLED)
        {
            IDLNBT.setPlayerIdeallandTagSafe(player, BASE_IS_SET, false);
            return;
        }

        boolean boolIsSet = getPlayerIdeallandBoolSafe(player, BASE_IS_SET);
        if(!boolIsSet) {
            if (player instanceof EntityPlayerMP) {
                IDLNBT.setPlayerIdeallandTagSafe(player, BASE_IS_SET, true);

                float temp = CommonFunctions.GetTemperatureHere(player);
                if (temp < ModConfig.TEMPERATURE_CONF.MIN_BASE_TEMP)
                {
                    temp = ModConfig.TEMPERATURE_CONF.MIN_BASE_TEMP;
                } else if (temp > ModConfig.TEMPERATURE_CONF.MAX_BASE_TEMP)
                {
                    temp = ModConfig.TEMPERATURE_CONF.MAX_BASE_TEMP;
                }

                IDLNBT.setPlayerIdeallandTagSafe(player, BASE_TEMPERATURE, temp);
                CommonFunctions.SendMsgToPlayer((EntityPlayerMP)player, "idealland.temperature.msg.default_temp", TemperatureForDisplay(temp));
            }
        }
    }


    public static void onTickPlayer(LivingEvent.LivingUpdateEvent event)
    {
        if (!ModConfig.TEMPERATURE_CONF.TEMPERATURE_ENABLED)
        {
            return;
        }

        World world = event.getEntityLiving().world;
        if (world.isRemote || world.getTotalWorldTime() % TICK_PER_SECOND != 0)
        {
            return;
        }

        EntityLivingBase livingBase = event.getEntityLiving();
        if (!(livingBase instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) livingBase;

        float bestTempForPlayer = getBestTempForPlayer(player);

        float tempPlusPerArmor = ModConfig.TEMPERATURE_CONF.ALLOW_UP_PER_ARMOR;
        float tempMinusPerArmor = ModConfig.TEMPERATURE_CONF.ALLOW_DOWN_PER_ARMOR;

        float minTemp = bestTempForPlayer + ModConfig.TEMPERATURE_CONF.ALLOW_TEMP_DOWN;
        float maxTemp = bestTempForPlayer + ModConfig.TEMPERATURE_CONF.ALLOW_TEMP_UP;

        for (EntityEquipmentSlot slot: EntityEquipmentSlot.values()) {
            if (slot == EntityEquipmentSlot.OFFHAND || slot == EntityEquipmentSlot.MAINHAND)
            {
                continue;
            }

            ItemStack stack = player.getItemStackFromSlot(slot);
            if (!stack.isEmpty()) {
                minTemp += tempMinusPerArmor;
                maxTemp += tempPlusPerArmor;
            }
        }

        ItemStack stackMainHand= player.getHeldItemMainhand();
        ItemStack stackOffhand= player.getHeldItemOffhand();

        if (stackMainHand.getItem() == Items.BLAZE_ROD || stackOffhand.getItem() == Items.BLAZE_ROD)
        {
            minTemp = -Float.MAX_VALUE;
        }

        if (stackMainHand.getItem() == Items.SNOWBALL || stackOffhand.getItem() == Items.SNOWBALL)
        {
            maxTemp = Float.MAX_VALUE;
        }

        float curTemp = CommonFunctions.GetTemperatureHere(player);
        if (curTemp < minTemp || curTemp > maxTemp)
        {
            boolean cold = curTemp < minTemp;
            float diff = curTemp < minTemp ? (minTemp - curTemp) : (curTemp - maxTemp);

            if (player.getRNG().nextFloat() < diff)
            {
                if (cold)
                {
                    EntityUtil.ApplyBuff(player, MobEffects.MINING_FATIGUE, (int) (diff / 0.1f), TICK_PER_SECOND);
                }else {
                    EntityUtil.ApplyBuff(player, MobEffects.MINING_FATIGUE, (int) (diff / 0.1f), TICK_PER_SECOND);
                }

                if (player.getRNG().nextFloat() < diff)
                {
                    if (cold)
                    {
                        EntityUtil.ApplyBuff(player, MobEffects.HUNGER, (int) (diff / 0.1f), TICK_PER_SECOND);
                    }
                    else {
                        EntityUtil.ApplyBuff(player, MobEffects.NAUSEA, (int) (diff / 0.1f), TICK_PER_SECOND);
                    }
                }
            }
        }
    }

    public static float getBestTempForPlayer(EntityPlayer player)
    {
        return IDLNBT.getPlayerIdeallandFloatSafe(player, IDLNBTDef.BASE_TEMPERATURE);
    }



//    public static void onSenseAttack(LivingSetAttackTargetEvent event)
//    {
//
//    }
}
