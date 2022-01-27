package com.somebody.idlframewok.designs.events.design;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.enchantments.ModEnchantmentInit;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.util.Color16Def.MSG_USE_FALL_PROTECT;
import static com.somebody.idlframewok.util.Color16Def.MSG_USE_OOW_PROTECT;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsSkyland {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    static void onTakeOutWorldDamage(LivingHurtEvent hurtEvent)
    {
        World world = hurtEvent.getEntityLiving().world;
        if (!world.isRemote)
        {
            EntityLivingBase livingBase = hurtEvent.getEntityLiving();
            int protection = IDLNBTUtil.GetIntAuto(livingBase, Color16Def.OUT_WORLD_PROTECT, 0);
            if (hurtEvent.getSource() == DamageSource.OUT_OF_WORLD
                && livingBase.posY < 0)
            {
                if (protection > 0)
                {
                    hurtEvent.setCanceled(true);
                    if (ModEnchantmentInit.VOID_CYCLE.getLevelOnCreature(livingBase) > 0)
                    {
                        for (EntityEquipmentSlot slot:
                                EntityEquipmentSlot.values()) {
                            ItemStack stack = livingBase.getItemStackFromSlot(slot);
                            if (stack.isEmpty())
                            {
                                continue;
                            }

                            if (ModEnchantmentInit.VOID_CYCLE.getEnchantmentLevel(stack) > 0)
                            {
                                stack.damageItem(1, livingBase);
                                livingBase.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1f, 2f);
                                break;
                            }
                        }
                    }
                    else {
                        IDLNBTUtil.setIntAuto(livingBase, Color16Def.OUT_WORLD_PROTECT, 0);
                        livingBase.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1f, 1f);
                    }

                    IDLNBTUtil.addIntAuto(livingBase, Color16Def.FALL_PROTECT, 1);
                    livingBase.setPositionAndUpdate(livingBase.posX, 260, livingBase.posZ);
                    EntityUtil.ApplyBuff(livingBase, ModPotions.OOW_BLESS, 0, 300);
                    livingBase.motionY = 0;
                    CommonFunctions.SafeSendMsgToPlayer(livingBase, MSG_USE_OOW_PROTECT);
                } else if (ModEnchantmentInit.VOID_CYCLE.getLevelOnCreature(livingBase) > 0)
                {
                    for (EntityEquipmentSlot slot:
                            EntityEquipmentSlot.values()) {
                        ItemStack stack = livingBase.getItemStackFromSlot(slot);
                        if (stack.isEmpty())
                        {
                            continue;
                        }

                        if (ModEnchantmentInit.VOID_CYCLE.getEnchantmentLevel(stack) > 0)
                        {
                            stack.damageItem(255, livingBase);
                            livingBase.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1f, 1f);
                            break;
                        }
                    }
                    livingBase.setPositionAndUpdate(livingBase.posX, 260, livingBase.posZ);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    static void onTakeFallDamage(LivingHurtEvent hurtEvent)
    {
        World world = hurtEvent.getEntityLiving().world;
        if (!world.isRemote)
        {
            EntityLivingBase livingBase = hurtEvent.getEntityLiving();
            int protect = IDLNBTUtil.GetIntAuto(livingBase, Color16Def.FALL_PROTECT, 0);
            if (hurtEvent.getSource() == DamageSource.FALL &&
                    protect > 0)
            {
                hurtEvent.setCanceled(true);
                IDLNBTUtil.addIntAuto(livingBase, Color16Def.FALL_PROTECT, -1);
                CommonFunctions.SafeSendMsgToPlayer(livingBase, MSG_USE_FALL_PROTECT, protect - 1);
            }
        }
    }
}
