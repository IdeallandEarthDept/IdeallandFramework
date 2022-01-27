package com.somebody.idlframewok.init;

import com.somebody.idlframewok.util.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Config(modid = Reference.MOD_ID, category = "")
public class ModConfig {

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler {

        private EventHandler() {
        }

        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Reference.MOD_ID)) {
                ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }


    @Config.LangKey("configgui.idlframewok.category.Menu0.GeneralConf")
    @Config.Comment("Idealland general config.")
    public static final GeneralConf GeneralConf = new GeneralConf();

    public static class GeneralConf {
        @Config.LangKey("idlframewok.conf.general.welcome")
        @Config.Comment("The text shown when a player logs in. Can be a key or a string.")
        public String WELCOME_MSG = "idlframewok.msg.welcome";

        @Config.LangKey("idlframewok.conf.general.blindness_reduce")
        @Config.Comment("Blindness will reduce mob follow range by this. 0.8 means -80%.")
        @Config.RangeDouble(min = 0, max = 1)
        @Config.RequiresMcRestart
        public double BLIND_FOR_MOBS = 0.8f;

        @Config.LangKey("idlframewok.conf.general.skill_rate")
        @Config.Comment("Moroon drop skill_package rate. default:0.01f")
        public float SKILL_RATE = 0.01f;

        @Config.LangKey("idlframewok.conf.general.edict_craftable")
        @Config.Comment("Random edicts can be crafted by paper and gold block from anvil. default:false")
        public boolean EDICT_CRAFTABLE = false;

        @Config.LangKey("idlframewok.conf.general.skill_upgrade_easy")
        @Config.Comment("Skills can be upgraded by level-up badge. default:true")
        public boolean SKILL_EASY_LV_UP = true;

        @Config.LangKey("idlframewok.conf.general.trap_hurt_intact_mob")
        @Config.Comment("Traps will hurt mobs even if they have 100% HP. default:false")
        public boolean TRAP_HURT_INTACT_MOB = false;

        @Config.LangKey("idlframewok.conf.general.friendly_fire_damage")
        @Config.Comment("Friendly fire damage is reduced to 20%")
        @Config.RangeDouble(min = 0, max = 1)
        public float FRIENDLY_DAMAGE_FACTOR = 0.2f;

        @Config.LangKey("idlframewok.conf.general.log_on")
        @Config.Comment("Tons of log in the back GROUND. default:true")
        public boolean LOG_ON = true;

        @Config.LangKey("idlframewok.conf.general.protection")
        @Config.Comment("Receive protection of Shen Ming.")
        public boolean SHENMING_PROTECTION = true;
    }


    @Config.LangKey("configgui.idlframewok.category.Menu0.EnchantConf")
    @Config.Comment("Idealland enchantment config.")
    public static final EnchantConf ENCHANT_CONF = new EnchantConf();

    public static class EnchantConf {
        @Config.LangKey("idlframewok.conf.enchantment.min_multiplier")
        @Config.Comment("Minimal Multiplier")
        public double MIN_MULTIPLIER = 0.05d;

        @Config.LangKey("idlframewok.conf.enchantment.enable_lover")
        @Config.Comment("Enable 'Romeo & Juliet'")
        public boolean ENABLE_LOVER = true;

        @Config.LangKey("idlframewok.conf.enchantment.enable_lover_death")
        @Config.Comment("Enable 'Romeo & Juliet' Death")
        public boolean ENABLE_LOVER_DEATH = true;
    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.PerformanceConf")
    @Config.Comment("Config about performance.")
    public static final PerformanceConf PerformanceConf = new PerformanceConf();

    public static class PerformanceConf {
        @Config.LangKey("idlframewok.conf.performance.builder")
        @Config.Comment("Client side won't calculate building progress")
        public boolean SIMPLE_BUILDER = false;

        @Config.LangKey("idlframewok.conf.performance.builder_speed")
        @Config.Comment("Builder Speed Per Tick")
        public float BUILDER_SPEED_MODIFIER = 1f;

        @Config.LangKey("idlframewok.conf.performance.allow_timing_trap")
        @Config.Comment("Disable it if it were lagging crazily")
        public boolean ENABLE_TIMING_TRAPS = false;
    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.ReviveCurseConf")
    @Config.Comment("Reviving Curse")
    public static final ReviveCurseConf REVIVE_CURSE = new ReviveCurseConf();

    public static class ReviveCurseConf {
        @Config.LangKey("idlframewok.conf.revive_curse.enabled")
        @Config.Comment("Non-undead creatures will be revived as undead when they revives.")
        public boolean REVIVE_CURSE_ACTIVE = true;

        @Config.LangKey("idlframewok.conf.revive_curse.avengeful")
        @Config.Comment("Non-undead creatures will be avengeful when they revives.")
        public boolean REVIVE_CURSE_AVENGEFUL = true;

        @Config.LangKey("idlframewok.conf.revive_curse.non_vanilla_can")
        @Config.Comment("Revive non-vanilla creatures")
        public boolean REVIVE_NON_VANILLA = true;

        @Config.LangKey("idlframewok.conf.revive_curse.players")
        @Config.Comment("Revive players.")
        public boolean REVIVE_VANNILA_PLAYER = true;

        @Config.LangKey("idlframewok.conf.revive_curse.child_threshold")
        @Config.Comment("Entity has a max health below this value will be revived as child.")
        public float REVIVE_CURSE_CHILD_THRESHOLD = 10f;

        @Config.LangKey("idlframewok.conf.revive_curse.revive_blacklist")
        @Config.Comment("Creatures with these names won't be revived as undead by the curse.")
        public String[] REVIVE_BLACKLIST = new String[]{"entity.Slime", "entity.Vex", "entity.Squid"};
    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.CurseConf")
    @Config.Comment("SP Design")
    public static final CurseConf CURSE_CONF = new CurseConf();

    public static class CurseConf {
        @Config.LangKey("idlframewok.conf.sleep_curse.enabled")
        @Config.Comment("Improved sleep, but become angry when interrupted.")
        public boolean IMPROVED_SLEEP_ACTIVE = true;

        @Config.LangKey("idlframewok.conf.death_box_curse.enabled")
        @Config.Comment("When you die, everything will be wrapped into a box.")
        public boolean BOX_CURSE_ACTIVE = true;

        @Config.LangKey("idlframewok.conf.level_curse.enabled")
        @Config.Comment("You gain attr according to your experience level.")
        public boolean LEVEL_CURSE_ACTIVE = false;

        @Config.LangKey("idlframewok.conf.level_curse.max_level")
        @Config.Comment("Over this level is regarded as this level.")
        public int LEVEL_CURSE_MAX_LV = 60;

        @Config.LangKey("idlframewok.conf.level_curse.max_speed")
        @Config.Comment("Your speed buff can not go over this. 1 = +100%.")
        public float LEVEL_CURSE_MAX_SPEED = 1;

        @Config.LangKey("idlframewok.conf.level_curse.max_armor_ratio")
        @Config.Comment("Armor modifier")
        public float LEVEL_CURSE_ARMOR_MODIFIER = 0.3f;

        @Config.LangKey("idlframewok.conf.level_curse.init_hp_ratio")
        @Config.Comment("Your base health will be modifed by this ratio. -0.5 = -50%")
        public float INIT_HP_RATIO = -0.5f;

        @Config.LangKey("idlframewok.conf.one_hit_curse.enabled")
        @Config.Comment("All damage are infinite and hence enough to kill")
        public boolean ONE_HIT_CURSE_ACTIVE = false;

        @Config.LangKey("idlframewok.conf.parasite_curse.enabled")
        @Config.Comment("Players will get infected with parasites in water if they don't wear pants")
        public boolean PARASITE_CURSE_ACTIVE = true;

        @Config.LangKey("idlframewok.conf.rain_curse.enabled")
        @Config.Comment("Rains every night, creatures in night rain get invisible.")
        public boolean RAIN_CURSE_ACTIVE = true;

        @Config.LangKey("idlframewok.conf.rain_curse.chance")
        @Config.Comment("Chance to rain per night")
        public float RAIN_CURSE_CHANCE = 1.0f;
    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.DebugConf")
    @Config.Comment("Config for developers")
    public static final DebugConf DEBUG_CONF = new DebugConf();

    public static class DebugConf {
        @Config.LangKey("idlframewok.conf.debug.debug_mode")
        public boolean DEBUG_MODE = false;

        @Config.LangKey("idlframewok.conf.debug.stop_dungeon_fading")
        public boolean STOP_DUNGEON_FADING = false;

        @Config.LangKey("idlframewok.conf.debug.room_size")
        @Config.Comment("The radius of room builder")
        public int ROOM_SIZE = 3;

        @Config.LangKey("idlframewok.conf.performance.enable_hex_dim")
        @Config.Comment("Enable Hex Dimension")
        public boolean ENABLE_HEX = true;

        @Config.LangKey("idlframewok.conf.debug.skyland_parse")
        @Config.Comment("Land occurs every this many chunk.")
        @Config.RangeInt(min = 1)
        public int SKY_LAND_PARSE = 3;

        @Config.LangKey("idlframewok.conf.debug.skyland_inc_chance")
        @Config.Comment("")
        @Config.RangeDouble(min = 0)
        public float SKY_LAND_INC_CHANCE = 0.8f;

        @Config.LangKey("idlframewok.conf.debug.skyland_dec_chance")
        @Config.Comment("")
        @Config.RangeDouble(min = 0)
        public float SKY_LAND_DEC_CHANCE = 0.8f;

        @Config.LangKey("idlframewok.conf.debug.helix_delta_y_min")
        @Config.Comment("")
        @Config.RangeInt(min = 0)
        public int HELLX_DELTA_Y_MIN = 0;

        @Config.LangKey("idlframewok.conf.debug.helix_delta_y_range")
        @Config.Comment("")
        @Config.RangeInt(min = 0)
        public int HELLX_DELTA_Y_MAX = 16;

        @Config.LangKey("idlframewok.conf.debug.helix_delta_y_range")
        @Config.Comment("")
        public float HELLX_OMEGA = 0.1f;

        @Config.LangKey("idlframewok.conf.debug.erase_snipers")
        @Config.Comment("Erase all existing snipers.")
        @Config.RequiresMcRestart
        public boolean ERASE_SNIPERS = false;

        public float HALO_OMEGA = 0.1f;

        @Config.RangeDouble(min = 0f)
        public float DROP_SPEED = 0.01f;

        public float BOOM_PARTICLE_SPEED = 10f;
        public float BOOM_PARTICLE_RADIUS = 0.5f;
        public int BOOM_PARTICLE_PER_TICK = 4;
        public boolean BOOM_PARTICLE_FILLED = false;

        public int TRAFFIC_LIGHT_PERIOD_HALF_SECOND = 15;

        public boolean SWITCH_A = false;
        public boolean SWITCH_B = false;

        @Config.RangeDouble(min = 0f)
        public float TRAP_PARTICLE_SPEED = 0.2f;

        @Config.Comment("For hotfix debugging.")
        public int OFFSET_X = 0;
        @Config.Comment("For hotfix debugging.")
        public int OFFSET_Y = 0;

        @Config.Comment("For hotfix debugging.")
        public float FLOAT_1 = 0.5f;
        @Config.Comment("For hotfix debugging.")
        public float FLOAT_2 = 0.5f;

        @Config.Comment("Fort hot fix debugging")
        public int[] INT_GROUP = {1, 1, 1, 1, 1};

        public int getInt(int index) {
            if (index < 0 || index >= INT_GROUP.length) {
                return 0;
            }
            return INT_GROUP[index];
        }
    }

//    @Config.LangKey("configgui.idlframewok.category.Menu0.TemperatureConf")
//    @Config.Comment("Temperature[WIP]")
//    public static final TemperatureConf TEMPERATURE_CONF = new TemperatureConf();
//
//    public static class TemperatureConf {
//        @Config.LangKey("idlframewok.conf.temperature_sys.enabled")
//        @Config.Comment("Temperature System on[WIP]")
//        public boolean TEMPERATURE_ENABLED = false;
//
//        public float MAX_BASE_TEMP = 1.5f;
//
//        public float MIN_BASE_TEMP = -0.5f;
//
//        public float ALLOW_TEMP_UP = 0.5f;
//        public float ALLOW_TEMP_DOWN =- 0.05f;
//
//        public float ALLOW_DOWN_PER_ARMOR = -0.2f;
//        public float ALLOW_UP_PER_ARMOR = -0.1f;
//    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.DungeonConf")
    @Config.Comment("Dungeon Generation, WIP")
    public static final DungeonConf DUNGEON_CONF = new DungeonConf();

    public static class DungeonConf {

        public boolean goDown = true;

        public int lastY = -7;
        @Config.RangeInt(min = 1)
        public int maxXZ = 7;
        @Config.RangeInt(min = 0)
        public int downBorder = maxXZ / 2;

        @Config.RangeInt(min = 0)
        public int downChancePercent = 10;

        @Config.RangeInt(min = 4)
        public int roomSizeXZ = 12;
        @Config.RangeInt(min = 4)
        public int roomSizeY = 10;

        @Config.RangeInt(min = 0)
        public int notPassageLength = 3; //including outerSize, xz dir sideways
        @Config.RangeInt(min = 0)
        public int notPassageLengthY = 3;//including outerSize. Y Passage, on xz
        @Config.RangeInt(min = 0)
        public int floorHeight = 2;  //including outerSize

        @Config.RangeInt(min = 2)
        public int xzPassageHeight = 3; //from floor, including roof, not including floor

        @Config.RangeInt(min = 0)
        public int stepLightDistance = 3; //from floor, including roof, not including floor

        @Config.RangeDouble(min = 0f, max = 1f)
        public double webChance = 0.05f; //from floor, including roof, not including floor

        @Config.RangeInt(min = 0)
        public int maze2sizeX = 6; //from floor, including roof, not including floor

        @Config.RangeInt(min = 0)
        public int maze2sizeY = 6; //from floor, including roof, not including floor

        @Config.RangeInt(min = 0)
        public int maze2sizeZ = 6; //from floor, including roof, not including floor

        public boolean debugBuild = false;
    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.WorldGenConf")
    @Config.Comment("World Generate")
    public static final WorldGenConf WORLD_GEN_CONF = new WorldGenConf();

    public static class WorldGenConf {

        @Config.LangKey("idlframewok.conf.debug.dim_one_id")
        @Config.Comment("DIM HEXA 16")
        @Config.RequiresMcRestart
        public int DIM_ONE_ID = 7777;

        @Config.LangKey("idlframewok.conf.debug.dim_two_id")
        @Config.Comment("DIM HEXA 2")
        @Config.RequiresMcRestart
        public int DIM_TWO_ID = 7778;

        @Config.LangKey("idlframewok.conf.debug.test_dim_id")
        @Config.Comment("DIM Universal")
        @Config.RequiresMcRestart
        public int DIM_UNIV_ID = 8888;

        @Config.LangKey("idlframewok.conf.debug.debug_dim_id")
        @Config.Comment("DIM HEXA 2")
        @Config.RequiresMcRestart
        public int DIM_DEBUG_ID = 8889;

        @Config.LangKey("conf.worldGen.world_spine")
        @Config.Comment("Generate world spine chance. 1 = 100% per chunk")
        public float SPINE_CHANCE = 0.01f;

        @Config.LangKey("conf.worldGen.sp_dungeon")
        @Config.Comment("Generate special dungeon. 1 = 100% per chunk")
        public float SP_DUNGEON_CHANCE = 0.01f;

        @Config.LangKey("conf.worldGen.whatnots")
        @Config.Comment("Generate whatnots. 1 = 100% per chunk")
        public float WHATNOT_CHANCE = 0.0001f;

        @Config.LangKey("conf.worldGen.med_turret")
        @Config.Comment("Generate Medium Turrets at hilly areas. 1 = 100% per chunk")
        public float MID_TRT_CHANCE = 0.1f;

        @Config.LangKey("conf.worldGen.skyland_chance")
        @Config.Comment("Chance to generate land in a chunk")
        @Config.RangeDouble(min = 0, max = 1)
        public float LAND_CHANCE = 0.3f;

        @Config.LangKey("conf.worldGen.skyland_chance_extra")
        @Config.Comment("Chance to generate extra land in a chunk")
        @Config.RangeDouble(min = 0, max = 1)
        public float LAND_CHANCE_EXTRA = 0.1f;

        @Config.LangKey("conf.worldGen.skyland_chance_small")
        //@Config.Comment("Chance to generate extra land in a chunk")
        @Config.RangeDouble(min = 0, max = 1)
        public float LAND_CHANCE_SMALL = 0.001f;

        @Config.LangKey("conf.worldGen.skyland_chance_small attempts")
        //@Config.Comment("Chance to generate extra land in a chunk")
        @Config.RangeDouble(min = 0)
        public float LAND_CHANCE_SMALL_ATTEMPT = 16f;

        @Config.LangKey("conf.worldGen.skyland_helix_chance")
        @Config.Comment("Chance to generate helix in a skyland chunk")
        @Config.RangeDouble(min = 0, max = 1)
        public float LAND_CHANCE_FULL_HELIX = 0.01f;

        @Config.LangKey("conf.worldGen.skyland_chance_waterfall")
        @Config.Comment("Chance to generate water wall in a chunk")
        @Config.RangeDouble(min = 0, max = 1)
        public float LAND_CHANCE_WATER_WALL = 0.05f;

        @Config.LangKey("conf.worldGen.star_per_chunk")
        @Config.Comment("Chance to generate water wall in a chunk")
        public int MAX_STAR_PER_CHUNK = 3;

        @Config.LangKey("conf.worldGen.railstop_wavelength")
        @Config.Comment("How many chunks is needed to create a rail station at the Great Railway")
        public int RAIL_STOP_WAVELENGTH = 5;

        public float GEN_CONNECTION = 0.99f;

        public float GEN_PEBBLE = 0.02f;
        public float GEN_CRATE = 0.01f;
        public float GEN_TORCH_STONE = 0.01f;
        public float GEN_SPAWN = 0.002f;
        public float GEN_FALL_STONE = 0.001f;
        public float GEN_TREE_SAPLING = 0.01f;
        public float GEN_GRASS = 0.8f;


    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.SpawnConf")
    @Config.Comment("Spawning")
    public static final SpawnConf SPAWN_CONF = new SpawnConf();

    public static class SpawnConf {
        @Config.LangKey("conf.spawn.enabled")
        @Config.Comment("Spawn mod creatures")
        @Config.RequiresMcRestart
        public boolean SPAWN = true;

        @Config.LangKey("conf.tainter_req_buff")
        @Config.Comment("Tainter requires buff")
        public boolean SPAWN_TAINTER_REQ_BUFF = true;

        @Config.LangKey("conf.kill_norm_increase_chance")
        @Config.Comment("Killing a non-Moroon unit has this chance to increase 1 moroon increase point. Valid when the 2nd option is active")
        public float KILL_NON_MOROON_INTEREST = 0.5f;

        @Config.LangKey("conf.invasion_length")
        @Config.Comment("The invasion will last this many seconds.(1~10000]")
        public int INVASION_LENGTH = 90;

        @Config.LangKey("conf.invasion_period")
        @Config.Comment("The invasion will start for each this notice value.(1~10000]")
        public int INVASION_PERIOD = 15;

        @Config.LangKey("conf.invasion_period_forcast_threshold")
        @Config.Comment("Reaching this notice value will give an alarm. should be lesser than previous.")
        public int INVASION_FORCAST = 12;

        @Config.LangKey("conf.tainter_buff_spawn_range")
        @Config.Comment("Tainter spawns within this range near a noticed player. Valid when the 2nd option is active")
        public float SPAWN_TAINTER_RANGE = 32f;

        @Config.LangKey("entity.moroon_tainter.name")
        @Config.Comment("Spawn Moroon Tainter")
        @Config.RequiresMcRestart
        public int SPAWN_TAINTER = 100;

        @Config.LangKey("entity.moroon_tide_maker.name")
        @Config.Comment("Spawn Moroon Tide Maker")
        @Config.RequiresMcRestart
        public int SPAWN_TIDE_MAKER = 30;

        @Config.LangKey("entity.skeleton_tower.name")
        @Config.Comment("Spawn Skeleton WatchTower")
        @Config.RequiresMcRestart
        public int SPAWN_SKELETON_TOWER = 30;

        @Config.LangKey("entity.moroon_orbital_beacon.name")
        @Config.Comment("Spawn Moroon Orbital Beacon")
        @Config.RequiresMcRestart
        public int SPAWN_M_O_B = 1;

        @Config.LangKey("entity.moroon_bastion_walker.name")
        @Config.Comment("Spawn Moroon Bastion Walker")
        @Config.RequiresMcRestart
        public int SPAWN_BASTION = 30;

        @Config.LangKey("entity.moroon_flick_fighter.name")
        @Config.Comment("Spawn Moroon Flicker Fighter")
        @Config.RequiresMcRestart
        public int SPAWN_FLICKER = 30;

        @Config.LangKey("entity.moroon_blinding_assassin.name")
        @Config.Comment("Spawn Moroon Blinding Assassin")
        @Config.RequiresMcRestart
        public int SPAWN_ASSASSIN = 30;

        @Config.LangKey("entity.moroon_sniper.name")
        @Config.Comment("Spawn Moroon Sniper")
        @Config.RequiresMcRestart
        public int SPAWN_SNIPER = 30;

        @Config.LangKey("entity.moroon_martialist.name")
        @Config.Comment("Spawn Moroon Martialist")
        @Config.RequiresMcRestart
        public int SPAWN_MARTIAL = 30;

        @Config.LangKey("entity.moroon_vampire.name")
        @Config.Comment("Spawn Moroon Vampire")
        @Config.RequiresMcRestart
        public int SPAWN_VAMPIRE = 30;

        @Config.LangKey("entity.moroon_mind_mage.name")
        @Config.Comment("Spawn Moroon Mind Mage")
        @Config.RequiresMcRestart
        public int SPAWN_MIND_MAGE = 4;

        @Config.LangKey("entity.squid_moroon.name")
        @Config.Comment("Spawn Moroon Squid")
        @Config.RequiresMcRestart
        public int SPAWN_MOR_SQUID = 16;

        @Config.LangKey("entity.identity_thief.name")
        @Config.Comment("Spawn Weight of this creature")
        @Config.RequiresMcRestart
        public int SPAWN_ID_THIEF = 16;

        @Config.LangKey("entity.stone_golem.name")
        @Config.Comment("Spawn Weight of this creature")
        @Config.RequiresMcRestart
        public int SPAWN_STONE_ELEM = 4;

        @Config.LangKey("entity.stone_golem.name")
        @Config.Comment("Spawn Weight of this creature")
        @Config.RequiresMcRestart
        public int SPAWN_SHADE_OTHERWORLD = 4;

        @Config.LangKey("entity.vanilla_mob_zealot.name")
        @Config.Comment("Spawn Weight of this creature")
        @Config.RequiresMcRestart
        public int SPAWN_VANILLA_MOB_ZEALOT = 16;

        @Config.LangKey("entity.earthlin.name")
        @Config.Comment("Spawn Weight of this creature")
        @Config.RequiresMcRestart
        public int SPAWN_EARTHLIN = 30;
    }


    @Config.LangKey("configgui.idlframewok.category.Menu0.SmartDropConfig")
    @Config.Comment("Smart Drop config.")
    public static final SmartDropConf smartDropConf = new SmartDropConf();

    public static class SmartDropConf {
        @Config.LangKey("idlframewok.conf.smart_drop.enabled")
        @Config.Comment("Wether monsters drop heart and food.")
        public boolean DROP_ENABLED = false;

        @Config.LangKey("idlframewok.conf.smart_drop.drop_heart_chance")
        @Config.Comment("The chance to drop a heart when a mob is killed.")
        public float heart_chance = 0.1f;

        @Config.LangKey("idlframewok.conf.smart_drop.drop_heart_chance_sinful")
        @Config.Comment("The chance to drop a heart when killed if that creature have attacked any players.")
        public float heart_chance_sinful = 0.3f;

        @Config.LangKey("idlframewok.conf.smart_drop.drop_hunger_chance")
        @Config.Comment("The chance to drop food when a mob is killed.")
        public float hunger_chance = 0.1f;
    }

    @Config.LangKey("configgui.idlframewok.category.Menu0.IdleConfig")
    @Config.Comment("Idle config.")
    public static final IdleConf idleConf = new IdleConf();

    public static class IdleConf {
        @Config.LangKey("idlframewok.conf.idle.enabled")
        @Config.Comment("Wether this system is enabled.")
        public boolean IDLE_ENABLED = false;

        @Config.LangKey("idlframewok.conf.idle.period_length")
        @Config.Comment("How many milli-second a period has.")
        public int PERIOD_LENGTH = 3600 * 1000;

        @Config.LangKey("idlframewok.conf.idle.max_stack")
        @Config.Comment("How many period can a player accumulate.")
        public int MAX_STACK_COUNT = 24;
//
//        @Config.LangKey("idlframewok.conf.smart_drop.drop_heart_chance_sinful")
//        @Config.Comment("The chance to drop a heart when killed if that creature have attacked any players.")
//        public float heart_chance_sinful = 0.3f;
//
//        @Config.LangKey("idlframewok.conf.smart_drop.drop_hunger_chance")
//        @Config.Comment("The chance to drop food when a mob is killed.")
//        public float hunger_chance = 0.1f;
    }
}
