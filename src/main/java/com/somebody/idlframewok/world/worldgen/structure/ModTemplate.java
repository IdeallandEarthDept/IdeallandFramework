package com.somebody.idlframewok.world.worldgen.structure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;

public class ModTemplate extends Template {
    //wont work as blocks and entities are deadly private
    /**
     * Adds blocks and entities from this structurebig to the given world.
     *
     * @param worldIn The world to use
     * @param pos The origin position for the structurebig
     * @param templateProcessor The template processor to use
     * @param placementIn Placement settings to use
     * @param flags Flags to pass to {@link World#setBlockState(BlockPos, IBlockState, int)}
     */
//    public void addBlocksToWorld(World worldIn, BlockPos pos, @Nullable ITemplateProcessor templateProcessor, PlacementSettings placementIn, int flags)
//    {
//
//
//        if ((!this.blocks.isEmpty() || !placementIn.getIgnoreEntities() && !this.entities.isEmpty()) && this.size.getX() >= 1 && this.size.getY() >= 1 && this.size.getZ() >= 1)
//        {
//            BlockPhasingOre block = placementIn.getReplacedBlock();
//            StructureBoundingBox structureboundingbox = placementIn.getBoundingBox();
//
//            for (Template.BlockInfo template$blockinfo : this.blocks)
//            {
//                BlockPos blockpos = transformedBlockPos(placementIn, template$blockinfo.pos).add(pos);
//                // Forge: skip processing blocks outside BB to prevent cascading worldgen issues
//                if (structureboundingbox != null && !structureboundingbox.isVecInside(blockpos)) continue;
//                Template.BlockInfo template$blockinfo1 = templateProcessor != null ? templateProcessor.processBlock(worldIn, blockpos, template$blockinfo) : template$blockinfo;
//
//                if (template$blockinfo1 != null)
//                {
//                    BlockPhasingOre block1 = template$blockinfo1.blockState.getBlock();
//
//                    if ((block == null || block != block1) && (!placementIn.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK) && (structureboundingbox == null || structureboundingbox.isVecInside(blockpos)))
//                    {
//                        IBlockState iblockstate = template$blockinfo1.blockState.withMirror(placementIn.getMirror());
//                        IBlockState iblockstate1 = iblockstate.withRotation(placementIn.getRotation());
//
//                        if (template$blockinfo1.tileentityData != null)
//                        {
//                            TileEntity tileentity = worldIn.getTileEntity(blockpos);
//
//                            if (tileentity != null)
//                            {
//                                if (tileentity instanceof IInventory)
//                                {
//                                    ((IInventory)tileentity).clear();
//                                }
//
//                                worldIn.setBlockState(blockpos, Blocks.BARRIER.getDefaultState(), 4);
//                            }
//                        }
//
//                        if (worldIn.setBlockState(blockpos, iblockstate1, flags) && template$blockinfo1.tileentityData != null)
//                        {
//                            TileEntity tileentity2 = worldIn.getTileEntity(blockpos);
//
//                            if (tileentity2 != null)
//                            {
//                                template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
//                                template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
//                                template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
//                                tileentity2.readFromNBT(template$blockinfo1.tileentityData);
//                                tileentity2.mirrorIn(placementIn.getMirror());
//                                tileentity2.rotate(placementIn.getRotation());
//                            }
//                        }
//                    }
//                }
//            }
//
//            for (Template.BlockInfo template$blockinfo2 : this.blocks)
//            {
//                if (block == null || block != template$blockinfo2.blockState.getBlock())
//                {
//                    BlockPos blockpos1 = transformedBlockPos(placementIn, template$blockinfo2.pos).add(pos);
//
//                    if (structureboundingbox == null || structureboundingbox.isVecInside(blockpos1))
//                    {
//                        ///worldIn.notifyNeighborsRespectDebug(blockpos1, template$blockinfo2.blockState.getBlock(), false);
//
//                        if (template$blockinfo2.tileentityData != null)
//                        {
//                            TileEntity tileentity1 = worldIn.getTileEntity(blockpos1);
//
//                            if (tileentity1 != null)
//                            {
//                                //tileentity1.markDirty();
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (!placementIn.getIgnoreEntities())
//            {
//                this.addEntitiesToWorld(worldIn, pos, placementIn.getMirror(), placementIn.getRotation(), structureboundingbox);
//            }
//        }
//    }

}
