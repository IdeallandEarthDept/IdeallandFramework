package com.somebody.idlframewok.designs;

public enum EnumDamageResistance {

    H2_WEAK("h_weak",4f),
    HIGH_WEAK("h_weak",2f),
    WEAK("weak",1.5f),
    SLIGHT_WEAK("s_weak",1.25f),
    NONE("none",1f),
    SLIGHT_RESIST("s_resist",0.8f),
    RESIST("resist",0.67f),
    HIGH_RESIST("h_resist",0.5f),
    H2_RESIST("h2_resist",0.25f),
    H3_RESIST("h2_resist",0.1f);

    final String key;
    final float modifier;

    EnumDamageResistance(String key, float modifier)
    {
        this.key = key;
        this.modifier = 1f;
    }

    public float getModifier() {
        return modifier;
    }
}
