package com.deeplake.idealland.item.edicts;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.item.ItemBase;
import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.deeplake.idealland.util.Reference.MOD_ID;

public class ItemEdictBase extends ItemBase {

//    public ItemEdictBase()
//    {
//        super();
//    }

    public ItemEdictBase(String name)
    {
        super(name);
        setRarity(EnumRarity.EPIC);
    }

    public static int useSecond = 4;

    @Override
    public void InitItem()
    {
        setMaxStackSize(1);
    }
    public void OnCastSuccess(ItemStack stack, World world, EntityLivingBase living, int time) {
        if (!world.isRemote) {
            CommonFunctions.BroadCastByKey(IDLNBTDef.EDICT_COMMON_END);
            stack.shrink(1);
            CommonFunctions.LogPlayerAction(living, "used edict:" + getUnlocalizedName(stack));
        }
        if (world.isRemote) {
            living.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER,10f, 1f);
        }
    }

    public void OnCastFail(ItemStack stack, World world, EntityLivingBase living, int time) {
        if (!world.isRemote) {
            CommonFunctions.BroadCastByKey(IDLNBTDef.EDICT_COMMON_FAIL);
            stack.shrink(1);
        }
    }

    @Override
    public void serverUseTick(ItemStack stack, EntityLivingBase living, int count) {
        super.serverUseTick(stack, living, count);
        if (count == 1) {
            CommonFunctions.BroadCastByKey(IDLNBTDef.EDICT_COMMON_START);
            CommonFunctions.BroadCastByKey(getUnlocalizedName() + IDLNBTDef.EDICT_START);
        }

        if (count == getUseTick()) {
            CommonFunctions.BroadCastByKey(getUnlocalizedName() + IDLNBTDef.EDICT_END);
        }
    }

    @Override
    public void clientUseTick(ItemStack stack, EntityLivingBase living, int count) {
        super.clientUseTick(stack, living, count);
        if (count >= getUseTick()) {
            float theta = living.getRNG().nextFloat() * 6.28f;
             living.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE,
                     living.posX + Math.cos(theta), living.getEyeHeight(), living.posZ + Math.sin(theta),
                     0, 1, 0);
        }
    }

    public int getUseTick()
    {
        return CommonFunctions.SecondToTicks(useSecond);
    }
    //---------------------------------------
    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    //Animation
    @Nonnull
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        player.setActiveHand(hand);
        ItemStack stack = player.getHeldItem(hand);
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public int getItemEnchantability()
    {
        return 0;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
        //Divine edicts are holy. They should not be tossed.
        return false;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(GetBasicDesc());
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase living, int time) {
        if (getMaxItemUseDuration(stack) - time >= getUseTick()) {
            OnCastSuccess(stack, world, living, time);
        }
        else
        {
            OnCastFail(stack, world, living, time);
        }

        return;
    }
}
