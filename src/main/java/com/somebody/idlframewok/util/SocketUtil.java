package com.somebody.idlframewok.util;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.*;


@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class SocketUtil {
    static TextFormatting colorYes = TextFormatting.GREEN;
    static TextFormatting colorNo =TextFormatting.DARK_GRAY;
    static TextFormatting colorJust =TextFormatting.YELLOW;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOW)
    static void onToolTip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        if (IDLNBTUtil.GetInt(stack, ANY_SOCKET) > 0)
        {
            if (IDLNBTUtil.GetInt(stack, JADE_SOCKET) > 0)
            {
                event.getToolTip().add(1, TextFormatting.GREEN + I18n.format(IDLNBTDef.JADE_SOCKET_KEY));
                int setCount = getAnyEquipCount(event.getEntityPlayer(), JADE_SOCKET);
                event.getToolTip().add(SocketUtil.formatBy(2, setCount, I18n.format(IDLNBTDef.JADE_SOCKET_KEY_2)));
                event.getToolTip().add(SocketUtil.formatBy(4, setCount, I18n.format(IDLNBTDef.JADE_SOCKET_KEY_4)));
            }

            if (IDLNBTUtil.GetInt(stack, CYCLE_SOCKET) > 0)
            {
                event.getToolTip().add(1, TextFormatting.GREEN + I18n.format(IDLNBTDef.CYCLE_SOCKET_KEY));
                int setCount = getAnyEquipCount(event.getEntityPlayer(), CYCLE_SOCKET);
                event.getToolTip().add(SocketUtil.formatBy(2, setCount, I18n.format(IDLNBTDef.CYCLE_SOCKET_KEY_2)));
                event.getToolTip().add(SocketUtil.formatBy(4, setCount, I18n.format(IDLNBTDef.CYCLE_SOCKET_KEY_4)));
            }

            if (IDLNBTUtil.GetInt(stack, FLESH_SOCKET) > 0)
            {
                event.getToolTip().add(1, TextFormatting.GREEN + I18n.format(IDLNBTDef.FLESH_SOCKET_KEY));
                int setCount = getAnyEquipCount(event.getEntityPlayer(), FLESH_SOCKET);
                event.getToolTip().add(SocketUtil.formatBy(2, setCount, I18n.format(IDLNBTDef.FLESH_SOCKET_KEY_2)));
                event.getToolTip().add(SocketUtil.formatBy(4, setCount, I18n.format(IDLNBTDef.FLESH_SOCKET_KEY_4)));
            }
        }
    }

    public static int getAnyEquipCount(@Nullable EntityLivingBase target, String key)
    {
        if (target == null)
        {
            return 0;
        }

        int count = 0;
        for (EntityEquipmentSlot slot :
                EntityEquipmentSlot.values()) {
            ItemStack stack = target.getItemStackFromSlot(slot);
            if (!stack.isEmpty() && IDLNBTUtil.GetInt(stack, key) > 0) {
                count++;
            }
        }
        return count;
    }

    public static String formatBy(int needCount, int curCount, String content)
    {
        if (needCount == curCount + 1)
        {
            return colorJust + content;
        }
        else if (needCount <= curCount) {
            return colorYes + content;
        }
        else{
            return colorNo + content;
        }
    }
}
