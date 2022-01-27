package com.somebody.idlframewok.enchantments;

import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class ModEnchantmentHalo extends ModEnchantmentBase {

    final Potion potion;

    float range = 8f;
    float sqRange = range * range;

    boolean toAll = false;
    EntityUtil.EnumAttitude reqAttitude = EntityUtil.EnumAttitude.FRIEND;

    EnumParticleTypes particleTypes = EnumParticleTypes.ENCHANTMENT_TABLE;

    public ModEnchantmentHalo setRange(float range) {
        this.range = range;
        sqRange = range * range;
        return this;
    }

    public ModEnchantmentHalo setToAll() {
        this.toAll = toAll;
        return this;
    }

    public ModEnchantmentHalo setReqAttitude(EntityUtil.EnumAttitude reqAttitude) {
        this.reqAttitude = reqAttitude;
        return this;
    }

    public ModEnchantmentHalo setParticleTypes(EnumParticleTypes particleTypes) {
        this.particleTypes = particleTypes;
        return this;
    }

    public ModEnchantmentHalo(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots, Potion potion) {
        super(name, rarityIn, typeIn, slots);
        this.potion = potion;
        CommonFunctions.addToEventBus(this);
    }

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase livingBase = event.getEntityLiving();
        World world = livingBase.getEntityWorld();
        int level = getLevelOnCreature(livingBase);
        if (level == 0)
        {
            return;
        }

        //can use some optimization here.
        if (world.isRemote)
        {
            EntityUtil.spawnHaloParticleAround(livingBase, particleTypes, range);
        }else {
            if (world.getWorldTime() % TICK_PER_SECOND == 0)
            {
                List<EntityLivingBase> in_block = EntityUtil.getEntitiesWithinAABB(world, EntityLivingBase.class, livingBase.getPositionEyes(0f), range, null);
                for (EntityLivingBase target:
                     in_block) {
                    if ((toAll || EntityUtil.getAttitude(livingBase, target) == reqAttitude)
                            && livingBase.getDistanceSq(target) < sqRange)
                    {
                        applyEffect(livingBase, target);
                    }
                }
            }
        }
    }

    public void applyEffect(EntityLivingBase source, EntityLivingBase target)
    {
        EntityUtil.ApplyBuff(target, potion, getLevelOnCreature(source) - 1, 2f);
    }





}
