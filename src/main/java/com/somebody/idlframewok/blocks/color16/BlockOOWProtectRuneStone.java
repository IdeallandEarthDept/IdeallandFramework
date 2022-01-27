package com.somebody.idlframewok.blocks.color16;

import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.Color16Def.MSG_GET_OOW_PROTECT;
import static com.somebody.idlframewok.util.Color16Def.MSG_PROTECT_ALREADY;

public class BlockOOWProtectRuneStone extends BlockRuneStoneBase {
    public BlockOOWProtectRuneStone(String name, Material material) {
        super(name, material);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (!worldIn.isRemote)
        {
            if (IDLNBTUtil.GetIntAuto(playerIn, Color16Def.OUT_WORLD_PROTECT, 0) == 0)
            {
                IDLNBTUtil.addIntAuto(playerIn, Color16Def.OUT_WORLD_PROTECT, 1);
                CommonFunctions.SafeSendMsgToPlayer(playerIn, MSG_GET_OOW_PROTECT);
            }
            else {
                CommonFunctions.SafeSendMsgToPlayer(playerIn, MSG_PROTECT_ALREADY);
                return false;
            }
        }

        return true;
    }
}
