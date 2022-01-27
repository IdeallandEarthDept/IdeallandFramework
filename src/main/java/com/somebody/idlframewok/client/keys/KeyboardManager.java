package com.somebody.idlframewok.client.keys;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.item.skills.ICastable;
import com.somebody.idlframewok.network.NetworkHandler;
import com.somebody.idlframewok.network.protocols.PacketCast;
import com.somebody.idlframewok.network.protocols.PacketMouseFire;
import com.somebody.idlframewok.util.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class KeyboardManager {
    //ref: https://harbinger.covertdragon.team/chapter-18/keyboard.html

    public static void init() {
        for (KeyBinding key:
             ClientProxy.KEY_BINDINGS) {
            ClientRegistry.registerKeyBinding(key);
        }
        Idealland.Log("Registered %d keys", ClientProxy.KEY_BINDINGS.size());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(receiveCanceled = true)
    public static void OnMouse(MouseEvent ev)
    {
        Minecraft mc = Minecraft.getMinecraft();

        //0 = left
        //1 = right

        if (ev.getButton() != 0 || !ev.isButtonstate())
            return;

        EntityPlayerSP player = mc.player;
        if(player == null) return;
        if(mc.isGamePaused()) return;
        if(!mc.inGameHasFocus) return;
        if(mc.currentScreen != null) return;

        NetworkHandler.SendToServer(new PacketMouseFire());

        for (EnumHand hand:
                EnumHand.values()
        ) {
            ItemStack item = player.getHeldItem(hand);
            if(item.getItem() instanceof ItemBase) {
                ((ItemBase) item.getItem()).onMouseFire(player);
            }
        }
    }

//    @SideOnly(Side.CLIENT)
//    static void checkCast(boolean cast, EntityEquipmentSlot slot)
//    {
//        Minecraft mc = Minecraft.getMinecraft();
//        if (cast) {
//            EntityPlayerSP player = mc.player;
//
//            Idealland.Log("pressed key cast :" + slot);
//
//            ItemStack item = player.getItemStackFromSlot(slot);
//            if(item.isEmpty())
//            {
//                Idealland.LogWarning("Trying to cast an empty item");
//            }
//
//            if(item.getItem() instanceof ICastable)
//            {
//                ICastable skill = (ICastable) item.getItem();
//                if (skill.canCast(player.world, player, item, slot))
//                {
//                    NetworkHandler.SendToServer(new PacketCast(slot.ordinal()));
//                    skill.applyCast(player.world, player, item, slot);
//                }
//                else {
//                    player.playSound(SoundEvents.UI_BUTTON_CLICK, 1f, 0.5f);
//                }
//            }
//        }
//    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onKeyPressed(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.player;
        if(player == null) return;
        if(mc.isGamePaused()) return;
        if(!mc.inGameHasFocus) return;
        if(mc.currentScreen != null) return;

//        checkCast(ClientProxy.CAST_HELMET.isPressed(), EntityEquipmentSlot.HEAD);
//        checkCast(ClientProxy.CAST_MAINHAND.isPressed(), EntityEquipmentSlot.MAINHAND);
//        checkCast(ClientProxy.CAST_OFFHAND.isPressed(), EntityEquipmentSlot.OFFHAND);
    }
}
