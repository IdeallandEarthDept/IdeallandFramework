package com.somebody.idlframewok.item.misc.armor;

import com.somebody.idlframewok.item.IGuaEnhance;
import com.somebody.idlframewok.item.ItemArmorBase;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.IDLSkillNBT.GetGuaEnhance;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.MODE;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemArmorXieGeta extends ItemArmorBase implements IGuaEnhance {

    static final int UP_HILL = 0;
    static final int FLAT_WALK = 1;
    static final int DOWN_HILL = 2;
    static final int MAX_MODE = 2;

    public ItemArmorXieGeta(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        showGuaSocketDesc = true;
        ignoreVanillaSystem = true;
    }

    @Override
    public void onUpdateWearing(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdateWearing(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote)
        {
            EntityLivingBase living = (EntityLivingBase) entityIn;

            int buffPower = getBuffPower(stack);
            switch ((IDLNBTUtil.GetInt(stack, MODE)))
            {
                case UP_HILL:
                    living.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, TICK_PER_SECOND, buffPower));
                    break;
                case FLAT_WALK:
                    living.addPotionEffect(new PotionEffect(MobEffects.SPEED, TICK_PER_SECOND, buffPower));
                    break;
                default:
                    living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, TICK_PER_SECOND, 0));
                    break;
            }
        }
    }

    public int getBuffPower(ItemStack stack)
    {
        return IDLSkillNBT.GetGuaEnhance(stack, CommonDef.G_EARTH) / 3;
    }

    static float basicReduction = 2f;
    public static float GetDamageReductionFall(ItemStack stack)
    {
        if (IDLNBTUtil.GetInt(stack, MODE) != DOWN_HILL)
        {
            return 0;
        }
        else {
            return IDLSkillNBT.GetGuaEnhance(stack, CommonDef.G_EARTH) + basicReduction;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        if (evt.isCanceled() || evt.getSource() != DamageSource.FALL) {
            //IdlFramework.Log("Xie Geta wrong type");
            return;
        }

        EntityLivingBase hurtOne = evt.getEntityLiving();
        ItemStack onFoot = hurtOne.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        if (onFoot.getItem() instanceof ItemArmorXieGeta)
        {
            float reduction = ItemArmorXieGeta.GetDamageReductionFall(onFoot);
            //IdlFramework.Log("Xie Geta dmg reduction:%s", reduction);
            if (evt.getAmount() <= reduction)
            {
                evt.setCanceled(true);
            }
            else {
                evt.setAmount(evt.getAmount() - reduction);
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand handIn, EnumFacing facing, float hitX, float hitY, float hitZ) {

        ItemStack stack = playerIn.getHeldItem(handIn);
        int mode = IDLNBTUtil.GetInt(stack, MODE);
        if (!worldIn.isRemote)
        {
            if (mode == MAX_MODE)
            {
                IDLNBTUtil.SetInt(stack, MODE, 0);
            }
            else
            {
                IDLNBTUtil.SetInt(stack, MODE, mode + 1);
            }
        }
        else {
            switch (mode)
            {
                case UP_HILL:
                    playerIn.playSound(SoundEvents.BLOCK_WOOD_BREAK, 1f, 1f);
                    break;

                case FLAT_WALK:
                    playerIn.playSound(SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, 1f, 1f);
                    break;

                case DOWN_HILL:
                    playerIn.playSound(SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, 1f, 1f);
                    break;

                default:
                    break;
            }

        }

        return super.onItemUse(playerIn, worldIn, pos, handIn, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean acceptGuaIndex(int index) {
        return index == 0 || index == 7;
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        String key = getNameKey(stack, world, tooltip, flag);
        if (I18n.hasKey(key))
        {
            float reduction = ItemArmorXieGeta.GetDamageReductionFall(stack);
            int buffPower = getBuffPower(stack);

            int mode = IDLNBTUtil.GetInt(stack, MODE);
            if (mode == DOWN_HILL)
            {
                return I18n.format(key,  reduction);
            }else
            {
                return I18n.format(key,  buffPower + 1);
            }

        }
        return "";
    }

    @SideOnly(Side.CLIENT)
    public String getNameKey(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        int mode = IDLNBTUtil.GetInt(stack, MODE);
        return stack.getUnlocalizedName() + ".desc." + String.valueOf(mode);
    }
}
