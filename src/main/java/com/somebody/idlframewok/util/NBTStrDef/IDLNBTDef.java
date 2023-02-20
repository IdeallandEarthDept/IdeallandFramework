package com.somebody.idlframewok.util.NBTStrDef;

import com.somebody.idlframewok.IdlFramework;

public class IDLNBTDef {
	//GENERAL:
	public static final String IDEALLAND = "idealland_nbt";

	//NBT
	public static final String IS_EARTH = "is_earth";
	public static final String IS_SKY = "is_sky";

	//UNO
	public static final String CARD_SUIT = "card_suit";

	//goblet
	public static final String P2W_EXP = "p2w_experience";
	public static final String P2W_PAYING_EXP = "p2w_experience_paying";
	public static final String P2W_CACHE_EXP = "p2w_experience_cache";

	//skill
	public static final String LEVEL_TAG = "level";
	public static final String RANGE_BOOST = "range_boost";
	public static final String DURA_BOOST = "dura_boost";
	public static final String IS_IDENTIFIED = "identified";
	public static final String STATE = "state";
	public static final String STATE_2 = "state2";

	public static final String IS_NAME_HIDDEN = "name_hidden";
	public static final String IS_MANUAL_READY = "manual_ready";
	public static final String OWNER = "owner";
	public static final String IS_HEIRLOOM = "heirloom_of";
	public static final String HATE = "hate";

	//skill arknights
	public static final String IS_CASTING = "is_casting";
	public static final String CUR_CHARGE = "cur_charge";//x100
	public static final String CUR_TIME_LEFT = "time_left";//x100,please note that two may be the same


	public static final String EDICT_REPEATABLE = "is_repeatable";

	//upgrading
	public static final String DIFFICULTY = "difficulty";

	//TOOLTIP
	public static final String TOOLTIP_SKY = ".sky_desc";
	public static final String TOOLTIP_EARTH = ".earth_desc";
	public static final String TOOLTIP_NORMAL = ".normal_desc";
	public static final String TOOLTIP_SHARED = ".shared_desc";
	public static final String TOOLTIP_HIDDEN = ".hidden";
	public static final String TOOLTIP_DAMAGE = ".damage_desc";

	public static final String NAME_OFF= "idlframewok.shared.off";
	public static final String NAME_ON= "idlframewok.shared.on";
	
	public static final String TRUENAME_TO_REVEAL = ".true_name_reveal";
	public static final String MANUAL_PAGE_COUNT = ".manual_page_count";
	public static final String MANUAL_PAGE_KEY = ".manual_page_";
	public static final String MANUAL_AUTHOR = ".manual_author";
	public static final String MANUAL_TITLE = ".manual_title";
	
	//player
	public static final String STARTER_BOOK_GIVEN = "starter_book_given";

	//goblet
    public static final String ASSIGNED_BLOCK_NAME = "assigned_block";

	//IDL
	public static final String DESC_COMMON = ".desc";
	public static final String KILL_COUNT = "kill_count";
	public static final String KILL_COUNT_MOR = "kill_count_mor";
    public static final String MOR_INTEREST = "mor_interest";

    public static final String CHARGE_VALUE = "charge_value";

    public static final String BIOMETAL_WARNED = "biometal_warned";

	public static final String MODE = "mode";

	public static final String STARTER_KIT_VERSION_TAG = "last_starter_kit_" + IdlFramework.MODID;
    public static final int CUR_STARTER_KIT_VERSION = 2;

	//edict
	public static final String EDICT_START = ".on_start";
	public static final String EDICT_END= ".on_end";
	public static final String EDICT_FAIL = ".on_fail";

	public static final String EDICT_COMMON_START = "edict.shared.start";
	public static final String EDICT_COMMON_END = "edict.shared.end";
	public static final String EDICT_COMMON_FAIL = "edict.shared.fail";


	public static final String EDICT_COMMON_REPEAT = "edict.shared.repeatable";

	//Builder
	public static final String CUR_TASK_INDEX = "cur_task_index";
	public static final String BUILD_SPEED = "build_speed";
	public static final String BUILD_ARG_1 = "build_arg_1";
	public static final String BUILD_ARG_2 = "build_arg_2";

	//gua socket
	public static final String GUA_NO_FREE_SOCKET_DESC = "idlframewok.gua_enhance_no_free.desc";
	public static final String GUA_FREE_SOCKET_DESC = "idlframewok.gua_enhance_free.desc";
	public static final String GUA_TOTAL_SOCKET_DESC = "idlframewok.gua_enhance_total.desc";
	public static final String GUA_N_A_DESC = "idlframewok.gua_not_applicable.desc";

	public static final String GUA_FREE_SOCKET = "gua_free_socket";
	public static final String GUA_ENHANCE = "gua_enhance";
	public static final String GUA_ENHANCE_8 = "gua_e_8";
	public static final String GUA_ENHANCE_64 = "gua_e_64";

	//anchor
	public static final String ANCHOR_READY = "anchor_ready";
	public static final String ANCHOR_X = "anchor_x";
	public static final String ANCHOR_Y = "anchor_y";
	public static final String ANCHOR_Z = "anchor_z";

	public static final String ANCHOR_READY_2 = "anchor_ready_2";
	public static final String ANCHOR_X_2 = "anchor_x_2";
	public static final String ANCHOR_Y_2 = "anchor_y_2";
	public static final String ANCHOR_Z_2 = "anchor_z_2";

	public static final String BUILDING_CHARGE = "building_charge";

	//research
    public static final String PACK_CODE = "pack_code";
    public static final String LEARNING_ID = "learning_id";
    public static final String LEARNING_PROGRESS = "learning_progress";
    public static final String LEARNING_DONE = "learning_done";

    //temperature
	public static final String BASE_TEMPERATURE = "base_temperature";
	public static final String BASE_IS_SET = "base_is_set";

	//level
	public static final String LAST_LEVEL = "last_lv";

	public static final String LEVEL = "lv_idl";

	public static final String INIT_DONE = "init_done";


	//nonsense
	public static final String KILL_COUNT_ITEM = "kill_count";//marked on items, not players
	public static final String KILL_COUNT_DESC = "idlframewok.kill_count.desc";

	public static final String MARKING_POS_A = "marking_pos_a";
	public static final String MARKING_POS_B = "marking_pos_b";

	public static final String MARK_ATK = "mark.attackDamage";
	public static final String MARK_HP = "mark.maxHealth";
	public static final String MARK_DEF = "mark.armor";
	public static final String MARK_ARMOR_T = "mark.armorToughness";
	public static final String MARK_RANGE = "mark.followRange";
	public static final String MARK_KB_R = "mark.knockbackResistance";
	public static final String MARK_SPEED = "mark.movementSpeed";
	public static final String MARK_ATK_SPEED = "mark.attackSpeed";

	public static final String MARK_TOTAL_COUNT = "mark.count";


    public static final String FLAVOR_KEY = ".flvr";
}
