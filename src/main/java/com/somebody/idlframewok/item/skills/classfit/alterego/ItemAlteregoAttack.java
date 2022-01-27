package com.somebody.idlframewok.item.skills.classfit.alterego;

import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityAlterego;
import com.somebody.idlframewok.item.skills.classfit.EnumSkillClass;
import com.somebody.idlframewok.item.skills.classfit.SkillClassUtil;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IDLSkillNBT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class ItemAlteregoAttack extends ItemAlteregoCommandBase {

    UUID ALTER_EGO_ATTR = UUID.fromString("51591f1c-f1ac-4f1d-9bd0-423a5cb85c8d");
    int NO_SELF_HURT_LEVEL = 2;

    public ItemAlteregoAttack(String name) {
        super(name);
        setCD(2f,0f);
        setDura(10f, 0f);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (isSelected && worldIn.isRemote)
        {
            List<EntityAlterego> alteregoList = worldIn.getEntities(EntityAlterego.class, EntityUtil.ALL_ALIVE);

            for (EntityAlterego instance :
                    alteregoList) {
                if (instance.player == entityIn) {
                    //draws the range
                    EntityUtil.spawnCubeParticleAround(instance, EnumParticleTypes.FLAME, 3);
                }
            }
        }
    }

    @Override
    public void applyToEgo(EntityAlterego alterego, EntityLivingBase player, ItemStack stack) {
        super.applyToEgo(alterego, player, stack);
        alterego.setBehaviorMode(EntityAlterego.EnumActionMode.ATTACK);

        EntityUtil.ApplyBuff(alterego, MobEffects.STRENGTH, 0, getDura(stack));
        EntityUtil.ApplyBuff(player, MobEffects.WEAKNESS, 0, getDura(stack));

        int skillLevel = IDLSkillNBT.getLevel(stack);
        int playerLevel = SkillClassUtil.getClassLevelForLiving(player, EnumSkillClass.EGO_TWIN);

        List<EntityLivingBase> livingBases =
                EntityUtil.getEntitiesWithinAABB(alterego.world, EntityLivingBase.class,
                        alterego.getPositionEyes(0),
                        getRangeAOE(skillLevel, playerLevel),
                        EntityUtil.ALL_ALIVE);

        //boost atk before attacking
        EntityUtil.boostAttrRatio(alterego,
                SharedMonsterAttributes.ATTACK_DAMAGE,
                SkillClassUtil.getSkillTotalModifierForLevel(skillLevel),
                ALTER_EGO_ATTR);

        for (EntityLivingBase nearby : livingBases)
        {
            if (nearby != alterego)//will not attack itself
            {
                if (nearby != alterego.player || skillLevel < NO_SELF_HURT_LEVEL)//may hurt owner if skill lv low.
                {
                    alterego.attackEntityAsMob(nearby);
                }
            }
        }

        //remove temporal boost
        EntityUtil.boostAttrRatio(alterego,
                SharedMonsterAttributes.ATTACK_DAMAGE,
                -SkillClassUtil.getSkillTotalModifierForLevel(skillLevel),
                ALTER_EGO_ATTR);
    }

    public float getRangeAOE(int classLevel, int skillLevel)
    {
        return 3f;
    }
}
