package com.somebody.idlframewok.item.weapon;

import com.somebody.idlframewok.item.ItemSwordBase;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.DIFFICULTY;

public class ItemCubixSword extends ItemSwordBase {
    public ItemCubixSword(String name, ToolMaterial material) {
        super(name, material);
    }

    public double getDifficultyMark(ItemStack stack)
    {
        return IDLNBTUtil.GetDouble(stack, DIFFICULTY, 0f);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return (int) (getDifficultyMark(stack) * getDurabilityPerEnch(stack) + 32);
    }

    public int getDurabilityPerEnch(ItemStack stack)
    {
        return 16;
    }

    public float getDamagePerEnch(ItemStack stack)
    {
        return 0.5f;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 1f + getDifficultyMark(stack) * getDamagePerEnch(stack), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }

}
