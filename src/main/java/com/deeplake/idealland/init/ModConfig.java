package com.deeplake.idealland.init;

import com.deeplake.idealland.util.Reference;
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

    @Config.LangKey("configgui.idealland.category.Menu0.GeneralConf")
    @Config.Comment("Idealland general config.")
    public static final GeneralConf GeneralConf = new GeneralConf();

    public static class GeneralConf {
        @Config.LangKey("idealland.conf.general.welcome")
        @Config.Comment("The text shown when a player logs in. Can be a key or a string.")
        public String WELCOME_MSG = "idealland.msg.welcome";

        @Config.LangKey("idealland.conf.general.skill_rate")
        @Config.Comment("Moroon drop skill_package rate. default:0.01f")
        public float SKILL_RATE = 0.01f;

        @Config.LangKey("idealland.conf.general.edict_craftable")
        @Config.Comment("Random edicts can be crafted by paper and gold block from anvil. default:false")
        public boolean EDICT_CRAFTABLE = false;

        @Config.LangKey("idealland.conf.general.log_on")
        @Config.Comment("Tons of log in the back ground. default:true")
        public boolean LOG_ON = true;
    }

    @Config.LangKey("configgui.idealland.category.Menu0.PerformanceConf")
    @Config.Comment("Config about performance.")
    public static final PerformanceConf PerformanceConf = new PerformanceConf();

    public static class PerformanceConf {
        @Config.LangKey("idealland.conf.performance.builder")
        @Config.Comment("Client side won't calculate building progress")
        public boolean SIMPLE_BUILDER = false;
    }

    @Config.LangKey("configgui.idealland.category.Menu0.CurseConf")
    @Config.Comment("Reviving Curse")
    public static final ReviveCurseConf REVIVE_CURSE = new ReviveCurseConf();

    public static class ReviveCurseConf {
        @Config.LangKey("idealland.conf.revive_curse.enabled")
        @Config.Comment("Non-undead creatures will be revived as undead when they revives.")
        @Config.RequiresMcRestart
        public boolean REVIVE_CURSE_ACTIVE = true;

        @Config.LangKey("idealland.conf.revive_curse.avengeful")
        @Config.Comment("Non-undead creatures will be avengeful when they revives.")
        @Config.RequiresMcRestart
        public boolean REVIVE_CURSE_AVENGEFUL = true;

        @Config.LangKey("idealland.conf.revive_curse.child_threshold")
        @Config.Comment("Entity has a max health below this value will be revived as child.")
        @Config.RequiresMcRestart
        public float REVIVE_CURSE_CHILD_THRESHOLD = 10f;

        @Config.LangKey("idealland.conf.revive_curse.revive_blacklist")
        @Config.Comment("Creatures with these names won't be revived as undead by the curse.")
        @Config.RequiresMcRestart
        public String[] REVIVE_BLACKLIST = new String[]{"entity.Slime", "entity.Vex"};
    }

    @Config.LangKey("configgui.idealland.category.Menu0.HitConf")
    @Config.Comment("One Hit Curse")
    public static final OneHitCurseConf ONE_HIT_CURSE = new OneHitCurseConf();

    public static class OneHitCurseConf {
        @Config.LangKey("idealland.conf.one_hit_curse.enabled")
        @Config.Comment("All damage are infinite and hence enough to kill")
        public boolean ONE_HIT_CURSE_ACTIVE = false;
    }

    @Config.LangKey("configgui.idealland.category.Menu0.DebugConf")
    @Config.Comment("Config for developers")
    public static final DebugConf DEBUG_CONF = new DebugConf();

    public static class DebugConf {
        @Config.LangKey("idealland.conf.debug.room_size")
        @Config.Comment("The radius of room builder")
        public int ROOM_SIZE = 3;

        @Config.LangKey("idealland.conf.debug.dim_one_id")
        @Config.Comment("DIM HEXA 16")
        @Config.RequiresMcRestart
        public int DIM_ONE_ID = 7777;

        @Config.LangKey("idealland.conf.debug.dim_two_id")
        @Config.Comment("DIM HEXA 2")
        @Config.RequiresMcRestart
        public int DIM_TWO_ID = 7778;
    }

    @Config.LangKey("configgui.idealland.category.Menu0.TemperatureConf")
    @Config.Comment("Temperature[WIP]")
    public static final TemperatureConf TEMPERATURE_CONF = new TemperatureConf();

    public static class TemperatureConf {
        @Config.LangKey("idealland.conf.temperature_sys.enabled")
        @Config.Comment("Temperature System on")
        public boolean TEMPERATURE_ENABLED = false;

        public float MAX_BASE_TEMP = 1.5f;

        public float MIN_BASE_TEMP = -0.5f;

        public float ALLOW_TEMP_UP = 0.5f;
        public float ALLOW_TEMP_DOWN =- 0.05f;

        public float ALLOW_DOWN_PER_ARMOR = -0.2f;
        public float ALLOW_UP_PER_ARMOR = -0.1f;
    }

    @Config.LangKey("configgui.idealland.category.Menu0.SpawnConf")
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
    }
}
