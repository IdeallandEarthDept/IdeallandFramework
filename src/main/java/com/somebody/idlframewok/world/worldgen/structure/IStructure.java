package com.somebody.idlframewok.world.worldgen.structure;

import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraftforge.fml.common.FMLCommonHandler;

public interface IStructure {
    WorldServer worldServer = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
    PlacementSettings settings = (new PlacementSettings()).setIgnoreEntities(false);

}
