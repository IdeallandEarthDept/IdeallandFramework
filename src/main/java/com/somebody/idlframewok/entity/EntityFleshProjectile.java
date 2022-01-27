package com.somebody.idlframewok.entity;

import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import com.somebody.idlframewok.entity.projectiles.ProjectileArgs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityFleshProjectile extends EntityIdlProjectile {
    protected EntityFleshProjectile(World worldIn) {
        super(worldIn);
    }

    protected EntityFleshProjectile(World worldIn, ProjectileArgs args) {
        super(worldIn, args);
    }

    public EntityFleshProjectile(World worldIn, ProjectileArgs args, BlockPos shooter, double accelX, double accelY, double accelZ, float acceleration) {
        super(worldIn, args, shooter, accelX, accelY, accelZ, acceleration);
    }

    public EntityFleshProjectile(World worldIn, ProjectileArgs args, EntityLivingBase shooter, double accelX, double accelY, double accelZ, float acceleration) {
        super(worldIn, args, shooter, accelX, accelY, accelZ, acceleration);
    }

    public EntityFleshProjectile(World worldIn, ProjectileArgs args, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, args, shooter, accelX, accelY, accelZ);
    }
}
