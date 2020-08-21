package com.deeplake.idealland.enchantments;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.meta.MetaUtil;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.EntityUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ModEnchantmentInit {

    public static final EntityEquipmentSlot[] aentityequipmentslot = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
    public static final EntityEquipmentSlot[] allSlots = EntityEquipmentSlot.values();
    public static final EntityEquipmentSlot[] handSlots = new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND};
    public static final EntityEquipmentSlot[] mainHand = new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND};

    public static final RegistryNamespaced<ResourceLocation, Enchantment> REGISTRY = net.minecraftforge.registries.GameData.getWrapper(Enchantment.class);
    public static final List<Enchantment> ENCHANTMENT_LIST = new ArrayList<Enchantment>();

//    public static final ModEnchantmentBase enchant_one = new ModEnchantmentBase("idealland.test_enchantment", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ALL,  aentityequipmentslot)
//            .setHidden(true);

    //Attacking - sword
    public static final ModEnchantmentBase ANTI_VANILLA = new ModEnchantmentBase("idealland.anti_vanilla", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(0.3f, 0.3f);
    public static final ModEnchantmentBase ANTI_MOD = new ModEnchantmentBase("idealland.anti_mod", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(0.15f, 0.15f);
    public static final ModEnchantmentBase ANTI_AOA = new ModEnchantmentBase("idealland.anti_aoa", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(0.3f, 0.3f);
    public static final ModEnchantmentBase ANTI_GOG = new ModEnchantmentBase("idealland.anti_gog", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(0.3f, 0.3f);

    public static final ModEnchantmentBase POISONED = new ModEnchantmentBase("idealland.poison_touch", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(4).setValue(5f, 1f);

    public static final ModEnchantmentBase DEATH_BRINGER = new ModEnchantmentBase("idealland.death_bringer", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(5f, 5f);

    public static final ModEnchantmentBase ARMOR_PIERCE = new ModEnchantmentBase("idealland.armor_pierce", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(0.1f, 0.1f);

    //defend
    public static final ModEnchantmentProtection ANTI_VANILLA_PROTECTION = (ModEnchantmentProtection) new ModEnchantmentProtection("idealland.anti_vanilla_protection", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR,  aentityequipmentslot)
            .setMaxLevel(10).setRarityModifier(0.5f, 0.8f);
    public static final ModEnchantmentProtection ANTI_MOD_PROTECTION = (ModEnchantmentProtection) new ModEnchantmentProtection("idealland.anti_mod_protection", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR,  aentityequipmentslot)
            .setMaxLevel(10);
    public static final ModEnchantmentProtection MANA_RESIST = (ModEnchantmentProtection) new ModEnchantmentProtection("idealland.anti_magic_protection", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR,  aentityequipmentslot)
            .setMaxLevel(10);

    //Misc
    public static final ModEnchantmentLover LOVER = (ModEnchantmentLover) new ModEnchantmentLover("idealland.lover", Enchantment.Rarity.RARE, EnumEnchantmentType.ALL,  allSlots)
            .setMaxLevel(10);

    public static final ModEnchantmentBase WATER_FORM = new ModEnchantmentBase("idealland.water_form", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE,  allSlots).setRarityModifier(4f,1f);
    public static final ModEnchantmentBase FIRE_FORM = new ModEnchantmentBase("idealland.fire_form", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE,  allSlots).setRarityModifier(3f,1f);
    //public static final ModEnchantmentBase MANA_FORM = new ModEnchantmentBase("idealland.fire_form", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE,  allSlots);

    //Conflict groups
    public static final Enchantment[] WEAPON_DAMAGE_CONFLICT_GROUP = new Enchantment[]
            {
                    Enchantments.SHARPNESS,
                    Enchantments.SMITE,
                    Enchantments.BANE_OF_ARTHROPODS,
                    ANTI_VANILLA,
                    ANTI_MOD,
                    ANTI_AOA,
                    ANTI_GOG,
            };

    public static final Enchantment[] ARMOR_PROTECT_CONFLICT_GROUP = new Enchantment[]
            {
                    Enchantments.PROTECTION,
                    Enchantments.PROJECTILE_PROTECTION,
                    Enchantments.FIRE_PROTECTION,
                    Enchantments.BLAST_PROTECTION,
                    ANTI_VANILLA_PROTECTION,
                    ANTI_MOD_PROTECTION,
            };

    public static final Enchantment[] MATERIAL_DEFINE = new Enchantment[]
            {
                    FIRE_FORM,
                    WATER_FORM,
            };


    public static void BeforeRegister()
    {
        ANTI_AOA.setHidden(!MetaUtil.isLoaded_AOA3);
        ANTI_GOG.setHidden(!MetaUtil.isLoaded_GOG);

        ApplyConflictGroup(MATERIAL_DEFINE);
        ApplyConflictGroup(ARMOR_PROTECT_CONFLICT_GROUP);
        ApplyConflictGroup(WEAPON_DAMAGE_CONFLICT_GROUP);
    }

    private static void ApplyConflictGroup(Enchantment[] group)
    {
        for (Enchantment ench:
                group
        ) {
            //is my enchants
            if (ench instanceof ModEnchantmentBase)
            {
                ((ModEnchantmentBase) ench).setConflicts(group);
            }
        }
    }


    @SubscribeEvent
    public static void onCreatureHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        if (evt.isCanceled() || world.isRemote)
        {
            return;
        }

        float dmgModifier = 1f;

        Entity trueSource = evt.getSource().getTrueSource();
        if (trueSource instanceof EntityLivingBase) {

            EntityLivingBase attacker = (EntityLivingBase) trueSource;

            int enchLv = DEATH_BRINGER.getLevelOnCreature(attacker);
            //sudden demise
            if (enchLv > 0)
            {
                float rateBase = DEATH_BRINGER.getValue(attacker) / 100f;
                rateBase *= evt.getAmount() / hurtOne.getMaxHealth();

                if (rateBase >= attacker.getRNG().nextFloat())
                {
                    evt.getSource().setDamageBypassesArmor();
                    evt.setAmount(hurtOne.getMaxHealth() * 100f);
                }
            }

            //imbue
            enchLv = POISONED.getLevelOnCreature(attacker);
            if (enchLv > 0)
            {
                EntityUtil.ApplyBuff(hurtOne, MobEffects.POISON, enchLv - 1, POISONED.getValue(attacker));
            }

            //armor pierce
            enchLv = ARMOR_PIERCE.getLevelOnCreature(attacker);
            if (enchLv > 0)
            {
                if (ARMOR_PIERCE.getValue(enchLv) >= attacker.getRNG().nextFloat()) {
                    evt.getSource().setDamageBypassesArmor();
                }
            }



            //mods(ATK)
            if (EntityUtil.isVanillaResident(hurtOne))
            {
                dmgModifier += ANTI_VANILLA.getValue(attacker);
            }
            else if (EntityUtil.isOtherworldAggression(hurtOne)){
                dmgModifier += ANTI_MOD.getValue(attacker);
                if (EntityUtil.isAOA3Creature(hurtOne))
                {
                    dmgModifier += ANTI_AOA.getValue(attacker);
                }

                if (EntityUtil.isGOGCreature(hurtOne))
                {
                    dmgModifier += ANTI_GOG.getValue(attacker);
                }
            }

            //mods(DEF)
            if (EntityUtil.isVanillaResident(attacker))
            {
                dmgModifier *= ANTI_VANILLA_PROTECTION.getValue(hurtOne);
            }
            else if (EntityUtil.isOtherworldAggression(attacker)){
                dmgModifier *= ANTI_MOD_PROTECTION.getValue(hurtOne);
            }

            if (evt.getSource().isMagicDamage())
            {
                dmgModifier *= MANA_RESIST.getValue(hurtOne);
            }

        }

        evt.setAmount(evt.getAmount() * dmgModifier);
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();

        for (EntityEquipmentSlot slot:
                EntityEquipmentSlot.values()) {
            checkItemForTicking(livingBase, slot);
        }
    }

    public static void checkItemForTicking(EntityLivingBase living, EntityEquipmentSlot slot)
    {
        World world = living.world;

        ItemStack stack = living.getItemStackFromSlot(slot);
        if (!stack.isEmpty())
        {
            if (world.getWorldTime() % TICK_PER_SECOND == 0)
            {
                if (living.isWet())
                {
                    if (WATER_FORM.getEnchantmentLevel(stack) > 0)
                    {
                        if (world.isRemote)
                        {
                            if (stack.isItemDamaged())
                            {
                                EntityUtil.SpawnParticleAround(living, EnumParticleTypes.WATER_BUBBLE,10);
                            }
                        }else {
                            CommonFunctions.RepairItem(stack, 1);
                        }
                    }
                }

                if (living.isBurning())
                {
                    if (FIRE_FORM.getEnchantmentLevel(stack) > 0)
                    {
                        if (world.isRemote)
                        {
                            if (stack.isItemDamaged())
                            {
                                EntityUtil.SpawnParticleAround(living, EnumParticleTypes.FLAME,10);
                            }
                        }else {
                            CommonFunctions.RepairItem(stack, 1);
                        }
                    }
                }
            }
        }
    }

    void OnJump(LivingEvent.LivingJumpEvent event)
    {

    }
}
