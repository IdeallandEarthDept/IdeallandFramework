package com.somebody.idlframewok.world.dimension.universal;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.designs.god16.God16Base;
import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.WorldGenUtil;
import com.somebody.idlframewok.world.biome.BiomeFlesh;
import com.somebody.idlframewok.world.biome.godlands.BiomeFlameTrapTower;
import com.somebody.idlframewok.world.biome.godlands.skylands.BiomeSkylandBase;
import com.somebody.idlframewok.world.dimension.ChunkGenBase;
import com.somebody.idlframewok.world.dimension.universal.chunk.ChunkFleshLand;
import com.somebody.idlframewok.world.dimension.universal.chunk.ChunkZendikarSkylands;
import com.somebody.idlframewok.world.dimension.universal.chunk.tower.ChunkFlameTrapTower;
import com.somebody.idlframewok.world.dimension.universal.chunk.tower.populate.PopulateFlameTrapTower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;
import static com.somebody.idlframewok.util.CommonDef.MAX_BUILD_HEIGHT;

public class ChunkGenUniversal extends ChunkGenBase {

    ChunkZendikarSkylands chunkZendikarSkylands;
    ChunkFleshLand chunkFleshLand;
    ChunkFlameTrapTower chunkFlameTrapTower;
    PopulateFlameTrapTower populateFlameTrapTower;
    public static final int sky_depth = 1;

    public ChunkGenUniversal(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions) {
        super(worldIn, seed, mapFeaturesEnabledIn, generatorOptions);
        worldIn.setSeaLevel(127);
        chunkZendikarSkylands = new ChunkZendikarSkylands(worldIn, rand);
        chunkFleshLand = new ChunkFleshLand(worldIn, rand);
        chunkFlameTrapTower = new ChunkFlameTrapTower(worldIn, rand);
        populateFlameTrapTower = new PopulateFlameTrapTower(this, chunkFlameTrapTower);
    }

    @Override
    public void setBiomeArray(Chunk chunk, World world, int x, int z) {
        byte[] abyte = chunk.getBiomeArray();

        BlockPos ref = WorldGenUtil.blockPosFromXZ(x, z);
        byte biome = (byte)Biome.getIdForBiome(world.getBiomeForCoordsBody(ref));
        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = biome;
        }
    }

    @Override
    public void buildChunk(int x, int z, Chunk chunk) {
        int xAbs = x * CHUNK_SIZE;
        int zAbs = z * CHUNK_SIZE;

        Biome biome = world.getBiome(new BlockPos(xAbs+7, 128, zAbs+7));

        if (biome instanceof BiomeSkylandBase)
        {
            chunkZendikarSkylands.buildChunk(x, z, chunk);
            tryBuildAltar(x,z,chunk,((BiomeSkylandBase) biome).index);
        }
        else if (biome instanceof BiomeFlesh)
        {
            chunkFleshLand.buildChunk(x, z, chunk);
        } else if (biome instanceof BiomeFlameTrapTower) {
            chunkFlameTrapTower.buildChunk(x, z, chunk);
        }

        super.buildChunk(x, z, chunk);
        buildNightSky(chunk);
    }

    @Override
    public void postVanillaPopulate(int x, int z, Biome biome) {
        super.postVanillaPopulate(x, z, biome);
//        if (biome instanceof BiomeFlameTrapTower) {
//            populateFlameTrapTower.onPostPopulate(x, z, biome);
//        }
    }

    void tryBuildAltar(int x, int z, Chunk chunk, int index)
    {
        if (x % CHUNK_SIZE ==0 && z % CHUNK_SIZE == 0)
        {
            int y = InitUnivGen.GEN_GOD_ALTAR[index].getRandomProperY(rand, chunk);
            InitUnivGen.GEN_GOD_ALTAR[index].generate(y, chunk);

            God16Base god = Init16Gods.GODS[index];
            //build constellation
            int min_sky_height = MAX_BUILD_HEIGHT - sky_depth + 1;
            for (int x1 = 0; x1 < CHUNK_SIZE; x1++)
            {
                for (int z1 = 0; z1 < CHUNK_SIZE; z1++)
                {
                    if (god.symbolMap[x1][z1])
                    {
                        WorldGenUtil.setBlockState(chunk,
                               x1,
                                min_sky_height + rand.nextInt(sky_depth),
                                z1,
                                ModBlocks.SKY_STARRY_BLOCK.getDefaultState());
                    }
                    else {
                        WorldGenUtil.setBlockState(chunk,
                                x1,
                                min_sky_height + rand.nextInt(sky_depth),
                                z1,
                                ModBlocks.SKY_NIGHT_BLOCK.getDefaultState());
                    }
                }
            }
        }
    }

    void buildNightSky(Chunk chunk)
    {
        int min_sky_height = MAX_BUILD_HEIGHT - sky_depth + 1;
        for (int y = MAX_BUILD_HEIGHT; y >= min_sky_height; y--) {
            for (int x1 = 0; x1 < CHUNK_SIZE; x1++) {
                for (int z1 = 0; z1 < CHUNK_SIZE; z1++) {
                    WorldGenUtil.setBlockState(chunk, x1, y, z1, getNightSky(chunk));
                }
            }
        }

        int starCount = ModConfig.WORLD_GEN_CONF.MAX_STAR_PER_CHUNK;
        for (int i = 0; i < starCount; i++)
        {
            WorldGenUtil.setBlockState(chunk,
                    rand.nextInt(CHUNK_SIZE),
                    min_sky_height + rand.nextInt(sky_depth),
                    rand.nextInt(CHUNK_SIZE),
                    ModBlocks.STAR_BLOCK.getDefaultState());
        }
    }


    IBlockState getNightSky(Chunk chunk)
    {
        if (rand.nextFloat() < 0.05f)
        {
            return ModBlocks.SKY_STARRY_BLOCK.getDefaultState();
        }
        else{
            return ModBlocks.SKY_NIGHT_BLOCK.getDefaultState();
        }
    }

    //    @Override
//    public void buildChunkPrimier(int x, int z, ChunkPrimer primer) {
//
//    }
}
