package com.somebody.idlframewok.entity.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityMoroonBullet extends EntityIdlProjectile {
    protected EntityMoroonBullet(World worldIn)
    {
        //dont call this directly
        super(worldIn);
    }

    public EntityMoroonBullet(World worldIn, ProjectileArgs args, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, args, shooter, accelX, accelY, accelZ, 0.1f);
    }

    public EntityMoroonBullet(World worldIn, ProjectileArgs args, EntityLivingBase shooter, double accelX, double accelY, double accelZ, float acceleration)
    {
        super(worldIn, args, shooter, accelX, accelY, accelZ, acceleration);
    }
}
