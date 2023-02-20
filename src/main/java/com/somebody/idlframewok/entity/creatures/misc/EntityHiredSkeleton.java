package com.somebody.idlframewok.entity.creatures.misc;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityHiredSkeleton extends EntitySkeleton {

    boolean canDespawnNatural = true;

    public void SetDespawnable(boolean val)
    {
        canDespawnNatural = val;
    }

    public EntityHiredSkeleton(World worldIn) {
        super(worldIn);
        setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
        setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
        setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
        setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));

        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        //IdlFramework.Log(getPositionVector() + " " + getUniqueID().toString());
    }

    protected boolean shouldBurnInDay()
    {
        return false;
    }
    protected boolean canDespawn()
    {
        return canDespawnNatural;
    }
}
