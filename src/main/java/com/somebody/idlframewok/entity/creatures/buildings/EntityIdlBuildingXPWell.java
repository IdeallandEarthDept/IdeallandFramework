package com.somebody.idlframewok.entity.creatures.buildings;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityIDLXPWell;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityIdlBuildingXPWell extends EntityIdlBuildingBase {
    public EntityIdlBuildingXPWell(World worldIn) {
        super(worldIn);
        setAttr(1, 0, 0, 8, 5);
        buildingCore.setSpeed(2f / TICK_PER_SECOND);
    }

    void InitTaskQueue()
    {
        BlockPos origin = new BlockPos(0,0,0);

        if (buildingCore == null)
        {
            Idealland.LogWarning("Core is null");
        }

        int bottomRange = 1;
        int pillarHeight = 3;

        //floor
        buildingCore.AddTaskBuildWallWithBlockCentered(origin.add(0, -1, 0), bottomRange, 1, bottomRange, ModBlocks.GRID_NORMAL.getDefaultState());

        //surrounding
        buildingCore.AddTaskBuildWallWithBlockCentered(origin.add(0, 0, bottomRange + 1), bottomRange + 1, 1, 0, ModBlocks.GRID_NORMAL.getDefaultState());
        buildingCore.AddTaskBuildWallWithBlockCentered(origin.add(0, 0, -bottomRange - 1), bottomRange + 1, 1, 0, ModBlocks.GRID_NORMAL.getDefaultState());
        buildingCore.AddTaskBuildWallWithBlockCentered(origin.add(bottomRange + 1, 0, 0), 0, 1, bottomRange, ModBlocks.GRID_NORMAL.getDefaultState());
        buildingCore.AddTaskBuildWallWithBlockCentered(origin.add(-bottomRange - 1, 0, 0), 0, 1, bottomRange, ModBlocks.GRID_NORMAL.getDefaultState());

        //center Pillar
        buildingCore.AddTaskBuildWallWithBlockCentered(origin.add(0, 0, 0), 0, pillarHeight, 0, ModBlocks.GRID_NORMAL.getDefaultState());
        buildingCore.AddTaskBuild(origin.add(0, pillarHeight, 0), ModBlocks.IDEALLAND_LIGHT_BASIC.getDefaultState());

        buildingCore.AddTaskSummon(origin.add(0, pillarHeight + 1, 0), EntityIDLXPWell.class);

       //AddTaskBuildWallWithBlockCentered(origin.add(-floorReach,2,0), 0, windowHeight, 1, windowMaterial);
    }
}
