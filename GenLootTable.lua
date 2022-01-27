local outFile = nil;
local modName = "idealland";

local initComma = false;

local totalWeight = 0;

local function GenItem(_itemName, weight)
	print("Creating:".._itemName)
	if (weight == nil) then
		weight = 100
	end

	totalWeight = totalWeight + weight;

	if (initComma == false) then
		initComma = true;
	else
		outFile:write(",");
	end

	local content = string.format('\t\t\t{"type": "item","name": "%s:%s","weight": %s}\n', modName, _itemName, weight);
	outFile:write(content);
end

local path = string.format("src\\main\\resources\\assets\\%s\\loot_tables\\draft.json", modName);
outFile = io.open(path,"w");

print("Creating Loot table:")

outFile:write('{\n');
outFile:write('	"pools": [\n');
outFile:write('\t{\n');
outFile:write('\t\t"rolls": 1,\n');
outFile:write('\t\t"entries": [\n');

GenItem("ethereal_ingot");
GenItem("modders_ingot");
GenItem("bio_bronze_ingot");
GenItem("mask_dou_er_dun");
GenItem("armor_qsh_1");
GenItem("armor_qsh_2");
GenItem("armor_qsh_3");
GenItem("armor_qsh_4");
GenItem("xie_geta");
GenItem("underfoot_geta");
GenItem("l_m_armor_1");
GenItem("l_m_armor_2");
GenItem("l_m_armor_3");
GenItem("l_m_armor_4");
GenItem("el_psy_congroo");
GenItem("level_up_badge");
GenItem("uno_card");
GenItem("ethereal_sword");
GenItem("mor_rifle");
GenItem("healing_gun");
GenItem("buff_sword");
GenItem("royal_gear");
GenItem("memory_bread");
GenItem("nano_mender_greater");
GenItem("skill_rule_of_survival");
GenItem("skill_truesilver_slash");




GenItem("saga_mother_praise");
GenItem("el_psy_congroo");

--skills
GenItem("skill_hate_detect");
GenItem("skill_hate_detect_sniper");
GenItem("skill_taunt_nearby");
GenItem("skill_all_attack_target");
GenItem("skill_leadership");
GenItem("skill_windwalk");
GenItem("skill_invincible");
GenItem("skill_repair_armor");
GenItem("skill_gambit");
GenItem("skill_sheep_transform");
GenItem("skill_xp_shield");
GenItem("skill_xp_strike");
GenItem("skill_sacrifice_2020");
GenItem("skill_cd_cut_percent");
GenItem("skill_cd_cut_fixed");
GenItem("skill_merlin_illusion");
GenItem("skill_mod_list_strike");
GenItem("skill_knights_attack");

GenItem("report_721");

outFile:write('\t\t]\n\t}\n\t]\n }');
outFile:close();

print("Creating Loot table done")




--GenBlock("idl_glass");

-- GenItem("misc", "nano_mender_greater");
-- GenItem("misc", "package_fade_armor_diamond");
-- for i = 1,4 do 
-- 	GenItem("misc", "armor_qsh_"..i);
-- end

-- for i = 1,4 do 
-- 	GenItem("misc", "mor_armor_"..i);
-- end
--GenBlock("builder_farm_sin");
--GenBlock("de_water_orb");

--GenItem("misc", "water_extractor")
--GenItem("misc", "disturb_measure")
--GenItem("skill", "skill_radar_creature")
-- GenItem("skill", "clone_block")

-- for i = 1,4 do 
-- 	GenItem("armor", "l_m_armor_" .. i)
-- end

--//{
--	"type": "item",
--	"name": "minecraft:golden_apple",
--	"weight": 20
--},
