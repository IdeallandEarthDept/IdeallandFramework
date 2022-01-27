package com.somebody.idlframewok.item.skills.classfit.alterego;

import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityAlterego;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;

import static com.somebody.idlframewok.item.skills.classfit.SkillClassUtil.getSkillTotalFactorForLevelPlus;

public class ItemAlteregoFollow extends ItemAlteregoCommandBase {
    public ItemAlteregoFollow(String name) {
        super(name);
        setDura(8f,0f);
    }

    @Override
    public float getCoolDown(int level) {
        return super.getCoolDown(1) / (getSkillTotalFactorForLevelPlus(level));
    }

    @Override
    public void applyToEgo(EntityAlterego alterego, EntityLivingBase player, ItemStack stack) {
        super.applyToEgo(alterego, player, stack);
        alterego.setBehaviorMode(EntityAlterego.EnumActionMode.FOLLOW);
        alterego.setAttackTarget(null);

        alterego.attemptTeleport(player.posX + player.getLookVec().x,
                player.posY,
                player.posZ + player.getLookVec().z);

        EntityUtil.ApplyBuff(alterego, MobEffects.SPEED, 0, getDura(stack));
        EntityUtil.ApplyBuff(player, MobEffects.SPEED, 0, getDura(stack));
    }
}
