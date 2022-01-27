package com.somebody.idlframewok.world.dimension.universal.chunk.tower;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.Reference;
import com.somebody.idlframewok.util.WorldGenUtil;
import com.somebody.idlframewok.world.worldgen.structure.IStructure;
import com.somebody.idlframewok.world.worldgen.structure.WorldGenStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class TowerBase extends WorldGenStructure implements IStructure {
    protected int min_floor = 1;
    protected int max_floor = 7;

    protected int heightPerStorey = 16;
    protected int heightOffset = 0;

    protected int wallThickness = 2;
    protected int inverseWallIndex = CHUNK_SIZE - wallThickness;

    protected IBlockState GROUND = WorldGenUtil.BEDROCK;
    protected IBlockState WALL = WorldGenUtil.BEDROCK;

    MinecraftServer mcServer;
    TemplateManager manager;

    String[] towerRoomNames =
            {
                    "tower/fire_tower_1a",
                    "tower/fire_tower_1a_i"
            };

    ResourceLocation[] towerRoomResLoc;
    Template[] towerRoomTemplates;

    public TowerBase(int min_floor, int max_floor, IBlockState GROUND, IBlockState WALL) {
        super("");
        this.min_floor = min_floor;
        this.max_floor = max_floor;
        this.GROUND = GROUND;
        this.WALL = WALL;

        handleResLocs();
    }

    public void handleResLocs() {
        towerRoomResLoc = new ResourceLocation[towerRoomNames.length];
        for (int i = 0; i < towerRoomNames.length; i++) {
            towerRoomResLoc[i] = new ResourceLocation(Reference.MOD_ID, towerRoomNames[i]);
        }
    }


    public void init(World worldIn) {
        mcServer = worldIn.getMinecraftServer();
        manager = worldServer.getStructureTemplateManager();

        towerRoomTemplates = new Template[towerRoomNames.length];
        for (int i = 0; i < towerRoomNames.length; i++) {
            towerRoomTemplates[i] = manager.get(mcServer, towerRoomResLoc[i]);
            if (towerRoomTemplates[i] == null) {
                Idealland.LogWarning("towerRoomTemplates %d is null, Resource Location = %s", i, towerRoomResLoc[i]);
            }
        }
    }

    public void buildTower(int x, int z, Chunk chunk) {
        for (int storey = min_floor; storey <= max_floor; storey++) {
            buildStorey(x, z, chunk, storey);
        }
    }

    void buildStorey(int x, int z, Chunk chunk, int storey) {
        int yFrom = storey * heightPerStorey + heightOffset;

        int yTo = yFrom + heightPerStorey;
        for (int _y = yFrom; _y < yTo; _y++) {

            if (_y == yFrom) {
                //build floor
                for (int _x = 0; _x < CHUNK_SIZE; _x++) {
                    for (int _z = 0; _z < CHUNK_SIZE; _z++) {
                        WorldGenUtil.setBlockState(chunk, _x, _y, _z, GROUND);
                    }
                }
            } else {
                //build outside wall
                for (int _x = 0; _x < CHUNK_SIZE; _x++) {
                    for (int _z = 0; _z < CHUNK_SIZE; _z++) {
                        if (isSurrounding(_x, _z)) {
                            WorldGenUtil.setBlockState(chunk, _x, _y, _z, WALL);
                        }
                    }
                }
            }
        }

        int resIndex = 0;
        WorldGenUtil.generate(towerRoomResLoc[resIndex],
                chunk.getWorld(),
                new BlockPos(x * CHUNK_SIZE + wallThickness, yTo - 8, z * CHUNK_SIZE + wallThickness),
                null);

        resIndex = 1;
        WorldGenUtil.generate(towerRoomResLoc[resIndex],
                chunk.getWorld(),
                new BlockPos(x * CHUNK_SIZE + wallThickness, yFrom, z * CHUNK_SIZE + wallThickness),
                null);
    }

    boolean isSurrounding(int x, int z) {
        return x < wallThickness || z < wallThickness || x >= inverseWallIndex || z >= inverseWallIndex;
    }
}
