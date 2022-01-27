package com.somebody.idlframewok.item;

import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.util.IDLSkillNBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonDef.G_SKY;
import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class ItemAdaptingBase extends ItemBase {
    public ItemAdaptingBase(String name) {
        super(name);
    }

    public float cool_down = 1f;
    public float cool_down_reduce_per_lv = 0.2f;

    public float base_range = 5f;
    public float range_per_level = 0f;

    public float basic_val = 0f;
    public float val_per_level = 0f;

    public float duration_val = 0f;
    public float duration_per_level = 0f;

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

    public boolean useXP_level = false;
    public int[] levelup_need_xp = new int[]
            {
                    1000,
                    1325,
                    1700,
                    2150,
                    2625,
                    3150,
                    3725,
                    4350,
                    5000,
                    5700,
                    6450,
                    7225,
                    8050,
                    8925,
                    9825,
                    10750,
                    11725,
                    12725,
                    13775,
            };

    public int getCoolDownTicks(ItemStack stack)
    {
        return (int) (getCoolDown(stack) * TICK_PER_SECOND);
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }


    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (!useable)
        {
            return;
        }

        onCreatureStoppedUsing(stack, worldIn, entityLiving, timeLeft);
        entityLiving.swingArm(entityLiving.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);

        if (!worldIn.isRemote)
        {
            if (entityLiving instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityLiving;
                {
                    entityplayer.getCooldownTracker().setCooldown(this, getCoolDownTicks(stack));
                }
            }
        }
    }

    public void onCreatureStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {

    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return super.getRarity(stack);
    }

    public ItemStack getXPUpdateResult(ItemStack old) {
        int curLv = IDLSkillNBT.getLevel(old);
        int curXP = IDLSkillNBT.getXP(old);
        int newLv = curLv;
        while (newLv < levelup_need_xp.length && curXP >= levelup_need_xp[newLv]) {
            curXP -= levelup_need_xp[newLv];
            newLv++;
        }

        ItemStack result = old.copy();
        IDLSkillNBT.SetLevel(old, newLv);
        IDLSkillNBT.setXP(old, curXP);
        return result;
    }

    public ItemAdaptingBase setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    public ItemAdaptingBase setVal(float val, float val_per_level) {
        basic_val = val;
        this.val_per_level = val_per_level;
        return this;
    }

    public ItemAdaptingBase setCD(float val, float val_per_level) {
        cool_down = val;
        this.cool_down_reduce_per_lv = val_per_level;
        return this;
    }

    public ItemAdaptingBase setDura(float val, float val_per_level) {
        duration_val = val;
        this.duration_per_level = val_per_level;
        return this;
    }

    public ItemAdaptingBase setRange(float val, float val_per_level) {
        base_range = val;
        this.range_per_level = val_per_level;
        return this;
    }

    public float getRange(int level) {
        return (level - 1) * range_per_level + base_range;
    }

    public float getDura(int level) {
        return (level - 1) * duration_per_level + duration_val;
    }

    public float getVal(int level) {
        return (level - 1) * val_per_level + basic_val;
    }

    public float getCoolDown(int level) {
        float result = -(level - 1) * cool_down_reduce_per_lv + cool_down;
        return result > 0.1f ? result : 0.1f;
    }

    public float getRange(ItemStack stack) {
        return (IDLSkillNBT.getLevel(stack) - 1) * range_per_level + base_range;
    }

    public float getDura(ItemStack stack) {
        return (IDLSkillNBT.getLevel(stack) - 1) * duration_per_level + duration_val;
    }

    public float getVal(ItemStack stack) {
        return (IDLSkillNBT.getLevel(stack) - 1) * val_per_level + basic_val;
    }

    public float getVal(ItemStack stack, EntityLivingBase caster) {
        return getVal(stack);
    }

    public float getCoolDown(ItemStack stack) {
        float result = -(IDLSkillNBT.getLevel(stack) - 1) * cool_down_reduce_per_lv + cool_down;
        return result > 0.1f ? result : 0.1f;
    }

    //leveling-------------------------------------

    public int GetLevelMax(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemSkillBase)) {
            return 0;
        }
        return maxLevel;
    }


    public static void activateCoolDown(EntityPlayer player, ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof ItemSkillBase) {
            player.getCooldownTracker().setCooldown(stack.getItem(), ((ItemSkillBase) item).getCoolDownTicks(stack));

        }
    }
}
