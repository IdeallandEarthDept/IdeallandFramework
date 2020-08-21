package com.deeplake.idealland.events;

import com.deeplake.idealland.util.IDLNBT;
import com.deeplake.idealland.util.IDLSkillNBT;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import com.deeplake.idealland.util.Reference;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.media.jfxmedia.events.PlayerEvent;
import com.sun.media.jfxmedia.events.PlayerStateEvent;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.UUID;

import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.LAST_LEVEL;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsPlayerLevel {
    private static final UUID LEVEL_BOOST = UUID.fromString("28cd6337-48c0-4460-881c-c74baa181fc2");

    @SubscribeEvent
    static void onPlayerLvChange(TickEvent.PlayerTickEvent event)
    {
        if (event.player.world.isRemote)
        {
            return;
        }

        EntityPlayer player = event.player;
        int curLevel = player.experienceLevel;
        int lastLevel = IDLNBTUtil.GetInt(player, LAST_LEVEL, -1);

        if (lastLevel != curLevel)
        {
            IDLNBTUtil.SetInt(player, LAST_LEVEL, curLevel);//should be some better way to do this.

            player.getAttributeMap().removeAttributeModifiers(getAttributeModifiers(lastLevel));
            player.getAttributeMap().applyAttributeModifiers(getAttributeModifiers(curLevel));
        }
    }

    static double getHealthBonus(int level)
    {
        return (level * 2);//there is level 0
    }

    static double getArmorBonus(int level)
    {
        return level > 10 ? (level) : (level << 1 + 5);//there is level 0
    }

    static double getSpeedBonus(int level)
    {
        return (level) * 0.03;//there is level 0
    }

    static double getAttackBonus(int level)
    {
        return level * 0.1;//there is level 0
    }

    public static Multimap<String, AttributeModifier> getAttributeModifiers(int level)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(LEVEL_BOOST,"Level modifier", getHealthBonus(level), 0));
        multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(LEVEL_BOOST,"Level modifier", getSpeedBonus(level), 2));

        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(LEVEL_BOOST,"Level modifier", getAttackBonus(level), 2));

        multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(LEVEL_BOOST,"Level modifier", getArmorBonus(level), 0));

        return multimap;
    }
}
