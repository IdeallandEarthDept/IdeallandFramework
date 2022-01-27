package com.somebody.idlframewok.entity.creatures.cubix;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.entity.creatures.moroon.EntityMoroonTainter;
import com.somebody.idlframewok.init.InitBiome;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.world.dimension.hexcube.HexCubeHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityCubix extends com.somebody.idlframewok.entity.creatures.EntityModUnit implements IMob {
    public EntityCubix(World worldIn) {
        super(worldIn);

        is_cubix = true;
        //todo: white and black, attack each other
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!getEntityWorld().isRemote)
        {
            if (!IDLNBTUtil.GetBoolean(this, IDLNBTDef.INIT_DONE, false))
            {
                if (getEntityWorld().getBiome(getPosition()) == InitBiome.BIOME_CUBE)
                {
                    int level = (int) (1 + HexCubeHelper.getDifficulty(getPosition()));
                    setLevel(level);
                    Idealland.Log("cubix level set to %d", level);
                }
                IDLNBTUtil.SetBoolean(this, IDLNBTDef.INIT_DONE, true);
            }

            EntityLivingBase target = getAttackTarget();
            if ( target != null && (getEntityWorld().getWorldTime() % TICK_PER_SECOND == 0))
            {
                //teleport to same height
                if (Math.abs(target.posY - posY )> 3f)
                {
                    boolean success = false;

                    success = attemptTeleport(this.posX, target.posY, this.posZ);

                    if(!success)
                    {
                        for (int i = -2; i <= 2; i++)
                        {
                            if (i != 0)
                            {
                                if (attemptTeleport(this.posX, target.posY + i, this.posZ)) {
                                    success = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (success)
                    {
                        EntityUtil.ApplyBuff(this, MobEffects.SPEED, 0, 1);
                        EntityUtil.ApplyBuff(this, MobEffects.WEAKNESS, 0, 1);
                        EntityUtil.ApplyBuff(this, ModPotions.INVINCIBLE, 0, 1);
                    }
                }
            }
        }
    }
//
//    public float getLevelModifier()
//    {
//        return (getLevel() - 1f) * 0.05f;
//    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16.0D, 0.2D, 3.0D, 1.0D, 6.0D);
    }

    protected void firstTickAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTNTPrimed.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityMoroonTainter.class}));
        applyGeneralAI();
    }

    protected void applyGeneralAI()
    {
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, false, false, EntityUtil.NotCubix));


        ((PathNavigateGround)this.getNavigator()).setEnterDoors(true);
    }

    public int getMaxLevel() {
        switch (getEntityWorld().getDifficulty())
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

        return super.onInitialSpawn(difficulty, livingdata);
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.getEntityWorld().getDifficulty() != EnumDifficulty.PEACEFUL && super.getCanSpawnHere();
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);

        if (wasRecentlyHit)
        {
            if (getLevel() > 4 && (rand.nextInt(getLevel()) > 4))
            {
                dropItem(ModItems.CUBIX_INGOT, rand.nextInt(getLevel() >> 2));
            }

            if (rand.nextFloat() < 0.01f * getLevel())
            {
                dropItem(ModItems.RANDOM_SKILL, 1);
            }

            if (rand.nextFloat() < 0.1f * getLevel())
            {
                dropItem(ModItems.itemNanoMender_16, 1 + rand.nextInt(2 + lootingModifier));
            }
        }
    }
}
