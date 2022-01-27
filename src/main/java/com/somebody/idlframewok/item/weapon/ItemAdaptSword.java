package com.somebody.idlframewok.item.weapon;

import com.somebody.idlframewok.enchantments.ModEnchantmentInit;
import com.somebody.idlframewok.item.IWIP;
import com.somebody.idlframewok.item.ItemSwordBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.*;

public class ItemAdaptSword extends ItemSwordBase implements IWIP {

    public static final String NOT_MATCH = "not_match";

    public ItemAdaptSword(String name, ToolMaterial material) {
        super(name, material);
        CommonFunctions.addToEventBus(this);
    }

    public float getAtkFromState(int state)
    {
        return 2 + state * 0.1f;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (IDLNBTUtil.GetBoolean(stack, IDLNBTDef.INIT_DONE))
        {
            boolean isLocked = IDLNBTUtil.GetBoolean(stack, NOT_MATCH);
            boolean isWrong = !IDLNBTUtil.GetString(stack, OWNER_NAME, "").equals(entityIn.getName());
            if (isLocked != isWrong) {
                IDLNBTUtil.SetBoolean(stack, NOT_MATCH, isWrong);
            }
        }
    }

    void onCheck(DamageSource source, EntityLivingBase hurtOne, boolean isOnHitCheck)
    {
        if (source.getTrueSource() instanceof EntityLivingBase &&
                ((EntityLivingBase)source.getTrueSource()).getHeldItemMainhand().getItem() == this)
        {
            EntityLivingBase attacker = (EntityLivingBase)source.getTrueSource();
            ItemStack stack = attacker.getHeldItemMainhand();

            if (isOnHitCheck != ModEnchantmentInit.ADAPT_ON_HIT.appliedOnCreature(attacker))
            {
                return;
            }

            if (!IDLNBTUtil.GetBoolean(stack, IDLNBTDef.INIT_DONE))
            {
                IDLNBTUtil.SetBoolean(stack, IDLNBTDef.INIT_DONE, true);
                IDLNBTUtil.SetString(stack, OWNER_NAME, attacker.getName());
            }
            else {
                if (!IDLNBTUtil.GetString(stack, OWNER_NAME, "").equals(attacker.getName())) {
                    //this is not your journey.
                    EntityPlayer player = (EntityPlayer) attacker;
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, player, "idlframewok.msg.adapt_not_yours");
                    return;
                }
            }

            int damageCounter = IDLNBTUtil.GetInt(stack, STATE);
            float damage = getAtkFromState(damageCounter);

            float baseHealthRatio =  isOnHitCheck ? ModEnchantmentInit.ADAPT_ON_HIT.getValue(attacker) : 1f;

            float healthModifier = baseHealthRatio + ModEnchantmentInit.ADAPT_OUTSTAND.getValue(attacker);
            boolean improve = hurtOne.getMaxHealth() * healthModifier > damage;

            int delta = 1 + attacker.getRNG().nextInt((int) (ModEnchantmentInit.ADAPT_FAST.getValue(attacker) + 2));
            if (!improve)
            {
                float protect = ModEnchantmentInit.ADAPT_DONT_DROP.getValue(attacker);
                if (attacker.getRNG().nextFloat() < protect)
                {
                    //prevent falling
                    return;
                }
                else {
                    delta = -delta;
                }
            }

            int lastCounter = damageCounter;
            damageCounter += delta;

            if (damageCounter < 1)
            {
                damageCounter = 1;
            }

            if (attacker instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) attacker;
                float deltaTrue = getAtkFromState(damageCounter)  - getAtkFromState(lastCounter);
                if (deltaTrue > 0)
                {
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GREEN, player, "idlframewok.msg.adapt_plus", String.format("%.2f", deltaTrue));
                }
                else if (deltaTrue < 0) {
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, player, "idlframewok.msg.adapt_minus", String.format("%.2f", -deltaTrue));
                }
            }

            IDLNBTUtil.setInt(stack, STATE, damageCounter);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    void onKill(LivingDeathEvent event)
    {
        if (event.isCanceled() || event.getEntity().world.isRemote) {
            return;
        }

        DamageSource source = event.getSource();
        onCheck(source, event.getEntityLiving(), false);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    void onHit(LivingHurtEvent event)
    {
        if (event.isCanceled() || event.getEntity().world.isRemote) {
            return;
        }

        DamageSource source = event.getSource();
        onCheck(source, event.getEntityLiving(), true);
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        if (IDLNBTUtil.GetBoolean(stack, NOT_MATCH))
        {
            return multimap;
        }

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getAtkFromState(IDLNBTUtil.GetInt(stack, STATE)), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }

    @SideOnly(Side.CLIENT)
    public String descGetKey(ItemStack stack, World world, boolean showFlavor)
    {
        if (showFlavor)
        {
            return stack.getUnlocalizedName() + IDLNBTDef.FLAVOR_KEY;
        }else {
            if (ModEnchantmentInit.ADAPT_ON_HIT.getEnchantmentLevel(stack) > 0)
            {
                return stack.getUnlocalizedName() + IDLNBTDef.DESC_COMMON + ".2";
            }
            else {
                return stack.getUnlocalizedName() + IDLNBTDef.DESC_COMMON;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (IDLNBTUtil.GetBoolean(stack, IDLNBTDef.INIT_DONE))
        {
           tooltip.add(I18n.format(OWNER_DESC, IDLNBTUtil.GetString(stack, OWNER_NAME, "")));
        }
        return super.getMainDesc(stack, world, tooltip, flag);
    }
}
