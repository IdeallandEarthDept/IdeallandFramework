package com.somebody.idlframewok.world.dimension.hexcube.structure;

import net.minecraft.block.BlockLog;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static net.minecraft.init.Blocks.LOG;

public class GenCubeWoodRoom extends GenCubeBase {

    public GenCubeWoodRoom(boolean notify) {
        super(notify);
    }

    public GenCubeWoodRoom(boolean notify, int xSize, int ySize, int zSize) {
        super(notify, xSize, ySize, zSize);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos positionOrigin) {
        //positionOrigin is 0,0,0 of the room. the corner of walls
        int minY = hasLight ? 2 : 1;
        int min = 1;
        int max = xSize - 1;

        int minZ = 1;
        int maxZ = zSize - 1;

        int yMax = ySize - 1;

        for (int y = minY; y <= yMax; y++)
        {
            CreateLogAt(worldIn, positionOrigin.add(min, y, minZ));
            CreateLogAt(worldIn, positionOrigin.add(min, y, maxZ));
            CreateLogAt(worldIn, positionOrigin.add(max, y, minZ));
            CreateLogAt(worldIn, positionOrigin.add(max, y, maxZ));
        }

        return false;
    }

    void CreateLogAt(World worldIn, BlockPos pos)
    {
        this.setBlockAndNotifyAdequately(worldIn, pos, LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y));
    }


}
