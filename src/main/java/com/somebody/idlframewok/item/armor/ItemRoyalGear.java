package com.somebody.idlframewok.item.armor;

import com.somebody.idlframewok.item.ItemArmorBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.STATE;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.STATE_2;

public class ItemRoyalGear extends ItemArmorBase {
    public ItemRoyalGear(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        CommonFunctions.addToEventBus(this);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && entityIn instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityIn;
            int state = IDLNBTUtil.GetInt(stack, STATE, 0);
            int hunger = player.getFoodStats().getFoodLevel();
            if (state != hunger){
                state = hunger;
                IDLNBTUtil.setInt(stack, STATE, state);
            }

            int state2 = IDLNBTUtil.GetInt(stack, STATE_2, 0);
            float saturation = player.getFoodStats().getSaturationLevel();
            if (state2 != saturation){
                state2 = (int) saturation;
                IDLNBTUtil.setInt(stack, STATE_2, state2);
            }

            if (saturation > 0)
            {
                CommonFunctions.repairItem(stack, 1);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    void onHit(LivingHurtEvent evt)
    {
        //attack or attacked with this gear will speed up exhaustion.
        if (!evt.isCanceled() && !evt.getEntity().world.isRemote)
        {
            EntityLivingBase damaged = evt.getEntityLiving();
            if (damaged instanceof EntityPlayer && damaged.getItemStackFromSlot(armorType).getItem() == this)
            {
                EntityPlayer player = (EntityPlayer) damaged;
                player.getFoodStats().addExhaustion(evt.getAmount());
            }

            if (evt.getSource().getTrueSource() instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) evt.getSource().getTrueSource();
                if (player.getItemStackFromSlot(armorType).getItem() == this)
                {
                    player.getFoodStats().addExhaustion(evt.getAmount());
                    EntityUtil.ApplyBuff(player, MobEffects.HUNGER, 0, 60);
                }
            }
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (equipmentSlot == this.armorType)
        {
            int state  = IDLNBTUtil.GetInt(stack, STATE, 0);
            int state2 = IDLNBTUtil.GetInt(stack, STATE_2, 0);

            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Armor modifier", (double)state, 0));
            multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Armor toughness", (double)state2, 0));

            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Armor toughness", (double)(state2 + state), 0));
//            multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()],"Health modifier", (double)2f + IDLSkillNBT.GetGuaEnhance(stack, 0), 0));
//            multimap.put(SharedMonsterAttributes.LUCK.getName(), new AttributeModifier(ARMOR_MODIFIERS_OVERRIDE[equipmentSlot.getIndex()], "Luck", (double)1f, 0));
        }

        return multimap;
    }
}
