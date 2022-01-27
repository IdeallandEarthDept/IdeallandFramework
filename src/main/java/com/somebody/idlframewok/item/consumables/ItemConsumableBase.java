package com.somebody.idlframewok.item.consumables;

import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class ItemConsumableBase extends ItemBase {
    boolean consumeSelf = true;
    boolean consumeOther = false;
    public ItemConsumableBase(String name) {
        super(name);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        onConsume(worldIn, playerIn, handIn);

        itemstack.shrink(1);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    public abstract ActionResult<ItemStack> onConsume(World worldIn, EntityPlayer playerIn, EnumHand handIn);

}
