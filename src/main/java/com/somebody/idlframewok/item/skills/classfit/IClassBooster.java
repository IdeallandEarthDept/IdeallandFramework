package com.somebody.idlframewok.item.skills.classfit;

import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.item.ItemStack;

public interface IClassBooster {
    default int getBoostLevel(ItemStack stack, EnumSkillClass skillClass)
    {
        return skillClass.getIntFromStack(stack);
    }

    default void setBoostLevel(ItemStack stack, EnumSkillClass skillClass, int level)
    {
        IDLNBTUtil.setInt(stack, skillClass.getKey(), level);
    }
}
