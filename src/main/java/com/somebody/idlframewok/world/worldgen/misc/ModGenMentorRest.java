package com.somebody.idlframewok.world.worldgen.misc;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.entity.creatures.misc.mentor.*;
import com.somebody.idlframewok.init.InitDimension;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;
import static net.minecraftforge.common.BiomeDictionary.Type.WATER;

public class ModGenMentorRest implements IWorldGenerator {

    IBlockState WALL_MAIN = Blocks.BRICK_BLOCK.getDefaultState();
    IBlockState WALL_MAIN_CORNER = Blocks.LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);

    IBlockState STAIR = Blocks.SPRUCE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH);

    IBlockState PLANKS = Blocks.PLANKS.getDefaultState();

    IBlockState DOOR = Blocks.BIRCH_DOOR.getDefaultState();
    IBlockState DOOR_UP = Blocks.BIRCH_DOOR.getDefaultState().withProperty(BlockDoor.HALF,  BlockDoor.EnumDoorHalf.UPPER);

    IBlockState LIGHT = ModBlocks.GRID_LAMP.getDefaultState();

    int centerX = 7;
    int centerZ = 7;

    public ModGenMentorRest() {

    }

    public void buildRoom(Random random, int chunkX, int chunkZ, World world, int y) {
        int min = 3;
        int max = 11;
        int y0 = y;
        int y1 = y + 5;

        //clear interior
        WorldGenUtil.buildForced(chunkX,chunkZ,world, WorldGenUtil.AIR,
                min + 1, y0 + 1, min + 1,
                max - 1, y1 - 1, max - 1);

        //four walls
        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN,
                min, y0, min,
                min, y1, max);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN,
                min, y0, min,
                max, y1, min);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN,
                max, y0, max,
                min, y1, max);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN,
                max, y0, max,
                max, y1, min);

        //pillars
        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN_CORNER,
                min, y0, min,
                min, y1, min);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN_CORNER,
                min, y0, max,
                min, y1, max);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN_CORNER,
                max, y0, min,
                max, y1, min);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, WALL_MAIN_CORNER,
                max, y0, max,
                max, y1, max);

        //Ground
        WorldGenUtil.buildForced(chunkX,chunkZ,world, PLANKS,
                min, y0, min,
                max, y0, max);

        WorldGenUtil.buildForced(chunkX,chunkZ,world, LIGHT,
                centerX, y0, centerZ);


        //Roof
        WorldGenUtil.buildForced(chunkX,chunkZ,world, STAIR,
                min+1, y1, min+1,
                max-1, y1, max-1);


        //door
        for (int dy = 1; dy<= 2; dy++)
        {
            IBlockState state = dy == 1 ? DOOR : DOOR_UP;

            WorldGenUtil.buildForced(chunkX,chunkZ,world, state,
                    min, y0+dy, centerZ);

            WorldGenUtil.buildForced(chunkX,chunkZ,world, state,
                    max, y0+dy, centerZ);

            WorldGenUtil.buildForced(chunkX,chunkZ,world, state,
                    centerX, y0+dy, min);

            WorldGenUtil.buildForced(chunkX,chunkZ,world, state,
                    centerX, y0+dy, max);
        }
    }

    public EntityMentorBase getMentor(Random random, World world)
    {
        int rand = random.nextInt(8);

        switch (rand){
            case 1:
                return new EntityMentorCrit(world);
            case 2:
                return new EntityMentorCritChance(world);
            case 3:
                return new EntityMentorEye(world);
            case 4:
                return new EntityMentorFire(world);
            case 5:
                return new EntityMentorHaste(world);
            case 6:
                return new EntityMentorLife(world);
            case 7:
                return new EntityMentorSpeed(world);
            default://0
                return new EntityMentorStalk(world);
        }
    }

    public void buildBase(Random random, int chunkX, int chunkZ, World world, int y) {
        EntityMentorBase mentor = getMentor(random, world);
        mentor.setPosition(centerX + CHUNK_SIZE * chunkX + 0.5f, y + 1, centerZ + CHUNK_SIZE * chunkZ + 0.5f);
        mentor.enablePersistence();

        if (!world.spawnEntity(mentor))
        {
            Idealland.Log("[Generated Mentor rest] Failed to spawn a mentor");
        }
        else {
            //skeleton.
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        random.nextFloat();
        DimensionType dimensionType = world.provider.getDimensionType();

        if (dimensionType != DimensionType.OVERWORLD && dimensionType != InitDimension.DIM_UNIV)
        {
            //only in overworld
            return;
        }

        BlockPos pos = new BlockPos(chunkX << 4, 64, chunkZ << 4);
        Biome biome = world.getBiome(pos);
        if (BiomeDictionary.getTypes(biome).contains(WATER)) {
            //dont spawn in water
            return;
        }

        if (random.nextInt(1000) != 0)// && ModConfig.DEBUG_CONF.SWITCH_A)
        {
            return;
        }
        //build mentor's house

        int minHeight = 3;
        int buildHeightLimit = world.getActualHeight() - 20;

        int groundY = buildHeightLimit;
        BlockPos.MutableBlockPos tempPos = new BlockPos.MutableBlockPos(pos.add(7, 0, 7));
        while (groundY > minHeight) {
            tempPos.setY(groundY);
            if (WorldGenUtil.isSolid(world, tempPos)) {
                break;
            }
            groundY--;
        }

        if (groundY <= minHeight) {
            //failed to found solid GROUND
            return;
        }

        buildRoom(random, chunkX, chunkZ, world, groundY);
        buildBase(random, chunkX, chunkZ, world, groundY);
    }
}
