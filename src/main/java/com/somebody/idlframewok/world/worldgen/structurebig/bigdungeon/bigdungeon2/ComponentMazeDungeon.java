package com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.bigdungeon2;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.world.worldgen.structurebig.Maze3d;
import com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.ComponentDungeonRoomBase;
import com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon.StructureBigDungeon;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class ComponentMazeDungeon extends ComponentDungeonRoomBase {

    public void setState(int state) {
        this.state = state;
    }

    int state = 0;

    //grammar
    public ComponentMazeDungeon() {
        setCoordBaseMode(EnumFacing.SOUTH);
        outerSize = 0;
    }

    public ComponentMazeDungeon(int type, EnumFacing mainDirection, Vec3i vec3i, StructureBigDungeon.Start start) {
        super(type, mainDirection, vec3i, start);
        outerSize = 0;
        setCoordBaseMode(EnumFacing.SOUTH);

        BlockPos worldPos = start.basePos.add(relPos.getX() * ModConfig.DUNGEON_CONF.roomSizeXZ,
                relPos.getY() * ModConfig.DUNGEON_CONF.roomSizeY,
                relPos.getZ() * ModConfig.DUNGEON_CONF.roomSizeXZ);

        sizeXZ = ModConfig.DUNGEON_CONF.roomSizeXZ;
        sizeY = ModConfig.DUNGEON_CONF.roomSizeY;

        this.boundingBox = new StructureBoundingBox(
                worldPos,
                worldPos.add(ModConfig.DUNGEON_CONF.roomSizeXZ - 1,
                        ModConfig.DUNGEON_CONF.roomSizeY - 1,
                        ModConfig.DUNGEON_CONF.roomSizeXZ - 1)
        );
    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox sbb) {
        if (ModConfig.DEBUG_CONF.SWITCH_A) {
            buildExteriorWallAndClean(worldIn, randomIn, sbb);
        }

        buildConnections(worldIn, randomIn, sbb);

        return true;
    }

    @Override
    public IBlockState getWallState() {
        return Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.byMetadata(state % 16));
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
        super.readStructureFromNBT(tagCompound, p_143011_2_);
        state = tagCompound.getInteger(IDLNBTDef.STATE);
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound) {
        super.writeStructureToNBT(tagCompound);
        tagCompound.setInteger(IDLNBTDef.STATE, state);
    }

    public void connectByCells(Maze3d.MicroCell cell)
    {
        for (EnumFacing facing:
             cell.connectionsMicro.keySet()) {
            connect(facing,  null);
        }
    }

}
