package com.somebody.idlframewok.item.weapon;

import com.somebody.idlframewok.item.ILogNBT;
import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.item.ItemAdaptingBase;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;

public abstract class ItemStaffBase extends ItemAdaptingBase implements IWIP, ILogNBT {
    protected int basePreWarmTicks = 3 * CommonDef.TICK_PER_SECOND;
    protected int basePeroid = CommonDef.TICK_PER_SECOND;
    protected float baseRadius = 16f;
    protected float ratioForBuff = 0.1f;//haste and fatigue will affect it

    public ItemStaffBase(String name) {
        super(name);
        useable = true;
    }

    public int getPrewarmTicks(ItemStack stack, EntityLivingBase base) {
        return (int) (basePreWarmTicks * (1f -
                ratioForBuff * (EntityUtil.getBuffLevelIDL(base, MobEffects.HASTE)
                        - EntityUtil.getBuffLevelIDL(base, MobEffects.MINING_FATIGUE))));
    }

    public int getPeriod(ItemStack stack, EntityLivingBase base) {
        return Math.max(1,
                (int) (basePeroid * (1f -
                        ratioForBuff * (EntityUtil.getBuffLevelIDL(base, MobEffects.HASTE)
                                - EntityUtil.getBuffLevelIDL(base, MobEffects.MINING_FATIGUE))
                )
                )
        );
    }

    public abstract void serverTakeEffect(ItemStack stack, EntityLivingBase caster, int count);

    @Override
    public void serverUseTick(ItemStack stack, EntityLivingBase living, int count) {
        super.serverUseTick(stack, living, count);
        int activated = count - getPrewarmTicks(stack, living);
        if (activated >= 0) {
            if (activated % getPeriod(stack, living) == 0) {
                serverTakeEffect(stack, living, count);
            }
        }
    }

    @Override
    public void clientUseTick(ItemStack stack, EntityLivingBase living, int count) {
        super.clientUseTick(stack, living, count);
        int activated = count - getPrewarmTicks(stack, living);
        if (activated >= 0) {
            for (int i = 0; i < 20; i++) {
                EntityUtil.spawnCubeParticleAround(living, EnumParticleTypes.CRIT_MAGIC, baseRadius);
            }
        } else {
            EntityUtil.spawnCubeParticleAround(living, EnumParticleTypes.CRIT_MAGIC, 1f);
        }
    }
}
