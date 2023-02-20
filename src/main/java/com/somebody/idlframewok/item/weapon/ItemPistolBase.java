package com.somebody.idlframewok.item.weapon;

import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import com.somebody.idlframewok.entity.projectiles.ProjectileArgs;
import com.somebody.idlframewok.item.ItemAdaptingBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPistolBase extends ItemAdaptingBase {
    public ItemPistolBase(String name) {
        super(name);
        setRangedWeapon();
        useable = true;
        base_cd = 1f;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 256;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    public EntityIdlProjectile getBullet(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        return new EntityIdlProjectile(worldIn, new ProjectileArgs(5f), entityLiving,
                entityLiving.getLookVec().x * 10,
                entityLiving.getLookVec().y * 10,
                entityLiving.getLookVec().z * 10);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.PUNCH || enchantment == Enchantments.INFINITY)
        {
            return false;
        }

        if (enchantment == Enchantments.POWER || enchantment == Enchantments.UNBREAKING)
        {
            return true;
        }

        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void onCreatureStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (!worldIn.isRemote)
        {
            EntityIdlProjectile bullet = getBullet(stack, worldIn, entityLiving, timeLeft);

            int powerEnchant = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

            if (powerEnchant > 0)
            {
                bullet.args.damage += (double)powerEnchant * 0.5D + 0.5D;
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
            {
                bullet.setFire(100);
            }

            worldIn.spawnEntity(bullet);
        }
    }
}
