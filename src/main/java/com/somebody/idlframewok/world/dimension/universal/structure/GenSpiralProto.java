package com.somebody.idlframewok.world.dimension.universal.structure;

import com.somebody.idlframewok.blocks.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GenSpiralProto extends WorldGenerator {
    protected int xSize = 16, ySize = 16, zSize = 16;

    enum DIR {

    }

    int xMin = 0;
    int xMax = 15;
    int zMin = 0;
    int zMax = 15;
    int yMin = 0;
    int yMax = 255;

    Vec3i[] dir =
            {
                    new Vec3i(1,0,0),
                    new Vec3i(-1,0,0),
                    new Vec3i(0,0,1),
                    new Vec3i(0,0,-1),
                    new Vec3i(0,1,0),
                    new Vec3i(0,-1,0),//never
            };

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        yMax = worldIn.getActualHeight() - 1;

        IBlockState blockState = ModBlocks.GRID_LAMP.getDefaultState();

        Vec3i pointer = new Vec3i(0,0,0);
        Vec3i curPointer = dir[0];
        int curPointerIndex = 0;
        Vec3i next = plus(pointer, curPointer);
        while (pointer.getY() < yMax)
        {
            if (isLegal(next))
            {
                worldIn.setBlockState(position.add(next), blockState);
                pointer = next;
                next = plus(pointer, curPointer);

                if (curPointerIndex == 4 && rand.nextInt(3) == 0)
                {
                    curPointerIndex = rand.nextInt(4);
                }
            }
            else
            {
                switch (curPointerIndex)
                {
                    case 0:
                        curPointerIndex = rand.nextInt(5);
                        break;
                    case 1:
                        curPointerIndex = rand.nextInt(5);
                        break;
                    case 2:
                        curPointerIndex = rand.nextInt(5);
                        break;
                    case 3:
                        curPointerIndex = rand.nextInt(5);
                        break;
                    case 4:
                        curPointerIndex = rand.nextInt(4);
                        break;
                    case 5:
                        curPointerIndex = 4;
                        break;
                }
            }
        }

        return false;
    }

    static Vec3i plus(Vec3i a, Vec3i b)
    {
        return new Vec3i(
                a.getX() + b.getX(),
                a.getY() + b.getY(),
                a.getZ() + b.getZ()
        );
    }

    boolean isLegal(Vec3i val)
    {
        int x = val.getX();
        int z = val.getZ();
        return x >= 0 && z >= 0 && x < xSize && z < zSize;
    }
}
