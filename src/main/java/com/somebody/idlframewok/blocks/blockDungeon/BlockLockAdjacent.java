package com.somebody.idlframewok.blocks.blockDungeon;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.HashMap;

public class BlockLockAdjacent extends BlockLockBase {
    public BlockLockAdjacent(String name, Material material, boolean isOn) {
        super(name, material, isOn);
        setLightOpacity(255);
    }

    static HashMap<Vec3i, Boolean> checked ;

    @Override
    public boolean isOk(World world, BlockPos pos)
    {
        Vec3i vec = new Vec3i(pos.getX(),pos.getY(),pos.getZ());
        if (checked.containsKey(vec))
        {
            return true;
        }
        else {
            checked.put(vec, true);
        }

        if (isOn)
        {
            for (EnumFacing facing1 : EnumFacing.values())
            {
                BlockPos pos1 = pos.offset(facing1);
                IBlockState stateAdj = world.getBlockState(pos1);
                if (stateAdj.getBlock() instanceof BlockLockAdjacent)
                {
                    if (!((BlockLockAdjacent) stateAdj.getBlock()).isOk(world, pos1))
                    {
                        //Idealland.Log("%s checking : pos = %s is false", facing1, pos1);
                        return false;
                    }
                }
                else {
                    continue;
                }
            }

            //Idealland.Log("checking : pos = %s is true",pos);
            return true;
        }
        else {
            //Idealland.Log("checking : pos = %s is false",pos);
            return false;
        }
    }

    public void toggleSelfAndAdjcacent(World worldIn, BlockPos pos)
    {
        for (EnumFacing facing1 : EnumFacing.values())
        {
            BlockPos pos1 = pos.offset(facing1);
            IBlockState stateAdj = worldIn.getBlockState(pos1);
            if (stateAdj.getBlock() instanceof BlockLockAdjacent)
            {
                //invert all direct adjcacent blocks
                ((BlockLockAdjacent)stateAdj.getBlock()).invertStatus(worldIn, pos1);
            }
        }
        invertStatus(worldIn, pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
        {
            toggleSelfAndAdjcacent(worldIn, pos);

            //check if all adjacent blocks are unlocked
            checked = new HashMap<>();
            boolean result = alternative.isOk(worldIn, pos);
            if (result)
            {
                int count = 0;
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                for (Vec3i vec: checked.keySet()) {
                    mutableBlockPos.setPos(vec);
                    unlock(worldIn, mutableBlockPos);
                    count++;
                }
                playerIn.addExperience(count);
                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 2F, 1f);
            }
            else {
                worldIn.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundCategory.BLOCKS, 1F,  isOn ? 1f : 2f);
            }
            //Idealland.Log("Check result: %s", result);
            checked.clear();

            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        }
        else {
            return true;
        }
    }
}
