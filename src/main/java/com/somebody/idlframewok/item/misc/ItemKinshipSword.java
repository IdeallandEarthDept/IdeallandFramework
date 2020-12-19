package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.item.IGuaEnhance;
import com.somebody.idlframewok.item.ItemSwordBase;
import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.util.IDLSkillNBT;
import com.somebody.idlframewok.util.Reference;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.IDLSkillNBT.GetGuaEnhance;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemKinshipSword extends ItemSwordBase implements IGuaEnhance {

    public ItemKinshipSword(String name, ToolMaterial material) {
        super(name, material);
        shiftToShowDesc = true;
    }

//    @SubscribeEvent
//    public static void onCreatureHurt(LivingHurtEvent evt) {
//        if (evt.isCanceled())
//        {
//            return;
//        }
//
//        World world = evt.getEntity().getEntityWorld();
//        if (!world.isRemote) {
//            EntityLivingBase hurtOne = evt.getEntityLiving();
//            Entity trueSource = evt.getSource().getTrueSource();
//
//            if (hurtOne instanceof EntityPlayer && !(evt.getSource().getTrueSource() instanceof EntityPlayer)) {
//                EntityPlayer player = (EntityPlayer) hurtOne;
//                ItemStack stack = AttemptPlayerHand(player, EnumHand.MAIN_HAND);
//                if (stack == ItemStack.EMPTY) {
//                    stack = AttemptPlayerHand(player, EnumHand.OFF_HAND);
//                }
//                if (stack != ItemStack.EMPTY) {
//                    ItemSkillCalmWalk skillThunderFall = ((ItemSkillCalmWalk) stack.getItem());
//
//                    if (!player.world.isRemote) {
//                        //int level = skillThunderFall.getLevel(stack);
//                        evt.setCanceled(true);
//                    }
//                }
//            }
//
//            if (trueSource instanceof EntityPlayer) {
//                ItemStack stack = AttemptPlayerHand((EntityPlayer) trueSource, EnumHand.MAIN_HAND);
//                if (stack == ItemStack.EMPTY) {
//                    stack = AttemptPlayerHand((EntityPlayer) trueSource, EnumHand.OFF_HAND);
//                }
//                if (stack != ItemStack.EMPTY) {
//                    evt.setCanceled(true);
//                }
//            }
//        }
//    }


    public float getSplashDamage(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        return  (float)attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        World worldIn = attacker.getEntityWorld();

        if (!worldIn.isRemote)
        {
            float base_range = 16f + IDLSkillNBT.GetGuaEnhance(stack, 7);

            Vec3d basePos = attacker.getPositionVector();
            List<EntityLivingBase> entities = worldIn.getEntitiesWithinAABB(target.getClass(), IDLGeneral.ServerAABB(basePos.addVector(-base_range, -base_range, -base_range), basePos.addVector(base_range, base_range, base_range)));

            for (EntityLivingBase living: entities
            ) {
                if (living != target)
                {
                    //damage is only dealt once
                    OnHitBasic(stack, attacker, living, EnumHand.MAIN_HAND);
                    OnHitExtra(stack, attacker, living, EnumHand.MAIN_HAND);
                }
                else {
                    OnHitExtra(stack, attacker, living, EnumHand.MAIN_HAND);
                }

            }
        }

        return true;
    }
    //gua enhance
    float windModifier = 0.1f;
    //6 wind
    public float getKBPower(ItemStack stack)
    {
        return  0.4f + windModifier * GetGuaEnhance(stack, 6);
    }

    public void OnHitBasic(ItemStack stack, EntityLivingBase playerIn, EntityLivingBase target, EnumHand handIn)
    {
        if (!playerIn.world.isRemote) {
            float dmg = getSplashDamage(stack, target, playerIn) / 2f + getExtraDamage(stack, target.isWet());

            target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) playerIn), dmg);
        }
    }

    float getExtraDamage(ItemStack stack, boolean targetWet)
    {
        int fire = GetGuaEnhance(stack, 5);
        int thunder = GetGuaEnhance(stack, 1);
        return  fire + (targetWet ? thunder * 2 : thunder);
    }

    public void OnHitExtra(ItemStack stack, EntityLivingBase playerIn, EntityLivingBase target, EnumHand handIn)
    {
        int mountain = GetGuaEnhance(stack, 4);
        int water = GetGuaEnhance(stack, 2);
        int lake = GetGuaEnhance(stack, 3);
        int fire = GetGuaEnhance(stack, 5);
        int thunder = GetGuaEnhance(stack, 1);

        if (mountain > 0)
        {
            target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, TICK_PER_SECOND * mountain * 2, mountain / 4));
        }

        if (water > 0)
        {
            target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, TICK_PER_SECOND * water * 2, water / 4));
            target.extinguish();
        }

        if (lake > 0)
        {
            target.heal(lake);
            playerIn.heal(lake);
        }

        //wind
        target.knockBack(playerIn, getKBPower(stack), (playerIn.posX - target.posX), (playerIn.posZ - target.posZ));

        //fire
        target.setFire(fire);

        //thunder
        if (thunder >= 8)
        {
            playerIn.world.addWeatherEffect(new EntityLightningBolt(playerIn.world, target.posX, target.posY, target.posZ, false));
        }

    }

    @Override
    public boolean acceptGuaIndex(int index) {
        return index != 0;
    }

    class BuffTuple
    {
        public int tick;
        public int power;

        public BuffTuple(int power, int tick) {
            this.tick = tick;
            this.power = power;
        }
    }

//    public com.somebody.idlframewok.item.skills.martial.BuffTuple GetWaterBuff(ItemStack stack)
//    {
//        int water = GetGuaEnhance(stack, 2);
//        return new com.somebody.idlframewok.item.skills.martial.BuffTuple(water / 4, TICK_PER_SECOND * water * 2);
//    }
//
//    public com.somebody.idlframewok.item.skills.martial.BuffTuple GetMountainBuff(ItemStack stack)
//    {
//        int gua = GetGuaEnhance(stack, 4);
//        return new com.somebody.idlframewok.item.skills.martial.BuffTuple(gua / 4, TICK_PER_SECOND * gua * 2);
//    }
//
//    public DamageSource GetDamageSource(ItemStack stack, EntityLivingBase playerIn, EntityLivingBase target, EnumHand handIn)
//    {
//        return new EntityDamageSource("race", playerIn);
//    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier",  getBaseAttackDamage() + GetGuaEnhance(stack, 7), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
}
