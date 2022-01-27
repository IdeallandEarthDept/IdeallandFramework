package com.somebody.idlframewok.entity.creatures.misc;

import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

//not mod unit
public class EntityHiredSkeleton extends EntitySkeleton {

    public EntityHiredSkeleton(World worldIn) {
        super(worldIn);
        EntityUtil.giveIfEmpty(this, EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
        EntityUtil.giveIfEmpty(this, EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
        EntityUtil.giveIfEmpty(this, EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
        EntityUtil.giveIfEmpty(this, EntityEquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));

        EntityUtil.giveIfEmpty(this, EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        //Idealland.Log(getPositionVector() + " " + getUniqueID().toString());
    }

    protected boolean shouldBurnInDay() {
        return false;
    }
}
