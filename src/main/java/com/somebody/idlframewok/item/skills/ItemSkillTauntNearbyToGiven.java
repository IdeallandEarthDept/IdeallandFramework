package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.util.IDLGeneral;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class ItemSkillTauntNearbyToGiven extends ItemSkillBase {
    public ItemSkillTauntNearbyToGiven(String name) {
        super(name);
        cool_down = 50f;
        base_range = 10f;
        range_per_level = 10f;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        if (isStackReady(playerIn, stack))
        {
            Vec3d basePos = playerIn.getPositionVector();
            List<EntityLiving> entities = playerIn.world.getEntitiesWithinAABB(EntityLiving.class, IDLGeneral.ServerAABB(basePos.addVector(-getRange(stack), -getRange(stack), -getRange(stack)),
                    basePos.addVector(getRange(stack), getRange(stack), getRange(stack))));
            for (EntityLiving living: entities
            ) {
                if (living != target)
                {
                    living.setAttackTarget(target);
                }
            }
            playerIn.swingArm(handIn);
            activateCoolDown(playerIn, stack);
            target.playSound(SoundEvents.ENTITY_POLAR_BEAR_WARNING, 1f, 1f);
            return true;
        }

        return false;
    }
}
