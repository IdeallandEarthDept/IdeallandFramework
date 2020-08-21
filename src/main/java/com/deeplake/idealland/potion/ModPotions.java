package com.deeplake.idealland.potion;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.item.ModItems;
import com.deeplake.idealland.item.misc.armor.masks.ItemHelmSniper;
import com.deeplake.idealland.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Collection;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;
import static net.minecraftforge.fml.common.eventhandler.Event.Result.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModPotions {
    public static Potion DEADLY;
    public static Potion RIP;
    public static PotionInsidiousDisease VIRUS_ONE;
    public static PotionZenHeart ZEN_HEART;

    //FGO
    public static PotionUndying UNDYING;

    //combat
    public static PotionMagicResist MAGIC_RESIST;
    public static PotionBlastResist BLAST_RESIST;
    public static BaseSimplePotion KB_RESIST;
    public static BaseSimplePotion INVINCIBLE;

    //other
    public static PotionErosion EROSION;
    public static BaseSimplePotion INTERFERENCE;
    public static final BaseSimplePotion NOTICED_BY_MOR = new BaseSimplePotion(true, 0x662266, "noticed_by_mor", 11);

    //critical
    public static BasePotion CRIT_CHANCE_PLUS;
    public static BasePotion CRIT_CHANCE_MINUS;

    public static BasePotion CRIT_DMG_PLUS;
    public static BasePotion CRIT_DMG_MINUS;

    public static BasePotion BERSERK;

    public static BaseSimplePotion HOT;
    public static BaseSimplePotion COLD;

    //todo:follow range bonus

    //todo: next gen. Can allow multi buff to exist together
    static BasePotion[] CRIT_INCREASE_GROUP;

    @Nullable
    private static Potion getRegisteredMobEffect(String id)
    {
        Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(id));

        if (potion == null)
        {
            throw new IllegalStateException("Invalid MobEffect requested: " + id);
        }
        else
        {
            return potion;
        }
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> evt)
    {
        Idealland.LogWarning("registering potion");
        DEADLY = new PotionDeadly(false, 0x333333, "deadly", 0);
        ZEN_HEART = new PotionZenHeart(false, 0xcccc00, "zen_heart", 1);

        VIRUS_ONE = new PotionInsidiousDisease(true, 0xffffff, "virus_one", 2);
        VIRUS_ONE.tuples.add(new EffectTuple(0.2f, MobEffects.NAUSEA, 100));

        UNDYING = new PotionUndying(false, 0xffff00, "undying", 3);

        MAGIC_RESIST = new PotionMagicResist(false, 0x0000ff, "magic_resist", 4);
        BLAST_RESIST = new PotionBlastResist(false, 0x440000, "blast_resist", 5);
        KB_RESIST = new BasePotion(false, 0x99ff99, "knockback_resist", 6).setKBResistance(0.25f, 0.25f);
        RIP = new BasePotion(false, 0xffffff, "rest_in_peace", 7);

        EROSION = new PotionErosion(true, 0x22ff33, "erosion", 8);
        INVINCIBLE = new BasePotion(false, 0xffffbb, "invincible", 9);

        INTERFERENCE = new BaseSimplePotion(true, 0x2266ff, "interference", 10);

        CRIT_CHANCE_PLUS = new BasePotion(true, 0xff3333, "crit_chance_plus", 12).setCritRateRatio(0.25f, 0.25f);
        CRIT_CHANCE_MINUS = new BasePotion(true, 0xffffff, "crit_chance_minus", 13).setCritRateRatio(-0.25f, -0.25f);

        CRIT_DMG_PLUS = new BasePotion(true, 0xff3333, "crit_dmg_plus", 14).setCritDamageRatio(0.25f, 0.25f);
        CRIT_DMG_MINUS = new BasePotion(true, 0xffffff, "crit_dmg_minus", 15).setCritDamageRatio(-0.25f, -0.25f);

        BERSERK = new BasePotion(true, 0xffffff, "berserk", 16).setAttackRatio(0.25f, 0.25f).setProtectionRatio(-0.25f, -0.25f);

        HOT = new BaseSimplePotion(true, 0x3333ff, "hot", 17);
        COLD = new BaseSimplePotion(true, 0xff6622, "cold", 18);

        evt.getRegistry().register(DEADLY);
        evt.getRegistry().register(ZEN_HEART);
        evt.getRegistry().register(VIRUS_ONE);

        evt.getRegistry().register(UNDYING);

        evt.getRegistry().register(MAGIC_RESIST);
        evt.getRegistry().register(BLAST_RESIST);
        evt.getRegistry().register(KB_RESIST);
        evt.getRegistry().register(INVINCIBLE);

        evt.getRegistry().register(RIP);

        evt.getRegistry().register(EROSION);
        evt.getRegistry().register(INTERFERENCE);

        evt.getRegistry().register(NOTICED_BY_MOR);

        evt.getRegistry().register(CRIT_CHANCE_PLUS);
        evt.getRegistry().register(CRIT_CHANCE_MINUS);

        evt.getRegistry().register(CRIT_DMG_PLUS);
        evt.getRegistry().register(CRIT_DMG_MINUS);

        evt.getRegistry().register(BERSERK);

        evt.getRegistry().register(HOT);
        evt.getRegistry().register(COLD);
        //REGISTRY.registerSpawnList(1, new ResourceLocation("speed"), (new Potion(false, 8171462))
        // .setPotionName("effect.moveSpeed")
        // .setIconIndex(0, 0)
        // .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2).setBeneficial());
    }

//    @SubscribeEvent
//    public static void onCreatureAttack(LivingAttackEvent evt) {
//
//    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        if (hurtOne.getActivePotionEffect(INVINCIBLE) != null)
        {
            evt.setCanceled(true);
            return;
        }

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
            if (sourceCreature.isEntityUndead())
            {
                PotionEffect curBuff = hurtOne.getActivePotionEffect(ZEN_HEART);
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
                if (buff.getPotion() instanceof BasePotion)
                {
                    BasePotion modBuff = (BasePotion)buff.getPotion();
                    if (!world.isRemote)
                    {
                        evt.setAmount((1 + modBuff.getAttackMultiplier(buff.getAmplifier())) * evt.getAmount());
                    }
                }
            }

            //blood paper(not potion related)
            ItemStack stackInHand = sourceCreature.getHeldItemMainhand();

            if (sourceCreature instanceof EntityPlayer &&
                    stackInHand.getItem() == Items.PAPER)
            {
                EntityPlayer player = (EntityPlayer) sourceCreature;
                stackInHand.shrink(1);

                player.addItemStackToInventory(new ItemStack(ModItems.PAPER_BLOOD));
            }

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

    //  * At this point armor, potion and absorption modifiers have already been applied to damage - this is FINAL value.<br>
//  * Also note that appropriate resources (like armor durability and absorption extra hearths) have already been consumed.<br>
//    @SubscribeEvent
//    public static void onCreatureDamaged(LivingDamageEvent evt) {
//        World world = evt.getEntity().getEntityWorld();
//        EntityLivingBase hurtOne = evt.getEntityLiving();
//        if (!world.isRemote) {
////            Entity trueSource = evt.getSource().getTrueSource();
////            if (trueSource instanceof EntityLivingBase){
////                EntityLivingBase sourceCreature = (EntityLivingBase)trueSource;
////                if (sourceCreature.isEntityUndead())
////                {
////                    PotionEffect curBuff = hurtOne.getActivePotionEffect(ZEN_HEART);
////                    if (curBuff != null) {
////                        evt.setCanceled(true);
////                    }
////                }
////            }
//        } else {
//
//        }
//    }

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
                    PotionEffect curBuff = hurtOne.getActivePotionEffect(ZEN_HEART);
                    if (curBuff != null) {
                        PotionEffect sourceBuff = sourceCreature.getActivePotionEffect(ZEN_HEART);
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


//    @SubscribeEvent
//    public static void onCreatureDie(LivingDeathEvent evt) {
//        //Idealland.Log(evt.getEntityLiving().getDisplayName() + " died @" + evt.getEntityLiving().getPositionVector().toString());
//    }

    //ProjectileImpactEvent
    //EntityItemPickupEvent   cannot pickup item
    //ClientChatEvent  cannot chat
    //ClientChatReceivedEvent

    //PotionBrewEvent todo brew book
}
