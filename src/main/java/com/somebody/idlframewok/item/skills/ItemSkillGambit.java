package com.somebody.idlframewok.item.skills;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.Random;

public class ItemSkillGambit extends ItemSkillBase {

    public ItemSkillGambit(String name) {
        super(name);
        maxLevel = 1;
        setCD(1,0);
        showDamageDesc = false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote && handIn == EnumHand.MAIN_HAND)
        {
            Random rand = playerIn.getRNG();
            if (playerIn.getRNG().nextBoolean())
            {
                playerIn.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
                worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1f, 3f);
            }
            else {
                playerIn.addItemStackToInventory(playerIn.getHeldItemOffhand().copy());
                worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.PLAYERS, 1f, 3f);
//                worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, playerIn.posX, playerIn.posY - 0.3D, playerIn.posZ,
//                        rand.nextGaussian() * 0.05D, -playerIn.motionY * 0.5D, rand.nextGaussian() * 0.05D);
            }


            activateCoolDown(playerIn, stack);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
