package com.somebody.idlframewok.item.skills;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSkillCloneBlock extends ItemSkillBase {

    public ItemSkillCloneBlock(String name) {
        super(name);
        maxLevel = 12;
        setCD(300, 20);
        showDamageDesc = false;
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        
        if (isStackReady(player, stack)) {
            World world = player.world;
            if (!world.isRemote) {
                IBlockState state = world.getBlockState(pos);

                if (state.getBlock().isAir(state, world, pos))
                {
                    return EnumActionResult.FAIL;
                }
//                if (isCreative && GuiScreen.isCtrlKeyDown() && state.getBlock().hasTileEntity(state))
//                    te = world.getTileEntity(pos);
                ItemStack cloneResult = state.getBlock().getPickBlock(state, null, world, pos, player);
                if (!cloneResult.isEmpty())
                {
                    player.addItemStackToInventory(cloneResult);
                    activateCoolDown(player, stack);
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
