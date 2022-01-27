package com.somebody.idlframewok.blocks.stateEnums;

import net.minecraft.util.IStringSerializable;

public enum EnumActive implements IStringSerializable
{
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String name;

    private EnumActive(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return this.name;
    }

    public String getName()
    {
        return this.name;
    }
}
