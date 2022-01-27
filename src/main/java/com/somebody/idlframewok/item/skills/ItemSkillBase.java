package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.item.ItemAdaptingBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.MessageDef;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.CommonFunctions.*;
import static com.somebody.idlframewok.util.MessageDef.getSkillCastKey;

enum SKILL_MSG_TYPE
{
    SUCCESS,
    CD,
}


public class ItemSkillBase extends ItemAdaptingBase implements ICastable {
    public boolean isMartial = false;

    public ItemSkillBase(String name) {
        super(name);
        setMaxStackSize(1);
        setNoRepair();
        setCreativeTab(ModCreativeTabsList.IDL_SKILL);
        offHandCast = true;
        mainHandCast = true;
    }

    public ItemSkillBase setPassive()
    {
        cannotMouseCast = true;
        offHandCast = false;
        mainHandCast = false;
        return this;
    }

    public void SendDefaultMsg(EntityPlayer player, ItemStack stack, SKILL_MSG_TYPE msg_type)
    {
        switch (msg_type)
        {
            case CD:
                SendMsgToPlayer((EntityPlayerMP)player, stack.getUnlocalizedName()+ IDLSkillNBT.IN_CD_DESC_KEY);
            case SUCCESS:
                SendMsgToPlayer((EntityPlayerMP)player, stack.getUnlocalizedName()+ IDLSkillNBT.SUCCESS_DESC_KEY);
            default:
                break;
        }
    }

    public static void notifyCoolingDown(EntityPlayerMP player)
    {
        CommonFunctions.SendMsgToPlayer(player, "idlframewok.skill.msg.cool_down");
    }

    //--------------------------

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        //stack.setItemDamage(stack.getItemDamage() - 1);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (cannotMouseCast)
        {
            return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        }

        ItemStack stack = playerIn.getHeldItem(handIn);
        if (isStackReady(playerIn, stack))
        {
            if (canCast(worldIn, playerIn, stack, slotFromHand(handIn), !worldIn.isRemote))
            {
                applyCast(worldIn, playerIn, stack, slotFromHand(handIn));
                activateCoolDown(playerIn, stack);
                return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
            }
            else {
                return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));

            }
        }

        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }

    //This will just check, will not give error message. use hasErrorMessage for hinting.
    @Override
    public final boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        return canCast(worldIn, livingBase, stack, slot, false);
    }

    public boolean canCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot, boolean hintErrorMsg) {
        if (livingBase instanceof EntityPlayer)
        {
            switch (slot)
            {
                case MAINHAND:
                    if (!mainHandCast)
                        return false;
                    break;
                case OFFHAND:
                    if (!offHandCast)
                        return false;
                    break;
                case FEET:
                    break;
                case LEGS:
                    break;
                case CHEST:
                    break;
                case HEAD:
                    break;
                default:
                    Idealland.LogWarning("Cast error", new IllegalStateException("Unexpected value: " + slot)); ;
            }
            return isStackReady((EntityPlayer) livingBase, stack);
        }
        return false;
    }

    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot)
    {
        if (livingBase instanceof EntityPlayer)
        {
            activateCoolDown((EntityPlayer) livingBase, stack);
        }
        return true;
    }

    public boolean onKeyboardCast(EntityLivingBase caster, ItemStack stack, EnumHand hand)
    {
        World world = caster.world;
        boolean casterIsPlayer = caster instanceof EntityPlayer;
        if (!casterIsPlayer || isStackReady((EntityPlayer) caster, stack)) {
            if (canCast(world, caster, stack, CommonFunctions.slotFromHand(hand))) {
                if (hand == EnumHand.MAIN_HAND) {
                    if (mainHandCast) {
                        return applyCast(caster.world, caster, stack, CommonFunctions.slotFromHand(hand));
                    } else  {
                        if (casterIsPlayer)
                        {
                            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, (EntityPlayer) caster, MessageDef.NOT_CASTABLE_MAINHAND);
                        }
                        else {
                            Idealland.LogWarning("Trying to do invalid cast from a creature: %s", caster.getName());
                        }
                    }
                } else if (hand == EnumHand.OFF_HAND)
                    if (offHandCast) {
                        return applyCast(caster.world, caster, stack, CommonFunctions.slotFromHand(hand));
                    }else {
                        if (casterIsPlayer)
                        {
                            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, (EntityPlayer) caster, MessageDef.NOT_CASTABLE_OFFHAND);
                        }
                        else {
                            Idealland.LogWarning("Trying to do invalid cast from a creature: %s", caster.getName());
                        }
                    }
            }
        }
        else {
            EntityPlayer player = (EntityPlayer) caster;
            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, player, MessageDef.IN_COOLDOWN);
        }

        return false;
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        return false;
    }

    //Desc
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);
        boolean shiftPressed = !shiftToShowDesc || isShiftPressed();
        if (shiftPressed)
        {
            if (showCDDesc)
                tooltip.add(I18n.format("idlframewok.skill.shared.cool_down_desc", getCoolDown(stack)));
            if (showDamageDesc && getVal(stack) > 0)
                tooltip.add(I18n.format("idlframewok.skill.shared.power_desc", getVal(stack)));
            if (showRangeDesc)
                tooltip.add(I18n.format("idlframewok.skill.shared.range_desc", getRange(stack)));
            if (showDuraDesc)
                tooltip.add(GetDuraDescString(getDura(stack)));

            if (maxLevel != 1)
            {
                tooltip.add(I18n.format("idlframewok.skill.shared.level_desc", IDLSkillNBT.getLevel(stack), maxLevel));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (maxLevel == 1)
        {
            return super.getItemStackDisplayName(stack);
        }

//    	String strMain ="";
////    	if (IsNameHidden(stack))
////    	{
////    		strMain = I18n.format(getUnlocalizedName(stack) + IDLNBTDef.TOOLTIP_HIDDEN);
////
////    	}
////    	else
//    	{
//    		strMain = super.getItemStackDisplayName(stack);
//    	}

        String strMain = super.getItemStackDisplayName(stack);
        int lv = IDLSkillNBT.getLevel(stack);
        String strMaxLv = lv == maxLevel ? I18n.format("idlframewok.skill.shared.lv_max") : "";

        return I18n.format("idlframewok.skill.shared.name_format_with_lv",strMain, IDLSkillNBT.getLevel(stack), strMaxLv);
    }

    public String GetDuraDescString(float val)
    {
        return I18n.format("idlframewok.skill.shared.duration_desc", val);
    }

    public void trySayDialogue(EntityLivingBase livingBase, ItemStack stack)
    {
        if (maxDialogues > 0)
        {
            int index = livingBase.getRNG().nextInt(maxDialogues);
            if (livingBase instanceof EntityPlayer)
            {
                CommonFunctions.SafeSendMsgToPlayer((EntityPlayer) livingBase, getSkillCastKey(stack, index));
            }
        }
    }
}
