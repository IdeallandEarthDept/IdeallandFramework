package com.somebody.idlframewok.potion.buff;

import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class PotionUndying extends BasePotion {
    public PotionUndying(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);

        resistancePerLevel = 0.25f;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCreatureDeath(LivingDeathEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        if (evt.isCanceled())
        {
            return;
        }

//        PotionEffect effect = hurtOne.getActivePotionEffect(ModPotions.UNDYING);
//        if (effect != null)
//        {
//            evt.setCanceled(true);
//            hurtOne.setHealth(hurtOne.getMaxHealth() / (ModPotions.UNDYING.resistancePerLevel * (effect.getAmplifier() + 1)));
//        }
    }
}
