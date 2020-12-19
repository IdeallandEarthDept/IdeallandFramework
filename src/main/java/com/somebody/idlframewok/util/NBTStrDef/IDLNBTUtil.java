package com.somebody.idlframewok.util.NBTStrDef;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.util.*;

import static com.somebody.idlframewok.util.IDLNBT.*;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.*;

//on a server, strlen 65000 is ok, but 66000 will crash
public class IDLNBTUtil {
	public static NBTTagCompound getNBT(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
		{
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		return nbt;
	}
	
	public static NBTTagCompound getNBT(Entity entity) {
		NBTTagCompound nbt = entity.getEntityData();
	    return nbt;
	}
	
	
	public static NBTTagCompound getNBT(NBTTagCompound tag) {
	    if(tag == null) {
	      return new NBTTagCompound();
	    }

	    return tag;
	  }
	
	//writeEntityToNBT
	//readEntityFromNBT
	
	@Nullable
	public static boolean StackHasKey(ItemStack stack, String key) {
		return !(stack.isEmpty() || !getNBT(stack).hasKey(key));
	}

	//Boolean
	public static boolean SetBoolean(ItemStack stack, String key, boolean value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setBoolean(key, value);
		return true;
	}
	
	public static boolean GetBoolean(ItemStack stack, String key, boolean defaultVal)
	{	
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getBoolean(key);
		}		
		else
		{
			return defaultVal;
		}
	}
	
	public static boolean GetBoolean(ItemStack stack, String key)
	{	
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getBoolean(key);
		}		
		else
		{
			return false;
		}
	}
	//get with default val
	public static boolean GetBooleanDF(ItemStack stack, String key, boolean defaultVal)
	{	
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getBoolean(key);
		}		
		else
		{
			return defaultVal;
		}
	}

	//Double
	public static boolean SetDouble(ItemStack stack, String key, double value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setDouble(key, value);
		return true;
	}

	public static double GetDouble(ItemStack stack, String key, double defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getDouble(key);
		}
		else
		{
			return defaultVal;
		}
	}

	//Integer
	public static boolean SetLong(ItemStack stack, String key, long value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setLong(key, value);
		return true;
	}
	public static boolean SetInt(ItemStack stack, String key, int value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setInteger(key, value);
		return true;
	}
	public static boolean SetInt(Entity entity, String key, int value)
	{
		NBTTagCompound nbt = getNBT(entity);
		nbt.setInteger(key, value);
		return true;
	}

	public static int GetInt(Entity entity, String key, int defaultVal)
	{
		if (EntityHasKey(entity, key))
		{
			NBTTagCompound nbt = getNBT(entity);
			return nbt.getInteger(key);
		}
		else
		{
			return defaultVal;
		}
	}

	public static int GetInt(ItemStack stack, String key, int defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getInteger(key);
		}		
		else
		{
			return defaultVal;
		}
	}

	public static long GetLong(ItemStack stack, String key, int defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getLong(key);
		}
		else
		{
			return defaultVal;
		}
	}


	public static int GetInt(ItemStack stack, String key)
	{
		return GetInt(stack, key, 0);
	}
	
	//String
	public static String GetString(ItemStack stack, String key, String defaultVal)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getString(key);
		}		
		else
		{
			return defaultVal;
		}
	}
	
	public static boolean SetString(ItemStack stack, String key, String value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setString(key, value);

		return true;
	}

	
	//entity
	@Nullable
	public static boolean EntityHasKey(Entity entity, String key)
	{
		return getNBT(entity).hasKey(key);
	}
	
	//Boolean
	public static boolean GetBoolean(Entity entity, String key, boolean defaultVal)
	{	
		if (EntityHasKey(entity, key))
		{
			NBTTagCompound nbt = getNBT(entity);
			return nbt.getBoolean(key);
		}		
		else
		{
			return defaultVal;
		}
	}
	
	public static boolean SetBoolean(Entity stack, String key, boolean value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setBoolean(key, value);
		return true;
	}

	public static boolean SetString(Entity stack, String key, String value)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setString(key, value);
		return true;
	}

	public static int[] GetIntArray(ItemStack stack, String key)
	{
		if (StackHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getIntArray(key);
		}
		else
		{
			return new int[0];
		}
	}

	public static int[] GetIntArray(EntityLivingBase stack, String key)
	{
		if (EntityHasKey(stack, key))
		{
			NBTTagCompound nbt = getNBT(stack);
			return nbt.getIntArray(key);
		}
		else
		{
			return new int[0];
		}
	}

	public static void SetIntArray(ItemStack stack, String key, int[] array)
	{
		NBTTagCompound nbt = getNBT(stack);
		nbt.setIntArray(key, array);
	}

	public static void SetGuaEnhanceFree(ItemStack stack, int val)
	{
		SetInt(stack, GUA_FREE_SOCKET, val);
	}

	public static boolean GetIsLearned(EntityPlayer player, int skillID)
	{
		int[] learnt = getPlayerIdeallandIntArraySafe(player, STARTER_KIT_VERSION_TAG);
		if (Arrays.binarySearch(learnt, skillID) >= 0)
		{
			return true;
		}
		return false;
	}

	public static void SetIsLearned(EntityPlayer player, int skillID, boolean val)
	{
		int[] learnt = getPlayerIdeallandIntArraySafe(player, LEARNING_DONE);
		int oldIndex = Arrays.binarySearch(learnt, skillID);
		if (oldIndex >= 0)
		{
			if (val)
			{
				return;
			}else {
				//todo: remove it

			}
		}else {
			if (val)
			{
				ArrayList<Integer> list = new ArrayList<Integer>();
				for (int oldID:
						learnt
					 ) {
					list.add(oldID);
				}
				list.add(skillID);
				Collections.sort(list);

				int[] newLearnt = list.stream().mapToInt(Integer::valueOf).toArray();
				setPlayerIdeallandTagSafe(player, LEARNING_DONE, newLearnt);
			}else {
				return;
			}
		}
	}
	//--------------------------------------------



	public static BlockPos getMarkedPos(ItemStack stack)
	{
		NBTTagCompound NBT = IDLNBTUtil.getNBT(stack);
		return new BlockPos(NBT.getDouble(ANCHOR_X), NBT.getDouble(ANCHOR_Y), NBT.getDouble(ANCHOR_Z));
	}

	public static BlockPos getMarkedPos2(ItemStack stack)
	{
		NBTTagCompound NBT = IDLNBTUtil.getNBT(stack);
		return new BlockPos(NBT.getDouble(ANCHOR_X_2), NBT.getDouble(ANCHOR_Y_2), NBT.getDouble(ANCHOR_Z_2));
	}

	public static void markPosToStack(ItemStack stack, BlockPos pos)
	{
		IDLNBTUtil.SetBoolean(stack, ANCHOR_READY, true);
		IDLNBTUtil.SetDouble(stack, ANCHOR_X, pos.getX());
		IDLNBTUtil.SetDouble(stack, ANCHOR_Y, pos.getY());
		IDLNBTUtil.SetDouble(stack, ANCHOR_Z, pos.getZ());
	}

	public static void markPosToStack2(ItemStack stack, BlockPos pos)
	{
		IDLNBTUtil.SetBoolean(stack, ANCHOR_READY_2, true);
		IDLNBTUtil.SetDouble(stack, ANCHOR_X_2, pos.getX());
		IDLNBTUtil.SetDouble(stack, ANCHOR_Y_2, pos.getY());
		IDLNBTUtil.SetDouble(stack, ANCHOR_Z_2, pos.getZ());
	}
}
