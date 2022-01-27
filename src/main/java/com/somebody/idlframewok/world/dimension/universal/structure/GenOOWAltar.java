package com.somebody.idlframewok.world.dimension.universal.structure;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.chunk.Chunk;

//World Generator cannot be used in build Chunk phase. It must be used in pouplate.
//therefore this uses normal class
public class GenOOWAltar {

    static GenOOWAltar instance;
    public static GenOOWAltar getInstance()
    {
        if (instance== null)
        {
            instance = new GenOOWAltar();
        }
        return instance;
    }

    public IBlockState BASE_BLOCK = ModBlocks.SKYLAND_BLANK_RUNESTONE.getDefaultState();
    public IBlockState WORK_BLOCK = ModBlocks.SKYLAND_OOW_RUNESTONE.getDefaultState();

    public boolean generate(int xC, int zC, int y0, int x0, int z0, Chunk chunk) {

        int radius = 1;
        int pillarHeight = 2;

        for (int x = -radius; x <= radius; x++)
        {
            for (int z = -radius; z <= radius; z++)
            {
                WorldGenUtil.setBlockState(chunk, x0 + x, y0, z0 + z, BASE_BLOCK);
            }
        }

        for (int y = -pillarHeight; y < pillarHeight; y++)
        {
            WorldGenUtil.setBlockState(chunk, x0 , y0 + y, z0, BASE_BLOCK);
        }

        WorldGenUtil.setBlockState(chunk, x0 , y0 + pillarHeight, z0, WORK_BLOCK);

        return true;
    }
}
