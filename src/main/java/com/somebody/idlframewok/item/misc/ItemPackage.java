package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemPackage extends ItemBase {
    public Item[] validItems = new Item[]{};
    int pick = 1;

    public ItemPackage(String name, Item[] validItems) {
        super(name);
        this.validItems = validItems;
    }

    public ItemPackage(String name, Item[] validItems, int pick) {
        super(name);
        this.validItems = validItems;
        this.pick = pick;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        player.setActiveHand(hand);
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote) {

            player.addItemStackToInventory(new ItemStack(validItems[player.getRNG().nextInt(validItems.length)]));
            player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            player.addExperience(10);

            //	Must do shrink AFTER addItemStackToInventory,
            //or it would make the addItemStackToInventory fail if the new thing were to be in the new place.
            //	Try do this when helding one sealed weapon in slot 1, and something else in slot 2.
            stack.shrink(1);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return EnumActionResult.PASS;
    }
}
