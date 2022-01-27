package com.somebody.idlframewok.item.weapon;

import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.item.ItemSwordBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

//todo:desc note feature
public class ItemWearingSword extends ItemSwordBase implements IWIP {

    public ItemWearingSword(String name, ToolMaterial material) {
        super(name, material);
    }

    float minDuraRatio = 0.1f;
    float maxDuraRatio = 0.9f;
    public float getDamageRatio(ItemStack stack)
    {
        float duraLeftRatio = 1f - (float) stack.getItemDamage() / stack.getMaxDamage();
        if (duraLeftRatio <= minDuraRatio)
        {
            duraLeftRatio = minDuraRatio;
        } else if (duraLeftRatio >= maxDuraRatio)
        {
            duraLeftRatio = maxDuraRatio;
        }

        return 0.5f + 0.5f * CommonFunctions.invLerpUnclamped(duraLeftRatio, minDuraRatio, maxDuraRatio);
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, IDLNBTDef.NAME_WEAPON_MODIFIER, (double)getBaseAttackDamage() * getDamageRatio(stack), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, IDLNBTDef.NAME_WEAPON_MODIFIER, SWORD_ATTR_ATK_SPD, 0));
        }

        return multimap;
    }
}
