package com.somebody.idlframewok.designs.combat;

import com.somebody.idlframewok.Idealland;
import net.minecraft.util.DamageSource;

import static com.somebody.idlframewok.util.CommonDef.GUA_TYPES;

public class IdlEntityDamageSource extends DamageSource {
    public boolean[] typeFlags = new boolean[GUA_TYPES];

    public IdlEntityDamageSource(String damageTypeIn) {
        super(damageTypeIn);
    }

    public IdlEntityDamageSource setOfType(int index, boolean val)
    {
        if (index >= 0 && index < GUA_TYPES)
        {
            typeFlags[index] = val;
        }
        else {
            //out of range
            Idealland.LogWarning("Trying to access invalid gua type:" + index);
        }
        return this;
    }

    public boolean isOfType(int index)
    {
        if (index < 0 || index >= GUA_TYPES)
        {
            //out of range
            Idealland.LogWarning("Trying to access invalid gua type:" + index);
            return false;
        }

        return typeFlags[index];
    }

}
