package com.somebody.idlframewok.enchantments;

import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.EnumParticleTypes;

public class ModEnchantmentAffinity extends ModEnchantmentBase {

    Item.ToolMaterial toolMaterial;
    ItemArmor.ArmorMaterial armorMaterial;

    public Item.ToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public ModEnchantmentAffinity setToolMaterial(Item.ToolMaterial toolMaterial) {
        this.toolMaterial = toolMaterial;
        return this;
    }

    public ItemArmor.ArmorMaterial getArmorMaterial() {
        return armorMaterial;
    }

    public ModEnchantmentAffinity setArmorMaterial(ItemArmor.ArmorMaterial armorMaterial) {
        this.armorMaterial = armorMaterial;
        return this;
    }

    public float getDamageModifierPlus(EntityLivingBase livingBase)
    {
        return getValue(livingBase) * checkAffinityLevel(livingBase);
    }

    public int checkAffinityLevel(EntityLivingBase livingBase)
    {
        int result = 0;
        for (EntityEquipmentSlot slot:
             EntityEquipmentSlot.values()) {

            if (checkMatch(livingBase.getItemStackFromSlot(slot)))
            {
                result++;
            }
        }

        return result;
    }

    public boolean checkMatch(ItemStack stack)
    {
        Item item = stack.getItem();
        if (item instanceof ItemArmor)
        {
            return ((ItemArmor) item).getArmorMaterial() == getArmorMaterial();
        }

        if (item instanceof ItemTool)
        {
            return ((ItemTool) item).getToolMaterialName().equals(getToolMaterial().name());
        }

        if (item instanceof ItemSword)
        {
            return ((ItemSword) item).getToolMaterialName().equals(getToolMaterial().name());
        }

        return false;
    }

    EnumParticleTypes particleTypes = EnumParticleTypes.ENCHANTMENT_TABLE;
    public ModEnchantmentAffinity setParticleTypes(EnumParticleTypes particleTypes) {
        this.particleTypes = particleTypes;
        return this;
    }

    public ModEnchantmentAffinity(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(name, rarityIn, typeIn, slots);
        CommonFunctions.addToEventBus(this);
    }






}
