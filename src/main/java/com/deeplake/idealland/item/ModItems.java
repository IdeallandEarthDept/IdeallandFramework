package com.deeplake.idealland.item;

import com.deeplake.idealland.entity.creatures.moroon.EntityMoroonGhostArcher;
import com.deeplake.idealland.item.consumables.*;
import com.deeplake.idealland.item.edicts.ItemBuildSimpleHouse;
import com.deeplake.idealland.item.edicts.ItemKillAllMortal;
import com.deeplake.idealland.item.fading.ItemFadingArmor;
import com.deeplake.idealland.item.food.ItemFoodBase;
import com.deeplake.idealland.item.goblet.*;
import com.deeplake.idealland.item.misc.*;
import com.deeplake.idealland.item.misc.GuaCasters.ItemGuaExtractorBase;
import com.deeplake.idealland.item.misc.GuaCasters.ItemGuaWaterBucket;
import com.deeplake.idealland.item.misc.armor.ItemArmorUnderfootGeta;
import com.deeplake.idealland.item.misc.armor.ItemArmorXieGeta;
import com.deeplake.idealland.item.misc.armor.masks.ItemHelmSanity;
import com.deeplake.idealland.item.misc.armor.masks.ItemHelmSniper;
import com.deeplake.idealland.item.misc.armor.masks.ItemMaskDouErDun;
import com.deeplake.idealland.item.misc.customized.ItemBiaoSword;
import com.deeplake.idealland.item.misc.customized.ItemKouSword;
import com.deeplake.idealland.item.misc.customized.ItemSaiSword;
import com.deeplake.idealland.item.skills.*;
import com.deeplake.idealland.item.skills.martial.ItemSkillGuaPalm;
import com.deeplake.idealland.item.skills.martial.ItemSkillMartialAttack;
import com.deeplake.idealland.item.weapon.ItemEtherealSword;
import com.deeplake.idealland.item.weapon.ItemHealingGun;
import com.deeplake.idealland.item.weapon.ItemPistolBase;
import com.deeplake.idealland.item.weapon.ItemSmashShield;
import com.deeplake.idealland.meta.MetaUtil;
import com.deeplake.idealland.potion.ModPotions;
import com.deeplake.idealland.special.ItemAITerminal;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;
import static net.minecraft.entity.SharedMonsterAttributes.*;

public class ModItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();

	/*
	WOOD(0, 59, 2.0F, 0.0F, 15),
    STONE(1, 131, 4.0F, 1.0F, 5),
    IRON(2, 250, 6.0F, 2.0F, 14),
    DIAMOND(3, 1561, 8.0F, 3.0F, 10),
    GOLD(0, 32, 12.0F, 0.0F, 22);

    harvestLevel, maxUses, efficiency, damage, enchantability
	*/

	//Material
//	public static final ToolMaterial TOOL_MATERIAL_DIVINE =
//			EnumHelper.addToolMaterial("material_divine", 3, 1024, 10F, 3F, 0);
	public static final Item BLOOD_IRON_INGOT = new ItemBase("blood_iron_ingot");

    public static final Item.ToolMaterial TOOL_MATERIAL_BLOOD =
			EnumHelper.addToolMaterial("material_blood", 3, 512, 3.0F, 4F, 20).setRepairItem(new ItemStack( ModItems.BLOOD_IRON_INGOT));

	public static final ItemKinshipSword KINSHIP_SWORD = new ItemKinshipSword("kinship_sword", TOOL_MATERIAL_BLOOD);

	public static final Item ETHEREAL_IRON_INGOT = new ItemBase("ethereal_ingot");

    public static final Item.ToolMaterial TOOL_MATERIAL_ETHEREAL =
            EnumHelper.addToolMaterial("material_ethereal", 3, 128, 3.0F, 1F, 200).setRepairItem(new ItemStack( ModItems.ETHEREAL_IRON_INGOT));

	public static final Item MODDERS_IRON_INGOT = new ItemBase("modders_ingot");

	public static final Item.ToolMaterial TOOL_MATERIAL_MODDERS =
			EnumHelper.addToolMaterial("material_modders", 3, MetaUtil.GetModCount() * 50, MetaUtil.GetModCount(), MetaUtil.GetModCount(), MetaUtil.GetModCount() * 10)
					.setRepairItem(new ItemStack( ModItems.MODDERS_IRON_INGOT));

	//Armor
//	public static final ArmorMaterial ARMOR_MATERIAL_COPPER =
//			EnumHelper.addArmorMaterial("armor_material_copper", Reference.MOD_ID + ":copper", 14,
//					new int[] {2,5,7,3}, 10, SoundEvents.BLOCK_ANVIL_HIT, 17);

	/*
	 * To Add an item,
	 * - add a line here,
	 * - add name in lang
	 * - add a json and corresponding png
	 */

	public static final ItemBasicBinary YIN_SIGN = new ItemBasicBinary("yin_sign").setValue(0);
	public static final ItemBasicBinary YANG_SIGN = new ItemBasicBinary("yang_sign").setValue(1);

	public static final ItemBasicBinary[] SIGNS = new ItemBasicBinary[]{YIN_SIGN, YANG_SIGN};

	public static final ItemBasicGua[] GUA =
            new ItemBasicGua[]{
                    new ItemBasicGua("gua_0").setGua(0),//kun
                    new ItemBasicGua("gua_1").setGua(1),//thunder
                    new ItemBasicGua("gua_2").setGua(2),//water
                    new ItemBasicGua("gua_3").setGua(3),//mountain
                    new ItemBasicGua("gua_4").setGua(4),//wind
                    new ItemBasicGua("gua_5").setGua(5),//fire
                    new ItemBasicGua("gua_6").setGua(6),//wind
                    new ItemBasicGua("gua_7").setGua(7),//sky
					//ItemGlassBottle
            };

	//public static final ItemTeamChanger teamChanger1 = new ItemTeamChanger("team_changer");
	public static final ItemSummonEternal itemSummonEternal = new ItemSummonEternal("summon_eternal");

    public static final ItemBase BIO_METAL_INGOT_1 = new ItemBase("bio_bronze_ingot");
    public static final ItemBase MOROON_INGOT = new ItemBase("moroon_ingot");


	public static final ItemKillAllMortal killAllMortal = new ItemKillAllMortal("kill_all_mortal_edict");
	public static final ItemBuildSimpleHouse buildSimpleHouse = new ItemBuildSimpleHouse("build_simple_house_edict");

	public static final ItemPackage RANDOM_EDICT = (ItemPackage) new ItemPackage("edict_random", new Item[]{
			killAllMortal, buildSimpleHouse
	}).setMaxStackSize(1);

//    LEATHER("leather", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F),
//    CHAIN("chainmail", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F),
//    IRON("iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F),
//    GOLD("gold", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F),
//    DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
	//Note that if you want to set a mod thing as repair material, define them before the material, otherwise it will be empty.

    public static final ItemArmor.ArmorMaterial woodArmorMaterial = EnumHelper.addArmorMaterial(
            "idealland:wood_armor", "idealland:wood_armor", 15, new int[] {1, 2, 3, 1}, 15, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, 0)
            .setRepairItem(new ItemStack(Blocks.PLANKS, 1, net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE));

    public static final ItemArmor.ArmorMaterial maskDouErDunMaterial = EnumHelper.addArmorMaterial(
			"idealland:mask_ded", "idealland:mask_ded", 15, new int[] {1, 4, 5, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);

	public static final ItemArmor.ArmorMaterial qinShiHuangMaterial = EnumHelper.addArmorMaterial(
			"idealland:armor_qsh", "idealland:armor_qsh", 35, new int[] {2, 5, 7, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2);

	public static final ItemArmor.ArmorMaterial livingMetal_1 = EnumHelper.addArmorMaterial(
			"idealland:armor_l_m_1", "idealland:armor_l_m_1", 20, new int[] {2, 5, 7, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2)
            .setRepairItem(new ItemStack(BIO_METAL_INGOT_1));



	public static final ItemMaskDouErDun maskDouErDun = (ItemMaskDouErDun) new ItemMaskDouErDun("mask_dou_er_dun", maskDouErDunMaterial, 1, EntityEquipmentSlot.HEAD).setRarity(EnumRarity.EPIC);

	public static final ItemArmorBase[] ARMOR_QSH =
			{new ItemArmorBase("armor_qsh_1",         qinShiHuangMaterial, 1, EntityEquipmentSlot.HEAD).setRarity(EnumRarity.EPIC)
					,new ItemArmorBase("armor_qsh_2", qinShiHuangMaterial, 1, EntityEquipmentSlot.CHEST).setRarity(EnumRarity.EPIC)
					,new ItemArmorBase("armor_qsh_3", qinShiHuangMaterial, 1, EntityEquipmentSlot.LEGS).setRarity(EnumRarity.EPIC)
					,new ItemArmorBase("armor_qsh_4", qinShiHuangMaterial, 1, EntityEquipmentSlot.FEET).setRarity(EnumRarity.EPIC)
			};

    public static final ItemArmor.ArmorMaterial moroonArmorMaterial = EnumHelper.addArmorMaterial(
            "idealland:armor_moroon", "idealland:armor_moroon", 80, new int[] {3, 6, 8, 3}, 2, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2)
            .setRepairItem(new ItemStack(MOROON_INGOT));

	public static final ItemArmor.ArmorMaterial moroonArmorMaterialSniper = EnumHelper.addArmorMaterial(
			"idealland:armor_moroon", "idealland:armor_mor_sniper", 40, new int[] {2, 5, 7, 2}, 2, SoundEvents.BLOCK_CLOTH_STEP, 2)
            .setRepairItem(new ItemStack(MOROON_INGOT));


	static PotionEffect eff = new PotionEffect(ModPotions.NOTICED_BY_MOR, TICK_PER_SECOND * 60, 0);
	public static final ItemFoodBase FIGHT_BREAD = (ItemFoodBase) new ItemFoodBase("war_bread", 10, 10, false).
			setPotionEffect(eff, 1.0f).
			setAlwaysEdible();
    public static final ItemFoodBase MEMORY_BREAD = new ItemFoodBase("memory_bread", 3, 0.6f, false).SetXP(10);

	//Building
	public static final ItemSummon EMP_SITE = new ItemSummon("emp_site").setEntity("idealland_electric_interferer");
	public static final ItemSummon BUILD_XP_WELL = new ItemSummon("build_xp_well").setEntity("idealland_builder_xp_well");
	public static final ItemSummon BUILD_TURRET = new ItemSummon("build_turret").setEntity("idealland_turret_prototype");
	public static final ItemSummon BUILD_TURRET_AA = new ItemSummon("build_turret_aa").setEntity("idealland_turret_aa");
	public static final ItemSummon BUILD_RADAR_AA = new ItemSummon("build_radar_aa").setEntity("idealland_radar_air");
	public static final ItemSummonRoom BUILD_ROOM = new ItemSummonRoom("build_room");


	//Crafting
	public static final Item MATTER_ORIGINAL = new ItemBase("matter_original");
	public static final Item SKILL_BLANK = new ItemBase("skill_base");
	public static final Item GOBLET_BLANK = new ItemBase("goblet_blank");
	public static final Item PAPER_BLOOD = new ItemBase("paper_blood");

	public static final Item MOR_FRAG = new ItemBase("moroon_fragment");
	public static final Item ANTENNA = new ItemBase("antenna");

	public static final ItemBase ITEM_IDL_ORDER_1 = new ItemBase("idl_order_1");
	public static final ItemBase ITEM_IDL_ORDER_2 = new ItemBase("idl_order_2");

	public static final ItemDisturbMeasure ITEM_DISTURB_MEASURE = new ItemDisturbMeasure("disturb_measure", 128, ITEM_IDL_ORDER_1, 4);
	public static final ItemDisturbMeasure ITEM_DISTURB_MEASURE_2 = new ItemDisturbMeasure("disturb_measure_2", 1024, ITEM_IDL_ORDER_1, 2);

	//Armor
	public static final ItemArmorXieGeta ITEM_ARMOR_XIE_GETA = new ItemArmorXieGeta("xie_geta", woodArmorMaterial,1, EntityEquipmentSlot.FEET);
	public static final ItemArmorUnderfootGeta ITEM_ARMOR_UNDERFOOT_GETA = new ItemArmorUnderfootGeta("underfoot_geta", woodArmorMaterial,1, EntityEquipmentSlot.FEET);
	public static final ItemHelmSanity ITEM_HELM_SANITY = new ItemHelmSanity("helm_sanity", moroonArmorMaterial,1, EntityEquipmentSlot.HEAD);


	public static final ItemHelmSniper helmetSniper = (ItemHelmSniper) new ItemHelmSniper("helmet_sniper", moroonArmorMaterialSniper, 1, EntityEquipmentSlot.HEAD).setRarity(EnumRarity.RARE);

	public static final ItemArmorBase[] MOR_GENERAL_ARMOR =
			{        new ItemArmorBase("mor_armor_1", moroonArmorMaterial, 1, EntityEquipmentSlot.HEAD)
					,new ItemArmorBase("mor_armor_2", moroonArmorMaterial, 1, EntityEquipmentSlot.CHEST)
					,new ItemArmorBase("mor_armor_3", moroonArmorMaterial, 1, EntityEquipmentSlot.LEGS)
					,new ItemArmorBase("mor_armor_4", moroonArmorMaterial, 1, EntityEquipmentSlot.FEET)
			};

	public static final ItemFadingArmor[] FADE_CHAIN =
			{new ItemFadingArmor("fade_chain_1", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.HEAD)
					,new ItemFadingArmor("fade_chain_2", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.CHEST)
					,new ItemFadingArmor("fade_chain_3", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.LEGS)
					,new ItemFadingArmor("fade_chain_4", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.FEET)
			};

	public static final ItemFadingArmor[] FADE_DIAMOND =
			{new ItemFadingArmor("fade_diamond_1", ItemArmor.ArmorMaterial.DIAMOND, 1, EntityEquipmentSlot.HEAD)
					,new ItemFadingArmor("fade_diamond_2",ItemArmor.ArmorMaterial.DIAMOND, 1,EntityEquipmentSlot.CHEST)
					,new ItemFadingArmor("fade_diamond_3",ItemArmor.ArmorMaterial.DIAMOND, 1,EntityEquipmentSlot.LEGS)
					,new ItemFadingArmor("fade_diamond_4",ItemArmor.ArmorMaterial.DIAMOND, 1,EntityEquipmentSlot.FEET)};

	public static final  ItemArmorLivingMetalBase[] L_M_ARMOR_1 =
			{		 new ItemArmorLivingMetalBase("l_m_armor_1", livingMetal_1, 1, EntityEquipmentSlot.HEAD)
					,new ItemArmorLivingMetalBase("l_m_armor_2", livingMetal_1, 1,EntityEquipmentSlot.CHEST)
					,new ItemArmorLivingMetalBase("l_m_armor_3", livingMetal_1, 1,EntityEquipmentSlot.LEGS)
					,new ItemArmorLivingMetalBase("l_m_armor_4", livingMetal_1, 1,EntityEquipmentSlot.FEET)};
	//todo:auto attack, low dura waring


	//Memes
	public static final ItemYiJianMei ITEM_YI_JIAN_MEI = new ItemYiJianMei("yi_jian_mei");
    public static final ItemElPsyCongroo ITEM_EL_PSY_CONGROO = new ItemElPsyCongroo("el_psy_congroo");

    //Modulars
	public static final ItemLevelUpBadge ITEM_LEVEL_UP_BADGE = new ItemLevelUpBadge("level_up_badge");

    public static final ItemPowerUpModular MODULAR_ATK = new ItemPowerUpModular("modular_atk", ATTACK_DAMAGE, 1f);
    public static final ItemPowerUpModular MODULAR_DEF = new ItemPowerUpModular("modular_def", ARMOR, 1f);
    public static final ItemPowerUpModular MODULAR_HP = new ItemPowerUpModular("modular_hp", MAX_HEALTH, 2f);

	public static final ItemPowerUpModularPercent MODULAR_ATK_2 = new ItemPowerUpModularPercent("modular_atk_2", ATTACK_DAMAGE, 0.1f);
	public static final ItemPowerUpModularPercent MODULAR_DEF_2 = new ItemPowerUpModularPercent("modular_def_2", ARMOR, 0.1f);
	public static final ItemPowerUpModularPercent MODULAR_HP_2 = new ItemPowerUpModularPercent("modular_hp_2", MAX_HEALTH, 0.1f);

	//Weapons
	public static final ItemUNOCard ITEM_UNO_CARD = new ItemUNOCard("uno_card");
	public static final ItemBiaoSword ITEM_BIAO_SWORD = new ItemBiaoSword("extra_biao_sword", Item.ToolMaterial.GOLD);
	public static final ItemSaiSword ITEM_SAI_SWORD = new ItemSaiSword("extra_sai_sword", Item.ToolMaterial.DIAMOND);
	public static final ItemKouSword ITEM_KOU_SWORD = new ItemKouSword("extra_kou_sword", Item.ToolMaterial.GOLD);

    public static final ItemEtherealSword ITEM_ETHREAL_SWORD = new ItemEtherealSword("ethereal_sword", TOOL_MATERIAL_ETHEREAL);

	public static final ItemPistolBase MOROON_RIFLE = new ItemPistolBase("mor_rifle");
	public static final ItemSmashShield SMASH_SHIELD = new ItemSmashShield("smash_shield");
	public static final ItemHealingGun HEALING_GUN = new ItemHealingGun("healing_gun");

	//skills
	public static final ItemHateDetector skill_hate_detect = (ItemHateDetector) new ItemHateDetector("skill_hate_detect").setRange(10f, 5f);
	public static final ItemHateDetector skill_hate_detect_sniper = (ItemHateDetector) new ItemHateDetector("skill_hate_detect_sniper", EntityMoroonGhostArcher.class, "idealland.msg.being_targeted_by_sniper", SoundEvents.BLOCK_NOTE_CHIME).
			setRange(16f, 10f);

	public static final ItemSkillCalmWalk skill_calm_walk = (ItemSkillCalmWalk) new ItemSkillCalmWalk("skill_calm_walk").setRarity(EnumRarity.EPIC);
	public static final ItemSkillTauntNearby skillTauntNearby = (ItemSkillTauntNearby) new ItemSkillTauntNearby("skill_taunt_nearby").setCD(18f,1f).setRange(5f,5f).setRarity(EnumRarity.RARE);
	public static final ItemSkillTauntNearbyToGiven skillAllAttackTarget = (ItemSkillTauntNearbyToGiven) new ItemSkillTauntNearbyToGiven("skill_all_attack_target").setCD(50f,5f).setRange(10f,5f).setRarity(EnumRarity.RARE);
	public static final ItemSkillThunderFall skillThunderFall = new ItemSkillThunderFall("skill_thunder_fall");
	public static final ItemSkillAttackBoost skillLeadership = new ItemSkillAttackBoost("skill_leadership");
	public static final ItemSkillWindWalk skillWindWalk = new ItemSkillWindWalk("skill_windwalk");
	public static final ItemSkillFireBlast skillFireBlast = (ItemSkillFireBlast) new ItemSkillFireBlast("skill_fireblast").setMaxLevel(15).setCD(20f, 0f).setVal(8f, 4f).setRange(5f,0.5f);
	public static final ItemSkillFireBall skillFireBall = (ItemSkillFireBall) new ItemSkillFireBall("skill_fireball").setCD(1f, 0.2f).setMaxLevel(5);
	public static final ItemSkillFireBall skillFireBallBig = (ItemSkillFireBall) new ItemSkillFireBall("skill_fireball_big").setIsSmallFireBall(false).setCD(10f, 1f).setVal(2f,2f).setMaxLevel(10).setRarity(EnumRarity.EPIC);
	public static final ItemSkillInvincibleArmor skillInvincibleArmor = (ItemSkillInvincibleArmor) new ItemSkillInvincibleArmor("skill_invincible").setVal(5, 5).setCD(50,0).setMaxLevel(9);

	public static final ItemSkillMartialAttack skillAttack1 = new ItemSkillMartialAttack("skill_martial1");
	public static final ItemSkillMartialAttack skillMartialSlam = (ItemSkillMartialAttack) new ItemSkillMartialAttack("skill_martial_slam").setIsSlam(true).setKB(2f, 0.3f).setCD(10f, 1f).setVal(15f,5f);

	public static final ItemSkillFadeArmor skillFadeArmorChain = (ItemSkillFadeArmor) new ItemSkillFadeArmor("skill_fade_armor_chain", FADE_CHAIN).setCD(500f, 50f);
	public static final ItemSkillFadeArmor skillFfadeArmorDiamond = (ItemSkillFadeArmor) new ItemSkillFadeArmor("skill_fade_armor_diamond", FADE_DIAMOND).setCD(1000f, 100f);
	public static final ItemSkillRepairArmor skillRepairArmor = (ItemSkillRepairArmor) new ItemSkillRepairArmor("skill_repair_armor").setVal(80, 50).setCD(20, 3).setRarity(EnumRarity.EPIC);

	public static final ItemSkillScry skillScry = new ItemSkillScry("skill_scry");
	public static final ItemSkillGambit skillGambit = new ItemSkillGambit("skill_gambit");
	public static final ItemMirrorWork skillMirrorWork = (ItemMirrorWork) new ItemMirrorWork("skill_mirrorwork").setRarity(EnumRarity.EPIC);

	public static final ItemSkillSheepTransform skillSheepTransform = (ItemSkillSheepTransform) new ItemSkillSheepTransform("skill_sheep_transform").setRarity(EnumRarity.EPIC);
	public static final ItemSkillElkTransform skillElkTransform = (ItemSkillElkTransform) new ItemSkillElkTransform("skill_elk_transform").setRarity(EnumRarity.EPIC);
	public static final ItemSkillMorNoticeMeter skillNoticeMeter = new ItemSkillMorNoticeMeter("skill_notice_meter");
	public static final ItemSkillExperiencePrideShield skillPrideShield = (ItemSkillExperiencePrideShield) new ItemSkillExperiencePrideShield("skill_pride_shield").setRarity(EnumRarity.EPIC);
	public static final ItemSkillExperienceDamageAbsorption skillXpShield = new ItemSkillExperienceDamageAbsorption("skill_xp_shield");
	public static final ItemSkillExperienceStrike skillXpStrike = new ItemSkillExperienceStrike("skill_xp_strike");
	public static final ItemSkillSacrifce2020 skillSacrifice2020 = (ItemSkillSacrifce2020) new ItemSkillSacrifce2020("skill_sacrifice_2020").setRarity(EnumRarity.EPIC);
	public static final ItemSkillCloneEgg skillCloneEgg = (ItemSkillCloneEgg) new ItemSkillCloneEgg("skill_clone_egg").setRarity(EnumRarity.EPIC);
	public static final ItemSkillCloneBlock skillCloneBlock = (ItemSkillCloneBlock) new ItemSkillCloneBlock("skill_clone_block").setRarity(EnumRarity.EPIC);
	public static final ItemSkillDecodeItem skillDecodeItem = (ItemSkillDecodeItem) new ItemSkillDecodeItem("skill_decode_item").setRarity(EnumRarity.RARE);

	public static final ItemSkillTimeCutFixed skillTimeCutFixed = new ItemSkillTimeCutFixed("skill_cd_cut_fixed");
	public static final ItemSkillTimeCutPercent skillTimeCutPercent = new ItemSkillTimeCutPercent("skill_cd_cut_percent");

	public static final ItemSkillMerlinIllusion skillMerlinIllusion = (ItemSkillMerlinIllusion) new ItemSkillMerlinIllusion("skill_merlin_illusion").setRarity(EnumRarity.EPIC);

	public static final ItemSkillGuaPalm skillGuaPalm = (ItemSkillGuaPalm) new ItemSkillGuaPalm("skill_gua_palm").setRarity(EnumRarity.RARE);

	public static final ItemCreatureRadar skillRadarCreature = (ItemCreatureRadar) new ItemCreatureRadar("skill_radar_creature").setRarity(EnumRarity.RARE);

	public static final ItemSkillModListStrike MOD_LIST_STRIKE = (ItemSkillModListStrike) new ItemSkillModListStrike("skill_mod_list_strike").setMaxLevel(1);

	public static final ItemNanoMender itemNanoMender_16 = new ItemNanoMender("nano_mender_experimental", 16);
	public static final ItemNanoMender itemNanoMender_128 = (ItemNanoMender) new ItemNanoMender("nano_mender_basic", 128).setRarity(EnumRarity.RARE);
	public static final ItemNanoMender itemNanoMender_1024 = (ItemNanoMender) new ItemNanoMender("nano_mender_greater", 1024).setRarity(EnumRarity.EPIC);


	public static final ItemFadePackageArmor fadeArmorDiamondPackage = new ItemFadePackageArmor("package_fade_armor_chain", FADE_CHAIN);
	public static final ItemFadePackageArmor fadeArmorChainPackage = new ItemFadePackageArmor("package_fade_armor_diamond", FADE_DIAMOND);

	public static final ItemP2WGoblet P_2_W_GOBLET = new ItemP2WGoblet("p2w_goblet");
	public static final ItemDigGoblet GOBLET_DIG = new ItemDigGoblet("goblet_dig");
	public static final ItemHealGoblet GOBLET_HEAL = new ItemHealGoblet("goblet_heal");
	public static final ItemFlameGoblet GOBLET_FLAME = new ItemFlameGoblet("goblet_flame");
	public static final ItemKnockBackGoblet GOBLET_WIND = new ItemKnockBackGoblet("goblet_wind");
	public static final ItemMountainGoblet GOBLET_MOUNTAIN = new ItemMountainGoblet("goblet_mountain");

	public static final ItemGuaWaterBucket WATER_CASTER = new ItemGuaWaterBucket("water_caster");

	public static final ItemGuaExtractorBase WATER_EXTRACTOR = new ItemGuaExtractorBase("water_extractor");

	public static final ItemPackage RANDOM_SKILL = (ItemPackage) new ItemPackage("random_skill", new Item[]{
			skillTauntNearby, skillAllAttackTarget, skillThunderFall, skillLeadership,
			skillWindWalk, skillFireBlast, skillFireBall, skillFireBallBig,
			skillInvincibleArmor, //skillAttack1, skillMartialSlam,
			skillFadeArmorChain, skillFfadeArmorDiamond, skillRepairArmor,
			skillScry, skillGambit, skillNoticeMeter,  skillPrideShield, //skillXpStrike,//skillXpShield,
			skillSheepTransform,
			skillTimeCutFixed, skillTimeCutPercent
			//skillMirrorWork, skill_calm_walk, skill_hate_detect
	}).setMaxStackSize(1);

	public static final ItemPackage RESEARCH_RESULT = (ItemPackage) new ItemPackage("research_result", new Item[]{
			skill_calm_walk, skillMirrorWork, skillCloneBlock, skillCloneEgg, skillXpStrike, skillDecodeItem
	}).setMaxStackSize(1);

	//WIP
	public static final ItemAITerminal ITEM_AI_TERMINAL = new ItemAITerminal("idl_ai_terminal");

	public static final ItemRainCall ITEM_RAIN_CALL = new ItemRainCall("report_721");

	public static final ItemBase ITEM_FUTURE_PACKGE = new ItemBase("idl_future_package");

	public static final ItemSkillBulletStrike DEBUG_ITEM = new ItemSkillBulletStrike("debug_test");
	//public static final ItemDebug DEBUG_ITEM = new ItemDebug("debug_test").SetIndex(1);
	public static final ItemDebug DEBUG_ITEM_2 = new ItemDebug("debug_test_2").SetIndex(2);
	public static final ItemDebug DEBUG_ITEM_3 = new ItemDebug("debug_test_3").SetIndex(3);
}
