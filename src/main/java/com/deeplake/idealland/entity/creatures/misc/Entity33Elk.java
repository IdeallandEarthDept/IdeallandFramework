package com.deeplake.idealland.entity.creatures.misc;

import com.deeplake.idealland.entity.creatures.EntityModUnit;
import com.deeplake.idealland.entity.creatures.moroon.EntityMoroonBombBeacon;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

//OKO OKO OKO OKO OKO OKO OKO OKO OKO
//OKO OKO OKO OKO OKO OKO OKO OKO OKO
//OKO OKO OKO OKO OKO OKO OKO OKO OKO
public class Entity33Elk extends EntityModUnit {
    public Entity33Elk(World worldIn) {
        super(worldIn);
        experienceValue = 3;
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        //this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 8.0F, 0.6D, 0.6D));
        //this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        //this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16,0.3f,3,0,33);
    }
}
