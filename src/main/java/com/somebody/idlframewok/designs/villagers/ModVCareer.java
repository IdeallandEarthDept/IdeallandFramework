package com.somebody.idlframewok.designs.villagers;

import com.somebody.idlframewok.Idealland;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;

public class ModVCareer extends VillagerCareer {
    public ModVCareer(VillagerRegistry.VillagerProfession parent, String name) {
        super(parent, Idealland.MODID + "." +name);
    }
}
