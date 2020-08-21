package com.deeplake.idealland.item.skills;

import com.deeplake.idealland.potion.ModPotions;
import com.deeplake.idealland.util.EntityUtil;
import com.deeplake.idealland.util.IDLGeneral;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static com.deeplake.idealland.util.CommonDef.SECOND_PER_TURN;
import static com.deeplake.idealland.util.CommonDef.TICK_PER_TURN;

//todo:FGO skill base
public class ItemSkillMerlinIllusion extends ItemSkillBase {
    public ItemSkillMerlinIllusion(String name) {
        super(name);
        cool_down = 9 * SECOND_PER_TURN;
        maxLevel = 1;
        setVal(0,0);
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
                    EntityUtil.ATTITUDE attitude = EntityUtil.getAttitude(playerIn, living);
                    if (attitude == EntityUtil.ATTITUDE.FRIEND)
                    {
                        living.addPotionEffect(new PotionEffect(ModPotions.INVINCIBLE, 1*TICK_PER_TURN, 0));
                        living.addPotionEffect(new PotionEffect(ModPotions.CRIT_DMG_PLUS, 1*TICK_PER_TURN, (int) getVal(stack)));
                    }
                    else if (attitude == EntityUtil.ATTITUDE.HATE)
                    {
                        living.addPotionEffect(new PotionEffect(ModPotions.CRIT_DMG_MINUS, 3*TICK_PER_TURN, (int) getVal(stack)));
                    }
                }
                playerIn.swingArm(handIn);
                //playerIn.playSound(SoundEvents.ENTITY_VILLAGER_YES, 1f, 1f);
                activateCoolDown(playerIn, stack);
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }
}
