package com.somebody.idlframewok.entity.creatures.buildings.uprising;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.entity.creatures.ICustomFaction;
import com.somebody.idlframewok.entity.creatures.ai.EntityAITurretAttack;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityMediumTurret extends EntityModUnit implements ICustomFaction {
    public EntityMediumTurret(World worldIn) {
        super(worldIn);
        setBuilding();
        setSize(0.5f, 2f);
    }

    @Override
    protected void firstTickAI() {
        super.firstTickAI();
        applyAttackingAI();
//        applyTargetingAI();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(20, 0, 5, 5, 50);
    }

    @Override
    public float getWatchNearbyDistance() {
        return (float) EntityUtil.getSight(this);
    }

    protected void applyAttackingAI() {
        this.tasks.addTask(1, new EntityAITurretAttack(this, 0.001f, 10, 0)
//                .setVolley(1, 7, 1)
//                .setCustomArgs(new ProjectileArgs(0f).setBypassArmor(true))
                .setSound(SoundEvents.ENTITY_GHAST_SHOOT));
    }

//    protected void applyTargetingAI() {
//        this.targetTasks.addTask(2, new ModAIAttackNearest<>(this, EntityLivingBase.class,
//                10, true, true, EntityUtil.HostileToIdl
//        ));
//    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_IRONGOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GENERIC_EXPLODE;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        dropItem(Items.IRON_NUGGET, 3 + lootingModifier);
    }
}
