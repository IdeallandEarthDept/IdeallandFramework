package com.somebody.idlframewok.world.hellgate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PortalSize {
    private final World world;
    private final EnumFacing.Axis axis;
    public final EnumFacing rightDir;
    public final EnumFacing leftDir;
    public int portalBlockCount;
    public BlockPos bottomLeft;
    public int height;
    public int width;
    public Block acknowledgedBlock = Blocks.PORTAL;

    public int minXSize = 2;
    public int maxYSize = 21;

    public PortalSize(World world, BlockPos pos, EnumFacing.Axis axis, Block acknowledgedBlock) {
        this(world, pos, axis);
        this.acknowledgedBlock = acknowledgedBlock;
    }

    public PortalSize(World worldIn, BlockPos pos, EnumFacing.Axis axis) {
        this.world = worldIn;
        this.axis = axis;

        if (axis == EnumFacing.Axis.X) {
            this.leftDir = EnumFacing.EAST;
            this.rightDir = EnumFacing.WEST;
        } else {
            this.leftDir = EnumFacing.NORTH;
            this.rightDir = EnumFacing.SOUTH;
        }

        for (BlockPos blockpos = pos; pos.getY() > blockpos.getY() - maxYSize && pos.getY() > 0 && this.isEmptyBlock(worldIn.getBlockState(pos.down()).getBlock()); pos = pos.down()) {
            ;
        }

        int i = this.getDistanceUntilEdge(pos, this.leftDir) - 1;

        if (i >= 0) {
            this.bottomLeft = pos.offset(this.leftDir, i);
            this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);

            if (this.width < minXSize || this.width > maxYSize) {
                this.bottomLeft = null;
                this.width = 0;
            }
        }

        if (this.bottomLeft != null) {
            this.height = this.calculatePortalHeight();
        }
    }

    protected int getDistanceUntilEdge(BlockPos p_180120_1_, EnumFacing p_180120_2_) {
        int i;

        for (i = 0; i < 22; ++i) {
            BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i);

            if (!this.isEmptyBlock(this.world.getBlockState(blockpos).getBlock()) || this.world.getBlockState(blockpos.down()).getBlock() != Blocks.OBSIDIAN) {
                break;
            }
        }

        Block block = this.world.getBlockState(p_180120_1_.offset(p_180120_2_, i)).getBlock();
        return block == Blocks.OBSIDIAN ? i : 0;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    protected int calculatePortalHeight() {
        label56:

        for (this.height = 0; this.height < 21; ++this.height) {
            for (int i = 0; i < this.width; ++i) {
                BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
                Block block = this.world.getBlockState(blockpos).getBlock();

                if (!this.isEmptyBlock(block)) {
                    break label56;
                }

                if (block == acknowledgedBlock) {
                    ++this.portalBlockCount;
                }

                if (i == 0) {
                    block = this.world.getBlockState(blockpos.offset(this.leftDir)).getBlock();

                    if (block != Blocks.OBSIDIAN) {
                        break label56;
                    }
                } else if (i == this.width - 1) {
                    block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();

                    if (block != Blocks.OBSIDIAN) {
                        break label56;
                    }
                }
            }
        }

        for (int j = 0; j < this.width; ++j) {
            if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != Blocks.OBSIDIAN) {
                this.height = 0;
                break;
            }
        }

        if (this.height <= 21 && this.height >= 3) {
            return this.height;
        } else {
            this.bottomLeft = null;
            this.width = 0;
            this.height = 0;
            return 0;
        }
    }

    protected boolean isEmptyBlock(Block blockIn) {
        return blockIn.getMaterial(blockIn.getDefaultState()) == Material.AIR || blockIn == Blocks.FIRE || blockIn == acknowledgedBlock;
    }

    public boolean isValid() {
        return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
    }

    public void placePortalBlocks() {
        for (int i = 0; i < this.width; ++i) {
            BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

            for (int j = 0; j < this.height; ++j) {
                this.world.setBlockState(blockpos.up(j), acknowledgedBlock.getDefaultState().withProperty(BlockPortal.AXIS, this.axis), 2);
            }
        }
    }
}
