package com.somebody.idlframewok.world.dimension;

import com.somebody.idlframewok.init.InitBiome;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.world.biome.BiomeBase;
import com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.StructureBigDungeon;
import com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.bigdungeon2.StructureMazeDungeon;
import com.somebody.idlframewok.world.worldgen.structurebig.test.ModGenStructureTest;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;
//import static com.somebody.idlframewok.world.worldgen.structurebig.test.ModGenStructureTest.NAME;

public abstract class ChunkGenBase implements IChunkGenerator {

    public Biome singleBiome = InitBiome.BIOME_ONE;

    public static final int heightLimit = 255;
    public boolean hasBedrockFloor = true;
    public int ceilBedrockLevel = -1;//<0 or > 255 means no ceil
    //private static final int yNoGateLimit = 255;

    public World world;
    public boolean generateStructures;
    public Random rand;
    public String generatorOptions;
    public ChunkGeneratorSettings settings;

    public boolean genLakes = true;
    public boolean genDungeons = true;
    public boolean doDecorate = true;
    public boolean genInitAnimals = true;
    public boolean genIce = true;

    ModGenStructureTest genStructureTest = new ModGenStructureTest();
    StructureBigDungeon genStructureDungeon = new StructureBigDungeon();
    StructureMazeDungeon genStructureMaze = new StructureMazeDungeon();

    public ChunkGenBase(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions) {
        this.world = worldIn;
        this.generateStructures = mapFeaturesEnabledIn;
        this.rand = new Random(seed);
        world.setSeaLevel(63);
        this.generatorOptions = generatorOptions;

        if (generatorOptions != null)
        {
            this.settings = ChunkGeneratorSettings.Factory.jsonToFactory(generatorOptions).build();
            worldIn.setSeaLevel(this.settings.seaLevel);
        }
    }

    //override this.
    public void buildChunkPrimier(int x, int z, ChunkPrimer primer)
    {
        if (hasBedrockFloor)
        {
            generateBedrockLayer(primer);
        }

        if (ceilBedrockLevel > 0 && ceilBedrockLevel <= 255)
        {
            generateBedrockLayer(primer, ceilBedrockLevel);
        }

        if (ModConfig.DEBUG_CONF.DEBUG_MODE) {
            genStructureTest.generate(world, x, z, primer);

            genStructureDungeon.generate(world, x, z, primer);

            genStructureMaze.generate(world, x, z, primer);
        }

    }

    //override this.
    public void buildChunk(int x, int z, Chunk chunk)
    {

    }

    public void setBiomeArray(Chunk chunk, World world, int x, int z)
    {
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = (byte)Biome.getIdForBiome(singleBiome);
        }
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        buildChunkPrimier(x,z,chunkprimer);

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        setBiomeArray(chunk, this.world, x, z);
        buildChunk(x,z,chunk);
        chunk.resetRelightChecks();
        return chunk;
    }

    protected void generateBedrockLayer(ChunkPrimer primer) {
        generateBedrockLayer(primer, 0);
    }

    protected void generateBedrockLayer(ChunkPrimer primer, int y) {

        for (int dx = 0; dx < CHUNK_SIZE; dx++)
        {
            //for (int dy = 0; dy < CommonDef.CHUNK_SIZE; dy++)
            {
                for (int dz = 0; dz < CHUNK_SIZE; dz++)
                {
                    primer.setBlockState(dx, y, dz,
                            Blocks.BEDROCK.getDefaultState());

                }
            }
        }
    }

    public void preVanillaPopulate(int x, int z, Biome biome) {

    }


    public void postVanillaPopulate(int x, int z, Biome biome) {

    }

    @Override
    public void populate(int x, int z) {
        BlockFalling.fallInstantly = true;
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
        this.rand.setSeed(this.world.getSeed());
        long k = this.rand.nextLong() / 2L * 2L + 1L;
        long l = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)x * k + (long)z * l ^ this.world.getSeed());
        boolean flag = false;

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, flag);

//        if (this.generateStructures)
//        {
//            if (this.settings.useMineShafts)
//            {
//                this.mineshaftGenerator.generateStructure(this.world, this.rand, chunkpos);
//            }
//        }

        preVanillaPopulate(x, z, biome);

        if (biome instanceof BiomeBase) {
            BiomeBase biomeBase = (BiomeBase) biome;
            if (genLakes && biomeBase.isGenLakes())
                populateLake(x, z, blockpos, biome, flag);

            if (genDungeons && biomeBase.isGenDungeons())
                populateDungeon(x, z, blockpos, flag);

            if (doDecorate && biomeBase.isDoDecorate())
                biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));

            if (genInitAnimals && biomeBase.isGenInitAnimals())
                populateAnimals(x, z, i, j, biome, flag);

            blockpos = blockpos.add(8, 0, 8);

            if (genIce && biomeBase.isGenIce())
                populateIce(x, z, blockpos, flag);
        } else {
            if (genLakes)
                populateLake(x, z, blockpos, biome, flag);

            if (genDungeons)
                populateDungeon(x, z, blockpos, flag);

            if (doDecorate)
                biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));

            if (genInitAnimals)
                populateAnimals(x, z, i, j, biome, flag);

            blockpos = blockpos.add(8, 0, 8);

            if (genIce)
                populateIce(x, z, blockpos, flag);
        }

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, flag);
        postVanillaPopulate(x, z, biome);

        populateStructure(x, z);

        BlockFalling.fallInstantly = false;
    }

    void populateStructure(int x, int z) {
        if (ModConfig.DEBUG_CONF.DEBUG_MODE) {

            final ChunkPos chunkCoord = new ChunkPos(x, z);
            genStructureTest.generateStructure(world, rand, chunkCoord);
            genStructureDungeon.generateStructure(world, rand, chunkCoord);
            genStructureMaze.generateStructure(world, rand, chunkCoord);
        }
    }

    private void populateIce(int x, int z, BlockPos blockpos, boolean flag) {
        if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ICE))
        {
            for (int k2 = 0; k2 < 16; ++k2)
            {
                for (int j3 = 0; j3 < 16; ++j3)
                {
                    BlockPos blockpos1 = this.world.getPrecipitationHeight(blockpos.add(k2, 0, j3));
                    BlockPos blockpos2 = blockpos1.down();

                    if (this.world.canBlockFreezeWater(blockpos2))
                    {
                        this.world.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
                    }

                    if (this.world.canSnowAt(blockpos1, true))
                    {
                        this.world.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
                    }
                }
            }
        }//Forge: End ICE
    }

    private void populateAnimals(int x, int z, int i, int j, Biome biome, boolean flag) {
        if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS))
            WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);
    }

    private void populateDungeon(int x, int z, BlockPos blockpos, boolean flag) {
        if (this.settings.useDungeons)
            if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.DUNGEON))
            {
                for (int j2 = 0; j2 < this.settings.dungeonChance; ++j2)
                {
                    int i3 = this.rand.nextInt(16) + 8;
                    int l3 = this.rand.nextInt(256);
                    int l1 = this.rand.nextInt(16) + 8;
                    (new WorldGenDungeons()).generate(this.world, this.rand, blockpos.add(i3, l3, l1));
                }
            }
    }

    private void populateLake(int x, int z, BlockPos blockpos, Biome biome, boolean flag) {
        if (biome != Biomes.DESERT && biome != Biomes.DESERT_HILLS && this.settings.useWaterLakes && !flag && this.rand.nextInt(this.settings.waterLakeChance) == 0)
            if (net.minecraftforge.event.terraingen.TerrainGen.populate(this, this.world, this.rand, x, z, flag, net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAKE))
            {
                int i1 = this.rand.nextInt(16) + 8;
                int j1 = this.rand.nextInt(256);
                int k1 = this.rand.nextInt(16) + 8;
                (new WorldGenLakes(Blocks.WATER)).generate(this.world, this.rand, blockpos.add(i1, j1, k1));
            }
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Nullable
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        if (!this.generateStructures) {
            return null;
        } else
        {
            return checkStructures(worldIn, structureName, position, findUnexplored);
        }
    }

    BlockPos checkStructures(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
        switch (structureName)
        {
            case ModGenStructureTest.NAME:
                return genStructureTest.getNearestStructurePos(worldIn, position, findUnexplored);

            case StructureBigDungeon.NAME:
                return genStructureDungeon.getNearestStructurePos(worldIn, position, findUnexplored);

            case StructureMazeDungeon.NAME:
                return genStructureMaze.getNearestStructurePos(worldIn, position, findUnexplored);
            default:
                return BlockPos.ORIGIN;
        }
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {
        genStructureTest.generate(world, x, z, null);
        genStructureDungeon.generate(world, x, z, null);
        genStructureMaze.generate(world, x, z, null);
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        switch (structureName) {
            case ModGenStructureTest.NAME:
                return genStructureTest.isInsideStructure(pos);

            case StructureBigDungeon.NAME:
                return genStructureDungeon.isInsideStructure(pos);

            case StructureMazeDungeon.NAME:
                return genStructureMaze.isInsideStructure(pos);
            default:
                return false;
        }
    }
}
