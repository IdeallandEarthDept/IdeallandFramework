package com.somebody.idlframewok.potion.buff;

import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class PotionMagicResist extends BasePotion {
    public PotionMagicResist(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);

        resistancePerLevel = 0.25f;
    }

    @SubscribeEvent
    public static void onCreatureHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        if (evt.isCanceled() || !evt.getSource().isMagicDamage())
        {
            return;
        }

        //Magic Damage Reduction
        Collection<PotionEffect> activePotionEffects = hurtOne.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() instanceof PotionMagicResist)
            {
                PotionMagicResist modBuff = (PotionMagicResist)buff.getPotion();
                if (!world.isRemote)
                {
                    float reduceRatio = modBuff.resistancePerLevel * (buff.getAmplifier());
                    evt.setAmount(Math.max(1 - reduceRatio, 0f) * evt.getAmount());
                }
            }
        }
    }
}
