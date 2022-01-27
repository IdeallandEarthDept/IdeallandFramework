package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemUseDemo extends ItemBase {
    public ItemUseDemo(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        world.newExplosion(
                player,
                player.posX,
                player.posY,
                player.posZ,
                3,
                true,
                true);

        return super.onItemRightClick(world, player, hand);
    }
}
