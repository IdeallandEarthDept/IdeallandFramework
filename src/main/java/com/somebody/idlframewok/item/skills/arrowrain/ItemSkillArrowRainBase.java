package com.somebody.idlframewok.item.skills.arrowrain;

import com.somebody.idlframewok.item.skills.ItemSkillBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import static net.minecraft.entity.SharedMonsterAttributes.ATTACK_DAMAGE;

public class ItemSkillArrowRainBase extends ItemSkillBase {
    public float KBPower = 1f;
    public boolean useSmall = true;
    public ItemSkillArrowRainBase(String name) {
        super(name);
    }

    public ItemSkillArrowRainBase setIsSmallFireBall(boolean val)
    {
        useSmall = val;
        return this;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (isStackReady(playerIn, stack))
        {
            if (!worldIn.isRemote)
            {
                //doRangedAttack(playerIn);
                playerIn.swingArm(handIn);
                activateCoolDown(playerIn, stack);
            }else {
                playerIn.playSound(SoundEvents.ENTITY_FIREWORK_LARGE_BLAST, 1f, 1f);
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }

    protected EntityArrow getArrow(float p_190726_1_, EntityLivingBase shooter)
    {
        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(shooter.world, shooter);
        entitytippedarrow.setEnchantmentEffectsFromEntity(shooter, p_190726_1_);
        return entitytippedarrow;
    }

    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor, EntityLivingBase shooter) {
        //EntityMoroonBullet entityArrow = new EntityMoroonBullet(world, new ProjectileArgs((float) this.getEntityAttribute(ATTACK_DAMAGE).getAttributeValue()));
        EntityArrow entityArrow = getArrow(distanceFactor, shooter);
        double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX - shooter.posX;
        double d2 = d0 - entityArrow.posY;
        double d3 = target.posZ - shooter.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        entityArrow.shoot(d1, d2 + (double)f, d3, 1.6F, 12.0F);
        double damage = shooter.getEntityAttribute(ATTACK_DAMAGE).getAttributeValue();
        if (target.isSneaking())
        {
            damage /= 5f;
        }
        entityArrow.setDamage(shooter.getEntityAttribute(ATTACK_DAMAGE).getAttributeValue());
        shooter.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (shooter.getRNG().nextFloat() * 0.4F + 0.8F));
        shooter.world.spawnEntity(entityArrow);
    }
}
