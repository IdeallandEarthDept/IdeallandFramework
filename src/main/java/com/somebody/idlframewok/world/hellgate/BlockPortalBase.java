package com.somebody.idlframewok.world.hellgate;

import com.somebody.idlframewok.util.IHasModel;
import com.google.common.cache.LoadingCache;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPortalBase extends BlockPortal implements IHasModel {

    //Called in BlockFire.onBlockAdded originally
    @Override
    public boolean trySpawnPortal(World worldIn, BlockPos pos) {
        PortalSize blockportal$size = new PortalSize(worldIn, pos, EnumFacing.Axis.X, this);

        if (blockportal$size.isValid() && blockportal$size.portalBlockCount == 0)
        //&& !net.minecraftforge.event.ForgeEventFactory.onTrySpawnPortal(worldIn, pos, blockportal$size)
        {
            blockportal$size.placePortalBlocks();
            return true;
        } else {
            PortalSize blockportal$size1 = new PortalSize(worldIn, pos, EnumFacing.Axis.Z, this);

            if (blockportal$size1.isValid() && blockportal$size1.portalBlockCount == 0)
            //&& !net.minecraftforge.event.ForgeEventFactory.onTrySpawnPortal(worldIn, pos, blockportal$size1)
            {
                blockportal$size1.placePortalBlocks();
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing.Axis enumfacing$axis = state.getValue(AXIS);

        if (enumfacing$axis == EnumFacing.Axis.X) {
            PortalSize blockportal$size = new PortalSize(worldIn, pos, EnumFacing.Axis.X);

            if (!blockportal$size.isValid() || blockportal$size.portalBlockCount < blockportal$size.width * blockportal$size.height) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        } else if (enumfacing$axis == EnumFacing.Axis.Z) {
            PortalSize blockportal$size1 = new PortalSize(worldIn, pos, EnumFacing.Axis.Z);

            if (!blockportal$size1.isValid() || blockportal$size1.portalBlockCount < blockportal$size1.width * blockportal$size1.height) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    public BlockPattern.PatternHelper createPatternHelper(World worldIn, BlockPos p_181089_2_) {
        EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Z;
        PortalSize blockportal$size = new PortalSize(worldIn, p_181089_2_, EnumFacing.Axis.X);
        LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.createLoadingCache(worldIn, true);

        if (!blockportal$size.isValid()) {
            enumfacing$axis = EnumFacing.Axis.X;
            blockportal$size = new PortalSize(worldIn, p_181089_2_, EnumFacing.Axis.Z);
        }

        if (!blockportal$size.isValid()) {
            return new BlockPattern.PatternHelper(p_181089_2_, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
        } else {
            int[] aint = new int[EnumFacing.AxisDirection.values().length];
            EnumFacing enumfacing = blockportal$size.rightDir.rotateYCCW();
            BlockPos blockpos = blockportal$size.bottomLeft.up(blockportal$size.getHeight() - 1);

            for (EnumFacing.AxisDirection enumfacing$axisdirection : EnumFacing.AxisDirection.values()) {
                BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);

                for (int i = 0; i < blockportal$size.getWidth(); ++i) {
                    for (int j = 0; j < blockportal$size.getHeight(); ++j) {
                        BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, j, 1);

                        if (blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getMaterial() != Material.AIR) {
                            ++aint[enumfacing$axisdirection.ordinal()];
                        }
                    }
                }
            }

            EnumFacing.AxisDirection enumfacing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;

            for (EnumFacing.AxisDirection enumfacing$axisdirection2 : EnumFacing.AxisDirection.values()) {
                if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()]) {
                    enumfacing$axisdirection1 = enumfacing$axisdirection2;
                }
            }

            return new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection1 ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection1, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);
        }
    }
}
