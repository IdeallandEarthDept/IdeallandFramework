package com.somebody.idlframewok.enchantments;

import com.somebody.idlframewok.IdlFramework;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

@Mod.EventBusSubscriber(modid = IdlFramework.MODID)
public class ModEnchantmentInit {

    public static final EntityEquipmentSlot[] armorSlots = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
    public static final EntityEquipmentSlot[] allSlots = EntityEquipmentSlot.values();
    public static final EntityEquipmentSlot[] handSlots = new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND};
    public static final EntityEquipmentSlot[] mainHand = new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND};

    public static final RegistryNamespaced<ResourceLocation, Enchantment> REGISTRY = net.minecraftforge.registries.GameData.getWrapper(Enchantment.class);
    public static final List<Enchantment> ENCHANTMENT_LIST = new ArrayList<Enchantment>();

    //Example Enchant
//    public static final ModEnchantmentBase ANTI_VANILLA = new ModEnchantmentBase("idlframewok.anti_vanilla", Enchantment.Rarity.UNCOMMON, EnumEnchantmentType.WEAPON,  mainHand)
//            .setMaxLevel(10).setValue(0.3f, 0.3f);

    //Conflict groups
    public static final Enchantment[] WEAPON_DAMAGE_CONFLICT_GROUP = new Enchantment[]
            {
                    Enchantments.SHARPNESS,
                    Enchantments.SMITE,
                    Enchantments.BANE_OF_ARTHROPODS,
                    //ANTI_VANILLA,
            };


    public static void BeforeRegister()
    {
        //ANTI_AOA.setHidden(!MetaUtil.isLoaded_AOA3);

        ApplyConflictGroup(WEAPON_DAMAGE_CONFLICT_GROUP);
    }

    private static void ApplyConflictGroup(Enchantment[] group)
    {
        for (Enchantment ench:
                group
        ) {
            //is my enchants
            if (ench instanceof ModEnchantmentBase)
            {
                ((ModEnchantmentBase) ench).setConflicts(group);
            }
        }
    }


    @SubscribeEvent
    public static void onCreatureHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();

        if (evt.isCanceled() || world.isRemote)
        {
            return;
        }

        float dmgModifier = 1f;

        Entity trueSource = evt.getSource().getTrueSource();
        if (trueSource instanceof EntityLivingBase) {

            EntityLivingBase attacker = (EntityLivingBase) trueSource;

        }

        evt.setAmount(evt.getAmount() * dmgModifier);
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();

        for (EntityEquipmentSlot slot:
                EntityEquipmentSlot.values()) {
            checkItemForTicking(livingBase, slot);
        }
    }

    public static void checkItemForTicking(EntityLivingBase living, EntityEquipmentSlot slot)
    {
        World world = living.world;

        ItemStack stack = living.getItemStackFromSlot(slot);
        if (!stack.isEmpty())
        {
            if (world.getWorldTime() % TICK_PER_SECOND == 0)
            {

            }
        }
    }

    void OnJump(LivingEvent.LivingJumpEvent event)
    {

    }
}
