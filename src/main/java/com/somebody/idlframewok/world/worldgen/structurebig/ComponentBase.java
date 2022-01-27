package com.somebody.idlframewok.world.worldgen.structurebig;

import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

import javax.annotation.Nullable;
import java.util.Random;

public class ComponentBase extends StructureComponent {
    protected Mirror mirrorIn;
    protected Rotation rotationIn;

    public ComponentBase() {
        //Make sure you have this!
        //Nearly abstract
    }

    //Remember that this will need to be enlisted in StructureStart.components
    //Other wise it will not generate
    public ComponentBase(int type) {
        super(type);
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound) {

    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {

    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        return false;
    }

    public void setCoordBaseMode(@Nullable EnumFacing facing) {
        super.setCoordBaseMode(facing);

        if (facing == null) {
            this.rotationIn = Rotation.NONE;
            this.mirrorIn = Mirror.NONE;
        } else {
            switch (facing) {
                case SOUTH:
                    this.mirrorIn = Mirror.NONE;//different from original
                    this.rotationIn = Rotation.NONE;
                    break;
                case WEST:
                    this.mirrorIn = Mirror.LEFT_RIGHT;
                    this.rotationIn = Rotation.CLOCKWISE_90;
                    break;
                case EAST:
                    this.mirrorIn = Mirror.NONE;
                    this.rotationIn = Rotation.CLOCKWISE_90;
                    break;
                default:
                    this.mirrorIn = Mirror.LEFT_RIGHT;//different from original
                    this.rotationIn = Rotation.NONE;
            }
        }
    }

    //change flags to avoid cwg
    protected void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
        BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));

        if (boundingboxIn.isVecInside(blockpos)) {
            if (this.mirrorIn != Mirror.NONE) {
                blockstateIn = blockstateIn.withMirror(this.mirrorIn);
            }

            if (this.rotationIn != Rotation.NONE) {
                blockstateIn = blockstateIn.withRotation(this.rotationIn);
            }
            //some of the doors failed to penetrate.
            WorldGenUtil.setBlockState(worldIn, blockpos, blockstateIn, WorldGenUtil.PREVENT_CASCADE_FLAGS);
        }
    }

    protected void logPos(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
        BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));

        if (boundingboxIn.isVecInside(blockpos)) {
            if (this.mirrorIn != Mirror.NONE) {
                blockstateIn = blockstateIn.withMirror(this.mirrorIn);
            }

            if (this.rotationIn != Rotation.NONE) {
                blockstateIn = blockstateIn.withRotation(this.rotationIn);
            }
//            Idealland.Log("Target Pos:%s", blockpos);
        }
    }

    protected void generateMaybeBoxXZDeco(World worldIn, StructureBoundingBox sbb, Random rand, float chance, int x1, int y1, int z1, int x2, int y2, int z2, IBlockState edgeState, IBlockState state, boolean requireNonAir, int requiredSkylight) {
        for (int i = y1; i <= y2; ++i) {
            for (int j = x1; j <= x2; ++j) {
                for (int k = z1; k <= z2; ++k) {
                    if (rand.nextFloat() <= chance && (!requireNonAir || this.getBlockStateFromPos(worldIn, j, i, k, sbb).getMaterial() != Material.AIR) && (requiredSkylight <= 0 || this.getSkyBrightness(worldIn, j, i, k, sbb) < requiredSkylight)) {
                        if (i != y1 && i != y2 && j != x1 && j != x2 && k != z1 && k != z2) {
                            this.setBlockState(worldIn, state, j, i, k, sbb);
                        } else {
                            this.setBlockState(worldIn, edgeState, j, i, k, sbb);
                        }
                    }
                }
            }
        }
    }

    //This is not random. This is for creating a sphere.
    @Override
    protected void randomlyRareFillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, IBlockState blockstateIn, boolean excludeAir) {
        super.randomlyRareFillWithBlocks(worldIn, boundingboxIn, minX, minY, minZ, maxX, maxY, maxZ, blockstateIn, excludeAir);
    }

    @Override
    protected void fillWithRandomizedBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean notAirOnly, Random rand, BlockSelector blockselector) {
        super.fillWithRandomizedBlocks(worldIn, boundingboxIn, minX, minY, minZ, maxX, maxY, maxZ, notAirOnly, rand, blockselector);
    }
}
