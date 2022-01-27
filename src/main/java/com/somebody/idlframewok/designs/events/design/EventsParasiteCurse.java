package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.PlayerUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraft.world.biome.*;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsParasiteCurse {
    static final int parasiteMax = 9;

    static final int infectDivider = 10;

    static final UUID uuid = UUID.fromString("2acf8374-5b23-43c4-86b7-6a469549c1c6");

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onAwake(TickEvent.PlayerTickEvent evt) {

        EntityPlayer player = evt.player;
        World world = player.getEntityWorld();
        if (!world.isRemote)
        {
            if (!CommonFunctions.isSecondTick(world))
            {
                return;
            }

            if (PlayerUtil.isCreative(player))
            {
                return;
            }

            if (!ModConfig.CURSE_CONF.PARASITE_CURSE_ACTIVE) {
                return;
            }

            int rate = getInfectionRate(player);
            if (shouldInfect(player))
            {
                if (player.getRNG().nextInt(infectDivider) == 0)
                {
                    if (rate < parasiteMax)
                    {
                        rate++;
                        setInfectionRate(player, rate);
                        CommonFunctions.LogPlayerAction(player, "infect = " + rate);
                    }
                }
            }

            FoodStats foodStats = player.getFoodStats();
            foodStats.addExhaustion(rate);//4 exhaust = -1 value
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCreatureDied(LivingDeathEvent evt) {
        if (!ModConfig.CURSE_CONF.PARASITE_CURSE_ACTIVE) {
            return;
        }

        if (evt.getEntityLiving() instanceof EntityPlayer && !evt.getEntityLiving().world.isRemote && !evt.isCanceled()) {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            int infection = getInfectionRate(player);
            if (infection > 0)
            {
                World world = player.getEntityWorld();
                EntityLivingBase parasite = new EntitySilverfish(world);
                parasite.setPosition(player.posX, player.posY + player.getEyeHeight(), player.posZ);
                EntityUtil.boostAttrRatio(parasite, SharedMonsterAttributes.MAX_HEALTH, infection * 0.1f, uuid);
                world.spawnEntity(parasite);

                Idealland.Log("Playerer died with infection: " + infection);
                setInfectionRate(player, 0);
            }

        }
    }

    static boolean shouldInfect(EntityPlayer player)
    {
        if (player.isInWater() && player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty())
        {
            Biome biome = EntityUtil.getBiomeForEntity(player);
            if (biome instanceof BiomeOcean ||
                    biome instanceof BiomeSwamp ||
                    biome instanceof BiomeRiver ||
                    biome instanceof BiomeBeach)
            {
                return true;
            }
        }
        return false;
    }

    public static int getInfectionRate(EntityPlayer player)
    {
        return IDLNBTUtil.getPlayerIdeallandIntSafe(player, IDLNBTDef.PARASITE_1);
    }

    public static void setInfectionRate(EntityPlayer player, int val)
    {
        IDLNBTUtil.setPlayerIdeallandTagSafe(player, IDLNBTDef.PARASITE_1, val);
    }
}
