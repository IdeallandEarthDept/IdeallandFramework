package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.MOR_INTEREST;

public class ItemSkillMorNoticeMeter extends ItemSkillBase {
    public ItemSkillMorNoticeMeter(String name) {
        super(name);
        maxLevel = 3;
        showDamageDesc = false;
        setCD(60,25);
    }

    public int GetError(ItemStack stack)
    {
        int level = IDLSkillNBT.getLevel(stack);
        return (maxLevel - level) * 3;
    }



    public int Distort(int val, int error)
    {
        int errorRaw = error;
        Random random = new Random();
        int result = -1;
        int period = ModConfig.SPAWN_CONF.INVASION_PERIOD;
        do {
            result = val + error - random.nextInt( 2 * error + 1);
        }
        while (result / period != val / period || result < 0);
        return result;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (isStackReady(playerIn, stack))
        {
            if (!worldIn.isRemote)
            {
                int period = ModConfig.SPAWN_CONF.INVASION_PERIOD;
                int readVal = Distort(IDLNBTUtil.getPlayerIdeallandIntSafe(playerIn, MOR_INTEREST), GetError(stack));
                Idealland.Log(String.format("true val = %d", IDLNBTUtil.getPlayerIdeallandIntSafe(playerIn, MOR_INTEREST)));
                CommonFunctions.SendMsgToPlayer((EntityPlayerMP) playerIn, "idlframewok.msg.notice_meter_report",
                        readVal, readVal % period, period, GetError(stack));

                worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_NOTE_HARP, SoundCategory.PLAYERS, 1f, 3f);
                activateCoolDown(playerIn, stack);
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }


    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
       if (ModConfig.SPAWN_CONF.SPAWN_TAINTER_REQ_BUFF)
       {
           super.addInformation(stack, world, tooltip, flag);

       } else
       {
           String mainDesc = I18n.format(stack.getUnlocalizedName() + ".desc_2");
           tooltip.add(mainDesc);
       }

       tooltip.add(I18n.format("idlframewok.skill.shared.error_desc", GetError(stack)));
    }
}
