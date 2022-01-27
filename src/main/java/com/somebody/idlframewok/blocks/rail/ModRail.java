package com.somebody.idlframewok.blocks.rail;

import com.somebody.idlframewok.util.WorldGenUtil;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

//copied from BlockRailBase.Rail
public class ModRail {
    private final World world;
    private final BlockPos pos;
    private final BlockRailBase block;
    private IBlockState state;
    private final boolean isPowered;
    private final List<BlockPos> connectedRails = Lists.<BlockPos>newArrayList();
    private final boolean canMakeSlopes;

    private boolean canMakeTurns;

    public ModRail(World worldIn, BlockPos pos, IBlockState state)
    {
        this.world = worldIn;
        this.pos = pos;
        this.state = state;
        this.block = (BlockRailBase)state.getBlock();
        BlockRailBase.EnumRailDirection ModBlockRailBase$enumraildirection = block.getRailDirection(worldIn, pos, state, null);
        this.isPowered = !this.block.isFlexibleRail(worldIn, pos);
        this.canMakeSlopes = this.block.canMakeSlopes(worldIn, pos);
        this.canMakeTurns = this.block.isFlexibleRail(worldIn, pos);

        this.updateConnectedRails(ModBlockRailBase$enumraildirection);
    }

    public List<BlockPos> getConnectedRails()
    {
        return this.connectedRails;
    }

    private void updateConnectedRails(BlockRailBase.EnumRailDirection railDirection)
    {
        this.connectedRails.clear();

        switch (railDirection)
        {
            case NORTH_SOUTH:
                this.connectedRails.add(this.pos.north());
                this.connectedRails.add(this.pos.south());
                break;
            case EAST_WEST:
                this.connectedRails.add(this.pos.west());
                this.connectedRails.add(this.pos.east());
                break;
            case ASCENDING_EAST:
                this.connectedRails.add(this.pos.west());
                this.connectedRails.add(this.pos.east().up());
                break;
            case ASCENDING_WEST:
                this.connectedRails.add(this.pos.west().up());
                this.connectedRails.add(this.pos.east());
                break;
            case ASCENDING_NORTH:
                this.connectedRails.add(this.pos.north().up());
                this.connectedRails.add(this.pos.south());
                break;
            case ASCENDING_SOUTH:
                this.connectedRails.add(this.pos.north());
                this.connectedRails.add(this.pos.south().up());
                break;
            case SOUTH_EAST:
                this.connectedRails.add(this.pos.east());
                this.connectedRails.add(this.pos.south());
                break;
            case SOUTH_WEST:
                this.connectedRails.add(this.pos.west());
                this.connectedRails.add(this.pos.south());
                break;
            case NORTH_WEST:
                this.connectedRails.add(this.pos.west());
                this.connectedRails.add(this.pos.north());
                break;
            case NORTH_EAST:
                this.connectedRails.add(this.pos.east());
                this.connectedRails.add(this.pos.north());
        }
    }

    private void removeSoftConnections()
    {
        for (int i = 0; i < this.connectedRails.size(); ++i)
        {
            ModRail ModBlockRailBase$rail = this.findRailAt(this.connectedRails.get(i));

            if (ModBlockRailBase$rail != null && ModBlockRailBase$rail.isConnectedToRail(this))
            {
                this.connectedRails.set(i, ModBlockRailBase$rail.pos);
            }
            else
            {
                this.connectedRails.remove(i--);
            }
        }
    }

    private boolean hasRailAt(BlockPos pos)
    {
        return BlockRailBase.isRailBlock(this.world, pos) || BlockRailBase.isRailBlock(this.world, pos.up()) || BlockRailBase.isRailBlock(this.world, pos.down());
    }

    @Nullable
    private ModRail findRailAt(BlockPos pos)
    {
        if (!WorldGenUtil.isBlockPosReady(this.world, pos))
        {
            return null;//prevent loading chunks
        }

        IBlockState iblockstate = this.world.getBlockState(pos);

        if (BlockRailBase.isRailBlock(iblockstate))
        {
            return new ModRail(this.world, pos, iblockstate);
        }
        else
        {
            BlockPos lvt_2_1_ = pos.up();
            iblockstate = this.world.getBlockState(lvt_2_1_);

            if (BlockRailBase.isRailBlock(iblockstate))
            {
                return new ModRail(this.world, lvt_2_1_, iblockstate);
            }
            else
            {
                lvt_2_1_ = pos.down();
                iblockstate = this.world.getBlockState(lvt_2_1_);
                return BlockRailBase.isRailBlock(iblockstate) ? new ModRail(this.world, lvt_2_1_, iblockstate) : null;
            }
        }
    }

    private boolean isConnectedToRail(ModRail rail)
    {
        return this.isConnectedTo(rail.pos);
    }

    private boolean isConnectedTo(BlockPos posIn)
    {
        for (int i = 0; i < this.connectedRails.size(); ++i)
        {
            BlockPos blockpos = this.connectedRails.get(i);

            if (blockpos.getX() == posIn.getX() && blockpos.getZ() == posIn.getZ())
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Counts the number of rails adjacent to this rail.
     */
    protected int countAdjacentRails()
    {
        int i = 0;

        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            if (this.hasRailAt(this.pos.offset(enumfacing)))
            {
                ++i;
            }
        }

        return i;
    }

    private boolean canConnectTo(ModRail rail)
    {
        return this.isConnectedToRail(rail) || this.connectedRails.size() != 2;
    }

    private void connectTo(ModRail rail)
    {
        this.connectedRails.add(rail.pos);
        BlockPos blockpos = this.pos.north();
        BlockPos blockpos1 = this.pos.south();
        BlockPos blockpos2 = this.pos.west();
        BlockPos blockpos3 = this.pos.east();
        boolean flag = this.isConnectedTo(blockpos);
        boolean flag1 = this.isConnectedTo(blockpos1);
        boolean flag2 = this.isConnectedTo(blockpos2);
        boolean flag3 = this.isConnectedTo(blockpos3);
        BlockRailBase.EnumRailDirection ModBlockRailBase$enumraildirection = null;

        if (flag || flag1)
        {
            ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
        }

        if (flag2 || flag3)
        {
            ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
        }

        if (!this.isPowered)
        {
            if (flag1 && flag3 && !flag && !flag2)
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
            }

            if (flag1 && flag2 && !flag && !flag3)
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
            }

            if (flag && flag2 && !flag1 && !flag3)
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
            }

            if (flag && flag3 && !flag1 && !flag2)
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
            }
        }

        if (ModBlockRailBase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH && canMakeSlopes)
        {
            if (WorldGenUtil.isBlockPosReady(this.world, blockpos) && BlockRailBase.isRailBlock(this.world, blockpos.up()))
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
            }

            if (WorldGenUtil.isBlockPosReady(this.world, blockpos1) && BlockRailBase.isRailBlock(this.world, blockpos1.up()))
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
            }
        }

        if (ModBlockRailBase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST && canMakeSlopes)
        {
            if (WorldGenUtil.isBlockPosReady(this.world, blockpos3) && BlockRailBase.isRailBlock(this.world, blockpos3.up()))
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
            }

            if (WorldGenUtil.isBlockPosReady(this.world, blockpos2) && BlockRailBase.isRailBlock(this.world, blockpos2.up()))
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
            }
        }

        if (ModBlockRailBase$enumraildirection == null)
        {
            ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
        }

        this.state = this.state.withProperty(this.block.getShapeProperty(), ModBlockRailBase$enumraildirection);
        this.world.setBlockState(this.pos, this.state, 3);
    }

    private boolean hasNeighborRail(BlockPos posIn)
    {
        ModRail ModBlockRailBase$rail = this.findRailAt(posIn);

        if (ModBlockRailBase$rail == null)
        {
            return false;
        }
        else
        {
            ModBlockRailBase$rail.removeSoftConnections();
            return ModBlockRailBase$rail.canConnectTo(this);
        }
    }

    public ModRail place(boolean powered, boolean initialPlacement)
    {
        BlockPos blockpos = this.pos.north();
        BlockPos blockpos1 = this.pos.south();
        BlockPos blockpos2 = this.pos.west();
        BlockPos blockpos3 = this.pos.east();
        boolean flag = this.hasNeighborRail(blockpos);
        boolean flag1 = this.hasNeighborRail(blockpos1);
        boolean flag2 = this.hasNeighborRail(blockpos2);
        boolean flag3 = this.hasNeighborRail(blockpos3);
        BlockRailBase.EnumRailDirection ModBlockRailBase$enumraildirection = null;

        if ((flag || flag1) && !flag2 && !flag3)
        {
            ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
        }

        if ((flag2 || flag3) && !flag && !flag1)
        {
            ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
        }

        if (this.canMakeTurns)//edited
        {
            //making turns
            if (flag1 && flag3 && !flag && !flag2)
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
            }

            if (flag1 && flag2 && !flag && !flag3)
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
            }

            if (flag && flag2 && !flag1 && !flag3)
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
            }

            if (flag && flag3 && !flag1 && !flag2)
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
            }
        }

        if (ModBlockRailBase$enumraildirection == null)
        {
            if (flag || flag1)
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            }

            if (flag2 || flag3)
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
            }

            if (this.canMakeTurns)//edited.
            {
                //for changing direction when powered.
                if (powered)
                {
                    if (flag1 && flag3)
                    {
                        ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
                    }

                    if (flag2 && flag1)
                    {
                        ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
                    }

                    if (flag3 && flag)
                    {
                        ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
                    }

                    if (flag && flag2)
                    {
                        ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
                    }
                }
                else
                {
                    if (flag && flag2)
                    {
                        ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
                    }

                    if (flag3 && flag)
                    {
                        ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
                    }

                    if (flag2 && flag1)
                    {
                        ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
                    }

                    if (flag1 && flag3)
                    {
                        ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
                    }
                }
            }
        }

        if (ModBlockRailBase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH && canMakeSlopes)
        {
            if (WorldGenUtil.isBlockPosReady(this.world, blockpos) && BlockRailBase.isRailBlock(this.world, blockpos.up()))
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
            }

            if (WorldGenUtil.isBlockPosReady(this.world, blockpos1) && BlockRailBase.isRailBlock(this.world, blockpos1.up()))
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
            }
        }

        if (ModBlockRailBase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST && canMakeSlopes)
        {
            if (WorldGenUtil.isBlockPosReady(this.world, blockpos3) && BlockRailBase.isRailBlock(this.world, blockpos3.up()))
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
            }

            if (WorldGenUtil.isBlockPosReady(this.world, blockpos2) && BlockRailBase.isRailBlock(this.world, blockpos2.up()))
            {
                ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
            }
        }

        if (ModBlockRailBase$enumraildirection == null)
        {
            ModBlockRailBase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
        }

        this.updateConnectedRails(ModBlockRailBase$enumraildirection);
        this.state = this.state.withProperty(this.block.getShapeProperty(), ModBlockRailBase$enumraildirection);

        if (initialPlacement || this.world.getBlockState(this.pos) != this.state)
        {
            this.world.setBlockState(this.pos, this.state, 3);

            for (int i = 0; i < this.connectedRails.size(); ++i)
            {
                ModRail ModBlockRailBase$rail = this.findRailAt(this.connectedRails.get(i));

                if (ModBlockRailBase$rail != null)
                {
                    ModBlockRailBase$rail.removeSoftConnections();

                    if (ModBlockRailBase$rail.canConnectTo(this))
                    {
                        ModBlockRailBase$rail.connectTo(this);
                    }
                }
            }
        }

        return this;
    }

    public IBlockState getBlockState()
    {
        return this.state;
    }
    
}
