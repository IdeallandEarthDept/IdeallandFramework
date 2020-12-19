package com.somebody.idlframewok.item.weapon;

import com.somebody.idlframewok.item.ItemSwordBase;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemEtherealSword extends ItemSwordBase {
    public ItemEtherealSword(String name, ToolMaterial material) {
        super(name, material);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getEnchantmentTotalLevel(stack) * getDurabilityPerEnch(stack) + 100;
    }

    public int getDurabilityPerEnch(ItemStack stack)
    {
        return 100;
    }

    public float getDamagePerEnch(ItemStack stack)
    {
        return 1f;
    }

    int getEnchantmentTotalLevel(ItemStack stack)
    {
        int result = 0;
        NBTTagList nbttaglist = stack.getEnchantmentTagList();

        for (int j = 0; j < nbttaglist.tagCount(); ++j)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
            int enchID = nbttagcompound.getShort("layer");//enchant ID
            int lvl = nbttagcompound.getShort("lvl");
            Enchantment enchantment = Enchantment.getEnchantmentByID(enchID);

            if (enchantment != null)
            {
                result += lvl;
            }
        }
        return result;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 1f + getEnchantmentTotalLevel(stack) * getDamagePerEnch(stack), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }

}
