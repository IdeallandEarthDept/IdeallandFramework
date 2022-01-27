package com.somebody.idlframewok.entity;

import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import com.somebody.idlframewok.entity.projectiles.ProjectileArgs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityProjectileBlade extends EntityIdlProjectile {
    //todo: handle correct rotationIn
    public EntityProjectileBlade(World worldIn) {
        super(worldIn);
    }

    public EntityProjectileBlade(World worldIn, ProjectileArgs args) {
        super(worldIn, args);
    }

    public EntityProjectileBlade(World worldIn, ProjectileArgs args, EntityLivingBase shooter, double accelX, double accelY, double accelZ, float acceleration) {
        super(worldIn, args, shooter, accelX, accelY, accelZ, acceleration);
        //setRotation(shooter.rotationYaw,shooter.rotationPitch);
        setLocationAndAngles(shooter.posX,shooter.posY+shooter.getEyeHeight(),shooter.posZ,shooter.rotationYaw,shooter.rotationPitch);
    }

    public EntityProjectileBlade(World worldIn, ProjectileArgs args, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, args, shooter, accelX, accelY, accelZ);
        //setRotation(shooter.rotationYaw,shooter.rotationPitch);
        setLocationAndAngles(shooter.posX,shooter.posY+shooter.getEyeHeight(),shooter.posZ,shooter.rotationYaw,shooter.rotationPitch);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }
}
