package com.somebody.idlframewok.designs.events.design.socket;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.SocketUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.WEIGHT_SOCKET;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsWeightSocket {

    //each: reduce KB by 0.1
    //2:KB +50%
    //4:Melee physics damage + 50%
    //6:

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        if (!evt.getEntityLiving().world.isRemote && !evt.isCanceled())
        {
            EntityLivingBase hurtOne = evt.getEntityLiving();
            int defSocketCount = getWeightEquipCount(hurtOne);

            if (defSocketCount == 0)
            {
                return;
            }

//            if (evt.getSource() == DamageSource.OUT_OF_WORLD)
//            {
//                if (defSocketCount >= 4)
//                {
//                    hurtOne.setPosition(hurtOne.posX, 300, hurtOne.posZ);
//                    hurtOne.fallDistance = 0;
//                    if (IDLNBTUtil.GetIntAuto(hurtOne, Color16Def.FALL_PROTECT, 0) == 0)
//                    {
//                        IDLNBTUtil.addIntAuto(hurtOne, Color16Def.FALL_PROTECT, 1);
//                    }
//                    evt.setCanceled(true);
//                }
//            }
//
//            if (evt.getSource() == DamageSource.FALL)
//            {
//                evt.setAmount(evt.getAmount() * (1 - 0.2f * defSocketCount));
//            }
        }
    }

//    @SubscribeEvent(priority = EventPriority.HIGHEST)
//    public static void onCreatureKB(LivingKnockBackEvent evt) {
//        World world = evt.getEntity().getEntityWorld();
//        EntityLivingBase hurtOne = evt.getEntityLiving();
//        if (!world.isRemote) {
//            int defSocketCount = getFleshEquipCount(hurtOne);
//
//            if (defSocketCount >= 2)
//            {
//                if (EntityUtil.getBiomeForEntity(hurtOne) instanceof BiomeSkylandBase)
//                {
//                    evt.setCanceled(true);
//                }
//            }
//        } else {
//
//        }
//    }

    public static int getWeightEquipCount(@Nullable EntityLivingBase target)
    {
        return SocketUtil.getAnyEquipCount(target, WEIGHT_SOCKET);
    }
}
