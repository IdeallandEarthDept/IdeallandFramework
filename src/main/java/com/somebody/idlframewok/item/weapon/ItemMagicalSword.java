package com.somebody.idlframewok.item.weapon;

import com.somebody.idlframewok.item.ItemSwordBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.STATE;

public class ItemMagicalSword extends ItemSwordBase {
    public ItemMagicalSword(String name, ToolMaterial material) {
        super(name, material);
        CommonFunctions.addToEventBus(this);
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event)
    {
        DamageSource source = event.getSource();
        if (source.getTrueSource() instanceof EntityLivingBase)
        {
            if (((EntityLivingBase) source.getTrueSource()).getHeldItemMainhand().getItem() == this)
            {
                source.setMagicDamage();
            }
        }
    }

    public float getAtkFromState(int state)
    {
        return getBaseAttackDamage() + state;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            int state = IDLNBTUtil.GetInt(stack, STATE);
            if (state > 0)
            {
                Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getAtkFromState(IDLNBTUtil.GetInt(stack, STATE)), 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
                return multimap;
            }

        }
        return super.getAttributeModifiers(equipmentSlot, stack);

    }
}
