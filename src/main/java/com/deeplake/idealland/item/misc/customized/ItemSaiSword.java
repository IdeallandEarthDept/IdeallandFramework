package com.deeplake.idealland.item.misc.customized;

import com.deeplake.idealland.item.ItemSwordBase;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.EntityUtil;
import com.deeplake.idealland.util.IDLGeneral;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.ANCHOR_READY;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.KILL_COUNT_ITEM;

public class ItemSaiSword extends ItemSwordBase {
    public ItemSaiSword(String name, ToolMaterial material) {
        super(name, material);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (IDLNBTUtil.GetBoolean(stack, IDLNBTDef.MARKING_POS_A))
        {
            IDLNBTUtil.markPosToStack(stack, entityIn.getPosition());
            IDLNBTUtil.SetBoolean(stack, IDLNBTDef.MARKING_POS_A, false);
        }

        if (IDLNBTUtil.GetBoolean(stack, IDLNBTDef.MARKING_POS_B))
        {
            IDLNBTUtil.markPosToStack2(stack, entityIn.getPosition());
            IDLNBTUtil.SetBoolean(stack, IDLNBTDef.MARKING_POS_B, false);
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (!playerIn.world.isRemote && IDLNBTUtil.GetBoolean(stack, IDLNBTDef.ANCHOR_READY_2))
        {
            BlockPos targetPos = IDLNBTUtil.getMarkedPos2(stack);
            target.attemptTeleport(target.posX, target.posY, targetPos.getZ());
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }


    public int chargeNeedTick(ItemStack stack)
    {
        return TICK_PER_SECOND;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return TICK_PER_SECOND * 3;
    }

    //Animation
    @Nonnull
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public void clientUseTick(ItemStack stack, EntityLivingBase living, int count) {
        if (count >= chargeNeedTick(stack)) {
            CreateParticle(stack, living);
        }
    }

    private void CreateParticle(ItemStack stack, EntityLivingBase living) {
        EntityUtil.SpawnParticleAround(living, EnumParticleTypes.PORTAL, 10);
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase living, int time) {
        //change mode
        if (!world.isRemote) {
            if (getMaxItemUseDuration(stack) - time >= chargeNeedTick(stack))
            {
                if (IDLNBTUtil.GetBoolean(stack, ANCHOR_READY, false))
                {
                    BlockPos pos = IDLNBTUtil.getMarkedPos(stack);
                    living.attemptTeleport(pos.getX(), pos.getY(), pos.getZ());
                }
                //DWeapons.LogWarning("Weapon mode is:" + GetWeaponMode(stack));
            }
        }
    }

}
