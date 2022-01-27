package com.somebody.idlframewok.blocks.blockDungeon.gargoyle;

import com.somebody.idlframewok.entity.creatures.mobs.gargoyle.EntityBasicGargoyle;
import com.somebody.idlframewok.entity.creatures.mobs.gargoyle.EntityGargoyleBase;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class BlockGargoyleHead extends BlockGargoyleBase {
    public int maxHomeDistance = 8;
    public BlockGargoyleHead(String name, Material material) {
        super(name, material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    public static final UUID UUID_BODY_BOOST = UUID.fromString("51591f1c-f1ac-4f1d-9bd0-423a5cb85c8d");
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public void trigger(World world, BlockPos pos) {
        if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
            return;
        }
        BlockPos.MutableBlockPos pointer = new BlockPos.MutableBlockPos(pos);

        int bodyCount = 0;
        while (true) {
            pointer.move(EnumFacing.DOWN);
            IBlockState state = world.getBlockState(pointer);
            if (state.getBlock() instanceof BlockGargoyleBody) {
                if (!world.isRemote) {
                    world.setBlockState(pointer, WorldGenUtil.AIR);
                }
                bodyCount++;
            } else {
                break;
            }
        }

        if (bodyCount > 0) {
            spawnGargoyle(world, pointer.add(0, 1, 0), bodyCount);
            if (!world.isRemote) {
                world.setBlockState(pos, WorldGenUtil.AIR);
            }
        }
    }


    public void spawnGargoyle(World world, BlockPos pos, int bodyCount) {
        if (!world.isRemote) {
            EntityGargoyleBase gargoyleBase = new EntityBasicGargoyle(world);
            EntityUtil.setPosition(gargoyleBase, pos);
            world.spawnEntity(gargoyleBase);
            handleAttr(gargoyleBase, bodyCount);
            gargoyleBase.setHealth(gargoyleBase.getMaxHealth());
            gargoyleBase.setHomePosAndDistance(pos, maxHomeDistance);
            //gargoyleBase.setLocationAndAngles();
        }
    }

    public void handleAttr(EntityGargoyleBase gargoyle, int bodyCount) {
        EntityUtil.boostAttrRatio(gargoyle, SharedMonsterAttributes.MAX_HEALTH, (bodyCount - 1), UUID_BODY_BOOST);
        EntityUtil.boostAttrRatio(gargoyle, SharedMonsterAttributes.ATTACK_DAMAGE, (bodyCount - 1) * 0.5f, UUID_BODY_BOOST);
    }

    //direction

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
}
