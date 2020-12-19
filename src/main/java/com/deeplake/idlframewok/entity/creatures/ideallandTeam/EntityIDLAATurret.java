package com.deeplake.idlframewok.entity.creatures.ideallandTeam;

import com.deeplake.idlframewok.entity.creatures.ai.EntityAITurretAttack;
import com.deeplake.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;

import net.minecraft.world.World;

public class EntityIDLAATurret extends EntityIdlTurret {
    public EntityIDLAATurret(World worldIn) {
        super(worldIn);
    }

    protected void applyAttackingAI()
    {
        this.tasks.addTask(1, new EntityAITurretAttack(this, 0.01f, 10, 5));
        //this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    protected void applyTargetingAI()
    {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityIdlTurret.class));
        this.targetTasks.addTask(2, new ModAIAttackNearestAntiAir<>(this, EntityLivingBase.class,
                0, true, false, EntityUtil.HostileToIdl_AIR
        ));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(64.0D, 0D, 5.0D, 3.0D, 50.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        EntityLivingBase target = getAttackTarget();
        if (target != null && target.onGround)
        {
            setAttackTarget(null);
        }
    }
}
