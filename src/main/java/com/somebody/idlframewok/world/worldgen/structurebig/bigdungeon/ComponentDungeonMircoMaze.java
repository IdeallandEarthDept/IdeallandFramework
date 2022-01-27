package com.somebody.idlframewok.world.worldgen.structurebig.bigdungeon;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.WorldGenUtil;
import com.somebody.idlframewok.world.worldgen.structurebig.Maze3d;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class ComponentDungeonMircoMaze extends ComponentDungeonRoomBase {

    Maze3d maze3d = new Maze3d();

    //grammar
    public ComponentDungeonMircoMaze() {
        setCoordBaseMode(EnumFacing.SOUTH);
    }

    public ComponentDungeonMircoMaze(int type, EnumFacing mainDirection, Vec3i vec3i, StructureBigDungeon.Start start) {
        super(type, mainDirection, vec3i, start);
    }

    @Override
    public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
        super.buildComponent(componentIn, listIn, rand);
        maze3d.setRandom(rand);
        maze3d.createMaze();
    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        boolean result = super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn);
        if (ModConfig.DUNGEON_CONF.debugBuild) {
            testBuild(worldIn, randomIn, structureBoundingBoxIn);
        }
        return result;
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound) {
        super.writeStructureToNBT(tagCompound);
        maze3d.writeStructureToNBT(tagCompound);
//        NBTTagList list = new NBTTagList();
//        for (BlockPos pos :
//                maze3d.cells.keySet()) {
//            NBTTagCompound compound = new NBTTagCompound();
//
//            compound.setLong(IDLNBTDef.POS_NAME, pos.toLong());
//            Maze3d.MicroCell cell = maze3d.cells.get(pos);
//            for (EnumFacing facing : cell.connectionsMicro.keySet()) {
//                tagCompound.setInteger(facing.getName(), cell.connectionsMicro.get(facing).ordinal());
//            }
//
//            list.appendTag(compound);
//        }
//        tagCompound.setTag(KEY_DUMMY, list);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
        super.readStructureFromNBT(tagCompound, p_143011_2_);
        maze3d.readStructureFromNBT(tagCompound);
//        NBTTagList list = tagCompound.getTagList(KEY_DUMMY, 10);
//        while (list.iterator().hasNext()) {
//            NBTBase base = list.iterator().next();
//            if (base instanceof NBTTagCompound) {
//                NBTTagCompound cellData = (NBTTagCompound) base;
//                BlockPos pos = BlockPos.fromLong(cellData.getLong(IDLNBTDef.POS_NAME));
//                Maze3d.MicroCell cell = maze3d.cells.get(pos);
//                for (EnumFacing facing : EnumFacing.values()) {
//                    if (cellData.hasKey(facing.getName())) {
//                        cell.connectionsMicro.put(facing, EnumConnection.values()[tagCompound.getInteger(facing.getName())]);
//                    }
//                }
//            }
//        }
    }

    void testBuild(World worldIn, Random randomIn, StructureBoundingBox sbb) {
        Idealland.Log("testbuild %s. cell count = %s", relPos, maze3d.cells);
        for (BlockPos pos :
                maze3d.cells.keySet()) {
            BlockPos actualPos = new BlockPos(6 + pos.getX() * 3, 3 + pos.getY() * 3, 6 + pos.getZ() * 3);
            //cell core
            setBlockState(worldIn, WorldGenUtil.BEDROCK, actualPos.getX(), actualPos.getY(), actualPos.getZ(), sbb);

            //cell connection
            Maze3d.MicroCell cell = maze3d.cells.get(pos);
            for (EnumFacing facing : cell.connectionsMicro.keySet()) {
                BlockPos toBuildPos = actualPos.offset(facing);
                setBlockState(worldIn, WorldGenUtil.BEDROCK, toBuildPos.getX(), toBuildPos.getY(), toBuildPos.getZ(), sbb);
            }
        }
    }


    //------------------------
//    Random random = new Random();
//
//    int xCellCount = 3;
//    int yCellCount = 2;
//    int zCellCount = 3;
//
//    public void setRandom(Random random) {
//        this.random = random;
//    }
//
//    public void createMaze() {
//        unconnected = new ArrayList<>();
//        unconnected.addAll(cells.values());
//
//        markStart();
//        genStack.push(BlockPos.ORIGIN);
//        while (!genStack.empty()) {
//            handle(genStack.pop());
//        }
//    }
////
////    public void writeStructureToNBT(NBTTagCompound tagCompound) {
////
////        NBTTagList list = new NBTTagList();
////        for (BlockPos pos :
////                cells.keySet()) {
////            NBTTagCompound compound = new NBTTagCompound();
////
////            compound.setLong(IDLNBTDef.POS_NAME, pos.toLong());
////            ComponentDungeonMircoMaze.MicroCell cell = cells.get(pos);
////            for (EnumFacing facing : cell.connectionsMicro.keySet()) {
////                tagCompound.setInteger(facing.getName(), cell.connectionsMicro.get(facing).ordinal());
////            }
////
////            list.appendTag(compound);
////        }
////        tagCompound.setTag(KEY_DUMMY, list);
////    }
////
////    public void readStructureFromNBT(NBTTagCompound tagCompound) {
////        NBTTagList list = tagCompound.getTagList(KEY_DUMMY, 10);
////        while (list.iterator().hasNext())
////        {
////            NBTBase base = list.iterator().next();
////            if (base instanceof NBTTagCompound)
////            {
////                NBTTagCompound cellData = (NBTTagCompound) base;
////                BlockPos pos = BlockPos.fromLong(cellData.getLong(IDLNBTDef.POS_NAME));
////                ComponentDungeonMircoMaze.MicroCell cell = cells.get(pos);
////                for (EnumFacing facing : EnumFacing.values()) {
////                    if (cellData.hasKey(facing.getName())) {
////                        cell.connectionsMicro.put(facing, EnumConnection.values()[tagCompound.getInteger(facing.getName())]);
////                    }
////                }
////            }
////        }
////    }
//
//    public HashMap<BlockPos, ComponentDungeonMircoMaze.MicroCell> cells = new HashMap<>();
//
//    void createCells() {
//        for (int x = 0; x < xCellCount; x++) {
//            for (int y = 0; y < yCellCount; y++) {
//                for (int z = 0; z < zCellCount; z++) {
//                    cells.put(new BlockPos(x, y, z), new ComponentDungeonMircoMaze.MicroCell());
//                }
//            }
//        }
//    }
//
//    void markStart() {
//        cells.get(BlockPos.ORIGIN).connectivity = 1;
////        for (EnumFacing facing:
////             connectionsMicro.keySet()) {
////            EnumConnection connectionThis = connectionsMicro.get(facing);
////            if (connectionThis != EnumConnection.NONE)
////            {
////                cells.get(new BlockPos(facing.getFrontOffsetX(),0,facing.getFrontOffsetZ())).connectivity = 1;
////            }
////        }
//    }
//
//    ArrayList<MicroCell> unconnected;
//    Stack<BlockPos> genStack = new Stack<>();
//    ArrayList<EnumFacing> adjacentUnconnected = new ArrayList<>();
//
//    void handle(BlockPos cur) {
//        adjacentUnconnected.clear();
//
//        BlockPos.MutableBlockPos curOffset = new BlockPos.MutableBlockPos(cur);
//        for (EnumFacing facing :
//                EnumFacing.values()) {
//            curOffset.setPos(cur);
//            curOffset = curOffset.move(facing);
//            //For each direction, find corresponding adj room if it exists.
//            if (cells.containsKey(curOffset)) {
//                MicroCell cellAdj = cells.get(curOffset);
//                if (cellAdj.connectivity <= 0) {
//                    adjacentUnconnected.add(facing);
//                }
//            }
//        }
//
//        //choose a random unvisited if it exists
//        if (!adjacentUnconnected.isEmpty()) {
//            int i = random.nextInt(adjacentUnconnected.size());
//            genStack.push(cur);
//
//            EnumFacing facing = adjacentUnconnected.get(i);
//            BlockPos posAdj = cur.offset(facing);
//            MicroCell cell = cells.get(cur);
//            MicroCell cellAdj = cells.get(posAdj);
//            cell.connect(facing, EnumConnection.PASS, cellAdj);
//            genStack.push(posAdj);
//        }
//    }
//
//    public class MicroCell {
//        public HashMap<EnumFacing, EnumConnection> connectionsMicro = new HashMap<>();
//        public int connectivity = 0;//0=unconnected, 1=connected
//
//        public void connect(EnumFacing facing, EnumConnection type, @Nullable MicroCell other) {
//            connectionsMicro.put(facing, type);
//            if (other != null) {
//                other.connectionsMicro.put(facing.getOpposite(), type);
//                if (type != EnumConnection.NONE) {
//                    int thisConnectivity = connectivity;
//                    int otherConnectivity = other.connectivity;
//                    if (thisConnectivity != otherConnectivity) {
//                        int max = Math.max(thisConnectivity, otherConnectivity);
//                        connectivity = max;
//                        other.connectivity = max;
//                        if (connectivity > 0) {
//                            unconnected.remove(this);
//                            unconnected.remove(other);
//                        }
//                    }
//                }
//            }
//        }
//    }
}
