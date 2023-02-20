package com.somebody.idlframewok.item.skills;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemSkillVanquishWeak extends ItemSkillBase {
    public ItemSkillVanquishWeak(String name) {
        super(name);
        cool_down = 5f;
        setVal(15,5f);
        setCD(1f, 0.1f);
        maxLevel = 25565;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        if (target.getHealth() < getVal(stack))
        {
            playerIn.swingArm(handIn);
            activateCoolDown(playerIn, stack);
            target.playSound(SoundEvents.ENTITY_POLAR_BEAR_WARNING, 1f, 1f);
            target.setDead();
            return true;
        }
        else {
            return false;
        }


    }
}
