package com.somebody.idlframewok.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTeleporter extends BlockBase {
    public BlockTeleporter(String name, Material material) {
        super(name, material);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
//            Teleport.teleportToDim(playerIn, ModConfig.DEBUG_CONF.DIM_ONE_ID, playerIn.getPosition().getX(),
//                    playerIn.posY + 5, playerIn.posZ);
            return true;
        }

        return false;
    }
}
