package com.somebody.idlframewok.item.consumables.food;

import com.somebody.idlframewok.designs.events.design.EventsParasiteCurse;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCureParasite extends ItemFoodBase {

    public ItemCureParasite(String name, int amount, float saturation, boolean isWolfFood) {
        super(name, amount, saturation, isWolfFood);
        shiftToShowDesc = true;
        setAlwaysEdible();
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);

        if (!worldIn.isRemote)
        {
            int parasite = EventsParasiteCurse.getInfectionRate(player);

            int cureAmount = Math.max(value_main, parasite);
            EntityUtil.ApplyBuff(player, MobEffects.NAUSEA, 0, value_main * 10);

            EventsParasiteCurse.setInfectionRate(player, Math.max(0, parasite - value_main));
        }
    }
}
