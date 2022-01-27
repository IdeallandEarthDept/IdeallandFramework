package com.somebody.idlframewok.util.enumGroup;

public enum EnumHarvestLevel {
    WOOD(0),
    STONE(1),
    IRON(2),
    DIAMOND(3);

    public final int level;

    EnumHarvestLevel(int level) {
        this.level = level;
    }
}
