package com.somebody.idlframewok.util;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class CommonDef {
    public static final String EMPTY = "";
    public static final String MINECRAFT = "minecraft";

    public static final String SSR = "MythicTCG";
    public static final double SPEED_NORMAL = 0.33000000417232513D;
    public static final String DO_MOB_LOOT = "doMobLoot";
    public static EnumRarity RARITY_SSR = EnumHelper.addRarity(SSR, TextFormatting.GOLD, "SSR");;

    public static final int STANDARD_DUNGEON_MOB_RARITY = 100;

    public static final UUID UUID_DEFAULT = UUID.fromString("3ff73966-00a0-4741-bf7e-648f92cbc82f");

    public static final int[] CYCLE_X = {-1,-1,1,1};
    public static final int[] CYCLE_Y = {-1,1,-1,1};

    public static final int[][] COMBIN_2D = {{-1,-1},
            {-1,1},
            {1,-1},
            {1,1},
    };

    public static final int[][] COMBIN_2D_L = {
            {1,1},
            {-1,-1},
            {-1,1},
            {1,-1},
    };

    public static final int[][] DIR = {
            {0, 1},
            {1, 0},
            {0, -1},
            {-1,0},
    };

    public static final int[][] DIR_L = {
        DIR[3],
        DIR[0],
        DIR[1],
        DIR[2],
    };

    public static final int INT_AS_FLOAT = 10000;

    public static final int TICK_PER_SECOND = 20;
    public static final int TICK_PER_DAY = 24000;

    public static final int MAX_BUILD_HEIGHT = 255;
    public static final int WORLD_HEIGHT = 256;

    public static final float TEMP_ABOVE_COLD = 0.1f;
    public static final float TEMP_ABOVE_HOT = 1.9f;

    //for fgo skills
    public static final int SECOND_PER_TURN = 5;
    //for arknight skills
    public static final int METER_PER_BLOCK = 2;

    public static final int TICK_PER_TURN = SECOND_PER_TURN * TICK_PER_SECOND;

    public static final float DEG_TO_RAD = 0.017453292F;
    public static final int CHUNK_SIZE = 16;
    public static final int CHUNK_MAX = 15;
    public static final float CHUNK_CENTER = 7.5f;
    public static final int CHUNK_CENTER_INT = 7;

    public static int GUA_TYPES = 8;

    public static int G_EARTH = 0;
    public static int G_THUNDER = 1;
    public static int G_WATER = 2;
    public static int G_MOUNTAIN = 4;
    public static int G_LAKE = 3;
    public static int G_FIRE = 5;
    public static int G_WIND = 6;
    public static int G_SKY = 7;

    public static int MAX_AIR = 300;

    public static int ANY_TYPE =99;//fk those stupid magical numbers in nbt storage.

    public static String MOD_NAME_AOA3 = "aoa3";
    public static String MOD_NAME_GOG = "grimoreofgaia3";
    public static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");

    /**
     * Flag 1 will cause a block update. Flag 2 will send the change to clients. Flag 4 will prevent the block from
     * being re-rendered, if this is a client world. Flag 8 will force any re-renders to run on the main thread instead
     * of the worker pool, if this is a client world and flag 4 is clear. Flag 16 will prevent observers from seeing
     * this change. Flags can be OR-ed
     */
    public static class BlockFlags
    {
        public static int BLOCK_UPDATE = 1;
        public static int TO_CLIENT = 2;
        public static int CLIENT_DONT_RENDER = 4;
        public static int FORCE_RENDER = 8;
        public static int IGNORE_OB = 16;
    }

    public static class AIMutexFlags {
        public static int MOVE = 1;
        public static int LOOK = 2;
        public static int SWIM_ETC = 4;
    }

    public static class TeamColor {
        public static int MOROON = 0xff8833cc;
        public static int MOB = 0xff887744;
    }

    public enum DigLevel {
        WOOD,
        STONE,
        IRON,
        DIAMOND,
        DIAMOND_P,
        DIAMOND_PP,
    }
}
