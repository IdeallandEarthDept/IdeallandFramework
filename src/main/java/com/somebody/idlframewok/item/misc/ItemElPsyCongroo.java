package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemElPsyCongroo extends ItemBase {
    public ItemElPsyCongroo(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        CommonFunctions.SafeSendMsgToPlayer(player, "idlframewok.msg.steins_gate" ,world.getSeed());

        return  ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }
}
