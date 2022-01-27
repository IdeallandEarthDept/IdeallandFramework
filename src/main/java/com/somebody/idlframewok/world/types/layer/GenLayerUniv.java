package com.somebody.idlframewok.world.types.layer;

import com.somebody.idlframewok.init.InitBiome;
import com.somebody.idlframewok.init.ModConfig;
import com.google.common.collect.Lists;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import java.util.List;

import static com.somebody.idlframewok.util.Color16Def.EARTH;
import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class GenLayerUniv extends GenLayer {

    static List<Biome> biomes = Lists.newArrayList();

    public static void initGenLayerUniv()
    {
        if (ModConfig.DEBUG_CONF.DEBUG_MODE)
        {
//            biomes.add(InitBiome.BIOME_FLESH);
            biomes.add(InitBiome.BIOME_SKYLANDS[EARTH]);
            biomes.add(InitBiome.BIOME_FIRE_TRAP_TOWER);
        }
        else {
            for (int i = 0; i < CHUNK_SIZE; i++)
            {
                biomes.add(InitBiome.BIOME_SKYLANDS[i]);
            }
        }

        //biomes.add(InitBiome.BIOME_SKYLANDS[SKY]);
    }

    public GenLayerUniv(long p_i2125_1_) {
        super(p_i2125_1_);
    }

    public GenLayerUniv(long l, GenLayer genlayer) {
        this(l);
        parent = genlayer;
    }


    private Biome getRandomBiome() {
        return biomes.get(nextInt(biomes.size()));
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int result[] = IntCache.getIntCache(areaWidth * areaHeight);

        for (int dz = 0; dz < areaHeight; dz++) {
            for (int dx = 0; dx < areaWidth; dx++) {
                initChunkSeed(dx + areaX, dz + areaWidth);

                result[dx + dz * areaWidth] = Biome.getIdForBiome(getRandomBiome());
            }
        }

//		for (int i = 0; i < areaWidth * areaHeight; i++)
//		{
//			if (result[i] < 0 || result[i] > TFBiomeBase.fireSwamp.biomeID)
//			{
//				System.err.printf("Made a bad ID, %d at %d, %d while generating\n", result[i], x, z);
//			}
//		}

        return result;
    }
}
