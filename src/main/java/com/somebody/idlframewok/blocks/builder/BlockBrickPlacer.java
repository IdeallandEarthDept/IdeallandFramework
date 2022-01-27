package com.somebody.idlframewok.blocks.builder;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBrickPlacer extends BlockBase {

    private static int DETECT_RANGE = 5;

    private Block blockToPlace;

    public BlockBrickPlacer(String name, Material material, Block blockToPlace){
        super(name, material);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);
        this.blockToPlace = blockToPlace;
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if (worldIn.isRemote) {
            return;
        }

        boolean activated = false;

        //Todo: Refreacture by delta vectors.
        BlockPos posCheck = new BlockPos(pos);
        for (int i = 1; i <= DETECT_RANGE; i++){
            posCheck = pos.add(i,0,0);
            if ((worldIn.getBlockState(posCheck).getBlock() != Blocks.AIR) || (worldIn.getBlockState(posCheck.add(0,1,0)).getBlock() != Blocks.AIR)) {
                if (worldIn.getBlockState(posCheck).getBlock() == blockToPlace || (worldIn.getBlockState(posCheck.add(0,1,0)).getBlock() == blockToPlace)) {
                    activated = true;
                    //Idealland.Log("dir 1:" + posCheck);
                    for (int j = 1; j <= i; j++) {
                        worldIn.setBlockState(pos.add(j,0,0), blockToPlace.getDefaultState());
                        worldIn.setBlockState(pos.add(j,1,0), blockToPlace.getDefaultState());
                    }
                }
                break;
            }
        }

        for (int i = 1; i <= DETECT_RANGE; i++){
            posCheck = pos.add(-i,0,0);
            if ((worldIn.getBlockState(posCheck).getBlock() != Blocks.AIR) || (worldIn.getBlockState(posCheck.add(0,1,0)).getBlock() != Blocks.AIR)) {
                if (worldIn.getBlockState(posCheck).getBlock() == blockToPlace || (worldIn.getBlockState(posCheck.add(0,1,0)).getBlock() == blockToPlace)) {
                    //Idealland.Log("dir 1:" + posCheck);
                    activated = true;
                    for (int j = 1; j <= i; j++) {
                        worldIn.setBlockState(pos.add(-j,0,0), blockToPlace.getDefaultState());
                        worldIn.setBlockState(pos.add(-j,1,0), blockToPlace.getDefaultState());
                    }
                }
                break;
            }
        }

        for (int i = 1; i <= DETECT_RANGE; i++){
            posCheck = pos.add(0,0, i);
            if ((worldIn.getBlockState(posCheck).getBlock() != Blocks.AIR) || (worldIn.getBlockState(posCheck.add(0,1,0)).getBlock() != Blocks.AIR)) {
                if (worldIn.getBlockState(posCheck).getBlock() == blockToPlace || (worldIn.getBlockState(posCheck.add(0,1,0)).getBlock() == blockToPlace)) {
                    //Idealland.Log("dir 1:" + posCheck);
                    activated = true;
                    for (int j = 1; j <= i; j++) {
                        worldIn.setBlockState(pos.add(0,0, j), blockToPlace.getDefaultState());
                        worldIn.setBlockState(pos.add(0,1, j), blockToPlace.getDefaultState());
                    }
                }
                break;
            }
        }

        for (int i = 1; i <= DETECT_RANGE; i++){
            posCheck = pos.add(0,0, -i);
            if ((worldIn.getBlockState(posCheck).getBlock() != Blocks.AIR) || (worldIn.getBlockState(posCheck.add(0,1,0)).getBlock() != Blocks.AIR)) {
                if (worldIn.getBlockState(posCheck).getBlock() == blockToPlace || (worldIn.getBlockState(posCheck.add(0,1,0)).getBlock() == blockToPlace)) {
                    //Idealland.Log("dir 1:" + posCheck);
                    activated = true;
                    for (int j = 1; j <= i; j++) {
                        worldIn.setBlockState(pos.add(0,0, -j), blockToPlace.getDefaultState());
                        worldIn.setBlockState(pos.add(0,1, -j), blockToPlace.getDefaultState());
                    }
                }
                break;
            }
        }

        if (activated) {
            worldIn.setBlockState(pos, blockToPlace.getDefaultState());
            worldIn.setBlockState(pos.add(0, 1, 0), blockToPlace.getDefaultState());
        }
    }

}
