package com.somebody.idlframewok.entity.creatures.moroon;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityIdeallandUnitBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityMoroonUnitBase extends EntityModUnit {

    int berserkGazeLv = 0;
    protected boolean inflictBerserkBuff = true;
    protected boolean autoArmor = false;

//    protected float[] inventoryHandsDropChances = new float[2];
//    /** Chances for armor dropping when this entity dies. */
//    protected float[] inventoryArmorDropChances = new float[4];



    // (Base + Op0) × (1 + SUM(Op1)) × PRODUCT(1+Op2)
    //Note that ops are 1 +
    //https://minecraft-zh.gamepedia.com/%E5%B1%9E%E6%80%A7
    public EntityMoroonUnitBase(World worldIn) {
        super(worldIn);
        setCanPickUpLoot(true);
        spawn_without_darkness = false;
        spawn_without_moroon_ground = false;
        experienceValue = 10;
        isMoroon = true;
        isIdealland = false;
        applyLevelBoost = true;
    }

    public int getMaxLevel() {
        switch (world.getDifficulty())
        {
            case PEACEFUL:
                return 1;
            case EASY:
                return 3;
            case NORMAL:
                return 5;
            case HARD:
                return maxHardLevel;
        }
        return 1;
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

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        ApplyGeneralLevelBoost(difficulty, livingdata);

        if (autoArmor)
        {
            this.setEquipmentBasedOnDifficulty(difficulty);
            this.setEnchantmentBasedOnDifficulty(difficulty);
        }

        return super.onInitialSpawn(difficulty, livingdata);
    }

    protected void applyGeneralAI()
    {


        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIdeallandUnitBase.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityZombie.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));

        ((PathNavigateGround)this.getNavigator()).setEnterDoors(true);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
//        EntityLivingBase target = getAttackTarget();
//        if (inflictBerserkBuff && target != null && berserkGazeLv >= 0 && (world.getWorldTime() % TICK_PER_SECOND == 7) )
//        {
//            target.addPotionEffect(new PotionEffect(ModPotions.BERSERK, TICK_PER_SECOND + 1,  berserkGazeLv));
//        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && super.getCanSpawnHere();
    }

    public boolean isPreventingPlayerRest(EntityPlayer playerIn)
    {
        return true;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        //IdlFramework.Log("dropLoot Called");
//        dropItem(ModItems.MOR_FRAG, 2 + rand.nextInt(2 + lootingModifier));
//        if (rand.nextFloat() < 0.01f * getLevel())
//        {
//            dropItem(ModItems.RANDOM_SKILL, 1);
//        }
//
//        if (rand.nextFloat() < 0.1f * getLevel())
//        {
//            dropItem(ModItems.itemNanoMender_16, 1 + rand.nextInt(2 + lootingModifier));
//        }
//
//        if (rand.nextFloat() < 0.1f * getLevel())
//        {
//            dropItem(ModItems.itemNanoMender_16, 1 + rand.nextInt(2 + lootingModifier));
//        }
//
//        if (rand.nextFloat() < 0.2f * getLevel())
//        {
//            dropItem(ModItems.FIGHT_BREAD, 1 + rand.nextInt(2 + lootingModifier));
//        }
//
//        if (rand.nextFloat() < ModConfig.GeneralConf.SKILL_RATE * getLevel())
//        {
//            dropItem(ModItems.itemNanoMender_128, 1);
//        }

    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PLAYER_HURT;
    }

    //    @Override
//    protected SoundEvent getAmbientSound() {
//        return ModSoundHandler.SOUND_1;
//    }
}
