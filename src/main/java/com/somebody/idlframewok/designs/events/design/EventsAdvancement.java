package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.PlayerUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.advancements.FrameType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventsAdvancement {
    @SubscribeEvent
    public static void onAdv(AdvancementEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (player.world.isRemote)
        {
            return;
        }

        //prevent errors
        if (event.getAdvancement().getDisplay() == null)
        {
            return;
        }

//        task\ chall \ goal. which is which
        if (event.getAdvancement().getDisplay().getFrame() == FrameType.CHALLENGE)
        {
            PlayerUtil.giveToPlayer(player, new ItemStack(ModItems.ITEM_IDL_ORDER_2));
        }else {
            PlayerUtil.giveToPlayer(player, new ItemStack(ModItems.ITEM_IDL_ORDER_1));
        }

    }
}
