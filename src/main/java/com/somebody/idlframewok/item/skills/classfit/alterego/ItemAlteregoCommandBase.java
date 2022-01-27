package com.somebody.idlframewok.item.skills.classfit.alterego;

import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityAlterego;
import com.somebody.idlframewok.item.skills.classfit.EnumSkillClass;
import com.somebody.idlframewok.item.skills.classfit.ItemSkillClassSpecific;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public abstract class ItemAlteregoCommandBase extends ItemSkillClassSpecific {
    public ItemAlteregoCommandBase(String name) {
        super(name, EnumSkillClass.EGO_TWIN);
    }

    public void applyToEgo(EntityAlterego alterego, EntityLivingBase player, ItemStack stack)
    {

    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (!worldIn.isRemote) {
            if (livingBase instanceof EntityPlayer) {
                List<EntityAlterego> alteregoList = worldIn.getEntities(EntityAlterego.class, EntityUtil.ALL_ALIVE);

                for (EntityAlterego instance :
                        alteregoList) {
                    if (instance.player == livingBase) {
                        applyToEgo(instance,livingBase,stack);

                        return super.applyCast(worldIn, livingBase, stack, slot);//can just control one.
                    }
                }
            }
        }
        return super.applyCast(worldIn, livingBase, stack, slot);
    }

}
