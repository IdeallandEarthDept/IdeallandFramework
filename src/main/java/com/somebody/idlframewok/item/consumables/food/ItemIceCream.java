package com.somebody.idlframewok.item.consumables.food;

import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemIceCream extends ItemFoodBase {
    float buffTime = 30f;

    public ItemIceCream(String name, int amount) {
        super(name, amount, 1f, false);
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);
        if (!worldIn.isRemote) {
            float temper = EntityUtil.getTemperature(player);
            player.extinguish();
            EntityUtil.TryRemoveGivenBuff(player, ModPotions.BURN);
            EntityUtil.TryRemoveGivenBuff(player, ModPotions.FEAR);
            EntityUtil.TryRemoveGivenBuff(player, ModPotions.ANGER);
            //Yes! Give them a stick!
            PlayerUtil.giveToPlayer(player, new ItemStack(Items.STICK));

            if (temper > CommonDef.TEMP_ABOVE_HOT) {
                EntityUtil.ApplyBuff(player, MobEffects.STRENGTH, 0, buffTime);
                EntityUtil.ApplyBuff(player, MobEffects.HASTE, 0, buffTime);

                if (EntityUtil.getBuffLevelIDL(player, ModPotions.SADNESS) == 0) {
                    EntityUtil.ApplyBuff(player, ModPotions.HAPPINESS, 0, buffTime);
                }
            } else if (temper < CommonDef.TEMP_ABOVE_COLD) {
                EntityUtil.ApplyBuff(player, MobEffects.MINING_FATIGUE, 0, buffTime);
                EntityUtil.ApplyBuff(player, MobEffects.WEAKNESS, 0, buffTime);
                EntityUtil.ApplyBuff(player, MobEffects.FIRE_RESISTANCE, 0, buffTime);
            }
        }
    }
}
