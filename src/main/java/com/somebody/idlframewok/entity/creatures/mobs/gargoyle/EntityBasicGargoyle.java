package com.somebody.idlframewok.entity.creatures.mobs.gargoyle;

import net.minecraft.world.World;

public class EntityBasicGargoyle extends EntityGargoyleBase {
    public EntityBasicGargoyle(World worldIn) {
        super(worldIn);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(8.0D, 0.2D, 5.0D, 0, 40);
    }

//    protected void applyTargetAI() {
//        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIdeallandUnitBase.class, true));
//        ((PathNavigateGround) this.getNavigator()).setEnterDoors(false);
//    }
}
