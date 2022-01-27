package com.somebody.idlframewok.item.consumables.keys;

import com.somebody.idlframewok.item.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ItemBlockConverterBase extends ItemBase {
    public ItemBlockConverterBase(String name, int range) {
        super(name);
        this.range = range;
    }

    int range;

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean isNeeded = false;
        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                for (int dz = -range; dz <= range; dz++) {
                    Block block = worldIn.getBlockState(pos).getBlock();
                    boolean result = handleBlock(worldIn, pos, block);

                    if (result) {
                        if (worldIn.isRemote) {
                            //optimization: return earlier
                            return EnumActionResult.SUCCESS;
                        } else {
                            isNeeded = true;
                        }
                    }

                }
            }
        }

        //only when isRemote = false
        if (isNeeded) {
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    public abstract boolean handleBlock(World world, BlockPos pos, Block block);
}