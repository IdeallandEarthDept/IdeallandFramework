package com.deeplake.idealland.entity.creatures.ideallandTeam;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.util.EntityUtil;
import com.deeplake.idealland.util.IDLGeneral;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.idealland.util.EntityUtil.FriendToIdl;
import static net.minecraft.util.DamageSource.causePlayerDamage;

public class EntityAntiAirRadar extends EntityIdeallandUnitBase {
    public EntityAntiAirRadar(World worldIn) {
        super(worldIn);
        is_pinned_on_ground = true;
        is_mechanic = true;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(64.0D, 0D, 3.0D, 3.0D, 20.0D);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(3, new EntityAILookIdle(this));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote && world.getWorldTime() % TICK_PER_SECOND == 0)
        {
            float sight = (float) EntityUtil.getSight(this);
            float base_range = sight ;
            Vec3d basePos = getPositionVector();
            List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLiving.class,
                    IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range),
                            basePos.addVector(base_range, 255, base_range)), EntityUtil.HostileToIdl_AIR);

            if (targets.size() > 0)
            {
                base_range = 16f;
                List<EntityLiving> entitiesFriend = world.getEntitiesWithinAABB(EntityLiving.class,
                        IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range),
                                basePos.addVector(base_range, base_range, base_range)),
                        FriendToIdl);
                for (EntityLiving living: entitiesFriend
                ) {
                    if (living.getAttackTarget() == null)
                    {
                        float sightSq = sight * sight;

                        for (EntityLivingBase tryTarget:
                             targets) {
                            if (living.getDistanceSq(tryTarget) <= sightSq)
                            {
                                living.getLookHelper().setLookPositionWithEntity(tryTarget, 0, 0);
                                living.setAttackTarget(tryTarget);
                                break;
                            }
                        }
                    }
                }
            }

        }
    }
}
