package com.deeplake.idealland.item.misc.armor.masks;

import com.deeplake.idealland.item.ItemArmorBase;
import com.deeplake.idealland.potion.ModPotions;
import com.deeplake.idealland.util.Reference;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;

//player: +50% crit chance, +25% crit damage
//other: crit chance +100% when sneaking, +8 follow range
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemHelmSniper extends ItemArmorBase {
    private float sightBonusForAI = 8f;
    public ItemHelmSniper(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        shiftToShowDesc = true;
    }

    public static boolean checkCertainCritical(LivingHurtEvent evt) {
        DamageSource source = evt.getSource();
        if (source.isProjectile())
        {
            Entity sourceCreature = source.getTrueSource();
            if (sourceCreature instanceof EntityLivingBase)
            {
                Item headItem = ((EntityLivingBase) sourceCreature).getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
                if (headItem instanceof ItemHelmSniper)
                {
                    return sourceCreature.isSneaking();
                }
            }
        }
        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote)
        {
            EntityLivingBase living = (EntityLivingBase) entityIn;
            if (living.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemHelmSniper)
            {
                boolean sneaking = entityIn.isSneaking();
                if (sneaking)
                {
                    living.addPotionEffect(new PotionEffect(ModPotions.CRIT_CHANCE_PLUS, TICK_PER_SECOND,  1));
                    living.addPotionEffect(new PotionEffect(ModPotions.CRIT_DMG_PLUS, TICK_PER_SECOND, 0));
                }
            }
        }
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
