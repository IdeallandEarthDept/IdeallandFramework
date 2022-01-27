package com.somebody.idlframewok.world.worldgen.structure;

import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;

//3 Chunks
public class IDFGenStructure32 extends IDFGenStructure implements IStructure {
    int chunkCountX;

    int chunkCountZ;

    public IDFGenStructure32(String name, int chunkCountX, int chunkCountZ) {
        super(name);
        this.chunkCountX = chunkCountX;
        this.chunkCountZ = chunkCountZ;
    }

    public boolean shouldGen(int chunkX, int chunkZ) {
        return chunkX % chunkCountX == 0 && chunkZ % chunkCountZ == 0;
    }

    public int getChunkCountX() {
        return chunkCountX;
    }

    public void setChunkCountX(int chunkCountX) {
        this.chunkCountX = chunkCountX;
    }

    public int getChunkCountZ() {
        return chunkCountZ;
    }

    public void setChunkCountZ(int chunkCountZ) {
        this.chunkCountZ = chunkCountZ;
    }

    public void generateStructure(World worldIn, BlockPos pos, Mirror mirror, Rotation rotation) {
        WorldGenUtil.generateIgnoreChunkBorder(location, worldIn, pos.add(0, yOffset, 0), new PlacementSettings().setMirror(mirror).setRotation(rotation));
    }

    public void generateStructure(World worldIn, BlockPos pos) {
        WorldGenUtil.generateIgnoreChunkBorder(location, worldIn, pos.add(0, yOffset, 0), null);
    }
}
