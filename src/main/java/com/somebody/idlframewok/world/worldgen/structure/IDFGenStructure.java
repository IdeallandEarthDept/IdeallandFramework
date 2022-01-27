package com.somebody.idlframewok.world.worldgen.structure;

import com.somebody.idlframewok.util.Reference;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;

import java.util.Random;

public class IDFGenStructure extends WorldGenerator implements IStructure {

    int yOffset = 0;
    int xOffset = 7;
    int zOffset = 7;

    ResourceLocation location;
    public IDFGenStructure(String name) {
        structureName = name;
        location = new ResourceLocation(Reference.MOD_ID, structureName);
    }

    public IDFGenStructure setyOffset(int yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    public String structureName;

    public void generateStructure(World worldIn, BlockPos pos, Mirror mirror, Rotation rotation) {
        WorldGenUtil.generate(location, worldIn, pos.add(xOffset, yOffset, zOffset), new PlacementSettings().setMirror(mirror).setRotation(rotation));
    }

    public void generateStructure(World worldIn, BlockPos pos) {
        WorldGenUtil.generate(location, worldIn, pos.add(xOffset, yOffset, zOffset), null);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        this.generateStructure(worldIn, position);
        return true;
    }
}
