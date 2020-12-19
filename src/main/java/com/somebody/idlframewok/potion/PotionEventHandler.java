package com.somebody.idlframewok.potion;

import com.somebody.idlframewok.potion.buff.BasePotion;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

import static net.minecraftforge.fml.common.eventhandler.Event.Result.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class PotionEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

//        if (hurtOne.getActivePotionEffect(INVINCIBLE) != null)
//        {
//            evt.setCanceled(true);
//            return;
//        }

        //Base Damage Reduction
        Collection<PotionEffect> activePotionEffects = hurtOne.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() instanceof BasePotion)
            {
                BasePotion modBuff = (BasePotion)buff.getPotion();
                if (!world.isRemote)
                {
                    float reduceRatio = modBuff.getDamageReductionMultiplier(buff.getAmplifier());
                    evt.setAmount((1 - reduceRatio) * evt.getAmount());
                }
            }
        }

        Entity trueSource = evt.getSource().getTrueSource();
        if (trueSource instanceof EntityLivingBase){
            EntityLivingBase sourceCreature = (EntityLivingBase)trueSource;
//            if (sourceCreature.isEntityUndead())
//            {
//                PotionEffect curBuff = hurtOne.getActivePotionEffect(ZEN_HEART);
//                if (curBuff != null) {
//                    if (!world.isRemote) {
//                        evt.setCanceled(true);
//                    }
//                }
//            }

            //Apply damage multiplier
            Collection<PotionEffect> activePotionEffectsAttacker = sourceCreature.getActivePotionEffects();
            for (int i = 0; i < activePotionEffectsAttacker.size(); i++) {
                PotionEffect buff = (PotionEffect)activePotionEffectsAttacker.toArray()[i];
                if (buff.getPotion() instanceof BasePotion)
                {
                    BasePotion modBuff = (BasePotion)buff.getPotion();
                    if (!world.isRemote)
                    {
                        evt.setAmount((1 + modBuff.getAttackMultiplier(buff.getAmplifier())) * evt.getAmount());
                    }
                }
            }

            //Critical Judgement
            if (!(trueSource instanceof EntityPlayer)) {//Players have their own critical judgement system. Now we add the non-player system.
                float critRate = 0.1f;
                boolean isCritical = false;

                //Critical chance buff
                activePotionEffects = ((EntityLivingBase) trueSource).getActivePotionEffects();
                for (int i = 0; i < activePotionEffects.size(); i++) {
                    PotionEffect buff = (PotionEffect) activePotionEffects.toArray()[i];
                    if (buff.getPotion() instanceof BasePotion) {
                        BasePotion modBuff = (BasePotion) buff.getPotion();

                        critRate += modBuff.getCritRate(buff.getAmplifier());
                    }
                }

                if (critRate > 0 && ((EntityLivingBase) trueSource).getRNG().nextFloat() < critRate) {
                    isCritical = true;
                }

                //Critical damage multiplier buff
                if (isCritical) {
                    float critDmg = 1.5f;//vanilla

                    activePotionEffects = ((EntityLivingBase) trueSource).getActivePotionEffects();
                    for (int i = 0; i < activePotionEffects.size(); i++) {
                        PotionEffect buff = (PotionEffect) activePotionEffects.toArray()[i];
                        if (buff.getPotion() instanceof BasePotion) {
                            BasePotion modBuff = (BasePotion) buff.getPotion();

                            critDmg += modBuff.getCritDmgModifier(buff.getAmplifier());
                        }
                    }

                    evt.setAmount((critDmg) * evt.getAmount());
                    //IdlFramework.Log(String.format("%s:isCrit = %s, x%s =%s, ", trueSource.getName(), isCritical, critDmg, evt.getAmount()));
                }
                //IdlFramework.Log(String.format("%s:isCrit = %s, x1f =%s, ", trueSource.getName(), isCritical, evt.getAmount()));
            }
        }

        if (evt.isCanceled())
        {
            return;
        }

        //slime erosion
//        if (trueSource instanceof EntitySlime)
//        {
//            if (!world.isRemote)
//            {
//                hurtOne.addPotionEffect(new PotionEffect(ModPotions.EROSION, (int)(TICK_PER_SECOND * (evt.getAmount() + 1f)), (int)(evt.getAmount() / 10)));
//            }
//        }

        //onHit effect
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() instanceof BasePotion)
            {
                BasePotion modBuff = (BasePotion)buff.getPotion();
                if (world.isRemote)
                {
                    modBuff.playOnHitEffect(hurtOne, evt.getAmount());
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureKB(LivingKnockBackEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();
        if (!world.isRemote) {
            //Handle virtue and undead
            Entity trueSource = evt.getOriginalAttacker();
            if (trueSource instanceof EntityLivingBase){
                EntityLivingBase sourceCreature = (EntityLivingBase)trueSource;
                if (sourceCreature.isEntityUndead())
                {
//                    PotionEffect curBuff = hurtOne.getActivePotionEffect(ZEN_HEART);
//                    if (curBuff != null) {
//                        PotionEffect sourceBuff = sourceCreature.getActivePotionEffect(ZEN_HEART);
//                        if (sourceBuff == null) {//prevent dead loop
//                            sourceCreature.knockBack(hurtOne, evt.getStrength(), -evt.getRatioX(), -evt.getRatioZ());
//                        }
//                        evt.setCanceled(true);
//                    }
                }
            }

            //KB Reduction
            if (evt.isCanceled())
            {
                return;
            }

            Collection<PotionEffect> activePotionEffects = hurtOne.getActivePotionEffects();
            for (int i = 0; i < activePotionEffects.size(); i++) {
                PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
                if (buff.getPotion() instanceof BasePotion)
                {
                    BasePotion modBuff = (BasePotion)buff.getPotion();

                    float reduceRatio = modBuff.getKBResistanceMultiplier(buff.getAmplifier());
                    evt.setStrength((1 - reduceRatio) * evt.getStrength());
                }
            }
        } else {

        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerCriticalJudge(CriticalHitEvent evt) {
//        public class CriticalHitEvent extends PlayerEvent {
//            private float damageModifier;
//            private final float oldDamageModifier;
//            private final Entity target;
//            private final boolean vanillaCritical;
//        }
        EntityPlayer player = evt.getEntityPlayer();
        float critRate = 0f;

        Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() instanceof BasePotion)
            {
                BasePotion modBuff = (BasePotion)buff.getPotion();

                critRate += modBuff.getCritRate(buff.getAmplifier());
            }
        }

        if (evt.isVanillaCritical())
        {
            if (critRate < 0 && player.getRNG().nextFloat() < (1 + critRate))
            {
                evt.setResult(DENY);
            }
        }else {
            if (critRate > 0 && player.getRNG().nextFloat() < (critRate))
            {
                evt.setResult(ALLOW);
            }
        }

        boolean isCrit = evt.getResult() == ALLOW || (evt.getResult() == DEFAULT && evt.isVanillaCritical());
        //is critical
        if (isCrit)
        {
            float critDmg = 0f;

            activePotionEffects = player.getActivePotionEffects();
            for (int i = 0; i < activePotionEffects.size(); i++) {
                PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
                if (buff.getPotion() instanceof BasePotion)
                {
                    BasePotion modBuff = (BasePotion)buff.getPotion();

                    critDmg += modBuff.getCritDmgModifier(buff.getAmplifier());
                }
            }

            float originalModifier = evt.getDamageModifier();
            evt.setDamageModifier(originalModifier + critDmg);
        }
    }
}
