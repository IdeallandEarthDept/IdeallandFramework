package com.somebody.idlframewok.util;

import com.somebody.idlframewok.Idealland;
import net.minecraft.item.ItemStack;

public class MessageDef {
    //GENERAL:
    public static final String OUT_OF_RANGE = "idlframewok.msg.out_of_range";
    public static final String IN_COOLDOWN = "idlframewok.skill.msg.cool_down";
    public static final String NOT_CASTABLE_MAINHAND = "idlframewok.skill.msg.not_castable_mainhand";
    public static final String NOT_CASTABLE_OFFHAND = "idlframewok.skill.msg.not_castable_offhand";

    public static final String TP_BADLUCK = "idlframewok.msg.tp_badluck";

    public static final String MAGICAL_EYE_GAZED = "idlframewok.msg.gazed_by";
    public static final String MAGICAL_EYE_READY = "idlframewok.msg.gazed_ready";
    public static final String MAGICAL_EYE_CD= "idlframewok.msg.gaze_cd";
    public static final String MAGICAL_EYE_CAST_BROAD= "idlframewok.msg.gaze_broad";
    public static final String MAGICAL_EYE_CAST_NARROW= "idlframewok.msg.gaze_narrow";

    //VEX
    public static final String VEX_ATTACK_YOU = "idlframewok.msg.vex_attack_you";
    public static final String VEX_DEFEND_YOU = "idlframewok.msg.vex_defend_you";

    public static final String AMK_NECKLACE = "msg.amk_necklace.basic";

    public static final String MSG_SOVEREIGN_FAIL = "msg.sovereign_seal.fail";

    //dungeon
    public static final String MSG_CLEANSE_FIREPROOF = "idlframewok.msg.dunegon.cleanse_fireproof";
    public static final String MSG_NEED_KILL_BOSS = "idlframewok.msg.dunegon.need_kill_boss";

    //Grand Protection
    public static final String MSG_LOGIN_ABSOLUTE = "msg.grand_def.login.absolute";
    public static final String MSG_LOGIN_RELATIVE = "msg.grand_def.login.relative";

    public static final String MSG_TRIGGER_ABSOLUTE_OPPONENT = "msg.grand_def.trigger.absolute.opponent";
    public static final String MSG_TRIGGER_RELATIVE_OPPONENT = "msg.grand_def.trigger.relative.opponent";
    public static final String MSG_LOST_ABSOLUTE = "msg.grand_def.lost.absolute";

    public static final String MSG_TRIGGER_RELATIVE = "msg.grand_def.trigger.relative";

    public static final String MSG_BEGIN_EVIL = "idlframewok.msg.evil.begin";
    public static final String MSG_FADE_EVIL = "idlframewok.msg.evil.fade";
    public static final String MSG_FADE_EVIL_FAIL = "idlframewok.msg.evil.fade.fail";
    public static final String MSG_END_EVIL = "idlframewok.msg.evil.end";

    public static final String MSG_NO_VALID_PLAYER = "idlframewok.msg.no_valid_player";

    public static final String MSG_TELEPORT_ACCEPTED = "idlframewok.msg.tp_accepted";

    public static final String MSG_WRONG_CLASS = "idlframewok.msg.skill.wrong_class";

    public static final String MSG_ALREADY_SUMMONED = "idlframewok.msg.skill.ego_twin.already_summoned";

    public static final String MSG_INTIMIDATE = "idlframewok.msg.intimidate";

    //dungeon
    public static final String MSG_TRAP_INFO = Idealland.MODID + ".msg.trap.info";

    public static final String MSG_DOOR_FAIL = Idealland.MODID + ".msg.door.fail";
    public static final String MSG_PLACE_STOPPED_BY_DAMPER = Idealland.MODID + ".msg.place_stopped_by_damper";
    public static final String MSG_DIG_STOPPED_BY_DAMPER = Idealland.MODID + ".msg.dig_stopped_by_damper";

    public static String getSkillCastKey(ItemStack stack, int index)
    {
        //remove"item."
        return String.format("msg.%s.cast.%d", stack.getUnlocalizedName().substring(5), index);
    }
}
