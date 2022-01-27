package com.somebody.idlframewok.world.biome;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.designs.events.design.socket.EventsFleshSocket.getFleshEquipCount;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class BiomeFlesh extends BiomeBase {
    public static final int color = 0xFF3333;

    public BiomeFlesh() {
        super(new BiomePropertiesModified("flesh_land").setBaseHeight(0.5f).setHeightVariation(0.4f).setTemperature(1.5f).setWaterColor(color));
        CommonFunctions.addToEventBus(this);
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingUpdateEvent event)
    {
        World world = event.getEntityLiving().world;
        if (!world.isRemote)
        {
            EntityLivingBase livingBase = event.getEntityLiving();

            if (EntityUtil.getBiomeForEntity(livingBase) instanceof BiomeFlesh)
            {
                int count = getFleshEquipCount(livingBase);
                if (livingBase instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) livingBase;

                    if (CommonFunctions.isSecondTick(world))
                    {
                        if (count < 2)
                        {
                            player.getFoodStats().addExhaustion(0.05f);
                        }
                    }

                    if (count < 4 && EntityUtil.getBuffLevelIDL(livingBase, MobEffects.POISON) == 0 && livingBase.isInWater())
                    {
                        //poison if not poisoned already
                        EntityUtil.ApplyBuff(livingBase, MobEffects.POISON, 0, 5f);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    void onGetGrassColor(BiomeEvent.GetGrassColor event)
    {
        if (event.getBiome() == this)
        {
            event.setNewColor(color);
        }
    }



}
