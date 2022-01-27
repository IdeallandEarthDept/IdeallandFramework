package com.somebody.idlframewok.world.dimension.hexcube.structure;

import com.somebody.idlframewok.blocks.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class GenCubeTeleporterRoom extends GenCubeBase {

    public GenCubeTeleporterRoom(boolean notify) {
        super(notify);
    }

    public GenCubeTeleporterRoom(boolean notify, int xSize, int ySize, int zSize) {
        super(notify, xSize, ySize, zSize);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos positionOrigin) {
        //positionOrigin is 0,0,0 of the room. the corner of walls
        //positionOrigin is 0,0,0 of the room. the corner of walls
        int sideA = 2;
        int sideB = xSize - 2;

        int minZ = 2;
        int maxZ = zSize - 2;

        int yMax = ySize - 1;

        BlockPos[] posList = {
                positionOrigin.add(sideA,1, minZ),
                positionOrigin.add(sideA,1, maxZ),
                positionOrigin.add(sideB,1, minZ),
                positionOrigin.add(sideB,1, maxZ)
        };

        float hasTPChance = 0.02f;
        for (BlockPos chestPos:
                posList) {
            worldIn.setBlockState(chestPos, ModBlocks.GRID_LAMP.getDefaultState(), 2);
            worldIn.setBlockState(chestPos.add(0,1,0), ModBlocks.GRID_LAMP.getDefaultState(), 2);
            if (rand.nextFloat() < hasTPChance)
            {
                worldIn.setBlockState(chestPos.add(0,2,0), ModBlocks.TELEPORTER_DIM_OW.getDefaultState(), 2);
            }
        }

        return false;
    }


}
