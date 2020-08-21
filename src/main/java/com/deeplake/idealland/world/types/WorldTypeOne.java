package com.deeplake.idealland.world.types;

import com.deeplake.idealland.init.InitBiome;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;

public class WorldTypeOne extends WorldType {
    /**
     * Creates a new world type, the ID is hidden and should not be referenced by modders.
     * It will automatically expand the underlying workdType array if there are no IDs left.
     *
     * @param name
     */
    public WorldTypeOne(String name) {
        super(name);
    }

    public WorldTypeOne() {
        super("TYPE_ONE");
    }

    @Override
    public BiomeProvider getBiomeProvider(World world) {
        return new BiomeProviderSingle(InitBiome.BIOME_ONE);
    }
}
