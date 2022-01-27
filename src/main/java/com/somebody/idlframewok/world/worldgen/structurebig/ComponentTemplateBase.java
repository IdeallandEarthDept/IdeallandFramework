package com.somebody.idlframewok.world.worldgen.structurebig;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponentTemplate;

import java.util.Random;

public class ComponentTemplateBase extends StructureComponentTemplate {
    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb) {

    }
}
