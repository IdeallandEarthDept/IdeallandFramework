package com.somebody.idlframewok.blocks.blockDungeon.triggers;

import com.somebody.idlframewok.entity.creatures.mobs.boss.EntityBossBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.MessageDef;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockTriggeringDoorBossRoom extends BlockTriggeringDoor {
    public BlockTriggeringDoorBossRoom(String name, Material material) {
        super(name, material);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (EntityUtil.getEntitiesWithinAABB(worldIn, EntityBossBase.class, pos, 16f, EntityUtil.ALL_ALIVE).size() == 0) {
                super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
            } else {
                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, playerIn, MessageDef.MSG_NEED_KILL_BOSS);
            }

        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
