package com.somebody.idlframewok.world.dimension.universal.chunk.tower;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.init.InitBiome;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.WorldGenUtil;
import com.somebody.idlframewok.world.WorldChunkBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class ChunkFlameTrapTower extends WorldChunkBase {

    Random rand;

    //need to do this for different biome
    public IBlockState WATER;
    public IBlockState topBlock;
    public IBlockState fillerBlock;

    BiomeProvider provider;
    World world;

    public GenDownTower downTower = new GenDownTower(1, (EARTH_TOP - CHUNK_SIZE) / CHUNK_SIZE,
            ModBlocks.DUNGEON_WALL.getDefaultState(), Blocks.BRICK_BLOCK.getDefaultState());
    public GenDownTower upTower = new GenDownTower(1, (CommonDef.MAX_BUILD_HEIGHT) / CHUNK_SIZE - 1,
            ModBlocks.DUNGEON_WALL.getDefaultState(), ModBlocks.DUNGEON_WALL_CRACKED.getDefaultState());

    static final int CYCLE_COUNT = 8;
    static final int UP_TOWER_INDEX = 4;

    static final int EARTH_TOP = 128;

    static final int BOTTOM_LAYER_END = 15;
    static final int BOTTOM_LAYER_BEGIN = 3;
    //bedrock x 1

    public ChunkFlameTrapTower(World worldIn, Random rand) {
        world = worldIn;
        provider = worldIn.getBiomeProvider();

        WATER = Blocks.WATER.getDefaultState();
        topBlock = InitBiome.BIOME_FIRE_TRAP_TOWER.topBlock;
        fillerBlock = InitBiome.BIOME_FIRE_TRAP_TOWER.fillerBlock;
        this.rand = rand;
    }

    public enum ChunkType {
        DOWN_TOWER,
        UP_TOWER,
        NORMAL
    }

    public ChunkType getChunkType(int x, int z) {
        int xFactor = x % CYCLE_COUNT;
        int zFactor = z % CYCLE_COUNT;
        if (xFactor == zFactor) {
            if (xFactor == 0) {
                return ChunkType.DOWN_TOWER;
            } else if (xFactor == UP_TOWER_INDEX) {
                return ChunkType.UP_TOWER;
            }
        }
        return ChunkType.NORMAL;
    }

    void buildBone(int x, int z, Chunk chunk) {
        for (int _x = 0; _x < CHUNK_SIZE; _x++) {
            for (int _z = 0; _z < CHUNK_SIZE; _z++) {
                for (int _y = EARTH_TOP; _y >= 0; _y--) {
                    WorldGenUtil.setBlockState(chunk, _x, _y, _z, getFillerBlock(x, z, _y, chunk, getChunkType(x, z)));
                }
            }
        }
    }

    IBlockState getFillerBlock(int xC, int zC, int y, Chunk chunk, ChunkType chunkType) {
        if (y >= EARTH_TOP) {
            return WorldGenUtil.AIR;
        } else if (y >= BOTTOM_LAYER_END) {
            return chunkType == ChunkType.NORMAL ? fillerBlock : WorldGenUtil.AIR;
        } else if (y >= BOTTOM_LAYER_BEGIN) {
            return WorldGenUtil.AIR;
        } else if (y > 0) {
            return topBlock;
        } else {
            return WorldGenUtil.BEDROCK;
        }
    }

    @Override
    public void buildChunk(int x, int z, Chunk chunk) {
        if (ModConfig.DEBUG_CONF.DEBUG_MODE) {
            //todo: remove this
            return;
        }
        buildBone(x, z, chunk);

        switch (getChunkType(x, z)) {
            case DOWN_TOWER:
//                downTower.buildTower(x, z, chunk);
                break;
            case UP_TOWER:
//                upTower.buildTower(x, z, chunk);
                break;
            case NORMAL:

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + getChunkType(x, z));
        }
    }

}
