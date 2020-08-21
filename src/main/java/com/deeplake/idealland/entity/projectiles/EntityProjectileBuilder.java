package com.deeplake.idealland.entity.projectiles;

import com.deeplake.idealland.blocks.ModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

//modified from fireballs
public class EntityProjectileBuilder extends EntityIdlProjectile implements IProjectile {


    protected EntityProjectileBuilder(World worldIn) {
        super(worldIn);
    }

    protected EntityProjectileBuilder(World worldIn, ProjectileArgs args) {
        super(worldIn, args);
    }

    public EntityProjectileBuilder(World worldIn, ProjectileArgs args, EntityLivingBase shooter, double accelX, double accelY, double accelZ, float acceleration) {
        super(worldIn, args, shooter, accelX, accelY, accelZ, acceleration);
    }

    public EntityProjectileBuilder(World worldIn, ProjectileArgs args, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, args, shooter, accelX, accelY, accelZ);
    }

    /**
     * Called when this EntityIdlProjectile hits a block or entity.
     */
    protected void onImpact(RayTraceResult result)
    {
        //Idealland.Log("bullet impact %s", getUniqueID());
        if (!this.world.isRemote)
        {
            if (result.entityHit != null)
            {
                result.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.shootingEntity).setProjectile(), args.damage);
                this.applyEnchantments(this.shootingEntity, result.entityHit);
            }

           world.setBlockState(new BlockPos(posX, posY, posZ), ModBlocks.GRID_NORMAL.getDefaultState());

            world.playSound(posX, posY, posZ, SoundEvents.BLOCK_LAVA_EXTINGUISH,
                    SoundCategory.NEUTRAL, 1f, 1f, false);

            this.setDead();
        }
        else {
            world.spawnParticle(EnumParticleTypes.CLOUD, posX, posY, posZ, motionX, motionY, motionZ);
            if (result.entityHit == null)
            {
                Random random = new Random();
                float vx = (float) (motionX * 0.3f);
                float vy = (float) (motionY * 0.3f);
                float vz = (float) (motionZ * 0.3f);

                world.spawnParticle(EnumParticleTypes.FLAME, posX, posY, posZ, vx * random.nextFloat(), vy * random.nextFloat(), vz * random.nextFloat());
                world.spawnParticle(EnumParticleTypes.FLAME, posX, posY, posZ, -vx * random.nextFloat(), -vy * random.nextFloat(), -vz * random.nextFloat());
                world.spawnParticle(EnumParticleTypes.FLAME, posX, posY, posZ, -vx * random.nextFloat(), -vy * random.nextFloat(), -vz * random.nextFloat());
            }
        }
    }

}
