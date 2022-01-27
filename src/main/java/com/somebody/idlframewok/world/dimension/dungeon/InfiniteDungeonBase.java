package com.somebody.idlframewok.world.dimension.dungeon;

import com.somebody.idlframewok.world.worldgen.structure.IDFGenStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfiniteDungeonBase {

    public IDFGenStructure structureBase;

    public InfiniteDungeonBase(String baseStuctureName) {
        this.structureBase = new IDFGenStructure(baseStuctureName);
    }

    public InfiniteDungeonBase(IDFGenStructure baseStucture) {
        this.structureBase = baseStucture;
    }

    public void createRoom(World world, BlockPos pos){
        structureBase.generateStructure(world, pos);
    }
}

