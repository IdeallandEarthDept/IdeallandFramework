package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsSmartDrop {
    public static final String NBT_HURT_PLAYER = "hurt_player";

    @SubscribeEvent
    public static void onDrop(LivingDropsEvent event)
    {
        List<EntityItem> eventDrops = event.getDrops();
        EntityLivingBase diedOne = event.getEntityLiving();

        //will not drop anything if it's farmed automatically
        if (!event.isRecentlyHit())
        {
            return;
        }

        //only mobs will drop
        if (!(diedOne instanceof IMob)) {
            return;
        }

        //if the player is hungry, there is chance to drop chicken
        //if the player is hurt, there is chance to drop heart, and extra chance if this monster has attacked player.
        if (event.getSource().getTrueSource() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            if (player.getHealth() < player.getMaxHealth())
            {
                float chance = IDLNBTUtil.GetInt(diedOne, NBT_HURT_PLAYER, 0) > 0 ?
                        ModConfig.smartDropConf.heart_chance_sinful :
                        ModConfig.smartDropConf.heart_chance;

                if (diedOne.getRNG().nextFloat() < chance)
                {
                    eventDrops.add(diedOne.dropItem(ModItems.DROP_HEART, 1));
                }
            }

            if (player.getFoodStats().needFood())
            {
                if (diedOne.getRNG().nextFloat() < ModConfig.smartDropConf.hunger_chance)
                {
                    eventDrops.add(diedOne.dropItem(ModItems.DROP_FOOD, 1));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {
        EntityLivingBase hurtOne = event.getEntityLiving();
        if (hurtOne instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityLiving)
        {
            IDLNBTUtil.setInt(event.getSource().getTrueSource(), NBT_HURT_PLAYER, 1);
        }
    }
}
