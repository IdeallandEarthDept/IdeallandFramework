package com.deeplake.idealland.combat;

import com.deeplake.idealland.IdlFramework;
import net.minecraft.util.DamageSource;

import static com.deeplake.idealland.util.CommonDef.GUA_TYPES;

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
            IdlFramework.LogWarning("Trying to access invalid gua type:" + index);
        }
        return this;
    }

    public boolean isOfType(int index)
    {
        if (index < 0 || index >= GUA_TYPES)
        {
            //out of range
            IdlFramework.LogWarning("Trying to access invalid gua type:" + index);
            return false;
        }

        return typeFlags[index];
    }

}
