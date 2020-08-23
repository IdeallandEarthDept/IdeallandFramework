package com.deeplake.idealland.potion;

import com.deeplake.idealland.IdlFramework;
import com.deeplake.idealland.potion.buff.BasePotion;
import com.deeplake.idealland.potion.buff.PotionDeadly;
import com.deeplake.idealland.potion.buff.PotionZenHeart;
import com.deeplake.idealland.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static net.minecraftforge.fml.common.eventhandler.Event.Result.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModPotions {

    public static final List<Potion> INSTANCES = new ArrayList<Potion>();

//    public static final PotionDeadly DEADLY = new PotionDeadly(false, 0x333333, "deadly", 0);
//    public static final PotionZenHeart ZEN_HEART = new PotionZenHeart(false, 0xcccc00, "zen_heart", 1);

    @Nullable
    private static Potion getRegisteredMobEffect(String id)
    {
        Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(id));

        if (potion == null)
        {
            throw new IllegalStateException("Invalid MobEffect requested: " + id);
        }
        else
        {
            return potion;
        }
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> evt)
    {
        //VIRUS_ONE.tuples.add(new EffectTuple(0.2f, MobEffects.NAUSEA, 100));

        evt.getRegistry().registerAll(INSTANCES.toArray(new Potion[0]));
        IdlFramework.LogWarning("registered %d potion", INSTANCES.size());
    }
}
