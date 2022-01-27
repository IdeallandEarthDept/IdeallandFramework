package com.somebody.idlframewok.item.skills.combat;

import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSkillKnightsAttack extends ItemSkillBase {
    public ItemSkillKnightsAttack(String name) {
        super(name);
        cool_down = 300f;
        maxLevel = 1;

        base_range = 16f;
        setRarity(EnumRarity.EPIC);

        showRangeDesc = true;
    }

    public static final Predicate<EntityLivingBase> riding = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            if (p_apply_1_ == null)
            {
                return false;
            }
            return  p_apply_1_.isRiding() || p_apply_1_.isBeingRidden();
        }
    };

    @Override
    public boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot, boolean showMsg) {
        return livingBase.isRiding() && super.canCast(worldIn, livingBase, stack, slot, showMsg);
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (worldIn.isRemote) {
            livingBase.playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL, 2f, 1f);
            return true;
        }

        Vec3d basePos = livingBase.getPositionVector();
        List<EntityLivingBase> entities = worldIn.getEntitiesWithinAABB(EntityLivingBase.class,
                IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range), basePos.addVector(base_range, base_range, base_range))
                , riding);

        int count = 0;
        int horseCount = 0;

        for (EntityLivingBase living : entities
        ) {
            if (EntityUtil.getAttitude(livingBase, living) == EntityUtil.EnumAttitude.FRIEND) {
                EntityUtil.ApplyBuff(living, MobEffects.STRENGTH, IDLSkillNBT.getLevel(stack) - 1, 30);
                EntityUtil.ApplyBuff(living, MobEffects.SPEED, IDLSkillNBT.getLevel(stack) - 1, 30);
                EntityUtil.ApplyBuff(living, ModPotions.CRIT_DMG_PLUS, IDLSkillNBT.getLevel(stack) - 1, 30);
                EntityUtil.ApplyBuff(living, ModPotions.CRIT_CHANCE_PLUS, IDLSkillNBT.getLevel(stack) - 1, 30);

                if (living.isRiding()) {
                    count++;
                }

                if (living.isBeingRidden()) {
                    horseCount++;
                }


                if (living instanceof EntityPlayer) {
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.BOLD, (EntityPlayer) living, "idlframewok.msg.knights_attack", livingBase.getDisplayName());
                }
            }
        }

        if (count <= 1 && horseCount <= 1) {
            for (EntityLivingBase living : entities
            ) {
                if (EntityUtil.getAttitude(livingBase, living) == EntityUtil.EnumAttitude.FRIEND) {
                    EntityUtil.ApplyBuff(living, ModPotions.UNDYING, 0, 60);
                    EntityUtil.ApplyBuff(living, ModPotions.INVINCIBLE, 0, 3);
                }
            }
        }

        if (slot == EntityEquipmentSlot.OFFHAND) {
            livingBase.swingArm(EnumHand.OFF_HAND);
        } else if (slot == EntityEquipmentSlot.MAINHAND){
            livingBase.swingArm(EnumHand.MAIN_HAND);
        }

        return true;
    }


}
