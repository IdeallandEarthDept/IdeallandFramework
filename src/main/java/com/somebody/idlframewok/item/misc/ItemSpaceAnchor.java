package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import java.util.List;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.*;

public class ItemSpaceAnchor extends ItemBase {
    public int chargeTickDefault = 20;

    public ItemSpaceAnchor(String name) {
        super(name);
        useable = true;
    }


    public int chargeNeedTick(ItemStack stack)
    {
        return chargeTickDefault;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 200;
    }

    //Animation
    @Nonnull
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {

        return EnumAction.BOW;

    }

    @Override
    public void clientUseTick(ItemStack stack, EntityLivingBase living, int count) {
//		//Particle;
        //DWeapons.LogWarning(String.format("onUsingTick %s",count));

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
                    if (living.attemptTeleport(pos.getX(), pos.getY(), pos.getZ()))
                    {
                        stack.shrink(1);
                    }
                    else {
                        CommonFunctions.SafeSendMsgToPlayer((EntityPlayer) living, "idlframewok.msg.teleport.fail");
                    }
                }
                else {
                    IDLNBTUtil.markPosToStack(stack, living.getPosition());
                }

                //DWeapons.LogWarning("Weapon mode is:" + GetWeaponMode(stack));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        //tooltip.add(I18n.format("idlframewok.gua_enhance_total.desc", IDLSkillNBT.GetGuaEnhanceTotal(stack)));
        tooltip.add(IDLNBTUtil.getNBT(stack).toString());
    }
//    @Override
//    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
//        return super.onItemUseFinish(stack, worldIn, entityLiving);
//    }
//
//    @Override
//    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//
//        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
//    }
//
//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
//        return super.onItemRightClick(worldIn, playerIn, handIn);
//    }
}
