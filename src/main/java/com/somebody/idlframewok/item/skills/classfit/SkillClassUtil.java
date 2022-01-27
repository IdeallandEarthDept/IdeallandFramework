package com.somebody.idlframewok.item.skills.classfit;

import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class SkillClassUtil {
    final static float[] skillModifier = {0.1f,0.2f,0.25f,0.3f,0.4f};
    final static float[] coreModifier = {0.4f,0.6f,0.7f,0.8f,1.0f};

    public static float getSkillTotalModifierForLevel(int level)
    {
        int newIndex = level-1;
        newIndex = CommonFunctions.clamp(newIndex, 0, skillModifier.length-1);
        return skillModifier[newIndex];
    }

    public static float getSkillTotalFactorForLevelPlus(int level)
    {
        return getSkillTotalModifierForLevel(level) + 1f;
    }

    public static float getCoreTotalModifierForLevel(int level)
    {
        int newIndex = level-1;
        newIndex = CommonFunctions.clamp(newIndex, 0, coreModifier.length-1);
        return coreModifier[newIndex];
    }

    public static boolean isCorrectClass(EntityLivingBase base, EnumSkillClass skillClass) {
        ItemStack coreStack = base.getHeldItemOffhand();
        if (coreStack.getItem() instanceof ItemSkillCore) {
            //wrong core?
            return ((ItemSkillCore) coreStack.getItem()).getSkillClass(coreStack) == skillClass;
        }
        return false;
    }

    public static int getClassLevelForLiving(EntityLivingBase base, EnumSkillClass skillClass)
    {
        if (isCorrectClass(base, skillClass))
        {
            int level = 1;
            for (EntityEquipmentSlot slot:
                    EntityEquipmentSlot.values()) {
                ItemStack stack = base.getItemStackFromSlot(slot);
                if (stack.getItem() instanceof IClassBooster)
                {
                    level += ((IClassBooster) stack.getItem()).getBoostLevel(stack, skillClass);
                }
            }

            return level;
        }

        return 0;//not holding a core
    }
}
