package com.somebody.idlframewok.item.skills.classfit;

import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.item.ItemStack;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.SKILL_CLASS_PREFIX;

public enum EnumSkillClass {
    NONE("c_none"),
    OBSERVER("c_observer"),
    EGO_TWIN("c_ego_twin"),
    SAMURAI("c_samurai");

    String name;

    EnumSkillClass(String name)
    {
        this.name = name;
    }

    public String getKey()
    {
        return name;
    }
    public String getUnlocalized() {return SKILL_CLASS_PREFIX +getKey();}

    public int getIntFromStack(ItemStack stack)
    {
        if (this == NONE)
        {
            return 0;
        }

        return IDLNBTUtil.GetInt(stack, name);
    }
}
