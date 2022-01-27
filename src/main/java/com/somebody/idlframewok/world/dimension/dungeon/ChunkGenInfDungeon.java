package com.somebody.idlframewok.world.dimension.dungeon;

import com.somebody.idlframewok.world.dimension.ChunkGenBase;
import com.somebody.idlframewok.world.worldgen.structure.IDFGenStructure;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class ChunkGenInfDungeon extends ChunkGenBase {
    IDFGenStructure baseUnitStruct = new IDFGenStructure("dungeon_unit");
    IDFGenStructure baseUnitStruct_next = new IDFGenStructure("dungeon_secret_next");
    IDFGenStructure baseUnitStruct_no_room = new IDFGenStructure("dungeon_unit_no_room");
    IDFGenStructure baseUnitSubwayStruct = new IDFGenStructure("dungeon_subway_station");

    IDFGenStructure[] ROOMS_5 = {
            //new IDFGenStructure("rooms/room_bed"),//bed
            //new IDFGenStructure("rooms/room_dead_bush"),//flower pot
            new IDFGenStructure("rooms/room_enchant"),
            //new IDFGenStructure("rooms/room_furnace"),//furnace
            new IDFGenStructure("rooms/room_spider"),
            new IDFGenStructure("rooms/room_store_base"),
            //new IDFGenStructure("rooms/room_trap_store"), //tripWire cascade world gen
            new IDFGenStructure("rooms/room_wood")
    };

    InfiniteDungeonBasic unitBasic = new InfiniteDungeonBasic(baseUnitStruct, ROOMS_5);
    InfiniteDungeonBase unitNext = new InfiniteDungeonBase(baseUnitStruct_next);
    InfiniteDungeonBase unitNoRoom = new InfiniteDungeonBase(baseUnitStruct_no_room);
    InfiniteDungeonBase unitSubway = new InfiniteDungeonBase(baseUnitSubwayStruct);

    int maxLayer = 8;
    int maxHeight = maxLayer * CHUNK_SIZE;

    final int SUBWAY_PERIOD = 12;
    final int SUBWAY_INDEX = 11;

    public static class UnitOption extends WeightedRandom.Item
    {
        public InfiniteDungeonBase type;
        public UnitOption(int weight, InfiniteDungeonBase type)
        {
            super(weight);
            this.type = type;
        }

        @Override
        public boolean equals(Object target)
        {
            return target instanceof UnitOption && type.equals(((UnitOption)target).type);
        }
    }

    List<UnitOption> structureList = new ArrayList<>();

    public ChunkGenInfDungeon(World worldIn, long seed, boolean mapFeaturesEnabledIn, String generatorOptions) {
        super(worldIn, seed, mapFeaturesEnabledIn, generatorOptions);
        hasBedrockFloor = false;
        structureList.add(new UnitOption(100, unitBasic));
        structureList.add(new UnitOption(20, unitNext));
        structureList.add(new UnitOption(80, unitNoRoom));
        //structureList.add(new UnitOption(100, unitBasic));
    }

    @Override
    public void populate(int x, int z) {
        super.populate(x, z);
        int posX = x * CHUNK_SIZE;
        int posZ = z * CHUNK_SIZE;

//        if (ModConfig.DEBUG_CONF.SWITCH_A)
//        {
//            return;
//        }

        for (int y = 0; y < maxHeight; y+=CHUNK_SIZE )
        {
            InfiniteDungeonBase picked = unitBasic;
            if (x % SUBWAY_PERIOD == SUBWAY_INDEX)
            {
                picked = unitSubway;
            }
            else
            {
                picked = WeightedRandom.getRandomItem(rand, structureList).type;
            }

            if (picked != null)
            {
                picked.createRoom(world, new BlockPos(posX, y, posZ));
            }
        }
    }
}
