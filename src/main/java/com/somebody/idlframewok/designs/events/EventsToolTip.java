package com.somebody.idlframewok.designs.events;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ILogNBT;
import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.item.skills.ItemSkillEye;
import com.somebody.idlframewok.item.skills.classfit.ItemSkillClassSpecific;
import com.somebody.idlframewok.item.skills.classfit.ItemSkillCore;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.WIP_DESC;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsToolTip {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onToolTipColor(RenderTooltipEvent.Color event) {
        if (event.getStack().getItem() instanceof IWIP) {
            event.setBackground(0xf0330000);
            event.setBorderStart(0xf0cc0000);
            event.setBorderEnd(0xf0cc0000);
        }
    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    static void onToolTip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (item instanceof IWIP)
        {
            event.getToolTip().add(1, TextFormatting.RED + I18n.format(WIP_DESC));
        }

        if (item instanceof ILogNBT)
        {
            event.getToolTip().add(String.valueOf(stack.getTagCompound()));
        }

        if (item instanceof ItemSkillBase)
        {
            ItemSkillBase skillBase = (ItemSkillBase) item;
            if (skillBase.useXP_level)
            {
                int lv = IDLSkillNBT.getLevel(stack);
                boolean isFull = lv < 0 || lv >= skillBase.levelup_need_xp.length;
                String tip2 = isFull ? I18n.format(IDLNBTDef.MAX) : String.valueOf(skillBase.levelup_need_xp[lv]);
                event.getToolTip().add(1, TextFormatting.AQUA + I18n.format(IDLNBTDef.XP_GAUGE, IDLSkillNBT.getXP(stack), tip2));
            }

            if (item instanceof ItemSkillEye)
            {
                event.getToolTip().add(1, TextFormatting.AQUA + I18n.format(IDLNBTDef.TIP_SKILL_EYE));
            }
            else {
                if (item instanceof ItemSkillCore)
                {
                    event.getToolTip().add(1, TextFormatting.AQUA +
                            I18n.format(IDLNBTDef.SKILL_CLASS_CORE, I18n.format(((ItemSkillCore) item).getSkillClass(stack).getUnlocalized())));
                } else if (item instanceof ItemSkillClassSpecific)
                {
                    event.getToolTip().add(1, TextFormatting.AQUA +
                            I18n.format(IDLNBTDef.SKILL_CLASS_SPEC, I18n.format(((ItemSkillClassSpecific) item).skillClass.getUnlocalized())));
                }

                if (!(skillBase.mainHandCast || skillBase.offHandCast))
                {
                    event.getToolTip().add(1, TextFormatting.AQUA + I18n.format(IDLNBTDef.PASSIVE));
                } else {
                    String tip1 = skillBase.mainHandCast ? I18n.format(IDLNBTDef.MAINHAND_CAST) : CommonDef.EMPTY;
                    String tip2 = skillBase.offHandCast ? I18n.format(IDLNBTDef.OFFHAND_CAST) : CommonDef.EMPTY;

                    event.getToolTip().add(1, TextFormatting.AQUA + I18n.format(IDLNBTDef.TIP_SKILL, tip1, tip2));
                }
            }
        }
    }
}
