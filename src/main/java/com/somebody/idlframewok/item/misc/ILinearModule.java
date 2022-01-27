package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.item.ItemStack;

public interface ILinearModule {
    default int getState(ItemStack stack)
    {
        return IDLNBTUtil.GetInt(stack, IDLNBTDef.STATE);
    }

    default void setState(ItemStack stack, int val)
    {
        IDLNBTUtil.setInt(stack, IDLNBTDef.STATE, val);
    }
}
