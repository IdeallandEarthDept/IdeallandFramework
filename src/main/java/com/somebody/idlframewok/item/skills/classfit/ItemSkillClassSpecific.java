package com.somebody.idlframewok.item.skills.classfit;

import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.MessageDef;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static com.somebody.idlframewok.item.skills.classfit.SkillClassUtil.isCorrectClass;

public abstract class ItemSkillClassSpecific extends ItemSkillBase implements IWIP {
    public final EnumSkillClass skillClass;
    public ItemSkillClassSpecific(String name, EnumSkillClass skillClass) {
        super(name);
        this.skillClass = skillClass;
        offHandCast = false;
        setCD(10f,0f);
    }

    @Override
    public boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot, boolean showMsg) {
        if (isCorrectClass(livingBase, skillClass))
        {
            return super.canCast(worldIn, livingBase, stack, slot, showMsg);
        }
        return false;
    }


    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (isCorrectClass(livingBase, skillClass))
        {
            return super.applyCast(worldIn, livingBase, stack, slot);
        }
        else {
            if (!worldIn.isRemote)
            {
                CommonFunctions.SafeSendMsgToPlayer(livingBase, MessageDef.MSG_WRONG_CLASS);
            }

            return false;
        }

    }
}
