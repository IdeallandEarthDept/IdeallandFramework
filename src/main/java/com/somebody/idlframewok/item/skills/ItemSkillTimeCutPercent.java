package com.somebody.idlframewok.item.skills;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSkillTimeCutPercent extends ItemSkillBase {

    public ItemSkillTimeCutPercent(String name) {
        super(name);
        maxLevel = 9;
        setCD(20,0);
        setVal(0.9f, -0.1f);
        showDamageDesc = false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote && handIn == EnumHand.MAIN_HAND)
        {
            Item itemToSet = playerIn.getHeldItemOffhand().getItem();

            float factor = getVal(stack);
            if (itemToSet != Items.AIR && playerIn.getCooldownTracker().getCooldown(itemToSet, 0f) > 0)
            {
                float cd = playerIn.getCooldownTracker().getCooldown(itemToSet, 0f);
                playerIn.getCooldownTracker().setCooldown(itemToSet, (int) (cd * factor));
                activateCoolDown(playerIn, stack);
                return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
            }

        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        String key = stack.getUnlocalizedName() + ".desc";
        if (I18n.hasKey(key))
        {
            String mainDesc = I18n.format(key, 100f - getVal(stack) * 100f);
            return mainDesc;
        }
        return "";
    }
}
