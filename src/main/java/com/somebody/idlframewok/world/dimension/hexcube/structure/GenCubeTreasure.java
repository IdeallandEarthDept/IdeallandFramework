package com.somebody.idlframewok.world.dimension.hexcube.structure;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class GenCubeTreasure extends GenCubeBase {

    public GenCubeTreasure(boolean notify) {
        super(notify);
    }

    public GenCubeTreasure(boolean notify, int xSize, int ySize, int zSize) {
        super(notify, xSize, ySize, zSize);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos positionOrigin) {
        //positionOrigin is 0,0,0 of the room. the corner of walls
        int sideA = 2;
        int sideB = xSize - 2;

        int minZ = 2;
        int maxZ = zSize - 2;

        int yMax = ySize - 1;

        BlockPos[] posList = {
            positionOrigin.add(sideA,1, minZ),
            positionOrigin.add(sideA,1, maxZ),
            positionOrigin.add(sideB,1, minZ),
            positionOrigin.add(sideB,1, maxZ)
        };

        float hasLootChance = 0.25f;
        for (BlockPos chestPos:
             posList) {
            worldIn.setBlockState(chestPos, Blocks.CHEST.correctFacing(worldIn, positionOrigin.add(xSize>>1, 1, zSize >> 1), Blocks.CHEST.getDefaultState()), 2);
            TileEntity tileEntity1 = worldIn.getTileEntity(chestPos);

            if (tileEntity1 instanceof TileEntityChest)
            {
                if (rand.nextFloat() < hasLootChance)
                {
                    ((TileEntityChest)tileEntity1).setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
                }
                else {
                    hasLootChance += 0.25f;
                }

            }
        }

        return false;
    }


}
