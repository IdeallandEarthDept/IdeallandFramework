package com.somebody.idlframework.util.helper;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TooltipHelper {
    public static void strline(List<String> tooltipIn,String s){
        for(String strline: I18n.format(s).split("\n")){
            tooltipIn.add(strline);
        }
    }
    public static List<String> strline(String s){
        List<String> strings=new ArrayList<>();
        for(String strline: I18n.format(s).split("\n")){
            strings.add(strline);
        }
        return strings;
    }
    public static void potionTooltip(List<PotionEffect> potions, List<String> tooltipIn) {
        List<PotionEffect> list = potions;
        float durationFactor = 1.0F;
        List<Tuple<String, AttributeModifier>> list1 = Lists.<Tuple<String, AttributeModifier>>newArrayList();

        if (list.isEmpty())
        {
            String s = net.minecraft.util.text.translation.I18n.translateToLocal("effect.none").trim();
            tooltipIn.add(TextFormatting.GRAY + s);
        }
        else
        {
            for (PotionEffect potioneffect : list)
            {
                String s1 = net.minecraft.util.text.translation.I18n.translateToLocal(potioneffect.getEffectName()).trim();
                Potion potion = potioneffect.getPotion();
                Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();

                if (!map.isEmpty())
                {
                    for (Map.Entry<IAttribute, AttributeModifier> entry : map.entrySet())
                    {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list1.add(new Tuple(((IAttribute)entry.getKey()).getName(), attributemodifier1));
                    }
                }

                if (potioneffect.getAmplifier() > 0)
                {
                    s1 = s1 + " " + net.minecraft.util.text.translation.I18n.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
                }

                if (potioneffect.getDuration() > 20)
                {
                    s1 = s1 + " (" + Potion.getPotionDurationString(potioneffect, durationFactor) + ")";
                }

                if (potion.isBadEffect())
                {
                    tooltipIn.add(TextFormatting.RED + s1);
                }
                else
                {
                    tooltipIn.add(TextFormatting.BLUE + s1);
                }
            }
        }

        if (!list1.isEmpty())
        {
            tooltipIn.add("");
            tooltipIn.add(TextFormatting.DARK_PURPLE + net.minecraft.util.text.translation.I18n.translateToLocal("potion.whenDrank"));

            for (Tuple<String, AttributeModifier> tuple : list1)
            {
                AttributeModifier attributemodifier2 = tuple.getSecond();
                double d0 = attributemodifier2.getAmount();
                double d1;

                if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2)
                {
                    d1 = attributemodifier2.getAmount();
                }
                else
                {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 > 0.0D)
                {
                    tooltipIn.add(TextFormatting.BLUE + net.minecraft.util.text.translation.I18n.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier2.getOperation(), ItemStack.DECIMALFORMAT.format(d1), net.minecraft.util.text.translation.I18n.translateToLocal("attribute.name." + (String)tuple.getFirst())));
                }
                else if (d0 < 0.0D)
                {
                    d1 = d1 * -1.0D;
                    tooltipIn.add(TextFormatting.RED + net.minecraft.util.text.translation.I18n.translateToLocalFormatted("attribute.modifier.take." + attributemodifier2.getOperation(), ItemStack.DECIMALFORMAT.format(d1), net.minecraft.util.text.translation.I18n.translateToLocal("attribute.name." + (String)tuple.getFirst())));
                }
            }
        }

    }
}
