package com.somebody.idlframewok.item.consumables.food;

import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemChilli extends ItemFoodBase {
    float burnTime = 3f;
    float buffTime = 30f;

    public ItemChilli(String name, int amount, float saturation, boolean isWolfFood) {
        super(name, amount, saturation, isWolfFood);
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        super.onFoodEaten(stack, worldIn, player);
        if (!worldIn.isRemote)
        {
            EntityUtil.ApplyBuff(player, ModPotions.BURN, 0, burnTime);
            float temper = EntityUtil.getTemperature(player);
            if (temper < CommonDef.TEMP_ABOVE_HOT)
            {
                EntityUtil.ApplyBuff(player, MobEffects.STRENGTH, 0, buffTime);
                if (temper < CommonDef.TEMP_ABOVE_COLD)
                {
                    EntityUtil.ApplyBuff(player, MobEffects.HASTE, 0, buffTime);
                }
            }
        }
    }
}
