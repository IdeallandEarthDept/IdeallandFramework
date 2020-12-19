package com.somebody.idlframewok.util;

import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.IDEALLAND;

public class IDLNBT {
	public int pearlCount;
	public boolean isEarth;
	public boolean isSky;
	
	private final NBTTagCompound basic;
	
	public IDLNBT()
	{
		pearlCount = 0;
		isEarth = false;
		isSky = false;
		
		basic = new NBTTagCompound();
	}
	
	public IDLNBT(NBTTagCompound srcNBT)
	{
		readFromBasic(srcNBT);
		basic = srcNBT;
	}
	
	public void readFromBasic(NBTTagCompound tag)
	{
		if (tag != null)
		{			
			pearlCount = tag.getInteger(IDLNBTDef.LEVEL_TAG);
			isEarth = tag.getBoolean(IDLNBTDef.IS_EARTH);
			isSky = tag.getBoolean(IDLNBTDef.IS_SKY);
		}
	}
	
	public void writeToBasic(NBTTagCompound tag)
	{
		if (tag == null)
		{			
			tag = new NBTTagCompound();
		}
		
		tag.setInteger(IDLNBTDef.LEVEL_TAG, pearlCount);
		tag.setBoolean(IDLNBTDef.IS_EARTH, isEarth);
		tag.setBoolean(IDLNBTDef.IS_SKY, isSky);
	}
	
	public NBTTagCompound getBasic()
	{
		NBTTagCompound tag = basic.copy();
		writeToBasic(tag);

	    return tag;
	}
	
	public void save()
	{
		writeToBasic(basic);
	}

	//PlayerData
	//--PERSISTED_NBT_TAG
	//  --IDEALLAND
	//    --KILL_COUNT,etc


	public static NBTTagCompound getTagSafe(NBTTagCompound tag, String key) {
		if(tag == null) {
			return new NBTTagCompound();
		}

		return tag.getCompoundTag(key);
	}

	public static NBTTagCompound getPlyrIdlTagSafe(EntityPlayer player) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getTagSafe(data, IDEALLAND);

		return idl_data;
	}

	public static NBTTagCompound getPlayerIdeallandTagGroupSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getCompoundTag(key);
	}

	public static int[] getPlayerIdeallandIntArraySafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getIntArray(key);
	}

	public static int getPlayerIdeallandIntSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getInteger(key);
	}
	public static float getPlayerIdeallandFloatSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getFloat(key);
	}
	public static double getPlayerIdeallandDoubleSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getDouble(key);
	}
	public static boolean getPlayerIdeallandBoolSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getBoolean(key);
	}
	public static String getPlayerIdeallandStrSafe(EntityPlayer player, String key) {
		return getPlyrIdlTagSafe(player).getString(key);
	}

	public static void setPlayerIdeallandTagSafe(EntityPlayer player, String key, int value) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getPlyrIdlTagSafe(player);

		idl_data.setInteger(key, value);

		data.setTag(IDEALLAND, idl_data);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
	}

	public static void setPlayerIdeallandTagSafe(EntityPlayer player, String key, int[] value) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getPlyrIdlTagSafe(player);

		idl_data.setIntArray(key, value);

		data.setTag(IDEALLAND, idl_data);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
	}

	public static void setPlayerIdeallandTagSafe(EntityPlayer player, String key, double value) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getPlyrIdlTagSafe(player);

		idl_data.setDouble(key, value);

		data.setTag(IDEALLAND, idl_data);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
	}

	public static void setPlayerIdeallandTagSafe(EntityPlayer player, String key, boolean value) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getPlyrIdlTagSafe(player);

		idl_data.setBoolean(key, value);

		data.setTag(IDEALLAND, idl_data);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
	}

	public static void setPlayerIdeallandTagSafe(EntityPlayer player, String key, String value) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound idl_data = getPlyrIdlTagSafe(player);

		idl_data.setString(key, value);

		data.setTag(IDEALLAND, idl_data);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
	}
}
