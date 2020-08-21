package com.deeplake.idealland.init;

import com.deeplake.idealland.world.dimension.DimenHexaCubix8;
import com.deeplake.idealland.world.dimension.DimensionOne;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class InitDimension {
    public static final DimensionType DIM_ONE = DimensionType.register("Dim_one", "_testdim", ModConfig.DEBUG_CONF.DIM_ONE_ID, DimensionOne.class, false);

    public static void registerDimensions()
    {
        DimensionManager.registerDimension(ModConfig.DEBUG_CONF.DIM_ONE_ID, DIM_ONE);
    }
}
