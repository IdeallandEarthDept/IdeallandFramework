package com.somebody.idlframewok.designs;

import com.somebody.idlframewok.item.armor.ItemArmorResistance;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;

public interface IDamageResistor {

    static boolean isOfType(DamageSource source, EnumDamageType damageType)
    {
        switch (damageType)
        {
            case NORMAL:
                return !(source.isMagicDamage() || source.isProjectile() || source.isExplosion() || source.isMagicDamage());
            case PROJECTILE:
                return source.isProjectile();
            case FIRE:
                return source.isFireDamage();
            case EXPLOSION:
                return source.isExplosion();
            case MAGIC:
                return source.isMagicDamage();
        }
        return false;
    }

    default float modifierByType(DamageSource source, EnumDamageType damageType)
    {
        if (isOfType(source, damageType))
        {
            return getResistance(damageType).modifier;
        }
        else {
            return getInvertResistance(damageType).modifier;
        }
    }

    default float getModifier(DamageSource source)
    {
        return modifierByType(source, EnumDamageType.FIRE) *
                modifierByType(source, EnumDamageType.EXPLOSION) *
                modifierByType(source, EnumDamageType.MAGIC) *
                modifierByType(source, EnumDamageType.PROJECTILE) *
                modifierByType(source, EnumDamageType.NORMAL);
    }

    default EnumDamageResistance getResistance(EnumDamageType type)
    {
        if (this instanceof EntityLivingBase)
        {
            Item item = ((EntityLivingBase) this).getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
            if (item instanceof ItemArmorResistance)
            {
                return ((ItemArmorResistance) item).getResistance(type);
            }
        }

        return EnumDamageResistance.NONE;
    }

    //resistance to non-X damage
    default EnumDamageResistance getInvertResistance(EnumDamageType type)
    {
        return EnumDamageResistance.NONE;
    }
}
