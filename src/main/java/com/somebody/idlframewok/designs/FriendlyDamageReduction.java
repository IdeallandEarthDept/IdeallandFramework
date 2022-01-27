package com.somebody.idlframewok.designs;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class FriendlyDamageReduction {
    @SubscribeEvent
    public static void onHit(LivingHurtEvent event) {
        EntityLivingBase hurtOne = event.getEntityLiving();
        if (!hurtOne.getEntityWorld().isRemote && event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
            if (EntityUtil.getAttitude(attacker, hurtOne) == EntityUtil.EnumAttitude.FRIEND) {
                if (attacker instanceof EntityLiving && ((EntityLiving) attacker).getAttackTarget() == hurtOne) {
                    //If AI creatures are determined to friendly fire, No protection is applied.
                    //Players can not bypass the protection this way.
                    return;
                }
                event.setAmount(event.getAmount() * ModConfig.GeneralConf.FRIENDLY_DAMAGE_FACTOR);
            }
        }
    }
}
