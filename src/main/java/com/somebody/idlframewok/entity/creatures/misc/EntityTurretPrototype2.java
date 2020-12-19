package com.somebody.idlframewok.entity.creatures.misc;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.entity.creatures.ai.BulletMode;
import com.somebody.idlframewok.entity.creatures.ai.EntityAIBulletAttack;
import com.somebody.idlframewok.entity.creatures.ai.RangedAttackArguments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityTurretPrototype2 extends EntityModUnit {
//public class EntityTurretPrototype2<region> extends EntityMob implements IRangedAttackMob {

    public static float lookRange = 10f;
    public static boolean fixed_on_ground = true;


    public EntityTurretPrototype2(World worldIn) {
        super(worldIn);
        setSize(0.9F,1F);
        entityCollisionReduction = 0.9f;//not much use for fixed ones
        is_mechanic = true;
    }

    @Override
    public float getEyeHeight() {
        return 0.5F;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, lookRange));
        this.tasks.addTask(6, new EntityAILookIdle(this));

        RangedAttackArguments args = new RangedAttackArguments();
        args.attack_mode = BulletMode.SmallFireball;
        args.range = (float) this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue();
        args.isTurret = true;

        this.tasks.addTask(5, new EntityAIBulletAttack(this, args));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityCow.class, true));

    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    }
}
