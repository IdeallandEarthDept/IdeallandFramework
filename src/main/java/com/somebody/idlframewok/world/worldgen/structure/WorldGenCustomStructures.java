package com.somebody.idlframewok.world.worldgen.structure;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.Color16Def;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenCustomStructures implements IWorldGenerator {

//    public static final IDFGenStructure MEK_1 = new IDFGenStructure("mek_go_simple").setyOffset(4);
//    public static final IDFGenStructure BOAT = new IDFGenStructure("aqua/boat_1").setyOffset(-2);
//    public static final IDFGenStructure IRON_BOAT = new IDFGenStructure32("aqua/boat_iron_enemy_1", 2, 2).setyOffset(-3);

    boolean generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, float chance, Block topBlock)
    {
        return generateStructure(generator, world, random, chunkX, chunkZ, chance, topBlock, false);
    }

    boolean generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, float chance, Block topBlock, boolean atCenter)
    {
        return generateStructure(generator, world, random, chunkX, chunkZ, calculateGenerationHeight(world, chunkX << 4, chunkZ << 4, topBlock), chance, atCenter);
    }

    boolean generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int y, float chance, boolean atCenter) {
        if (generator instanceof IDFGenStructure32) {
            if (!((IDFGenStructure32) generator).shouldGen(chunkX, chunkZ)) {
                //prevent overlay
                return false;
            }
        }

        int x = (chunkX << 4);
        int z = (chunkZ << 4);

        if (y < 0) {
            //Idealland.LogWarning("Failed to find y for a structurebig %s", generator);
            return false;
        }

        BlockPos pos = atCenter ? new BlockPos(x + 7, y, z + 7) : new BlockPos(x, y, z);
        if (random.nextFloat() < chance) {
            generator.generate(world, random, pos);
            return true;
        }
        return false;
    }

    static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
    {
        int y = world.getHeight();
        boolean foundGround = false;
        BlockPos pos = new BlockPos(x, y, z);

        if (topBlock == Blocks.AIR) {
            IBlockState topState = world.getBiome(pos).topBlock;
            while (!foundGround && y-- >= 0) {
                foundGround = world.getBlockState(pos) == topState;
            }
        } else {
            while (!foundGround && y-- >= 0) {
                Block block = world.getBlockState(pos).getBlock();
                foundGround = block == topBlock;
            }

            if (!foundGround) {
                return 0;
            }
        }

        if (!foundGround) {
             return -1;
        }

        return y;
    }

    public static float WHATNOT_CHANCE() {
        return ModConfig.WORLD_GEN_CONF.WHATNOT_CHANCE;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int dimID = world.provider.getDimension();

        if (dimID == 0 || dimID == ModConfig.WORLD_GEN_CONF.DIM_UNIV_ID)
        {
            BlockPos pos = new BlockPos(chunkX << 4, 128, chunkZ << 4);
            Biome biome = world.getBiome(pos);

            if (ModConfig.DEBUG_CONF.DEBUG_MODE)
            {
                //generateStructure(DUNGEON_TOWER, world,random,chunkX,chunkZ, 0.001f, Blocks.AIR);
            }

            generateStructure(MEK_1, world, random, chunkX, chunkZ, WHATNOT_CHANCE(), Blocks.SAND, true);
            generateStructure(PILLAR, world, random, chunkX, chunkZ, WHATNOT_CHANCE(), Blocks.GRASS, true);
            generateStructure(FAKE_HOUSE, world, random, chunkX, chunkZ, WHATNOT_CHANCE(), Blocks.GRASS, true);
            generateStructure(MEK_FIREWORKS, world, random, chunkX, chunkZ, WHATNOT_CHANCE(), Blocks.AIR, true);

            switch ((chunkX + chunkZ) % 16)
            {
                case Color16Def.FIRE:
                    generateStructure(GOD_1, world, random, chunkX, chunkZ, WHATNOT_CHANCE(), Blocks.AIR, true);
                    break;
                default:
//                    throw new IllegalStateException("Unexpected value: " + (chunkX + chunkZ) % 16);
            }

            int seaLevel = world.getSeaLevel();
            IDFGenStructure structureNow = RAIL_WAY;
            if (chunkZ % ModConfig.WORLD_GEN_CONF.RAIL_STOP_WAVELENGTH == 0) {
                structureNow = RAIL_STOP;
            }

            if (chunkX == 0) {
                generateStructure(structureNow, world, random, chunkX, chunkZ, seaLevel, 1, false);
            }

            //but will be overlayed by the following.

            if (BiomeManager.oceanBiomes.contains(biome)) {
                generateStructure(BOAT, world, random, chunkX, chunkZ, WHATNOT_CHANCE(), Blocks.WATER, false);
                generateStructure(IRON_BOAT, world, random, chunkX, chunkZ, WHATNOT_CHANCE(), Blocks.WATER, false);
            }
        }
    }
}
