package com.somebody.idlframewok.designs.events.design.socket;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.SocketUtil;
import com.somebody.idlframewok.world.biome.godlands.skylands.BiomeSkylandBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.CYCLE_SOCKET;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsCycleSocket {

    //2:immune to kb in skyland
    //4:void cycling
    //6:

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        if (!evt.getEntityLiving().world.isRemote && !evt.isCanceled())
        {
            EntityLivingBase hurtOne = evt.getEntityLiving();
            int defSocketCount = getCycleEquipCount(hurtOne);

            if (defSocketCount == 0)
            {
                return;
            }

            if (evt.getSource() == DamageSource.OUT_OF_WORLD)
            {
                if (defSocketCount >= 4)
                {
                    hurtOne.setPositionAndUpdate(hurtOne.posX, 300, hurtOne.posZ);
                    hurtOne.fallDistance = 0;
                    if (IDLNBTUtil.GetIntAuto(hurtOne, Color16Def.FALL_PROTECT, 0) == 0)
                    {
                        IDLNBTUtil.addIntAuto(hurtOne, Color16Def.FALL_PROTECT, 1);
                    }
                    //evt.setCanceled(true);
                }
            }

            if (evt.getSource() == DamageSource.FALL)
            {
                evt.setAmount(evt.getAmount() * (1 - 0.2f * defSocketCount));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureKB(LivingKnockBackEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();
        if (!world.isRemote) {
            int defSocketCount = getCycleEquipCount(hurtOne);

            if (defSocketCount >= 2)
            {
                if (EntityUtil.getBiomeForEntity(hurtOne) instanceof BiomeSkylandBase)
                {
                    evt.setCanceled(true);
                }
            }
        } else {

        }
    }

    public static int getCycleEquipCount(@Nullable EntityLivingBase target)
    {
        return SocketUtil.getAnyEquipCount(target, CYCLE_SOCKET);
    }
}
