package com.somebody.idlframewok.util;

import static com.somebody.idlframewok.util.Reference.MOD_ID;

//note it's usually called advancements instead of achievements
public class AchvDef {
    public final static String ELK_TRANSFORM = "elk_transform";
    public final static String GetAchvName(String key)
    {
        return MOD_ID + ":" + key;
    }
}
