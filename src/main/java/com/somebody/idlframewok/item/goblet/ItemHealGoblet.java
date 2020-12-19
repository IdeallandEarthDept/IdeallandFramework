package com.somebody.idlframewok.item.goblet;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class ItemHealGoblet extends ItemGobletBase {
    public ItemHealGoblet(String name) {
        super(name);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (isSelected && !worldIn.isRemote)
        {
            int level = GetLevelFromEXP(GetCacheEXP(stack));

            if (level > 0)
            {
                if (entityIn instanceof EntityLivingBase)
                {
                    ((EntityLivingBase) entityIn).heal((float)level / TICK_PER_SECOND);
                }
            }
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        target.heal(GetLevelFromEXP(GetCacheEXP(stack)));

        playerIn.swingArm(handIn);
        activateCoolDown(playerIn, stack);
        target.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1f, 1f);
        return true;
    }

    //Desc
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        String mainDesc = I18n.format(stack.getUnlocalizedName() + ".desc", GetLevelFromEXP(GetCacheEXP(stack)));
        tooltip.add(mainDesc);
        addInformationLast(stack, world, tooltip, flag);
    }
}
