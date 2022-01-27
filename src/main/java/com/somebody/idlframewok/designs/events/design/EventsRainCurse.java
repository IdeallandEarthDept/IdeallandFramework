package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.MobEffects;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

import static net.minecraftforge.fml.common.gameevent.TickEvent.Type.WORLD;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsRainCurse {
    final static int NIGHT_BEGIN = 13000;
    final static int DAY_BEGIN = 0;//it's like 6am in the morning, but actually tick 0

    public static boolean isInNightRain(EntityLivingBase livingBase)
    {
        World world = livingBase.world;
        return world.getWorldTime() % CommonDef.TICK_PER_DAY >= NIGHT_BEGIN
            && world.isRainingAt(livingBase.getPosition())
                && world.getLightFor(EnumSkyBlock.BLOCK, livingBase.getPosition()) < 7;
    }

    @SubscribeEvent
    static void onLivingTick(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();
        World world = livingBase.world;
        if (world.isRemote || !ModConfig.CURSE_CONF.RAIN_CURSE_ACTIVE)
        {
            return;
        }

        if (isInNightRain(livingBase))
        {
            if (livingBase.getActivePotionEffect(MobEffects.INVISIBILITY) == null
                || livingBase.getActivePotionEffect(MobEffects.INVISIBILITY).getDuration() < 5)
            {
                if (livingBase.getActivePotionEffect(MobEffects.GLOWING) == null)
                {
                    //glowing creatures don't turn invisible
                    EntityUtil.ApplyBuff(livingBase, MobEffects.INVISIBILITY, 0, 0.5f);
                }
            }
        }
    }

    @SubscribeEvent
    static void onLivingBirth(LivingSpawnEvent.SpecialSpawn event)
    {
        World world = event.getEntity().world;
        if (world.isRemote || !ModConfig.CURSE_CONF.RAIN_CURSE_ACTIVE)
        {
            return;
        }

        if (event.getEntity() instanceof EntityLightningBolt)
        {
            List<EntityLivingBase> livingBases = world.getEntities(EntityLivingBase.class, EntityUtil.UNDER_SKY);

            //Idealland.Log("Thunder:%s", event.getEntity().getPosition());

            for (EntityLivingBase livingBase:livingBases) {
                EntityUtil.ApplyBuff(livingBase, MobEffects.GLOWING, 0, 0.5f);
            }
        }
    }

    @SubscribeEvent
    static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        World world = event.world;
        if (world.isRemote || !ModConfig.CURSE_CONF.RAIN_CURSE_ACTIVE)
        {
            return;
        }

        if (event.type == WORLD) {
            if (world.getWorldTime() % CommonDef.TICK_PER_DAY == NIGHT_BEGIN)
            {
                if (world.rand.nextFloat() < ModConfig.CURSE_CONF.RAIN_CURSE_CHANCE)
                {
                    WorldInfo worldInfo = world.getWorldInfo();
                    worldInfo.setRaining(true);
                    worldInfo.setThundering(true);
                    worldInfo.setRainTime(11000);//rain until sunset
                }
            }
            //world.setRainStrength(1.0f);
        }
    }
}
