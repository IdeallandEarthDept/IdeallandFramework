package com.somebody.idlframewok.item.consumables;

import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.WorldInfo;

public class ItemRainCall extends ItemConsumableBase {
    public ItemRainCall(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onConsume(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        Biome biome = worldIn.getBiome(playerIn.getPosition());

        if (biome.canRain() && !worldIn.isRaining())
        {
            if (worldIn.isRemote)
            {
                playerIn.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 1f, 1f);
            }else {
                Rain(playerIn, 12000);
            }

            //worldIn.setRainStrength(100f);
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
        else {
            if (playerIn instanceof EntityPlayerMP)
            {
                CommonFunctions.SendMsgToPlayer((EntityPlayerMP) playerIn, "idlframewok.msg.cannot_rain");
            }
            return new ActionResult<ItemStack>(EnumActionResult.FAIL,stack);
        }
    }

    void Rain(EntityPlayer playerIn, int tick)
    {
        if (playerIn.world.isRemote)
            return;

        EntityPlayerMP playerMP = (EntityPlayerMP)(playerIn);
        WorldInfo worldInfo = playerMP.getEntityWorld().getWorldInfo();

        worldInfo.setRaining(true);

        worldInfo.setRainTime(tick);
    }
}
