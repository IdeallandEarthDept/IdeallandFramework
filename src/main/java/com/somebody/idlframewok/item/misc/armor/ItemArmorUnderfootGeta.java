package com.somebody.idlframewok.item.misc.armor;

import com.somebody.idlframewok.item.IGuaEnhance;
import com.somebody.idlframewok.item.ItemArmorBase;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.Reference;
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
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemArmorUnderfootGeta extends ItemArmorBase implements IGuaEnhance {
    public ItemArmorUnderfootGeta(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        showGuaSocketDesc = true;
        ignoreVanillaSystem = true;
    }

    static final int maxEnhance = 5;

    @Override
    public void onUpdateWearing(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdateWearing(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote)
        {
            EntityLivingBase livingBase = (EntityLivingBase) entityIn;

            if (livingBase instanceof EntityPlayer)
            {
                if (((EntityPlayer) livingBase).capabilities.isCreativeMode)
                {
                    //quite annoying in creative
                    return;
                }
            }

            if (worldIn.getWorldTime() % (maxEnhance - IDLSkillNBT.GetGuaEnhance(stack, CommonDef.G_EARTH)) == 0)
            {
                Collection<PotionEffect> activePotionEffects = livingBase.getActivePotionEffects();
                for (int i = 0; i < activePotionEffects.size(); i++) {
                    PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
                    if (buff.getPotion().isBadEffect()){
                        livingBase.removePotionEffect(buff.getPotion());
                        break;
                    }
                }

                for (int i = 0; i < activePotionEffects.size(); i++) {
                    PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
                    if (!buff.getPotion().isBadEffect()){
                        livingBase.removePotionEffect(buff.getPotion());
                        break;
                    }
                }
            }
        }
    }

    static float fireRatioBase = 1.5f;
    static float fireRatioPerEnhance = 0.5f;
    public static float GetDamageMultiplierFire(ItemStack stack)
    {
        return IDLSkillNBT.GetGuaEnhance(stack, CommonDef.G_EARTH) * fireRatioPerEnhance + fireRatioBase;
    }

    public static float GetDebuffPeriod(ItemStack stack)
    {
        return Math.max(1f - (float)IDLSkillNBT.GetGuaEnhance(stack, CommonDef.G_EARTH) / (float)maxEnhance, 1f/TICK_PER_SECOND);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        if (evt.isCanceled() || !evt.getSource().isFireDamage()) {
            return;
        }

        EntityLivingBase hurtOne = evt.getEntityLiving();
        ItemStack onFoot = hurtOne.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        if (onFoot.getItem() instanceof ItemArmorUnderfootGeta)
        {
            float amplifier = ItemArmorUnderfootGeta.GetDamageMultiplierFire(onFoot);
            evt.setAmount(evt.getAmount() * amplifier);
        }
    }

    @Override
    public boolean acceptGuaIndex(int index) {
        return index == 0;
    }

    @SideOnly(Side.CLIENT)
    public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
    {
        String key = getUnlocalizedName() + ".desc";
        if (I18n.hasKey(key))
        {
            float fireAmplify = GetDamageMultiplierFire(stack);
            float buffTime = GetDebuffPeriod(stack);

            return I18n.format(key, buffTime, fireAmplify);
        }
        return "";
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == this.armorType)
        {
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Armor modifier", (double)this.damageReduceAmount, 0));
            multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Armor toughness", (double)this.toughness, 0));
            multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()],"Health modifier", (double)2f + IDLSkillNBT.GetGuaEnhance(stack, 0), 0));
            multimap.put(SharedMonsterAttributes.LUCK.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Luck", (double)1f, 0));
        }

        return multimap;
    }
}
