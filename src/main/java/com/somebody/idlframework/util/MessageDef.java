package com.somebody.idlframework.util;

import net.minecraft.item.ItemStack;

public class MessageDef {
    //GENERAL:
    public static final String OUT_OF_RANGE = "idlframework.msg.out_of_range";
    public static final String IN_COOLDOWN = "idlframework.skill.msg.cool_down";
    public static final String NOT_CASTABLE_MAINHAND = "idlframework.skill.msg.not_castable_mainhand";
    public static final String NOT_CASTABLE_OFFHAND = "idlframework.skill.msg.not_castable_offhand";

    public static String getSkillCastKey(ItemStack stack, int index)
    {
        //remove"item."
        return String.format("msg.%s.cast.%d", stack.getUnlocalizedName().substring(5), index);
    }
}
