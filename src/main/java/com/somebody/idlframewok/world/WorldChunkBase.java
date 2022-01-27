package com.somebody.idlframewok.world;

import net.minecraft.world.chunk.Chunk;

public abstract class WorldChunkBase {
    public abstract void buildChunk(int x, int z, Chunk chunk);
}
