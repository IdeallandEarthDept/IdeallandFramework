package com.somebody.idlframewok.blocks.Furnitures;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.init.ModCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockExtractionDoorTest extends BlockBase {
    public BlockExtractionDoorTest(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(5.0F);
        setResistance(35.0F);
        setLightOpacity(1);

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.NORTH).withProperty(POWERED, Boolean.valueOf(false)));
        this.setCreativeTab(ModCreativeTab.IDL_MISC);
    }

    public static final PropertyEnum<BlockExtractionDoorTest.EnumOrientation> FACING = PropertyEnum.create("facing", BlockExtractionDoorTest.EnumOrientation.class);
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    protected static final AxisAlignedBB LEVER_NORTH_AABB = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);
    protected static final AxisAlignedBB LEVER_SOUTH_AABB = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);
    protected static final AxisAlignedBB LEVER_WEST_AABB  = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);
    protected static final AxisAlignedBB LEVER_EAST_AABB  = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);
    protected static final AxisAlignedBB LEVER_UP_AABB    = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);
    protected static final AxisAlignedBB LEVER_DOWN_AABB  = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);

    protected static final AxisAlignedBB OPENED_LEVER_NORTH_AABB = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);
    protected static final AxisAlignedBB OPENED_LEVER_SOUTH_AABB = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);
    protected static final AxisAlignedBB OPENED_LEVER_WEST_AABB  = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);
    protected static final AxisAlignedBB OPENED_LEVER_EAST_AABB  = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);
    protected static final AxisAlignedBB OPENED_LEVER_UP_AABB    = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);
    protected static final AxisAlignedBB OPENED_LEVER_DOWN_AABB  = new AxisAlignedBB(0D,0D,0D,1D,1D,1D);


    @Deprecated
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        if (blockState.getValue(POWERED)) {
            return null;
        }
        else
        {
            return blockState.getBoundingBox(worldIn, pos);
        }
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return !state.getValue(POWERED);
    }

    public boolean isFullCube(IBlockState state)
    {
        return !state.getValue(POWERED);
    }

    public boolean causesSuffocation(IBlockState state)
    {
        return false;
    }

    /**
     * Check whether this Block can be placed at pos, while aiming at the specified side of an adjacent block
     */
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return canAttachTo(worldIn, pos, side);
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : EnumFacing.values())
        {
            if (canAttachTo(worldIn, pos, enumfacing))
            {
                return true;
            }
        }

        return false;
    }

    protected static boolean canAttachTo(World worldIn, BlockPos p_181090_1_, EnumFacing p_181090_2_)
    {
        return true;
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(POWERED, Boolean.valueOf(false));

        if (canAttachTo(worldIn, pos, facing))
        {
            return iblockstate.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.forFacings(facing, placer.getHorizontalFacing()));
        }
        else
        {
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
            {
                if (enumfacing != facing && canAttachTo(worldIn, pos, enumfacing))
                {
                    return iblockstate.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.forFacings(enumfacing, placer.getHorizontalFacing()));
                }
            }

            if (worldIn.getBlockState(pos.down()).isTopSolid())
            {
                return iblockstate.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.forFacings(EnumFacing.UP, placer.getHorizontalFacing()));
            }
            else
            {
                return iblockstate;
            }
        }
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (this.checkCanSurvive(worldIn, pos, state) && !canAttachTo(worldIn, pos, state.getValue(FACING).getFacing()))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    private boolean checkCanSurvive(World worldIn, BlockPos pos, IBlockState state)
    {
        if (this.canPlaceBlockAt(worldIn, pos))
        {
            return true;
        }
        else
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING))
        {
            case EAST:
            default:
                return LEVER_EAST_AABB;
            case WEST:
                return LEVER_WEST_AABB;
            case SOUTH:
                return LEVER_SOUTH_AABB;
            case NORTH:
                return LEVER_NORTH_AABB;
            case UP_Z:
            case UP_X:
                return LEVER_UP_AABB;
            case DOWN_X:
            case DOWN_Z:
                return LEVER_DOWN_AABB;
        }
    }

    /**
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            Toggle(worldIn, state, pos);
            float f = state.getValue(POWERED) ? 0.6F : 0.5F;
            worldIn.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);

            OnNotifySync(worldIn, pos.add(1,0,0),  !state.getValue(POWERED));
            OnNotifySync(worldIn, pos.add(-1,0,0), !state.getValue(POWERED));
            OnNotifySync(worldIn, pos.add(0,1,0),  !state.getValue(POWERED));
            OnNotifySync(worldIn, pos.add(0,-1,0), !state.getValue(POWERED));
            OnNotifySync(worldIn, pos.add(0,0,1),  !state.getValue(POWERED));
            OnNotifySync(worldIn, pos.add(0,0,-1), !state.getValue(POWERED));
            return true;
        }
    }

    public void Toggle(World worldIn, IBlockState state,BlockPos pos)
    {
        state = state.cycleProperty(POWERED);
        worldIn.setBlockState(pos, state, 3);
        worldIn.notifyNeighborsOfStateChange(pos, this, false);
        EnumFacing enumfacing = state.getValue(FACING).getFacing();
        worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this, false);
    }

    public static void OnNotifySync(World world, BlockPos pos, boolean toOpen) {
        IBlockState block = world.getBlockState(pos);

        if (block.getBlock() instanceof BlockExtractionDoorTest) {
            BlockExtractionDoorTest door = (BlockExtractionDoorTest)(block.getBlock());
            if (isOpened(block) != toOpen){
                door.Toggle(world, block, pos);
                OnNotifySync(world, pos.add(1,0,0),  !block.getValue(POWERED));
                OnNotifySync(world, pos.add(-1,0,0), !block.getValue(POWERED));
                OnNotifySync(world, pos.add(0,1,0),  !block.getValue(POWERED));
                OnNotifySync(world, pos.add(0,-1,0), !block.getValue(POWERED));
                OnNotifySync(world, pos.add(0,0,1),  !block.getValue(POWERED));
                OnNotifySync(world, pos.add(0,0,-1), !block.getValue(POWERED));
            }
        }
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (state.getValue(POWERED).booleanValue())
        {
            worldIn.notifyNeighborsOfStateChange(pos, this, false);
            EnumFacing enumfacing = state.getValue(FACING).getFacing();
            worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this, false);
        }

        super.breakBlock(worldIn, pos, state);
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.byMetadata(meta & 7)).withProperty(POWERED, Boolean.valueOf((meta & 8) > 0));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FACING).getMetadata();

        if (state.getValue(POWERED))
        {
            i |= 8;
        }

        return i;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch (rot)
        {
            case CLOCKWISE_180:

                switch (state.getValue(FACING))
                {
                    case EAST:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.WEST);
                    case WEST:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.EAST);
                    case SOUTH:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.NORTH);
                    case NORTH:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.SOUTH);
                    default:
                        return state;
                }

            case COUNTERCLOCKWISE_90:

                switch (state.getValue(FACING))
                {
                    case EAST:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.NORTH);
                    case WEST:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.SOUTH);
                    case SOUTH:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.EAST);
                    case NORTH:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.WEST);
                    case UP_Z:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.UP_X);
                    case UP_X:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.UP_Z);
                    case DOWN_X:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.DOWN_Z);
                    case DOWN_Z:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.DOWN_X);
                }

            case CLOCKWISE_90:

                switch (state.getValue(FACING))
                {
                    case EAST:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.SOUTH);
                    case WEST:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.NORTH);
                    case SOUTH:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.WEST);
                    case NORTH:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.EAST);
                    case UP_Z:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.UP_X);
                    case UP_X:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.UP_Z);
                    case DOWN_X:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.DOWN_Z);
                    case DOWN_Z:
                        return state.withProperty(FACING, BlockExtractionDoorTest.EnumOrientation.DOWN_X);
                }

            default:
                return state;
        }
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING).getFacing()));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, POWERED);
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return isOpened(worldIn, pos);
    }

    public static boolean isOpened(IBlockAccess worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos).getValue(POWERED);
    }

    public static boolean isOpened(IBlockState state)
    {
        return state.getValue(POWERED);
    }

    public enum EnumOrientation implements IStringSerializable
    {
        DOWN_X(0, "down_x", EnumFacing.DOWN),
        EAST(1, "east", EnumFacing.EAST),
        WEST(2, "west", EnumFacing.WEST),
        SOUTH(3, "south", EnumFacing.SOUTH),
        NORTH(4, "north", EnumFacing.NORTH),
        UP_Z(5, "up_z", EnumFacing.UP),
        UP_X(6, "up_x", EnumFacing.UP),
        DOWN_Z(7, "down_z", EnumFacing.DOWN);

        private static final BlockExtractionDoorTest.EnumOrientation[] META_LOOKUP = new BlockExtractionDoorTest.EnumOrientation[values().length];
        private final int meta;
        private final String name;
        private final EnumFacing facing;

        EnumOrientation(int meta, String name, EnumFacing facing)
        {
            this.meta = meta;
            this.name = name;
            this.facing = facing;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        public EnumFacing getFacing()
        {
            return this.facing;
        }

        public String toString()
        {
            return this.name;
        }

        public static BlockExtractionDoorTest.EnumOrientation byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        public static BlockExtractionDoorTest.EnumOrientation forFacings(EnumFacing clickedSide, EnumFacing entityFacing)
        {
            switch (clickedSide)
            {
                case DOWN:

                    switch (entityFacing.getAxis())
                    {
                        case X:
                            return DOWN_X;
                        case Z:
                            return DOWN_Z;
                        default:
                            throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
                    }

                case UP:

                    switch (entityFacing.getAxis())
                    {
                        case X:
                            return UP_X;
                        case Z:
                            return UP_Z;
                        default:
                            throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
                    }

                case NORTH:
                    return NORTH;
                case SOUTH:
                    return SOUTH;
                case WEST:
                    return WEST;
                case EAST:
                    return EAST;
                default:
                    throw new IllegalArgumentException("Invalid facing: " + clickedSide);
            }
        }

        public String getName()
        {
            return this.name;
        }

        static
        {
            for (BlockExtractionDoorTest.EnumOrientation blocklever$enumorientation : values())
            {
                META_LOOKUP[blocklever$enumorientation.getMetadata()] = blocklever$enumorientation;
            }
        }
    }

}
