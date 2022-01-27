package com.somebody.idlframewok.blocks.blockMisc;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.MessageDef;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockRallyHelper extends BlockBase {
    public BlockRallyHelper(String name, Material material) {
        super(name, material);
        setHardness(30f);
        setLightLevel(0.3f);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (!worldIn.isRemote)
        {
            List<EntityPlayer> playerList = worldIn.getPlayers(EntityPlayer.class, EntitySelectors.IS_ALIVE);
            playerList.remove(playerIn);

            if (playerList.size() > 0)
            {
                EntityPlayer target = playerList.get(playerIn.getRNG().nextInt(playerList.size()));
                playerIn.attemptTeleport(target.posX+0.5f, target.posY, target.posZ);
            }
            else {
                CommonFunctions.SafeSendMsgToPlayer(playerIn, MessageDef.MSG_NO_VALID_PLAYER);
            }

            worldIn.playSound(playerIn, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.BLOCKS, 1f, 2f);
        }

        return true;
    }
}
