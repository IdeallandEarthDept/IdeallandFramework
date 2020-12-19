package com.somebody.idlframewok.item.consumables;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemLevelUpBadge  extends ItemBase {
    public ItemLevelUpBadge(String name) {
        super(name);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        if (target instanceof EntityModUnit)
        {
            EntityModUnit legalTarget = (EntityModUnit) target;
            int level = legalTarget.getLevel();
            int lvMax = legalTarget.getMaxLevel();
            if (level < lvMax)
            {
                legalTarget.setLevel(level+1);
            }

            playerIn.swingArm(handIn);
            target.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            stack.shrink(1);
            return true;
        }
        return false;
    }
}
