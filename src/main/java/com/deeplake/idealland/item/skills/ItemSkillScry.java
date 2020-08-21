package com.deeplake.idealland.item.skills;

import com.deeplake.idealland.item.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.List;

import static com.deeplake.idealland.util.CommonDef.GUA_TYPES;
import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;

public class ItemSkillScry extends ItemSkillBase {
    public ItemSkillScry(String name) {
        super(name);
        maxLevel = 3;
        showDamageDesc = false;
        setCD(5, 2f);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);


        if (!worldIn.isRemote)
        {
            playerIn.addItemStackToInventory(new ItemStack(ModItems.GUA[playerIn.getRNG().nextInt(GUA_TYPES)]));
            playerIn.addItemStackToInventory(new ItemStack(ModItems.GUA[playerIn.getRNG().nextInt(GUA_TYPES)]));
            worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_NOTE_HARP, SoundCategory.PLAYERS, 1f, 3f);
            activateCoolDown(playerIn, stack);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));

    }
}
