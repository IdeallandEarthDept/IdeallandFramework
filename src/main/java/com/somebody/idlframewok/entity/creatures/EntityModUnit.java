package com.somebody.idlframewok.entity.creatures;

import com.somebody.idlframewok.blocks.blockMoroon.BlockMoroonBase;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.google.common.base.Predicate;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import java.util.UUID;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityModUnit extends EntityCreature {

    private int level = 1;

    public boolean is_pinned_on_ground = false;
    public boolean is_mechanic = false;
    public boolean is_god = false;
    public boolean is_building = false;
    public boolean is_cubix = false;

    public boolean spawn_without_darkness = false;
    public boolean spawn_without_moroon_ground = false;

    public boolean isMoroon = false;
    public boolean isIdealland = false;
    protected boolean applyLevelBoost = false;

    public boolean dontDespawn = false;

    public float MP = 0;
    public float MPMax = 0;
    public float MPRegen = 1 / TICK_PER_SECOND;

    protected static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntityModUnit.class, DataSerializers.BOOLEAN);

    public EntityModUnit(World worldIn) {
        super(worldIn);
        //this.entityCollisionReduction = 0.9f;//not much use for fixed ones
    }

    public EntityModUnit setBuilding()
    {
        is_mechanic = true;
        is_building = true;
        is_pinned_on_ground = true;
        dontDespawn = true;
        return this;
    }


    protected void entityInit()
    {
        this.dataManager.register(SWINGING_ARMS, Boolean.FALSE);

        super.entityInit();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger(IDLNBTDef.LEVEL, level);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        level = compound.getInteger(IDLNBTDef.LEVEL);
    }

    public void setLevel(int newLevel)
    {
        if (newLevel > 0)
        {
            level = newLevel;
            ApplyLevelModifier();
        }
    }

    public int getLevel()
    {
        return level;
    }

    protected int maxEasyLevel = 3;
    protected int maxNormLevel = 5;
    protected int maxHardLevel = 10;

    public int getMaxLevel() {
        return 10;
    }

    public int getRank()
    {
        int level = getLevel();
        if (level>=maxHardLevel)
        {
            return 4;
        }
        else if (level>=maxNormLevel)
        {
            return 3;
        }
        else if (level>=maxEasyLevel)
        {
            return 2;
        }
        return 1;
    }

    //在简单难度中区域难度的范围是0.75–1.5，在普通难度时为1.5–4.0，在困难难度时为2.25–6.75。
    public void ApplyGeneralLevelBoost(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        //with localDiff / 10 chance to lv up recursively
        int level = getLevel();
        int maxLv = getMaxLevel();
        float localDifficulty = difficulty.getAdditionalDifficulty();
        while (level < maxLv && ((getRNG().nextFloat()* 10f) <= localDifficulty))
        {
            level++;
        }
        setLevel(level);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (EntityUtil.getAttitude(EntityUtil.Faction.PLAYER, this) == EntityUtil.ATTITUDE.HATE)
        {
            if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
            {
                this.setDead();
            }
        }

        if (is_pinned_on_ground)
        {
            motionX = 0;
            motionZ = 0;
        }

        if (MP < MPMax)
        {
            MP += MPRegen;
            if (MP > MPMax)
            {
                MP = MPMax;
            }
        }
    }

    public boolean trySpendMana(float val)
    {
        if (val <= MP)
        {
            MP -= val;
            return true;
        }
        return false;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    }

    //KB:1.0 = total defend
    public void setAttr(double sight, double speed, double attack, double armor, double hp)
    {
        //float modifier = getLevelModifier();
        float modifier = 1f;
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(sight * modifier);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(speed * modifier);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(attack * modifier);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(armor * modifier);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(hp * modifier);
        if (is_pinned_on_ground)
        {
            this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(2.0f);
        }
    }

    protected static final UUID LEVEL_BONUS_UUID = UUID.fromString("CEE0E5FE-3E5C-4515-AD0C-66483EAC7B9B");

    public void ApplyLevelModifier()
    {
        AttributeModifier LEVEL_BONUS_MODIFIER = (new AttributeModifier(LEVEL_BONUS_UUID, "level bonus", getLevelModifier(), 1));

        //prevent crashing
        if (this.getEntityAttribute(SharedMonsterAttributes.ARMOR).hasModifier(LEVEL_BONUS_MODIFIER)) {
            this.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(LEVEL_BONUS_UUID);
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).removeModifier(LEVEL_BONUS_UUID);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(LEVEL_BONUS_UUID);
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(LEVEL_BONUS_UUID);
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(LEVEL_BONUS_UUID);
        }

        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(LEVEL_BONUS_MODIFIER);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(LEVEL_BONUS_MODIFIER);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(LEVEL_BONUS_MODIFIER);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(LEVEL_BONUS_MODIFIER);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(LEVEL_BONUS_MODIFIER);
        this.setHealth(this.getMaxHealth());
    }

    public float getLevelModifier()
    {
        return (level - 1f) * 0.2f;
    }

    //Simulate EntityMob, use enchantments, knockback, etc.
    //this is attacking other
    public boolean attackEntityAsMob(Entity target)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;

        if (target instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)target).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = target.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0)
            {
                ((EntityLivingBase)target).knockBack(this, (float)i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                target.setFire(j * 4);
            }

            if (target instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)target;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer))
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, target);
        }

        return flag;
    }



    public EnumPushReaction getPushReaction()
    {
        if (is_pinned_on_ground)
        {
            return EnumPushReaction.IGNORE;
        }
        else {
            return EnumPushReaction.NORMAL;
        }
    }
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
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

    public boolean getCanSpawnHere()
    {
        IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());

        return iblockstate.canEntitySpawn(this)
                && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F
                && (spawn_without_darkness || isValidLightLevel())
                && (spawn_without_moroon_ground || iblockstate.getBlock() instanceof BlockMoroonBase);
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (this.world.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int i = this.world.getLightFromNeighbors(blockpos);

            if (this.world.isThundering())
            {
                int j = this.world.getSkylightSubtracted();
                this.world.setSkylightSubtracted(10);
                i = this.world.getLightFromNeighbors(blockpos);
                this.world.setSkylightSubtracted(j);
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
    public class ModAIAttackNearest<T extends EntityLivingBase>  extends EntityAINearestAttackableTarget
    {
        public ModAIAttackNearest(EntityCreature creature, Class classTarget, boolean checkSight) {
            super(creature, classTarget, checkSight);
        }

        public ModAIAttackNearest(EntityCreature creature, Class classTarget, boolean checkSight, boolean onlyNearby) {
            super(creature, classTarget, checkSight, onlyNearby);
        }

        public ModAIAttackNearest(EntityCreature creature, Class classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable Predicate targetSelector) {
            super(creature, classTarget, chance, checkSight, onlyNearby, targetSelector);
        }

        protected AxisAlignedBB getTargetableArea(double targetDistance)
        {
            return this.taskOwner.getEntityBoundingBox().grow(targetDistance, targetDistance, targetDistance);
        }
    }

    public class ModAIAttackNearestAntiAir<T extends EntityLivingBase>  extends EntityAINearestAttackableTarget
    {
        public ModAIAttackNearestAntiAir(EntityCreature creature, Class classTarget, boolean checkSight) {
            super(creature, classTarget, checkSight);
        }

        public ModAIAttackNearestAntiAir(EntityCreature creature, Class classTarget, boolean checkSight, boolean onlyNearby) {
            super(creature, classTarget, checkSight, onlyNearby);
        }

        public ModAIAttackNearestAntiAir(EntityCreature creature, Class classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable Predicate targetSelector) {
            super(creature, classTarget, chance, checkSight, onlyNearby, targetSelector);
        }

        protected AxisAlignedBB getTargetableArea(double targetDistance)
        {
            return this.taskOwner.getEntityBoundingBox().grow(targetDistance, 255, targetDistance);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (is_building){
            if (source == DamageSource.IN_WALL) {
                return false;
            }

            if (source.isExplosion() || source.isFireDamage())
            {
                amount *= 2;
            }

            if (source.isProjectile())
            {
                amount *= 0.5f;
            }
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean isNoDespawnRequired() {
        return dontDespawn;
    }

    protected boolean canDespawn()
    {
        return !dontDespawn;
    }
}
