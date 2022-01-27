package com.somebody.idlframewok.item.skills.classfit;

import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.item.skills.ItemSkillBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSkillCore extends ItemSkillBase implements IWIP {
    EnumSkillClass skillClass = EnumSkillClass.NONE;
    public ItemSkillCore(String name, EnumSkillClass skillClass) {
        super(name);
        this.skillClass = skillClass;
        offHandCast = true;
        mainHandCast = false;
    }

    public EnumSkillClass getSkillClass(ItemStack stack)
    {
        return skillClass;
    }

    //convienient approach
    public int getSelfClassLevel(EntityLivingBase livingBase)
    {
        return SkillClassUtil.getClassLevelForLiving(livingBase, skillClass);
    }

    @Override
    public boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot, boolean showMsg) {
        return super.canCast(worldIn, livingBase, stack, slot, showMsg) && livingBase.getHeldItemMainhand().isEmpty();
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        return canCast(worldIn, livingBase, stack, slot) && super.applyCast(worldIn, livingBase, stack, slot);
    }
}
