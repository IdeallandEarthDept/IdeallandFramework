package com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.bigdungeon2;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.world.worldgen.structurebig.Maze3d;
import com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.ComponentDungeonRoomBase;
import com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.StructureBigDungeon;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

import javax.annotation.Nullable;
import java.util.Random;

public class StructureMazeDungeon extends StructureBigDungeon {
    public static final String NAME = "BigDungeon2";
    final int SEED_CONST = 20220109;
    final int maxDistance = 32;//in chunks
    static final int ATTEMPTS = 100;
    Random random = new Random();

    //tools

    public void resetRandom(int chunkX, int chunkZ) {
        random = world.setRandomSeed(chunkX, chunkZ, SEED_CONST);
    }

    //must-haves-----------------------

    @Override
    public String getStructureName() {
        return NAME;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, maxDistance, 8, SEED_CONST, false, ATTEMPTS, findUnexplored);
    }


    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        resetRandom(chunkX, chunkZ);
        if (chunkX % maxDistance == 2 && chunkZ % maxDistance == 2) {
            return true;
        }
        return false;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new StructureMazeDungeon.Start(world, rand, chunkX, chunkZ);
    }

    //StructureStart
    public static class Start extends StructureBigDungeon.Start {

        Maze3d maze3d;
        //Make sure you have this. Or it will error when reloading the game.
        public Start() {
            maze3d = new Maze3d();
        }

        public Start(World worldIn, Random rand, int x, int z) {
            super(worldIn, rand, x, z);
        }

        public void init(World worldIn, Random rand, int x, int z)
        {
            maze3d = new Maze3d(ModConfig.DUNGEON_CONF.maze2sizeX,ModConfig.DUNGEON_CONF.maze2sizeY,ModConfig.DUNGEON_CONF.maze2sizeX);
            basePos = new BlockPos(x << 4, worldIn.getSeaLevel(), z << 4);
            maze3d.setRandom(random);
            maze3d.createMaze();
            for (BlockPos pos:
                maze3d.cells.keySet()) {

                Maze3d.MicroCell cell = maze3d.getCell(pos);
                if (cell != null)
                {
                    ComponentDungeonRoomBase roomBase = getNewRoom(0, EnumFacing.NORTH, pos, this);
                    if (roomBase instanceof ComponentMazeDungeon)
                    {
                        ((ComponentMazeDungeon) roomBase).setState(cell.index);
                        ((ComponentMazeDungeon) roomBase).connectByCells(cell);
                    }
                    enlistRoom(roomBase);
                }
            }
        }

        @Override
        public ComponentDungeonRoomBase getNewRoom(int type, EnumFacing mainDirection, Vec3i vec3i, StructureBigDungeon.Start start) {
            ComponentDungeonRoomBase room = new ComponentMazeDungeon(type, mainDirection, vec3i, start);
            room.buildComponent(room, null, random);
            return room;
        }
    }
}
