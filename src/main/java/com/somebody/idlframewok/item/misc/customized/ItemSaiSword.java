package com.somebody.idlframewok.item.misc.customized;

import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.item.ItemSwordBase;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.ANCHOR_READY;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.ANCHOR_READY_2;

public class ItemSaiSword extends ItemSwordBase implements IWIP {
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
        if (!playerIn.world.isRemote && IDLNBTUtil.GetBoolean(stack, ANCHOR_READY_2))
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

    @SideOnly(Side.CLIENT)
    @Override
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        String key = stack.getUnlocalizedName() + ".desc";
        if (I18n.hasKey(key))
        {
            String mainDesc = I18n.format(key);

            if (IDLNBTUtil.GetBoolean(stack, ANCHOR_READY, false))
            {
                BlockPos pos = IDLNBTUtil.getMarkedPos(stack);
                mainDesc += I18n.format(key + ".pos1", pos.getX(), pos.getY(), pos.getZ() );
            }

            if (IDLNBTUtil.GetBoolean(stack, ANCHOR_READY_2, false))
            {
                BlockPos pos = IDLNBTUtil.getMarkedPos2(stack);
                mainDesc += I18n.format(key + ".pos2", pos.getX(), pos.getY(), pos.getZ());
            }

            return mainDesc;
        }
        return "";
    }
}
