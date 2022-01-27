package com.somebody.idlframewok.util.enumGroup;

import net.minecraft.util.IStringSerializable;

public enum EnumVariants implements IStringSerializable {
    V_0(0, "0"),
    V_1(1, "1"),
    V_2(2, "2"),
    V_3(3, "3"),
    V_4(4, "4"),
    V_5(5, "5"),
    V_6(6, "6"),
    V_7(7, "7"),
    V_8(8, "8"),
    V_9(9, "9"),
    V_10(10, "10"),
    V_11(11, "11"),
    V_12(12, "12"),
    V_13(13, "13"),
    V_14(14, "14"),
    V_15(15, "15");


    static final EnumVariants[] META_LOOKUP = new EnumVariants[values().length];
    final int meta;
    final String name, unlocalizedName;

    private EnumVariants(int meta, String name) {
        this(meta, name, name);
    }

    private EnumVariants(int meta, String name, String unlocalizedName) {
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

    public static EnumVariants byMetadata(int meta) {
        return META_LOOKUP[meta];
    }

    static {
        for (EnumVariants enumType : values()) {
            META_LOOKUP[enumType.getMeta()] = enumType;
        }
    }
}
