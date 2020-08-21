package com.deeplake.idealland.util;

import com.deeplake.idealland.item.IGuaEnhance;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.*;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil.*;

public class IDLSkillNBT {
    public int level;
    public int range_boost = 0;
    public int duration_boost = 0;
    public int[] gua_boost = new int[8];

//    private final NBTTagCompound basic;

//    public IDLSkillNBT()
//    {
//        level = 1;
//        range_boost = 0;
//        duration_boost = 0;
//
//        basic = new NBTTagCompound();
//    }
//
//    public IDLSkillNBT(NBTTagCompound srcNBT)
//    {
//        readFromBasic(srcNBT);
//        basic = srcNBT;
//    }
//
//    public void readFromBasic(NBTTagCompound tag)
//    {
//        if (tag != null)
//        {
//            level = tag.getInteger(IDLNBTDef.LEVEL_TAG);
//            range_boost = tag.getInteger(IDLNBTDef.RANGE_BOOST);
//            duration_boost = tag.getInteger(IDLNBTDef.DURA_BOOST);
//        }
//    }

//    public void writeToBasic(NBTTagCompound tag)
//    {
//        if (tag == null)
//        {
//            tag = new NBTTagCompound();
//        }
//
//        tag.setInteger(IDLNBTDef.LEVEL_TAG, level);
//        tag.setInteger(IDLNBTDef.RANGE_BOOST, range_boost);
//        tag.setInteger(IDLNBTDef.DURA_BOOST, duration_boost);
//    }
//
//    public NBTTagCompound getBasic()
//    {
//        NBTTagCompound tag = basic.copy();
//        writeToBasic(tag);
//
//        return tag;
//    }
//
//    public void save()
//    {
//        writeToBasic(basic);
//    }
    //----------------------
    //Integer
    public static void SetGuaEnhance(ItemStack stack, int guaIndex, int value)
    {
        NBTTagCompound ori = getNBT(stack);

        NBTTagCompound subset = new NBTTagCompound();
        subset.setInteger(GUA_ENHANCE_8 + guaIndex, value);

        NBTTagCompound newTag = new NBTTagCompound();
        newTag.setTag(GUA_ENHANCE, subset);

        ori.merge(newTag);
//        if (!StackHasKey(stack, GUA_ENHANCE))
//        {
//            getNBT(stack).setTag(GUA_ENHANCE, new NBTTagCompound());
//        }else {
//
//        }
//
//        NBTTagCompound guaEnhanceNBT = getNBT(stack).getCompoundTag(GUA_ENHANCE);
//
//        guaEnhanceNBT.setInteger(GUA_ENHANCE_8 + guaIndex, value);
    }

    public static void AddGuaEnhance(ItemStack stack, int guaIndex, int value)
    {
        SetGuaEnhance(stack, guaIndex, value + GetGuaEnhance(stack, guaIndex));
    }

    public static String GetGuaEnhanceString(ItemStack stack, int guaIndex)
    {
        Item stackItem = stack.getItem();
        if (stackItem instanceof IGuaEnhance)
        {
            IGuaEnhance guaEnhance = (IGuaEnhance) stackItem;
            if (guaEnhance.acceptGuaIndex(guaIndex))
            {
                return String.valueOf(GetGuaEnhance(stack, guaIndex, 0));
            }
            else {
                return I18n.format(GUA_N_A_DESC);
            }
        }
        else {
            return "";
        }

    }

    public static int GetGuaEnhance(ItemStack stack, int guaIndex)
    {
        return GetGuaEnhance(stack, guaIndex, 0);
    }

    public static int GetGuaEnhance(ItemStack stack, int guaIndex, int defaultVal)
    {
        if (StackHasKey(stack, GUA_ENHANCE))
        {
            NBTTagCompound nbt = getNBT(stack).getCompoundTag(GUA_ENHANCE);
            String str = String.valueOf(GUA_ENHANCE_8 + guaIndex);
            if (nbt.hasKey(str))
            {
                return nbt.getInteger(str);
            }
        }

        return defaultVal;
    }

    public static int GetGuaEnhanceTotal(ItemStack stack)
    {
        int result = 0;
        if (StackHasKey(stack, GUA_ENHANCE))
        {
            NBTTagCompound nbt = getNBT(stack).getCompoundTag(GUA_ENHANCE);
            for (String key: nbt.getKeySet()
            ) {
                result += nbt.getInteger(key);
            }
        }

        return result;
    }

    public static int GetGuaEnhanceFree(ItemStack stack)
    {
        return GetInt(stack, GUA_FREE_SOCKET);
    }

    public static void SetGuaEnhanceFree(ItemStack stack, int val)
    {
        SetInt(stack, GUA_FREE_SOCKET, val);
    }

    @SideOnly(Side.CLIENT)
    public static void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag,
                                       boolean shiftToShowDesc, boolean isShiftPressed , boolean showGuaSocketDesc, String mainDesc) {

        boolean shiftPressed = !shiftToShowDesc || isShiftPressed;
        if (shiftPressed)
        {
            String desc = mainDesc;
            if (!desc.isEmpty())
            {
                tooltip.add(desc);
            }

            if (showGuaSocketDesc)
            {
                int guaTotal = IDLSkillNBT.GetGuaEnhanceTotal(stack);
                tooltip.add(I18n.format(GUA_TOTAL_SOCKET_DESC, IDLSkillNBT.GetGuaEnhanceTotal(stack)));
                if (guaTotal > 0)
                {
                    tooltip.add(I18n.format("idealland.gua_enhance_list.desc", GetGuaEnhanceString(stack, 0),
                            GetGuaEnhanceString(stack, 1),
                            GetGuaEnhanceString(stack, 2),
                            GetGuaEnhanceString(stack, 3),
                            GetGuaEnhanceString(stack, 4),
                            GetGuaEnhanceString(stack, 5),
                            GetGuaEnhanceString(stack, 6),
                            GetGuaEnhanceString(stack, 7)));
                }

                int freeSockets = IDLSkillNBT.GetGuaEnhanceFree(stack);
                if (freeSockets > 0)
                {
                    tooltip.add(TextFormatting.AQUA + I18n.format(IDLNBTDef.GUA_FREE_SOCKET_DESC, freeSockets));
                }
                else {
                    tooltip.add(TextFormatting.ITALIC + (TextFormatting.WHITE + I18n.format(IDLNBTDef.GUA_NO_FREE_SOCKET_DESC)));
                }
            }
        }
        else {
            tooltip.add(TextFormatting.AQUA +  I18n.format("idealland.shared.press_shift"));
        }
    }
}
