package com.deeplake.idealland.item.skills;

import com.deeplake.idealland.init.ModCreativeTab;
import com.deeplake.idealland.item.ItemBase;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
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
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.deeplake.idealland.util.CommonDef.G_SKY;
import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.idealland.util.CommonFunctions.SendMsgToPlayer;

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

    public float level_modifier = 0f;

    public int maxLevel = 5;

    public static final String SUCCESS_DESC_KEY = ".on_sucess";
    public static final String IN_CD_DESC_KEY = ".on_cooldown";

    public int gua_index = G_SKY;

    public boolean showCDDesc = true;
    public boolean showDamageDesc = true;
    public boolean showRangeDesc = false;

    protected int GetMaxTick(ItemStack stack) {
        return (int) (getCoolDown(stack) * TICK_PER_SECOND);
    }

    public ItemSkillBase(String name) {
        super(name);
        setMaxStackSize(1);
        setNoRepair();
        setCreativeTab(ModCreativeTab.IDL_SKILL);
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

    public ItemSkillBase setRange(float val, float val_per_level)
    {
        base_range = val;
        this.range_per_level = val_per_level;
        return this;
    }

    public float getRange(ItemStack stack)
    {
        return (getLevel(stack) - 1) * range_per_level + base_range;
    }

    public float getVal(ItemStack stack)
    {
        return  (getLevel(stack) - 1) * val_per_level + basic_val;
    }
    public float getCoolDown(ItemStack stack) {
        float result = -(getLevel(stack) - 1) * cool_down_reduce_per_lv + cool_down;
        return result > 0.1f ? result : 0.1f; }

    //leveling-------------------------------------

    public int GetLevelMax(ItemStack stack)
    {
        if (!(stack.getItem() instanceof ItemSkillBase)) {
            return 0;
        }
        return maxLevel;
    }

    public int getLevel(ItemStack stack)
    {
        int level = IDLNBTUtil.GetInt(stack, IDLNBTDef.LEVEL_TAG);
        return level == 0 ? 1 : level;
    }

    public void SetLevel(ItemStack stack, int count)
    {
        if (!(stack.getItem() instanceof ItemSkillBase)) {
            return;
        }

        if (count <= GetLevelMax(stack)) {
            IDLNBTUtil.SetInt(stack, IDLNBTDef.LEVEL_TAG, count);
        }
    }

    public void AddLevelByCount(ItemStack stack, int count)
    {
        if (!(stack.getItem() instanceof ItemSkillBase)) {
            return;
        }

        int anticipatedCount = count + getLevel(stack);
        if (anticipatedCount <= GetLevelMax(stack)) {
            IDLNBTUtil.SetInt(stack, IDLNBTDef.LEVEL_TAG, anticipatedCount);
        }
        else {
            IDLNBTUtil.SetInt(stack, IDLNBTDef.LEVEL_TAG, GetLevelMax(stack));
        }
    }
    
    public void SendDefaultMsg(EntityPlayer player, ItemStack stack, SKILL_MSG_TYPE msg_type)
    {
        switch (msg_type)
        {
            case CD:
                SendMsgToPlayer((EntityPlayerMP)player, stack.getUnlocalizedName()+ IN_CD_DESC_KEY);
            case SUCCESS:
                SendMsgToPlayer((EntityPlayerMP)player, stack.getUnlocalizedName()+ SUCCESS_DESC_KEY);
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
        CommonFunctions.SendMsgToPlayer(player, "idealland.skill.msg.cool_down");
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
        stack.setItemDamage(stack.getItemDamage() - 1);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
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
                tooltip.add(I18n.format("idealland.skill.shared.cool_down_desc", getCoolDown(stack)));
            if (showDamageDesc && getVal(stack) > 0)
                tooltip.add(I18n.format("idealland.skill.shared.power_desc", getVal(stack)));
            if (showRangeDesc)
                tooltip.add(I18n.format("idealland.skill.shared.range_desc", getRange(stack)));
            if (maxLevel != 1)
            {
                tooltip.add(I18n.format("idealland.skill.shared.level_desc", getLevel(stack), maxLevel));
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
        int lv = getLevel(stack);
        String strMaxLv = lv == maxLevel ? I18n.format("idealland.skill.shared.lv_max") : "";

        return I18n.format("idealland.skill.shared.name_format_with_lv",strMain, getLevel(stack), strMaxLv);
    }

    public String GetDuraDescString(float val)
    {
        return I18n.format("idealland.skill.shared.duration_desc", val);
    }

}
