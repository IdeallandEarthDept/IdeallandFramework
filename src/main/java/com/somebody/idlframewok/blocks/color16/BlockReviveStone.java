package com.somebody.idlframewok.blocks.color16;

import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockReviveStone extends BlockRuneStoneBase {
    public BlockReviveStone(String name, Material material) {
        super(name, material);
        setLightLevel(0.2f);
        setBlockUnbreakable();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (!worldIn.isRemote)
        {
            playerIn.setSpawnPoint(pos.add(0,2,0), true);
            CommonFunctions.SafeSendMsgToPlayer(playerIn, Color16Def.MSG_USE_REIVE_SET);
            worldIn.playSound(playerIn, pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1f, 2f);
        }

        return true;
    }
}
