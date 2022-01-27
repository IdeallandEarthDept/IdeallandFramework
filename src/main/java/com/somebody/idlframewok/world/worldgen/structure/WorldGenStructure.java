package com.somebody.idlframewok.world.worldgen.structure;

import com.somebody.idlframewok.util.Reference;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenStructure extends WorldGenerator implements IStructure {

    public String structureName;
    ResourceLocation location ;
    public WorldGenStructure(String name) {
        structureName = name;
        location = new ResourceLocation(Reference.MOD_ID, structureName);
    }

    public void generateStructure(World worldIn, BlockPos pos) {
        WorldGenUtil.generate(location, worldIn, pos, null);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        this.generateStructure(worldIn, position);
        return true;
    }
}
