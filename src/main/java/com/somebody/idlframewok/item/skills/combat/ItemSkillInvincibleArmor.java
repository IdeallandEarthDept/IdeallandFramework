package com.somebody.idlframewok.item.skills.combat;

import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.IDLSkillNBT;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class ItemSkillInvincibleArmor extends ItemSkillBase {
    public ItemSkillInvincibleArmor(String name) {
        super(name);
        showDamageDesc = false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (isStackReady(playerIn, stack))
        {
            if (!worldIn.isRemote)
            {
                playerIn.addPotionEffect(new PotionEffect(ModPotions.INVINCIBLE, (int)(getVal(stack) * TICK_PER_SECOND), IDLSkillNBT.getLevel(stack) - 1));
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
