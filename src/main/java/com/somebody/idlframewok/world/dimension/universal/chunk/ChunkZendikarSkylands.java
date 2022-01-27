package com.somebody.idlframewok.world.dimension.universal.chunk;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.init.InitBiome;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.WorldGenUtil;
import com.somebody.idlframewok.world.WorldChunkBase;
import com.somebody.idlframewok.world.biome.godlands.skylands.BiomeSkylandBase;
import com.somebody.idlframewok.world.dimension.universal.structure.GenOOWAltar;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.*;

public class ChunkZendikarSkylands extends WorldChunkBase {

    IBlockState DEAD_BUSH = Blocks.DEADBUSH.getDefaultState();
    BlockBush bushBlock = (net.minecraft.block.BlockBush) DEAD_BUSH.getBlock();
    IBlockState SAPLING = Blocks.SAPLING.getDefaultState();
    BlockSapling saplingBlock = (BlockSapling) Blocks.SAPLING;

    IBlockState TALLGRASS = Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);
    //BlockSapling saplingBlock = (BlockSapling) Blocks.SAPLING;

    IBlockState PEBBLE = ModBlocks.SKYLAND_PEBBLE.getDefaultState();
    IBlockState GRASS = Blocks.GRASS.getDefaultState();
    IBlockState BOX = ModBlocks.CRATE.getDefaultState();
    IBlockState TORCH_STONE = ModBlocks.TORCH_RUNE.getDefaultState();
    IBlockState FALL_RUNE = ModBlocks.SKYLAND_FALL_RUNESTONE.getDefaultState();
    IBlockState BLANK_RUNE = ModBlocks.SKYLAND_BLANK_RUNESTONE.getDefaultState();
    IBlockState REVIVE_RUNE = ModBlocks.REVIVE_STONE.getDefaultState();
    //water
    IBlockState SEA_STONE = Blocks.PRISMARINE.getDefaultState();
    IBlockState SEA_LANTERN = Blocks.SEA_LANTERN.getDefaultState();
    IBlockState MOSS_STONE = Blocks.MOSSY_COBBLESTONE.getDefaultState();

    BiomeSkylandBase WATER_ISLAND;

    int connectDiffFromEdge = 7;//[0,15]

    final int seaLevel = 127;
    //int[] connectX = {16, 128};
    //int[] connectZ = {20, 124};
    final int[] standardY = {//32,
            //64,
            //96,
            seaLevel,
            //160,
            //192,
            };//224

    final int xPlus = 2;//connect Y height offset
    final int zPlus = -3;//connect Y height offset

    final int minLandY = 32;
    final int maxLandY = 200;//must not add to 255. will overflow when creating surface features.


    Random rand;

    //need to do this for different biome

    public IBlockState blockConnectX, blockConnectZ;
    public IBlockState fillerBlock;
    public IBlockState topBlock;

    BiomeProvider provider;
    World world;

    public ChunkZendikarSkylands(World worldIn, Random rand) {
        world = worldIn;
        provider = worldIn.getBiomeProvider();
        blockConnectX = Blocks.ACACIA_FENCE.getDefaultState().withRotation(Rotation.CLOCKWISE_90);
        blockConnectZ = Blocks.ACACIA_FENCE.getDefaultState();
        fillerBlock = Blocks.STONE.getDefaultState();
        topBlock = Blocks.GRASS.getDefaultState();
        WATER_ISLAND = InitBiome.BIOME_SKYLANDS[Color16Def.WATER];
        this.rand = rand;
    }

    @Override
    public void buildChunk(int x, int z, Chunk chunk) {
        //Idealland.Log("Building chunk: %d,%d", x, z);
        boolean hasBedrock = false;
        int parse = ModConfig.DEBUG_CONF.SKY_LAND_PARSE;

        Biome mainBiome = chunk.getBiome(new BlockPos(7,7,7), provider);
        if (mainBiome instanceof BiomeSkylandBase)
        {
            BiomeSkylandBase biomeSkylandBase = (BiomeSkylandBase) mainBiome;
            if (biomeSkylandBase.index == Color16Def.WATER)
            {
                createWaterWall(chunk);
                createOcean(chunk);
            }

            if (biomeSkylandBase.index == Color16Def.EARTH)
            {
                hasBedrock = true;
            }
        }

        if (!hasBedrock)
        {
            //GROUND
            for (int x1 = 0; x1 < CHUNK_SIZE; x1++)
            {
                for (int z1 = 0; z1 < CHUNK_SIZE; z1++) {
                    WorldGenUtil.setBlockState(chunk, x1, 0, z1, Blocks.AIR.getDefaultState());
                }
            }
        }

        double rate = ModConfig.WORLD_GEN_CONF.LAND_CHANCE;

        boolean[] isLand = new boolean[standardY.length];
        int life = standardY.length;
        if ((x % parse == 0) && (z % parse == 0)) {
            while (rand.nextDouble() < rate && life > 0) {
                isLand[rand.nextInt(standardY.length)] = true;
                life--;//prevent deadloop;
            }
        }

        for (int i = 0; i < isLand.length; i++) {
            int y = standardY[i];

            if (isLand[i]) {
                buildChunkIsland(x,z, standardY[i], isLand, chunk);
            } else {
                if (z % parse == 0) {
                    //a connectionsMicro
                    y = standardY[i] + xPlus;
                    for (int x1 = 0; x1 < CHUNK_SIZE; x1++) {
                        if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_CONNECTION) {
                            WorldGenUtil.setBlockState(chunk, x1, y, connectDiffFromEdge, blockConnectX);
                        }
                        //todo: add some fancy
                    }
                }

                if (x % parse == 0)
                {
                    y = standardY[i] + zPlus;
                    for (int x1 = 0; x1 < CHUNK_SIZE; x1++)
                    {
                        if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_CONNECTION)
                        {
                            WorldGenUtil.setBlockState(chunk, connectDiffFromEdge, y, x1, blockConnectZ);
                        }
                        //todo: add some fancy
                    }
                }
            }
        }

        if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.LAND_CHANCE_EXTRA)
        {
            buildChunkIsland(x,z, minLandY + rand.nextInt(maxLandY), isLand, chunk);
        }

        for (int i = 0; i < ModConfig.WORLD_GEN_CONF.LAND_CHANCE_SMALL_ATTEMPT; i++)
        {
            if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.LAND_CHANCE_SMALL)
            {
                buildSmallIsland(x,z, minLandY + rand.nextInt(maxLandY), rand.nextInt(CHUNK_SIZE), rand.nextInt(CHUNK_SIZE), chunk);
            }
        }

        if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.LAND_CHANCE_FULL_HELIX)
        {
            buildSmallIslandHelixFull(x, z, chunk);
        }

        //seems causes crashes? or no?
        //chunk.enqueueRelightChecks();
    }

    private void createWaterWall(Chunk chunk) {
        //water wall
        if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.LAND_CHANCE_WATER_WALL)
        {
            for (int k = 0; k < CHUNK_SIZE; k++)
            {
                for (int y = 0; y < MAX_BUILD_HEIGHT; y++)
                {
                    WorldGenUtil.setBlockState(chunk, 0, y, k, Blocks.WATER.getDefaultState());
                    WorldGenUtil.setBlockState(chunk, CHUNK_MAX, y, k, Blocks.WATER.getDefaultState());
                    WorldGenUtil.setBlockState(chunk, k, y, 0, Blocks.WATER.getDefaultState());
                    WorldGenUtil.setBlockState(chunk, k, y, CHUNK_MAX, Blocks.WATER.getDefaultState());
                }
            }
        }
    }

    private void createOcean(Chunk chunk) {
        //ocean
        for (int x1 = 0; x1 < CHUNK_SIZE; x1++) {
            for (int z1 = 0; z1 < CHUNK_SIZE; z1++) {
                for (int y = 0; y < seaLevel; y++)
                {
                    WorldGenUtil.setBlockState(chunk, x1, y, z1, Blocks.WATER.getDefaultState());
                }
            }
        }
    }

    void buildSmallIslandHelixFull(int xC, int zC,  Chunk chunk) {

        float theta = rand.nextFloat() * 6.262f;
        float omegaY = 0.1f;

        int lastY = minLandY;
        for (int y1 = minLandY; y1 <= maxLandY;)
        {
            int x1 = 4 + rand.nextInt(2);
            //int z1 = 5 + rand.nextInt(3);

            buildSmallIsland(xC, zC, y1, (int)(x1 * Math.cos(theta) + CHUNK_CENTER), (int)(x1 * Math.sin(theta) + CHUNK_CENTER), chunk, 2);

            int deltaY = ModConfig.DEBUG_CONF.HELLX_DELTA_Y_MIN + rand.nextInt(ModConfig.DEBUG_CONF.HELLX_DELTA_Y_MAX + 1);
            theta += omegaY * deltaY;
            lastY = y1;
            y1 += deltaY;

        }

        GenOOWAltar.getInstance().generate(xC,zC,lastY, CHUNK_CENTER_INT, CHUNK_CENTER_INT, chunk);
    }

    IBlockState getFillerBlock(int xC, int zC, int y, boolean isLand,  Chunk chunk)
    {
        return fillerBlock;
    }

    IBlockState getFillerBlockTop(int xC, int zC, int y, boolean isLand,  Chunk chunk)
    {
        return topBlock;
    }

    void buildSmallIsland(int xC, int zC, int y, int x, int z, Chunk chunk) {
        buildSmallIsland(xC, zC, y, x, z, chunk, CHUNK_SIZE);
    }

    void buildSmallIsland(int xC, int zC, int y, int x, int z, Chunk chunk, int maxRadius) {
        if (x < 0 || z < 0 || x >= CHUNK_SIZE || z >= CHUNK_SIZE)
        {
            return;
        }

        int xSpare = Math.min(x, CHUNK_SIZE - x - 1);
        int zSpare = Math.min(z, CHUNK_SIZE - z - 1);
        int xzSpare = Math.max(Math.min(xSpare, zSpare), 0);
        int radiusXZ = Math.min(maxRadius, (rand.nextInt(xzSpare + 1) + rand.nextInt(xzSpare + 1))/2);

        //todo: find out when radius = 0
        int height = radiusXZ + rand.nextInt(radiusXZ + 1);
        int curRadius = radiusXZ;

        for (int y1 = y; y1 > y - height; y1--) {
            if (y1 > MAX_BUILD_HEIGHT || y1 < 0)
            {
                continue;
            }
            if (curRadius >= 0)
            {
                curRadius--;
            }
            for (int x1 = x - curRadius; x1 <= x + curRadius; x1++) {
                for (int z1 = z - curRadius; z1 <= z + curRadius; z1++) {
                    WorldGenUtil.setBlockState(chunk, x1, y1, z1, getFillerBlock(xC, zC, y1, false, chunk));
                }
            }
        }

        for (int x1 = x - radiusXZ; x1 <= x + radiusXZ; x1++) {
            for (int z1 = z - radiusXZ; z1 <= z + radiusXZ; z1++) {
                WorldGenUtil.setBlockState(chunk, x1, y, z1, getFillerBlockTop(xC, zC, y, false, chunk));
                createSurfaceMisc(chunk, x1, y+1, z1);
            }
        }
    }

    int getSmallerInvRadius(int max)
    {
        int result = rand.nextInt(rand.nextInt(rand.nextInt(max) + 1) + 1);
        return result;
    }

    void buildChunkIsland(int x, int z, int y, boolean[] isLand,  Chunk chunk) {
        //an island
        //WorldGenUtil.setBlockState(chunk, 0, y, connectDiffFromEdge, WATER);
        //WorldGenUtil.setBlockState(chunk, CHUNK_SIZE - 1, y, connectDiffFromEdge, WATER);
        //y = connectZ[i];
        //WorldGenUtil.setBlockState(chunk, connectDiffFromEdge, y, 0, WATER);
        //WorldGenUtil.setBlockState(chunk, connectDiffFromEdge, y, CHUNK_SIZE - 1, WATER);
        int sizeUpY = 32, sizeDownY = 32;
        int yMax = y + sizeUpY -1;
        int radiusInvMax = 8;

        int[] startRadius = {
                        getSmallerInvRadius(radiusInvMax),
                        getSmallerInvRadius(radiusInvMax),
                        getSmallerInvRadius(radiusInvMax),
                        getSmallerInvRadius(radiusInvMax)};//will not equal this

        int radiusInvX1 = startRadius[0];
        int radiusInvX2 = startRadius[1];
        int radiusInvZ1 = startRadius[2];
        int radiusInvZ2 = startRadius[3];

        int baseRadiusInv = radiusInvX1;

        float increaseChance = ModConfig.DEBUG_CONF.SKY_LAND_INC_CHANCE;
        float decreaseChance = ModConfig.DEBUG_CONF.SKY_LAND_DEC_CHANCE;//should be different for different biome

        int maxStrech = 3;

        //straight up, only once. the height need reconsidering to be symmetric
        int extraHeight = rand.nextInt(maxStrech) + 1;
        for (int y1 = y; y1 > y - extraHeight; y1--) {
            for (int x1 = radiusInvX1; x1 < CHUNK_SIZE - radiusInvX2; x1++) {
                for (int z1 = radiusInvZ1; z1 < CHUNK_SIZE - radiusInvZ2; z1++) {
                    WorldGenUtil.setBlockState(chunk, x1, y1, z1, fillerBlock);
                }
            }
        }

        for (int y1 = y - extraHeight ; y1 >= y - sizeDownY; y1--) {
            //bottom half
            radiusInvX1 += rand.nextFloat() < increaseChance ? 1 : 0;
            radiusInvX2 += rand.nextFloat() < increaseChance ? 1 : 0;
            radiusInvZ1 += rand.nextFloat() < increaseChance ? 1 : 0;
            radiusInvZ2 += rand.nextFloat() < increaseChance ? 1 : 0;


            if (radiusInvX1 + radiusInvX2 >= CHUNK_SIZE ||
                    radiusInvZ1 + radiusInvZ2 >= CHUNK_SIZE
            )
            {
                break;
            }

            for (int x1 = radiusInvX1; x1 < CHUNK_SIZE - radiusInvX2; x1++) {
                for (int z1 = radiusInvZ1; z1 < CHUNK_SIZE - radiusInvZ2; z1++) {
                    WorldGenUtil.setBlockState(chunk, x1, y1, z1, fillerBlock);
                }
            }
        }

        radiusInvX1 = startRadius[0];
        radiusInvX2 = startRadius[1];
        radiusInvZ1 = startRadius[2];
        radiusInvZ2 = startRadius[3];

        //straight up, only once. the height need reconsidering to be symmetric
        //int extraHeight = (rand.nextInt(sizeUpY) + 1) / 2;
        for (int y1 = y+1; y1 < y + extraHeight; y1++) {
            for (int x1 = radiusInvX1; x1 < CHUNK_SIZE - radiusInvX2; x1++) {
                for (int z1 = radiusInvZ1; z1 < CHUNK_SIZE - radiusInvZ2; z1++) {
                    WorldGenUtil.setBlockState(chunk, x1, y1, z1, fillerBlock);
                }
            }
        }

        //slow ramp
        for (int y1 = y+extraHeight; y1 < y + sizeUpY && y1 <= MAX_BUILD_HEIGHT; y1++) {

            //the top half
            int deltaX = (rand.nextInt(CHUNK_SIZE - radiusInvX1 - radiusInvX2)) / 2 + 1;
            int deltaZ = (rand.nextInt(CHUNK_SIZE - radiusInvZ1 - radiusInvZ2)) / 2 + 1;
            int lastY1 = y1 - 1;
            for (int x1 = radiusInvX1; x1 < CHUNK_SIZE - radiusInvX2; x1++) {
                for (int z1 = radiusInvZ1; z1 < CHUNK_SIZE - radiusInvZ2; z1++) {
                    boolean isBorder = x1 < radiusInvX1 + deltaX ||
                            x1 >= CHUNK_SIZE - radiusInvX2 - deltaX ||
                            z1 < radiusInvZ1 + deltaZ ||
                            z1 >= CHUNK_SIZE - radiusInvZ2 - deltaZ;

                    if (isBorder)
                    {
                        //outside border
                        WorldGenUtil.setBlockState(chunk, x1, lastY1, z1, topBlock);
                        createSurfaceMisc(chunk, x1, lastY1+1, z1);
                    }
                }
            }

            radiusInvX1 += deltaX;
            radiusInvX2 += deltaX;
            radiusInvZ1 += deltaZ;
            radiusInvZ2 += deltaZ;

            if (radiusInvX1 + radiusInvX2 >= CHUNK_SIZE ||
                    radiusInvZ1 + radiusInvZ2 >= CHUNK_SIZE
            )
            {
                break;
            }

            for (int x1 = radiusInvX1; x1 < CHUNK_SIZE - radiusInvX2; x1++) {
                for (int z1 = radiusInvZ1; z1 < CHUNK_SIZE - radiusInvZ2; z1++) {

                    if (y1 == yMax)
                    {
                        WorldGenUtil.setBlockState(chunk, x1, yMax, z1, topBlock);
                        createSurfaceMisc(chunk, x1, yMax+1, z1);
                    }
                    else {
                        WorldGenUtil.setBlockState(chunk, x1, y1, z1, fillerBlock);
                    }
                }
            }
        }

        //fix connectionsMicro
        int y1 = y + xPlus;
        for (int x1 = 0; x1 < CHUNK_SIZE; x1++)
        {
            WorldGenUtil.setBlockStateIfAir(chunk, x1, y1, connectDiffFromEdge, blockConnectX);
        }

        y1 = y + zPlus;
        for (int x1 = 0; x1 < CHUNK_SIZE; x1++)
        {
            WorldGenUtil.setBlockStateIfAir(chunk, connectDiffFromEdge, y1, x1, blockConnectZ);
        }
    }

    boolean createSurfaceMisc(Chunk chunk, int x, int y, int z)
    {
        if (y > MAX_BUILD_HEIGHT)
        {
            return false;
        }

        boolean watery = chunk.getBiome(new BlockPos(x,0,z), provider) == WATER_ISLAND;

        if (watery)
        {
            if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_PEBBLE)
            {
                WorldGenUtil.setBlockState(chunk,x, y, z, PEBBLE);
                return true;
            }else if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_CRATE)
            {
                WorldGenUtil.setBlockState(chunk,x, y, z, BOX);
                return true;
            }else if(rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_TORCH_STONE)
            {
                WorldGenUtil.setBlockState(chunk,x, y + 1, z, SEA_LANTERN);
                WorldGenUtil.setBlockState(chunk,x, y, z, SEA_STONE);
                WorldGenUtil.setBlockState(chunk,x, y - 1, z, SEA_STONE);
                return true;
            }else if(rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_TREE_SAPLING) {
                //if (saplingBlock.canBlockStay(chunk.getWorld(), new BlockPos(x,y,z), SAPLING))
                {
                    WorldGenUtil.setBlockState(chunk, x, y, z, MOSS_STONE);
                }
            }
        }
        else {
            if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_PEBBLE)
            {
                WorldGenUtil.setBlockState(chunk,x, y, z, PEBBLE);
                return true;
            }else if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_CRATE)
            {
                WorldGenUtil.setBlockState(chunk,x, y, z, BOX);
                return true;
            }else if(rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_TORCH_STONE)
            {
                WorldGenUtil.setBlockState(chunk,x, y + 1, z, TORCH_STONE);
                WorldGenUtil.setBlockState(chunk,x, y, z, BLANK_RUNE);
                WorldGenUtil.setBlockState(chunk,x, y - 1, z, BLANK_RUNE);
                return true;
            }
            else if(rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_FALL_STONE)
            {
                WorldGenUtil.setBlockState(chunk,x, y, z, FALL_RUNE);
                WorldGenUtil.setBlockState(chunk,x, y-1, z, BLANK_RUNE);
                return true;
            }else if(rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_SPAWN)
            {
                WorldGenUtil.setBlockState(chunk,x, y, z, REVIVE_RUNE);
                WorldGenUtil.setBlockState(chunk,x, y-1, z, BLANK_RUNE);
                return true;
            }
            else if(rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_TREE_SAPLING)
            {
                //if (saplingBlock.canBlockStay(chunk.getWorld(), new BlockPos(x,y,z), SAPLING))
                {
                    WorldGenUtil.setBlockState(chunk,x, y, z, SAPLING);
                }

                return true;
//        }
//        else if(rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_BUSH)
//        {
//            //if (bushBlock.canBlockStay(chunk.getWorld(), new BlockPos(x,y,z), DEAD_BUSH))
//            {
//                WorldGenUtil.setBlockState(chunk,x, y, z, DEAD_BUSH);
//            }
//
//            return true;
            } else if (rand.nextFloat() < ModConfig.WORLD_GEN_CONF.GEN_GRASS)
            {
                //if (bushBlock.canBlockStay(chunk.getWorld(), new BlockPos(x,y,z), GRASS)) {
                WorldGenUtil.setBlockState(chunk, x, y, z, TALLGRASS);
                //}
                return true;
            }
        }

        return false;
    }


}
