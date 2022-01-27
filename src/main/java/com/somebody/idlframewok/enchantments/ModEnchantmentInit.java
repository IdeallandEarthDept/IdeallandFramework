package com.somebody.idlframewok.enchantments;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.MetaUtil;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ModEnchantmentInit {

    public static final EntityEquipmentSlot[] armorSlots = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
    public static final EntityEquipmentSlot[] allSlots = EntityEquipmentSlot.values();
    public static final EntityEquipmentSlot[] handSlots = new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND};
    public static final EntityEquipmentSlot[] mainHand = new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND};
    public static final EntityEquipmentSlot[] feet = new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND};
    public static final EntityEquipmentSlot[] chest = new EntityEquipmentSlot[] {EntityEquipmentSlot.CHEST};

    public static final RegistryNamespaced<ResourceLocation, Enchantment> REGISTRY = net.minecraftforge.registries.GameData.getWrapper(Enchantment.class);
    public static final List<Enchantment> ENCHANTMENT_LIST = new ArrayList<Enchantment>();

//    public static final ModEnchantmentBase enchant_one = new ModEnchantmentBase("idlframewok.test_enchantment", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ALL,  armorSlots)
//            .setHidden(true);

    //Attacking - sword
    public static final float initSPAtk = 0.15f, incrementSPAtk = 0.05f;
    public static final float initSPAtk2 = 0.3f, incrementSPAtk2 = 0.1f;

    //defense plus
    public static final float initSPDef = 0.08f, incrementSPDef = 0.08f;
    public static final float initHordeFactor = 0.5f, incrementHordeFactor = 0.5f;

    public static final ModEnchantmentBase ANTI_VANILLA = new ModEnchantmentBase("idlframewok.anti_vanilla", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk2, incrementSPAtk2);
    public static final ModEnchantmentBase ANTI_MOD = new ModEnchantmentBase("idlframewok.anti_mod", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);
    public static final ModEnchantmentBase ANTI_AOA = new ModEnchantmentBase("idlframewok.anti_aoa", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk2, incrementSPAtk2);
    public static final ModEnchantmentBase ANTI_GOG = new ModEnchantmentBase("idlframewok.anti_gog", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk2, incrementSPAtk2);

    public static final ModEnchantmentBase POISONED = new ModEnchantmentBase("idlframewok.poison_touch", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(4).setValue(5f, 1f);

    public static final ModEnchantmentBase SLOWNESS = new ModEnchantmentBase("idlframewok.slow_touch", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(4).setValue(5f, 1f);

    public static final ModEnchantmentBase GOLD_TOUCH = new ModEnchantmentBase("idlframewok.gold_touch", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(1);

    public static final ModEnchantmentBase POISONED_SKIN = new ModEnchantmentBase("idlframewok.poison_skin", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR,  armorSlots)
            .setMaxLevel(4).setValue(5f, 1f);

    public static final ModEnchantmentBase DEATH_BRINGER = new ModEnchantmentBase("idlframewok.death_bringer", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(5f, 5f);

    public static final ModEnchantmentBase ARMOR_PIERCE = new ModEnchantmentBase("idlframewok.armor_pierce", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(0.1f, 0.1f);

    //special attacks
    public static final ModEnchantmentBase BACKSTAB = new ModEnchantmentBase("idlframewok.backstab", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);

    public static final ModEnchantmentBase SHOCK = new ModEnchantmentBase("idlframewok.shock", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);

    public static final ModEnchantmentBase OCEAN_ATK = new ModEnchantmentBase("idlframewok.ocean_atk", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);

    public static final ModEnchantmentBase OCEAN_DEF = new ModEnchantmentProtectionClassical("idlframewok.ocean_def", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10);

    public static final ModEnchantmentBase SNOWY_ATK = new ModEnchantmentBase("idlframewok.snowy_atk", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);

    public static final ModEnchantmentBase SNOWY_DEF = new ModEnchantmentProtectionClassical("idlframewok.snowy_def", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10);

    public static final ModEnchantmentBase SUNNY_ATK = new ModEnchantmentBase("idlframewok.sunny_atk", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);

    public static final ModEnchantmentBase SUNNY_DEF = new ModEnchantmentProtectionClassical("idlframewok.sunny_def", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10);

    public static final ModEnchantmentBase MOON_ATK = new ModEnchantmentBase("idlframewok.lunar_atk", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk * 1.5f, incrementSPAtk * 1.5f);

    public static final ModEnchantmentBase MOON_DEF = new ModEnchantmentProtectionClassical("idlframewok.lunar_def", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10);

    public static final ModEnchantmentBase RIDING_ATK = new ModEnchantmentBase("idlframewok.riding_atk", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);

    public static final ModEnchantmentBase RIDING_DEF = new ModEnchantmentProtectionClassical("idlframewok.riding_def", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10);

    public static final ModEnchantmentBase AG_RIDING_ATK = new ModEnchantmentBase("idlframewok.ag_riding_atk", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);

    public static final ModEnchantmentBase AG_RIDING_DEF = new ModEnchantmentProtectionClassical("idlframewok.ag_riding_def", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10);

    //todo:chiboshangzhen
//    public static final ModEnchantmentBase NAKE_ATTACK = new ModEnchantmentBase("idlframewok.ag_riding_atk", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
//            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);

    public static final ModEnchantmentBase ARMOR_OFFENSE = new ModEnchantmentBase("idlframewok.armor_offense", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);

    public static final ModEnchantmentBase WOUND_ATTACK_BUFF = new ModEnchantmentBase("idlframewok.wound_atk", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);

    public static final ModEnchantmentBase ENEMY_WOUND_ATTACK_BUFF = new ModEnchantmentBase("idlframewok.enemy_wound_atk", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(10).setValue(initSPAtk, incrementSPAtk);

    public static final ModEnchantmentBase KB_REFLECT = new ModEnchantmentBase("idlframewok.kb_reflect", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(4).setValue(0.4f, 0.2f);

    public static final ModEnchantmentBase THUNDER_ATK = new ModEnchantmentBase("idlframewok.thunder_attack", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(10).setValue(0.1f, 0.1f);

    //Numbers
    public static final ModEnchantmentBase KILL_LONE = new ModEnchantmentBase("idlframewok.kill_lone", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(4).setValue(initSPAtk2, incrementSPAtk2);

    public static final ModEnchantmentBase KILL_HORDE = new ModEnchantmentBase("idlframewok.kill_horde", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(4).setValue(initHordeFactor, incrementHordeFactor);

    public static final ModEnchantmentBase HORDE_DEF = new ModEnchantmentBase("idlframewok.horde_def", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(4).setValue(initHordeFactor, incrementHordeFactor);

    public static final ModEnchantmentBase HORDE_ATK = new ModEnchantmentBase("idlframewok.horde_atk", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(4).setValue(initHordeFactor, incrementHordeFactor);

    //height
    public static final float heightInit = 0.5f, heightPlus = 0.25f;

    public static final ModEnchantmentBase HEIGHT_ATK = new ModEnchantmentBase("idlframewok.height_atk", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(3).setValue(heightInit, heightPlus);

    public static final ModEnchantmentBase HEIGHT_DEF = new ModEnchantmentBase("idlframewok.height_def", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(3).setValue(heightInit, heightPlus);

    public static final ModEnchantmentBase INV_HEIGHT_ATK = new ModEnchantmentBase("idlframewok.inv_height_atk", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(3).setValue(heightInit, heightPlus);

    public static final ModEnchantmentBase INV_HEIGHT_DEF = new ModEnchantmentBase("idlframewok.inv_height_def", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(3).setValue(heightInit, heightPlus);

    public static final ModEnchantmentBase FIST_FIGHTER = new ModEnchantmentBase("idlframewok.fist_fighter", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10).setValue(0.5f, 0.5f);

    //defend
    public static final ModEnchantmentBase ANTI_VANILLA_PROTECTION = new ModEnchantmentProtectionClassical("idlframewok.anti_vanilla_protection", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10).setRarityModifier(0.5f, 0.8f);
    public static final ModEnchantmentBase ANTI_MOD_PROTECTION = new ModEnchantmentProtectionClassical("idlframewok.anti_mod_protection", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10);
    public static final ModEnchantmentBase MANA_RESIST = new ModEnchantmentProtectionClassical("idlframewok.anti_magic_protection", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10);
    public static final ModEnchantmentBase DEFLECT_ARMOR = new ModEnchantmentBase("idlframewok.deflect_armor", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10).setValue(0.1f, 0.1f);

    public static final ModEnchantmentBase MERCY = new ModEnchantmentBase("idlframewok.mercy", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(10).setValue(1f, 1f);
    public static final ModEnchantmentBase BERSERK = new ModEnchantmentBase("idlframewok.berserk", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(10).setValue(1f, 1f);

    //Death
    public static final ModEnchantmentBase REPAIR_ON_KILL = new ModEnchantmentBase("idlframewok.repair_on_kill", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE, allSlots)
            .setMaxLevel(10);

    public static final ModEnchantmentBase HEAL_ON_KILL = new ModEnchantmentBase("idlframewok.heal_on_kill", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(10);

    public static final ModEnchantmentBase FEEDING =new ModEnchantmentBase("idlframewok.feeding", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(10);

    //Misc
    public static final ModEnchantmentLover LOVER = (ModEnchantmentLover) new ModEnchantmentLover("idlframewok.lover", Enchantment.Rarity.RARE, EnumEnchantmentType.ALL,  allSlots)
            .setMaxLevel(10);//its events are in the lover class

    public static final ModEnchantmentBase REACH_DIG = new ModEnchantmentBase("idlframewok.long_reach", Enchantment.Rarity.RARE, EnumEnchantmentType.DIGGER,  handSlots)
            .setMaxLevel(10).setValue(1f, 0.5f);

//    public static final ModEnchantmentBase REACH_ATK = new ModEnchantmentBase("idlframewok.long_reach_atk", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON,  mainHand)
//            .setMaxLevel(10).setValue(1f, 0.5f);

    public static final ModEnchantmentBase NO_TELEPORT = new ModEnchantmentBase("idlframewok.no_teleport", Enchantment.Rarity.RARE, EnumEnchantmentType.ALL, allSlots)
            .setMaxLevel(1);

    //Halo
    static float haloBase = 1.5f, haloDelta = 1.5f;
    public static final ModEnchantmentBase HALO_ARMOR = new ModEnchantmentHalo("idlframewok.halo_armor", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots, MobEffects.RESISTANCE)
            .setMaxLevel(2).setRarityModifier(haloBase, haloDelta);

    public static final ModEnchantmentBase HALO_ATTACK = new ModEnchantmentHalo("idlframewok.halo_attack", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots, MobEffects.STRENGTH)
            .setParticleTypes(EnumParticleTypes.CRIT_MAGIC)
            .setMaxLevel(4).setRarityModifier(haloBase, haloDelta);

    public static final ModEnchantmentBase HALO_HASTE = new ModEnchantmentHalo("idlframewok.halo_haste", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots, MobEffects.HASTE)
            .setParticleTypes(EnumParticleTypes.CRIT_MAGIC)
            .setMaxLevel(4).setRarityModifier(haloBase, haloDelta);

    public static final ModEnchantmentBase HALO_SPEED = new ModEnchantmentHalo("idlframewok.halo_speed", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots, MobEffects.SPEED)
            .setParticleTypes(EnumParticleTypes.CRIT_MAGIC)
            .setMaxLevel(4).setRarityModifier(haloBase, haloDelta);

    public static final ModEnchantmentBase HALO_HEAL_MINUS = new ModEnchantmentHalo("idlframewok.halo_heal_minus_tyrant", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots, ModPotions.HEAL_MINUS)
            .setParticleTypes(EnumParticleTypes.REDSTONE).setToAll()
            .setMaxLevel(4).setRarityModifier(haloBase, haloDelta).setAsCurse();

    public static final ModEnchantmentBase HALO_HEAL_PLUS = new ModEnchantmentHalo("idlframewok.halo_heal_plus", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots, ModPotions.HEAL_PLUS)
            .setParticleTypes(EnumParticleTypes.HEART)
            .setMaxLevel(4).setRarityModifier(haloBase, haloDelta);

    public static final ModEnchantmentBase HALO_CAST_MINUS = new ModEnchantmentHalo("idlframewok.halo_cast_minus", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots, ModPotions.HEAL_MINUS)
            .setParticleTypes(EnumParticleTypes.REDSTONE).setToAll()
            .setMaxLevel(3).setRarityModifier(haloBase, haloDelta);

    public static final ModEnchantmentBase HALO_CAST_PLUS = new ModEnchantmentHalo("idlframewok.halo_cast_plus", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots, ModPotions.HEAL_PLUS)
            .setParticleTypes(EnumParticleTypes.CRIT_MAGIC)
            .setMaxLevel(4).setRarityModifier(haloBase, haloDelta);

    public static final ModEnchantmentBase HALO_HEAL = new ModEnchantmentHaloHeal("idlframewok.halo_heal", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots, MobEffects.SPEED)
            .setParticleTypes(EnumParticleTypes.NOTE)
            .setMaxLevel(4).setRarityModifier(haloBase, haloDelta);

    public static final ModEnchantmentBase HALO_FLAME = new ModEnchantmentHaloFlame("idlframewok.halo_flame", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots, null)
            .setReqAttitude(EntityUtil.EnumAttitude.HATE).setRange(5f).setParticleTypes(EnumParticleTypes.FLAME)
            .setMaxLevel(1).setRarityModifier(2, 1);

    public static final ModEnchantmentBase BLADE_BULLET = new ModEnchantmentBase("idlframewok.blade_bullet", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(3).setAsTreasure().setValue(0.5f, 0.25f);

    public static final ModEnchantmentBase BLADE_FAN = new ModEnchantmentBase("idlframewok.blade_fan", Enchantment.Rarity.RARE, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(3).setAsTreasure().setValue(0.25f, 0.25f);

    public static final ModEnchantmentBase FIRE_SIGHT = new ModEnchantmentBase("idlframewok.fire_sight", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR_HEAD, armorSlots)
            .setMaxLevel(1).setAsTreasure();

    public static final ModEnchantmentBase LIT_SIGHT = new ModEnchantmentBase("idlframewok.lit_sight", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(1);

    public static final ModEnchantmentBase SLOW_SIGHT = new ModEnchantmentBase("idlframewok.slow_sight", Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR_HEAD, armorSlots)
            .setMaxLevel(4).setAsTreasure();

    public static final ModEnchantmentBase DEFER_DAMAGE = new ModEnchantmentBase("idlframewok.defer_damage", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots)
            .setMaxLevel(1).setValue(1f, 0.5f).setAsTreasure().setHidden(true);//.setValueAdvanced(5, 1f)

    public static final ModEnchantmentBase AED = new ModEnchantmentBase("idlframewok.aed", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_CHEST, chest)
            .setMaxLevel(1).setAsTreasure();

    //todo: also adds ingot and blocks
    public static final ModEnchantmentAffinity AFFINITY_DIAMOND = (ModEnchantmentAffinity) new ModEnchantmentAffinity("idlframewok.aff_diamond", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE, allSlots)
            .setArmorMaterial(ItemArmor.ArmorMaterial.DIAMOND).setToolMaterial(Item.ToolMaterial.DIAMOND)
            .setMaxLevel(10).setValue(0.5f, 0.25f);

    public static final ModEnchantmentAffinity AFFINITY_GOLD = (ModEnchantmentAffinity) new ModEnchantmentAffinity("idlframewok.aff_gold", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE, allSlots)
            .setArmorMaterial(ItemArmor.ArmorMaterial.GOLD).setToolMaterial(Item.ToolMaterial.GOLD)
            .setMaxLevel(10).setValue(0.5f, 0.25f);

    public static final ModEnchantmentAffinity AFFINITY_IRON = (ModEnchantmentAffinity) new ModEnchantmentAffinity("idlframewok.aff_iron", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE, allSlots)
            .setArmorMaterial(ItemArmor.ArmorMaterial.IRON).setToolMaterial(Item.ToolMaterial.IRON)
            .setMaxLevel(10).setValue(0.5f, 0.25f);

    //Arknights
    public static final ModEnchantmentBase OCEAN_REGEN = new ModEnchantmentBase("idlframewok.ocean_regen", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots)
            .setAsTreasure().setMaxLevel(1);

    //Grand Enchantment
    public static final ModEnchantmentBase REPAIR_ALL = new ModEnchantmentBase("idlframewok.repair_all", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.BREAKABLE, allSlots)
            .setAsTreasure().setMaxLevel(1).setRarityModifier(2,1);

    public static final ModEnchantmentBase MAGIC_IMMUNE = new ModEnchantmentBase("idlframewok.magic_immnue", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR, armorSlots)
            .setAsTreasure().setMaxLevel(1);

    public static final ModEnchantmentBase WOUND_TO_ATTACK = new ModEnchantmentBase("idlframewok.wound_to_attack", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, mainHand)
            .setAsTreasure().setMaxLevel(2).setValue(0.5f, 0.5f);

    public static final ModEnchantmentBase FLOWER_WALK = new ModEnchantmentBase("idlframewok.flower_walk", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_FEET, armorSlots)
            .setAsTreasure().setMaxLevel(1);
    public static final ModEnchantmentBase HARVEST_WALK = new ModEnchantmentBase("idlframewok.harvest_walk", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_FEET, armorSlots)
            .setAsTreasure().setMaxLevel(1);

        public static final ModEnchantmentBase NAKE_CHEST_ATTACK = new ModEnchantmentBase("idlframewok.nake_chest_atk", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON,  mainHand)
            .setMaxLevel(1).setValue(1f,1f).setAsTreasure();

    //adapting sword
    public static final ModEnchantmentBase ADAPT_DONT_DROP = new ModEnchantmentBase("idlframewok.adapt_no_drop", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(10).setValue(0.08f, 0.08f).setValueAdvanced(5, 0.1f).setHidden(true);

    public static final ModEnchantmentBase ADAPT_FAST = new ModEnchantmentBase("idlframewok.adapt_faster", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(10).setValue(1f, 1f).setValueAdvanced(5, 1f).setHidden(true);

    public static final ModEnchantmentBase ADAPT_ON_HIT = new ModEnchantmentBase("idlframewok.adapt_on_hit", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(1).setValue(0.2f, 0).setHidden(true);

    public static final ModEnchantmentBase ADAPT_OUTSTAND = new ModEnchantmentBase("idlframewok.adapt_outstand", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, mainHand)
            .setMaxLevel(10).setValue(0.05f, 0.05f).setValueAdvanced(5, 0.15f).setHidden(true);


    //fgo
    public static final ModEnchantmentBase LONG_REACH_ARMOR = new ModEnchantmentBase("idlframewok.long_reach_armor", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_CHEST,  armorSlots)
            .setValue(4f, 2f)
            .setAsTreasure().setMaxLevel(1);

    //form
    static float formBase = 1.5f, formDelta = 1f;
    public static final ModEnchantmentBase WATER_FORM = new ModEnchantmentBase("idlframewok.water_form", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE,  allSlots).setRarityModifier(formBase, formDelta);
    public static final ModEnchantmentBase FIRE_FORM = new ModEnchantmentBase("idlframewok.fire_form", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE,  allSlots).setRarityModifier(formBase, formDelta);
    public static final ModEnchantmentBase TIME_FORM = new ModEnchantmentBase("idlframewok.auto_repair", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE,  allSlots).setRarityModifier(formBase, formDelta);
    public static final ModEnchantmentBase SUN_FORM = new ModEnchantmentBase("idlframewok.sun_form", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE,  allSlots).setRarityModifier(formBase, formDelta);

    public static final ModEnchantmentBase EASY_REPAIR = new ModEnchantmentBase("idlframewok.easy_repair", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.BREAKABLE, allSlots)
            .setMaxLevel(2).setRarityModifier(2, 2);    //todo: test this

    public static final ModEnchantmentBase VOID_CYCLE = new ModEnchantmentBase("idlframewok.void_cycle", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE,  allSlots).setRarityModifier(formBase, formDelta).setHidden(true);

    //curse
    static float curseBase = 1.5f, curseDelta = 1f;
    public static final ModEnchantmentBase FADING = new ModEnchantmentBase("idlframewok.fading", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE,  allSlots).setRarityModifier(curseBase, curseDelta).setAsCurse().setMaxLevel(10);
    public static final ModEnchantmentBase SUN_BURN = new ModEnchantmentBase("idlframewok.sun_burn", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE, allSlots).setRarityModifier(curseBase, curseDelta).setAsCurse();
    public static final ModEnchantmentBase WEAROUT_ALL_ON_HURT = new ModEnchantmentBase("idlframewok.wearout_on_hurt", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE, allSlots).setRarityModifier(curseBase, curseDelta).setAsCurse();
    public static final ModEnchantmentBase NO_HEAL = new ModEnchantmentBase("idlframewok.no_heal", Enchantment.Rarity.RARE, EnumEnchantmentType.BREAKABLE, allSlots).setRarityModifier(curseBase, curseDelta).setAsCurse();

    //Conflict groups
    public static final Enchantment[] WEAPON_DAMAGE_CONFLICT_GROUP = new Enchantment[]
            {
                    Enchantments.SHARPNESS,
                    Enchantments.SMITE,
                    Enchantments.BANE_OF_ARTHROPODS,

                    KILL_HORDE,
                    KILL_LONE,
                    OCEAN_ATK,
                    SNOWY_ATK,
                    HEIGHT_ATK,
                    INV_HEIGHT_ATK,
                    HORDE_ATK,
                    RIDING_ATK,
                    AG_RIDING_ATK,
                    SUNNY_ATK,
                    MOON_DEF,

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

                    HEIGHT_DEF,
                    INV_HEIGHT_DEF,
                    HORDE_DEF,
                    SNOWY_DEF,
                    OCEAN_DEF,
                    RIDING_DEF,
                    AG_RIDING_DEF,
                    SUNNY_DEF,
                    MOON_DEF,
            };

    public static final Enchantment[] ON_ATTACK = new Enchantment[]
            {
                    POISONED,
                    SLOWNESS,
                    GOLD_TOUCH
            };

    public static final Enchantment[] HALO_CONFLICT_GROUP = new Enchantment[]
            {
                    HALO_ARMOR,
                    HALO_ATTACK,
                    HALO_FLAME,
                    HALO_HASTE,
                    HALO_HEAL,
                    HALO_SPEED,
                    HALO_HEAL_MINUS,
                    HALO_HEAL_PLUS,
                    HALO_CAST_MINUS,
                    HALO_CAST_PLUS,
            };

    public static final Enchantment[] MATERIAL_DEFINE = new Enchantment[]
            {
                    FIRE_FORM,
                    WATER_FORM,
                    TIME_FORM,
                    SUN_FORM,
                    REPAIR_ON_KILL,
                    FADING
            };

    public static final Enchantment[] MIRRORCLOAK = new Enchantment[]
            {
                    FIRE_SIGHT,
                    LIT_SIGHT,
                    SLOW_SIGHT
            };

    public static final Enchantment[] ARKNIGHTS = new Enchantment[]
            {
                    ARMOR_PIERCE,
                    OCEAN_REGEN
            };

    public static final Enchantment[] STEPS = new Enchantment[]
            {
                    FLOWER_WALK,
                    HARVEST_WALK
            };

    public static final Enchantment[] ADAPT_SPEED = new Enchantment[]
            {
                    ADAPT_FAST,
                    ADAPT_ON_HIT
            };

    public static final Enchantment[] RANGED_BLADE = new Enchantment[]
            {
                    BLADE_BULLET,
                    BLADE_FAN
            };

    public static void BeforeRegister()
    {
        ANTI_AOA.setHidden(!MetaUtil.isLoaded_AOA3);
        ANTI_GOG.setHidden(!MetaUtil.isLoaded_GOG);

        ApplyConflictGroup(MATERIAL_DEFINE);
        ApplyConflictGroup(ARMOR_PROTECT_CONFLICT_GROUP);
        ApplyConflictGroup(WEAPON_DAMAGE_CONFLICT_GROUP);
        ApplyConflictGroup(HALO_CONFLICT_GROUP);
        ApplyConflictGroup(MIRRORCLOAK);
        ApplyConflictGroup(ARKNIGHTS);
        ApplyConflictGroup(ON_ATTACK);
        ApplyConflictGroup(STEPS);
        ApplyConflictGroup(ADAPT_SPEED);
        ApplyConflictGroup(RANGED_BLADE);
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


    void OnJump(LivingEvent.LivingJumpEvent event)
    {

    }
}
