package com.somebody.idlframewok.potion.instance;

import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

//todo: need test
public class ModPotionFreeze extends ModPotionBase {
    public ModPotionFreeze(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
        super(isBadEffectIn, liquidColorIn, name, icon);
        CommonFunctions.addToEventBus(this);
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "f2c40362-63c4-4bc7-9142-a475b10d1030", -1, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, "f2c40362-63c4-4bc7-9142-a475b10d1030", -1, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "f2c40362-63c4-4bc7-9142-a475b10d1030", -1, 2);
    }

    @SubscribeEvent
    public void tick(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving().world.isRemote && event.getEntityLiving().getActivePotionEffect(this) != null)
        {
            World world = event.getEntity().world;
            EntityLivingBase livingBase = event.getEntityLiving();
            Random random = livingBase.getRNG();
            world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, livingBase.posX + random.nextFloat(),
                    livingBase.posY + random.nextFloat(),
                    livingBase.posZ + random.nextFloat(),
                    0,
                    0,
                    0);

            if (livingBase.isBurning())
            {
                EntityUtil.TryRemoveGivenBuff(livingBase, this);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onCreatureHurt(LivingHurtEvent evt) {
        if (!evt.getEntityLiving().world.isRemote && !evt.isCanceled())
        {
            if (evt.getSource().isFireDamage())
            {
                float damage = evt.getAmount();
                EntityUtil.TryRemoveGivenBuff(evt.getEntityLiving(), this);
            }
        }

    }
}
