package com.somebody.idlframewok.item.weapon.staff;

import com.somebody.idlframewok.item.weapon.ItemStaffBase;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemHealStaff extends ItemStaffBase {
    public ItemHealStaff(String name) {
        super(name);
        setVal(1f, 1f);
        setRange(12f, 4f);
        basePreWarmTicks = (int) (CommonDef.TICK_PER_SECOND * 1.5f);
    }

    @Override
    public void serverTakeEffect(ItemStack stack, EntityLivingBase caster, int count) {
        List<EntityLivingBase> livingBases =
                EntityUtil.getEntitiesWithinAABB(caster.getEntityWorld(),
                        EntityLivingBase.class,
                        caster.getPositionVector(),
                        getRange(stack),
                        EntityUtil.ALL_ALIVE);

        for (EntityLivingBase target :
                livingBases) {
            if (EntityUtil.getAttitude(caster, target) != EntityUtil.EnumAttitude.HATE) {
                target.heal(getVal(stack, caster));
                target.playSound(SoundEvents.BLOCK_NOTE_BELL, 1f, 1f);
            }
        }
    }
}
