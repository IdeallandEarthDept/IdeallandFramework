package com.somebody.idlframewok.item.skills;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemSkillFireBall extends ItemSkillBase {
    public float KBPower = 1f;
    public boolean useSmall = true;
    public ItemSkillFireBall(String name) {
        super(name);
    }

    public ItemSkillFireBall setIsSmallFireBall(boolean val)
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
                doRangedAttack(playerIn);
                playerIn.swingArm(handIn);
                activateCoolDown(playerIn, stack);
            }else {
                playerIn.playSound(SoundEvents.ENTITY_FIREWORK_LARGE_BLAST, 1f, 1f);
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }

    protected void doRangedAttack(EntityPlayer caster)
    {
        World worldObj = caster.world;
        ItemStack stack = caster.getHeldItemMainhand();

        double d1 = caster.getCollisionBorderSize();
        Vec3d vec3d = caster.getLookVec().scale(10);

        EntityFireball entityBullet;
        if (useSmall) {
            entityBullet = new EntitySmallFireball(worldObj, caster, vec3d.x, vec3d.y, vec3d.z);
        } else {
            entityBullet = new EntityLargeFireball(worldObj, caster,vec3d.x, vec3d.y, vec3d.z);
            ((EntityLargeFireball)entityBullet).explosionPower = (int)getVal(stack);
        }

        entityBullet.posX = caster.getPositionEyes(0f).x + vec3d.x * d1;
        entityBullet.posY = caster.getPositionEyes(0f).y + vec3d.y * d1;
        entityBullet.posZ = caster.getPositionEyes(0f).z + vec3d.z * d1;
        worldObj.spawnEntity(entityBullet);
    }
}
