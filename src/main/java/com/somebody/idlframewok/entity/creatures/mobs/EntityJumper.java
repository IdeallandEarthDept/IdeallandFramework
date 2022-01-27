package com.somebody.idlframewok.entity.creatures.mobs;

import com.somebody.idlframewok.entity.creatures.EntityGeneralMob;
import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.world.World;

public class EntityJumper extends EntityGeneralMob {

    //a mob in hilly areas

    public float[] jumpHeight = new float[]{1, 1, 3, 1, 8};
    int index = 0;

    public EntityJumper(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (CommonFunctions.isSecondTick(world) && onGround) {
            jump();//what? does not work. keep jumping in air. dunno why
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        super.fall(distance, 0);
    }

    @Override
    protected float getJumpUpwardsMotion() {
        index++;
        if (index >= jumpHeight.length) {
            index = 0;
        }
        return jumpHeight[index];
    }

    @Override
    public int getMaxFallHeight() {
        return 8;
    }
}
