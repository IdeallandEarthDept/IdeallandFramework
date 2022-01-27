package com.somebody.idlframewok.enchantments;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.PlayerUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.List;
import java.util.UUID;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.DEFERED_PAIN;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsEnchantment {

//    @SubscribeEvent
//    public static void onCreatureStruck(LivingHurtEvent evt) {
//        World world = evt.getEntity().getEntityWorld();//获取世界
//        EntityLivingBase hurtOne = evt.getEntityLiving();//获取被打的生物
//
//        if (evt.isCanceled() || world.isRemote) {//没打中或者本地端不执行
//            return;
//        }
//
//        Entity trueSource = evt.getSource().getTrueSource();//获取攻击者
//        if (trueSource instanceof EntityLivingBase) {//攻击者确实存在，且是一个生物
//            EntityLivingBase attacker = (EntityLivingBase) trueSource;//把攻击者类型转化为生物
//            if (ModEnchantmentInit.THUNDER_ATK.getLevelOnCreature(attacker) > 0) {//攻击者有雷击附魔
//                world.addWeatherEffect(new EntityLightningBolt(world, hurtOne.posX, hurtOne.posY, hurtOne.posZ, false));//召唤一个雷
//            }
//        }
//    }


    public static final DamageSource PAIN_DEFER = (new DamageSource("pain_defer")).setDamageIsAbsolute().setDamageBypassesArmor();

    public static void onCheckDamageDefer(LivingHurtEvent evt)
    {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        if (evt.isCanceled() ||
                world.isRemote ||
                evt.getSource().getDamageType().equals(PAIN_DEFER.getDamageType())) {
            return;
        }

        //will frequent attack disrupt the invincible ticks?

        ModEnchantmentBase ench = ModEnchantmentInit.DEFER_DAMAGE;
        if (ench.appliedOnCreature(hurtOne)) {
            evt.setCanceled(true);
            IDLNBTUtil.setIntAuto(hurtOne, DEFERED_PAIN, (int) Math.ceil(evt.getAmount()));

            //doesn't feel correct. damage too much or too fast?
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        IDLNBTUtil.setIntAuto(event.player, DEFERED_PAIN, 0);
    }

    @SubscribeEvent
    public static void onCreatureHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().world;
        EntityLivingBase hurtOne = evt.getEntityLiving();

        float ySeaLevel = world.getSeaLevel();
        float yMax = world.getActualHeight();

        int hurtAllyCount = 0;
        int attackAllyCount = 0;

        if (evt.isCanceled() || world == null || world.isRemote) {
            return;
        }

        if (evt.getSource() == DamageSource.OUT_OF_WORLD || evt.getSource().getTrueSource() == null )
        {
            return;
        }

        double dmgModifier = 1f;
        float dmgModifierPlus = 0f;

        float extraModifier = 0.5f;

        float protectionModifier = 0f;

        //biome
        Biome biome = EntityUtil.getBiomeForEntity(hurtOne);
        if (biome.isSnowyBiome()) {
            protectionModifier -= ModEnchantmentInit.SNOWY_DEF.getValue(hurtOne);
            if (world.isRaining())
            {
                protectionModifier -= extraModifier * ModEnchantmentInit.SNOWY_DEF.getValue(hurtOne);
            }
        }

        if (BiomeManager.oceanBiomes.contains(biome))
        {
            protectionModifier -= ModEnchantmentInit.OCEAN_DEF.getValue(hurtOne);
            if (hurtOne.isInWater()) {
                protectionModifier -= extraModifier * ModEnchantmentInit.OCEAN_DEF.getValue(hurtOne);
            }
        }

        if (EntityUtil.isSunlit(hurtOne))
        {
            protectionModifier -= ModEnchantmentInit.SUNNY_DEF.getValue(hurtOne);
        }

        if (EntityUtil.isMoonlit(hurtOne))
        {
            protectionModifier -= ModEnchantmentInit.MOON_DEF.getValue(hurtOne) * hurtOne.world.getCurrentMoonPhaseFactor();
        }

        if (hurtOne.isRiding())
        {
            protectionModifier -= ModEnchantmentInit.RIDING_DEF.getValue(hurtOne);
        }

        //mods(DEF)
        if (evt.getSource().isMagicDamage()) {
            protectionModifier -= ModEnchantmentInit.MANA_RESIST.getValue(hurtOne);
        }

        //numbers
        List<EntityLivingBase> nearby = EntityUtil.getEntitiesWithinAABB(world, EntityLivingBase.class, hurtOne.getPositionVector(), 16f, null);
        for (EntityLivingBase creature : nearby) {
            if (creature == hurtOne)
            {
                continue;
            }

            if (EntityUtil.getAttitude(hurtOne, creature) == EntityUtil.EnumAttitude.FRIEND)
            {
                hurtAllyCount++;
            }
        }
        dmgModifierPlus -= hurtAllyCount * ModEnchantmentInit.HORDE_DEF.getValue(hurtOne);

        //altitude
        float yDef = hurtOne.getPosition().getY();
        if (yDef > ySeaLevel)
        {
            dmgModifier -= (yDef - ySeaLevel) / (yMax - ySeaLevel) * ModEnchantmentInit.HEIGHT_DEF.getValue(hurtOne);
        }
        else {
            dmgModifier -= (ySeaLevel - yDef) / (ySeaLevel) * ModEnchantmentInit.INV_HEIGHT_DEF.getValue(hurtOne);
        }

        if (ModEnchantmentInit.AFFINITY_DIAMOND.appliedOnCreature(hurtOne))
        {
            dmgModifierPlus -= ModEnchantmentInit.AFFINITY_DIAMOND.getDamageModifierPlus(hurtOne);
        }

        if (ModEnchantmentInit.AFFINITY_GOLD.appliedOnCreature(hurtOne))
        {
            dmgModifierPlus -= ModEnchantmentInit.AFFINITY_GOLD.getDamageModifierPlus(hurtOne);
        }

        if (ModEnchantmentInit.AFFINITY_IRON.appliedOnCreature(hurtOne))
        {
            dmgModifierPlus -= ModEnchantmentInit.AFFINITY_IRON.getDamageModifierPlus(hurtOne);
        }

        //need attacker
        Entity trueSource = evt.getSource().getTrueSource();
        if (trueSource instanceof EntityLivingBase) {

            EntityLivingBase attacker = (EntityLivingBase) trueSource;

            int enchLv = ModEnchantmentInit.DEATH_BRINGER.getLevelOnCreature(attacker);
            //sudden demise
            if (enchLv > 0) {
                float rateBase = ModEnchantmentInit.DEATH_BRINGER.getValue(attacker) / 100f;
                rateBase *= evt.getAmount() / hurtOne.getMaxHealth();

                if (rateBase >= attacker.getRNG().nextFloat()) {
                    evt.getSource().setDamageBypassesArmor();
                    dmgModifierPlus = (hurtOne.getMaxHealth() * 100f);
                }
            }

            enchLv = ModEnchantmentInit.THUNDER_ATK.getLevelOnCreature(attacker);
            //thunder struck
            if (enchLv > 0) {
                float rateBase = ModEnchantmentInit.THUNDER_ATK.getValue(attacker);
                if (world.isThundering())
                {
                    rateBase *= 2;
                }
                if (rateBase >= attacker.getRNG().nextFloat()) {
                    world.addWeatherEffect(new EntityLightningBolt(world, hurtOne.posX, hurtOne.posY, hurtOne.posZ, false));
                }
            }
            //THUNDER_ATK

            if (attacker.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty())
            {
                dmgModifier += ModEnchantmentInit.NAKE_CHEST_ATTACK.getValue(attacker);
            }

            //imbue
            enchLv = ModEnchantmentInit.POISONED.getLevelOnCreature(attacker);
            if (enchLv > 0) {
                EntityUtil.ApplyBuff(hurtOne, MobEffects.POISON, enchLv - 1, ModEnchantmentInit.POISONED.getValue(attacker));
            }

            enchLv = ModEnchantmentInit.POISONED_SKIN.getLevelOnCreature(hurtOne);
            if (enchLv > 0) {
                EntityUtil.ApplyBuff(attacker, MobEffects.POISON, enchLv - 1, ModEnchantmentInit.POISONED_SKIN.getValue(hurtOne));
            }

            enchLv = ModEnchantmentInit.SLOWNESS.getLevelOnCreature(attacker);
            if (enchLv > 0) {
                EntityUtil.ApplyBuff(hurtOne, MobEffects.SLOWNESS, enchLv - 1, ModEnchantmentInit.SLOWNESS.getValue(attacker));
            }

            if (attacker instanceof EntityPlayer && ModEnchantmentInit.GOLD_TOUCH.appliedOnCreature(attacker))
            {
                int goldBlock = 0;
                int goldIngot = 0;
                int goldNugget = 0;

                int damage = (int) evt.getAmount();
                goldBlock += damage / 27;
                if (goldBlock > 64)
                {
                    goldBlock = 64;
                }
                goldIngot += (damage % 27) / 9;
                goldNugget += damage % 9;

                if (goldBlock > 0)
                {
                    PlayerUtil.giveToPlayer((EntityPlayer) attacker, new ItemStack(Blocks.GOLD_BLOCK, goldBlock));
                }

                if (goldIngot > 0)
                {
                    PlayerUtil.giveToPlayer((EntityPlayer) attacker, new ItemStack(Items.GOLD_INGOT, goldIngot));
                }

                if (goldNugget > 0)
                {
                    PlayerUtil.giveToPlayer((EntityPlayer) attacker, new ItemStack(Items.GOLD_NUGGET, goldNugget));
                }
            }

            //armor pierce
            enchLv = ModEnchantmentInit.ARMOR_PIERCE.getLevelOnCreature(attacker);
            if (enchLv > 0) {
                if (ModEnchantmentInit.ARMOR_PIERCE.getValue(enchLv) >= attacker.getRNG().nextFloat()) {
                    evt.getSource().setDamageBypassesArmor();
                }
            }

            //mods(ATK)
            if (EntityUtil.isVanillaResident(hurtOne)) {
                dmgModifier += ModEnchantmentInit.ANTI_VANILLA.getValue(attacker);
            } else if (EntityUtil.isOtherworldAggression(hurtOne)) {
                dmgModifier += ModEnchantmentInit.ANTI_MOD.getValue(attacker);
                if (EntityUtil.isAOA3Creature(hurtOne)) {
                    dmgModifier += ModEnchantmentInit.ANTI_AOA.getValue(attacker);
                }

                if (EntityUtil.isGOGCreature(hurtOne)) {
                    dmgModifier += ModEnchantmentInit.ANTI_GOG.getValue(attacker);
                }
            }

            if (hurtOne.getLookVec().dotProduct(
                    attacker.getPositionEyes(0f).subtract(hurtOne.getPositionEyes(0f)))
                    < 0) {
                dmgModifier += ModEnchantmentInit.BACKSTAB.getValue(attacker);
            }

            if (hurtOne.isWet()) {
                dmgModifier += ModEnchantmentInit.SHOCK.getValue(attacker);
                if (ModEnchantmentInit.SHOCK.appliedOnCreature(attacker))
                {
                    evt.getSource().setDamageBypassesArmor();
                }
            }

            Biome biomeAtk = EntityUtil.getBiomeForEntity(hurtOne);
            if (biomeAtk.isSnowyBiome()) {
                dmgModifier += ModEnchantmentInit.SNOWY_ATK.getValue(attacker);
                if (world.isRaining()) {
                    dmgModifier += extraModifier * ModEnchantmentInit.SNOWY_ATK.getValue(attacker);
                }
            }

            if (BiomeManager.oceanBiomes.contains(biomeAtk))
            {
                dmgModifier += ModEnchantmentInit.OCEAN_ATK.getValue(attacker);
                if (attacker.isInWater())
                {
                    dmgModifier += extraModifier * ModEnchantmentInit.OCEAN_ATK.getValue(attacker);
                }
            }

            if (EntityUtil.isSunlit(attacker))
            {
                dmgModifier += ModEnchantmentInit.SUNNY_ATK.getValue(attacker);
            }

            if (EntityUtil.isMoonlit(attacker))
            {
                dmgModifier += ModEnchantmentInit.MOON_ATK.getValue(attacker) * world.getCurrentMoonPhaseFactor();
            }

            if (attacker.isRiding())
            {
                dmgModifier += ModEnchantmentInit.RIDING_ATK.getValue(attacker);
                dmgModifier -= ModEnchantmentInit.AG_RIDING_DEF.getValue(hurtOne);
            }

            if (ModEnchantmentInit.WOUND_ATTACK_BUFF.appliedOnCreature(attacker))
            {
                dmgModifier += ModEnchantmentInit.WOUND_ATTACK_BUFF.getValue(attacker) * (attacker.getMaxHealth() - attacker.getHealth());
            }

            if (ModEnchantmentInit.ENEMY_WOUND_ATTACK_BUFF.appliedOnCreature(attacker))
            {
                dmgModifier += ModEnchantmentInit.ENEMY_WOUND_ATTACK_BUFF.getValue(attacker) * (hurtOne.getMaxHealth() - hurtOne.getHealth());
            }

            //Numbers
            List<EntityLivingBase> nearbyAtk = EntityUtil.getEntitiesWithinAABB(world, EntityLivingBase.class, attacker.getPositionVector(), 16f, null);
            for (EntityLivingBase creature : nearbyAtk) {
                if (creature == attacker)
                {
                    continue;
                }

                if (EntityUtil.getAttitude(attacker, creature) == EntityUtil.EnumAttitude.FRIEND)
                {
                    attackAllyCount++;
                }
            }

            dmgModifierPlus += attackAllyCount * ModEnchantmentInit.HORDE_ATK.getValue(attacker);
            dmgModifierPlus += hurtAllyCount * ModEnchantmentInit.KILL_HORDE.getValue(attacker);

            if (hurtAllyCount == 0)
            {
                dmgModifier += ModEnchantmentInit.KILL_LONE.getValue(attacker);
            }

            //affinity
            if (ModEnchantmentInit.AFFINITY_DIAMOND.appliedOnCreature(attacker))
            {
                dmgModifierPlus += ModEnchantmentInit.AFFINITY_DIAMOND.getDamageModifierPlus(attacker);
            }

            if (ModEnchantmentInit.AFFINITY_GOLD.appliedOnCreature(attacker))
            {
                dmgModifierPlus += ModEnchantmentInit.AFFINITY_GOLD.getDamageModifierPlus(attacker);
            }

            if (ModEnchantmentInit.AFFINITY_IRON.appliedOnCreature(attacker))
            {
                dmgModifierPlus += ModEnchantmentInit.AFFINITY_IRON.getDamageModifierPlus(attacker);
            }

            if (ModEnchantmentInit.ARMOR_OFFENSE.appliedOnCreature(attacker))
            {
                dmgModifierPlus += ModEnchantmentInit.ARMOR_OFFENSE.getValue(attacker) * EntityUtil.getAttr(attacker, SharedMonsterAttributes.ARMOR);
            }

            if (ModEnchantmentInit.WOUND_TO_ATTACK.appliedOnCreature(attacker))
            {
                dmgModifierPlus += ModEnchantmentInit.WOUND_TO_ATTACK.getValue(attacker) * (attacker.getMaxHealth() - attacker.getHealth());
            }

            //barefist
            if (ModEnchantmentInit.FIST_FIGHTER.appliedOnCreature(attacker) && attacker.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty())
            {
                dmgModifierPlus += ModEnchantmentInit.AFFINITY_IRON.getDamageModifierPlus(attacker);
            }

            //height atk

            float yAtk = attacker.getPosition().getY();
            if (yAtk > ySeaLevel)
            {
                dmgModifier += (yAtk - ySeaLevel) / (yMax - ySeaLevel) * ModEnchantmentInit.HEIGHT_ATK.getValue(attacker);
            }
            else {
                dmgModifier += (ySeaLevel - yAtk) / (ySeaLevel) * ModEnchantmentInit.INV_HEIGHT_ATK.getValue(attacker);
            }


            enchLv = ModEnchantmentInit.FEEDING.getLevelOnCreature(attacker);
            if (enchLv > 0)
            {
                if (attacker instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) attacker;
                    player.getFoodStats().addStats(enchLv, 0);
                }
                else {
                    attacker.heal(enchLv / 2f);
                }
            }

            //DEF related to attacker
            if (EntityUtil.isVanillaResident(attacker)) {
                dmgModifier -= ModEnchantmentInit.ANTI_VANILLA_PROTECTION.getValue(hurtOne);
            } else if (EntityUtil.isOtherworldAggression(attacker)) {
                dmgModifier -= ModEnchantmentInit.ANTI_MOD_PROTECTION.getValue(hurtOne);
            }

            if (hurtOne.isRiding())
            {
                dmgModifier += ModEnchantmentInit.AG_RIDING_ATK.getValue(attacker);
            }

            dmgModifierPlus -= Math.max(ModEnchantmentInit.MERCY.getValue(attacker), ModEnchantmentInit.MERCY.getValue(hurtOne));
            dmgModifierPlus += Math.max(ModEnchantmentInit.BERSERK.getValue(attacker), ModEnchantmentInit.MERCY.getValue(hurtOne));

            if (ModEnchantmentInit.KB_REFLECT.appliedOnCreature(hurtOne))
            {
                float power = ModEnchantmentInit.WOUND_TO_ATTACK.getValue(attacker) * (attacker.getMaxHealth() - attacker.getHealth());
                EntityUtil.simpleKnockBack(power, hurtOne, attacker);
            }
        }

        if (!evt.getSource().isDamageAbsolute())
        {
            dmgModifier += protectionModifier;
            float oriAmount = evt.getAmount();
            if (dmgModifier < ModConfig.ENCHANT_CONF.MIN_MULTIPLIER)
            {
                dmgModifier = ModConfig.ENCHANT_CONF.MIN_MULTIPLIER;
            }
            evt.setAmount((float) (oriAmount * dmgModifier + dmgModifierPlus));

            if (ModEnchantmentInit.MAGIC_IMMUNE.appliedOnCreature(hurtOne))
            {
                evt.setAmount(0);
                evt.setCanceled(true);
                return;
            }
            checkBareHandDodge(evt, hurtOne);

        }

        checkWearoutAllCurse(evt, hurtOne);

        onCheckDamageDefer(evt);
    }

    private static void checkBareHandDodge(LivingHurtEvent evt, EntityLivingBase hurtOne) {
        //barefist
        if (ModEnchantmentInit.FIST_FIGHTER.appliedOnCreature(hurtOne) && hurtOne.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty())
        {
            if (hurtOne.getRNG().nextFloat() <= 0.04f * ModEnchantmentInit.FIST_FIGHTER.getLevelOnCreature(hurtOne))
            {
                evt.setAmount(1);
                //evt.setCanceled(true);
            }
        }
    }

    private static void checkWearoutAllCurse(LivingHurtEvent evt, EntityLivingBase hurtOne) {
        if (!evt.isCanceled() && ModEnchantmentInit.WEAROUT_ALL_ON_HURT.appliedOnCreature(hurtOne))
        {
            for (EntityEquipmentSlot slot :
                    EntityEquipmentSlot.values()) {
                ItemStack stack = hurtOne.getItemStackFromSlot(slot);
                if (!stack.isEmpty())
                {
                    stack.damageItem(1, hurtOne);
                }
            }
        }
    }

    static final UUID UUID_ABYSS_REGEN = UUID.fromString("a9875952-c6ff-41dc-a54d-a40cfa54f447");

    @SubscribeEvent
    public static void onTeleport(EnderTeleportEvent evt)
    {
        float range = 5f;
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase teleportee = evt.getEntityLiving();
        Vec3d pos = evt.getEntity().getPositionEyes(0);
        Vec3d pos2 = new Vec3d(evt.getTargetX(), evt.getTargetY(), evt.getTargetZ());
        if (!world.isRemote) {
            //wielder stops nearby end-power teleporting and damage teleporters.
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, IDLGeneral.ServerAABB(pos.addVector(-range, -range, -range), pos.addVector(range, range, range)));
            for (EntityLivingBase living : list) {
                if (ModEnchantmentInit.NO_TELEPORT.appliedOnCreature(living)) {
                    //play sound
                    world.playSound(null, living.getPosition(), SoundEvents.BLOCK_NOTE_HARP, SoundCategory.AMBIENT, 1f, 1f);
                    evt.setCanceled(true);
                    return;
                }
            }

            list = world.getEntitiesWithinAABB(EntityLivingBase.class, IDLGeneral.ServerAABB(pos2.addVector(-range, -range, -range), pos2.addVector(range, range, range)));
            for (EntityLivingBase living : list) {
                if (ModEnchantmentInit.NO_TELEPORT.appliedOnCreature(living)) {
                    //play sound
                    world.playSound(null, living.getPosition(), SoundEvents.BLOCK_NOTE_HARP, SoundCategory.AMBIENT, 1f, 1f);
                    evt.setCanceled(true);
                    return;
                }
            }
        } else {
            //currently do nothing
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    static void checkAED(LivingDeathEvent event)
    {
        EntityLivingBase diedOne = event.getEntityLiving();
        if (event.isCanceled() || diedOne.world == null || diedOne.world.isRemote || !ModEnchantmentInit.AED.appliedOnCreature(diedOne))
        {
            return;
        }

        event.setCanceled(true);
        EntityUtil.TryRemoveDebuff(diedOne);
        diedOne.setHealth(diedOne.getMaxHealth() * 0.3f);

        ModEnchantmentBase aed = ModEnchantmentInit.AED;

        for (EntityEquipmentSlot slot:
                aed.applicableEquipmentTypesOpen) {
            ItemStack stack = diedOne.getItemStackFromSlot(slot);

            if (aed.getEnchantmentLevel(stack) > 0)
            {
                if (diedOne instanceof EntityPlayer)
                {
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, diedOne, "idlframewok.msg.aed_trigger");
                }

                NBTTagList nbttaglist = stack.getEnchantmentTagList();

                int fonndIndex = -1;
                for (int j = 0; j < nbttaglist.tagCount(); ++j)
                {
                    NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
                    int k = nbttagcompound.getShort("id");
                    //int l = nbttagcompound.getShort("lvl");
                    Enchantment enchantment = Enchantment.getEnchantmentByID(k);

                    if (enchantment == aed)
                    {
                        fonndIndex = j;
                        break;
                    }
                }

                if (fonndIndex != -1)
                {
                    nbttaglist.removeTag(fonndIndex);
                    return;
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCreatureDie(LivingDeathEvent event)
    {
        if (event.isCanceled() || event.getEntityLiving().getEntityWorld().isRemote)
        {
            return;
        }

        EntityLivingBase diedOne = event.getEntityLiving();

        Entity trueSource = event.getSource().getTrueSource();
        if (trueSource instanceof EntityLivingBase) {

            EntityLivingBase attacker = (EntityLivingBase) trueSource;

            int enchLv = ModEnchantmentInit.REPAIR_ON_KILL.getLevelOnCreature(attacker);
            if (enchLv > 0)
            {
                for (EntityEquipmentSlot slot :
                        EntityEquipmentSlot.values()) {
                    ItemStack stack = attacker.getItemStackFromSlot(slot);
                    CommonFunctions.repairItem(stack, enchLv);
                }
            }

            enchLv = ModEnchantmentInit.HEAL_ON_KILL.getLevelOnCreature(attacker);
            if (enchLv > 0)
            {
                attacker.heal(enchLv);
            }
        }
    }

    public static Multimap<String, AttributeModifier> getAttributeModifiersAbsRegen()
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(UUID_ABYSS_REGEN,"Abyssal Regen", 0.1, 1));

        return multimap;
    }

    @SubscribeEvent
    public static void onLivingHealEvent(LivingHealEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();
        World world = livingBase.getEntityWorld();

        if (ModEnchantmentInit.NO_HEAL.appliedOnCreature(livingBase))
        {
            event.setCanceled(true);
            return;
        }
    }

    @SubscribeEvent
    public static void onSetAttackTarget(LivingSetAttackTargetEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();
        World world = livingBase.getEntityWorld();

        if (world.isRemote || event.getTarget() == null)
        {
            return;
        }

        EntityLivingBase target = event.getTarget();
        if (target == null || !EntityUtil.canSee(livingBase, target))
        {
            return;
        }
        //canEntityBeSeen

        if (ModEnchantmentInit.FIRE_SIGHT.appliedOnCreature(target))
        {
            event.getEntityLiving().setFire(8);
        }

        if (ModEnchantmentInit.LIT_SIGHT.appliedOnCreature(target))
        {
            EntityUtil.ApplyBuff(event.getEntityLiving(), MobEffects.GLOWING, 0, 10);
        }

        if (ModEnchantmentInit.SLOW_SIGHT.appliedOnCreature(target))
        {
            EntityUtil.ApplyBuff(event.getEntityLiving(), MobEffects.SLOWNESS, ModEnchantmentInit.SLOWNESS.getLevelOnCreature(target) - 1, 3);
        }
    }

    @SubscribeEvent
    public static void onProjectile(ProjectileImpactEvent event)
    {
        Entity projectile = event.getEntity();
        if (!projectile.getEntityWorld().isRemote)
        {
            RayTraceResult rayTraceResult = event.getRayTraceResult();
            Entity hurtOne = rayTraceResult.entityHit;

            if (hurtOne instanceof EntityLivingBase && ModEnchantmentInit.DEFLECT_ARMOR.appliedOnCreature((EntityLivingBase) hurtOne))
            {
                boolean success = ((EntityLivingBase) hurtOne).getRNG().nextFloat() < ModEnchantmentInit.DEFLECT_ARMOR.getValue((EntityLivingBase) hurtOne);
                if (success)
                {
                    projectile.setVelocity(-projectile.motionX, -projectile.motionY, -projectile.motionZ);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase livingBase = event.getEntityLiving();
        World world = livingBase.getEntityWorld();

        if (CommonFunctions.isSecondTick(world))
        {
            for (EntityEquipmentSlot slot :
                    EntityEquipmentSlot.values()) {
                checkItemForTicking(livingBase, slot);
            }

            if (!world.isRemote && !livingBase.isDead)
            {
                int defer_damage = IDLNBTUtil.GetIntAuto(livingBase, DEFERED_PAIN, 0);
                if (defer_damage > 0)
                {
                    Idealland.Log("dfr dmg = %d", defer_damage);
                    int level =  ModEnchantmentInit.DEFER_DAMAGE.getLevelOnCreature(livingBase);
                    float maxAllowDamage = level == 0 ? livingBase.getMaxHealth() : livingBase.getMaxHealth() / ModEnchantmentInit.DEFER_DAMAGE.getValue(livingBase);

                    float damageThisTick = defer_damage >= maxAllowDamage ? maxAllowDamage : defer_damage;

                    livingBase.attackEntityFrom(PAIN_DEFER, damageThisTick);
                    IDLNBTUtil.setIntAuto(livingBase, DEFERED_PAIN, (int) (defer_damage - Math.ceil(damageThisTick)));
                }

                handleSunBurn(livingBase);

                //Arknights
                boolean hasAbysalRegen = ModEnchantmentInit.OCEAN_REGEN.appliedOnCreature(livingBase);
                if(hasAbysalRegen)
                {
                    livingBase.heal(livingBase.getMaxHealth() * 0.02f);
                    livingBase.getAttributeMap().applyAttributeModifiers(getAttributeModifiersAbsRegen());
                }
                else {
                    livingBase.getAttributeMap().removeAttributeModifiers(getAttributeModifiersAbsRegen());
                }
            }
        }

        handleFlowerWalk(event.getEntityLiving());

        handleLastReach(livingBase, world);


    }

    private static void handleSunBurn(EntityLivingBase livingBase) {
        if (ModEnchantmentInit.SUN_BURN.appliedOnCreature(livingBase) && EntityUtil.isSunlit(livingBase))
        {
            ItemStack head = livingBase.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            if (head.isEmpty())
            {
                livingBase.setFire(2);
            }
            else {
                head.damageItem(1, livingBase);
            }
        }
    }

    private static void handleLastReach(EntityLivingBase livingBase, World world) {
        if (livingBase instanceof EntityPlayer && !world.isRemote)
        {
            int lastLevel = IDLNBTUtil.GetInt(livingBase, LAST_REACH_KEY, 0);
            int curLevel = ModEnchantmentInit.REACH_DIG.getLevelOnCreature(livingBase);
            if (lastLevel != curLevel)
            {
                IDLNBTUtil.setInt(livingBase, LAST_REACH_KEY, curLevel);
                AttributeModifier LEVEL_BONUS_MODIFIER = (new AttributeModifier(LONG_REACH_UUID, "long reach", ModEnchantmentInit.REACH_DIG.getValue(curLevel), 0));
                if (livingBase.getEntityAttribute(EntityPlayer.REACH_DISTANCE).hasModifier(LEVEL_BONUS_MODIFIER)) {
                    livingBase.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeModifier(LONG_REACH_UUID);
                }
                livingBase.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(LEVEL_BONUS_MODIFIER);
                //CommonFunctions.SendMsgToPlayer((EntityPlayerMP) livingBase, String.format("Cur reach: %s", livingBase.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue()));
            }

            lastLevel = IDLNBTUtil.GetInt(livingBase, LAST_REACH_KEY_2, 0);
            curLevel = ModEnchantmentInit.LONG_REACH_ARMOR.getLevelOnCreature(livingBase);
            if (lastLevel != curLevel)
            {
                IDLNBTUtil.setInt(livingBase, LAST_REACH_KEY_2, curLevel);
                AttributeModifier LEVEL_BONUS_MODIFIER = (new AttributeModifier(LONG_REACH_2_UUID, "long reach 2", ModEnchantmentInit.LONG_REACH_ARMOR.getValue(curLevel), 0));
                if (livingBase.getEntityAttribute(EntityPlayer.REACH_DISTANCE).hasModifier(LEVEL_BONUS_MODIFIER)) {
                    livingBase.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeModifier(LONG_REACH_2_UUID);
                }
                livingBase.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(LEVEL_BONUS_MODIFIER);
                //CommonFunctions.SendMsgToPlayer((EntityPlayerMP) livingBase, String.format("Cur reach: %s", livingBase.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue()));
            }
            //Idealland.Log("curLevel = %d", curLevel);
        }
    }

    enum BoneMealMode{
        UNDER,
        INSIDE
    }
    static void castBoneMeal(EntityLivingBase livingBase, BoneMealMode mode, boolean acceptSapling)
    {
        World world = livingBase.getEntityWorld();
        BlockPos blockPos = mode == BoneMealMode.UNDER ? livingBase.getPosition().down(1) : livingBase.getPosition();
        IBlockState iblockstate = world.getBlockState(blockPos);

        if (livingBase instanceof EntityPlayer)
        {
            ForgeEventFactory.onApplyBonemeal((EntityPlayer) livingBase, world, blockPos, iblockstate, new ItemStack(Items.DYE), null);
        }

        Block block = iblockstate.getBlock();
        if (block instanceof IGrowable && (acceptSapling || !(block instanceof BlockSapling)))
        {
            IGrowable igrowable = (IGrowable)iblockstate.getBlock();

            if (igrowable.canGrow(world, blockPos, iblockstate, world.isRemote))
            {
                if (!world.isRemote)
                {
                    if (igrowable.canUseBonemeal(world, world.rand, blockPos, iblockstate))
                    {
                        igrowable.grow(world, world.rand, blockPos, iblockstate);
                    }
                }
            }
        }
    }

    static void handleFlowerWalk(EntityLivingBase livingBase)
    {
        //speed judgement does not work at server side
        if (ModEnchantmentInit.FLOWER_WALK.appliedOnCreature(livingBase) && livingBase.onGround)
        {
            castBoneMeal(livingBase, BoneMealMode.UNDER, false);
        }

        if (ModEnchantmentInit.HARVEST_WALK.appliedOnCreature(livingBase) && livingBase.onGround)
        {
            castBoneMeal(livingBase, BoneMealMode.INSIDE, false);
        }
    }

    private static final String LAST_REACH_KEY = "last_reach";
    private static final String LAST_REACH_KEY_2 = "last_reach_2";
    private static final UUID LONG_REACH_UUID = UUID.fromString("f4420891-431e-4539-9253-c23ef72feba7");
    private static final UUID LONG_REACH_2_UUID = UUID.fromString("881ef9e3-a634-493d-afc7-dadd13e97abf");
    public static void checkItemForTicking(EntityLivingBase living, EntityEquipmentSlot slot)
    {
        World world = living.getEntityWorld();

        ItemStack stack = living.getItemStackFromSlot(slot);
        if (!stack.isEmpty())
        {
//            if (CommonFunctions.isSecondTick(world))
//            {
                if (ModEnchantmentInit.REPAIR_ALL.appliedOnCreature(living))
                {
                    if (world.isRemote)
                    {
                        if (stack.isItemDamaged())
                        {
                            EntityUtil.SpawnParticleAround(living, EnumParticleTypes.SMOKE_NORMAL,1);
                        }
                    }else {
                        CommonFunctions.repairItem(stack, 1);
                    }

                }

                if (living.getRNG().nextInt(10) < ModEnchantmentInit.FADING.getEnchantmentLevel(stack)) {
                    stack.damageItem(1, living);
                }

                if (living.isWet())
                {
                    if (ModEnchantmentInit.WATER_FORM.getEnchantmentLevel(stack) > 0)
                    {
                        if (world.isRemote)
                        {
                            if (stack.isItemDamaged())
                            {
                                EntityUtil.SpawnParticleAround(living, EnumParticleTypes.WATER_BUBBLE,10);
                            }
                        }else {
                            CommonFunctions.repairItem(stack, 10);
                        }
                    }
                }

                if (living.isBurning())
                {
                    if (ModEnchantmentInit.FIRE_FORM.getEnchantmentLevel(stack) > 0)
                    {
                        if (world.isRemote)
                        {
                            if (stack.isItemDamaged())
                            {
                                EntityUtil.SpawnParticleAround(living, EnumParticleTypes.FLAME,10);
                            }
                        }else {
                            CommonFunctions.repairItem(stack, 80);
                        }
                    }
                }

                if (ModEnchantmentInit.TIME_FORM.getEnchantmentLevel(stack) > 0)
                {
                    if (world.isRemote)
                    {
                        if (stack.isItemDamaged())
                        {
                            EntityUtil.SpawnParticleAround(living, EnumParticleTypes.SPELL,10);
                        }
                    }else {
                        CommonFunctions.repairItem(stack, 2);
                    }
                }

                if (EntityUtil.isSunlit(living)) {
                    if (ModEnchantmentInit.SUN_FORM.getEnchantmentLevel(stack) > 0) {
                        if (world.isRemote) {
                            if (stack.isItemDamaged()) {
                                EntityUtil.SpawnParticleAround(living, EnumParticleTypes.VILLAGER_HAPPY, 10);
                            }
                        } else {
                            CommonFunctions.repairItem(stack, 2);
                        }
                    }
                }
            //}
        }
    }
}
