package com.somebody.idlframewok.entity.creatures.ai;

import com.somebody.idlframewok.entity.creatures.misc.EntitySquidBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAISquidMove extends EntityAIBase
{
    private final EntitySquidBase squid;

    public EntityAISquidMove(EntitySquidBase p_i45859_1_)
    {
        this.squid = p_i45859_1_;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return true;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        int i = this.squid.getIdleTime();

        if (i > 100)
        {
            this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
        }
        else if (this.squid.getRNG().nextInt(50) == 0 || !this.squid.isInWater() || !this.squid.hasMovementVector())
        {
            float f = this.squid.getRNG().nextFloat() * ((float)Math.PI * 2F);
            float f1 = MathHelper.cos(f) * 0.2F;
            float f2 = -0.1F + this.squid.getRNG().nextFloat() * 0.2F;
            float f3 = MathHelper.sin(f) * 0.2F;
            this.squid.setMovementVector(f1, f2, f3);
        }
    }
}
