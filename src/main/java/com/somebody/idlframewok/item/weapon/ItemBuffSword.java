package com.somebody.idlframewok.item.weapon;

import com.somebody.idlframewok.item.ItemSwordBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collection;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.STATE;

public class ItemBuffSword extends ItemSwordBase {
    static int TICK_PER_MIN = 60 * TICK_PER_SECOND;

    public ItemBuffSword(String name, ToolMaterial material) {
        super(name, material);
        this.addPropertyOverride(new ResourceLocation("pic"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                int state = IDLNBTUtil.GetInt(stack, STATE);
                if (state == 0) {
                    return 0;
                } else if (state <= 4) {
                    return 1;
                } else {
                    return 2;
                }
            }
        });
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 1024;
    }

    public float getDamagePerEnch(ItemStack stack)
    {
        return 1f;
    }

    int getPotionTotalLevel(EntityLivingBase player)
    {
        int count = 0;
        Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();
        for (PotionEffect effect: activePotionEffects) {
            //todo: cap it
            int length = (int) Math.ceil((float)effect.getDuration() / TICK_PER_MIN / 2f);
            if (length > 100)
            {
                length = 100;
            }
            count += (effect.getAmplifier() + 1) * length;
        }
        return count;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase livingBase = (EntityLivingBase) entityIn;
            int curState = IDLNBTUtil.GetInt(stack, STATE, 0);
            int curBuff = getPotionTotalLevel(livingBase);

            if (curBuff != curState)
            {
                IDLNBTUtil.setInt(stack, STATE, curBuff);
            }

            PotionEffect effect = livingBase.getActivePotionEffect(MobEffects.REGENERATION);
            if (effect != null)
            {
                CommonFunctions.repairItem(stack, effect.getAmplifier() + 1);
            }
        }
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            int curState = IDLNBTUtil.GetInt(stack, STATE, 0);
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 1f + curState * getDamagePerEnch(stack), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }

}
