package com.somebody.idlframewok.world.dimension.universal.chunk.tower.populate;

import com.somebody.idlframewok.world.dimension.ChunkGenBase;
import com.somebody.idlframewok.world.dimension.universal.chunk.tower.ChunkFlameTrapTower;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class PopulateFlameTrapTower {
    ChunkGenBase chunkGenBase;
    ChunkFlameTrapTower chunkFlameTrapTower;

    public PopulateFlameTrapTower(ChunkGenBase chunkGenBase, ChunkFlameTrapTower chunkFlameTrapTower) {
        this.chunkGenBase = chunkGenBase;
        this.chunkFlameTrapTower = chunkFlameTrapTower;
    }

    public void onPostPopulate(int x, int z, Biome biome) {

        //now the lakes are settled, let's build those towers.
        ChunkFlameTrapTower.ChunkType chunkType = chunkFlameTrapTower.getChunkType(x, z);
        Chunk chunk = chunkGenBase.world.getChunkFromChunkCoords(x, z);

        switch (chunkType) {
            case DOWN_TOWER:
                chunkFlameTrapTower.downTower.buildTower(x, z, chunk);
                break;
            case UP_TOWER:
                chunkFlameTrapTower.upTower.buildTower(x, z, chunk);
                break;
            case NORMAL:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + chunkType);
        }
    }


}
