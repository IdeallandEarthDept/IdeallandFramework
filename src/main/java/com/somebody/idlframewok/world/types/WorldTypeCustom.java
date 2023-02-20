package com.somebody.idlframewok.world.types;

import com.somebody.idlframewok.world.types.layer.GenLayerCustom;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;

public class WorldTypeCustom extends WorldType {
    /**
     * Creates a new world type, the ID is hidden and should not be referenced by modders.
     * It will automatically expand the underlying workdType array if there are no IDs left.
     *
     * @param name
     */
    public WorldTypeCustom(String name) {
        super(name);
    }

    @Override
    public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer, ChunkGeneratorSettings chunkSettings) {
        return new GenLayerCustom(worldSeed, parentLayer, this, chunkSettings);
    }
}
