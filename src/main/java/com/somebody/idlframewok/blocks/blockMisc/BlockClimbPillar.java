package com.somebody.idlframewok.blocks.blockMisc;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.somebody.idlframewok.util.CommonDef.MAX_BUILD_HEIGHT;

public class BlockClimbPillar extends BlockBase {
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.4D, 0.0D, 0.4D, 0.6D, 1.0D, 0.6D);
    public BlockClimbPillar(String name, Material material) {
        super(name, material);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() == Item.getItemFromBlock(this))
        {
            if (!worldIn.isRemote) {
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
                blockpos$mutableblockpos.setPos(pos);
                for (int y = pos.getY() + 1; y <= MAX_BUILD_HEIGHT; y++)
                {
                    blockpos$mutableblockpos.setY(y);
                    if (worldIn.getBlockState(blockpos$mutableblockpos) == state)
                    {
                        continue;
                    }
                    else if (worldIn.getBlockState(blockpos$mutableblockpos).getBlock().isReplaceable(worldIn, pos))
                    {
                        worldIn.setBlockState(blockpos$mutableblockpos, getDefaultState());
                        if (!PlayerUtil.isCreative(playerIn))
                        {
                            stack.shrink(1);
                        }
                        return true;
                    }
                    else {
                        break;
                    }
                }

                for (int y = pos.getY() - 1; y >= 0; y--)
                {
                    blockpos$mutableblockpos.setY(y);
                    if (worldIn.getBlockState(blockpos$mutableblockpos) == state)
                    {
                        continue;
                    }
                    else if (worldIn.getBlockState(blockpos$mutableblockpos).getBlock().isReplaceable(worldIn, pos))
                    {
                        worldIn.setBlockState(blockpos$mutableblockpos, getDefaultState());
                        if (!PlayerUtil.isCreative(playerIn))
                        {
                            stack.shrink(1);
                        }
                        return true;
                    }
                    else {
                        break;
                    }
                }
                return false;
            }

            return true;
        }
        else {
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        }
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        if (face == EnumFacing.UP || face == EnumFacing.DOWN)
        {
            return BlockFaceShape.CENTER;
        }
        return BlockFaceShape.UNDEFINED;
    }

//    /**
//     * Check whether this BlockPhasingOre can be placed at pos, while aiming at the specified side of an adjacent block
//     */
//    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
//    {
//        return side == EnumFacing.DOWN || side == EnumFacing.UP || this.canPlaceBlockAt(worldIn, pos);
//    }

    @Override public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) { return true; }
}
