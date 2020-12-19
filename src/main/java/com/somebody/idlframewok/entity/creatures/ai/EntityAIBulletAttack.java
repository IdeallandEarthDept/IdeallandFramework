package com.somebody.idlframewok.entity.creatures.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIBulletAttack extends EntityAIBase
{
    public RangedAttackArguments attackArguments = new RangedAttackArguments();
    public static float hostRadius = 0.5f;
    private World worldObj;
    /** The entity the AI instance has been applied to */
    private EntityLiving entityHost;
    private EntityLivingBase attackTarget;
    private float sqRange = 100f;
    /**
     * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the
     * maxRangedAttackTime.
     */
    private int rangedAttackTime = 0;
    private int ticksLookingAtTarget = 0;

    public EntityAIBulletAttack(EntityLiving par1EntityLiving, RangedAttackArguments args){
        this.attackArguments = args;
        sqRange = args.range * args.range;
        this.entityHost = par1EntityLiving;
        this.worldObj = par1EntityLiving.world;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute()
    {
        EntityLivingBase var1 = this.entityHost.getAttackTarget();

        if (var1 == null || this.entityHost.getRNG().nextFloat() > attackArguments.attack_chance)
        {
            return false;
        }
        else
        {
            this.attackTarget = var1;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting()
    {
//        if (attackArguments.isTurret) {
//            return this.shouldExecute();
//        } else {
            return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
//        }
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask()
    {
        this.attackTarget = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask()
    {
        double maxRange = sqRange;
        double targetDistance = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.posY, this.attackTarget.posZ);
        boolean canSee = this.entityHost.getEntitySenses().canSee(this.attackTarget);

        if (canSee)
        {
            ++this.ticksLookingAtTarget;
        }
        else
        {
//            IdlFramework.LogWarning("cant see");
            this.ticksLookingAtTarget = 0;
        }

//        IdlFramework.Log(String.format("Looking ticks = %d", ticksLookingAtTarget ));
//        IdlFramework.Log(String.format("dist = %.2f/%.2f", targetDistance, maxRange));
        if (targetDistance <= maxRange && this.ticksLookingAtTarget >= 20)
        {
            this.entityHost.getNavigator().clearPath();
        }
        else
        {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, attackArguments.bullet_speed);
//            if (!attackArguments.isTurret) {
//                this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, attackArguments.bullet_speed);
//            }
//            else if (this.ticksLookingAtTarget >= 100) {
//                resetTask();
//                return;
//            }
        }

        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
        this.rangedAttackTime = Math.max(this.rangedAttackTime - 1, 0);

        if (this.rangedAttackTime <= 0)
        {
            if (targetDistance <= maxRange && canSee)
            {
                this.doRangedAttack();
                this.rangedAttackTime = attackArguments.cool_down;
            }
        }
    }

    /**
     * Performs a ranged attack according to the AI's rangedAttackID.
     */
    protected void doRangedAttack()
    {
        double d1 = hostRadius;
        Vec3d vec3d = this.entityHost.getLook(1.0F);
        double d2 = attackTarget.posX - (this.entityHost.posX + vec3d.x * d1);
        double d3 = attackTarget.getEntityBoundingBox().minY + (double)(attackTarget.height / 2.0F) - (0.5D + this.entityHost.posY + (double)(this.entityHost.height / 2.0F));
        double d4 = attackTarget.posZ - (this.entityHost.posZ + vec3d.z * d1);
        worldObj.playEvent(null, 1016, new BlockPos(this.entityHost), 0);

        //this swing is not working for turrets, reason unknown.
        //will probably work on other things.
        this.entityHost.swingArm(EnumHand.MAIN_HAND);
        this.entityHost.swingArm(EnumHand.OFF_HAND);
        this.entityHost.limbSwingAmount = 1.5f;

        EntityFireball entityBullet;
        if (attackArguments.attack_mode == BulletMode.SmallFireball) {
            entityBullet = new EntitySmallFireball(worldObj, this.entityHost, d2, d3, d4);
        } else {
            entityBullet = new EntityLargeFireball(worldObj, this.entityHost, d2, d3, d4);
            ((EntityLargeFireball)entityBullet).explosionPower = (int)attackArguments.power;
        }

        entityBullet.posX = this.entityHost.posX + vec3d.x * d1;
        entityBullet.posY = this.entityHost.posY + (double)(this.entityHost.height / 2.0F);
        entityBullet.posZ = this.entityHost.posZ + vec3d.z * d1;
        worldObj.spawnEntity(entityBullet);
    }
}
