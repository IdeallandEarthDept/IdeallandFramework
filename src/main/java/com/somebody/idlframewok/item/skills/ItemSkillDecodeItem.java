package com.somebody.idlframewok.item.skills;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSkillDecodeItem extends ItemSkillBase {

    public ItemSkillDecodeItem(String name) {
        super(name);
        maxLevel = 10;
        setCD(12, 1);
        setVal(1, 0.1f);
        showDamageDesc = false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote && handIn == EnumHand.MAIN_HAND)
        {
            //playerIn.setFire(5);
            int count = playerIn.getHeldItemOffhand().getCount();
            float xp = (float)count * getVal(stack);
            playerIn.addExperience((int) xp);
            playerIn.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
            activateCoolDown(playerIn, stack);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        String mainDesc = I18n.format(stack.getUnlocalizedName() + ".desc.extra", getVal(stack));
        tooltip.add(mainDesc);
    }
}
