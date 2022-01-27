package com.somebody.idlframewok.entity.projectiles;

import net.minecraft.nbt.NBTTagCompound;

public class ProjectileArgs {
    public float damage = 1f;
    public float explosion_power = 0f;
    public float speed = 1f;
    public boolean burning = false;
    public int ttl = 9999;
    public boolean bypassArmor = false;

    public ProjectileArgs(float damage, float explosion_power, float speed, boolean burning) {
        this.damage = damage;
        this.explosion_power = explosion_power;
        this.speed = speed;
        this.burning = burning;
    }

    public ProjectileArgs(float damage) {
        this.damage = damage;
    }

    public ProjectileArgs setBypassArmor(boolean bypassArmor) {
        this.bypassArmor = bypassArmor;
        return this;
    }

    public ProjectileArgs setBlast(float blastPower) {
        this.explosion_power = damage;
        return this;
    }

    public ProjectileArgs setTTL(int tick) {
        this.ttl = tick;
        return this;
    }

    public ProjectileArgs setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setFloat("damage", damage);
        compound.setFloat("explosion_power", explosion_power);
        compound.setBoolean("burning", this.burning);
        compound.setFloat("explosion_power", this.explosion_power);
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        this.damage = compound.getFloat("damage");
        this.explosion_power = compound.getFloat("explosion_power");
        this.burning = compound.getBoolean("burning");
        this.explosion_power = compound.getFloat("explosion_power");
    }
}
