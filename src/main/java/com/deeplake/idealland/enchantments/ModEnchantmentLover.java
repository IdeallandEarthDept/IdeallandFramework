package com.deeplake.idealland.enchantments;

import com.deeplake.idealland.util.CommonFunctions;
import com.deeplake.idealland.util.EntityUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.deeplake.idealland.util.EntityUtil.GetRandomAround;

//@Mod.EventBusSubscriber(modid=Idealland.MODID)
public class ModEnchantmentLover extends ModEnchantmentBase {
    public float range = 15f;

    public ModEnchantmentLover(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(name, rarityIn, typeIn, slots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 10;
    }


    //need to plus 1
    public float getDistanceAddtionModifier(float distance)
    {
        return (range - distance) / range;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onCreatureDie(LivingDeathEvent event)
    {
        if (event.isCanceled() || event.getEntityLiving().world.isRemote)
        {
            return;
        }

        EntityLivingBase diedOne = event.getEntityLiving();

        EntityLivingBase lover = EntityUtil.FindLover(event.getEntityLiving(), range,this);
        if (lover != null)
        {
            CommonFunctions.BroadCastByKey("idealland.msg.until_death", diedOne.getName(), lover.getName());
            lover.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
        }
    }

     public float getLifeStealRatio(int level)
     {
         return level * 0.1f;
     }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onCreatureAttack(LivingHurtEvent event) {
        World world = event.getEntity().getEntityWorld();
        EntityLivingBase hurtone = event.getEntityLiving();

        Entity trueSource = event.getSource().getTrueSource();
        if (trueSource instanceof EntityLivingBase) {

            EntityLivingBase attacker = (EntityLivingBase) trueSource;
            if (event.isCanceled())
            {
                return;
            }

            int loverLevelThis = EnchantmentHelper.getMaxEnchantmentLevel(this, attacker);
            if (loverLevelThis == 0)
            {
                return;
            }

            EntityLivingBase lover = EntityUtil.FindLover(attacker, range,this);

            if (lover != null)
            {
//                if (attacker instanceof  EntityPlayer) {
//                    Idealland.Log("lover lover found!");
//                }
                if (world.isRemote) {
                    Vec3d pos = GetRandomAround(lover,1f);
                    world.spawnParticle(EnumParticleTypes.HEART, pos.x, pos.y, pos.z, 0, 1, 0);
                }else {
                    float distance = lover.getDistance(attacker);
                    if (event.getAmount() != Float.MAX_VALUE)
                    {
                        float healAmount = event.getAmount() * getLifeStealRatio(loverLevelThis) * (1 + getDistanceAddtionModifier(distance));
                        //Idealland.Log("lover heal %f",healAmount);
                        lover.heal(healAmount);
                    }
                }
            }
        }


    }

}
