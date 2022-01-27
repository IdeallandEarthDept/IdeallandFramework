package com.somebody.idlframewok.blocks.uprising;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.PlayerUtil;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockLandmine extends BlockBase {
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.4D, 0.0D, 0.4D, 0.6D, 0.21, 0.6D);
    EntityUtil.EnumFaction faction = EntityUtil.EnumFaction.MOB_VANILLA;
    int power = 1;

    public BlockLandmine(String name, Material material, EntityUtil.EnumFaction faction, int power) {
        super(name, material);
        this.faction = faction;
        this.power = power;
        setPickable(true);
        setSoundType(SoundType.ANVIL);
        setLightOpacity(0);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);
        //will not trigger if AABB too small.
        if (shouldTrigger(entityIn))
            kaboom(worldIn, pos);
    }

    boolean shouldTrigger(Entity entityIn) {
        if (entityIn instanceof EntityLivingBase) {
            if (entityIn instanceof EntityPlayer && PlayerUtil.isCreative((EntityPlayer) entityIn)) {
                return false;
            }

            if (EntityUtil.getAttitude(faction, (EntityLivingBase) entityIn) == EntityUtil.EnumAttitude.HATE)
                return true;
        }

        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (shouldTrigger(playerIn)) {
            kaboom(worldIn, pos);
            return true;
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    void kaboom(World worldIn, BlockPos pos) {
        if (worldIn.isRemote) {
            return;
        }

        worldIn.setBlockState(pos, WorldGenUtil.AIR);
        worldIn.createExplosion(null,
                pos.getX() + 0.5f, pos.getY() + 0.01f, pos.getZ() + 0.5f,
                power, false);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (worldIn.isBlockPowered(pos)) {
            kaboom(worldIn, pos);
            return;
        }

        if (!canPlaceOn(worldIn, pos.down())) {
            if (worldIn.rand.nextInt(10) <= 3) {
                dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockState(pos, WorldGenUtil.AIR);
            } else {
                kaboom(worldIn, pos);
            }
        }
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }

    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!worldIn.isRemote) {
            if (entityIn instanceof IProjectile) {
                kaboom(worldIn, pos);
            }

        }

        if (shouldTrigger(entityIn)) {
            kaboom(worldIn, pos);
        }
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
        kaboom(world, pos);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return AABB;
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    private boolean canPlaceOn(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        return state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return this.canPlaceAt(worldIn, pos, EnumFacing.UP);
    }

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

        if (facing.equals(EnumFacing.UP) && this.canPlaceOn(worldIn, blockpos)) {
            return true;
        } else if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
            return !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID;
        } else {
            return false;
        }
    }
}
