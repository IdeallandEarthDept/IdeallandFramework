package com.somebody.idlframewok.world.worldgen.structurebig;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.world.worldgen.structurebig.test.ModGenStructureTest;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class ModifyOverworld {

    ModGenStructureTest genStructureTest = new ModGenStructureTest();

//    @SubscribeEvent
//    public static void onReplaceTopBlock(ChunkGeneratorEvent.ReplaceBiomeBlocks event) {
//        if (event.getWorld().provider.getDimension() == DimensionType.OVERWORLD.getId()) {
//
//        }
//    }
}
