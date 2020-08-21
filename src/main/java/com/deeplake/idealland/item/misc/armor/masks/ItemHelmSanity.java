package com.deeplake.idealland.item.misc.armor.masks;

import com.deeplake.idealland.enchantments.ModEnchantmentInit;
import com.deeplake.idealland.item.ItemArmorBase;
import com.deeplake.idealland.potion.ModPotions;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import com.deeplake.idealland.util.Reference;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemHelmSanity extends ItemArmorBase {
    private float sightBonusForAI = -2f;
    public ItemHelmSanity(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        shiftToShowDesc = true;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType)
        {
            multimap.put(SharedMonsterAttributes.FOLLOW_RANGE.getName(), new AttributeModifier("Sight Modifier", (double)sightBonusForAI, 0));
        }

        return multimap;
    }
}
