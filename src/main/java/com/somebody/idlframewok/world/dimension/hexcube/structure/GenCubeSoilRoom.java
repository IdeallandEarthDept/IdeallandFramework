package com.somebody.idlframewok.world.dimension.hexcube.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class GenCubeSoilRoom extends GenCubeBase {

    public GenCubeSoilRoom(boolean notify) {
        super(notify);
    }

    public GenCubeSoilRoom(boolean notify, int xSize, int ySize, int zSize) {
        super(notify, xSize, ySize, zSize);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos positionOrigin) {
        //positionOrigin is 0,0,0 of the room. the corner of walls
        int min = 1;
        int max = xSize - 1;

        int minZ = 1;
        int maxZ = zSize - 1;

        int yMax = ySize - 1;

        //with chance the grass can grow
        boolean isGrass = hasDoorX && !hasDoorY && hasDoorZ;

        for (int x = min; x <= max; x++)
        {
            for (int z = minZ; z <= maxZ; z++)
            {
                BlockPos pos = positionOrigin.add(x, 1, z);
                if ((x > min && x < max) || (z > minZ && z < maxZ))
                {
                    if (worldIn.getBlockState(pos).getBlock() != Blocks.AIR) {
                        //if there is a light placed, keep it
                        continue;
                    }
                }

                worldIn.setBlockState(pos, isGrass ? Blocks.GRASS.getDefaultState() : Blocks.DIRT.getDefaultState(), 2);
            }
        }

        return false;
    }


}
