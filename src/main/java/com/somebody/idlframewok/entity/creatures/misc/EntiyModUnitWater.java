package com.somebody.idlframewok.entity.creatures.misc;

import com.somebody.idlframewok.blocks.blockMoroon.BlockMoroonBase;
import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntiyModUnitWater extends EntityModUnit {
    public int maxAir = 300;

    //copies EntityWaterMob
    public EntiyModUnitWater(World worldIn) {
        super(worldIn);
    }

    //EntityWaterMob
    public boolean canBreatheUnderwater()
    {
        return true;
    }


    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
        return (iblockstate.getBlock() == Blocks.WATER || iblockstate.getBlock() == Blocks.FLOWING_WATER)
                && (spawn_without_darkness || isValidLightLevel())
                && (spawn_without_moroon_ground || iblockstate.getBlock() instanceof BlockMoroonBase);
    }

    /**
     * Checks that the entity is not colliding with any blocks / liquids
     */
    public boolean isNotColliding()
    {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval()
    {
        return 120;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return true;
    }


    /**
     * Gets called every tick from main Entity class
     */
    public void onEntityUpdate()
    {
        int i = this.getAir();
        super.onEntityUpdate();

        if (this.isEntityAlive() && !this.isInWater())
        {
            --i;
            this.setAir(i);

            if (this.getAir() == -20)
            {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
        }
        else
        {
            this.setAir(maxAir);
        }
    }

    public boolean isPushedByWater()
    {
        return false;
    }
}
