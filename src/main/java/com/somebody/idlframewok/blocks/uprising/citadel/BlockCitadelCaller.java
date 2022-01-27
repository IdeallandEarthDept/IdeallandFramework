package com.somebody.idlframewok.blocks.uprising.citadel;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.blocks.uprising.citadel.util.CitadelUtil;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static com.somebody.idlframewok.util.PlayerUtil.setCoolDown;

public class BlockCitadelCaller extends BlockBase {
    public BlockCitadelCaller(String name) {
        super(name, Material.GROUND);
    }

    final int detect_range = 3;

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
        {
            setCoolDown(playerIn, hand);

            List<BlockPos> posList = Lists.newArrayList();
            CitadelUtil.EnumSingleZoneState citadelState = CitadelUtil.EnumSingleZoneState.ERR_NO_CITADEL;

            if (facing == EnumFacing.UP)
            {
                for (int x = -detect_range; x <= detect_range; x++)
                {
                    for (int z = -detect_range; z <= detect_range; z++)
                    {
                        Block block = worldIn.getBlockState(pos.add(x,0,z)).getBlock();
                        if (block instanceof BlockCitadelBase)
                        {
                            posList.add(pos.add(x,0,z));
                            if (block instanceof BlockCitadelTurretBuilder)
                            {
                                citadelState = CitadelUtil.checkZoneState(worldIn, pos);
                            }
                        }
                    }
                }
            }

            if (citadelState == CitadelUtil.EnumSingleZoneState.CLEAR) {
                for (BlockPos _pos :
                        posList) {
                    Block block = worldIn.getBlockState(_pos).getBlock();
                    if (block instanceof BlockCitadelTurretBuilder) {
                        ((BlockCitadelTurretBuilder) block).tryActivate(playerIn, worldIn, _pos);
                    } else if (block instanceof BlockCitadelBuildingZone) {
                        ((BlockCitadelBuildingZone) block).tryActivate(playerIn, worldIn, _pos);
                    }
                }
            }
        }


        //((BlockCitadelBase) block).tryActivate(playerIn, worldIn, pos);

        return true;
    }
}
