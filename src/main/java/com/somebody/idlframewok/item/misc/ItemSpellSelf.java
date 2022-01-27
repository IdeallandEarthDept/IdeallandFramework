package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.SPELL_KEY;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.TOOLTIP_HIDDEN;

public class ItemSpellSelf extends ItemSkillBase {

    Random random = new Random();

    public final int total = 29;

    float buffDura = 66f;//seconds

    public ItemSpellSelf(String name) {
        super(name);
        maxLevel = 1;
        offHandCast = true;
        cannotMouseCast = true;
        setCD(60f, 0f);
        CommonFunctions.addToEventBus(this);
    }

    public int getMark(ItemStack stack, int index)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SPELL_KEY);
        stringBuilder.append(index);
        return IDLNBTUtil.GetInt(stack, stringBuilder.toString());
    }

    public void randomActivate(ItemStack stack, int times)
    {
        for (int i = 1; i <= times; i++)
        {
            setMark(stack, random.nextInt(total), 1);
        }
    }

    public void setMark(ItemStack stack, int index, int value)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SPELL_KEY);
        stringBuilder.append(index);
        Idealland.Log("Set %d to %d", index, value);
        IDLNBTUtil.setInt(stack, stringBuilder.toString(), value);
    }

    public int getNextMark(ItemStack stack)
    {
        return getMark(stack, getCounter());
    }

    int counter = 0;

    int getCounter()
    {
        counter++;
        return counter-1;
    }

    void resetCounter()
    {
        counter = 0;
    }

    void tryApplyBuff(Potion effect, EntityLivingBase livingBase, ItemStack stack)
    {
        if (getNextMark(stack) > 0)
        {
            EntityUtil.ApplyBuff(livingBase, effect, 0, buffDura);
        }
    }

    void tryApplyQuadraBuff(Potion effect, EntityLivingBase livingBase, ItemStack stack)
    {
        int level = getNextMark(stack) + getNextMark(stack) + getNextMark(stack) + getNextMark(stack);
        if (level > 0)
        {
            EntityUtil.ApplyBuff(livingBase, effect, level - 1, buffDura);
        }
    }

    @Override
    public boolean applyCast(World worldIn, EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot) {
        if (livingBase.world.isRemote)
        {
            return true;
        }

        activateSpell(livingBase, stack);
        if (livingBase instanceof EntityPlayer)
        {
            activateCoolDown((EntityPlayer) livingBase, stack);
        }
        return true;
        //return super.applyCast(worldIn, livingBase, handIn);
    }

    public void activateSpell(EntityLivingBase livingBase, ItemStack stack)
    {
        resetCounter();
        tryApplyQuadraBuff(MobEffects.HASTE, livingBase, stack);
        tryApplyQuadraBuff(MobEffects.SPEED, livingBase, stack);
        tryApplyQuadraBuff(MobEffects.RESISTANCE, livingBase, stack);
        tryApplyQuadraBuff(MobEffects.STRENGTH, livingBase, stack);
        tryApplyQuadraBuff(MobEffects.REGENERATION, livingBase, stack);
        tryApplyQuadraBuff(MobEffects.JUMP_BOOST, livingBase, stack);
        tryApplyBuff(MobEffects.NIGHT_VISION,livingBase, stack);
        tryApplyBuff(MobEffects.INVISIBILITY,livingBase, stack);
        tryApplyBuff(MobEffects.FIRE_RESISTANCE,livingBase, stack);
        tryApplyBuff(MobEffects.WATER_BREATHING,livingBase, stack);
        if (getNextMark(stack) > 0) {
            EntityUtil.TryRemoveDebuff(livingBase);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {

        StringBuilder stringBuilderMain = new StringBuilder();
        String regName = getUnlocalizedName();
        for (int i = 0; i < total; i++)
        {
            StringBuilder stringBuilderKey = new StringBuilder();

            int counter = i;
            stringBuilderKey.append(regName).append(".").append(counter);
            if (getMark(stack, i) <= 0)
            {
                stringBuilderKey.append(TOOLTIP_HIDDEN);
            }
            stringBuilderMain.append(I18n.format(stringBuilderKey.toString()));
        }
        stringBuilderMain.append(super.getMainDesc(stack, world, tooltip, flag));
        return stringBuilderMain.toString();
    }
}
