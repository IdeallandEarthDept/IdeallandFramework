package com.somebody.idlframewok.item.skills;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSkillCloneEgg extends ItemSkillBase {

    public ItemSkillCloneEgg(String name) {
        super(name);
        maxLevel = 12;
        setCD(60, 5);
        showDamageDesc = false;
    }


    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        if (isStackReady(playerIn, stack)) {
            World world = playerIn.world;
            if (!world.isRemote) {
                ItemStack cloneResult = target.getPickedResult(null);
                if (!cloneResult.isEmpty())
                {
                    playerIn.addItemStackToInventory(cloneResult);
                    activateCoolDown(playerIn, stack);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
