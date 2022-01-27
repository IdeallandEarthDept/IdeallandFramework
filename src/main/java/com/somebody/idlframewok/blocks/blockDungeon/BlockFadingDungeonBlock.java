package com.somebody.idlframewok.blocks.blockDungeon;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFadingDungeonBlock extends BlockDungeonWall {

    public BlockFadingDungeonBlock(String name, Material material) {
        super(name, material);
        setHardness(-1);
        setLightOpacity(255);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (!worldIn.isRemote && !ModConfig.DEBUG_CONF.STOP_DUNGEON_FADING)
        {
            disenchant(worldIn,pos);
        }
    }
}
