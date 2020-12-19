package com.somebody.idlframewok.entity.creatures.ai;

import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumHand;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityAIStrafeRangedAttack<T extends EntityLiving & IRangedAttackMob> extends EntityAIBase
{
    private final T entity;
    private final double moveSpeedAmp;
    private int attackCooldown;
    private final float maxAttackDistanceSq;
    private int attackTime = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;
    float targetLostThreshold = 3f;

    float aimingCooldownTimeMin = 0.3f;
    float aimingCooldownTimeDelta = 0.7f;

    int curAimingCooldownThreshold = 0;
    void randomAimingCooldown()
    {
        curAimingCooldownThreshold = (int) (TICK_PER_SECOND *( aimingCooldownTimeMin + this.entity.getRNG().nextFloat() * aimingCooldownTimeDelta));
    }

    public EntityAIStrafeRangedAttack(T self, double moveSpeedAmp, int attackCd, float maxAttackDistance)
    {
        this.entity = self;
        this.moveSpeedAmp = moveSpeedAmp;
        this.attackCooldown = attackCd;
        this.maxAttackDistanceSq = maxAttackDistance * maxAttackDistance;
        this.setMutexBits(3);
    }

    //This is the time between two attacking-aiming sequence
    public void setAttackCooldown(int attackCooldown)
    {
        this.attackCooldown = attackCooldown;
    }

    //aiming can be interruppted by sight lost
    public void setAimingCooldown(float minVal, float range)
    {
        aimingCooldownTimeMin = minVal;
        aimingCooldownTimeDelta = range;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return this.entity.getAttackTarget() != null && this.isRangedWeaponInMainhand();
    }

    protected boolean isRangedWeaponInMainhand()
    {
        return CommonFunctions.isItemRangedWeapon(this.entity.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND));
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return (this.shouldExecute() || !this.entity.getNavigator().noPath()) && this.isRangedWeaponInMainhand();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.entity.setSwingingArms(true);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        super.resetTask();
        this.entity.setSwingingArms(false);
        this.seeTime = 0;
        this.attackTime = -1;
        this.entity.resetActiveHand();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        EntityLivingBase entitylivingbase = this.entity.getAttackTarget();

        if (entitylivingbase != null)
        {
            double d0 = this.entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
            boolean canSee = this.entity.getEntitySenses().canSee(entitylivingbase);
            boolean flag1 = this.seeTime > 0;

            if (canSee != flag1)
            {
                this.seeTime = 0;
            }

            if (canSee)
            {
                ++this.seeTime;
            }
            else
            {
                --this.seeTime;
            }

            if (d0 <= (double)this.maxAttackDistanceSq && this.seeTime >= TICK_PER_SECOND)
            {
                this.entity.getNavigator().clearPath();
                ++this.strafingTime;
            }
            else
            {
                this.entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= TICK_PER_SECOND)
            {
                if ((double)this.entity.getRNG().nextFloat() < 0.3D)
                {
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if ((double)this.entity.getRNG().nextFloat() < 0.3D)
                {
                    this.strafingBackwards = !this.strafingBackwards;
                }

                this.strafingTime = 0;
            }

            if (this.strafingTime > -1)
            {
                if (d0 > (double)(this.maxAttackDistanceSq * 0.75F))
                {
                    this.strafingBackwards = false;
                }
                else if (d0 < (double)(this.maxAttackDistanceSq * 0.25F))
                {
                    this.strafingBackwards = true;
                }

                this.entity.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                this.entity.faceEntity(entitylivingbase, 30.0F, 30.0F);
            }
            else
            {
                this.entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
            }

            //IdlFramework.Log("isHandActive = %s, uuid=%s", this.entity.isHandActive(), this.entity.getUniqueID());
            if (this.entity.isHandActive())
            {
                if (!canSee && this.seeTime < -targetLostThreshold * TICK_PER_SECOND)
                {
                    this.entity.resetActiveHand();
                }
                else if (canSee)
                {
                    int aimingTicks = this.entity.getItemInUseMaxCount();

                    if (aimingTicks >= curAimingCooldownThreshold)
                    {
                        this.entity.resetActiveHand();
                        this.entity.attackEntityWithRangedAttack(entitylivingbase, (float) d0);
                        this.attackTime = this.attackCooldown;

                        randomAimingCooldown();
                    }
                }
            }
            else if (--this.attackTime <= 0 && this.seeTime >= -targetLostThreshold * TICK_PER_SECOND)
            {
                this.entity.setActiveHand(EnumHand.MAIN_HAND);
            }
        }
    }
}