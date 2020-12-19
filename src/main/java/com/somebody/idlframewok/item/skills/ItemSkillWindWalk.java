package com.somebody.idlframewok.item.skills;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.IDLSkillNBT.getLevel;

public class ItemSkillWindWalk extends ItemSkillBase {
    public ItemSkillWindWalk(String name) {
        super(name);
        cool_down = 50f;
        maxLevel = 4;
        showDamageDesc = false;
        setVal(20,5);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (isStackReady(playerIn, stack))
        {
            if (!worldIn.isRemote)
            {
                playerIn.addPotionEffect(new PotionEffect(MobEffects.SPEED, (int)(getVal(stack) * TICK_PER_SECOND), getLevel(stack) - 1));
                playerIn.playSound(SoundEvents.BLOCK_NOTE_HARP, 1f, 3f);
                activateCoolDown(playerIn, stack);
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }


    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
       super.addInformation(stack, world, tooltip, flag);
       tooltip.add(GetDuraDescString(getVal(stack)));
    }
}
