package com.somebody.idlframewok.world.dimension.hexcube;

import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class HexCubeHelper {

    private static final float CHUNK_PER_DIFF = 4f;

    public static float getDifficulty(int chunkX, int y, int chunkZ)
    {
        if (chunkX < 0)
        {
            chunkX = -chunkX;
        }
        if (chunkZ < 0)
        {
            chunkZ = -chunkZ;
        }

        return chunkX / CHUNK_PER_DIFF + chunkZ / CHUNK_PER_DIFF + (y >> 4) / CHUNK_PER_DIFF;
    }

    public static void genGrass(ChunkPrimer primer, int x, int y, int z)
    {
        for (int dx = 1; dx < CHUNK_SIZE; dx++)
        {
            //for (int dy = 0; dy < CHUNK_SIZE; dy++)
            //{
                for (int dz = 1; dz < CHUNK_SIZE; dz++)
                {
                    //BlockPos curPos = new BlockPos(x+dx, y+dy, z+dz);

                    int min = 6;
                    int max = 10;

                    if ((dx > min && dx < max)  ||
                            (dz > min && dz < max))
                    {
                        continue;
                    }
                    primer.setBlockState(x, 1, z, Blocks.GRASS.getDefaultState());
                }
            //}
        }
    }
}
