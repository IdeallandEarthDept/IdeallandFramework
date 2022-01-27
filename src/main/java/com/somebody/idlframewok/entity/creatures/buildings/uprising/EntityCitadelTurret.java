package com.somebody.idlframewok.entity.creatures.buildings.uprising;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.entity.creatures.ICustomFaction;
import com.somebody.idlframewok.entity.creatures.ai.EntityAITurretAttack;
import com.somebody.idlframewok.entity.projectiles.ProjectileArgs;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

public class EntityCitadelTurret extends EntityModUnit implements ICustomFaction {

    protected static final DataParameter<Byte> WARM_UP_COUNTER = EntityDataManager.<Byte>createKey(EntityCitadelTurret.class, DataSerializers.BYTE);

    final byte MAX_WARM_UP = 100;//if you write this into Byte, register will silently fail and produce crash later.

    int warm_up_per_tick = 10;

    boolean isAI_On = false;

    int onAttackBroadCastCoolDown = 0;
    int onAttackBroadCastCoolDownMax = 100;

    public EntityCitadelTurret(World worldIn) {
        super(worldIn);
        setBuilding();
        setSize(4.5f, 7.5f);
        attack_all_players = 0;
        avengeful = true;
        forceSyncLimbSwingToClient = true;
        CommonFunctions.addToEventBus(this);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(WARM_UP_COUNTER, (byte) 0);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(32.0D, 0D, 5.0D, 20.0D, 200.0D);
    }

    @Override
    public void onFirstTickAfterConstruct() {
        //dont init AI at once
    }

    public void initiateWarmupProcess() {
        setHealth(1f);
        isAI_On = false;
        this.targetTasks.taskEntries.clear();
        this.tasks.taskEntries.clear();
        dataManager.set(WARM_UP_COUNTER, MAX_WARM_UP);
    }

    @Override
    protected void firstTickAI() {
        super.firstTickAI();
        applyAttackingAI();
//        applyTargetingAI();
    }

    protected void applyAttackingAI() {
        this.tasks.addTask(1, new EntityAITurretAttack(this, 0.001f)
                .setVolley(2, 7, 1)
                .setCustomArgs(new ProjectileArgs(0f).setBypassArmor(true))
                .setSound(SoundEvents.ENTITY_FIREWORK_SHOOT));
    }

//    protected void applyTargetingAI() {
//        this.targetTasks.addTask(2, new ModAIAttackNearest<>(this, EntityLivingBase.class,
//                10, true, true, EntityUtil.HostileToIdl
//        ));
//    }

    @Override
    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
        if (EntityUtil.getFaction(entitylivingbaseIn) == getFaction()) {
            return;
        }
        super.setAttackTarget(entitylivingbaseIn);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        //rotationYaw = 0f;

        int counter = this.dataManager.get(WARM_UP_COUNTER);
        if (counter > 0 && CommonFunctions.isSecondTick(getEntityWorld())) {
            heal((float) warm_up_per_tick / MAX_WARM_UP);
            this.dataManager.set(WARM_UP_COUNTER, (byte) (counter - warm_up_per_tick));
            if (counter <= warm_up_per_tick) {
                onReady();
            }
        }

        if (!isAI_On && counter <= 0) {
            firstTickAI();
            isAI_On = true;
        }

        if (onAttackBroadCastCoolDown > 0) {
            onAttackBroadCastCoolDown--;
        }
    }

//    @Override
//    public void travel(float strafe, float vertical, float forward) {
//        if (this.isServerWorld() || this.canPassengerSteer()) {
//            if (!this.isInWater()) {
//                if (!this.isInLava()) {
//                    if (this.isElytraFlying()) {
//                        if (this.motionY > -0.5D) {
//                            this.fallDistance = 1.0F;
//                        }
//
//                        Vec3d vec3d = this.getLookVec();
//                        float f = this.rotationPitch * 0.017453292F;
//                        double d6 = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
//                        double d8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
//                        double d1 = vec3d.lengthVector();
//                        float f4 = MathHelper.cos(f);
//                        f4 = (float) ((double) f4 * (double) f4 * Math.min(1.0D, d1 / 0.4D));
//                        this.motionY += -0.08D + (double) f4 * 0.06D;
//
//                        if (this.motionY < 0.0D && d6 > 0.0D) {
//                            double d2 = this.motionY * -0.1D * (double) f4;
//                            this.motionY += d2;
//                            this.motionX += vec3d.x * d2 / d6;
//                            this.motionZ += vec3d.z * d2 / d6;
//                        }
//
//                        if (f < 0.0F) {
//                            double d10 = d8 * (double) (-MathHelper.sin(f)) * 0.04D;
//                            this.motionY += d10 * 3.2D;
//                            this.motionX -= vec3d.x * d10 / d6;
//                            this.motionZ -= vec3d.z * d10 / d6;
//                        }
//
//                        if (d6 > 0.0D) {
//                            this.motionX += (vec3d.x / d6 * d8 - this.motionX) * 0.1D;
//                            this.motionZ += (vec3d.z / d6 * d8 - this.motionZ) * 0.1D;
//                        }
//
//                        this.motionX *= 0.9900000095367432D;
//                        this.motionY *= 0.9800000190734863D;
//                        this.motionZ *= 0.9900000095367432D;
//                        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
//
//                        if (this.collidedHorizontally && !this.world.isRemote) {
//                            double d11 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
//                            double d3 = d8 - d11;
//                            float f5 = (float) (d3 * 10.0D - 3.0D);
//
//                            if (f5 > 0.0F) {
//                                this.playSound(this.getFallSound((int) f5), 1.0F, 1.0F);
//                                this.attackEntityFrom(DamageSource.FLY_INTO_WALL, f5);
//                            }
//                        }
//
//                        if (this.onGround && !this.world.isRemote) {
//                            this.setFlag(7, false);
//                        }
//                    } else {
//                        float f6 = 0.91F;
//                        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(this.posX, this.getEntityBoundingBox().minY - 1.0D, this.posZ);
//
//                        if (this.onGround) {
//                            IBlockState underState = this.world.getBlockState(blockpos$pooledmutableblockpos);
//                            f6 = underState.getBlock().getSlipperiness(underState, this.world, blockpos$pooledmutableblockpos, this) * 0.91F;
//                        }
//
//                        float f7 = 0.16277136F / (f6 * f6 * f6);
//                        float f8;
//
//                        if (this.onGround) {
//                            f8 = this.getAIMoveSpeed() * f7;
//                        } else {
//                            f8 = this.jumpMovementFactor;
//                        }
//
//                        this.moveRelative(strafe, vertical, forward, f8);
//                        f6 = 0.91F;
//
//                        if (this.onGround) {
//                            IBlockState underState = this.world.getBlockState(blockpos$pooledmutableblockpos.setPos(this.posX, this.getEntityBoundingBox().minY - 1.0D, this.posZ));
//                            f6 = underState.getBlock().getSlipperiness(underState, this.world, blockpos$pooledmutableblockpos, this) * 0.91F;
//                        }
//
//                        if (this.isOnLadder()) {
//                            float f9 = 0.15F;
//                            this.motionX = MathHelper.clamp(this.motionX, -0.15000000596046448D, 0.15000000596046448D);
//                            this.motionZ = MathHelper.clamp(this.motionZ, -0.15000000596046448D, 0.15000000596046448D);
//                            this.fallDistance = 0.0F;
//
//                            if (this.motionY < -0.15D) {
//                                this.motionY = -0.15D;
//                            }
//
//                            this.isSneaking();
//                        }
//
//                        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
//
//                        if (this.collidedHorizontally && this.isOnLadder()) {
//                            this.motionY = 0.2D;
//                        }
//
//                        if (this.isPotionActive(MobEffects.LEVITATION)) {
//                            this.motionY += (0.05D * (double) (this.getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motionY) * 0.2D;
//                        } else {
//                            blockpos$pooledmutableblockpos.setPos(this.posX, 0.0D, this.posZ);
//
//                            if (!this.world.isRemote || this.world.isBlockLoaded(blockpos$pooledmutableblockpos) && this.world.getChunkFromBlockCoords(blockpos$pooledmutableblockpos).isLoaded()) {
//                                if (!this.hasNoGravity()) {
//                                    this.motionY -= 0.08D;
//                                }
//                            } else if (this.posY > 0.0D) {
//                                this.motionY = -0.1D;
//                            } else {
//                                this.motionY = 0.0D;
//                            }
//                        }
//
//                        this.motionY *= 0.9800000190734863D;
//                        this.motionX *= (double) f6;
//                        this.motionZ *= (double) f6;
//                        blockpos$pooledmutableblockpos.release();
//                    }
//                } else {
//                    double d4 = this.posY;
//                    this.moveRelative(strafe, vertical, forward, 0.02F);
//                    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
//                    this.motionX *= 0.5D;
//                    this.motionY *= 0.5D;
//                    this.motionZ *= 0.5D;
//
//                    if (!this.hasNoGravity()) {
//                        this.motionY -= 0.02D;
//                    }
//
//                    if (this.collidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d4, this.motionZ)) {
//                        this.motionY = 0.30000001192092896D;
//                    }
//                }
//            } else {
//                double d0 = this.posY;
//                float f1 = this.getWaterSlowDown();
//                float f2 = 0.02F;
//                float f3 = (float) EnchantmentHelper.getDepthStriderModifier(this);
//
//                if (f3 > 3.0F) {
//                    f3 = 3.0F;
//                }
//
//                if (!this.onGround) {
//                    f3 *= 0.5F;
//                }
//
//                if (f3 > 0.0F) {
//                    f1 += (0.54600006F - f1) * f3 / 3.0F;
//                    f2 += (this.getAIMoveSpeed() - f2) * f3 / 3.0F;
//                }
//
//                this.moveRelative(strafe, vertical, forward, f2);
//                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
//                this.motionX *= (double) f1;
//                this.motionY *= 0.800000011920929D;
//                this.motionZ *= (double) f1;
//
//                if (!this.hasNoGravity()) {
//                    this.motionY -= 0.02D;
//                }
//
//                if (this.collidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d0, this.motionZ)) {
//                    this.motionY = 0.30000001192092896D;
//                }
//            }
//        }
//
//        this.prevLimbSwingAmount = this.limbSwingAmount;
//        this.limbSwingAmount += -this.limbSwingAmount * 0.4F;
//        this.limbSwing += this.limbSwingAmount;
//    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (dataManager.get(WARM_UP_COUNTER) > 0) {
            for (int i = 0; i < 10; i++) {
                float radius = 3f;
                float deltaOmega = ModConfig.DEBUG_CONF.HALO_OMEGA * i;
                float angle = ticksExisted * 5f;

                Vec3d pos = new Vec3d(posX + radius * Math.sin(angle + deltaOmega),
                        posY + height * (10 - i) / 10f,
                        posZ + radius * Math.cos(angle + deltaOmega));

                getEntityWorld().spawnParticle(EnumParticleTypes.REDSTONE, pos.x, pos.y, pos.z,
                        0, -0.1, 0);//this changes color, not speed
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (world.isRemote) {
            dataManager.set(WARM_UP_COUNTER, compound.getByte(IDLNBTDef.KEY_WARM_UP));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setByte(IDLNBTDef.KEY_WARM_UP, dataManager.get(WARM_UP_COUNTER));
        return super.writeToNBT(compound);
    }

    public static final String MSG_CITADEL_READY = "uprising.msg.citadel.ready";
    public static final String MSG_CITADEL_UNDER_ATTACK = "uprising.msg.citadel.under_attack";
    public static final String MSG_CITADEL_DESTROYED = "uprising.msg.citadel.destroyed";
    public static final String MSG_CITADEL_DESTROYED_WITH_ATTACK = "uprising.msg.citadel.destroyed.attacker";

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onAttack(LivingAttackEvent event) {
        if (event.getEntity() == this && !event.isCanceled() && EntityUtil.getAttitude(EntityUtil.EnumFaction.PLAYER, this) == EntityUtil.EnumAttitude.FRIEND) {
            if (!getEntityWorld().isRemote) {
                if (onAttackBroadCastCoolDown > 0) {
                    return;
                }
                CommonFunctions.broadCastByKey(TextFormatting.YELLOW, MSG_CITADEL_UNDER_ATTACK, posX, posY, posZ);
                onAttackBroadCastCoolDown = onAttackBroadCastCoolDownMax;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onDestroy(LivingDeathEvent event) {
        //consider doing this like CombatTracker.getDeathMessage
        if (event.getEntity() == this && !event.isCanceled() && !getEntityWorld().isRemote) {

            TextFormatting color = null;
            if (EntityUtil.getAttitude(EntityUtil.EnumFaction.PLAYER, this) == EntityUtil.EnumAttitude.FRIEND) {
                color = TextFormatting.RED;

            } else if (EntityUtil.getAttitude(EntityUtil.EnumFaction.PLAYER, this) == EntityUtil.EnumAttitude.HATE) {
                color = TextFormatting.GREEN;
            } else if (event.getSource().getTrueSource() instanceof EntityPlayer) {
                color = TextFormatting.YELLOW;
            }

            if (color != null) {
                Entity attacker = event.getSource().getTrueSource();
                if (attacker != null) {
                    CommonFunctions.broadCastByKey(color, MSG_CITADEL_DESTROYED_WITH_ATTACK, attacker.getDisplayName(), posX, posY, posZ);
                } else {
                    CommonFunctions.broadCastByKey(color, MSG_CITADEL_DESTROYED, posX, posY, posZ);
                }
            }
        }
    }

    public void onReady() {
        if (EntityUtil.getAttitude(EntityUtil.EnumFaction.PLAYER, this) == EntityUtil.EnumAttitude.FRIEND) {
            if (!getEntityWorld().isRemote) {
                CommonFunctions.broadCastByKey(TextFormatting.GREEN, MSG_CITADEL_READY, posX, posY, posZ);
            }
        }
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!player.getEntityWorld().isRemote && player.isCreative()) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() == ModItems.MOR_FRAG) {
                setFaction(EntityUtil.EnumFaction.MOROON);
            } else if (stack.getItem() == Items.ROTTEN_FLESH) {
                setFaction(EntityUtil.EnumFaction.MOB_VAN_ZOMBIE);
            } else if (stack.getItem() == Items.GOLDEN_APPLE) {
                setFaction(EntityUtil.EnumFaction.PLAYER);
            } else if (stack.getItem() == ModItems.ITEM_IDL_ORDER_1) {
                setFaction(EntityUtil.EnumFaction.IDEALLAND);
            }

            PlayerUtil.setCoolDown(player, hand);
        }
        return super.processInteract(player, hand);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GENERIC_EXPLODE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_ANVIL_STEP;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        dropItem(Items.IRON_INGOT, 2 + lootingModifier);
    }


    //public void on
}
