package com.somebody.idlframewok.world.dimension.universal.chunk.tower;

import net.minecraft.block.state.IBlockState;

public class GenDownTower extends TowerBase {
    public GenDownTower(int min_floor, int max_floor, IBlockState GROUND, IBlockState WALL) {
        super(min_floor, max_floor, GROUND, WALL);
    }

//    void buildStorey(int x, int z, Chunk chunk, int storey) {
//        int yFrom = storey * heightPerStorey + heightOffset;
//
//        int yTo = yFrom + heightPerStorey;
//        for (int _y = yFrom; _y < yTo; _y++) {
//
//            if (_y == yFrom)
//            {
//                //build floor
//                for (int _x = 0; _x < CHUNK_SIZE; _x++) {
//                    for (int _z = 0; _z < CHUNK_SIZE; _z++) {
//                        WorldGenUtil.setBlockState(chunk, _x, _y, _z, GROUND);
//                    }
//                }
//            } else
//            {
//                //build outside wall
//                for (int _x = 0; _x < CHUNK_SIZE; _x++) {
//                    for (int _z = 0; _z < CHUNK_SIZE; _z++) {
//                        if (isSurrounding(_x, _z))
//                        {
//                            WorldGenUtil.setBlockState(chunk, _x, _y, _z, WALL);
//                        }
//                    }
//                }
//            }
//        }
//    }
}
