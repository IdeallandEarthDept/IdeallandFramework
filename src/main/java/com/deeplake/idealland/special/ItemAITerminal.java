package com.deeplake.idealland.special;

import com.deeplake.idealland.item.ItemBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemAITerminal extends ItemBase {
    public ItemAITerminal(String name) {
        super(name);
        //your Cortana.
        useable = true;
    }

    //todo: auto


    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        //todo: talk hints. like ores are generated on chunks that have not been generated yet.
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        //todo: mark a timestamp of last use.
        //and talk accordingly
        return super.onItemRightClick(world, player, hand);
    }

    public void talkTo(EntityPlayer player, String talkID)
    {

    }

}
