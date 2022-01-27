package com.somebody.idlframewok.world.dimension.dungeon;

import com.somebody.idlframewok.init.InitDimension;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.world.dimension.universal.UniversalBiomeProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class DimInfDungeon extends WorldProvider {
    public DimInfDungeon(){
        setDimension(ModConfig.WORLD_GEN_CONF.DIM_DEBUG_ID);
    }

    @Override
    public void init() {
        NBTTagCompound data = InitDimension.getDimensionData(world);
        //seed = data.hasKey(SEED_KEY, Constants.NBT.TAG_LONG) ? data.getLong(SEED_KEY) : loadSeed();
        //hasSkyLight = isSkylightEnabled(data);
        biomeProvider = new UniversalBiomeProvider(world);
        hasSkyLight = true;

    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGenInfDungeon(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), world.getWorldInfo().getGeneratorOptions());
    }

    //----------trivial
    @Override
    public int getAverageGroundLevel() {
        return 128;
    }

    @Override
    public boolean canRespawnHere() {
        // lie about this until the world is initialized
        // otherwise the server will try to generate enough terrain for a spawn point and that's annoying
        return world.getWorldInfo().isInitialized();
    }

    @Override
    public DimensionType getDimensionType() {
        return InitDimension.DIM_DEBUG;
    }
}
