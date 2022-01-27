package com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.WorldGenUtil;
import com.somebody.idlframewok.world.worldgen.structurebig.ComponentBase;
import com.somebody.idlframewok.world.worldgen.structurebig.EnumConnection;
import com.sun.istack.internal.NotNull;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.HashMap;
import java.util.Random;

public class ComponentDungeonRoomBase extends ComponentBase {
    public HashMap<EnumFacing, EnumConnection> connections = new HashMap<>();
    EnumFacing mainDirection = EnumFacing.NORTH;
    public BlockPos relPos;//not in the unit of blocks, but of rooms.
    StructureBigDungeon.Start start; //please note that when populating, this may be null.

    BlockSelectorDecoFloor blockSelectorDecoFloor = new BlockSelectorDecoFloor();

    protected int outerSize = 1;
    protected int sizeXZ;
    protected int sizeY;

    private IBlockState wallState = Blocks.BRICK_BLOCK.getDefaultState();
    private IBlockState wall2 = Blocks.BRICK_BLOCK.getDefaultState();
    private IBlockState floor = Blocks.BRICK_BLOCK.getDefaultState();
    private IBlockState STEP_LIGHT = Blocks.BRICK_BLOCK.getDefaultState();
    private IBlockState GLASS_WHITE = Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.WHITE);
    IBlockState WEB = Blocks.WEB.getDefaultState();

    //grammar
    public ComponentDungeonRoomBase() {
        setCoordBaseMode(EnumFacing.SOUTH);
    }

    public ComponentDungeonRoomBase(int type, EnumFacing mainDirection, Vec3i vec3i, StructureBigDungeon.Start start) {
        super(type);
        this.mainDirection = mainDirection;
        relPos = new BlockPos(vec3i);
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

        if (ModConfig.DEBUG_CONF.DEBUG_MODE) {
            StructureBigDungeon.Start.log("[N]Created a room index(%s,%s,%s), bb is %s", vec3i.getX(), vec3i.getY(), vec3i.getZ(), this.boundingBox);
        }

        checkArgumentValidity();
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound) {
        for (EnumFacing facing : connections.keySet()) {
            tagCompound.setInteger(facing.getName(), connections.get(facing).ordinal());
        }
        tagCompound.setTag(IDLNBTDef.POS_NAME, NBTUtil.createPosTag(relPos));
        tagCompound.setInteger(IDLNBTDef.MAIN_DIR, mainDirection.ordinal());
        tagCompound.setInteger(IDLNBTDef.KEY_SIZE_XZ, sizeXZ);
        tagCompound.setInteger(IDLNBTDef.KEY_SIZE_Y, sizeY);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
        for (EnumFacing facing : EnumFacing.values()) {
            if (tagCompound.hasKey(facing.getName())) {
                connections.put(facing, EnumConnection.values()[tagCompound.getInteger(facing.getName())]);
            }
        }
        relPos = NBTUtil.getPosFromTag(tagCompound.getCompoundTag(IDLNBTDef.POS_NAME));
        mainDirection = EnumFacing.values()[tagCompound.getInteger(IDLNBTDef.MAIN_DIR)];
        sizeXZ = tagCompound.getInteger(IDLNBTDef.KEY_SIZE_XZ);
        sizeY = tagCompound.getInteger(IDLNBTDef.KEY_SIZE_Y);
    }

//    float ver
//    protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, float xMin, float yMin, float zMin, float xMax, float yMax, float zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly) {
//        fillWithBlocks(worldIn, boundingboxIn,
//                Math.round(xMin),
//                Math.round(yMin),
//                Math.round(zMin),
//                Math.round(xMax),
//                Math.round(yMax),
//                Math.round(zMax),
//                boundaryBlockState, insideBlockState, existingOnly);
//    }

    //places blocks.
    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        if (ModConfig.DEBUG_CONF.SWITCH_A) {
            buildExteriorWallAndClean(worldIn, randomIn, structureBoundingBoxIn);
        }

        if (!connections.containsKey(EnumFacing.DOWN)) {
            buildFloor(worldIn, randomIn, structureBoundingBoxIn);
        }

        decorateWeb(worldIn, randomIn, structureBoundingBoxIn);
//        decorateCorner(worldIn, randomIn, structureBoundingBoxIn);

        buildConnections(worldIn, randomIn, structureBoundingBoxIn);

        return true;
    }

    void buildFloor(World worldIn, Random randomIn, StructureBoundingBox sbb) {
        fillWithRandomizedBlocks(worldIn, sbb,
                outerSize + 1, outerSize + 1, outerSize + 1, sizeXZ - outerSize - 2, getFloorHeight(), sizeXZ - outerSize - 2, false, randomIn, blockSelectorDecoFloor);
    }

    void decorateWeb(World worldIn, Random randomIn, StructureBoundingBox sbb) {
        //this will not clear other blocks in range.
        generateMaybeBox(worldIn, sbb, randomIn, (float) ModConfig.DUNGEON_CONF.webChance, outerSize + 1, getFloorHeight() + 1, outerSize + 1, sizeXZ - outerSize - 2, sizeY - outerSize - 2, sizeXZ - outerSize - 2, WEB, WEB, false, 0);
    }

    void decorateCorner(World worldIn, Random randomIn, StructureBoundingBox sbb) {
        if (ModConfig.DEBUG_CONF.getInt(2) >= 2) {
            setBlockState(worldIn, getWall2(), 0, 0, 0, sbb);
            setBlockState(worldIn, getWall2(), 0, 0, sizeXZ - 1, sbb);
            setBlockState(worldIn, getWall2(), 0, sizeY - 1, 0, sbb);
            setBlockState(worldIn, getWall2(), 0, sizeY - 1, sizeXZ - 1, sbb);
            setBlockState(worldIn, getWall2(), sizeXZ - 1, 0, 0, sbb);
            setBlockState(worldIn, getWall2(), sizeXZ - 1, 0, sizeXZ - 1, sbb);
            setBlockState(worldIn, getWall2(), sizeXZ - 1, sizeY - 1, 0, sbb);
            setBlockState(worldIn, getWall2(), sizeXZ - 1, sizeY - 1, sizeXZ - 1, sbb);

            int xC = sizeXZ / 2, yC = sizeY / 2, zC = sizeXZ / 2;
            setBlockState(worldIn, getWall2(), xC, yC, zC, sbb);
//            Idealland.Log("center block is:");
//            logPos(worldIn, getWall2(), xC, yC, zC, sbb);
        }
    }

    protected void buildExteriorWallAndClean(World worldIn, Random randomIn, @NotNull StructureBoundingBox sbb) {
        //basic exterior
//        StructureBigDungeon.Start.log("building walls: %s", relPos);

        fillWithBlocks(worldIn, sbb,
                outerSize, outerSize, outerSize, sizeXZ - outerSize - 1, sizeY - outerSize - 1, sizeXZ - outerSize - 1, getWallState(), WorldGenUtil.AIR, false);
    }

//    private int notPassageLength = 3; //including outerSize, xz dir sideways
//    private int notPassageLengthY = 3;//including outerSize. Y Passage, on xz
//    private int floorHeight = 2;  //including outerSize
//    private int xzPassageHeight = 3; //from floor, including roof, not including floor

    protected boolean checkArgumentValidity() {
        if (getNotPassageLengthY() * 2 >= sizeXZ) {
            Idealland.LogWarning("notPassage Y is too big. Force set to 1");
            setNotPassageLengthY(1);
            return false;
        }

        if (getXzPassageHeight() < 3) {
            Idealland.LogWarning("xzPassageHeight is too small for floor height. Auto adjusting");
            setXzPassageHeight(3);
            return false;
        }

        if (getFloorHeight() + getXzPassageHeight() + outerSize >= sizeY) {
            setFloorHeight(outerSize);
            setXzPassageHeight(3);
            Idealland.LogWarning("xz Passage is not valid, too big for this room height. Auto adjusting");
            return false;
        }
        return true;
    }

    protected void buildConnections(World worldIn, Random randomIn, @NotNull StructureBoundingBox sbb) {
        for (EnumFacing facing :
                connections.keySet()) {
//            Idealland.Log("building connectionsMicro %s %s, sbb = %s", relPos, facing, sbb);
            buildConnection(worldIn, randomIn, sbb, facing);
        }
    }

    //note that u have to use this sbb instead of the bounding box of this component.
    void buildConnection(World worldIn, Random randomIn, @NotNull StructureBoundingBox sbb, EnumFacing facing) {
//        StructureBigDungeon.Start.log("connectionsMicro: %s->%s", relPos, facing);
        if (CommonFunctions.isVertical(facing)) {
            int yMin = facing == EnumFacing.DOWN ? 0 : (sizeY - outerSize - 1);
            int yMax = facing == EnumFacing.DOWN ? outerSize : (sizeY - 1);
            int xzMin = getNotPassageLength();
            int xzMax = sizeXZ - getNotPassageLength() - 1;

            for (int x = xzMin; x <= xzMax; x++) {
                for (int z = xzMin; z <= xzMax; z++) {
                    boolean isBoundary = x == xzMin || z == xzMin || x == xzMax || z == xzMax;
                    IBlockState state = isBoundary ? getWall2() : WorldGenUtil.AIR;
                    for (int y = yMin; y <= yMax; y++) {
                        setBlockState(worldIn, state, x, y, z, sbb);
                    }
                }
            }
        } else {
            int yMin = getFloorHeight();
            int yMax = getFloorHeight() + getXzPassageHeight();

            int doorMin = getNotPassageLengthY();
            int doorMax = sizeXZ - getNotPassageLengthY() - 1;

            //Passage depth = outerSize + 1
            boolean isNegative = CommonFunctions.isNegativeDir(facing);

            int depthMin = isNegative ? 0 : (sizeXZ - outerSize - 1);
            int depthMax = isNegative ? (outerSize) : (sizeXZ - 1);

//            StructureBigDungeon.Start.log("connectionsMicro: %d,%d,%d,%d,%d,%d", doorMin, doorMax, yMin, yMax, depthMin, depthMax);

            if (CommonFunctions.isXDir(facing)) {
                for (int z = doorMin; z <= doorMax; z++) {
                    for (int y = yMin; y <= yMax; y++) {
                        boolean isBoundary = y == yMin || z == doorMin || y == yMax || z == doorMax;
                        IBlockState state = isBoundary ? getWall2() : WorldGenUtil.AIR;
                        for (int x = depthMin; x <= depthMax; x++) {
                            setBlockState(worldIn, state, x, y, z, sbb);
                        }
                    }
                }
            } else {
                for (int x = doorMin; x <= doorMax; x++) {
                    for (int y = yMin; y <= yMax; y++) {
                        boolean isBoundary = y == yMin || x == doorMin || y == yMax || x == doorMax;
                        IBlockState state = isBoundary ? getWall2() : WorldGenUtil.AIR;
                        for (int z = depthMin; z <= depthMax; z++) {
                            setBlockState(worldIn, state, x, y, z, sbb);
                        }
                    }
                }
            }
        }
    }

    public void connect(EnumFacing facing, ComponentDungeonRoomBase target) {
        connections.put(facing, EnumConnection.PASS);
        if (target != null) {
            target.connections.put(facing.getOpposite(), EnumConnection.PASS);
//            StructureBigDungeon.Start.log("connecting: %s->%s,%s", relPos, target.relPos, facing);
        } else {
            Idealland.LogWarning("Connection Failed: %s-%s", relPos, facing);
        }
    }

    public IBlockState getWallState() {
        if (ModConfig.DEBUG_CONF.getInt(0) == 2) {
            return GLASS_WHITE;
        }

        return wallState;
    }

    public void setWallState(IBlockState wallState) {
        this.wallState = wallState;
    }

    public IBlockState getWall2() {
        return wall2;
    }

    public void setWall2(IBlockState wall2) {
        this.wall2 = wall2;
    }

    public IBlockState getFloor() {
        return floor;
    }

    public IBlockState getLightDecoFloor(BlockPos pos) {
        int dist = ModConfig.DUNGEON_CONF.stepLightDistance + 1;
        if (pos.getX() % dist == 0 && pos.getZ() % dist == 0) {
            return STEP_LIGHT;
        }
        return getFloor();
    }

    public void setFloor(IBlockState floor) {
        this.floor = floor;
    }

    protected int getNotPassageLength() {
        return ModConfig.DUNGEON_CONF.notPassageLength;
    }

    protected void setNotPassageLength(int notPassageLength) {
        ModConfig.DUNGEON_CONF.notPassageLength = notPassageLength;
    }

    protected int getNotPassageLengthY() {
        return ModConfig.DUNGEON_CONF.notPassageLengthY;
    }

    protected void setNotPassageLengthY(int notPassageLengthY) {
        ModConfig.DUNGEON_CONF.notPassageLengthY = notPassageLengthY;
    }

    protected int getFloorHeight() {
        return ModConfig.DUNGEON_CONF.floorHeight;
    }

    protected void setFloorHeight(int floorHeight) {
        ModConfig.DUNGEON_CONF.floorHeight = floorHeight;
    }

    protected int getXzPassageHeight() {
        return ModConfig.DUNGEON_CONF.xzPassageHeight;
    }

    protected void setXzPassageHeight(int xzPassageHeight) {
        ModConfig.DUNGEON_CONF.xzPassageHeight = xzPassageHeight;
    }

    public static class BlockSelectorDecoFloor extends BlockSelector {
        //caching is ok
        IBlockState normal = Blocks.BRICK_BLOCK.getDefaultState();
        IBlockState light = Blocks.BRICK_BLOCK.getDefaultState();

        IBlockState state = Blocks.BRICK_BLOCK.getDefaultState();

        /**
         * picks Block Ids and Metadata
         */
        public void selectBlocks(Random rand, int x, int y, int z, boolean wall) {
            int dist = ModConfig.DUNGEON_CONF.stepLightDistance + 1;
            if (x % dist == 0 && z % dist == 0) {
                state = light;
            } else {
                state = normal;
            }
        }

        public IBlockState getBlockState() {
            return state;
        }
    }
}
