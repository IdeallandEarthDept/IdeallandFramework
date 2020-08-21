package com.deeplake.idealland.entity.creatures.ideallandTeam;

import com.deeplake.idealland.entity.creatures.ai.EntityAITurretAttack;
import com.deeplake.idealland.util.CommonDef;
import com.deeplake.idealland.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityIdlTurret extends EntityIdeallandUnitBase {
    public EntityIdlTurret(World worldIn) {
        super(worldIn);
        is_mechanic = true;
        is_pinned_on_ground = true;
        setSize(0.5f, 0.5f);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        applyAttackingAI();
        //this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLivingBase.class, 4.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));

        applyTargetingAI();
    }

    protected void applyAttackingAI()
    {
        this.tasks.addTask(1, new EntityAITurretAttack(this, 0.01f));
    }

    protected void applyTargetingAI()
    {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityIdlTurret.class));
        this.targetTasks.addTask(2, new ModAIAttackNearest<>(this, EntityLivingBase.class,
                0, true, true, EntityUtil.HostileToIdl
        ));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16.0D, 0D, 3.0D, 1.0D, 20.0D);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        //ApplyGeneralLevelBoost(difficulty, livingdata);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void onUpdate() {
        if ((world.getWorldTime() % CommonDef.TICK_PER_SECOND == 0) &&
                this.getAttackTarget() != null)
        {
            //prevent the turrets from firing at each other
            if (EntityUtil.getAttitude(this, this.getAttackTarget()) == EntityUtil.ATTITUDE.FRIEND)
            {
                setAttackTarget(null);
            }
        }

        super.onUpdate();
    }
}
