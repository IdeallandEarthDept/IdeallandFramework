package com.somebody.idlframewok.item.misc.whatnots;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemBeatBox extends ItemBase {

    public ItemBeatBox(String name) {
        super(name);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        if (!world.isRemote) {
            if (player.isSneaking()) {
                IDLNBTUtil.addInt(player.getHeldItem(hand), IDLNBTDef.STATE, 1);
            } else {
                IDLNBTUtil.addInt(player.getHeldItem(hand), IDLNBTDef.STATE_2, 1);
            }
            PlayerUtil.setCoolDown(player, hand);
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && entityIn instanceof EntityLivingBase) {
            EntityLivingBase livingBase = (EntityLivingBase) entityIn;
            if (livingBase.getHeldItemOffhand() == stack) {
                int highTicks = 3;
                int period = 3 + IDLNBTUtil.GetInt(stack, IDLNBTDef.STATE);
                int offset = IDLNBTUtil.GetInt(stack, IDLNBTDef.STATE_2);

                if (worldIn.getTotalWorldTime() % period == offset) {
                    livingBase.addPotionEffect(new PotionEffect(MobEffects.SPEED, highTicks, 0));
                }
            }
        }
    }
}
