package com.somebody.idlframewok.entity.creatures.ai;

import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIMechInterfere extends EntityAIBase {
    protected final EntityLiving self;

    public EntityAIMechInterfere(EntityLiving self) {
        this.self = self;
        this.setMutexBits(CommonDef.AIMutexFlags.LOOK | CommonDef.AIMutexFlags.MOVE | CommonDef.AIMutexFlags.SWIM_ETC);
    }

    @Override
    public boolean shouldExecute() {
        return EntityUtil.isMechanical(self) && EntityUtil.getBuffLevelIDL(self, ModPotions.INTERFERENCE) > 0;
    }
}
