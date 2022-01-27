package com.somebody.idlframewok.init;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMessup extends ItemBase {
    public ItemMessup(String name) {
        super(name);
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        //Write your code here
        if (!worldIn.isRemote) {
            ItemStack firework = new ItemStack(Items.FIREWORKS);

            IDLNBTUtil.SetBoolean(firework, "Flicker", true);

            EntityFireworkRocket rocket =
                    new EntityFireworkRocket(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, firework);

            worldIn.spawnEntity(rocket);
        }

        itemstack.shrink(1);//count -1

        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
