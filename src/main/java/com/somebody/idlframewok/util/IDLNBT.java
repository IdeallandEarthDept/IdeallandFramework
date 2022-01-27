package com.somebody.idlframewok.util;

import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import net.minecraft.nbt.NBTTagCompound;

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


}
