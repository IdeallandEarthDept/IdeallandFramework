package com.somebody.idlframewok.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class ItemAdaptingBase extends ItemBase {
    public ItemAdaptingBase(String name) {
        super(name);
    }

    public float base_power = 6f;
    public float base_range = 3f;
    public float base_cd = 10f;

    public float getPower(ItemStack stack)
    {
        float result = base_power;
        int powerEnchant = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

        if (powerEnchant > 0)
        {
            result += powerEnchant ;
        }
        return result;
    }

    public float getRange(ItemStack stack)
    {
        float result = base_power;
        int powerEnchant = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

        if (powerEnchant > 0)
        {
            result += powerEnchant ;
        }
        return result;
    }


    public int getCoolDownTicks(ItemStack stack)
    {
        return (int) (base_cd * TICK_PER_SECOND);
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
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (!useable)
        {
            return;
        }

        onCreatureStoppedUsing(stack, worldIn, entityLiving, timeLeft);
        entityLiving.swingArm(entityLiving.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);

        if (!worldIn.isRemote)
        {
            if (entityLiving instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityLiving;
                {
                    entityplayer.getCooldownTracker().setCooldown(this, getCoolDownTicks(stack));
                }
            }
        }
    }


    public void onCreatureStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {

    }
}
