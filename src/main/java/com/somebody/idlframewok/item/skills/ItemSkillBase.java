package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.IdlFramework;
import com.somebody.idlframewok.init.ModCreativeTab;
import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.MessageDef;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.G_SKY;
import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.CommonFunctions.SendMsgToPlayer;
import static com.somebody.idlframewok.util.CommonFunctions.isShiftPressed;
import static com.somebody.idlframewok.util.MessageDef.getSkillCastKey;

enum SKILL_MSG_TYPE
{
    SUCCESS,
    CD,
}


public class ItemSkillBase extends ItemBase {
    public boolean isMartial = false;

    public float cool_down = 1f;
    public float cool_down_reduce_per_lv = 0.2f;

    public float base_range = 5f;
    public float range_per_level = 0f;

    public float basic_val = 0f;
    public float val_per_level = 0f;

    public float dura_val = 0f;
    public float dura_per_level = 0f;

    public float level_modifier = 0f;

    public int maxLevel = 5;

    public int gua_index = G_SKY;

    public boolean showCDDesc = true;
    public boolean showDamageDesc = true;
    public boolean showRangeDesc = false;
    public boolean showDuraDesc = false;

    //for arknights
    public boolean offHandCast = false;
    public boolean mainHandCast = false;
    public boolean cannotMouseCast = false;

    protected int maxDialogues = 0;

    protected int GetMaxTick(ItemStack stack) {
        return (int) (getCoolDown(stack) * TICK_PER_SECOND);
    }

    public ItemSkillBase(String name) {
        super(name);
        setMaxStackSize(1);
        setNoRepair();
        setCreativeTab(ModCreativeTab.IDL_MISC);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return super.getRarity(stack);
    }

    public ItemSkillBase setMaxLevel(int maxLevel)
    {
        this.maxLevel = maxLevel;
        return this;
    }

    public ItemSkillBase setVal(float val, float val_per_level)
    {
        basic_val = val;
        this.val_per_level = val_per_level;
        return this;
    }

    public ItemSkillBase setCD(float val, float val_per_level)
    {
        cool_down = val;
        this.cool_down_reduce_per_lv = val_per_level;
        return this;
    }

    public ItemSkillBase setDura(float val, float val_per_level)
    {
        dura_val = val;
        this.dura_per_level = val_per_level;
        return this;
    }

    public ItemSkillBase setRange(float val, float val_per_level)
    {
        base_range = val;
        this.range_per_level = val_per_level;
        return this;
    }

    public float getRange(ItemStack stack)
    {
        return (IDLSkillNBT.getLevel(stack) - 1) * range_per_level + base_range;
    }

    public float getDura(ItemStack stack)
    {
        return  (IDLSkillNBT.getLevel(stack) - 1) * dura_per_level + dura_val;
    }
    public float getVal(ItemStack stack)
    {
        return  (IDLSkillNBT.getLevel(stack) - 1) * val_per_level + basic_val;
    }
    public float getCoolDown(ItemStack stack) {
        float result = -(IDLSkillNBT.getLevel(stack) - 1) * cool_down_reduce_per_lv + cool_down;
        return result > 0.1f ? result : 0.1f; }

    //leveling-------------------------------------

    public int GetLevelMax(ItemStack stack)
    {
        if (!(stack.getItem() instanceof ItemSkillBase)) {
            return 0;
        }
        return maxLevel;
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

    public static void activateCoolDown(EntityPlayer player, ItemStack stack)
    {
        Item item = stack.getItem();
        if (item instanceof ItemSkillBase)
        {
            player.getCooldownTracker().setCooldown(stack.getItem(), ((ItemSkillBase) item).GetMaxTick(stack));
        }
    }

    public static void notifyCoolingDown(EntityPlayerMP player)
    {
        CommonFunctions.SendMsgToPlayer(player, "idlframewok.skill.msg.cool_down");
    }

    public static boolean isStackReady(EntityPlayer player, ItemStack stack)
    {
        return !player.getCooldownTracker().hasCooldown(stack.getItem());
        //return stack.getItemDamage() == 0;
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
            if (canCast(worldIn, playerIn, handIn))
            {
                tryCast(worldIn, playerIn, handIn);
                activateCoolDown(playerIn, stack);
                return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
            }
            else {
                return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));

            }
        }

        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }

    public boolean canCast(World worldIn, EntityLivingBase livingBase, EnumHand handIn)
    {
        if (livingBase instanceof EntityPlayer)
        {
            isStackReady((EntityPlayer) livingBase, livingBase.getHeldItem(handIn));
        }
        return true;
    }

    public boolean tryCast(World worldIn, EntityLivingBase livingBase, EnumHand handIn)
    {
        if (livingBase instanceof EntityPlayer)
        {
            activateCoolDown((EntityPlayer) livingBase, livingBase.getHeldItem(handIn));
        }
        return true;
    }

    public boolean onKeyboardCast(EntityLivingBase caster, ItemStack stack, EnumHand hand)
    {
        World world = caster.world;
        boolean casterIsPlayer = caster instanceof EntityPlayer;
        if (!casterIsPlayer || isStackReady((EntityPlayer) caster, stack)) {
            if (canCast(world, caster, hand)) {
                if (hand == EnumHand.MAIN_HAND) {
                    if (mainHandCast) {
                        return tryCast(caster.world, caster, hand);
                    } else  {
                        if (casterIsPlayer)
                        {
                            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, (EntityPlayer) caster, MessageDef.NOT_CASTABLE_MAINHAND);
                        }
                        else {
                            IdlFramework.LogWarning("Trying to do invalid cast from a creature: %s", caster.getName());
                        }
                    }
                } else if (hand == EnumHand.OFF_HAND)
                    if (offHandCast) {
                        return tryCast(caster.world, caster, hand);
                    }else {
                        if (casterIsPlayer)
                        {
                            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, (EntityPlayer) caster, MessageDef.NOT_CASTABLE_OFFHAND);
                        }
                        else {
                            IdlFramework.LogWarning("Trying to do invalid cast from a creature: %s", caster.getName());
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
