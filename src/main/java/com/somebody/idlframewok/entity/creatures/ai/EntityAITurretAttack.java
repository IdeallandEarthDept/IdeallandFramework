package com.somebody.idlframewok.entity.creatures.ai;

import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import com.somebody.idlframewok.entity.projectiles.ProjectileArgs;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;

import java.util.Random;

import static net.minecraft.entity.SharedMonsterAttributes.ATTACK_SPEED;

public class EntityAITurretAttack extends EntityAIBase {

    protected final EntityLiving self;
    protected int curCoolDown;
    protected int curVolleyIndex = 0;
    protected int thisVolleyCount = 0;
    protected float rangeSquare = 400f;
    protected float errorModifier;
    protected float bulletAccel = 0.1f;
    //volley
    protected int volleyCountMin = 1;
    protected int volleyCountMax = 1;
    protected int volleyInBetween = 5;//ticks

    public float yFactor = 0.5f;

    boolean smartEstimate = true;

    public int coolDownMin = 20;//ticks
    public int coolDownDelta = 20;//ticks

    ProjectileArgs customArgs = null;

    SoundEvent soundEvent = null;

    public EntityAITurretAttack(EntityLiving self, float errorModifier)
    {
        this.self = self;
        this.errorModifier = errorModifier;
        resetVolleyCount();
        this.setMutexBits(CommonDef.AIMutexFlags.LOOK);
    }

    public EntityAITurretAttack(EntityLiving self, float errorModifier, int coolDownMin, int coolDownDelta) {
        this(self, errorModifier);
        this.coolDownMin = coolDownMin;
        this.coolDownDelta = coolDownDelta;
    }

    public EntityAITurretAttack setVolley(int min, int max, int volleyInBetween) {
        this.volleyCountMin = min;
        this.volleyCountMax = max;
        this.volleyInBetween = volleyInBetween;
        resetVolleyCount();
        return this;
    }

    public EntityAITurretAttack setCustomArgs(ProjectileArgs customArgs) {
        this.customArgs = customArgs;
        return this;
    }

    public EntityAITurretAttack setSound(SoundEvent event) {
        soundEvent = event;
        return this;
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
    public void resetTask() {
        resetVolleyCount();
    }

    public int getVolleyCount(Random random) {
        if (volleyCountMax <= volleyCountMin) {
            return volleyCountMax;
        } else {
            return volleyCountMin + random.nextInt(volleyCountMax - volleyCountMin);
        }
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        if (self.getEntityWorld().getDifficulty() != EnumDifficulty.PEACEFUL)
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
                    commenceAttack(attackTarget);
                }
                else
                {
                    self.setAttackTarget(null);
                }
            }
            super.updateTask();
        }
    }

    private double getFrequencyFactor() {
        IAttributeInstance attribute = self.getEntityAttribute(ATTACK_SPEED);
        return ((attribute == null) || (attribute.getAttributeValue() == 0)) ? 1 : attribute.getBaseValue() / attribute.getAttributeValue();
    }

    void resetVolleyCount() {
        thisVolleyCount = getVolleyCount(this.self.getRNG());
        curVolleyIndex = 0;
    }

    private void commenceAttack(EntityLivingBase attackTarget) {
        if (this.curCoolDown <= 0) {
            curVolleyIndex++;

            onAttackTriggered(attackTarget);

            if (curVolleyIndex == thisVolleyCount) {
                //last shoot
                this.curCoolDown = (int) ((coolDownMin
                        + (coolDownDelta == 0 ? 0 : self.getRNG().nextInt(coolDownDelta)))
                        * getFrequencyFactor());
                resetVolleyCount();
            } else {
                this.curCoolDown = (int) (volleyInBetween * getFrequencyFactor());
            }
        }
    }

    public ProjectileArgs getCustomArgs() {
        return customArgs != null ? customArgs.setDamage((float) EntityUtil.getAttack(self)) : new ProjectileArgs((float) EntityUtil.getAttack(self));
    }

    public void onAttackTriggered(EntityLivingBase target)
    {
        double d0 = self.getDistanceSq(target);
        double d1 = target.posX - self.posX;
        double d2 = target.posY + (target.height / 2.0F) - (self.posY + (double) (self.height * yFactor));
        double d3 = target.posZ - self.posZ;

        float dist =  MathHelper.sqrt(MathHelper.sqrt(d0));
        float estimateHitTime = MathHelper.sqrt(2 * dist / bulletAccel);

        EntityIdlProjectile entityIdlProjectile = new EntityIdlProjectile(self.getEntityWorld(), getCustomArgs(), self,
                d1 + self.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionX,
                d2 + self.getRNG().nextGaussian() * (double) errorModifier + (target.onGround ? 0 : estimateHitTime * target.motionY),
                d3 + self.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionZ,
                bulletAccel);

        self.getEntityWorld().spawnEntity(entityIdlProjectile);

        if (soundEvent != null) {
//            self.getEntityWorld().playSound(null, self.getPosition(), soundEvent, SoundCategory.NEUTRAL, 1f, 1f);
            self.playSound(soundEvent, 1f, 1f);
        }

        self.swingArm(EnumHand.MAIN_HAND);
        self.swingArm(EnumHand.OFF_HAND);
    }
}
