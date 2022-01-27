package com.somebody.idlframewok.item.armor.masks;

import com.somebody.idlframewok.item.ItemArmorBase;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonDef.MAX_AIR;

public class ItemAirSphere extends ItemArmorBase {
    public ItemAirSphere(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        ignoreVanillaSystem = true;
    }

    @Override
    public void onUpdateWearing(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdateWearing(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote)
        {
            EntityLivingBase living = (EntityLivingBase) entityIn;

            if (living.isInWater() && (living.getActivePotionEffect(MobEffects.WATER_BREATHING) == null))
            {
                living.setAir(MAX_AIR);
                int i = EnchantmentHelper.getRespirationModifier(living);
                if (i == 0 || (living.getRNG().nextInt(i + 1) == 0))
                {
                     stack.damageItem(1, living);
                }
            }
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType)
        {
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Armor", (double)-1f, 0));
        }

        return multimap;
    }
}
