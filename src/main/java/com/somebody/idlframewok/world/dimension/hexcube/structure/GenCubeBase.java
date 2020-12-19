package com.somebody.idlframewok.world.dimension.hexcube.structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GenCubeBase extends WorldGenerator {

    protected boolean hasDoorX;
    protected boolean hasDoorY;
    protected boolean hasDoorZ;

    protected boolean hasLight;

    protected int xSize, ySize, zSize;

    public void setSize(int sizeX, int sizeY, int sizeZ) {
        this.xSize = sizeX;
        this.ySize = sizeY;
        this.zSize = sizeZ;
    }

    public GenCubeBase(boolean notify) {
        this(notify, 16, 16, 16);
    }

    public GenCubeBase(boolean notify, int xSize, int ySize, int zSize) {
        super(notify);
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
    }

    public GenCubeBase(boolean notify, int xSize, int ySize) {
        this(notify, xSize, ySize, xSize);
    }

    //reuse the object to reduce GC.
    public void setHasDoorXYZ(boolean hasDoorX, boolean hasDoorY, boolean hasDoorZ) {
        this.hasDoorX = hasDoorX;
        this.hasDoorY = hasDoorY;
        this.hasDoorZ = hasDoorZ;
    }

    public void setHasLightXYZ(boolean hasLight)
    {
       this.hasLight = hasLight;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos positionOrigin) {
        return false;
    }


}
