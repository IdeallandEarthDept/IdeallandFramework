package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.entity.creatures.misc.EntityEternalZombie;
import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSummonEternal extends ItemBase {

    public ItemSummonEternal(String name) {
        super(name);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand handIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote)
        {
            stack.shrink(1);
            EntityEternalZombie zombie = new EntityEternalZombie(worldIn);
            zombie.setPosition(pos.getX(),pos.getY()+1,pos.getZ());
            worldIn.spawnEntity(zombie);
        }else {
            playerIn.playSound(SoundEvents.ENTITY_FIREWORK_LARGE_BLAST, 1f, 1f);
        }

        return super.onItemUse(playerIn, worldIn, pos, handIn, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }
}
