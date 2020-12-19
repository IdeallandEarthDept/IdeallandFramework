package com.somebody.idlframewok.entity.creatures.ai;

;

public class RangedAttackArguments {
    public float range = 100f;
    public int cool_down = 20;//ticks
    public float power = 1f;
    public float bullet_speed = 2f;
    public float attack_chance = 1f;
    public BulletMode attack_mode = BulletMode.SmallFireball;
    public boolean isTurret = false;
}
