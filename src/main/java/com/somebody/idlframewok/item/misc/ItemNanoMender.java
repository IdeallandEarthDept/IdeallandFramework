package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ItemNanoMender extends ItemBase {
    public ItemNanoMender(String name, int maxDmg) {
        super(name);
        setMaxDamage(maxDmg);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event)
    {
        if (event.getItemStack().getItem() instanceof ItemNanoMender)
        {
            boolean isOn = IDLNBTUtil.GetInt(event.getItemStack(), IDLNBTDef.STATE) > 0;
            event.getToolTip().add(I18n.format(isOn ? IDLNBTDef.NAME_ON : IDLNBTDef.NAME_OFF));
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        if (!world.isRemote)
        {
            IDLNBTUtil.switchState(player.getHeldItem(hand));
        }

        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote && IDLNBTUtil.GetInt(stack, IDLNBTDef.STATE) > 0)
        {
            EntityPlayer playerIn = (EntityPlayer) entityIn;
            for (EntityEquipmentSlot slot:
                    EntityEquipmentSlot.values()) {

                ItemStack itemstack1 = playerIn.getItemStackFromSlot(slot);
                if (!itemstack1.isEmpty() && itemstack1.isItemDamaged() && !(itemstack1.getItem() instanceof ItemNanoMender)) {
                    //Fix Dura
                    CommonFunctions.repairItem(itemstack1, 1);
                    stack.damageItem(1, playerIn);
                    break;
                }
            }
        }
    }

//    public EnumRarity getRarity(ItemStack stack)
//    {
//        return stack.isItemEnchanted() ? EnumRarity.RARE : EnumRarity.COMMON;
//    }
}
