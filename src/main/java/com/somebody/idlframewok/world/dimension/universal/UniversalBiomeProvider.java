package com.somebody.idlframewok.world.dimension.universal;

import com.somebody.idlframewok.init.InitBiome;
import com.somebody.idlframewok.world.types.layer.GenLayerUniv;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nullable;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class UniversalBiomeProvider extends BiomeProvider {
    public final BiomeCache biomeCacheOpen;
    public GenLayer biomeIndexLayerOpen;
    public GenLayer genBiomesOpen;

    public UniversalBiomeProvider(World world)
    {
        super(world.getWorldInfo());
        this.biomeCacheOpen = new BiomeCache(this);
        getBiomesToSpawnIn().clear();
        for (int i = 0; i < CHUNK_SIZE; i++) {
            getBiomesToSpawnIn().add(InitBiome.BIOME_SKYLANDS[i]);
        }
//        getBiomesToSpawnIn().add(InitBiome.BIOME_ONE);
        getBiomesToSpawnIn().add(InitBiome.BIOME_FLESH);
        getBiomesToSpawnIn().add(InitBiome.BIOME_FIRE_TRAP_TOWER);
//        getBiomesToSpawnIn().add(Biomes.JUNGLE);

        makeLayers(world.getSeed());
    }

    private void makeLayers(long seed) {
        GenLayer biomes = new GenLayerUniv(1L);

        biomes = new GenLayerZoom(1000L, biomes);
        biomes = new GenLayerZoom(1001, biomes);

        //biomes = new GenLayerTFBiomeStabilize(700L, biomes);

        //biomes = new GenLayerTFThornBorder(500L, biomes);

        biomes = new GenLayerZoom(1002, biomes);
        biomes = new GenLayerZoom(1003, biomes);
        biomes = new GenLayerZoom(1004, biomes);
        biomes = new GenLayerZoom(1005, biomes);

        // do "voronoi" zoom
        GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);

        biomes.initWorldGenSeed(seed);
        genlayervoronoizoom.initWorldGenSeed(seed);

        genBiomesOpen = biomes;
        biomeIndexLayerOpen = genlayervoronoizoom;
    }

    public Biome getBiome(BlockPos pos, Biome defaultBiome)
    {
        return this.biomeCacheOpen.getBiome(pos.getX(), pos.getZ(), defaultBiome);
    }

    public void cleanupCache()
    {
        super.cleanupCache();
        this.biomeCacheOpen.cleanupCache();
    }

    /**
     * Gets a list of biomes for the specified blocks.
     */
    public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag)
    {
        IntCache.resetIntCache();

        if (listToReuse == null || listToReuse.length < width * length)
        {
            listToReuse = new Biome[width * length];
        }

        if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0)
        {
            Biome[] abiome = this.biomeCacheOpen.getCachedBiomes(x, z);
            System.arraycopy(abiome, 0, listToReuse, 0, width * length);
            return listToReuse;
        }
        else
        {
            int[] aint = this.biomeIndexLayerOpen.getInts(x, z, width, length);

            for (int i = 0; i < width * length; ++i)
            {
                listToReuse[i] = Biome.getBiome(aint[i], Biomes.DEFAULT);
            }

            return listToReuse;
        }
    }
}
