package com.somebody.idlframewok.entity.creatures.misc;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;

import java.util.List;

public class EntityAutumnRiderGroup extends EntityModUnit {
    public EntityAutumnRiderGroup(World worldIn) {
        super(worldIn);
        spawn_without_darkness = false;
    }

    @Override
    public void onEntityUpdate() {
        //Idealland.Log(String.format("onEntityUpdate = %s", world.isRemote) );
        //Idealland.Log(String.format("onEntityUpdate Position = %s", getPosition()) );
        super.onEntityUpdate();
        if (!world.isRemote)
        {
            EntityGhostHorse horse = new EntityGhostHorse(world);
            horse.setPosition(posX,posY,posZ);
            world.spawnEntity(horse);

            if (EntityAutumnVisitor.instance != null)
            {
                setDead();
                return;
            }

            EntityAutumnVisitor rider = new EntityAutumnVisitor(world);
            rider.setPosition(posX,posY,posZ);
            rider.startRiding(horse);
            world.spawnEntity(rider);

            playSound(SoundEvents.ENTITY_HORSE_AMBIENT, 8f, 1.2f);

            List<EntityLivingBase> livingBases = EntityUtil.getEntitiesWithinAABB(world, EntityLivingBase.class, getPositionVector(), 32f, null);
            for (EntityLivingBase livingBase:
            livingBases) {
                if (livingBase.isEntityUndead())
                {
                    EntityUtil.ApplyBuff(livingBase, ModPotions.CRIT_CHANCE_PLUS, 1, 10f);
                    EntityUtil.ApplyBuff(livingBase, ModPotions.CRIT_DMG_PLUS, 1, 10f);
                    EntityUtil.ApplyBuff(livingBase, MobEffects.STRENGTH, 1, 10f);
                }
                else {
                    EntityUtil.ApplyBuff(livingBase, ModPotions.CRIT_CHANCE_MINUS, 1, 10f);
                    EntityUtil.ApplyBuff(livingBase, ModPotions.CRIT_DMG_MINUS, 1, 10f);
                    EntityUtil.ApplyBuff(livingBase, MobEffects.WEAKNESS, 1, 10f);
                }
            }
            this.setDead();
        }
    }

}
