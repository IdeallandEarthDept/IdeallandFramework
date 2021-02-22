package com.somebody.idlframewok.entity.creatures.ideallandTeam;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityIdeallandUnitBase extends EntityModUnit {
    public EntityIdeallandUnitBase(World worldIn) {
        super(worldIn);
        setCanPickUpLoot(false);//prevent pick up player things. keep until gui done
        isMoroon = false;
        isIdealland = true;
        dontDespawn = true;
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

//    @Nullable
//    @Override
//    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
//        livingdata = super.onInitialSpawn(difficulty, livingdata);
//        ApplyGeneralLevelBoost(difficulty, livingdata);
//        return super.onInitialSpawn(difficulty, livingdata);
//    }

    protected void applyGeneralAI()
    {

//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, false, true, new Predicate<EntityLiving>()
//        {
//            public boolean apply(@Nullable EntityLiving p_apply_1_)
//            {
//                return p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper);
//            }
//        }));

        ((PathNavigateGround)this.getNavigator()).setEnterDoors(true);
    }
}
