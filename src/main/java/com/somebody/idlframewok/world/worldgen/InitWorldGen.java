package com.somebody.idlframewok.world.worldgen;

import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitWorldGen {
    static int nextWeight = 100;
    static int layer1 = 10;
    static int layer2 = 32;
    static int layer3 = 64;
    static int layer4 = 128;


    public static void registerWorldGen()
    {
//        MapGenStructureIO.registerStructure(StructureBigDungeon.Start.class, StructureBigDungeon.NAME);
//        MapGenStructureIO.registerStructureComponent(ComponentDungeonRoomBase.class, "DunRoom");
//        MapGenStructureIO.registerStructureComponent(ComponentDungeonMircoMaze.class, "MazeRm");

//
//        register(new GenOre(ModBlocks.JADE_ORE.getDefaultState(), layer1, layer2, 4), nextWeight);
//        register(new GenRainOre(ModBlocks.RAIN_ORE.getDefaultState(), layer2, layer3, 4), nextWeight);

    }

    static void register(IWorldGenerator generator, int modGenerationWeight)
    {
        GameRegistry.registerWorldGenerator(generator, modGenerationWeight);
        nextWeight++;
    }
}
