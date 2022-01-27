package com.somebody.idlframewok.designs.god16;

import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.world.biome.GodBelieverSingle;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.Idealland.MODID;
import static com.somebody.idlframewok.util.Color16Def.GOLD;
import static com.somebody.idlframewok.util.Color16Def.IRON;

@Mod.EventBusSubscriber(modid = MODID)
public class EventsGod {

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase livingBase = event.getEntityLiving();
        World world = livingBase.getEntityWorld();
        if (!world.isRemote)
        {
            Biome biome = world.getBiome(livingBase.getPosition());
            if (biome instanceof GodBelieverSingle)
            {
                int index =((GodBelieverSingle) biome).getGodIndex();
                God16Base god16Base = Init16Gods.GODS[index];
                int tribute = god16Base.getBeliefPoint(livingBase);
                Potion potion = god16Base.buff;
                if (potion != null && tribute > 50)
                {
                    if (potion == MobEffects.NIGHT_VISION || livingBase.getActivePotionEffect(potion) == null)
                    {
                        EntityUtil.ApplyBuff(livingBase, potion, 0, 5);
                    }
                }

                potion = god16Base.debuff;
                if (potion != null && tribute < -50)
                {
                    if (livingBase.getActivePotionEffect(potion) == null)
                    {
                        EntityUtil.ApplyBuff(livingBase, potion, 0, 5);
                    }
                }
            }
        }
    }

    public static ItemStack getArmorStack(EntityEquipmentSlot slot, int index, EntityLivingBase livingBase)
    {
        if (livingBase.world == null)
        {
            return ItemStack.EMPTY;
        }

        ItemStack stack = ItemStack.EMPTY;
        Item item = null;
        switch (index)
        {
            case IRON:
                item = EntityLiving.getArmorByChance(slot, 3);
                break;
            case GOLD:
                item = EntityLiving.getArmorByChance(slot, 1);
                break;
        }

        if (item != null)
        {
            stack = new ItemStack(item);
            //enchant like vanilla does
            float f = livingBase.world.getDifficultyForLocation(livingBase.getPosition()).getClampedAdditionalDifficulty();
            if (!stack.isEmpty() && livingBase.getRNG().nextFloat() < 0.5F * f)
            {
                stack = EnchantmentHelper.addRandomEnchantment(livingBase.getRNG(), stack, (int)(5.0F + f * (float)livingBase.getRNG().nextInt(18)), false);
            }
        }
        return stack;
    }

    public static void trySetArmorGod(EntityLivingBase livingBase, int index)
    {
        World world = livingBase.world;
        if (world == null)
        {
            return;
        }
        boolean fullSuit = false;
        boolean chest = false;
        switch (world.getDifficulty())
        {
            case NORMAL:
                chest = livingBase.getRNG().nextFloat() < 0.2f;
                fullSuit = livingBase.getRNG().nextFloat() < 0.01f;
                break;

            case HARD:
                fullSuit = livingBase.getRNG().nextFloat() < 0.5f;
                break;
        }

        if (chest || fullSuit)
        {
            EntityUtil.giveIfEmpty(livingBase, EntityEquipmentSlot.CHEST, getArmorStack(EntityEquipmentSlot.CHEST, index, livingBase));

            if (fullSuit)
            {
                EntityUtil.giveIfEmpty(livingBase, EntityEquipmentSlot.HEAD, getArmorStack(EntityEquipmentSlot.HEAD, index, livingBase));
                EntityUtil.giveIfEmpty(livingBase, EntityEquipmentSlot.LEGS, getArmorStack(EntityEquipmentSlot.LEGS, index, livingBase));
                EntityUtil.giveIfEmpty(livingBase, EntityEquipmentSlot.FEET, getArmorStack(EntityEquipmentSlot.FEET, index, livingBase));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    static void onSpawn(LivingSpawnEvent.SpecialSpawn event)
    {
        World world = event.getEntity().getEntityWorld();
        if (world == null || world.isRemote)
        {
            return;
        }

        Biome biome = world.getBiome(new BlockPos(event.getX(), event.getY(), event.getZ()));

        if (biome instanceof GodBelieverSingle)
        {
            if (event.getEntity() instanceof EntityLivingBase)
            {
                EntityLivingBase livingBase = (EntityLivingBase) event.getEntity();
                int index = ((GodBelieverSingle) biome).getGodIndex();
                if (livingBase instanceof EntitySheep)
                {
                    EntitySheep sheep = (EntitySheep) livingBase;
                    sheep.setFleeceColor(EnumDyeColor.byDyeDamage(index));
                }

                if (livingBase instanceof EntityLiving && EntityUtil.IS_HUMANOID.apply(livingBase))
                {
                    God16Base god = Init16Gods.getGodByIndex(index);
                    if (god != null) {
                        //tipped arrow
                        if (livingBase instanceof EntitySkeleton || livingBase.getHeldItemMainhand().getItem() == Items.BOW) {
                            if (god.getPotionTypeBad() != null)
                            {
                                ItemStack stack = new ItemStack(Items.TIPPED_ARROW);

                                PotionUtils.addPotionToItemStack(stack, god.getPotionTypeBad());
                                //PotionUtils.appendEffects(stack, PotionUtils.getFullEffectsFromItem(itemstack));
                                EntityUtil.giveIfEmpty(livingBase, EntityEquipmentSlot.OFFHAND, stack);
                            }
                        }

                        //banner
                        if (livingBase.getRNG().nextFloat() < 0.1f) {
                            livingBase.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemBanner.makeBanner(EnumDyeColor.byDyeDamage(index), null));
                            Color16Def.increaseBelief(livingBase, index, 100);
                        }

                        //hand
                        switch (index) {
                            case Color16Def.FEMALE:
                                //pink tulip
                                EntityUtil.giveIfEmpty(livingBase, EntityEquipmentSlot.MAINHAND, new ItemStack(Blocks.RED_FLOWER, 1, 7));
                                break;
                            case Color16Def.MALE:
                                //blue orchid
                                EntityUtil.giveIfEmpty(livingBase, EntityEquipmentSlot.OFFHAND, new ItemStack(Blocks.RED_FLOWER, 1, 1));
                                break;
                        }
                        EntityUtil.giveIfEmpty(livingBase, god.getSlotForItem(), god.getRepresentiveStack());

                        //armor
                        trySetArmorGod(livingBase, index);
                    }
                }
            }
        }
    }
}
