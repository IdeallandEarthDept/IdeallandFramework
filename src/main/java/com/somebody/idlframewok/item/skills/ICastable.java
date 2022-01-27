package com.somebody.idlframewok.item.skills;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ICastable {
    public boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot);
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot);
}
