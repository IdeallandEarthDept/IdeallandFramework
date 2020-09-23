package com.deeplake.idealland.util;

import net.minecraft.item.ItemStack;

public class MessageDef {
    //GENERAL:
    public static final String OUT_OF_RANGE = "idealland.msg.out_of_range";
    public static final String IN_COOLDOWN = "idealland.skill.msg.cool_down";
    public static final String NOT_CASTABLE_MAINHAND = "idealland.skill.msg.not_castable_mainhand";
    public static final String NOT_CASTABLE_OFFHAND = "idealland.skill.msg.not_castable_offhand";

    public static String getSkillCastKey(ItemStack stack, int index)
    {
        return String.format("msg.%s.cast.%d", stack, index);
    }
}
