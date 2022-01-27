package com.somebody.idlframewok.potion;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.item.armor.masks.ItemHelmSniper;
import com.somebody.idlframewok.potion.instance.ModPotionAttr;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.EntityUtil.getBuffLevelIDL;
import static net.minecraftforge.fml.common.eventhandler.Event.Result.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsPotion {

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingUpdateEvent event)
    {
        World world = event.getEntityLiving().world;
        if (!world.isRemote)
        {
            //water bless
            EntityLivingBase livingBase = event.getEntityLiving();
            if (livingBase.isInWater() && livingBase.getActivePotionEffect(ModPotions.WATER_BLESS) != null) {
                EntityUtil.ApplyBuff(livingBase, MobEffects.SPEED, 0, 5);
                EntityUtil.ApplyBuff(livingBase, MobEffects.WATER_BREATHING, 0, 5);
                EntityUtil.ApplyBuff(livingBase, MobEffects.NIGHT_VISION, 0, 5);
            }

            if (livingBase.getActivePotionEffect(ModPotions.OOW_BLESS) != null)
            {
                if (livingBase.motionY < -ModConfig.DEBUG_CONF.DROP_SPEED)
                {
                    livingBase.motionY = -ModConfig.DEBUG_CONF.DROP_SPEED;//todo: need checking, doesnot work
                }

                if (livingBase.onGround)
                {
                    livingBase.removePotionEffect(ModPotions.OOW_BLESS);
                }
            }

            if (livingBase.getActivePotionEffect(ModPotions.BURN) != null)
            {
                livingBase.setFire(3);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureHeal(LivingHealEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        float amount = evt.getAmount();

        int modifierLevel = getBuffLevelIDL(hurtOne, ModPotions.HEAL_PLUS) - getBuffLevelIDL(hurtOne, ModPotions.HEAL_MINUS);
//        if (modifierLevel == -4)
//        {
//            evt.setCanceled(true);
//            return;
//        }

        if (modifierLevel != 0)
        {
            amount *= 1 + modifierLevel * 0.25f;
        }

        evt.setAmount(amount);
    }


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        if (evt.getSource() == DamageSource.OUT_OF_WORLD)
        {
            return;
        }

        if (hurtOne.getActivePotionEffect(ModPotions.INVINCIBLE) != null)
        {
            evt.setCanceled(true);
            return;
        }

        float multiplyModifier = 0f;

        //Base Damage Reduction
        Collection<PotionEffect> activePotionEffects = hurtOne.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() instanceof ModPotionAttr)
            {
                ModPotionAttr modBuff = (ModPotionAttr)buff.getPotion();
                if (!world.isRemote)
                {
                    multiplyModifier -= modBuff.getDamageReductionMultiplier(buff.getAmplifier());
                }
            }
        }

        Entity trueSource = evt.getSource().getTrueSource();
        if (trueSource instanceof EntityLivingBase){
            EntityLivingBase sourceCreature = (EntityLivingBase)trueSource;
            if (sourceCreature.isEntityUndead())
            {
                PotionEffect curBuff = hurtOne.getActivePotionEffect(ModPotions.ZEN_HEART);
                if (curBuff != null) {
                    if (!world.isRemote) {
                        evt.setCanceled(true);
                    }
                }
            }

            //Apply damage multiplier
            Collection<PotionEffect> activePotionEffectsAttacker = sourceCreature.getActivePotionEffects();
            for (int i = 0; i < activePotionEffectsAttacker.size(); i++) {
                PotionEffect buff = (PotionEffect)activePotionEffectsAttacker.toArray()[i];
                if (buff.getPotion() instanceof ModPotionAttr)
                {
                    ModPotionAttr modBuff = (ModPotionAttr)buff.getPotion();
                    if (!world.isRemote)
                    {
                        multiplyModifier += modBuff.getAttackMultiplier(buff.getAmplifier());
                    }
                }
            }

            if (evt.getSource().isMagicDamage())
            {
                int modifier = EntityUtil.getBuffLevelIDL(sourceCreature, ModPotions.SPELL_PLUS) - EntityUtil.getBuffLevelIDL(sourceCreature, ModPotions.SPELL_MINUS);
                multiplyModifier += modifier * 0.25f;
            }

            evt.setAmount(evt.getAmount() * (1+multiplyModifier));

            //Critical Judgement
            if (!(trueSource instanceof EntityPlayer)) {//Players have their own critical judgement system. Now we add the non-player system.
                float critRate = 0.1f;
                boolean isCritical = false;

                if (ItemHelmSniper.checkCertainCritical(evt))
                {
                    critRate += 1.0f;
                }

                //Critical chance buff
                activePotionEffects = ((EntityLivingBase) trueSource).getActivePotionEffects();
                for (int i = 0; i < activePotionEffects.size(); i++) {
                    PotionEffect buff = (PotionEffect) activePotionEffects.toArray()[i];
                    if (buff.getPotion() instanceof ModPotionAttr) {
                        ModPotionAttr modBuff = (ModPotionAttr) buff.getPotion();

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
                        if (buff.getPotion() instanceof ModPotionAttr) {
                            ModPotionAttr modBuff = (ModPotionAttr) buff.getPotion();

                            critDmg += modBuff.getCritDmgModifier(buff.getAmplifier());
                        }
                    }

                    evt.setAmount((critDmg) * evt.getAmount());
                    //Idealland.Log(String.format("%s:isCrit = %s, x%s =%s, ", trueSource.getName(), isCritical, critDmg, evt.getAmount()));
                }
                //Idealland.Log(String.format("%s:isCrit = %s, x1f =%s, ", trueSource.getName(), isCritical, evt.getAmount()));
            }
        }

        if (evt.isCanceled())
        {
            return;
        }

        //slime erosion
        if (trueSource instanceof EntitySlime)
        {
            if (!world.isRemote)
            {
                hurtOne.addPotionEffect(new PotionEffect(ModPotions.EROSION, (int)(TICK_PER_SECOND * (evt.getAmount() + 1f)), (int)(evt.getAmount() / 10)));
            }
        }

        //onHit effect
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() instanceof ModPotionAttr)
            {
                ModPotionAttr modBuff = (ModPotionAttr)buff.getPotion();
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
                    PotionEffect curBuff = hurtOne.getActivePotionEffect(ModPotions.ZEN_HEART);
                    if (curBuff != null) {
                        PotionEffect sourceBuff = sourceCreature.getActivePotionEffect(ModPotions.ZEN_HEART);
                        if (sourceBuff == null) {//prevent dead loop
                            sourceCreature.knockBack(hurtOne, evt.getStrength(), -evt.getRatioX(), -evt.getRatioZ());
                        }
                        evt.setCanceled(true);
                    }
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
                if (buff.getPotion() instanceof ModPotionAttr)
                {
                    ModPotionAttr modBuff = (ModPotionAttr)buff.getPotion();

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
            if (buff.getPotion() instanceof ModPotionAttr)
            {
                ModPotionAttr modBuff = (ModPotionAttr)buff.getPotion();

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
                if (buff.getPotion() instanceof ModPotionAttr)
                {
                    ModPotionAttr modBuff = (ModPotionAttr)buff.getPotion();

                    critDmg += modBuff.getCritDmgModifier(buff.getAmplifier());
                }
            }

            float originalModifier = evt.getDamageModifier();
            evt.setDamageModifier(originalModifier + critDmg);
        }
    }
}
