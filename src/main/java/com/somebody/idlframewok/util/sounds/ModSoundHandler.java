package com.somebody.idlframewok.util.sounds;

import com.somebody.idlframewok.Idealland;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ModSoundHandler {

    public static final List<ModSoundEvent> SOUNDS = new ArrayList<>();

    public static SoundEvent SOUND_1 = new ModSoundEvent("entity.moroon.ambient");
    public static SoundEvent SOUND_2 = new ModSoundEvent("entity.moroon.hurt");

    public static SoundEvent MJDS_JUMP = new ModSoundEvent("mjds.jump");
    public static SoundEvent MJDS_FALL = new ModSoundEvent("mjds.fall");

    public static void soundRegister()
    {
        Idealland.Log("Registering %s sounds.", SOUNDS.size());
        ForgeRegistries.SOUND_EVENTS.registerAll(ModSoundHandler.SOUNDS.toArray(new SoundEvent[0]));
        Idealland.Log("Registered %s sounds.", SOUNDS.size());
    }
}
