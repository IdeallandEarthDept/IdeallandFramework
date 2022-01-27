package com.somebody.idlframewok.entity.projectiles.casting;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonDef;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityDelayBoomCast extends EntityCastingBase {

    //todo: add "Alert!(wei)" buff

    //enchant table can only fall down, and is highly uncontrolable
    EnumParticleTypes particleTypes = EnumParticleTypes.FLAME;
    int triggerTick = 1;

    public EntityDelayBoomCast(World worldIn) {
        super(worldIn);
        setTickToLive(3 * CommonDef.TICK_PER_SECOND);
    }

    public EntityDelayBoomCast(World worldIn ,float life) {
        super(worldIn);
        setTickToLive((int) (life * CommonDef.TICK_PER_SECOND));
    }

//    @Override
//    public boolean canRenderOnFire() {
//        return super.canRenderOnFire();
//    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        upkeep();
        if (world.isRemote)
        {
            //create particles
            int particlePerTick = ModConfig.DEBUG_CONF.BOOM_PARTICLE_PER_TICK;
            float radius = ModConfig.DEBUG_CONF.BOOM_PARTICLE_RADIUS;
            float ySpeed = ModConfig.DEBUG_CONF.BOOM_PARTICLE_SPEED;
            for (int i = 0; i < particlePerTick; i++)
            {
                float angle = (float) (Math.random() * 6.28f);
                float tempRadius = ModConfig.DEBUG_CONF.BOOM_PARTICLE_FILLED ? radius : (float) (radius * Math.random());
                world.spawnParticle(particleTypes,
                        posX + Math.sin(angle) * tempRadius,
                        posY ,
                        posZ + Math.cos(angle) * tempRadius,
                        0,
                        ySpeed,
                        0
                        );

                world.spawnParticle(particleTypes,
                        posX + Math.sin(angle) * radius,
                        posY ,
                        posZ + Math.cos(angle) * radius,
                        0,
                        0,
                        0
                );
            }
        }
        else {

        }
    }

    @Override
    public void onExpire() {
        super.onExpire();
        trigger();
    }

    void trigger()
    {
        if (!world.isRemote)
        {
            world.createExplosion(shootingEntity, posX, posY, posZ, power, false);
        }
    }
}
