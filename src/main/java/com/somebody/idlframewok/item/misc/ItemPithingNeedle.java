package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.IMPRINT_DESC;

public class ItemPithingNeedle extends ItemBase {

    public ItemPithingNeedle(String name) {
        super(name);
        CommonFunctions.addToEventBus(this);
        useable = true;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (PlayerUtil.isCreative(playerIn))
        {
            stack = playerIn.getHeldItem(hand);
        }

        if (target instanceof EntityLiving)
        {
            World world = playerIn.world;
            if (IDLNBTUtil.GetString(stack, IDLNBTDef.IMPRINT, CommonDef.EMPTY).equals(CommonDef.EMPTY))
            {
                if (world.isRemote)
                {
                    target.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1f, 2f);
                }else {
                    IDLNBTUtil.SetString(stack, IDLNBTDef.IMPRINT, EntityUtil.getRegName(target));
                }
                return true;
            }
            else {
                return false;
            }
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(I18n.format(IMPRINT_DESC, I18n.format(IDLNBTUtil.GetString(stack, IDLNBTDef.IMPRINT, CommonDef.EMPTY))));
        return super.getMainDesc(stack, world, tooltip, flag);
    }

    @SubscribeEvent
    public void onSpawn(LivingSpawnEvent.CheckSpawn event)
    {
        if (event.getWorld().isRemote)
        {
            return ;
        }

        List<EntityPlayer> players = event.getWorld().playerEntities;
        for (EntityPlayer player:
             players) {
            ItemStack stack = player.getHeldItemOffhand();
            String state = IDLNBTUtil.GetString(stack, IDLNBTDef.IMPRINT, CommonDef.EMPTY);
            if (stack.getItem() == this && !state.equals(CommonDef.EMPTY))
            {
                if (EntityUtil.getRegName(event.getEntityLiving()).equals(state))
                {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
