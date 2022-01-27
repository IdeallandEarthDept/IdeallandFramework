package com.somebody.idlframewok.potion.instance;

import net.minecraft.entity.ai.attributes.AttributeModifier;

public class ModPotionAttrClassicalBase extends ModPotionBase {
    protected float valPerLevel = 0f;

    public ModPotionAttrClassicalBase(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
        //registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, 0)
    }

    public ModPotionAttrClassicalBase setVal(float valPerLevel)
    {
        this.valPerLevel = valPerLevel;
        return this;
    }

    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier)
    {
        if (modifier.getAmount() < 0)
        {
            return - this.valPerLevel * (double)(amplifier + 1);
        }else {
            return this.valPerLevel * (double)(amplifier + 1);
        }
    }
}
