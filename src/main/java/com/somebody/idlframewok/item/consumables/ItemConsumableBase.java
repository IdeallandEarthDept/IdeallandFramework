package com.somebody.idlframewok.item.consumables;

import com.somebody.idlframewok.init.ModCreativeTab;
import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemConsumableBase extends ItemBase {
    boolean consumeSelf = true;
    boolean consumeOther = false;
    public ItemConsumableBase(String name) {
        super(name);
        setCreativeTab(ModCreativeTab.IDL_MISC);
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        itemstack.shrink(1);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
//        EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);
//        ItemStack itemstack1 = playerIn.getItemStackFromSlot(entityequipmentslot);
//
//        if (itemstack1.isEmpty())
//        {
//            playerIn.setItemStackToSlot(entityequipmentslot, itemstack.copy());
//            itemstack.setCount(0);
//            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
//        }
//        else
//        {
//            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
//        }
    }
}
