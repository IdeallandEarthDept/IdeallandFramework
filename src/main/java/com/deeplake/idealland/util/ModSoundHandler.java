package com.deeplake.idealland.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModSoundHandler {
    public static SoundEvent SOUND_1, SOUND_2, SOUND_3;

    //To add a sound, remember assets.idealland.sounds.json


    public static void  registerSounds()
    {
        //SOUND_1 = registerSound("entity.moroon.ambient");
        //SOUND_2 = registerSound("entity.moroon.hurt");
//        SOUND_3 = registerSound("entity.moroon.ambient3");
    }

    private static SoundEvent registerSound(String name)
    {
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return  event;
    }

}
