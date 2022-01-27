package com.somebody.idlframewok.entity.creatures.misc;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityAutumnVisitor extends EntityModUnit {
    static EntityAutumnVisitor instance;
    public EntityAutumnVisitor(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onRemovedFromWorld() {
        if (instance == this)
        {
            instance = null;
        }
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX,posY,posZ,0,0,0);
        super.onRemovedFromWorld();
    }

    @Override
    public boolean isEntityUndead() {
        return true;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(32.0D, 0.5D, 16.0D, 5.0D, 40.0D);
    }

    protected void firstTickAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 2.0D, false));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.6D, 1D));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityLivingBase.class, EntityUtil.LIVING_HIGHER, 16.0F, 0.6D, 1D));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityEvoker.class, true, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVindicator.class, true, true));
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (instance == null)
        {
            instance = this;
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote)
        {
            if (!isRiding())
            {
                setDead();
                playSound(SoundEvents.AMBIENT_CAVE, 1f, 0.5f);
            }

            if (instance != this && instance != null)
            {
                Idealland.Log("instance = ", instance);
                setDead();
            }

            if (this.world.isDaytime())
            {
                setDead();
            }
        }
    }
}
