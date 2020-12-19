package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.meta.MetaUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSkillModListStrike extends ItemSkillBase {
    public ItemSkillModListStrike(String name) {
        super(name);
        setCD(1f,0f);
        showDamageDesc = false;
        showCDDesc = true;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand handIn) {
        World world = playerIn.world;
        if (!world.isRemote)
        {
            DamageSource damageSource = DamageSource.causePlayerDamage(playerIn).setMagicDamage();

            damageSource.setDamageBypassesArmor();

            target.attackEntityFrom(damageSource, MetaUtil.GetModCount());
        }
        playerIn.swingArm(handIn);
        activateCoolDown(playerIn, stack);
        target.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1f, 1f);
        return true;
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        String key = stack.getUnlocalizedName() + ".desc";
        if (I18n.hasKey(key))
        {
            String mainDesc = I18n.format(key, Loader.instance().getActiveModList().size());
            return mainDesc;
        }
        return "";
    }
}
