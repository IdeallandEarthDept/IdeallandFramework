package com.somebody.idlframewok.entity.creatures.ideallandTeam;

import com.somebody.idlframewok.entity.creatures.ai.EntityAIStrafeRangedAttack;
import com.somebody.idlframewok.entity.creatures.mobs.EntityIdentityThief;
import com.somebody.idlframewok.entity.creatures.mobs.IPlayerDoppleganger;
import com.somebody.idlframewok.entity.projectiles.EntityMoroonBullet;
import com.somebody.idlframewok.entity.projectiles.ProjectileArgs;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

import static com.somebody.idlframewok.util.CommonDef.UUID_DEFAULT;

public class EntityPlayerClone extends EntityIdeallandUnitBase implements IRangedAttackMob, IPlayerDoppleganger {
    private float bulletAccel = 0.1f;
    private float errorModifier = 0.2f;
    public EntityPlayer player;
    protected static final DataParameter<String> PLAYER_UUID = EntityDataManager.<String>createKey(EntityIdentityThief.class, DataSerializers.STRING);

    public EntityPlayerClone(World worldIn) {
        super(worldIn);
        this.setCombatTask();
        experienceValue = 0;
    }

    public void imitatePlayer(EntityPlayer player)
    {
        imitateLiving(player);
        this.player = player;
        this.dataManager.set(PLAYER_UUID, player.getName());
    }

    public UUID getPlayerUUID()
    {
        try {
            return UUID.fromString(this.dataManager.get(PLAYER_UUID));
        }
        catch (Exception e)
        {
            return UUID_DEFAULT;
        }
    }

    protected void entityInit()
    {
        this.dataManager.register(PLAYER_UUID, UUID_DEFAULT.toString());
        super.entityInit();
    }

    @Override
    public EntityPlayer getPlayer() {
        return player;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (world.isRemote && player == null)
        {
            player = world.getPlayerEntityByUUID(UUID.fromString(this.dataManager.get(PLAYER_UUID)));
        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16.0D, 0.33000000417232513D, 1.0D, 0, 20.0D);
    }
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setCombatTask();
    }

    protected final EntityAIStrafeRangedAttack<EntityPlayerClone> aiArrowAttack = new EntityAIStrafeRangedAttack<>(this, 1.0D, 5, 16.0F);
    protected final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false)
    {
        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            super.resetTask();
            EntityPlayerClone.this.setSwingingArms(false);
        }
        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            super.startExecuting();
            EntityPlayerClone.this.setSwingingArms(true);
        }
    };

    protected void firstTickAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));

        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTNTPrimed.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 0.5D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        applyGeneralAI();
    }

    protected void applyGeneralAI()
    {
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, false, true,
                (Predicate<EntityLiving>) p_apply_1_ -> p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper)));

        ((PathNavigateGround)this.getNavigator()).setEnterDoors(true);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.setCombatTask();
        return super.onInitialSpawn(difficulty, livingdata);
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        if (this.getRNG().nextFloat() < 0.5f)
        {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
        }else {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.MOROON_RIFLE));
        }

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

        EntityMoroonBullet entityIdlProjectile = new EntityMoroonBullet(this.world, new ProjectileArgs((float) EntityUtil.getAttack(this)), this,
                d1 + this.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionX,
                d2 + this.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionY,
                d3 + this.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionZ,
                bulletAccel);

        playSound(SoundEvents.BLOCK_PISTON_EXTEND, 1f, 2f);
        world.spawnEntity(entityIdlProjectile);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        this.dataManager.set(SWINGING_ARMS, swingingArms);
    }
}
