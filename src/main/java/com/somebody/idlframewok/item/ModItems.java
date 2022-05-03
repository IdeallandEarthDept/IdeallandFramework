package com.somebody.idlframewok.item;

import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();
//
    public static final ItemBase BIO_METAL_INGOT_1 = new ItemAntiBoomBase("yi_jian_mei");
//    public static final ItemBase BIO_METAL_INGOT_1 = new ItemUseDemo("bio_bronze_ingot");
//	public static final ItemBase MOROON_INGOT = new ItemBase("moroon_ingot");
//
//	public static final HorseArmorType TEST_TYPE = EnumHelper.addHorseArmor("test", "test", 13);
//	/*
//	WOOD(0, 59, 2.0F, 0.0F, 15),
//	STONE(1, 131, 4.0F, 1.0F, 5),
//	IRON(2, 250, 6.0F, 2.0F, 14),
//	DIAMOND(3, 1561, 8.0F, 3.0F, 10),
//	GOLD(0, 32, 12.0F, 0.0F, 22);
//
//	harvestLevel, maxUses, efficiency, damage, enchantability
//	*/
//	public static final ItemArmor.ToolMaterial TOOL_MOR_ORIGIUM_MATERIAL = EnumHelper.addToolMaterial(
//			"idlframewok:tool_mor_origium", 3, 357, 10f, 2.5F, 30)
//			.setRepairItem(new ItemStack(MOROON_INGOT));
//	//Material
//	public static final Item BLOOD_IRON_INGOT = new ItemBase("blood_iron_ingot");
//
//	public static final Item.ToolMaterial TOOL_MATERIAL_BLOOD =
//			EnumHelper.addToolMaterial("material_blood", 3, 512, 3.0F, 4F, 18).setRepairItem(new ItemStack(ModItems.BLOOD_IRON_INGOT));
//
//	public static final ItemKinshipSword KINSHIP_SWORD = new ItemKinshipSword("kinship_sword", TOOL_MATERIAL_BLOOD);
//
//	public static final Item ETHEREAL_IRON_INGOT = new ItemBase("ethereal_ingot").setGlitter();
//
//	public static final Item.ToolMaterial TOOL_MATERIAL_ETHEREAL =
//			EnumHelper.addToolMaterial("material_ethereal", 3, 128, 3.0F, 1F, 200).setRepairItem(new ItemStack(ModItems.ETHEREAL_IRON_INGOT));
//
//	public static final Item.ToolMaterial TOOL_MATERIAL_BUFF =
//			EnumHelper.addToolMaterial("material_buff", 3, 128, 3.0F, 1F, 25);
//
//    public static final Item.ToolMaterial TOOL_MATERIAL_COMP_BRICK =
//            EnumHelper.addToolMaterial("material_comp_bric", 2, 256, 2.0F, 2F, 18);
//
//
//	public static final Item CUBIX_INGOT = new ItemBase("cubix_ingot");
//
//	public static final Item.ToolMaterial TOOL_MATERIAL_CUBIX =
//			EnumHelper.addToolMaterial("material_cubix", 3, 128, 3.0F, 1F, 18).setRepairItem(new ItemStack(ModItems.CUBIX_INGOT));
//
//
//	public static final Item MODDERS_IRON_INGOT = new ItemBase("modders_ingot");
//
//	public static final Item.ToolMaterial TOOL_MATERIAL_MODDERS =
//			EnumHelper.addToolMaterial("material_modders", 3, MetaUtil.GetModCount() * 50, MetaUtil.GetModCount(), MetaUtil.GetModCount(), (int) (MetaUtil.GetModCount() * 1.5f))
//					.setRepairItem(new ItemStack(ModItems.MODDERS_IRON_INGOT));
//
//	public static final Item.ToolMaterial TOOL_MATERIAL_SKYLAND_PEBBLE =
//			EnumHelper.addToolMaterial("material_skyland_pebble", 3, 64, 2.0F, 1.0f, 18).setRepairItem(new ItemStack(ModBlocks.SKYLAND_PEBBLE));
//
//	public static final Item FIRE_ESSENCE = new ItemBase("essence_fire");
//
//	public static final Item.ToolMaterial TOOL_MATERIAL_FIRE_ESSENCE =
//			EnumHelper.addToolMaterial("material_fire_essence", 3, 128, 1.3F, 2.0f, 12).setRepairItem(new ItemStack(ModItems.FIRE_ESSENCE));
//
//
//	//Armor
////	public static final ArmorMaterial ARMOR_MATERIAL_COPPER =
////			EnumHelper.addArmorMaterial("armor_material_copper", Reference.MOD_ID + ":copper", 14,
////					new int[] {2,5,7,3}, 10, SoundEvents.BLOCK_ANVIL_HIT, 17);
//
//	/*
//	 * To Add an item,
//	 * - add a line here,
//	 * - add name in lang
//	 * - add a json and corresponding png
//	 */
//
//	public static final ItemBasicBinary YIN_SIGN = new ItemBasicBinary("yin_sign").setValue(0);
//	public static final ItemBasicBinary YANG_SIGN = new ItemBasicBinary("yang_sign").setValue(1);
//
//	public static final ItemBasicBinary[] SIGNS = new ItemBasicBinary[]{YIN_SIGN, YANG_SIGN};
//
//	public static final ItemBasicGua[] GUA =
//			new ItemBasicGua[]{
//					new ItemBasicGua("gua_0").setGua(0),//kun
//					new ItemBasicGua("gua_1").setGua(1),//thunder
//					new ItemBasicGua("gua_2").setGua(2),//water
//					new ItemBasicGua("gua_3").setGua(3),//mountain
//					new ItemBasicGua("gua_4").setGua(4),//wind
//					new ItemBasicGua("gua_5").setGua(5),//fire
//					new ItemBasicGua("gua_6").setGua(6),//wind
//					new ItemBasicGua("gua_7").setGua(7),//sky
//					//ItemGlassBottle
//			};
//
//	//public static final ItemTeamChanger teamChanger1 = new ItemTeamChanger("team_changer");
//	public static final ItemSummonEternal itemSummonEternal = new ItemSummonEternal("summon_eternal");
//	public static final ItemSummonClonePlayer SUMMON_CLONE_PLAYER = new ItemSummonClonePlayer("summon_clone_player");
//
//	public static final ItemKillAllMortal killAllMortal = new ItemKillAllMortal("kill_all_mortal_edict");
//	public static final ItemBuildSimpleHouse buildSimpleHouse = new ItemBuildSimpleHouse("build_simple_house_edict");
//
//	public static final ItemPackage RANDOM_EDICT = (ItemPackage) new ItemPackage("edict_random", new Item[]{
//			killAllMortal, buildSimpleHouse
//	}).setMaxStackSize(1);
//
////    LEATHER("leather", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F),
////    CHAIN("chainmail", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F),
////    IRON("iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F),
////    GOLD("gold", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F),
////    DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
//	//Note that if you want to set a mod thing as repair material, define them before the material, otherwise it will be empty.
//
//	public static final ItemArmor.ArmorMaterial woodArmorMaterial = EnumHelper.addArmorMaterial(
//			"idlframewok:wood_armor", "idlframewok:wood_armor", 15, new int[]{1, 2, 3, 1}, 15, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, 0)
//			.setRepairItem(new ItemStack(Blocks.PLANKS, 1, net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE));
//
//	public static final ItemArmor.ArmorMaterial maskDouErDunMaterial = EnumHelper.addArmorMaterial(
//			"idlframewok:mask_ded", "idlframewok:mask_ded", 15, new int[]{1, 4, 5, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
//
//	public static final ItemArmor.ArmorMaterial qinShiHuangMaterial = EnumHelper.addArmorMaterial(
//			"idlframewok:armor_qsh", "idlframewok:armor_qsh", 35, new int[]{2, 5, 7, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2);
//
//	public static final ItemArmor.ArmorMaterial livingMetal_1 = EnumHelper.addArmorMaterial(
//			"idlframewok:armor_l_m_1", "idlframewok:armor_l_m_1", 20, new int[]{2, 5, 7, 2}, 20, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2)
//			.setRepairItem(new ItemStack(BIO_METAL_INGOT_1));
//
//	public static final ItemArmor.ArmorMaterial royalGearArmorMaterial = EnumHelper.addArmorMaterial(
//			"idlframewok:royal_gear", "idlframewok:royal_gear", 20, new int[]{1, 1, 1, 1}, 50, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
//
//
//	public static final ItemMaskDouErDun maskDouErDun = (ItemMaskDouErDun) new ItemMaskDouErDun("mask_dou_er_dun", maskDouErDunMaterial, 1, EntityEquipmentSlot.HEAD).setRarity(EnumRarity.EPIC);
//
//	public static final ItemArmorBase[] ARMOR_QSH =
//			{new ItemArmorBase("armor_qsh_1", qinShiHuangMaterial, 1, EntityEquipmentSlot.HEAD).setRarity(EnumRarity.EPIC)
//					, new ItemArmorBase("armor_qsh_2", qinShiHuangMaterial, 1, EntityEquipmentSlot.CHEST).setRarity(EnumRarity.EPIC)
//					, new ItemArmorBase("armor_qsh_3", qinShiHuangMaterial, 1, EntityEquipmentSlot.LEGS).setRarity(EnumRarity.EPIC)
//					, new ItemArmorBase("armor_qsh_4", qinShiHuangMaterial, 1, EntityEquipmentSlot.FEET).setRarity(EnumRarity.EPIC)
//			};
//
//    //todo: give texture
//    public static final ItemArmorBase INTIMIDATE_MASK = new ItemArmorBase("intimidate_mask", ItemArmor.ArmorMaterial.GOLD, 1, EntityEquipmentSlot.HEAD).setRarity(EnumRarity.RARE);
//
//	public static final ItemArmor.ArmorMaterial moroonArmorMaterial = EnumHelper.addArmorMaterial(
//			"idlframewok:armor_moroon", "idlframewok:armor_moroon", 80, new int[]{3, 6, 8, 3}, 2, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2)
//			.setRepairItem(new ItemStack(MOROON_INGOT));
//
//	public static final ItemArmor.ArmorMaterial moroonArmorMaterialSniper = EnumHelper.addArmorMaterial(
//			"idlframewok:armor_moroon", "idlframewok:armor_mor_sniper", 40, new int[]{2, 5, 7, 2}, 2, SoundEvents.BLOCK_CLOTH_STEP, 2)
//			.setRepairItem(new ItemStack(MOROON_INGOT));
//
//
//	static PotionEffect eff = new PotionEffect(ModPotions.NOTICED_BY_MOR, TICK_PER_SECOND * 60, 0);
//	public static final ItemFoodBase FIGHT_BREAD = (ItemFoodBase) new ItemFoodBase("war_bread", 10, 10, false).
//			setPotionEffect(eff, 1.0f).
//			setAlwaysEdible();
//	public static final ItemFoodBase MEMORY_BREAD = new ItemFoodBase("memory_bread", 3, 0.6f, false).SetXP(10);
//	public static final ItemFoodBase CURE_PARASITE = new ItemCureParasite("medicine_parasite_1", 0, 0, false).setValue(1);
//	public static final ItemFoodBase CURE_PARASITE_3 = new ItemCureParasite("medicine_parasite_3", 0, 0, false).setValue(3);
//	public static final ItemFoodBase CURE_PARASITE_9 = new ItemCureParasite("medicine_parasite_9", 0, 0, false).setValue(9);
//
//	//diagnostics
//	public static final ItemDiagnosticParasite ITEM_DIAGNOSTIC_PARASITE = new ItemDiagnosticParasite("diag_parasite");
//
//	//Building
//	public static final ItemSummon EMP_SITE = new ItemSummon("emp_site").setEntity("idealland_electric_interferer");
//	public static final ItemSummon BUILD_XP_WELL = new ItemSummon("build_xp_well").setEntity("idealland_builder_xp_well");
//	public static final ItemSummon BUILD_TURRET = new ItemSummon("build_turret").setEntity("idealland_turret_prototype");
//	public static final ItemSummon BUILD_TURRET_AA = new ItemSummon("build_turret_aa").setEntity("idealland_turret_aa");
//	public static final ItemSummon BUILD_RADAR_AA = new ItemSummon("build_radar_aa").setEntity("idealland_radar_air");
//	public static final ItemSummonRoom BUILD_ROOM = new ItemSummonRoom("build_room");
//
//
//	//Crafting
//	public static final Item MATTER_ORIGINAL = new ItemBase("matter_original");
//	public static final Item SKILL_BLANK = new ItemBase("skill_base");
//	public static final Item GOBLET_BLANK = new ItemBase("goblet_blank");
//	public static final Item PAPER_BLOOD = new ItemBase("paper_blood");
//
//	public static final Item MOR_FRAG = new ItemBase("moroon_fragment");
//	public static final Item ANTENNA = new ItemBase("antenna");
//
//	public static final ItemBase ITEM_IDL_ORDER_1 = new ItemBase("idl_order_1");
//	public static final ItemBase ITEM_IDL_ORDER_2 = new ItemBase("idl_order_2");
//
//	public static final ItemDisturbMeasure ITEM_DISTURB_MEASURE = new ItemDisturbMeasure("disturb_measure", 128, ITEM_IDL_ORDER_1, 4);
//	public static final ItemDisturbMeasure ITEM_DISTURB_MEASURE_2 = new ItemDisturbMeasure("disturb_measure_2", 1024, ITEM_IDL_ORDER_1, 2);
//
//	//Armor
//	public static final ItemArmorXieGeta ITEM_ARMOR_XIE_GETA = new ItemArmorXieGeta("xie_geta", woodArmorMaterial, 1, EntityEquipmentSlot.FEET);
//	public static final ItemArmorUnderfootGeta ITEM_ARMOR_UNDERFOOT_GETA = new ItemArmorUnderfootGeta("underfoot_geta", woodArmorMaterial, 1, EntityEquipmentSlot.FEET);
//	public static final ItemHelmSanity ITEM_HELM_SANITY = new ItemHelmSanity("helm_sanity", moroonArmorMaterial, 1, EntityEquipmentSlot.HEAD);
//	public static final ItemHelmSniper helmetSniper = (ItemHelmSniper) new ItemHelmSniper("helmet_sniper", moroonArmorMaterialSniper, 1, EntityEquipmentSlot.HEAD).setRarity(EnumRarity.RARE);
//	public static final ItemRoyalGear ITEM_ROYAL_GEAR = (ItemRoyalGear) new ItemRoyalGear("royal_gear", royalGearArmorMaterial, 1, EntityEquipmentSlot.HEAD).setRarity(EnumRarity.EPIC);
//
//	public static final ItemArmor.ArmorMaterial airMaterial = EnumHelper.addArmorMaterial(
//			"idlframewok:air_material", "idlframewok:air_material", 20, new int[]{0, 0, 0, 0}, 20, SoundEvents.ENTITY_BOAT_PADDLE_WATER, 0);
//
//	public static final ItemAirSphere ITEM_AIR_SPHERE = (ItemAirSphere) new ItemAirSphere("air_sphere", airMaterial, 1, EntityEquipmentSlot.HEAD).setRarity(EnumRarity.EPIC);
//
//	public static final ItemArmorBase[] MOR_GENERAL_ARMOR =
//			{new ItemArmorBase("mor_armor_1", moroonArmorMaterial, 1, EntityEquipmentSlot.HEAD)
//					, new ItemArmorBase("mor_armor_2", moroonArmorMaterial, 1, EntityEquipmentSlot.CHEST)
//					, new ItemArmorBase("mor_armor_3", moroonArmorMaterial, 1, EntityEquipmentSlot.LEGS)
//					, new ItemArmorBase("mor_armor_4", moroonArmorMaterial, 1, EntityEquipmentSlot.FEET)
//			};
//
//	public static final ItemFadingArmor[] FADE_CHAIN =
//			{new ItemFadingArmor("fade_chain_1", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.HEAD)
//					, new ItemFadingArmor("fade_chain_2", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.CHEST)
//					, new ItemFadingArmor("fade_chain_3", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.LEGS)
//					, new ItemFadingArmor("fade_chain_4", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.FEET)
//			};
//
//	public static final ItemFadingArmor[] FADE_DIAMOND =
//			{new ItemFadingArmor("fade_diamond_1", ItemArmor.ArmorMaterial.DIAMOND, 1, EntityEquipmentSlot.HEAD)
//					, new ItemFadingArmor("fade_diamond_2", ItemArmor.ArmorMaterial.DIAMOND, 1, EntityEquipmentSlot.CHEST)
//					, new ItemFadingArmor("fade_diamond_3", ItemArmor.ArmorMaterial.DIAMOND, 1, EntityEquipmentSlot.LEGS)
//					, new ItemFadingArmor("fade_diamond_4", ItemArmor.ArmorMaterial.DIAMOND, 1, EntityEquipmentSlot.FEET)};
//
//	public static final ItemArmorLivingMetalBase[] L_M_ARMOR_1 =
//			{new ItemArmorLivingMetalBase("l_m_armor_1", livingMetal_1, 1, EntityEquipmentSlot.HEAD)
//					, new ItemArmorLivingMetalBase("l_m_armor_2", livingMetal_1, 1, EntityEquipmentSlot.CHEST)
//					, new ItemArmorLivingMetalBase("l_m_armor_3", livingMetal_1, 1, EntityEquipmentSlot.LEGS)
//					, new ItemArmorLivingMetalBase("l_m_armor_4", livingMetal_1, 1, EntityEquipmentSlot.FEET)};
//	//todo:auto attack, low dura waring
//
//
//	//Memes
//	public static final ItemYiJianMei ITEM_YI_JIAN_MEI = new ItemYiJianMei("yi_jian_mei");
//	public static final ItemElPsyCongroo ITEM_EL_PSY_CONGROO = new ItemElPsyCongroo("el_psy_congroo");
//
//	//Sagas
//	public static final ItemMotherPraise ITEM_SAGA_MOTHER = new ItemMotherPraise("saga_mother_praise");
//
//	//Modulars
//	public static final ItemLevelUpBadge ITEM_LEVEL_UP_BADGE = new ItemLevelUpBadge("level_up_badge");
//
//	public static final ItemPowerUpModular MODULAR_ATK = new ItemPowerUpModular("modular_atk", ATTACK_DAMAGE, 1f);
//	public static final ItemPowerUpModular MODULAR_DEF = new ItemPowerUpModular("modular_def", ARMOR, 1f);
//	public static final ItemPowerUpModular MODULAR_HP = new ItemPowerUpModular("modular_hp", MAX_HEALTH, 2f);
//
//	public static final ItemPowerUpModularPercent MODULAR_ATK_2 = new ItemPowerUpModularPercent("modular_atk_2", ATTACK_DAMAGE, 0.1f);
//	public static final ItemPowerUpModularPercent MODULAR_DEF_2 = new ItemPowerUpModularPercent("modular_def_2", ARMOR, 0.1f);
//	public static final ItemPowerUpModularPercent MODULAR_HP_2 = new ItemPowerUpModularPercent("modular_hp_2", MAX_HEALTH, 0.1f);
//
//	//Weapons
//	public static final ItemUNOCard ITEM_UNO_CARD = new ItemUNOCard("uno_card");
//	public static final ItemBiaoSword ITEM_BIAO_SWORD = new ItemBiaoSword("extra_biao_sword", Item.ToolMaterial.GOLD);
//	public static final ItemSaiSword ITEM_SAI_SWORD = new ItemSaiSword("extra_sai_sword", Item.ToolMaterial.DIAMOND);
//	public static final ItemKouSword ITEM_KOU_SWORD = new ItemKouSword("extra_kou_sword", Item.ToolMaterial.GOLD);
//
//	public static final ItemWearingSword ITEM_WEARING_SWORD = new ItemWearingSword("item_wearing_sword", Item.ToolMaterial.IRON);//for beginners
//	public static final ItemWearingSword ITEM_WEARING_SWORD_FIRE = new ItemWearingSword("item_wearing_sword_fire", TOOL_MATERIAL_FIRE_ESSENCE);
//
//	public static final ItemEtherealSword ITEM_ETHREAL_SWORD = new ItemEtherealSword("ethereal_sword", TOOL_MATERIAL_ETHEREAL);
//	public static final ItemCubixSword ITEM_CUBIX_SWORD = new ItemCubixSword("cubix_sword", TOOL_MATERIAL_CUBIX);
//	public static final ItemBuffSword ITEM_BUFF_SWORD = new ItemBuffSword("buff_sword", TOOL_MATERIAL_BUFF);
//
//	public static final ItemSword ITEM_MOR_ORI_SWORD = new ItemMagicalSword("mor_ori_sword", TOOL_MOR_ORIGIUM_MATERIAL);
//
//	public static final ItemPistolBase MOROON_RIFLE = new ItemPistolBase("mor_rifle");
//	public static final ItemSmashShield SMASH_SHIELD = new ItemSmashShield("smash_shield");
//	public static final ItemHealingGun HEALING_GUN = new ItemHealingGun("healing_gun");
//
//	//skills
//	public static final ItemHateDetector skill_hate_detect = (ItemHateDetector) new ItemHateDetector("skill_hate_detect").setRange(10f, 5f);
//	public static final ItemHateDetector skill_hate_detect_sniper = (ItemHateDetector) new ItemHateDetector("skill_hate_detect_sniper", EntityMoroonGhostArcher.class, "idlframewok.msg.being_targeted_by_sniper", SoundEvents.BLOCK_NOTE_CHIME).
//			setRange(16f, 10f);
//
//	public static final ItemSkillCalmWalk skill_calm_walk = (ItemSkillCalmWalk) new ItemSkillCalmWalk("skill_calm_walk").setRarity(EnumRarity.EPIC);
//	public static final ItemSkillTauntNearby skillTauntNearby = (ItemSkillTauntNearby) new ItemSkillTauntNearby("skill_taunt_nearby").setCD(18f, 1f).setRange(5f, 5f).setRarity(EnumRarity.RARE);
//	public static final ItemSkillTauntNearbyToGiven skillAllAttackTarget = (ItemSkillTauntNearbyToGiven) new ItemSkillTauntNearbyToGiven("skill_all_attack_target").setCD(50f, 5f).setRange(10f, 5f).setRarity(EnumRarity.RARE);
//	public static final ItemSkillThunderFall skillThunderFall = new ItemSkillThunderFall("skill_thunder_fall");
//	public static final ItemSkillLeadership skillLeadership = new ItemSkillLeadership("skill_leadership");
//	public static final ItemSkillWindWalk skillWindWalk = new ItemSkillWindWalk("skill_windwalk");
//	public static final ItemSkillFireBlast skillFireBlast = (ItemSkillFireBlast) new ItemSkillFireBlast("skill_fireblast").setMaxLevel(15).setCD(20f, 0f).setVal(8f, 4f).setRange(5f, 0.5f);
//	public static final ItemSkillFireBall skillFireBall = (ItemSkillFireBall) new ItemSkillFireBall("skill_fireball").setCD(1f, 0.2f).setMaxLevel(5);
//	public static final ItemSkillFireBall skillFireBallBig = (ItemSkillFireBall) new ItemSkillFireBall("skill_fireball_big").setIsSmallFireBall(false).setCD(10f, 1f).setVal(2f, 2f).setMaxLevel(10).setRarity(EnumRarity.EPIC);
//	public static final ItemSkillInvincibleArmor skillInvincibleArmor = (ItemSkillInvincibleArmor) new ItemSkillInvincibleArmor("skill_invincible").setVal(5, 5).setCD(50, 0).setMaxLevel(9);
//
//	public static final ItemSkillMartialAttack skillAttack1 = new ItemSkillMartialAttack("skill_martial1");
//	public static final ItemSkillMartialAttack skillMartialSlam = (ItemSkillMartialAttack) new ItemSkillMartialAttack("skill_martial_slam").setIsSlam(true).setKB(2f, 0.3f).setCD(10f, 1f).setVal(15f, 5f);
//
//	public static final ItemSkillFadeArmor skillFadeArmorChain = (ItemSkillFadeArmor) new ItemSkillFadeArmor("skill_fade_armor_chain", FADE_CHAIN).setCD(500f, 50f);
//	public static final ItemSkillFadeArmor skillFadeArmorDiamond = (ItemSkillFadeArmor) new ItemSkillFadeArmor("skill_fade_armor_diamond", FADE_DIAMOND).setCD(1000f, 100f);
//	public static final ItemSkillRepairArmor skillRepairArmor = (ItemSkillRepairArmor) new ItemSkillRepairArmor("skill_repair_armor").setVal(80, 50).setCD(20, 3).setRarity(EnumRarity.EPIC);
//
//	public static final ItemSkillScry skillScry = new ItemSkillScry("skill_scry");
//	public static final ItemSkillGambit skillGambit = new ItemSkillGambit("skill_gambit");
//	public static final ItemMirrorWork skillMirrorWork = (ItemMirrorWork) new ItemMirrorWork("skill_mirrorwork").setRarity(EnumRarity.EPIC);
//	public static final ItemSkillCloneEgg skillCloneEgg = (ItemSkillCloneEgg) new ItemSkillCloneEgg("skill_clone_egg").setRarity(EnumRarity.EPIC);
//	public static final ItemSkillCloneBlock skillCloneBlock = (ItemSkillCloneBlock) new ItemSkillCloneBlock("skill_clone_block").setRarity(EnumRarity.EPIC);
//
//	public static final ItemSkillSheepTransform skillSheepTransform = (ItemSkillSheepTransform) new ItemSkillSheepTransform("skill_sheep_transform").setRarity(EnumRarity.EPIC);
//	public static final ItemSkillElkTransform skillElkTransform = (ItemSkillElkTransform) new ItemSkillElkTransform("skill_elk_transform").setRarity(EnumRarity.EPIC);
//	public static final ItemSkillMorNoticeMeter skillNoticeMeter = new ItemSkillMorNoticeMeter("skill_notice_meter");
//	public static final ItemSkillExperiencePrideShield skillPrideShield = (ItemSkillExperiencePrideShield) new ItemSkillExperiencePrideShield("skill_pride_shield").setRarity(EnumRarity.EPIC);
//	public static final ItemSkillExperienceDamageAbsorption skillXpShield = new ItemSkillExperienceDamageAbsorption("skill_xp_shield");
//	public static final ItemSkillExperienceStrike skillXpStrike = new ItemSkillExperienceStrike("skill_xp_strike");
//	public static final ItemSkillSacrifce2020 skillSacrifice2020 = (ItemSkillSacrifce2020) new ItemSkillSacrifce2020("skill_sacrifice_2020").setRarity(EnumRarity.EPIC);
//
//	public static final ItemSkillDecodeItem skillDecodeItem = (ItemSkillDecodeItem) new ItemSkillDecodeItem("skill_decode_item").setRarity(EnumRarity.RARE);
//
//	public static final ItemSkillTimeCutFixed skillTimeCutFixed = new ItemSkillTimeCutFixed("skill_cd_cut_fixed");
//	public static final ItemSkillTimeCutPercent skillTimeCutPercent = new ItemSkillTimeCutPercent("skill_cd_cut_percent");
//
//	public static final ItemSkillMerlinIllusion skillMerlinIllusion = (ItemSkillMerlinIllusion) new ItemSkillMerlinIllusion("skill_merlin_illusion").setRarity(EnumRarity.EPIC);
//
//	public static final ItemSkillGuaPalm skillGuaPalm = (ItemSkillGuaPalm) new ItemSkillGuaPalm("skill_gua_palm").setRarity(EnumRarity.RARE);
//
//	public static final ItemCreatureRadar skillRadarCreature = (ItemCreatureRadar) new ItemCreatureRadar("skill_radar_creature").setRarity(EnumRarity.RARE);
//
//	public static final ItemSkillModListStrike MOD_LIST_STRIKE = (ItemSkillModListStrike) new ItemSkillModListStrike("skill_mod_list_strike").setMaxLevel(1);
//
//	public static final ItemSkillKnightsAttack skillKnightsAttack = new ItemSkillKnightsAttack("skill_knights_attack");
//	public static final ItemShenjiaTeleport skillSJTeleport = new ItemShenjiaTeleport("skill_sj_teleport");
//	public static final ItemShenjiaGrandTeleport skillSJTeleport2 = new ItemShenjiaGrandTeleport("skill_sj_teleport_grand");
//
//	//new!
//	public static final ItemSkillHasteBoost itemSkillHasteBoost = new ItemSkillHasteBoost("skill_haste_boost");
//	public static final ItemSkillArmorBoost itemSkillArmorBoost = new ItemSkillArmorBoost("skill_armor_boost");
//	public static final ItemSkillLuckBoost itemSkillLuckBoost = new ItemSkillLuckBoost("skill_luck_boost");
//
//	//arknights
//	public static final ItemSkillBase skillRuleOfSurvival = new ItemSkillRuleOS("skill_rule_of_survival");
//	public static final ItemSkillBase skillTruesilverSlash = new ItemSkillTrueSL("skill_truesilver_slash");
//
//	//observer clothes
//	public static final ItemArmorObserver[] ITEM_ARMOR_OBSERVER =
//			{new ItemArmorObserver("armor_ob_1", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.HEAD)
//					, new ItemArmorObserver("armor_ob_2", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.CHEST)
//					, new ItemArmorObserver("armor_ob_3", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.LEGS)
//					, new ItemArmorObserver("armor_ob_4", ItemArmor.ArmorMaterial.CHAIN, 1, EntityEquipmentSlot.FEET)
//			};
//
//
//	//skill classes
//	public static final ItemSkillCore SKILL_CORE_EGO = new ItemCoreAlterego("core_ego_twin");
//	public static final ItemSkillClassSpecific SKILL_EGO_ATK = new ItemAlteregoAttack("skill_ego_twin_atk");
//	public static final ItemSkillClassSpecific SKILL_EGO_DEF = new ItemAlteregoDefend("skill_ego_twin_def");
//	public static final ItemSkillClassSpecific SKILL_EGO_SPD = new ItemAlteregoFollow("skill_ego_twin_spd");
//	//public static final ItemSkillClassSpecific SKILL_EGO_FINAL = new ItemAlteregoAttack("skill_ego_twin_final");
//	public static final ItemClassEssence ESSENCE_EGO_TWIN = new ItemClassEssence("essence_ego_twin", EnumSkillClass.EGO_TWIN);
//
//	public static final ItemNanoMender itemNanoMender_16 = new ItemNanoMender("nano_mender_experimental", 16);
//	public static final ItemNanoMender itemNanoMender_128 = (ItemNanoMender) new ItemNanoMender("nano_mender_basic", 128).setRarity(EnumRarity.RARE);
//	public static final ItemNanoMender itemNanoMender_1024 = (ItemNanoMender) new ItemNanoMender("nano_mender_greater", 1024).setRarity(EnumRarity.EPIC);
//
//
//	public static final ItemFadePackageArmor fadeArmorDiamondPackage = new ItemFadePackageArmor("package_fade_armor_chain", FADE_CHAIN);
//	public static final ItemFadePackageArmor fadeArmorChainPackage = new ItemFadePackageArmor("package_fade_armor_diamond", FADE_DIAMOND);
//
//	public static final ItemP2WGoblet P_2_W_GOBLET = new ItemP2WGoblet("p2w_goblet");
//	public static final ItemDigGoblet GOBLET_DIG = new ItemDigGoblet("goblet_dig");
//	public static final ItemHealGoblet GOBLET_HEAL = new ItemHealGoblet("goblet_heal");
//	public static final ItemFlameGoblet GOBLET_FLAME = new ItemFlameGoblet("goblet_flame");
//	public static final ItemKnockBackGoblet GOBLET_WIND = new ItemKnockBackGoblet("goblet_wind");
//	public static final ItemMountainGoblet GOBLET_MOUNTAIN = new ItemMountainGoblet("goblet_mountain");
//
//	public static final ItemGuaWaterBucket WATER_CASTER = new ItemGuaWaterBucket("water_caster");
//
//	public static final ItemGuaExtractorBase WATER_EXTRACTOR = new ItemGuaExtractorBase("water_extractor");
//
//	public static final ItemPackage RANDOM_SKILL = (ItemPackage) new ItemPackage("random_skill", new Item[]{
//			skillTauntNearby, skillAllAttackTarget, skillThunderFall, skillLeadership,
//			skillWindWalk, skillFireBlast, skillFireBall, skillFireBallBig,
//			skillInvincibleArmor, //skillAttack1, skillMartialSlam,
//			skillFadeArmorChain, skillFadeArmorDiamond, skillRepairArmor,
//			skillScry, skillGambit, skillNoticeMeter, skillPrideShield, //skillXpStrike,//skillXpShield,
//			skillSheepTransform,
//			skillTimeCutFixed, skillTimeCutPercent
//			//skillMirrorWork, skill_calm_walk, skill_hate_detect
//	}).setMaxStackSize(1);
//
//	public static final ItemPackage RESEARCH_RESULT = (ItemPackage) new ItemPackage("research_result", new Item[]{
//			skill_calm_walk, skillMirrorWork, skillCloneBlock, skillCloneEgg, skillXpStrike, skillDecodeItem
//	}).setMaxStackSize(1);
//
//	//WIP
//	public static final ItemAITerminal ITEM_AI_TERMINAL = new ItemAITerminal("idl_ai_terminal");
//
////	public static final ItemRainCall ITEM_RAIN_CALL = new ItemRainCall("report_721");
//
//	public static final ItemBase ITEM_DRESSING_MIRROR = new ItemMirrorArmor("mirror_copy_armor");
//	public static final ItemSpellSelf ITEM_SPELL_SELF = new ItemSpellSelf("spell_self");
//
//	public static final ItemAdaptSword ITEM_ADPAT_SWORD = new ItemAdaptSword("adapt_sword", TOOL_MATERIAL_BLOOD);
//
//	public static final ItemBase ITEM_FUTURE_PACKGE = new ItemBase("idl_future_package");
//
//	public static final ItemNotes DIARY_NOTES = new ItemNotes("diary_notes");
//
//    public static final Item DEBUG_ITEM = new ItemHealStaff("debug_test").setMaxStackSize(3);
//	//public static final ItemDebug DEBUG_ITEM = new ItemDebug("debug_test").SetIndex(1);
//	public static final ItemDebug DEBUG_ITEM_2 = new ItemDebug("debug_test_2").SetIndex(2);
////	public static final ItemSkillEye DEBUG_ITEM_3 = new ItemGazeEffectBase("debug_test_3", MobEffects.POISON);
//public static final Item DEBUG_ITEM_3 = new ItemHorseArmor("debug_test_3");
//
//	public static final ItemTool SKYLAND_PICK = new ItemPickaxeBase("skyland_pick", TOOL_MATERIAL_SKYLAND_PEBBLE);
//
//	public static final ItemBase CYCLE_STONE_SHARD = new ItemBase("cycle_stone_shard");
//	public static final ItemBase CYCLE_STONE = new ItemBase("cycle_stone");
//
//	public static final ItemTCG[] TCG_SET_1 = {
//			new ItemTCG(0),
//			new ItemTCG(1),
//			new ItemTCG(2),
//			new ItemTCG(3),
//			new ItemTCG(4),
//			new ItemTCG(5),
//			new ItemTCG(6),
//			new ItemTCG(7),
//			new ItemTCG(8).setRarityTCG(1),
//			new ItemTCG(9).setRarityTCG(1),
//			new ItemTCG(10).setRarityTCG(1),
//			new ItemTCG(11).setRarityTCG(1),
//			new ItemTCG(12).setRarityTCG(2),
//			new ItemTCG(13).setRarityTCG(2),
//			new ItemTCG(14).setRarityTCG(1),
//			new ItemTCG(15).setRarityTCG(2),
//			new ItemTCG(16).setRarityTCG(2),
//			new ItemTCG(17).setRarityTCG(3),
//			new ItemTCG(18).setRarityTCG(3),
////			new ItemTCG(19),
////			new ItemTCG(20),
////			new ItemTCG(21),
////			new ItemTCG(22),
////			new ItemTCG(23),
////			new ItemTCG(24),
////			new ItemTCG(25),
////			new ItemTCG(26),
////			new ItemTCG(27),
////			new ItemTCG(28),
////			new ItemTCG(29),
////			new ItemTCG(30),
////			new ItemTCG(31),
////			new ItemTCG(32),
////			new ItemTCG(33),
////			new ItemTCG(34),
////			new ItemTCG(35),
////			new ItemTCG(36),
////			new ItemTCG(37),
////			new ItemTCG(38),
////			new ItemTCG(39),
////			new ItemTCG(40),
////			new ItemTCG(41),
////			new ItemTCG(42),
////			new ItemTCG(43),
////			new ItemTCG(44),
////			new ItemTCG(45),
////			new ItemTCG(46),
////			new ItemTCG(47),
////			new ItemTCG(48),
////			new ItemTCG(49),
////			new ItemTCG(50),
////			new ItemTCG(51),
////			new ItemTCG(52),
////			new ItemTCG(53),
////			new ItemTCG(54),
////			new ItemTCG(55),
////			new ItemTCG(56),
////			new ItemTCG(57),
////			new ItemTCG(58),
////			new ItemTCG(59),
////			new ItemTCG(60),
////			new ItemTCG(61),
////			new ItemTCG(62),
////			new ItemTCG(63),
////			new ItemTCG(64),
////			new ItemTCG(65),
////			new ItemTCG(66),
////			new ItemTCG(67),
////			new ItemTCG(68),
////			new ItemTCG(69),
////			new ItemTCG(70),
////			new ItemTCG(71),
////			new ItemTCG(72),
////			new ItemTCG(73),
////			new ItemTCG(74),
////			new ItemTCG(75),
////			new ItemTCG(76),
////			new ItemTCG(77),
////			new ItemTCG(78),
////			new ItemTCG(79),
////			new ItemTCG(80),
////			new ItemTCG(81),
////			new ItemTCG(82),
////			new ItemTCG(83),
////			new ItemTCG(84),
////			new ItemTCG(85),
////			new ItemTCG(86),
////			new ItemTCG(87),
////			new ItemTCG(88),
////			new ItemTCG(89),
////			new ItemTCG(90),
////			new ItemTCG(91),
////			new ItemTCG(92),
////			new ItemTCG(93),
////			new ItemTCG(94),
////			new ItemTCG(95),
////			new ItemTCG(96),
////			new ItemTCG(97),
////			new ItemTCG(98),
////			new ItemTCG(99),
////			new ItemTCG(100),
//	};
//
//
//	static PotionEffect eff2 = new PotionEffect(MobEffects.LEVITATION, TICK_PER_SECOND * 10, 0);
//	public static final Item FLOAT_FOOD = new ItemFoodBase("float_food", 0, 0, false).setPotionEffect(eff2, 1f).setAlwaysEdible();
//	public static final Item ICE_POP = new ItemIceCream("ice_pop", 0).setAlwaysEdible();
//
//	public static final Item CHILLI = new ItemChilli("chilli", 1, 0, false);
//	public static final Item DIGEST_PILLS = new ItemDigestFood("digest_pills", 0, 0, false);
//
//	static PotionEffect eff3 = new PotionEffect(MobEffects.LUCK, TICK_PER_SECOND * 15, 0);
//	public static final ItemFoodBase CLOVER = (ItemFoodBase) new ItemFoodBase("clover", 0, 0, false).setPotionEffect(eff3, 1.0f);
//
//	public static final Item HELL_COIN_BASE = new ItemBase("hell_coin_base");
//	public static final Item HELL_COIN = new ItemBase("hell_coin");
//	public static final Item PITHING_NEEDLE = new ItemPithingNeedle("pithing_needle");
//
//	public static final ItemDeathSword ARTIFACT_DEATH = new ItemDeathSword("artifact_death", TOOL_MATERIAL_MODDERS);
//
//	public static final ItemArmorEgyptNecklace AMK_NECKLACE = new ItemArmorEgyptNecklace("amk_necklace", ItemArmor.ArmorMaterial.GOLD, 2, EntityEquipmentSlot.CHEST);
//
//	public static final ItemJadeSeal JADE_SEAL = new ItemJadeSeal("sovereign_seal");
//
//	public static final ItemBase JADE = new ItemBase("jade_shard");
//	public static final ItemBase JADE_CRUDE = new ItemBase("jade_crude");
//	static final Item STONE = Item.getItemFromBlock(Blocks.STONE);
//	public static final ItemBase JADE_SEAL_PACK = new ItemPackageJadeSeal("jade_seal_pack",
//			new Item[]{JADE, STONE, STONE}
//	);
//
//	//	public static final ItemVariantBase LUCK_CARD = new ItemVariantBase("tcg_card", 4);
//	public static final ItemVariantBase MISC = new ItemVariantBase("misc", 1);
//    public static final ItemVariantBase BASIC_16_RUNE = new ItemVariantBase("basic16rune", 16);
////	public static final ItemVariantBase TEST = new ItemVariantBase("test_meta", 17);
//
//	public static final ItemBase TP_INVITE = new ItemTeleport("tp_invite");
//
//	public static final ItemDropFood DROP_FOOD = new ItemDropFood("drop_food",2f);
//	public static final ItemDropHeart DROP_HEART = new ItemDropHeart("drop_heart", 2f);
//
//	//fire dungeon
//	public static final ItemFood EAT_TO_GET_ANTI_CLEANSE = new ItemFoodAdvancement("eat_to_get_anti_cleanse", AdvancementKeys.H_NO_REMOVE_FIRE_PROOF, 0, 0, false);
//	public static final ItemFood EAT_TO_GET_ANTI_IGNITE = new ItemFoodAdvancement("eat_to_get_anti_ignite", AdvancementKeys.H_NO_IGNITE, 0, 0, false);
//
//	//dungeon kit
//	public static final ItemTrapToolBase PHASE_SET = new ItemTrapSetter("trap_phase_set", 1, EnumTrapArgTypes.PHASE);
//	public static final ItemTrapToolBase PHASE_ADD = new ItemTrapAdder("trap_phase_add", 1, EnumTrapArgTypes.PHASE);
//
//	public static final ItemTrapToolBase PERIOD_SET = new ItemTrapSetter("trap_period_set", 1, EnumTrapArgTypes.PERIOD);
//	public static final ItemTrapToolBase PERIOD_ADD = new ItemTrapAdder("trap_period_add", 1, EnumTrapArgTypes.PERIOD);
//
//	public static final ItemTrapToolBase ACTIVE_SET = new ItemTrapSetter("trap_active_set", 1, EnumTrapArgTypes.ACTIVE_TICKS);
//	public static final ItemTrapToolBase ACTIVE_ADD = new ItemTrapAdder("trap_active_add", 1, EnumTrapArgTypes.ACTIVE_TICKS);
//
//	public static final ItemTrapToolBase DAMAGE_SET = new ItemTrapSetter("trap_damage_set", 1, EnumTrapArgTypes.DAMAGE);
//	public static final ItemTrapToolBase DAMAGE_ADD = new ItemTrapAdder("trap_damage_add", 1, EnumTrapArgTypes.DAMAGE);
//
//	public static final ItemAdvancedSpawnEgg SPAWN_EGG = new ItemAdvancedSpawnEgg("advanced_spawn_egg");
//
//    public static final ItemHammerBase BRIC_HAMMER = new ItemHammerBase("bric_hammer", TOOL_MATERIAL_COMP_BRICK);
//
//	public static final ItemIceShard ICE_SHARD = new ItemIceShard("ice_shard_0", 0);
//	public static final ItemIceShard ICE_SHARD_5 = new ItemIceShard("ice_shard_5", 5);
//	public static final ItemFlameShard FLAME_SHARD = new ItemFlameShard("flame_shard_0", 0);
//	public static final ItemFlameShard FLAME_SHARD_5 = new ItemFlameShard("flame_shard_5", 5);
//
//    //materials
//    public static final ItemBase BASIC_CONDUIT = (ItemBase) new ItemBase("basic_conduit").setCreativeTab(ModCreativeTabsList.IDL_CIRCUIT);
//	public static final ItemBase COMPOSITE_BRICK = (ItemBase) new ItemBase("composite_brick").setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//
//	public static final ItemBase RAIN_RUNE = (ItemBase) new ItemBase("rain_rune").setCreativeTab(ModCreativeTabsList.IDL_WORLD);
//    public static final ItemBase RAIN_CALLER = new ItemRainCall("rain_caller");
//    public static final ItemBase BEAT_HOLDER = new ItemBeatBox("beat_holder");
//    public static final ItemBase TORPEDO = new ItemTorpedo("torpedo");
}
