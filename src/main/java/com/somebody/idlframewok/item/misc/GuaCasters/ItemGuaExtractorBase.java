package com.somebody.idlframewok.item.misc.GuaCasters;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.item.ModItems;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

//an item that extracts water into Gua2
public class ItemGuaExtractorBase extends ItemBase {
    public ItemGuaExtractorBase(String name) {
        super(name);
    }

    protected void giveItem(EntityPlayer player, IBlockState state)
    {
        player.addItemStackToInventory(new ItemStack(ModItems.GUA[2]));
    }


    protected boolean isValid(IBlockState state)
    {
        Material material = state.getMaterial();

        return (material == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0);
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;

        if (raytraceresult == null)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }
        else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }
        else
        {
            BlockPos blockpos = raytraceresult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockpos))
            {
                return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
            }
            else
            {
                if (!playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
                }
                else
                {
                    IBlockState iblockstate = worldIn.getBlockState(blockpos);
                    if (isValid(iblockstate))
                    {
                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                        giveItem(playerIn, iblockstate);

                        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
                    }
                    else
                    {
                        return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
                    }
                }
            }
        }
    }
}
