package com.somebody.idlframewok.entity.creatures.moroon;

import com.somebody.idlframewok.entity.creatures.ai.EntityAIStrafeRangedAttack;
import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityMoroonStandardInfantry extends EntityMoroonUnitBase implements IRangedAttackMob {
    private float bulletAccel = 0.1f;
    private float errorModifier = 0.2f;

    public EntityMoroonStandardInfantry(World worldIn) {
        super(worldIn);
        this.setCombatTask();
        autoArmor = true;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);

        if (wasRecentlyHit) {
            if (getRNG().nextFloat() < 0.1f * (1 + lootingModifier))
            {
                //dropItem (ModItems.MOROON_RIFLE, 1);
            }
        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(32.0D, 0.33000000417232513D, 8.0D, 5.0D, 20.0D);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setCombatTask();
    }

    protected final EntityAIStrafeRangedAttack<EntityMoroonStandardInfantry> aiArrowAttack = new EntityAIStrafeRangedAttack<>(this, 1.0D, 5, 16.0F);
    protected final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false)
    {
        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            super.resetTask();
            EntityMoroonStandardInfantry.this.setSwingingArms(false);
        }
        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            super.startExecuting();
            EntityMoroonStandardInfantry.this.setSwingingArms(true);
        }
    };

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
       // this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        //this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTNTPrimed.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 0.5D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityMoroonTainter.class}));
        applyGeneralAI();
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        this.setCombatTask();
        return super.onInitialSpawn(difficulty, livingdata);
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        //this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.MOROON_RIFLE));
    }

    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack)
    {
        super.setItemStackToSlot(slotIn, stack);

        if (!this.world.isRemote && slotIn == EntityEquipmentSlot.MAINHAND)
        {
            this.setCombatTask();
        }
    }

    /**
     * sets this entity's combat AI.
     */
    public void setCombatTask()
    {
        if (this.world != null && !this.world.isRemote)
        {
            this.tasks.removeTask(this.aiAttackOnCollide);
            this.tasks.removeTask(this.aiArrowAttack);
            ItemStack itemstack = this.getHeldItemMainhand();

            if (CommonFunctions.isItemRangedWeapon(itemstack))
            {
                int i = 20;

                if (this.world.getDifficulty() != EnumDifficulty.HARD)
                {
                    i = 40;
                }

                this.aiArrowAttack.setAttackCooldown(i);
                this.tasks.addTask(4, this.aiArrowAttack);
            }
            else
            {
                this.tasks.addTask(4, this.aiAttackOnCollide);
            }
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        double d0 = this.getDistanceSq(target);
        double d1 = target.posX - this.posX;
        double d2 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
        double d3 = target.posZ - this.posZ;

        float dist =  MathHelper.sqrt(MathHelper.sqrt(d0));
        float estimateHitTime = MathHelper.sqrt(2 * bulletAccel * dist);

//        EntityMoroonBullet entityIdlProjectile = new EntityMoroonBullet(this.world, new ProjectileArgs((float) EntityUtil.getAttack(this)), this,
//                d1 + this.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionX,
//                d2 + this.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionY,
//                d3 + this.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionZ,
//                bulletAccel);
//
//        playSound(SoundEvents.BLOCK_PISTON_EXTEND, 1f, 2f);
//        world.spawnEntity(entityIdlProjectile);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        this.dataManager.set(SWINGING_ARMS, swingingArms);
    }
}
