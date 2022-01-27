package com.somebody.idlframewok.world.worldgen.structurebig.test;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import javax.annotation.Nullable;
import java.util.Random;

public class ModGenStructureTest extends MapGenStructure {
    public static final String NAME = "StructureOne";
    final int SEED_CONST = 20211231;
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
        if (chunkX % maxDistance == 0 && chunkZ % maxDistance == 0) {
            return true;
        }
        return false;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new Start(world, rand, chunkX, chunkZ);
    }


    //StructureStart
    public static class Start extends StructureStart {
        //Make sure you have this. Or it will error when reloading the game.
        public Start() {
        }

        public Start(World worldIn, Random rand, int x, int z) {
            super(x, z);

            BlockPos basePos = new BlockPos(x << 4, 200, z << 4);
            TestComponentBase firstComp = new TestComponentBase(1, rand.nextInt(16), basePos);
            components.add(firstComp);
            firstComp.buildComponent(firstComp, this.components, rand);
            updateBoundingBox();

            for (int xC = 1; xC <= 4; xC++) {
                for (int zC = 1; zC <= 4; zC++) {
                    TestComponentBase tempComp = getOne(rand, basePos.add(xC * 16, 0, zC * 16));
                    components.add(tempComp);
                    firstComp.buildComponent(firstComp, this.components, rand);
                    updateBoundingBox();
                }
            }

        }

        public TestComponentBase getOne(Random random, BlockPos pos) {
            return new TestComponentBase(1, random.nextInt(16), pos);
        }
    }
}
