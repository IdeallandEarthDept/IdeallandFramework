package com.somebody.idlframewok.entity.creatures.ai;

import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import com.somebody.idlframewok.entity.projectiles.EntityProjectileSensorBlast;
import com.somebody.idlframewok.entity.projectiles.ProjectileArgs;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class EntityAITurretAttackAA extends EntityAITurretAttack {
    protected float blastPower;
    public EntityAITurretAttackAA(EntityLiving self, float errorModifier, float blastPower) {
        super(self, errorModifier);
        this.blastPower =blastPower;
    }

    public EntityAITurretAttackAA(EntityLiving self, float errorModifier, int coolDownMin, int coolDownDelta, float blastPower) {
        super(self, errorModifier, coolDownMin, coolDownDelta);
        this.blastPower =blastPower;
    }


    public void onAttackTriggered(EntityLivingBase target)
    {
        double d0 = self.getDistanceSq(target);
        double d1 = target.posX - self.posX;
        double d2 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (self.posY + (double)(self.height / 2.0F));
        double d3 = target.posZ - self.posZ;

        float dist =  MathHelper.sqrt(MathHelper.sqrt(d0));
        float estimateHitTime = MathHelper.sqrt(2 * bulletAccel * dist);

        //float halfDist = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
        
        EntityIdlProjectile entityIdlProjectile = new EntityProjectileSensorBlast(self.getEntityWorld(), new ProjectileArgs((float) EntityUtil.getAttack(self)).setBlast(blastPower), self,
                d1 + self.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionX,
                d2 + self.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionY,
                d3 + self.getRNG().nextGaussian() * (double)errorModifier + estimateHitTime * target.motionZ,
                bulletAccel);

        //entityIdlProjectile.posY += self.posY + (double)(self.height / 2.0F);
        self.getEntityWorld().spawnEntity(entityIdlProjectile);
    }
}
