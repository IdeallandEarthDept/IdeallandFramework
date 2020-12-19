package com.somebody.idlframewok.world.dimension.hexcube.structure;

import net.minecraft.block.BlockTorch;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class GenCubeTorchRoom extends GenCubeBase {
    public GenCubeTorchRoom(boolean notify) {
        super(notify);
    }

    public GenCubeTorchRoom(boolean notify, int xSize, int ySize, int zSize) {
        super(notify, xSize, ySize, zSize);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos positionOrigin) {
        //positionOrigin is 0,0,0 of the room. the corner of walls
        int minY = hasLight ? 2 : 1;
        int min = 1;
        int max = xSize - 1;

        int minZ = 1;
        int maxZ = zSize - 1;

        int yMax = ySize - 1;

        //east +x
        //south +z
        for (int y = minY; y <= yMax; y++)
        {
            CreateLogAt(worldIn, positionOrigin.add(min, y, minZ), EnumFacing.SOUTH);
            CreateLogAt(worldIn, positionOrigin.add(min, y, maxZ), EnumFacing.EAST);
            CreateLogAt(worldIn, positionOrigin.add(max, y, maxZ), EnumFacing.NORTH);
            CreateLogAt(worldIn, positionOrigin.add(max, y, minZ), EnumFacing.WEST);
        }

        return false;
    }

    void CreateLogAt(World worldIn, BlockPos pos, EnumFacing facing)
    {
        //todo: prevent torch from falling
        if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() == Blocks.AIR)//does this work? need check
        {
            this.setBlockAndNotifyAdequately(worldIn, pos, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, facing));
        }


    }


}
