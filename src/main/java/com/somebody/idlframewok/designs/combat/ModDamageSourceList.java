package com.somebody.idlframewok.designs.combat;

import net.minecraft.util.DamageSource;

public class ModDamageSourceList {
    public static final DamageSource TRAP = (new DamageSource("trap")).setDifficultyScaled();
    public static final DamageSource TRAP_ABSOLUTE = (new DamageSource("trap")).setDamageIsAbsolute();
    public static final DamageSource FLESH = (new DamageSource("wall_of_flesh")).setDifficultyScaled();
}
