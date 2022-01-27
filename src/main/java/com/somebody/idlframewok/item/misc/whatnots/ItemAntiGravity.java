package com.somebody.idlframewok.item.misc.whatnots;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class ItemAntiGravity extends ItemBase {
    public ItemAntiGravity(String name) {
        super(name);
        setMaxDamage(TICK_PER_SECOND * 60);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && isSelected && entityIn instanceof EntityLivingBase) {
            EntityUtil.ApplyBuff((EntityLivingBase) entityIn, MobEffects.LEVITATION, 0, 0.2f);
            stack.damageItem(1, (EntityLivingBase) entityIn);
            //entityIn.motionY = entityIn.motionY + 0.1f;
            //Idealland.Log("vy = %s", entityIn.motionY);
        }
    }
}
