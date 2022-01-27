package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.Reference;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.UUID;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.LAST_LEVEL;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsPlayerLevel {
    private static final UUID LEVEL_BOOST = UUID.fromString("28cd6337-48c0-4460-881c-c74baa181fc2");
    private static final UUID LEVEL_MODIFER_INIT = UUID.fromString("2ae70999-4b98-4cb6-ab5e-4e45253d1d75");

    @SubscribeEvent
    static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.player.world.isRemote)
        {
            return;
        }

        if (!ModConfig.CURSE_CONF.LEVEL_CURSE_ACTIVE)
        {
            EntityPlayer player = event.player;
            IDLNBTUtil.setInt(player, LAST_LEVEL, -1);
            player.getAttributeMap().removeAttributeModifiers(getAttributeModifiers(1));
            player.getAttributeMap().removeAttributeModifiers(getAttributeModifiersInit());
            Idealland.Log("Removed player %s's level attr.", player.getDisplayNameString());
            return;
        }
        else {
            //EntityPlayer player = event.player;
            //player.getAttributeMap().applyAttributeModifiers(getAttributeModifiersInit());

            //CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, player, "idlframewok.msg.init_active");
        }
    }

    @SubscribeEvent
    static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (!event.player.world.isRemote)
        {
            EntityPlayer player = event.player;
            IDLNBTUtil.setInt(player, LAST_LEVEL, -1);
        }

    }


    @SubscribeEvent
    static void onPlayerLvChange(TickEvent.PlayerTickEvent event)
    {
        if (event.player.world.isRemote || !ModConfig.CURSE_CONF.LEVEL_CURSE_ACTIVE)
        {
            return;
        }

        EntityPlayer player = event.player;
        int maxLevel = ModConfig.CURSE_CONF.LEVEL_CURSE_MAX_LV;
        int curLevel = player.experienceLevel > maxLevel ? maxLevel : player.experienceLevel;
        int lastLevel = IDLNBTUtil.GetInt(player, LAST_LEVEL, -1);

        if (lastLevel != curLevel)
        {
            IDLNBTUtil.setInt(player, LAST_LEVEL, curLevel);//should be some better way to do this.

            player.getAttributeMap().removeAttributeModifiers(getAttributeModifiers(lastLevel));
            player.getAttributeMap().applyAttributeModifiers(getAttributeModifiers(curLevel));

            if (lastLevel >= maxLevel && curLevel == maxLevel)
            {
                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GRAY, player, "idlframewok.msg.stat_lv_overflow");
            }else {
                if (lastLevel == -1 || curLevel < lastLevel)
                {
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, player, "idlframewok.msg.init_active");
                }
                else {
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GREEN, player, "idlframewok.msg.stat_lv_up");
                }
            }
        }
    }

    static double getHealthBonus(int level)
    {
        return (level * 2);//there is level 0
    }

    static double getArmorBonus(int level)
    {
        return (level <= 9 ? (level * 0.333) : ((level * 0.15f) + 3)) * ModConfig.CURSE_CONF.LEVEL_CURSE_ARMOR_MODIFIER;//after lv 10, armor increase will slow down
    }

    static double getSpeedBonus(int level)
    {
        double val = (level) * 0.03;
        double maxSpeed = ModConfig.CURSE_CONF.LEVEL_CURSE_MAX_SPEED;
        return val > maxSpeed ? maxSpeed : val;//I once saw a player with 9000 level
    }

    static double getAttackBonus(int level)
    {
        return level * 0.05;//there is level 0
    }

    static double getLuckBonus(int level)
    {
        return level * 0.05;//there is level 0
    }

    public static Multimap<String, AttributeModifier> getAttributeModifiers(int level)
    {
        Multimap<String, AttributeModifier> multimap = getAttributeModifiersInit();

        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(LEVEL_BOOST,"Level modifier", getHealthBonus(level), 0));
        multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(LEVEL_BOOST,"Level modifier", getSpeedBonus(level), 2));

        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(LEVEL_BOOST,"Level modifier", getAttackBonus(level), 2));

        multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(LEVEL_BOOST,"Level modifier", getArmorBonus(level), 0));

        multimap.put(SharedMonsterAttributes.LUCK.getName(), new AttributeModifier(LEVEL_BOOST,"Level modifier", getLuckBonus(level), 0));

        return multimap;
    }

    public static Multimap<String, AttributeModifier> getAttributeModifiersInit()
    {
        //If you dont make this percentage, you may get killed if some other mod are changing your HPmax.
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        float ratio = ModConfig.CURSE_CONF.INIT_HP_RATIO;
        if (ratio <= -1)
        {
            ratio = -0.5f;
        }
        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(LEVEL_MODIFER_INIT,"Level modifier init", ratio, 1));

        return multimap;
    }
}
