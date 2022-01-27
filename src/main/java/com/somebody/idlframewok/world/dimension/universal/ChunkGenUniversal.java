package com.somebody.idlframewok.world.dimension.universal;

import com.somebody.idlframewok.util.WorldGenUtil;
import com.somebody.idlframewok.world.biome.BiomeFlesh;
import com.somebody.idlframewok.world.dimension.ChunkGenBase;
import com.somebody.idlframewok.world.dimension.universal.chunk.ChunkFleshLand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class ChunkGenUniversal extends ChunkGenBase {

    ChunkFleshLand chunkFleshLand;
    public static final int sky_depth = 1;

    public ChunkGenUniversal(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions) {
        super(worldIn, seed, mapFeaturesEnabledIn, generatorOptions);
        worldIn.setSeaLevel(127);
        chunkFleshLand = new ChunkFleshLand(worldIn, rand);
//        populateFlameTrapTower = new PopulateFlameTrapTower(this, chunkFlameTrapTower);
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

        if (biome instanceof BiomeFlesh)
        {
            chunkFleshLand.buildChunk(x, z, chunk);
        }

        super.buildChunk(x, z, chunk);
    }

    @Override
    public void postVanillaPopulate(int x, int z, Biome biome) {
        super.postVanillaPopulate(x, z, biome);
//        if (biome instanceof BiomeFlameTrapTower) {
//            populateFlameTrapTower.onPostPopulate(x, z, biome);
//        }
    }
}
