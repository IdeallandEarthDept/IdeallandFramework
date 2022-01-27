package com.somebody.idlframewok.util.enumGroup;

import net.minecraft.util.IStringSerializable;

public enum EnumSwitch implements IStringSerializable {
    ON,
    OFF;

    public static final String NAME_ON = "on";
    public static final String NAME_OFF = "off";

    @Override
    public String getName() {
        return this == ON ? NAME_ON : NAME_OFF;
    }

    //same as ordinal
    public int getIndex() {
        return this == ON ? 1 : 0;
    }

}
