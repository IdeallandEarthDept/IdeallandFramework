package com.somebody.idlframewok.blocks.blockMisc;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.util.ChancePicker;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCrate extends BlockBase {
    public ChancePicker tributeReward = new ChancePicker();

    public BlockCrate(String name, Material material) {
        super(name, material);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
    }


    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;
        drops.add(tributeReward.GetItem(rand));
        if (fortune > 0 && rand.nextInt(10) < fortune)
        {
            //double drop
            drops.add(tributeReward.GetItem(rand));
        }
    }
}
