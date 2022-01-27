package com.somebody.idlframewok.blocks.color16;

import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class BlockTorchCreateRuneStone extends BlockRuneStoneBase {
    IBlockState TORCH = Blocks.TORCH.getDefaultState();
    public BlockTorchCreateRuneStone(String name, Material material) {
        super(name, material);
        setTickRandomly(true);
        setBlockUnbreakable();
        setLightLevel(0.3f);
    }

    @Override
    public int tickRate(World worldIn) {
        return TICK_PER_SECOND;
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(worldIn, pos, state, random);
        if (!worldIn.isRemote)
        //while (random.nextFloat() < 0.1f)
        {
            EnumFacing facing = EnumFacing.values()[1 + random.nextInt(5)];
            WorldGenUtil.setBlockStateIfAir(worldIn, pos.offset(facing), TORCH.withProperty(BlockTorch.FACING, facing));
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
        {
            ItemStack stack = playerIn.getHeldItem(hand);
            if (stack.getItem() == Items.STICK)
            {
                playerIn.setHeldItem(hand, new ItemStack(Blocks.TORCH, stack.getCount()));
            }
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }


}
