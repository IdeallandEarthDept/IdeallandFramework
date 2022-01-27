package com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.world.worldgen.structurebig.EnumConnection;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Random;

public class StructureBigDungeon extends MapGenStructure {
    public static final String NAME = "BigDungeon";
    final int SEED_CONST = 20220108;
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
        if (chunkX % maxDistance == 8 && chunkZ % maxDistance == 8) {
            return true;
        }
        return false;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new StructureBigDungeon.Start(world, rand, chunkX, chunkZ);
    }

    //StructureStart
    public static class Start extends StructureStart {
        HashMap<Vec3i, ComponentDungeonRoomBase> roomHashMap = new HashMap<>();
        HashMap<Integer, Boolean> canGoDown = new HashMap<>();
        public BlockPos basePos;
        protected Random random;

//        boolean goDown = true;
//        int lastY = -7;
//        int maxXZ = 7;
//        int downBorder = maxXZ / 2;
//
//        int downChancePercent = 10;
//
//        int roomSizeXZ = 12;
//        int roomSizeY = 10;

        public static int cascadeLevel = 0;

        //Make sure you have this. Or it will error when reloading the game.
        public Start() {
        }

        public Start(World worldIn, Random rand, int x, int z) {
            super(x, z);
            this.random = rand;
            init(worldIn, rand, x, z);
            updateBoundingBox();
        }

        public void init(World worldIn, Random rand, int x, int z)
        {
            basePos = new BlockPos(x << 4, worldIn.getSeaLevel(), z << 4);
            ComponentDungeonRoomBase firstComp = new ComponentDungeonRoomBase(1, EnumFacing.NORTH, Vec3i.NULL_VECTOR, this);
            firstComp.connections.put(EnumFacing.UP, EnumConnection.PASS);
            firstComp.buildComponent(firstComp, null, random);
            expandRoom(firstComp, rand);
        }

        public static void log(String s, Object... args) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i <= cascadeLevel; i++) {
                stringBuilder.append("  ");
            }
            stringBuilder.append(String.format(s, args));
            Idealland.Log(stringBuilder.toString());
        }

        public ComponentDungeonRoomBase getNewRoom(int type, EnumFacing mainDirection, Vec3i vec3i, StructureBigDungeon.Start start) {
            ComponentDungeonRoomBase room = new ComponentDungeonMircoMaze(type, mainDirection, vec3i, start);
            room.buildComponent(room, null, random);
            return room;
        }

        //returns whether a room is created
        boolean tryGoDown(ComponentDungeonRoomBase roomBase, Random random) {
            cascadeLevel++;
            log("tryGoDown room from %s", roomBase.relPos);
            BlockPos ori = new BlockPos(roomBase.relPos);

            int y = roomBase.relPos.getY();
            if (isLastStorey(y)) {
                cascadeLevel--;
                return false;
            }

            if (canGoDown.get(y) != null) {
                //only 1 down each floor
                cascadeLevel--;
                return false;
            } else {
                canGoDown.put(y, true);

                ComponentDungeonRoomBase nextRoom = getNewRoom(0, roomBase.mainDirection.getOpposite(), ModConfig.DUNGEON_CONF.goDown ? ori.down() : ori.up(), this);
                roomBase.connect(EnumFacing.DOWN, nextRoom);

                expandRoom(nextRoom, random);
                cascadeLevel--;
                return true;
            }
        }

        //returns whether a room is created
        boolean expandRoom(ComponentDungeonRoomBase roomBase, Random random) {
            cascadeLevel++;
            log("expand room from %s", roomBase.relPos);

            enlistRoom(roomBase);
            BlockPos ori = new BlockPos(roomBase.relPos);

            boolean down = false;
            if (canGoDown(ori) && random.nextInt(100) <= ModConfig.DUNGEON_CONF.downChancePercent) {
                //side way, may branch here
                down = tryGoDown(roomBase, random);
            }

            EnumFacing dir = pickDir(roomBase, random);
            if (goDir(roomBase, random, dir)) {
                cascadeLevel--;
                return true;
            } else {
                for (EnumFacing facing : EnumFacing.values()) {
                    if (facing != dir &&
                            facing != roomBase.mainDirection.getOpposite() &&
                            facing != EnumFacing.UP &&
                            facing != EnumFacing.DOWN) {
                        if (goDir(roomBase, random, facing)) {
                            cascadeLevel--;
                            return true;//found a secondary option to continue
                        }
                    }
                }

                int y = ori.getY();
                if (canGoDown.get(y) == null && canGoDown(roomBase.relPos) && !down) {
                    //cannot go side way, try go down.
                    down = tryGoDown(roomBase, random);
                    if (canGoDown.get(y) == null) {
                        Idealland.LogWarning("Failed to finish a dungeon @%s", ori);
                        cascadeLevel--;
                        return false;//total failure to continue
                    }
                }
                cascadeLevel--;
                return down;
            }//end of if fail to expand
        }

        //returns true if it creates at least 1 new room.
        boolean goDir(ComponentDungeonRoomBase roomBase, Random random, EnumFacing dir) {
            cascadeLevel++;
            log("go dir from %s", roomBase.relPos);
            BlockPos nextPos = new BlockPos(roomBase.relPos).offset(dir);
            if (inBound(nextPos)) {
                ComponentDungeonRoomBase targetRoom = roomHashMap.get(nextPos);
                if (targetRoom != null) {
                    //already occupied. break it through but fail the room creation.
                    roomBase.connect(dir, targetRoom);
                    cascadeLevel--;
                    return false;
                } else {
                    //create one
                    ComponentDungeonRoomBase nextRoom = getNewRoom(0, dir, nextPos, this);
                    roomBase.connect(dir, nextRoom);
                    expandRoom(nextRoom, random);
                    cascadeLevel--;
                    return true;
                }
            } else {
                //crash border
                boolean result = tryGoDown(roomBase, random);
                cascadeLevel--;
                return result;
            }
        }

        boolean canGoDown(Vec3i vec3i) {
            int x = vec3i.getX();
            int z = vec3i.getZ();
            boolean isInner =
                    x <= ModConfig.DUNGEON_CONF.downBorder &&
                            z <= ModConfig.DUNGEON_CONF.downBorder &&
                            x >= -ModConfig.DUNGEON_CONF.downBorder &&
                            z >= -ModConfig.DUNGEON_CONF.downBorder;
            return isInner ^ (vec3i.getY() % 2 == 0);//alternately
        }

        boolean isLastStorey(int y) {
            if (ModConfig.DUNGEON_CONF.goDown) {
                return y <= ModConfig.DUNGEON_CONF.lastY;
            } else {
                return y >= ModConfig.DUNGEON_CONF.lastY;
            }
        }

        boolean inBound(Vec3i vec3i) {
            int x = vec3i.getX();
            int z = vec3i.getZ();
            return vec3i.getY() >= ModConfig.DUNGEON_CONF.lastY &&
                    x <= ModConfig.DUNGEON_CONF.maxXZ &&
                    z <= ModConfig.DUNGEON_CONF.maxXZ &&
                    x >= -ModConfig.DUNGEON_CONF.maxXZ &&
                    z >= -ModConfig.DUNGEON_CONF.maxXZ;
        }

        EnumFacing pickDir(ComponentDungeonRoomBase roomBase, Random random) {
            int factor = random.nextInt(100);
            if (factor < 50) {
                return roomBase.mainDirection;
            } else {
                if (factor < 75) {
                    return roomBase.mainDirection.rotateY();
                } else {
                    return roomBase.mainDirection.rotateYCCW();
                }
            }
        }

        public void enlistRoom(ComponentDungeonRoomBase roomBase) {
            roomHashMap.put(roomBase.relPos, roomBase);
            components.add(roomBase);
        }

    }
}
