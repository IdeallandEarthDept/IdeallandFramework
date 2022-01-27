package com.somebody.idlframewok.world.worldgen.structurebig;

import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.*;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.KEY_DUMMY;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.STATE;

public class Maze3d {
    Random random = new Random();

    int xCellCount = 3;
    int yCellCount = 2;
    int zCellCount = 3;

    //grammar
    public Maze3d() {
        createCells();
    }

    public Maze3d(int xCellCount, int yCellCount, int zCellCount) {
        this.xCellCount = xCellCount;
        this.yCellCount = yCellCount;
        this.zCellCount = zCellCount;
        createCells();
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setCellCount(int x, int y, int z)
    {
        xCellCount = x;
        yCellCount = y;
        zCellCount = z;
    }

    int curIndex = 0;

    @Nullable
    public MicroCell getCell(BlockPos pos)
    {
        return cells.get(pos);
    }

    public void createMaze() {
        unconnected = new ArrayList<>();
        unconnected.addAll(cells.values());

        markStart();
        genStack.push(BlockPos.ORIGIN);
        while (!genStack.empty() && !unconnected.isEmpty()) {
            handle(genStack.pop());
        }
    }

    public void writeStructureToNBT(NBTTagCompound tagCompound) {

        NBTTagList list = new NBTTagList();
        for (BlockPos pos :
                cells.keySet()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setLong(IDLNBTDef.POS_NAME, pos.toLong());
            Maze3d.MicroCell cell = cells.get(pos);
            compound.setInteger(IDLNBTDef.STATE, cell.index);
            for (EnumFacing facing : cell.connectionsMicro.keySet()) {
                tagCompound.setInteger(facing.getName(), cell.connectionsMicro.get(facing).ordinal());
            }
            list.appendTag(compound);
        }
        tagCompound.setTag(KEY_DUMMY, list);
    }

    public void readStructureFromNBT(NBTTagCompound tagCompound) {
        NBTTagList list = tagCompound.getTagList(KEY_DUMMY, 10);
        for (NBTBase base : list) {
            if (base instanceof NBTTagCompound) {
                NBTTagCompound cellData = (NBTTagCompound) base;
                BlockPos pos = BlockPos.fromLong(cellData.getLong(IDLNBTDef.POS_NAME));
                MicroCell cell = cells.get(pos);
                for (EnumFacing facing : EnumFacing.values()) {
                    if (cellData.hasKey(facing.getName())) {
                        cell.connectionsMicro.put(facing, EnumConnection.values()[tagCompound.getInteger(facing.getName())]);
                    }
                }
                cell.index = cellData.getInteger(STATE);
            }
        }
    }

    public HashMap<BlockPos, Maze3d.MicroCell> cells = new HashMap<>();

    void createCells() {
        for (int x = 0; x < xCellCount; x++) {
            for (int y = 0; y < yCellCount; y++) {
                for (int z = 0; z < zCellCount; z++) {
                    cells.put(new BlockPos(x, y, z), new Maze3d.MicroCell());
                }
            }
        }
    }

    void markStart() {
        cells.get(BlockPos.ORIGIN).connectivity = 1;
        cells.get(BlockPos.ORIGIN).index = 0;
//        for (EnumFacing facing:
//             connectionsMicro.keySet()) {
//            EnumConnection connectionThis = connectionsMicro.get(facing);
//            if (connectionThis != EnumConnection.NONE)
//            {
//                cells.get(new BlockPos(facing.getFrontOffsetX(),0,facing.getFrontOffsetZ())).connectivity = 1;
//            }
//        }
    }

    ArrayList<Maze3d.MicroCell> unconnected;
    Stack<BlockPos> genStack = new Stack<>();
    ArrayList<EnumFacing> adjacentUnconnected = new ArrayList<>();

    void handle(BlockPos cur) {
        adjacentUnconnected.clear();

        BlockPos.MutableBlockPos curOffset = new BlockPos.MutableBlockPos(cur);
        for (EnumFacing facing :
                EnumFacing.values()) {
            curOffset.setPos(cur);
            curOffset = curOffset.move(facing);
            //For each direction, find corresponding adj room if it exists.
            if (cells.containsKey(curOffset)) {
                Maze3d.MicroCell cellAdj = cells.get(curOffset);
                if (cellAdj.connectivity <= 0) {
                    adjacentUnconnected.add(facing);
                }
            }
        }

        //choose a random unvisited if it exists
        if (!adjacentUnconnected.isEmpty()) {
            int i = random.nextInt(adjacentUnconnected.size());
            genStack.push(cur);

            EnumFacing facing = adjacentUnconnected.get(i);
            BlockPos posAdj = cur.offset(facing);
            Maze3d.MicroCell cell = cells.get(cur);
            Maze3d.MicroCell cellAdj = cells.get(posAdj);
            cell.connect(facing, EnumConnection.PASS, cellAdj);
            curIndex++;
            cell.index = curIndex;

            genStack.push(posAdj);
        }
    }

    public class MicroCell {
        public HashMap<EnumFacing, EnumConnection> connectionsMicro = new HashMap<>();
        public int connectivity = 0;//0=unconnected, 1=connected
        public int index = 0;

        public void connect(EnumFacing facing, EnumConnection type, @Nullable Maze3d.MicroCell other) {
            connectionsMicro.put(facing, type);
            if (other != null) {
                other.connectionsMicro.put(facing.getOpposite(), type);
                if (type != EnumConnection.NONE) {
                    int thisConnectivity = connectivity;
                    int otherConnectivity = other.connectivity;
                    if (thisConnectivity != otherConnectivity) {
                        int max = Math.max(thisConnectivity, otherConnectivity);
                        connectivity = max;
                        other.connectivity = max;
                        if (connectivity > 0) {
                            unconnected.remove(this);
                            unconnected.remove(other);
                        }
                    }
                }
            }
        }
    }
}
