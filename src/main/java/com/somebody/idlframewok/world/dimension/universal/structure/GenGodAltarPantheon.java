package com.somebody.idlframewok.world.dimension.universal.structure;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBanner;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_MAX;
import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class GenGodAltarPantheon extends GenGodAltarBase{

    public GenGodAltarPantheon(int index) {
        super(index);
        GROUND = ModBlocks.PANTHEON_LOOP.getDefaultState();
        WALL = ModBlocks.PANTHEON_CHECKER.getDefaultState();
        //GOD_RUNE = ModBlocks.SKYLAND_GOD_RUNSTONES[index].getDefaultState();
        GLASS = Blocks.GLASS.getDefaultState();
    }

    public int getRandomProperY(Random rand, Chunk chunk)
    {
        return  128;
    }

    @Override
    public void genSpecial(int y0, Chunk chunk) {
        int y1 = y0 + 1;
        for (int i = 0; i < CHUNK_SIZE; i++)
        {
            WorldGenUtil.setBlockState(chunk, floorBegin + (i & 3), y0 + 1 + i / 4, mainRuneZ, ModBlocks.SKYLAND_GOD_RUNSTONES[i].getDefaultState());
            WorldGenUtil.setBlockState(chunk, 0, y0, i, ModBlocks.PANTHEON_VORTEX.getDefaultState());
            WorldGenUtil.setBlockState(chunk, CHUNK_MAX, y0, i, ModBlocks.PANTHEON_VORTEX.getDefaultState());
            buildFlag(y1, chunk, 0,  i, EnumFacing.SOUTH, i);
            buildFlag(y1, chunk, CHUNK_MAX,  i, EnumFacing.NORTH, i);
        }
    }

    private void buildFlag(int y, Chunk chunk, int x, int z, EnumFacing facing, int i) {
        IBlockState flag = Blocks.STANDING_BANNER.getDefaultState();

        WorldGenUtil.setBlockState(chunk, x, y, z, flag.withProperty(BlockStandingSign.ROTATION, i));

        TileEntity tileentity = chunk.getTileEntity(new BlockPos(x,y,z), Chunk.EnumCreateEntityType.CHECK);
        if (tileentity instanceof TileEntityBanner)
        {
            Idealland.LogWarning("Dye banner successful");
            ((TileEntityBanner) tileentity).setItemValues(ItemBanner.makeBanner(EnumDyeColor.byDyeDamage(i), (NBTTagList) null), false);
        }
        else {
            Idealland.LogWarning("Banner is: ", tileentity);
        }
    }

}
