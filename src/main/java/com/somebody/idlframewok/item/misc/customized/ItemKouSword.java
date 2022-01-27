package com.somebody.idlframewok.item.misc.customized;

import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.item.ItemSwordBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Collection;

import static net.minecraft.init.Items.GOLD_INGOT;
import static net.minecraftforge.fml.common.registry.ForgeRegistries.ITEMS;

public class ItemKouSword extends ItemSwordBase implements IWIP {
    public ItemKouSword(String name, ToolMaterial material) {
        super(name, material);
        //shiftToShowDesc = true;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (worldIn.isRemote)
        {
            ItemStack stack1 = playerIn.getHeldItemOffhand();
            if (stack1.getItem() == GOLD_INGOT)
            {
                stack1.shrink(1);
                Collection<Item> items = ITEMS.getValuesCollection();
                Item itemType = (Item) items.toArray()[playerIn.getRNG().nextInt(items.size())];
                playerIn.addItemStackToInventory(new ItemStack(itemType));
                stack.damageItem(10, playerIn);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

}
