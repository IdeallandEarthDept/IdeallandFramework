package com.somebody.idlframewok.entity.creatures.misc;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

//not mod unit
public class EntityGhostHorse extends AbstractHorse {
    public EntityGhostHorse(World worldIn) {
        super(worldIn);
    }

    public boolean getCanSpawnHere() {
        IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());

        return iblockstate.canEntitySpawn(this)
                && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F
                && (isValidLightLevel());
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    protected void firstTickAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.2D));
        this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
        //this.tasks.addTask(2, new EntityAIMate(this, 1.0D, AbstractHorse.class));
        //this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0D));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.2D, 1.0D));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.7D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel() {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (this.world.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32)) {
            return false;
        } else {
            int i = this.world.getLightFromNeighbors(blockpos);

            if (this.world.isThundering()) {
                int j = this.world.getSkylightSubtracted();
                this.world.setSkylightSubtracted(10);
                i = this.world.getLightFromNeighbors(blockpos);
                this.world.setSkylightSubtracted(j);
            }

            return i <= this.rand.nextInt(8);
        }
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.world.isDaytime() && !this.world.isRemote) {
            float f = this.getBrightness();

            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ))) {
                attackEntityFrom(DamageSource.STARVE, 1);
            }
        }
    }
}
