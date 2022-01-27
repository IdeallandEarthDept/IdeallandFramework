package com.somebody.idlframewok.blocks.trashcan;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.PlayerUtil.giveToPlayer;

public class BlockTrashCanBase extends BlockBase {

    public static final String TYPE_A = "type_a";

    public int xp = 1;
    public String getStatMark()
    {
        return TYPE_A;
    }

    public BlockTrashCanBase(String name, Material material) {
        super(name, material);
        setResistance(0);
    }

    public boolean isCorrect(ItemStack stack)
    {
        return stack.getItem() == Items.PAPER;
    }

    public int getCriticalNumber()
    {
        return 100;
    }

    public void giveCriticalReward(EntityPlayer player)
    {
        CommonFunctions.SafeSendMsgToPlayer(TextFormatting.AQUA, player, "idlframewok.msg.crit");
        //giveToPlayer(player, new ItemStack(Items.DYE, player.getRNG().nextInt(3), EnumDyeColor.WHITE.getDyeDamage()));
    }

    public void giveNormalReward(EntityPlayer player)
    {
        giveToPlayer(player, new ItemStack(Items.DYE, player.getRNG().nextInt(3), EnumDyeColor.WHITE.getDyeDamage()));
    }

    /**
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            ItemStack stack = playerIn.getHeldItem(hand);
            if (stack.isEmpty())
            {
                return false;
            }
            else {
                if (isCorrect(stack))
                {
                    int stackCount = stack.getCount();

                    int lastCount = IDLNBTUtil.getPlayerIdeallandIntSafe(playerIn, getStatMark());
                    int newCount = lastCount + stackCount;
                    IDLNBTUtil.setPlayerIdeallandTagSafe(playerIn, getStatMark(), newCount);

                    int critNumber = getCriticalNumber();

                    int lastCritIndex = lastCount / critNumber;
                    int newCritIndex = newCount / critNumber;

                    for (int i = lastCount + 1; i <= newCount; i++ )
                    {
                        if (i % critNumber == 0)
                        {
                            giveCriticalReward(playerIn);
                        }else {
                            giveNormalReward(playerIn);
                        }
                    }

                    playerIn.addExperience(xp * stackCount);

                    int nextCritNumber = newCritIndex * critNumber + critNumber;
                    Idealland.Log("Correct: %d items. Progress: %d / %d", stackCount, newCount, nextCritNumber);
                }
                else {
                    worldIn.createExplosion(playerIn, pos.getX(), pos.getY(), pos.getZ(), 2, true);
                }

                stack.shrink(stack.getCount());
            }
        }

        return true;
    }
//
//    /**
//     * Returns a new instance of a block's tile entity class. Called on placing the block.
//     */
//    public TileEntity createNewTileEntity(World worldIn, int meta)
//    {
//        return new TileEntityTrashCan();
//    }
}
