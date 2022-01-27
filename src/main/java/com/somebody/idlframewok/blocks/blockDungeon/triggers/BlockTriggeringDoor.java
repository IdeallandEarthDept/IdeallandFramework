package com.somebody.idlframewok.blocks.blockDungeon.triggers;

import com.somebody.idlframewok.blocks.blockDungeon.BlockDungeonWall;
import com.somebody.idlframewok.blocks.blockDungeon.ITriggerable;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.MessageDef;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;

public class BlockTriggeringDoor extends BlockDungeonWall {
    public BlockTriggeringDoor(String name, Material material) {
        super(name, material);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && facing == EnumFacing.UP)
        {
            int y0 = pos.getY() - 8;

            //goes down one floor
            if (playerIn.attemptTeleport(pos.getX() + 0.5f, y0 + 1.5f, pos.getZ() + 0.5f))
            {
                //remove last 4 binary digits
                int x0 = (pos.getX() >> 4) << 4;
                int z0 = (pos.getZ() >> 4) << 4;

                for (int x = 0; x < CHUNK_SIZE; x++)
                {
                    for (int z = 0; z < CHUNK_SIZE; z++)
                    {
                        BlockPos pos1 = new BlockPos(x0 + x, y0, z0+z);
                        Block block = worldIn.getBlockState(pos1).getBlock();
                        if (block instanceof ITriggerable)
                        {
                            ((ITriggerable) block).triggerAt(worldIn, pos1);
                        }
                    }
                }
            }
            else {
                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, playerIn, MessageDef.MSG_DOOR_FAIL);
            }

        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
