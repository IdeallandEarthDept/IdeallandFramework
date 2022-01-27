package com.somebody.idlframewok.entity.creatures;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityGeneralMob extends EntityModUnit implements IMob {
    protected boolean autoArmor = false;

    public EntityGeneralMob(World worldIn) {
        super(worldIn);
        attack_all_players = 2;
        melee_atk = true;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(32, 0.3, 1, 0, 20);
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

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        if (autoArmor)
        {
            this.setEquipmentBasedOnDifficulty(difficulty);
            this.setEnchantmentBasedOnDifficulty(difficulty);
        }

        return super.onInitialSpawn(difficulty, livingdata);
    }


}
