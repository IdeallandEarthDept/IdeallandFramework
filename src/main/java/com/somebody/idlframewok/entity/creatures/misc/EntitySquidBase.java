package com.somebody.idlframewok.entity.creatures.misc;

import com.somebody.idlframewok.entity.creatures.ai.EntityAISquidMove;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

//not instance of EntityWaterMob
public class EntitySquidBase extends EntiyModUnitWater {

    public float squidPitch;
    public float prevSquidPitch;
    public float squidYaw;
    public float prevSquidYaw;
    /**
     * appears to be rotationIn in radians; we already have pitch & yaw, so this completes the triumvirate.
     */
    public float squidRotation;
    /** previous squidRotation in radians */
    public float prevSquidRotation;
    /** angle of the tentacles in radians */
    public float tentacleAngle;
    /** the last calculated angle of the tentacles in radians */
    public float lastTentacleAngle;
    private float randomMotionSpeed;
    /** change in squidRotation in radians. */
    private float rotationVelocity;
    private float rotateSpeed;
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;

    public EntitySquidBase(World worldIn) {
        super(worldIn);
        this.rand.setSeed((long)(1 + this.getEntityId()));
        this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;

        experienceValue = 20;
        applyLevelBoost = true;

        spawn_without_darkness = true;
        spawn_without_moroon_ground = true;
    }


    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        boolean faster = getRNG().nextFloat() < 0.3f;
        boolean stronger = getRNG().nextFloat() < 0.3f;
        setAttr(16,
                faster ? 0.3f : 0.1f,
                stronger ? 5f : 3f,//attack
                stronger ? 2f : 0f, //armor
                stronger ? 50 : 35);//hp
    }

    protected void firstTickAI()
    {
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(0, new EntityAISquidMove(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        applyGeneralAI();
    }

    protected void applyGeneralAI()
    {
        ((PathNavigateGround)this.getNavigator()).setEnterDoors(false);
    }

    void handleAttackingMotion()
    {
        if (this.getAttackTarget() != null)
        {
            Vec3d pos = this.getAttackTarget().getPositionVector();

            Vec3d dir = pos.subtract(getPositionVector()).normalize();
            randomMotionVecX = (float) dir.x;
            randomMotionVecY = (float) dir.y;
            randomMotionVecZ = (float) dir.z;
        }
    }

    public float getInkChance(EntityLivingBase target)
    {
        return getLevel() * 0.1f;
    }

    public float getInkTime(EntityLivingBase target)
    {
        return getLevel() * 3f;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        if (world.isRemote)
        {
            return super.attackEntityFrom(source, amount);
        }

        if (source.getTrueSource() instanceof EntityLivingBase)
        {
            EntityLivingBase trueSource = (EntityLivingBase) source.getTrueSource();
            if (getRNG().nextFloat() < getInkChance(trueSource))
            {
                EntityUtil.ApplyBuff(trueSource, MobEffects.BLINDNESS, 0, getInkTime(trueSource));
            }
        }

        return super.attackEntityFrom(source, amount);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        this.lastTentacleAngle = this.tentacleAngle;
        this.squidRotation += this.rotationVelocity;

        if ((double)this.squidRotation > (Math.PI * 2D))
        {
            if (this.world.isRemote)
            {
                this.squidRotation = ((float)Math.PI * 2F);
            }
            else
            {
                this.squidRotation = (float)((double)this.squidRotation - (Math.PI * 2D));

                if (this.rand.nextInt(10) == 0)
                {
                    this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
                }

                this.world.setEntityState(this, (byte)19);
            }
        }

        if (this.inWater)
        {
            if (this.squidRotation < (float)Math.PI)
            {
                float f = this.squidRotation / (float)Math.PI;
                this.tentacleAngle = MathHelper.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;

                if ((double)f > 0.75D)
                {
                    this.randomMotionSpeed = 1.0F;
                    this.rotateSpeed = 1.0F;
                }
                else
                {
                    this.rotateSpeed *= 0.8F;
                }
            }
            else
            {
                this.tentacleAngle = 0.0F;
                this.randomMotionSpeed *= 0.9F;
                this.rotateSpeed *= 0.99F;
            }

            if (!this.world.isRemote)
            {
                handleAttackingMotion();
                this.motionX = (double)(this.randomMotionVecX * this.randomMotionSpeed);
                this.motionY = (double)(this.randomMotionVecY * this.randomMotionSpeed);
                this.motionZ = (double)(this.randomMotionVecZ * this.randomMotionSpeed);
            }

            float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset += (-((float)MathHelper.atan2(this.motionX, this.motionZ)) * (180F / (float)Math.PI) - this.renderYawOffset) * 0.1F;
            this.rotationYaw = this.renderYawOffset;
            this.squidYaw = (float)((double)this.squidYaw + Math.PI * (double)this.rotateSpeed * 1.5D);
            this.squidPitch += (-((float)MathHelper.atan2((double)f1, this.motionY)) * (180F / (float)Math.PI) - this.squidPitch) * 0.1F;
        }
        else
        {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * (float)Math.PI * 0.25F;

            if (!this.world.isRemote)
            {
                this.motionX = 0.0D;
                this.motionZ = 0.0D;

                if (this.isPotionActive(MobEffects.LEVITATION))
                {
                    this.motionY += 0.05D * (double)(this.getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motionY;
                }
                else if (!this.hasNoGravity())
                {
                    this.motionY -= 0.08D;
                }

                this.motionY *= 0.9800000190734863D;
            }

            this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0F - this.squidPitch) * 0.02D);
        }
    }

    public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn)
    {
        this.randomMotionVecX = randomMotionVecXIn;
        this.randomMotionVecY = randomMotionVecYIn;
        this.randomMotionVecZ = randomMotionVecZIn;
    }

    public boolean hasMovementVector()
    {
        return this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F;
    }



}
