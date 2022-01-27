package com.somebody.idlframewok.init;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class InitDimension {
//    public static DimensionType DIM_UNIV = DimensionType.register("idl_universal", "_idluniv", ModConfig.WORLD_GEN_CONF.DIM_UNIV_ID, DimensionUniversal.class, false);
//    public static DimensionType DIM_DEBUG = DimensionType.register("idl_debug", "_idldebug", ModConfig.WORLD_GEN_CONF.DIM_DEBUG_ID, DimInfDungeon.class, false);

    public static void registerDimensions()
    {
//        DimensionManager.registerDimension(ModConfig.WORLD_GEN_CONF.DIM_UNIV_ID, DIM_UNIV);
//
//        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
//        {
//            try
//            {
//                DimensionManager.registerDimension(ModConfig.WORLD_GEN_CONF.DIM_DEBUG_ID, DIM_DEBUG);
//            }
//            catch (Exception e)
//            {
//                Idealland.LogWarning(e.toString());
//            }
//        }
//        InitUnivGen.init();
    }

    public static NBTTagCompound getDimensionData(World world) {
        return world.getWorldInfo().getDimensionData(ModConfig.WORLD_GEN_CONF.DIM_UNIV_ID);
    }
    public void init()
    {

    }
}
