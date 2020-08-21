package com.deeplake.idealland.item.skills;

import com.deeplake.idealland.potion.ModPotions;
import com.deeplake.idealland.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemSkillCalmWalk extends ItemSkillBase{
    public ItemSkillCalmWalk(String name) {
        super(name);
        showDamageDesc = false;
        showCDDesc = false;
        showRangeDesc = false;
        maxLevel = 4;
    }
    //todo: Lv1 immnune most damage, cant deal damage. slow 1
    //Lv2 immnue all damage,slow 1
    //Lv3 also immune knock backs

    public static ItemStack AttemptPlayerHand(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof ItemSkillCalmWalk){
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void onUpdate(ItemStack stack1, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack1, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote && (worldIn.getWorldTime() % TICK_PER_SECOND == 0))
        {
            ItemStack stack = AttemptPlayerHand((EntityPlayer) entityIn, EnumHand.MAIN_HAND);
            if (stack == ItemStack.EMPTY)
            {
                stack = AttemptPlayerHand((EntityPlayer) entityIn, EnumHand.OFF_HAND);
            }
            if (stack != ItemStack.EMPTY)
            {
                int level = getLevel(stack);
                if (level <= 2)
                {
                    ((EntityPlayer) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5 * TICK_PER_SECOND, 2 - level));
                }

                ((EntityPlayer) entityIn).addPotionEffect(new PotionEffect(ModPotions.KB_RESIST, 5 * TICK_PER_SECOND, level));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        if (evt.isCanceled())
        {
            return;
        }

        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote) {
            EntityLivingBase hurtOne = evt.getEntityLiving();
            Entity trueSource = evt.getSource().getTrueSource();

            if (hurtOne instanceof EntityPlayer && !(evt.getSource().getTrueSource() instanceof EntityPlayer)) {
                EntityPlayer player = (EntityPlayer) hurtOne;
                ItemStack stack = AttemptPlayerHand(player, EnumHand.MAIN_HAND);
                if (stack == ItemStack.EMPTY) {
                    stack = AttemptPlayerHand(player, EnumHand.OFF_HAND);
                }
                if (stack != ItemStack.EMPTY) {
                    ItemSkillCalmWalk skillThunderFall = ((ItemSkillCalmWalk) stack.getItem());

                    if (!player.world.isRemote) {
                        //int level = skillThunderFall.getLevel(stack);
                        evt.setCanceled(true);
                    }
                }
            }

            if (trueSource instanceof EntityPlayer) {
                ItemStack stack = AttemptPlayerHand((EntityPlayer) trueSource, EnumHand.MAIN_HAND);
                if (stack == ItemStack.EMPTY) {
                    stack = AttemptPlayerHand((EntityPlayer) trueSource, EnumHand.OFF_HAND);
                }
                if (stack != ItemStack.EMPTY) {
                    evt.setCanceled(true);
                }
            }
        }
    }


}
