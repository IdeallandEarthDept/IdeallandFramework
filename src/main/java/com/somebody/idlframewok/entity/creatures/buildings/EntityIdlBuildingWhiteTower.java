package com.somebody.idlframewok.entity.creatures.buildings;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityIdlBuildingWhiteTower extends EntityIdlBuildingBase {
    public EntityIdlBuildingWhiteTower(World worldIn) {
        super(worldIn);
        setAttr(1, 0, 0, 8, 5);
    }


    void InitTaskQueue()
    {
        int wallHeight = 255 + 32 - getPosition().getY();
        int radius = 32;
        int reachDown = 32;
        BlockPos origin = new BlockPos(0,0,0);

        if (buildingCore == null)
        {
            Idealland.LogWarning("Core is null");
        }

        buildingCore.AddTaskBuildWallWithBlockCentered(origin.add(-radius,-reachDown,0), 0, wallHeight, radius, ModBlocks.IDL_GLASS.getDefaultState());
        buildingCore.AddTaskBuildWallWithBlockCentered(origin.add(radius,-reachDown,0), 0, wallHeight, radius, ModBlocks.IDL_GLASS.getDefaultState());
        buildingCore.AddTaskBuildWallWithBlockCentered(origin.add(0, -reachDown, radius), radius, wallHeight, 0, ModBlocks.IDL_GLASS.getDefaultState());
        buildingCore.AddTaskBuildWallWithBlockCentered(origin.add(0, -reachDown, -radius), radius, wallHeight, 0, ModBlocks.IDL_GLASS.getDefaultState());

        buildingCore.AddTaskBuildWallWithBlockCentered(origin.add(0, -reachDown, 0), radius, 1, radius, ModBlocks.GRID_NORMAL.getDefaultState());
        //AddTaskBuildWallWithBlockCentered(origin.add(-floorReach,2,0), 0, windowHeight, 1, windowMaterial);
    }
}
