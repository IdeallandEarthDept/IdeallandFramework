package com.somebody.idlframewok.world.worldgen;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.world.worldgen.hill.ModGenTurret;
import com.somebody.idlframewok.world.worldgen.misc.ModGenMentorRest;
import com.somebody.idlframewok.world.worldgen.misc.ModGenMoroonCube;
import com.somebody.idlframewok.world.worldgen.misc.ModGenSkeletonFactory;
import com.somebody.idlframewok.world.worldgen.ocean.ModGenPrismarine;
import com.somebody.idlframewok.world.worldgen.ocean.ModGenPrismarineMoroon;
import com.somebody.idlframewok.world.worldgen.ocean.ModGenSpine;
import com.somebody.idlframewok.world.worldgen.ocean.ModGenWaterPillar;
import com.somebody.idlframewok.world.worldgen.ore.GenOre;
import com.somebody.idlframewok.world.worldgen.ore.GenRainOre;
import com.somebody.idlframewok.world.worldgen.structure.WorldGenCustomStructures;
import com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.ComponentDungeonMircoMaze;
import com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.ComponentDungeonRoomBase;
import com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.StructureBigDungeon;
import com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.bigdungeon2.ComponentMazeDungeon;
import com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.bigdungeon2.StructureMazeDungeon;
import com.somebody.idlframewok.world.worldgen.structurebig.test.ModGenStructureTest;
import com.somebody.idlframewok.world.worldgen.structurebig.test.TestComponentBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
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
        MapGenStructureIO.registerStructure(ModGenStructureTest.Start.class, ModGenStructureTest.NAME);
        MapGenStructureIO.registerStructureComponent(TestComponentBase.class, "TestComp");

        MapGenStructureIO.registerStructure(StructureBigDungeon.Start.class, StructureBigDungeon.NAME);
        MapGenStructureIO.registerStructureComponent(ComponentDungeonRoomBase.class, "DunRoom");
        MapGenStructureIO.registerStructureComponent(ComponentDungeonMircoMaze.class, "MazeRm");

        MapGenStructureIO.registerStructure(StructureMazeDungeon.Start.class, StructureMazeDungeon.NAME);
        MapGenStructureIO.registerStructureComponent(ComponentMazeDungeon.class, "MazeRm2");

        register(new GenOre(ModBlocks.JADE_ORE.getDefaultState(), layer1, layer2, 4), nextWeight);
        register(new GenRainOre(ModBlocks.RAIN_ORE.getDefaultState(), layer2, layer3, 4), nextWeight);

        register(new ModGenSpine(), nextWeight);
        register(new ModGenWaterPillar(), nextWeight);

        register(new ModGenPrismarine(), nextWeight);
        register(new ModGenPrismarineMoroon(), nextWeight);

        register(new ModGenMentorRest(), nextWeight);

        register(new ModGenMoroonCube(), nextWeight);
        register(new ModGenSkeletonFactory(), nextWeight);

        register(new WorldGenCustomStructures(), nextWeight);

        register(new ModGenTurret(), nextWeight);
    }

    static void register(IWorldGenerator generator, int modGenerationWeight)
    {
        GameRegistry.registerWorldGenerator(generator, modGenerationWeight);
        nextWeight++;
    }
}
