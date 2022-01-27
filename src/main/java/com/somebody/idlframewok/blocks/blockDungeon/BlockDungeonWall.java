package com.somebody.idlframewok.blocks.blockDungeon;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDungeonWall extends BlockBase {
    public IBlockState disenchanted_state = Blocks.STONEBRICK.getDefaultState();

    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return false;
    }

    public BlockDungeonWall(String name, Material material) {
        super(name, material);
        setHardness(-1);
        setLightOpacity(255);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);
    }

    public BlockDungeonWall setAlternative(IBlockState disenchanted_state)
    {
        this.disenchanted_state = disenchanted_state;
        return this;
    }

    public static void disenchant(World world, BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof BlockDungeonWall)
        {
            world.setBlockState(pos, ((BlockDungeonWall) block).disenchanted_state);
        }
    }
}
