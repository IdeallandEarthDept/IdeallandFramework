package com.somebody.idlframewok.designs.events.design.socket;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.SocketUtil;
import com.somebody.idlframewok.world.biome.BiomeFlesh;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.FLESH_SOCKET;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsFleshSocket {

    //2:immune to flesh land fatigue
    //4: wont hunger
    //6:
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        if (!evt.getEntityLiving().world.isRemote && !evt.isCanceled())
        {
            EntityLivingBase hurtOne = evt.getEntityLiving();
            int defSocketCount = getFleshEquipCount(hurtOne);

            Biome biome = EntityUtil.getBiomeForEntity(hurtOne);
            if (biome instanceof BiomeFlesh)
            {
                evt.setAmount(evt.getAmount() + 2 - defSocketCount * 0.5f);
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

    public static int getFleshEquipCount(@Nullable EntityLivingBase target)
    {
        return SocketUtil.getAnyEquipCount(target, FLESH_SOCKET);
    }
}
