package com.somebody.idlframewok.item.armor.masks;

import com.somebody.idlframewok.item.ItemArmorBase;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

//Blue face Dou-Er-Dun, steals the royal horse

public class ItemMaskDouErDun extends ItemArmorBase {
    public ItemMaskDouErDun(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        ignoreVanillaSystem = true;
    }

    @Override
    public void onUpdateWearing(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdateWearing(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote)
        {
            EntityLivingBase living = (EntityLivingBase) entityIn;

            if (entityIn.isSneaking())
            {
                living.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, TICK_PER_SECOND, 0));
            }else if (entityIn.getRidingEntity() != null) {
                Entity riding = entityIn.getRidingEntity();
                if (riding instanceof EntityHorse)
                {
                    EntityHorse horse = (EntityHorse) riding;
                    if (horse.getHorseArmorType() == HorseArmorType.GOLD)//"royal"
                    {
                        living.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, TICK_PER_SECOND, 0));
                        horse.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, TICK_PER_SECOND, 0));
                    }
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
            multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()],"Health modifier", (double)2f, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Atk by Armor", (double)2f, 0));
        }

        return multimap;
    }
}
