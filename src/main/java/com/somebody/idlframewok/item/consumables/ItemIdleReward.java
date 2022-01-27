package com.somebody.idlframewok.item.consumables;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemIdleReward extends ItemConsumableBase {
    public ItemIdleReward(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onConsume(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return null;
    }
}
