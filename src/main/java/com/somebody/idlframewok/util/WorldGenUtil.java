package com.somebody.idlframewok.util;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.blockBasic.ModBlockNightSky;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.*;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class WorldGenUtil {
    public static final IBlockState AIR = Blocks.AIR.getDefaultState();
    public static final IBlockState MOB_SPAWNER = Blocks.MOB_SPAWNER.getDefaultState();
    public static final IBlockState BOX = Blocks.CHEST.getDefaultState();
    public static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();

    public static final int PREVENT_CASCADE_FLAGS = BlockFlags.TO_CLIENT | BlockFlags.IGNORE_OB;
    public static final int PREVENT_CASCADE_FLAGS_2 = PREVENT_CASCADE_FLAGS | BlockFlags.CLIENT_DONT_RENDER;

    static Rotation[] rotation = Rotation.values();

    public static boolean isStopUpdate() {
        return stopUpdate;
    }

    private static boolean stopUpdate = false;

    @SubscribeEvent
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event)
    {
        if (stopUpdate)
        {
            //to stop the template from cascading world gen by notifying neighbours.
            event.setCanceled(true);
        }
    }

    public static void setStopUpdate(boolean value) {
        stopUpdate = value;
    }

    public static boolean isBlockPosReady(World world, BlockPos pos)
    {
        //return world.isChunkGeneratedAt(pos.getX()/16, pos.getZ()/16) && world.getChunkFromBlockCoords(pos).isPopulated();
        return world.isBlockLoaded(pos, false);
    }

    public static boolean generate(ResourceLocation res, World worldIn, BlockPos position, @Nullable PlacementSettings placementsettings)
    {
        return generate(res, worldIn, position, placementsettings,1.0f, false,false);
    }

    //ref: world gen fossil
    public static boolean generate(ResourceLocation res, World worldIn, BlockPos position, @Nullable PlacementSettings placementsettings, float integrity, boolean useMinorOffset, boolean useRandomRotation) {
        stopUpdate = true;
        Random random = worldIn.getChunkFromBlockCoords(position).getRandomWithSeed(987234911L);
        MinecraftServer minecraftserver = worldIn.getMinecraftServer();

        Rotation rotation = Rotation.NONE;
        if (useRandomRotation) {
            rotation = WorldGenUtil.rotation[random.nextInt(WorldGenUtil.rotation.length)];
        }

        TemplateManager templatemanager = worldIn.getSaveHandler().getStructureTemplateManager();
        Template template = templatemanager.get(minecraftserver, res);
        if (template == null) {
            Idealland.LogWarning("Trying to load Template that does not exist: %s", res);
            stopUpdate = false;
            return false;
        }
        ChunkPos chunkpos = new ChunkPos(position);
        StructureBoundingBox structureboundingbox = new StructureBoundingBox(chunkpos.getXStart(), 0, chunkpos.getZStart(), chunkpos.getXEnd() + CHUNK_SIZE, WORLD_HEIGHT, chunkpos.getZEnd() + CHUNK_SIZE);
        if (placementsettings == null) {
            placementsettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(structureboundingbox).setRandom(random);
        } else {
            placementsettings.setRotation(rotation).setBoundingBox(structureboundingbox).setRandom(random);
        }
        BlockPos blockpos = template.transformedSize(rotation);
        int x = 0;
        int z = 0;
        //lesser than 16 structurebig can have a little offset:
        if (useMinorOffset) {
            if (blockpos.getX() < CHUNK_SIZE){
                x = random.nextInt(16 - blockpos.getX());
            }

            if (blockpos.getZ() < CHUNK_SIZE) {
                z = random.nextInt(16 - blockpos.getZ());
            }
        }

        int y = WORLD_HEIGHT;

        int coordMax = blockpos.getX();
        for (int x1 = 0; x1 < coordMax; ++x1) {
            for (int z1 = 0; z1 < coordMax; ++z1) {
                y = Math.min(y, worldIn.getHeight(position.getX() + x1 + x, position.getZ() + z1 + z));
            }
        }

        //int k1 = Math.max(y - CHUNK_MAX - random.nextInt(10), 10);//random y delta. I don't need this
        BlockPos blockpos1 = template.getZeroPositionWithTransform(position.add(x, 0, z), Mirror.NONE, rotation);
        placementsettings.setIntegrity(integrity);
        template.addBlocksToWorld(worldIn, blockpos1, placementsettings, PREVENT_CASCADE_FLAGS_2);
        stopUpdate = false;
        return true;
    }

    public static boolean generateIgnoreChunkBorder(ResourceLocation res, World worldIn, BlockPos position, @Nullable PlacementSettings placementsettings) {
        return generateIgnoreChunkBorder(res, worldIn, position, placementsettings, 1.0f, false, false);
    }

    //ref: world gen fossil
    public static boolean generateIgnoreChunkBorder(ResourceLocation res, World worldIn, BlockPos position, @Nullable PlacementSettings placementsettings, float integrity, boolean useMinorOffset, boolean useRandomRotation) {
        stopUpdate = true;
        Random random = worldIn.getChunkFromBlockCoords(position).getRandomWithSeed(987234911L);
        MinecraftServer minecraftserver = worldIn.getMinecraftServer();

        Rotation rotation = Rotation.NONE;
        if (useRandomRotation) {
            rotation = WorldGenUtil.rotation[random.nextInt(WorldGenUtil.rotation.length)];
        }

        TemplateManager templatemanager = worldIn.getSaveHandler().getStructureTemplateManager();
        Template template = templatemanager.get(minecraftserver, res);
        if (template == null) {
            Idealland.LogWarning("Trying to load Template that does not exist: %s", res);
            stopUpdate = false;
            return false;
        }
        ChunkPos chunkpos = new ChunkPos(position);
        StructureBoundingBox structureboundingbox = new StructureBoundingBox(chunkpos.getXStart() - 16, 0, chunkpos.getZStart() - 16, chunkpos.getXEnd() + 16, WORLD_HEIGHT, chunkpos.getZEnd() + 16);
        if (placementsettings == null) {
            placementsettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(structureboundingbox).setRandom(random);
        } else {
            placementsettings.setRotation(rotation).setBoundingBox(structureboundingbox).setRandom(random);
        }
        BlockPos blockpos = template.transformedSize(rotation);
        int x = 0;
        int z = 0;
        //lesser than 16 structurebig can have a little offset:
        if (useMinorOffset) {
            if (blockpos.getX() < CHUNK_SIZE) {
                x = random.nextInt(16 - blockpos.getX());
            }

            if (blockpos.getZ() < CHUNK_SIZE) {
                z = random.nextInt(16 - blockpos.getZ());
            }
        }

        int y = WORLD_HEIGHT;

        int coordMax = blockpos.getX();
        for (int x1 = 0; x1 < coordMax; ++x1) {
            for (int z1 = 0; z1 < coordMax; ++z1) {
                y = Math.min(y, worldIn.getHeight(position.getX() + x1 + x, position.getZ() + z1 + z));
            }
        }

        //int k1 = Math.max(y - CHUNK_MAX - random.nextInt(10), 10);//random y delta. I don't need this
        BlockPos blockpos1 = template.getZeroPositionWithTransform(position.add(x, 0, z), Mirror.NONE, rotation);
        placementsettings.setIntegrity(integrity);
        template.addBlocksToWorld(worldIn, blockpos1, placementsettings, PREVENT_CASCADE_FLAGS_2);
        stopUpdate = false;
        return true;
    }

    static BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
    public static void setBlockState(Chunk chunk, int relX, int y, int relZ, IBlockState state)
    {
        if (y > MAX_BUILD_HEIGHT)
        {
            Idealland.LogWarning("#2 Failed to generate %s@(%d,%d,%d) as it's too high", state, relX, y, relZ);
            return;
        }

        try {
            chunk.setBlockState(new BlockPos(relX, y, relZ), state);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            Idealland.LogWarning("#3 Failed to generate:(%d,%d,%d)", relX, y, relZ);
        }
    }

    //prevent side blocks from updating nearby blocks
    public static final int PREVENT_SIDE = PREVENT_CASCADE_FLAGS;
    public static void setBlockState(World world, BlockPos pos, IBlockState state)
    {
        world.setBlockState(pos, state, PREVENT_SIDE);
    }

    public static void setBlockState(World world, BlockPos pos, IBlockState state, int flag)
    {
        world.setBlockState(pos, state, flag);
    }

    public static void setBlockState(World world, int x, int y, int z, IBlockState state, int flag)
    {
        //will not use BlockPos.Mutable as it's maybe not thread safe?
        world.setBlockState(blockpos$mutableblockpos.setPos(x,y,z), state, flag);
    }
    public static void setBlockState(World world, BlockPos chunkPosBase, int relX, int y, int relZ, IBlockState state)
    {
        world.setBlockState(chunkPosBase.add(relX, y, relZ), state,PREVENT_SIDE);
    }
    public static void setBlockStateIfAir(Chunk chunk, int relX, int y, int relZ, IBlockState state)
    {
        if (y > MAX_BUILD_HEIGHT)
        {
            Idealland.LogWarning("#1 Failed to generate:(%d,%d,%d) as it's too high",relX, y, relZ);
            return;
        }
        if (chunk.getBlockState(relX, y, relZ).getBlock() == Blocks.AIR)
        {
            setBlockState(chunk, relX, y, relZ, state);
        }
    }

    public static void setBlockStateIfAir(World world, BlockPos pos, IBlockState state)
    {
        if (world.getBlockState(pos).getBlock() == Blocks.AIR)
        {
            world.setBlockState(pos, state, PREVENT_SIDE);
        }
    }

//    public static void setBlockStateIfAir(World world, int relX, int y, int relZ, IBlockState state)
//    {
//        BlockPos pos = new BlockPos(relX, y, relZ);
//        setBlockStateIfAir(world, pos, state);
//    }
//
//    public static void buildIfAir(int chunkX, int chunkZ, World world, int x, int y, int z, IBlockState state) {
//        setBlockStateIfAir(world, new BlockPos(chunkX * CHUNK_SIZE + x, y, chunkZ * CHUNK_SIZE + z), state);
//    }

    public static void buildForced(int chunkX, int chunkZ, World world, IBlockState state, int x, int y, int z, int flag) {
        BlockPos pos = blockpos$mutableblockpos.setPos(chunkX * CHUNK_SIZE + x, y, chunkZ * CHUNK_SIZE + z);
        setBlockState(world, pos, state, flag);
    }

    public static void buildForced(int chunkX, int chunkZ, World world, IBlockState state, int x, int y, int z) {
        buildForced(chunkX, chunkZ, world, state, x, y, z, PREVENT_SIDE);
    }

    public static void buildForced(int chunkX, int chunkZ, World world, IBlockState state, int x1, int y1, int z1, int x2, int y2, int z2, int flag) {
        int t = 0;
        if (x1 > x2)
        {
            t = x2;
            x2 = x1;
            x1 = t;
        }
        if (y1 > y2)
        {
            t = y2;
            y2 = y1;
            y1 = t;
        }
        if (z1 > z2)
        {
            t = z2;
            z2 = z1;
            z1 = t;
        }

        for (int x = x1; x <= x2; x++)
        {
            for (int y = y1; y <= y2; y++)
            {
                for (int z = z1; z <= z2; z++)
                {
                    buildForced(chunkX, chunkZ, world, state, x, y, z, flag);
                }
            }
        }
    }

    //build a block
    public static void buildForced(int chunkX, int chunkZ, World world, IBlockState state, int x1, int y1, int z1, int x2, int y2, int z2) {
        buildForced(chunkX, chunkZ, world, state, x1, y1, z1, x2, y2, z2, PREVENT_SIDE);
    }

    public static BlockPos blockPosFromXZ(int relX, int relZ)
    {
        return new BlockPos(relX * CHUNK_SIZE, 0, relZ * CHUNK_SIZE);
    }

    public static boolean notSolid(World world, BlockPos pos)
    {
        return !world.getBlockState(pos).getMaterial().isSolid() || (world.getBlockState(pos).getBlock() instanceof ModBlockNightSky);
    }

    public static boolean isSolid(World world, BlockPos pos)
    {
        return !notSolid(world, pos);
    }

    //Note that if no ground, will return null!
    @Nullable
    public static BlockPos getGroundPos(Random random, World world, BlockPos pos) {
        int minHeight = 3;

        int groundY = world.getActualHeight() - 20;
        int x = 1 + random.nextInt(CHUNK_MAX - 1);//1~14
        int z = 1 + random.nextInt(CHUNK_MAX - 1);//1~14

        BlockPos.MutableBlockPos tempPos = new BlockPos.MutableBlockPos(pos.add(x, 0, z));
        while (groundY > minHeight) {
            tempPos.setY(groundY);
            if (isSolid(world, tempPos)) {
                break;
            }
            groundY--;
        }

        if (groundY <= minHeight) {
            //failed to found solid GROUND
            return null;
        }

        BlockPos target = pos.add(x, groundY - pos.getY(), z);
        return target;
    }
}
