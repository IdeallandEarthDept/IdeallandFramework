package com.somebody.idlframewok.designs.villagers;

import com.somebody.idlframewok.Idealland;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class ModVProfession extends VillagerRegistry.VillagerProfession {
    public ModVProfession(String name, String texture, String zombie) {
        super(Idealland.MODID + "." +name, texture, zombie);
        InitVillagers.PROFESSION_LIST.add(this);
    }

    public ModVProfession(String name) {
        this(name,
                "minecraft:textures/entity/villager/priest.png",
                "minecraft:textures/entity/zombie_villager/zombie_priest.png");
    }
}
