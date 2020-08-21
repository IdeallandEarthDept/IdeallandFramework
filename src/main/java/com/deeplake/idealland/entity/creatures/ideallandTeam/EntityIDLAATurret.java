package com.deeplake.idealland.entity.creatures.ideallandTeam;

import com.deeplake.idealland.entity.creatures.EntityModUnit;
import com.deeplake.idealland.entity.creatures.ai.EntityAITurretAttack;
import com.deeplake.idealland.util.EntityUtil;
import com.deeplake.idealland.util.IDLGeneral;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Objects;

public class EntityIDLAATurret extends EntityIdlTurret {
    public EntityIDLAATurret(World worldIn) {
        super(worldIn);
    }

    protected void applyAttackingAI()
    {
        this.tasks.addTask(1, new EntityAITurretAttack(this, 0.01f, 10, 5));
        //this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    protected void applyTargetingAI()
    {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityIdlTurret.class));
        this.targetTasks.addTask(2, new ModAIAttackNearestAntiAir<>(this, EntityLivingBase.class,
                0, true, false, EntityUtil.HostileToIdl_AIR
        ));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(64.0D, 0D, 5.0D, 3.0D, 50.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        EntityLivingBase target = getAttackTarget();
        if (target != null && target.onGround)
        {
            setAttackTarget(null);
        }
    }
}
