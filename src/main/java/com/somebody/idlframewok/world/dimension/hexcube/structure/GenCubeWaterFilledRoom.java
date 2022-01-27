package com.somebody.idlframewok.world.dimension.hexcube.structure;

import com.somebody.idlframewok.blocks.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class GenCubeWaterFilledRoom extends GenCubeBase {

    public GenCubeWaterFilledRoom(boolean notify) {
        super(notify);
    }

    public GenCubeWaterFilledRoom(boolean notify, int xSize, int ySize, int zSize) {
        super(notify, xSize, ySize, zSize);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos positionOrigin) {
        //positionOrigin is 0,0,0 of the room. the corner of walls
        int min = 1;
        int max = xSize - 1;

        int minZ = 1;
        int maxZ = zSize - 1;

        int yMin = 1;
        int yMax = ySize - 1;

        for (int x = min; x <= max; x++)
        {
            for (int z = minZ; z <= maxZ; z++)
            {
                for (int y = yMin; y <= yMax; y++) {
                    BlockPos pos = positionOrigin.add(x, y, z);
                    worldIn.setBlockState(pos, Blocks.WATER.getDefaultState(), 2);
                }
            }
        }

        //repair the lights
        if (hasLight)
        {
            min = 1;
            max = xSize -1;

            minZ = 1;
            maxZ = zSize - 1;

            int minY = 1;
            int maxY = ySize - 1;
            worldIn.setBlockState(positionOrigin.add(min, minY, minZ), ModBlocks.GRID_LAMP.getDefaultState());
            worldIn.setBlockState(positionOrigin.add(min, minY, maxZ), ModBlocks.GRID_LAMP.getDefaultState());
            worldIn.setBlockState(positionOrigin.add(max, minY, minZ), ModBlocks.GRID_LAMP.getDefaultState());
            worldIn.setBlockState(positionOrigin.add(max, minY, maxZ), ModBlocks.GRID_LAMP.getDefaultState());
            worldIn.setBlockState(positionOrigin.add(min, maxY, minZ), ModBlocks.GRID_LAMP.getDefaultState());
            worldIn.setBlockState(positionOrigin.add(min, maxY, maxZ), ModBlocks.GRID_LAMP.getDefaultState());
            worldIn.setBlockState(positionOrigin.add(max, maxY, minZ), ModBlocks.GRID_LAMP.getDefaultState());
            worldIn.setBlockState(positionOrigin.add(max, maxY, maxZ), ModBlocks.GRID_LAMP.getDefaultState());
        }

        return false;
    }


}
