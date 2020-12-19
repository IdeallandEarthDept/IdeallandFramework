package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IDLGeneral;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_TURN;
import static com.somebody.idlframewok.util.IDLSkillNBT.getLevel;

public class ItemSkillAttackBoost extends ItemSkillBase {
    public ItemSkillAttackBoost(String name) {
        super(name);
        cool_down = 50f;
        maxLevel = 4;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (isStackReady(playerIn, stack))
        {
            if (!worldIn.isRemote)
            {
                Vec3d basePos = playerIn.getPositionVector();
                List<EntityLivingBase> entities = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range), basePos.addVector(base_range, base_range, base_range)));
                for (EntityLivingBase living: entities
                ) {
                    if (EntityUtil.getAttitude(playerIn, living) == EntityUtil.ATTITUDE.FRIEND)
                    {
                        living.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 3*TICK_PER_TURN, getLevel(stack) - 1));
                    }
                }
                playerIn.swingArm(handIn);
                playerIn.playSound(SoundEvents.ENTITY_VILLAGER_YES, 1f, 1f);
                activateCoolDown(playerIn, stack);
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }
}
