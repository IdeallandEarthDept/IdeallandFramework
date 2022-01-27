package com.somebody.idlframewok.init;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.world.dimension.DimenHexaCubix8;
import com.somebody.idlframewok.world.dimension.DimensionOne;
import com.somebody.idlframewok.world.dimension.dungeon.DimInfDungeon;
import com.somebody.idlframewok.world.dimension.universal.DimensionUniversal;
import com.somebody.idlframewok.world.dimension.universal.InitUnivGen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class InitDimension {
    public static final DimensionType DIM_ONE = DimensionType.register("Dim_one", "_testdim", ModConfig.WORLD_GEN_CONF.DIM_ONE_ID, DimensionOne.class, false);
    public static final DimensionType DIM_TWO = DimensionType.register("Dim_two", "_testdim2", ModConfig.WORLD_GEN_CONF.DIM_TWO_ID, DimenHexaCubix8.class, false);
    public static DimensionType DIM_UNIV = DimensionType.register("idl_universal", "_idluniv", ModConfig.WORLD_GEN_CONF.DIM_UNIV_ID, DimensionUniversal.class, false);
    public static DimensionType DIM_DEBUG = DimensionType.register("idl_debug", "_idldebug", ModConfig.WORLD_GEN_CONF.DIM_DEBUG_ID, DimInfDungeon.class, false);

    public static void registerDimensions()
    {
        if (ModConfig.DEBUG_CONF.ENABLE_HEX)
        {
            DimensionManager.registerDimension(ModConfig.WORLD_GEN_CONF.DIM_ONE_ID, DIM_ONE);
            DimensionManager.registerDimension(ModConfig.WORLD_GEN_CONF.DIM_TWO_ID, DIM_TWO);
        }

        DimensionManager.registerDimension(ModConfig.WORLD_GEN_CONF.DIM_UNIV_ID, DIM_UNIV);

        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
            try
            {
                DimensionManager.registerDimension(ModConfig.WORLD_GEN_CONF.DIM_DEBUG_ID, DIM_DEBUG);
            }
            catch (Exception e)
            {
                Idealland.LogWarning(e.toString());
            }
        }
        InitUnivGen.init();
    }

    public static NBTTagCompound getDimensionData(World world) {
        return world.getWorldInfo().getDimensionData(ModConfig.WORLD_GEN_CONF.DIM_UNIV_ID);
    }
    public void init()
    {

    }
}
