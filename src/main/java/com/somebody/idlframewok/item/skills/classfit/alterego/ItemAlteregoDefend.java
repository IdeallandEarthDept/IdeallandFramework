package com.somebody.idlframewok.item.skills.classfit.alterego;

import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityAlterego;
import com.somebody.idlframewok.item.skills.classfit.SkillClassUtil;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IDLSkillNBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;

public class ItemAlteregoDefend extends ItemAlteregoCommandBase {
    public ItemAlteregoDefend(String name) {
        super(name);
        setDura(5f, 0f);
    }

    @Override
    public void applyToEgo(EntityAlterego alterego, EntityLivingBase player, ItemStack stack) {
        super.applyToEgo(alterego, player, stack);
        alterego.setBehaviorMode(EntityAlterego.EnumActionMode.DEFEND);

        EntityUtil.ApplyBuff(alterego, ModPotions.INVINCIBLE, 0, SkillClassUtil.getSkillTotalModifierForLevel(IDLSkillNBT.getLevel(stack)));
        EntityUtil.ApplyBuff(player, MobEffects.RESISTANCE, 0, getDura(stack) * (1 + SkillClassUtil.getSkillTotalModifierForLevel(IDLSkillNBT.getLevel(stack))));
    }
}
