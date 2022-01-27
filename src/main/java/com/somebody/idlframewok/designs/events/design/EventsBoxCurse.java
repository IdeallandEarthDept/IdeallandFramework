package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsBoxCurse {

    static boolean canPlace(World world, BlockPos pos)
    {
        if (world.isOutsideBuildHeight(pos))
        {
            return false;
        }

        TileEntity tileEntity = world.getTileEntity(pos);

        if (world.getBlockState(pos).getBlock().getBlockHardness(null,null,null) < 0)
        {
            return false;
        }

        return tileEntity == null &&
            Blocks.TRAPPED_CHEST.canPlaceBlockAt(world, pos);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCreatureDied(LivingDeathEvent evt) {
        if (!ModConfig.CURSE_CONF.BOX_CURSE_ACTIVE)
        {
            return;
        }

        if (evt.getEntityLiving().world.getGameRules().getBoolean("keepInventory"))
        {
            return;
        }

        if (evt.getEntityLiving() instanceof EntityPlayer && !evt.getEntityLiving().world.isRemote && !evt.isCanceled())
        {
            EntityPlayer player = (EntityPlayer) evt.getEntityLiving();
            BlockPos pos = player.getPosition();

            World world = player.world;

            int maxTrial = 20;
            int trialPos = maxTrial;

            if (world.isOutsideBuildHeight(pos))
            {
                pos = pos.getY() < 0 ?
                        new BlockPos(pos.getX(), 0, pos.getZ()) :
                        new BlockPos(pos.getX(), 253, pos.getZ()) ;
            }

            BlockPos tryCenter = pos;
            boolean success;
            while(!canPlace(world, pos))
            {
                pos = pos.add(1,0,0);
                trialPos--;
                if (trialPos <= 0)
                {
                    Idealland.Log("Cannot place box for player at %s.", pos);
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, player, "idlframewok.msg.cannot_place_box");
                    return;
                }
            }

            //todo : try every direction

            world.setBlockState(pos, Blocks.TRAPPED_CHEST.getDefaultState(), 2);

            //gravestone
            world.setBlockState(pos.add(0,1,0), Blocks.COBBLESTONE_WALL.getDefaultState(), 2);
            world.setBlockState(pos.add(0,2,0), Blocks.STANDING_SIGN.getDefaultState(), 2);

            //signText
            TileEntity tileEntity1 = world.getTileEntity(pos.add(0,2,0));

            CommonFunctions.WriteGraveToSign(player, world, tileEntity1);

            int chestIndex = 0;
            IInventory container = ((BlockChest)Blocks.TRAPPED_CHEST).getLockableContainer(world, pos);
            if (container!=null) {
                int sizeBox = container.getSizeInventory();

                for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                    ItemStack itemstack = player.inventory.getStackInSlot(i);
                    //chest is full. move to next chest
                    if ((chestIndex % sizeBox == 0) && (chestIndex != 0))
                    {
                        do
                        {
                            pos = pos.add(1,0,0);
                            trialPos--;
                            if (trialPos <= 0)
                            {
                                Idealland.Log("Cannot place box for player at %s.", pos);
                                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, player, "idlframewok.msg.cannot_place_box");
                                return;
                            }
                        }
                        while(!canPlace(world, pos));

                        world.setBlockState(pos, Blocks.TRAPPED_CHEST.getDefaultState(), 2);
                        container =  ((BlockChest)(Blocks.TRAPPED_CHEST)).getLockableContainer(world, pos);
                        if (container == null)
                        {
                            Idealland.LogWarning("wrong! no box!");
                            break;
                        }
                        //chestIndex = 0;
                    }

                    if (!itemstack.isEmpty())
                    {
                        if (chestIndex >= container.getSizeInventory())
                        {
                            chestIndex %= container.getSizeInventory();
                        }

                        container.setInventorySlotContents(chestIndex, itemstack);
                        player.inventory.removeStackFromSlot(i);
                        //player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                        chestIndex++;
                    }
                }
            }

            Idealland.Log("Player %s is now a box @%s.", player.getDisplayNameString(), player.getPosition());
        }
    }

}
