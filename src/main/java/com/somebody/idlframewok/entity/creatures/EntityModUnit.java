package com.somebody.idlframewok.entity.creatures;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.blockMoroon.BlockMoroonBase;
import com.somebody.idlframewok.entity.creatures.ai.EntityAIMechInterfere;
import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityIdeallandUnitBase;
import com.somebody.idlframewok.entity.creatures.moroon.EntityMoroonBombBeacon;
import com.somebody.idlframewok.entity.creatures.moroon.EntityMoroonUnitBase;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.PlayerUtil;
import com.google.common.base.Predicate;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.EntityUtil.EnumFaction.CRITTER;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.KEY_FACTION;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EntityModUnit extends EntityCreature {

    private int level = 1;

    public boolean is_pinned_on_ground = false;
    public boolean is_mechanic = false;
    public boolean is_elemental = false;
    public boolean is_god = false;
    public boolean is_building = false;
    public boolean is_cubix = false;
    public boolean hold_ground = true;

    //ai
    protected boolean wander = true;
    protected boolean melee_atk = true;
    protected boolean can_swim = true;
    protected boolean avoid_danger = true;
    protected boolean across_village = true;
    protected boolean go_home = false;
    protected boolean isEnterDoors = false;
    protected boolean avengeful = true;
    protected boolean rally_on_hurt = true;

    protected int attack_all_players = -1;

    public boolean spawn_without_darkness = false;
    public boolean spawn_without_moroon_ground = true;
    public boolean can_spawn_in_peaceful = false;

    public boolean isMoroon = false;
    public boolean isIdealland = false;
    protected boolean applyLevelBoost = false;

    public boolean dontDespawn = false;

    public boolean dontDrown = false;

    protected boolean enterDoors = false;

    public float MP = 0;
    public float MPMax = 0;
    public float MPRegen = 1 / TICK_PER_SECOND;

    private boolean needFirstTickInLife = true;
    private boolean needFirstTickAfterConstruct = true;

    public boolean forceSyncLimbSwingToClient = false;

    protected static final DataParameter<Float> LIMB_SWING_AMOUNT = EntityDataManager.<Float>createKey(EntityModUnit.class, DataSerializers.FLOAT);

    protected static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntityModUnit.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<Byte> FACTION_TEAM = EntityDataManager.<Byte>createKey(EntityModUnit.class, DataSerializers.BYTE);

    public EntityModUnit(World worldIn) {
        super(worldIn);

//        this.entityCollisionReduction = 0.9f;//not much use for fixed ones
    }

    //sets this entity as a building
    public EntityModUnit setBuilding() {
        is_mechanic = true;
        is_building = true;
        is_pinned_on_ground = true;
        dontDespawn = true;
        hold_ground = true;

        wander = false;
        can_swim = false;
        go_home = false;
        melee_atk = false;
        avoid_danger = false;
        across_village = false;
        return this;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SWINGING_ARMS, Boolean.FALSE);
        this.dataManager.register(LIMB_SWING_AMOUNT, 0f);
        this.dataManager.register(FACTION_TEAM, CRITTER.index);
    }

    public void onFirstTickInLife() {

    }

    public float getWatchNearbyDistance() {
        return 8.0f;//vanilla value
    }

    public void onFirstTickAfterConstruct() {
        firstTickAI();
    }

    protected void firstTickAI() {
        if (is_mechanic) {
            tasks.addTask(0, new EntityAIMechInterfere(this));
        }

        if (can_swim) {
            this.tasks.addTask(1, new EntityAISwimming(this));
        }

        if (melee_atk) {
            this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
        }

        if (avoid_danger) {
            this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 8.0F, 0.6D, 0.6D));
            this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTNTPrimed.class, 8.0F, 0.6D, 0.6D));
        }

        if (go_home) {
            this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        }

        if (across_village) {
            this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        }

        if (wander) {
            this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        }

        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, getWatchNearbyDistance()));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        //wont move through village
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        applyTargetAI();
    }

    protected void applyTargetAI() {
        if (avengeful) {
            this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, rally_on_hurt));
        }

        if (attack_all_players > 0) {
            this.targetTasks.addTask(attack_all_players, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        }

        ((PathNavigateGround) this.getNavigator()).setEnterDoors(isEnterDoors);
    }

    public void resetAttackFaction() {
        if (this instanceof ICustomFaction) {
            targetTasks.taskEntries.clear();
            this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, rally_on_hurt));
//            Idealland.Log("Faction ID = %s", ((ICustomFaction) this).getFaction());
            switch (((ICustomFaction) this).getFaction()) {

                case PLAYER:
                case IDEALLAND:
                    this.targetTasks.addTask(2, new ModAIAttackNearest<>(this, EntityLivingBase.class,
                            0, true, true, EntityUtil.HostileToIdl
                    ));
                    break;
                case MOB_VANILLA:
                    this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
                    break;
                case MOB_VAN_ZOMBIE:
                    this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
                    this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityMoroonUnitBase.class, true));
                    break;
                case MOROON:
                    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
                    this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIdeallandUnitBase.class, true));
                    this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityZombie.class, false));
                    this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
                    break;
                case CRITTER:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + ((ICustomFaction) this).getFaction());
            }
        } else {
            targetTasks.taskEntries.clear();
            applyTargetAI();
        }
    }

    //Caution:
    //This function is invoked in EntityLiving::ctor, hence abandoned.
    @Deprecated
    protected void initEntityAI() {

    }

    @Override
    public void onLivingUpdate() {
        if (needFirstTickInLife) {
            onFirstTickInLife();
            needFirstTickInLife = false;
        }

        if (needFirstTickAfterConstruct) {
            onFirstTickAfterConstruct();
            needFirstTickAfterConstruct = false;
        }

        updateArmSwingProgress();

        super.onLivingUpdate();

        if (!getEntityWorld().isRemote && forceSyncLimbSwingToClient) {
            this.dataManager.set(LIMB_SWING_AMOUNT, limbSwingAmount);
        }
    }

    public void setLevel(int newLevel) {
        if (newLevel > 0) {
            level = newLevel;
            ApplyLevelModifier();
        }
    }

    protected int decreaseAirSupply(int air) {
        if (dontDrown)
            return air;
        else
            return super.decreaseAirSupply(air);
    }

    public void clearEquips() {
        for (EntityEquipmentSlot slot:
                EntityEquipmentSlot.values()) {

            this.setItemStackToSlot(slot, ItemStack.EMPTY);
        }
    }


    //---------
    public void imitateLiving(EntityLivingBase livingBase) {
        for (EntityEquipmentSlot slot:
                EntityEquipmentSlot.values()) {

            this.setItemStackToSlot(slot, livingBase.getItemStackFromSlot(slot));
        }

        setAttr(
                livingBase instanceof EntityPlayer ? 16 :
                        EntityUtil.getAttrBase(livingBase, SharedMonsterAttributes.FOLLOW_RANGE),

                livingBase instanceof EntityPlayer ? 0.215 :
                        EntityUtil.getAttrBase(livingBase, SharedMonsterAttributes.MOVEMENT_SPEED),

                EntityUtil.getAttrBase(livingBase, SharedMonsterAttributes.ATTACK_DAMAGE),
                EntityUtil.getAttrBase(livingBase, SharedMonsterAttributes.ARMOR),
                EntityUtil.getAttrBase(livingBase, SharedMonsterAttributes.MAX_HEALTH)
        );

        setHealth(livingBase.getHealth());

        setCustomNameTag(livingBase.getName());
        //use nbt later
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger(IDLNBTDef.LEVEL, level);
        compound.setBoolean(IDLNBTDef.NEED_FIRST_TICK, needFirstTickInLife);
        if (this instanceof ICustomFaction) {
            compound.setByte(KEY_FACTION, ((ICustomFaction) this).getFaction().index);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        level = compound.getInteger(IDLNBTDef.LEVEL);
        needFirstTickInLife = compound.getBoolean(IDLNBTDef.NEED_FIRST_TICK);
        if (this instanceof ICustomFaction) {
            ((ICustomFaction) this).setFaction(compound.getByte(KEY_FACTION));
        }
    }

    public int getLevel() {
        return level;
    }

    protected int maxEasyLevel = 3;
    protected int maxNormLevel = 5;
    protected int maxHardLevel = 10;

    public int getMaxLevel() {
        return 10;
    }

    public int getRank() {
        int level = getLevel();
        if (level>=maxHardLevel) {
            return 4;
        } else if (level>=maxNormLevel) {
            return 3;
        } else if (level>=maxEasyLevel) {
            return 2;
        }
        return 1;
    }

    //在简单难度中区域难度的范围是0.75–1.5，在普通难度时为1.5–4.0，在困难难度时为2.25–6.75。
    public void ApplyGeneralLevelBoost(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        //with localDiff / 10 chance to lv up recursively
        int level = getLevel();
        int maxLv = getMaxLevel();
        float localDifficulty = difficulty.getAdditionalDifficulty();
        while (level < maxLv && ((getRNG().nextFloat()* 10f) <= localDifficulty)) {
            level++;
        }
        setLevel(level);
    }

    @Override
    public void onEntityUpdate() {
        //Vanilla cannot clear this automatically, need manual doing
        if (!getEntityWorld().isRemote) {
            EntityLivingBase atkTarget = getAttackTarget();
            if (atkTarget != null && atkTarget.isDead) {
                setAttackTarget(null);
            }
        }
        super.onEntityUpdate();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (EntityUtil.getAttitude(EntityUtil.EnumFaction.PLAYER, this) == EntityUtil.EnumAttitude.HATE) {
            if (!this.getEntityWorld().isRemote && this.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL) {
                this.setDead();
            }
        }

        if (is_pinned_on_ground) {
            motionX = 0;
            motionZ = 0;
        }

        if (MP < MPMax) {
            MP += MPRegen;
            if (MP > MPMax) {
                MP = MPMax;
            }
        }
    }

    public boolean trySpendMana(float val) {
        if (val <= MP) {
            MP -= val;
            return true;
        }
        return false;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    }

    //KB:1.0 = total defend
    public void setAttr(double sight, double speed, double attack, double armor, double hp) {
        //float modifier = getLevelModifier();
        float modifier = 1f;
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(sight * modifier);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(speed);//don't modify speed, crazy.
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(attack * modifier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(armor * modifier);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(hp * modifier);
        if (is_pinned_on_ground) {
            this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(2.0f);
        }
    }

    protected static final UUID LEVEL_BONUS_UUID = UUID.fromString("CEE0E5FE-3E5C-4515-AD0C-66483EAC7B9B");

    public void ApplyLevelModifier() {
        AttributeModifier LEVEL_BONUS_MODIFIER = (new AttributeModifier(LEVEL_BONUS_UUID, "level bonus", getLevelModifier(), 1));

        //prevent crashing
        if (this.getEntityAttribute(SharedMonsterAttributes.ARMOR).hasModifier(LEVEL_BONUS_MODIFIER)) {
            this.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(LEVEL_BONUS_UUID);
            //this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).removeModifier(LEVEL_BONUS_UUID);
            //this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(LEVEL_BONUS_UUID);
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(LEVEL_BONUS_UUID);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(LEVEL_BONUS_UUID);
        }

        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(LEVEL_BONUS_MODIFIER);
        //this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(LEVEL_BONUS_MODIFIER);
        //this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(LEVEL_BONUS_MODIFIER);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(LEVEL_BONUS_MODIFIER);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(LEVEL_BONUS_MODIFIER);
        this.setHealth(this.getMaxHealth());
    }

    public float getLevelModifier() {
        return (level - 1f) * 0.2f;
    }

    //Simulate EntityMob, use enchantments, knockback, etc.
    //this is attacking other, not being attacked
    public boolean attackEntityAsMob(Entity target) {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;

        if (target instanceof EntityLivingBase) {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)target).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = target.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag) {
            if (i > 0) {
                ((EntityLivingBase)target).knockBack(this, (float)i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0) {
                target.setFire(j * 4);
            }

            if (target instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer)target;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer)) {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1) {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.getEntityWorld().setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, target);
        }

        return flag;
    }

    public EnumPushReaction getPushReaction() {
        if (is_pinned_on_ground) {
            return EnumPushReaction.IGNORE;
        } else {
            return EnumPushReaction.NORMAL;
        }
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        if (applyLevelBoost) {
            ApplyGeneralLevelBoost(difficulty, livingdata);
        }

        return livingdata;
    }

    @Override
    public void addVelocity(double x, double y, double z) {
        if (is_pinned_on_ground) {
            super.addVelocity(0, y, 0);
        } else {
            super.addVelocity(x, y, z);
        }
    }

    //being knocked by others
    @Override
    public void knockBack(Entity source, float strength, double xRatio, double zRatio) {
        if (!is_pinned_on_ground) {
            super.knockBack(source, strength, xRatio, zRatio);
        }
    }

    //sound
    @Override
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return super.getHurtSound(damageSourceIn);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }


    //Spawn------------------------------------------------------

    public boolean getCanSpawnHere() {
        IBlockState iblockstate = getEntityWorld().getBlockState((new BlockPos(this)).down());

        return iblockstate.canEntitySpawn(this)
                && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F
                && (spawn_without_darkness || isValidLightLevel())
                && (spawn_without_moroon_ground || iblockstate.getBlock() instanceof BlockMoroonBase)
                && (getEntityWorld().getDifficulty() != EnumDifficulty.PEACEFUL || can_spawn_in_peaceful);
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel() {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (getEntityWorld().getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32)) {
            return false;
        } else {
            int i = getEntityWorld().getLightFromNeighbors(blockpos);

            if (getEntityWorld().isThundering()) {
                int j = getEntityWorld().getSkylightSubtracted();
                getEntityWorld().setSkylightSubtracted(10);
                i = getEntityWorld().getLightFromNeighbors(blockpos);
                getEntityWorld().setSkylightSubtracted(j);
            }

            return i <= this.rand.nextInt(8);
        }
    }

//    /**
//     * Get this Entity's EnumCreatureAttribute
//     */
//    public EnumCreatureAttribute getCreatureAttribute()
//    {
//        return EnumCreatureAttribute.UNDEAD;
//    }

//    @Nullable
//    protected ResourceLocation getLootTable()
//    {
//        return ModLootList.EMPTY;
//    }

    //AI..................................
    public class ModAIAttackNearest<T extends EntityLivingBase>  extends EntityAINearestAttackableTarget {
        public ModAIAttackNearest(EntityCreature creature, Class classTarget, boolean checkSight) {
            super(creature, classTarget, checkSight);
        }

        public ModAIAttackNearest(EntityCreature creature, Class classTarget, boolean checkSight, boolean onlyNearby) {
            super(creature, classTarget, checkSight, onlyNearby);
        }

        public ModAIAttackNearest(EntityCreature creature, Class classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable Predicate targetSelector) {
            super(creature, classTarget, chance, checkSight, onlyNearby, targetSelector);
        }

        protected AxisAlignedBB getTargetableArea(double targetDistance) {
            return this.taskOwner.getEntityBoundingBox().grow(targetDistance, targetDistance, targetDistance);
        }
    }

    public class ModAIAttackNearestAntiAir<T extends EntityLivingBase>  extends EntityAINearestAttackableTarget {
        public ModAIAttackNearestAntiAir(EntityCreature creature, Class classTarget, boolean checkSight) {
            super(creature, classTarget, checkSight);
        }

        public ModAIAttackNearestAntiAir(EntityCreature creature, Class classTarget, boolean checkSight, boolean onlyNearby) {
            super(creature, classTarget, checkSight, onlyNearby);
        }

        public ModAIAttackNearestAntiAir(EntityCreature creature, Class classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable Predicate targetSelector) {
            super(creature, classTarget, chance, checkSight, onlyNearby, targetSelector);
        }

        protected AxisAlignedBB getTargetableArea(double targetDistance) {
            return this.taskOwner.getEntityBoundingBox().grow(targetDistance, 255, targetDistance);
        }
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (is_building){
            if (source == DamageSource.IN_WALL) {
                return false;
            }

            if (source.isExplosion() || source.isFireDamage()) {
                amount *= 2;
            }

            if (source.isProjectile()) {
                amount *= 0.5f;
            }
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean isNoDespawnRequired() {
        return dontDespawn;
    }

    protected boolean canDespawn() {
        return !dontDespawn;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (world.isRemote && this instanceof ICustomFaction) {
            ((ICustomFaction) this).setFaction(EntityUtil.EnumFaction.fromIndex(compound.getByte(IDLNBTDef.KEY_FACTION)));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        if (world.isRemote && this instanceof ICustomFaction) {
            compound.setByte(KEY_FACTION, dataManager.get(FACTION_TEAM));
        }
        return super.writeToNBT(compound);
    }

    //too many accepted ones, maybe we should consider immune ones
    static final HashSet<Potion> mecPotionSet = new HashSet<>();

    {
        mecPotionSet.add(ModPotions.INVINCIBLE);
        mecPotionSet.add(ModPotions.BURN);
        mecPotionSet.add(ModPotions.INTERFERENCE);
        mecPotionSet.add(ModPotions.BLAST_RESIST);
        mecPotionSet.add(ModPotions.KB_RESIST);
        mecPotionSet.add(ModPotions.MAGIC_RESIST);
        mecPotionSet.add(ModPotions.BERSERK);
        mecPotionSet.add(ModPotions.CRIT_CHANCE_PLUS);
        mecPotionSet.add(ModPotions.CRIT_CHANCE_MINUS);
        mecPotionSet.add(ModPotions.CRIT_DMG_PLUS);
        mecPotionSet.add(ModPotions.CRIT_DMG_MINUS);
        mecPotionSet.add(ModPotions.HOT);
        mecPotionSet.add(ModPotions.COLD);
        mecPotionSet.add(ModPotions.MINUS_ARMOR_PERCENTAGE);
        mecPotionSet.add(ModPotions.PLUS_ARMOR_PERCENTAGE);
        mecPotionSet.add(ModPotions.FROZEN);
        mecPotionSet.add(ModPotions.STINK);
        mecPotionSet.add(ModPotions.NOTICED_BY_MOR);

        mecPotionSet.add(MobEffects.SPEED);
        mecPotionSet.add(MobEffects.SLOWNESS);
        mecPotionSet.add(MobEffects.HASTE);
        mecPotionSet.add(MobEffects.MINING_FATIGUE);
        mecPotionSet.add(MobEffects.STRENGTH);
        mecPotionSet.add(MobEffects.JUMP_BOOST);
        mecPotionSet.add(MobEffects.RESISTANCE);
        mecPotionSet.add(MobEffects.FIRE_RESISTANCE);
        mecPotionSet.add(MobEffects.INVISIBILITY);
        mecPotionSet.add(MobEffects.BLINDNESS);
        mecPotionSet.add(MobEffects.NIGHT_VISION);
        mecPotionSet.add(MobEffects.WEAKNESS);
        mecPotionSet.add(MobEffects.WITHER);
        mecPotionSet.add(MobEffects.GLOWING);
        mecPotionSet.add(MobEffects.LEVITATION);

    }

    @Override
    public boolean isPotionApplicable(PotionEffect potioneffectIn) {
        if (is_mechanic) {
            return mecPotionSet.contains(potioneffectIn.getPotion());
        }
        return super.isPotionApplicable(potioneffectIn);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (this instanceof ICustomFaction) {
            if (!player.getEntityWorld().isRemote && player.isCreative()) {
                ItemStack stack = player.getHeldItem(hand);
                ICustomFaction faction = (ICustomFaction) this;
                if (stack.getItem() == Items.END_CRYSTAL) {
                    faction.setFaction(EntityUtil.EnumFaction.MOROON);
                } else if (stack.getItem() == Items.BONE) {
                    faction.setFaction(EntityUtil.EnumFaction.MOB_VANILLA);
                } else if (stack.getItem() == Items.ROTTEN_FLESH) {
                    faction.setFaction(EntityUtil.EnumFaction.MOB_VAN_ZOMBIE);
                } else if (stack.getItem() == Items.GOLDEN_APPLE) {
                    faction.setFaction(EntityUtil.EnumFaction.PLAYER);
//                } else if (stack.getItem() == ModItems.ITEM_IDL_ORDER_1) {
//                    faction.setFaction(EntityUtil.EnumFaction.IDEALLAND);
                } else {
                    return false;
                }

                PlayerUtil.setCoolDown(player, hand);
            }
        }

        return super.processInteract(player, hand);
    }

    //Some units can prevent its ground base from being dug
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        World world = event.getWorld();
        List<EntityModUnit> units = EntityUtil.getEntitiesWithinAABB(world, EntityModUnit.class, event.getPos().up(), 0.5f, null);
        for (EntityModUnit unit :
                units) {
            if (unit.hold_ground) {
                event.setCanceled(true);
                return;
            }
        }
    }

}
