package com.somebody.idlframewok.world.dimension.universal;

import com.somebody.idlframewok.world.dimension.universal.structure.GenGodAltarBase;
import com.somebody.idlframewok.world.dimension.universal.structure.GenGodAltarPantheon;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class InitUnivGen {

    public static GenGodAltarBase[] GEN_GOD_ALTAR;
    public static GenGodAltarPantheon GEN_PANTHEON ;

    public static void init()
    {
        GEN_GOD_ALTAR = new GenGodAltarBase[16];
        for (int i = 0; i < CHUNK_SIZE; i++)
        {
            GEN_GOD_ALTAR[i] = new GenGodAltarBase(i);
        }
        GEN_PANTHEON = new GenGodAltarPantheon(0);
    }
}
