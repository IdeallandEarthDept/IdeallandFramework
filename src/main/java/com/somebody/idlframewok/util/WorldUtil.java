package com.somebody.idlframewok.util;

import com.somebody.idlframewok.Idealland;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.BiomeDictionary;

import static net.minecraftforge.common.BiomeDictionary.Type.HILLS;
import static net.minecraftforge.common.BiomeDictionary.Type.MOUNTAIN;

public class WorldUtil {
    //Read-only access
    public static NBTTagCompound getDimNbtIDL(World world)
    {
        if (world == null)
        {
            return new NBTTagCompound();
        }

        int id =world.provider.getDimension();
        WorldInfo info = world.getWorldInfo();
        NBTTagCompound worldNBT = info.getDimensionData(id);
        //Idealland.Log("nbt = ", worldNBT.toString());
        if (worldNBT != null)
        {
            NBTTagCompound idlNBT = worldNBT.getCompoundTag(Idealland.MODID);
            if (idlNBT == null)
            {
                idlNBT = new NBTTagCompound();
                worldNBT.setTag(Idealland.MODID, idlNBT);
                info.setDimensionData(id, worldNBT);
            }

            return idlNBT;

            //need this manual saving
            //info.setDimensionData(world.provider.getDimension(), worldNBT);
        }
        else {
            Idealland.LogWarning("nbt is null");
            return new NBTTagCompound();
        }
    }

    //force save. Not needed if you call the following ones.
    public static void saveDimData(World world)
    {
        WorldInfo info = world.getWorldInfo();
        NBTTagCompound nbtIDL = getDimNbtIDL(world);
        NBTTagCompound nbt = info.getDimensionData(world.provider.getDimension());
        nbt.setTag(Idealland.MODID, nbtIDL);
        info.setDimensionData(world.provider.getDimension(), nbt);
    }

    public static void setDimData(World world, String key, double value)
    {
        WorldInfo info = world.getWorldInfo();
        NBTTagCompound nbtIDL = getDimNbtIDL(world);
        NBTTagCompound nbt = info.getDimensionData(world.provider.getDimension());
        nbtIDL.setDouble(key, value);
        nbt.setTag(Idealland.MODID, nbtIDL);
        info.setDimensionData(world.provider.getDimension(), nbt);
    }

    //It is recommended to use int instead of bool
    public static void setDimData(World world, String key, int value)
    {
        WorldInfo info = world.getWorldInfo();
        NBTTagCompound nbtIDL = getDimNbtIDL(world);
        NBTTagCompound nbt = info.getDimensionData(world.provider.getDimension());
        nbtIDL.setInteger(key, value);
        nbt.setTag(Idealland.MODID, nbtIDL);
        info.setDimensionData(world.provider.getDimension(), nbt);
    }

    public static void setDimData(World world, String key, long value)
    {
        WorldInfo info = world.getWorldInfo();
        NBTTagCompound nbtIDL = getDimNbtIDL(world);
        NBTTagCompound nbt = info.getDimensionData(world.provider.getDimension());
        nbtIDL.setLong(key, value);
        nbt.setTag(Idealland.MODID, nbtIDL);
        info.setDimensionData(world.provider.getDimension(), nbt);
    }

    public static void setDimData(World world, String key, String value)
    {
        WorldInfo info = world.getWorldInfo();
        NBTTagCompound nbtIDL = getDimNbtIDL(world);
        NBTTagCompound nbt = info.getDimensionData(world.provider.getDimension());
        nbtIDL.setString(key, value);
        nbt.setTag(Idealland.MODID, nbtIDL);
        info.setDimensionData(world.provider.getDimension(), nbt);
    }

    public static int getDimDataInt(World world, String key)
    {
        return getDimNbtIDL(world).getInteger(key);
    }

    public static double getDimDataDouble(World world, String key)
    {
        return getDimNbtIDL(world).getDouble(key);
    }

    public static String getDimDataString(World world, String key)
    {
        return getDimNbtIDL(world).getString(key);
    }

    public static boolean isNotFlat(Biome biome) {
        if (BiomeDictionary.getTypes(biome).contains(MOUNTAIN)) {
            return true;
        }

        if (BiomeDictionary.getTypes(biome).contains(HILLS)) {
            return true;
        }

        if (biome.getHeightVariation() > 0.4f) {
            return true;
        }

        return false;
    }

    static final float MAX_SNOW_TEMPER = 0.0f;
    static final float MAX_RAIN_TEMPER = 2.0f;

    public static boolean isWaterRainable(World world, BlockPos pos) {
        return isWaterRainable(world.getBiome(pos).getTemperature(pos));
    }

    public static boolean isWaterRainable(float temperature) {
        return temperature <= MAX_RAIN_TEMPER && temperature >= MAX_SNOW_TEMPER;
    }
}
