package com.somebody.idlframewok.item.goblet;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.IDLNBT.getTagSafe;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.*;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.P2W_EXP;

public class ItemGobletBase extends ItemBase {

    public float cool_down = 1f;
    public float cool_down_reduce_per_lv = 0f;

    public ItemGobletBase(String name) {
        super(name);
    }

    public void SetPayingEXP(ItemStack stack, int val)
    {
        IDLNBTUtil.SetInt(stack, P2W_PAYING_EXP, val);
    }

    public int GetPayingEXP(ItemStack stack)
    {
        return IDLNBTUtil.GetInt(stack, P2W_PAYING_EXP);
    }

    public void SetCacheEXP(ItemStack stack, int val)
    {
        IDLNBTUtil.SetInt(stack, P2W_CACHE_EXP, val);
    }

    public static int GetCacheEXP(ItemStack stack)
    {
        return IDLNBTUtil.GetInt(stack, P2W_CACHE_EXP);
    }

    public static int GetP2WExpForPlayer(EntityPlayer player)
    {
        NBTTagCompound playerData = player.getEntityData();
        NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);

        return data.getInteger(P2W_EXP);
    }

    public static void SetP2WExpForPlayer(EntityPlayer player, int val)
    {
        NBTTagCompound playerData = player.getEntityData();
        NBTTagCompound data = getTagSafe(playerData, EntityPlayer.PERSISTED_NBT_TAG);

        data.setInteger(P2W_EXP, val);
        playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
    }

    public static int GetLevelFromEXP(int xp)
    {
        return (int)Math.log(xp+1);
    }

    public static int GetExpForLevelFromEXP(int xp)
    {
        return (int)Math.pow(Math.exp(1), (int)Math.log(xp+1)+1);
    }
    public static int GetLevelFromItemStack(ItemStack stack)
    {
        return GetCacheEXP(stack);
    }

    public static void activateCoolDown(EntityPlayer player, ItemStack stack)
    {
        Item item = stack.getItem();
        if (item instanceof ItemGobletBase)
        {
            player.getCooldownTracker().setCooldown(stack.getItem(), ((ItemGobletBase) item).GetMaxTick(stack));
        }
    }

    private int GetMaxTick(ItemStack stack) {
        return (int) (getCoolDown(stack) * TICK_PER_SECOND);
    }

    public float getCoolDown(ItemStack stack) {
        float result = -(GetLevelFromItemStack(stack) - 1) * cool_down_reduce_per_lv + cool_down;
        return result > 0.1f ? result : 0.1f; }


    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote)
        {
            EntityPlayer player = (EntityPlayer) entityIn;
            int playerXP = GetP2WExpForPlayer((EntityPlayer) entityIn);
            int payingXP = GetPayingEXP(stack);
            if (payingXP > 0)
            {
                //first, crafting charges the goblet
                //when the player holds the goblet, the player absorbs the experience.
                //todo; make the player drink from it to get the experience
                playerXP += payingXP;
                SetP2WExpForPlayer(player, playerXP);
                SetPayingEXP(stack, 0);
            }

            int lastCache = GetCacheEXP(stack);
            if (lastCache != playerXP)
            {
                SetCacheEXP(stack, playerXP);
                //stack.writeToNBT()
            }
            //IdlFramework.Log(playerXP + " " + getUnlocalizedName(stack));
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformationLast(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        int val = GetPayingEXP(stack);
        if (val > 0)
            tooltip.add(I18n.format("item.p2w_goblet.contain.desc", val));

        val = GetCacheEXP(stack);

        if (GetCacheEXP(stack) > 0)
            tooltip.add(I18n.format("item.p2w_goblet.player.desc", GetLevelFromEXP(val), val, GetExpForLevelFromEXP(val)));
    }

    //Desc
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        String mainDesc = I18n.format(stack.getUnlocalizedName() + ".desc");
        tooltip.add(mainDesc);
        addInformationLast(stack, world, tooltip, flag);
    }
}
