package com.somebody.idlframewok.util;

import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.EntityLivingBase;

import javax.vecmath.Color3f;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class Color16Def {
    //    @SubscribeEvent
//    public static void handleItemColors(ColorHandlerEvent.Item event)
//    {
//        //see:ItemColors
//    }
    public static final int INIT_BELIEF = 100;

    public static final int EARTH= 0;
    public static final int FIRE = 1;
    public static final int LIFE = 2;
    public static final int SOIL = 3;
    public static final int WATER = 4;
    public static final int POISON = 5;
    public static final int MALE = 6;
    public static final int IRON = 7;
    public static final int STONE = 8;
    public static final int FEMALE = 9;
    public static final int WOOD = 10;
    public static final int GOLD = 11;
    public static final int WIND = 12;
    public static final int DEATH = 13;
    public static final int LAVA = 14;
    public static final int SKY = 15;

    public static final int DARKNESS= 0;
    public static final int SUN = 1;
//    public static final int LIFE = 2;
//    public static final int SOIL = 3;
//    public static final int WATER = 4;
//    public static final int POISON = 5;
    public static final int WAR = 6;
//    public static final int IRON = 7;
//    public static final int STONE = 8;
    public static final int PEACE = 9;
//    public static final int WOOD = 10;
//    public static final int GOLD = 11;
//    public static final int WIND = 12;
//    public static final int DEATH = 13;
    public static final int MOON = 14;
    public static final int LIGHT = 15;

    public static final String OUT_WORLD_PROTECT = "oow_protect";
    public static final String FALL_PROTECT = "fall_protect";
    public static final int OUT_WORLD_PROTECT_ACTIVE = 1;

    public static final String KEY_PRAY_TIMESTAMP = "god16_pray_cd";

    public static final String MSG_GET_OOW_PROTECT = IDLNBTDef.MSG_PREFIX + "oow_protect_receive";
    public static final String MSG_PROTECT_ALREADY = IDLNBTDef.MSG_PREFIX + "protect_already";
    public static final String MSG_USE_OOW_PROTECT = IDLNBTDef.MSG_PREFIX + "oow_protect_use";

    public static final String MSG_GET_FALL_PROTECT = IDLNBTDef.MSG_PREFIX + "fall_protect_receive";
    public static final String MSG_USE_FALL_PROTECT = IDLNBTDef.MSG_PREFIX + "fall_protect_use";

    public static final String MSG_USE_REIVE_SET = IDLNBTDef.MSG_PREFIX + "revive_set";

    public static final String MSG_GOD_ACCEPT_PRAY = IDLNBTDef.MSG_PREFIX + "god_accept_pray";
    public static final String MSG_GOD_ACCEPT_TRIBUTE = IDLNBTDef.MSG_PREFIX + "god_accept_tribute";
    public static final String MSG_GOD_REJECT = IDLNBTDef.MSG_PREFIX + "god_reject_pray";
    public static final String MSG_GOD_PRAY_CD = IDLNBTDef.MSG_PREFIX + "god_pray_cd";
    public static final int[] COLOR_16 ={
            0x1D1D21,
            0xB02E26,
            0x5E7C16,
            0x835432,
            0x3C44AA,
            0x8932B8,
            0x169C9C,
            0x9D9D97,
            0x474F52,
            0xF38BAA,
            0x80C71F,
            0xFED83D,
            0x3AB3DA,
            0xC74EBD,
            0xF9801D,
            0xF9FFFE
    };

    public static Color3f[] Color3f_16;

    static {
        Color3f_16 = new Color3f[16];
        for (int i = 0; i < COLOR_16.length; i++) {
            Color3f_16[i] = colorFromInt(COLOR_16[i]);
        }
    }

    static final String KEY_TRIBUE = "god16tri_";
    public static String getKeyTribute(int index)
    {
        return KEY_TRIBUE + index;
    }

    static final String GOD_NAME = "idlframewok.god_";
    public static String getKeyGodName(int index)
    {
        return GOD_NAME + index;
    }

    public static void increaseBelief(EntityLivingBase livingBase, int index, int point)
    {
        IDLNBTUtil.addIntAuto(livingBase, getKeyTribute(index), point);
    }

    public static void setBelief(EntityLivingBase livingBase, int index, int point)
    {
        IDLNBTUtil.setIntAuto(livingBase, getKeyTribute(index), point);
    }

    public static int getGodColor(int index)
    {
        if (index < 0 || index >= CHUNK_SIZE)
        {
            //prevent crash
            return COLOR_16[0];
        }
        return COLOR_16[index];
    }

    public static Color3f colorFromInt(int val) {
        return new Color3f(((val >> 16) & 0xff) / 255f, ((val >> 8) & 0xff) / 255f, (val & 0xff) / 255f);
    }


}
