package com.somebody.idlframewok.potion;

import com.somebody.idlframewok.Idealland;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

import javax.annotation.Nullable;

public class ModPotionType extends PotionType {
    protected ModPotionType(PotionEffect... p_i46739_1_) {
        this(null, p_i46739_1_);
    }

    public ModPotionType(@Nullable String p_i46740_1_, PotionEffect... p_i46740_2_) {
        super(p_i46740_1_, p_i46740_2_);
        setRegistryName(Idealland.MODID, p_i46740_1_);
        ModPotions.TYPE_INSTANCES.add(this);
    }
}
