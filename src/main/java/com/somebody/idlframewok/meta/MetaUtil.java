package com.somebody.idlframewok.meta;

import net.minecraftforge.fml.common.Loader;

public class MetaUtil {
    public static boolean isIDLLoaded = false;
    public static boolean isIRRLoaded = false;
    public static boolean isLoaded_TiC = false;
    public static boolean isLoaded_Slashblade = false;
    public static boolean isLoaded_Botania = false;
    public static boolean isLoaded_DWeapon = false;
    public static boolean isLoaded_AOA3 = false;
    public static boolean isLoaded_GC = false;
    public static boolean isLoaded_Taoism = false;
    public static boolean isLoaded_GOG = false;

    //extra difficulty
    public static int HARD_AOA3 = 5;
    public static int HARD_GOG = 4;

    //static int modListDifficulty = 0;
    static int modListExtraDifficulty = 0;

    public static int getModListExtraDifficulty() {
        return modListExtraDifficulty;
    }

    public static void CalcModListDifficulty()
    {
       //modListDifficulty = CommonFunctions.GetModCount();
        if (isLoaded_AOA3)
        {
            modListExtraDifficulty+=HARD_AOA3;
        }

        if(isLoaded_GOG)
        {
            modListExtraDifficulty+=HARD_GOG;
        }
    }

    public static int GetModCount()
    {
        return Loader.instance().getActiveModList().size();
    }
}
