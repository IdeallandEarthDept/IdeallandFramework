package com.somebody.idlframewok.entity.creatures.mobs.skyland;

import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.MessageDef;
import com.somebody.idlframewok.util.Reference;
import com.somebody.idlframewok.world.biome.GodBelieverSingle;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EntityCatharVex extends EntityModUnit {
    public EntityCatharVex(World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
        this.moveHelper = new AIMoveControl(this);
        this.setSize(0.4F, 0.8F);
        this.experienceValue = 3;

        this.limitedLifespan = true;
        this.limitedLifeTicks = TICK_PER_SECOND * 30;
        this.is_elemental = true;
    }

    protected static final DataParameter<Byte> VEX_FLAGS = EntityDataManager.<Byte>createKey(EntityCatharVex.class, DataSerializers.BYTE);
//    public static void registerFixesVex(DataFixer fixer)
//    {
//        EntityLiving.registerFixesMob(fixer, EntityVex.class);
//    }

    @Nullable
    private BlockPos boundOrigin;
    private boolean limitedLifespan;
    private int limitedLifeTicks;
    private EntityLivingBase owner;

    protected void firstTickAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityCatharVex.AIChargeAttack());
        this.tasks.addTask(8, new EntityCatharVex.AIMoveRandom());
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityCatharVex.class));
        this.targetTasks.addTask(2, new EntityCatharVex.AICopyOwnerTarget(this));
        //this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(VEX_FLAGS, (byte) 0);
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
        setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));

        float f = difficulty.getClampedAdditionalDifficulty();
        for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
        {
            if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR)
            {
                ItemStack itemstack = this.getItemStackFromSlot(entityequipmentslot);

                if (!itemstack.isEmpty() && this.rand.nextFloat() < 0.5F * f)
                {
                    this.setItemStackToSlot(entityequipmentslot, EnchantmentHelper.addRandomEnchantment(this.rand, itemstack, (int)(5.0F + f * (float)this.rand.nextInt(18)), false));
                }
            }
        }
        return livingdata;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        setAttr(32, 0.7, 7, 0, 16);
    }

    //MOVING-------------------
    /* Tries to move the entity towards the specified location.
     */
    public void move(MoverType type, double x, double y, double z)
    {
        super.move(type, x, y, z);
        this.doBlockCollisions();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.noClip = true;
        super.onUpdate();
        this.noClip = false;
        this.setNoGravity(true);

        if (this.limitedLifespan && --this.limitedLifeTicks <= 0)
        {
            this.limitedLifeTicks = 20;
            this.attackEntityFrom(DamageSource.STARVE, 1.0F);
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {
        World world = event.getEntity().world;
        if (world.isRemote)
        {
            return;
        }

        if (event.getSource().getTrueSource() instanceof EntityLivingBase)
        {
            EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
            EntityLivingBase hurtOne = event.getEntityLiving();

            if (world.getBiome(hurtOne.getPosition()) instanceof GodBelieverSingle)
            {
                int godIndex = ((GodBelieverSingle) world.getBiome(hurtOne.getPosition())).getGodIndex();
                int hurtOneBelief = Init16Gods.getBelief(hurtOne, godIndex);
                if (hurtOneBelief > 0 )
                {
                    int attackerBelief = Init16Gods.getBelief(attacker, godIndex);
                    if (attackerBelief <= 0)
                    {
                        boolean needNotify = attacker instanceof EntityPlayer;
                        boolean needNotifyHurt = hurtOne instanceof EntityPlayer;
                        List<EntityCatharVex> nearbyVex = EntityUtil.getEntitiesWithinAABB(world, EntityCatharVex.class, hurtOne.getPositionVector(), 32f, null);
                        for (EntityCatharVex vex:
                             nearbyVex) {
                             if (vex.getAttackTarget() == null)
                             {
                                  vex.setOwner(hurtOne);
                                  vex.setAttackTarget(attacker);
                                  if (needNotify)
                                  {
                                      CommonFunctions.SafeSendMsgToPlayer(attacker, MessageDef.VEX_ATTACK_YOU);
                                      needNotify = false;
                                  }
                                 if (needNotifyHurt)
                                 {
                                     CommonFunctions.SafeSendMsgToPlayer(attacker, MessageDef.VEX_DEFEND_YOU);
                                     needNotify = false;
                                 }
                             }
                        }
                    }
                }
            }

        }
    }

    //-------------------
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("BoundX"))
        {
            this.boundOrigin = new BlockPos(compound.getInteger("BoundX"), compound.getInteger("BoundY"), compound.getInteger("BoundZ"));
        }

        if (compound.hasKey("LifeTicks"))
        {
            this.setLimitedLife(compound.getInteger("LifeTicks"));
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        if (this.boundOrigin != null)
        {
            compound.setInteger("BoundX", this.boundOrigin.getX());
            compound.setInteger("BoundY", this.boundOrigin.getY());
            compound.setInteger("BoundZ", this.boundOrigin.getZ());
        }

        if (this.limitedLifespan)
        {
            compound.setInteger("LifeTicks", this.limitedLifeTicks);
        }
    }

    public EntityLivingBase getOwner()
    {
        return this.owner;
    }

    @Nullable
    public BlockPos getBoundOrigin()
    {
        return this.boundOrigin;
    }

    public void setBoundOrigin(@Nullable BlockPos boundOriginIn)
    {
        this.boundOrigin = boundOriginIn;
    }

    private boolean getVexFlag(int mask)
    {
        int i = this.dataManager.get(VEX_FLAGS);
        return (i & mask) != 0;
    }

    private void setVexFlag(int mask, boolean value)
    {
        int i = this.dataManager.get(VEX_FLAGS);

        if (value)
        {
            i = i | mask;
        }
        else
        {
            i = i & ~mask;
        }

        this.dataManager.set(VEX_FLAGS, Byte.valueOf((byte)(i & 255)));
    }

    public boolean isCharging()
    {
        return this.getVexFlag(1);
    }

    public void setCharging(boolean charging)
    {
        this.setVexFlag(1, charging);
    }

    public void setOwner(EntityLivingBase ownerIn)
    {
        this.owner = ownerIn;
    }

    public void setLimitedLife(int limitedLifeTicksIn)
    {
        this.limitedLifespan = true;
        this.limitedLifeTicks = limitedLifeTicksIn;
    }

    public void setUnlimitedLife()
    {
        this.limitedLifespan = false;
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_VEX_AMBIENT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_VEX_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_VEX_HURT;
    }

//    @Nullable
//    protected ResourceLocation getLootTable()
//    {
//        return LootTableList.ENTITIES_VEX;
//    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return 1.0F;
    }


    //--------------------------------------------------
    //AI: VEX
    class AIChargeAttack extends EntityAIBase
    {
        public AIChargeAttack()
        {
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            if (EntityCatharVex.this.getAttackTarget() != null && !EntityCatharVex.this.getMoveHelper().isUpdating() && EntityCatharVex.this.rand.nextInt(7) == 0)
            {
                return EntityCatharVex.this.getDistanceSq(EntityCatharVex.this.getAttackTarget()) > 4.0D;
            }
            else
            {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return EntityCatharVex.this.getMoveHelper().isUpdating() && EntityCatharVex.this.isCharging() && EntityCatharVex.this.getAttackTarget() != null && EntityCatharVex.this.getAttackTarget().isEntityAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            EntityLivingBase entitylivingbase = EntityCatharVex.this.getAttackTarget();
            Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
            EntityCatharVex.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
            EntityCatharVex.this.setCharging(true);
            EntityCatharVex.this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0F, 1.0F);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            EntityCatharVex.this.setCharging(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            EntityLivingBase entitylivingbase = EntityCatharVex.this.getAttackTarget();

            if (EntityCatharVex.this.getEntityBoundingBox().intersects(entitylivingbase.getEntityBoundingBox()))
            {
                EntityCatharVex.this.attackEntityAsMob(entitylivingbase);
                EntityCatharVex.this.setCharging(false);
            }
            else
            {
                double d0 = EntityCatharVex.this.getDistanceSq(entitylivingbase);

                if (d0 < 9.0D)
                {
                    Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
                    EntityCatharVex.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
                }
            }
        }
    }

    class AICopyOwnerTarget extends EntityAITarget
    {
        public AICopyOwnerTarget(EntityCreature creature)
        {
            super(creature, false);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return EntityCatharVex.this.owner instanceof EntityLiving && ((EntityLiving) EntityCatharVex.this.owner).getAttackTarget() != null && this.isSuitableTarget(((EntityLiving) EntityCatharVex.this.owner).getAttackTarget(), false);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            if (EntityCatharVex.this.owner instanceof EntityLiving)
            {
                EntityCatharVex.this.setAttackTarget(((EntityLiving) EntityCatharVex.this.owner).getAttackTarget());
            }
            super.startExecuting();
        }
    }

    class AIMoveControl extends EntityMoveHelper
    {
        public AIMoveControl(EntityModUnit vex)
        {
            super(vex);
        }

        public void onUpdateMoveHelper()
        {
            if (this.action == EntityMoveHelper.Action.MOVE_TO)
            {
                double d0 = this.posX - EntityCatharVex.this.posX;
                double d1 = this.posY - EntityCatharVex.this.posY;
                double d2 = this.posZ - EntityCatharVex.this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = (double) MathHelper.sqrt(d3);

                if (d3 < EntityCatharVex.this.getEntityBoundingBox().getAverageEdgeLength())
                {
                    this.action = EntityMoveHelper.Action.WAIT;
                    EntityCatharVex.this.motionX *= 0.5D;
                    EntityCatharVex.this.motionY *= 0.5D;
                    EntityCatharVex.this.motionZ *= 0.5D;
                }
                else
                {
                    EntityCatharVex.this.motionX += d0 / d3 * 0.05D * this.speed;
                    EntityCatharVex.this.motionY += d1 / d3 * 0.05D * this.speed;
                    EntityCatharVex.this.motionZ += d2 / d3 * 0.05D * this.speed;

                    if (EntityCatharVex.this.getAttackTarget() == null)
                    {
                        EntityCatharVex.this.rotationYaw = -((float)MathHelper.atan2(EntityCatharVex.this.motionX, EntityCatharVex.this.motionZ)) * (180F / (float)Math.PI);
                        EntityCatharVex.this.renderYawOffset = EntityCatharVex.this.rotationYaw;
                    }
                    else
                    {
                        double d4 = EntityCatharVex.this.getAttackTarget().posX - EntityCatharVex.this.posX;
                        double d5 = EntityCatharVex.this.getAttackTarget().posZ - EntityCatharVex.this.posZ;
                        EntityCatharVex.this.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * (180F / (float)Math.PI);
                        EntityCatharVex.this.renderYawOffset = EntityCatharVex.this.rotationYaw;
                    }
                }
            }
        }
    }

    class AIMoveRandom extends EntityAIBase
    {
        public AIMoveRandom()
        {
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return !EntityCatharVex.this.getMoveHelper().isUpdating() && EntityCatharVex.this.rand.nextInt(7) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            BlockPos blockpos = EntityCatharVex.this.getBoundOrigin();

            if (blockpos == null)
            {
                blockpos = new BlockPos(EntityCatharVex.this);
            }

            for (int i = 0; i < 3; ++i)
            {
                BlockPos blockpos1 = blockpos.add(EntityCatharVex.this.rand.nextInt(15) - 7, EntityCatharVex.this.rand.nextInt(11) - 5, EntityCatharVex.this.rand.nextInt(15) - 7);

                if (EntityCatharVex.this.world.isAirBlock(blockpos1))
                {
                    EntityCatharVex.this.moveHelper.setMoveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);

                    if (EntityCatharVex.this.getAttackTarget() == null)
                    {
                        EntityCatharVex.this.getLookHelper().setLookPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }

                    break;
                }
            }
        }
    }
}
