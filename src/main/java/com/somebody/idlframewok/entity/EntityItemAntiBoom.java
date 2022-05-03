package com.somebody.idlframewok.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityItemAntiBoom extends EntityItem {
    public EntityItemAntiBoom(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityItemAntiBoom(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
    }

    public EntityItemAntiBoom(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isExplosion())
        {
            return false;
        }

        return super.attackEntityFrom(source, amount);
    }
}
