package com.somebody.idlframewok.blocks.rail;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.init.RegistryHandler;
import com.somebody.idlframewok.util.IHasModel;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.block.BlockRail.SHAPE;

//prevent cascade world gen
public class ModBlockRailBase extends BlockRailBase implements IHasModel {
    public ModBlockRailBase(String name) {
        super(true);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabsList.IDL_WORLD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
        ModBlocks.BLOCKS.add(this);
        addToItems();
    }

    public void addToItems()
    {
        RegistryHandler.addToItems(this);
    }

    protected IBlockState updateDir(World worldIn, BlockPos pos, IBlockState state, boolean initialPlacement)
    {
        return worldIn.isRemote ? state : (new ModRail(worldIn, pos, state)).place(false, initialPlacement).getBlockState();
    }

    public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty()
    {
        return SHAPE;
    }

    /**
     * Convert the given metadata into a BlockState for this BlockPhasingOre
     */
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(SHAPE).getMetadata();
    }

    /**
     * Returns the blockstate with the given rotationIn from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @SuppressWarnings({"incomplete-switch","deprecation"})
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case CLOCKWISE_180:

                switch ((BlockRailBase.EnumRailDirection)state.getValue(SHAPE))
                {
                    case ASCENDING_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
                    case SOUTH_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
                    case SOUTH_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
                    case NORTH_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
                    case NORTH_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
                }

            case COUNTERCLOCKWISE_90:

                switch ((BlockRailBase.EnumRailDirection)state.getValue(SHAPE))
                {
                    case ASCENDING_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
                    case ASCENDING_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
                    case ASCENDING_NORTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
                    case ASCENDING_SOUTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
                    case SOUTH_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
                    case SOUTH_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
                    case NORTH_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
                    case NORTH_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
                    case NORTH_SOUTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);
                    case EAST_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
                }

            case CLOCKWISE_90:

                switch ((BlockRailBase.EnumRailDirection)state.getValue(SHAPE))
                {
                    case ASCENDING_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
                    case ASCENDING_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
                    case ASCENDING_NORTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
                    case ASCENDING_SOUTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
                    case SOUTH_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
                    case SOUTH_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
                    case NORTH_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
                    case NORTH_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
                    case NORTH_SOUTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);
                    case EAST_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
                }

            default:
                return state;
        }
    }

    /**
     * Returns the blockstate with the given mirrorIn of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @SuppressWarnings({"incomplete-switch","deprecation"})
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue(SHAPE);

        switch (mirrorIn)
        {
            case LEFT_RIGHT:

                switch (blockrailbase$enumraildirection)
                {
                    case ASCENDING_NORTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
                    case SOUTH_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
                    case SOUTH_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
                    case NORTH_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
                    case NORTH_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
                    default:
                        return super.withMirror(state, mirrorIn);
                }

            case FRONT_BACK:

                switch (blockrailbase$enumraildirection)
                {
                    case ASCENDING_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                    case ASCENDING_SOUTH:
                    default:
                        break;
                    case SOUTH_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
                    case SOUTH_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
                    case NORTH_WEST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
                    case NORTH_EAST:
                        return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
                }
        }

        return super.withMirror(state, mirrorIn);
    }

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote && !WorldGenUtil.isStopUpdate()) {
            state = this.updateDir(worldIn, pos, state, true);

            if (this.isPowered) {
                state.neighborChanged(worldIn, pos, this, pos);
            }
        }
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, SHAPE);
    }
}
