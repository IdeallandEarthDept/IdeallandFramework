package com.somebody.idlframewok.potion.instance;

import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class PotionUndying extends ModPotionAttr {
    public PotionUndying(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);

        resistancePerLevel = 0.1f;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCreatureDeath(LivingDeathEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        if (evt.isCanceled())
        {
            return;
        }

        PotionEffect effect = hurtOne.getActivePotionEffect(ModPotions.UNDYING);
        if (effect != null)
        {
            evt.setCanceled(true);
            float maxHP = hurtOne.getMaxHealth();
            float newHP = maxHP * (ModPotions.UNDYING.resistancePerLevel * (effect.getAmplifier() + 1));
            hurtOne.setHealth((newHP > maxHP) ? maxHP : newHP);
        }
    }
}
