package com.somebody.idlframewok.util.enumGroup;

import net.minecraft.util.IStringSerializable;

public enum EnumGodType implements IStringSerializable {
    //note that variants can be at most 16
    EARTH(0, "earth"),
    FIRE(1, "fire"),//0~15
    LIFE(2, "life");//0~15

    static final EnumGodType[] META_LOOKUP = new EnumGodType[values().length];
    final int meta;
    final String name, unlocalizedName;

    private EnumGodType(int meta, String name) {
        this(meta, name, name);
    }

    private EnumGodType(int meta, String name, String unlocalizedName) {
        this.meta = meta;
        this.name = name;
        this.unlocalizedName = unlocalizedName;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getMeta() {
        return meta;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public String toString() {
        return name;
    }

    public static EnumGodType byMetadata(int meta) {
        return META_LOOKUP[meta];
    }

    static {
        for (EnumGodType enumType : values()) {
            META_LOOKUP[enumType.getMeta()] = enumType;
        }
    }
}
