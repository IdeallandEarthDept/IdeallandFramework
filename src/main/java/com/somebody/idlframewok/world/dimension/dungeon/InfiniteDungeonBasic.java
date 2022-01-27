package com.somebody.idlframewok.world.dimension.dungeon;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.world.worldgen.structure.IDFGenStructure;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class InfiniteDungeonBasic extends InfiniteDungeonBase {
    public Vec3i[] ROOM_POS =
            {
                    new Vec3i(1, 12, 1),
                    new Vec3i(10, 12, 1),
                    new Vec3i(1, 12, 14),
                    new Vec3i(10, 12, 14),
            };

    public Mirror[] MIRROR = {
            Mirror.NONE,
            Mirror.NONE,
            Mirror.LEFT_RIGHT,
            Mirror.LEFT_RIGHT
    };

    //darn rail will cascade world gen. BlockRailBase.onBlockAdded @ updateDir
    public IDFGenStructure[] structRooms;

    public InfiniteDungeonBasic(IDFGenStructure baseStucture, IDFGenStructure[] structRooms) {
        super(baseStucture);
        this.structRooms = structRooms;
    }

    public void createRoom(World world, BlockPos pos){
        structureBase.generateStructure(world, pos);
        if (ModConfig.DEBUG_CONF.SWITCH_B)
        {
            return;
        }
        for (int i = 0; i < ROOM_POS.length; i++)
        {
            structRooms[world.rand.nextInt(structRooms.length)].generateStructure(world, pos.add(ROOM_POS[i]), MIRROR[i], Rotation.NONE);
        }
    }
}

