package com.somebody.idlframewok.world.worldgen.structurebig.test;

import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class TestComponentBase extends StructureComponent {
    int colorIndex;
    String KEY_COLOR = "Color";
    BlockPos basePos;

    //Make sure you have this. Or it will error when reloading the game.
    //See MapGenStructureIO::getStructureComponent
    public TestComponentBase() {

    }

    public TestComponentBase(int type, int colorIndex, BlockPos pos) {
        super(type);
        this.colorIndex = colorIndex;
        basePos = pos;
        boundingBox = new StructureBoundingBox(
                pos.getX(), pos.getY(), pos.getZ(),
                pos.getX() + 16, pos.getY() + 4, pos.getZ() + 16);
        setCoordBaseMode(EnumFacing.NORTH);
//        Idealland.Log("Spawning Test Component: %s, %s", basePos, boundingBox);
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger(KEY_COLOR, colorIndex);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
        colorIndex = tagCompound.getInteger(KEY_COLOR);
    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        IBlockState state = Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.byMetadata(colorIndex));

        IBlockState state2 = Blocks.GLASS.getDefaultState();
        for (int x = 0; x <= 16; x++) {
            for (int y = 0; y <= 4; y++) {
                for (int z = 0; z <= 16; z++) {
                    if ((x + z) % 8 == 0) {
//                        Idealland.Log("Spawning Block(%s,%s,%s)", x,y,z);
                        setBlockState(worldIn, state, x, y, z, structureBoundingBoxIn);
                    } else {
                        setBlockState(worldIn, state2, x, y, z, structureBoundingBoxIn);
                    }
                }
            }
        }

        return true;
    }
}
