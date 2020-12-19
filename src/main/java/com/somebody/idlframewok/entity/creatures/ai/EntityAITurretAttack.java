package com.somebody.idlframewok.entity.creatures.ai;

import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import com.somebody.idlframewok.entity.projectiles.ProjectileArgs;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;

import static net.minecraft.entity.SharedMonsterAttributes.ATTACK_SPEED;

public class EntityAITurretAttack extends EntityAIBase {

    protected final EntityLiving self;
    protected int curCoolDown;
    protected float rangeSquare = 400f;
    float errorModifier;
    float bulletAccel = 0.1f;

    public int coolDownMin = 20;//ticks
    public int coolDownDelta = 20;//ticks

    public EntityAITurretAttack(EntityLiving self, float errorModifier)
    {
        this.self = self;
        this.errorModifier = errorModifier;
    }

    public EntityAITurretAttack(EntityLiving self, float errorModifier, int coolDownMin, int coolDownDelta) {
        this.self = self;
        this.errorModifier = errorModifier;
        this.coolDownMin = coolDownMin;
        this.coolDownDelta = coolDownDelta;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = self.getAttackTarget();
        return entitylivingbase != null && entitylivingbase.isEntityAlive();
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.curCoolDown = coolDownMin;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {

    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        if (self.world.getDifficulty() != EnumDifficulty.PEACEFUL)
        {
            --this.curCoolDown;
            EntityLivingBase attackTarget = self.getAttackTarget();

            if (attackTarget != null)
            {
                double d0 = self.getDistanceSq(attackTarget);
                self.getLookHelper().setLookPosition(attackTarget.posX,
                        attackTarget.posY + (double)attackTarget.getEyeHeight(),
                        attackTarget.posZ,
                        (float)self.getHorizontalFaceSpeed(),
                        (float)self.getVerticalFaceSpeed());

                rangeSquare = (float) (EntityUtil.getSight(self) * EntityUtil.getSight(self));

                if (d0 < rangeSquare)
                {
                    if (this.curCoolDown <= 0)
                    {
                        IAttributeInstance attribute = self.getEntityAttribute(ATTACK_SPEED);
                        double speedFactor = ((attribute == null) || (attribute.getAttributeValue() == 0)) ? 1 : attribute.getBaseValue() / attribute.getAttributeValue();

                        this.curCoolDown = (int) ((coolDownMin + self.getRNG().nextInt(coolDownDelta)) * speedFactor);
                        onAttackTriggered(attackTarget);

                    }
                }
                else
                {
                    self.setAttackTarget(null);
                }
            }
            super.updateTask();
        }
    }
    
    public void onAttackTriggered(EntityLivingBase target)
    {
        double d0 = self.getDistanceSq(target);
        double d1 = target.posX - self.posX;
        double d2 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (self.posY + (double)(self.height / 2.0F));
        double d3 = target.posZ - self.posZ;

        float dist =  MathHelper.sqrt(MathHelper.sqrt(d0));
        float estimateHitTime = MathHelper.sqrt(2 * bulletAccel * dist);

        //float halfDist = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
        
        EntityIdlProjectile entityIdlProjectile = new EntityIdlProjectile(self.world, new ProjectileArgs((float) EntityUtil.getAttack(self)), self,
                d1 + self.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionX,
                d2 + self.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionY,
                d3 + self.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionZ,
                bulletAccel);

        //entityIdlProjectile.posY += self.posY + (double)(self.height / 2.0F);
        self.world.spawnEntity(entityIdlProjectile);
    }
}
