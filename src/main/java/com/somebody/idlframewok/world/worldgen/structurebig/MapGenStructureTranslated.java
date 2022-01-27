package com.somebody.idlframewok.world.worldgen.structurebig;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.*;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Random;

public abstract class MapGenStructureTranslated extends MapGenBase {
    private MapGenStructureData structureData;
    /**
     * Used to store a list of all structures that have been recursively generated. Used so that during recursive
     * generation, the structurebig generator can avoid generating structures that intersect ones that have already been
     * placed.
     */
    protected Long2ObjectMap<StructureStart> structureMap = new Long2ObjectOpenHashMap<StructureStart>(1024);

    public abstract String getStructureName();

    /**
     * Recursively called by generate()
     */
    protected final synchronized void recursiveGenerate(World worldIn, final int chunkX, final int chunkZ, int originalX, int originalZ, ChunkPrimer chunkPrimerIn) {
        this.initializeStructureData(worldIn);

        if (!this.structureMap.containsKey(ChunkPos.asLong(chunkX, chunkZ))) {
            this.rand.nextInt();

            try {
                if (this.canSpawnStructureAtCoords(chunkX, chunkZ)) {
                    StructureStart structurestart = this.getStructureStart(chunkX, chunkZ);
                    this.structureMap.put(ChunkPos.asLong(chunkX, chunkZ), structurestart);

                    if (structurestart.isSizeableStructure()) {
                        this.setStructureStart(chunkX, chunkZ, structurestart);
                    }
                }
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception preparing structurebig feature");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Feature being prepared");
                crashreportcategory.addDetail("Is feature chunk", new ICrashReportDetail<String>() {
                    public String call() throws Exception {
                        return MapGenStructureTranslated.this.canSpawnStructureAtCoords(chunkX, chunkZ) ? "True" : "False";
                    }
                });
                crashreportcategory.addCrashSection("Chunk location", String.format("%d,%d", chunkX, chunkZ));
                crashreportcategory.addDetail("Chunk pos hash", new ICrashReportDetail<String>() {
                    public String call() throws Exception {
                        return String.valueOf(ChunkPos.asLong(chunkX, chunkZ));
                    }
                });
                crashreportcategory.addDetail("Structure type", new ICrashReportDetail<String>() {
                    public String call() throws Exception {
                        return MapGenStructureTranslated.this.getClass().getCanonicalName();
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
    }

    public synchronized boolean generateStructure(World worldIn, Random randomIn, ChunkPos chunkCoord) {
        this.initializeStructureData(worldIn);
        int i = (chunkCoord.x << 4) + 8;
        int j = (chunkCoord.z << 4) + 8;
        boolean flag = false;
        ObjectIterator objectiterator = this.structureMap.values().iterator();

        while (objectiterator.hasNext()) {
            StructureStart structurestart = (StructureStart) objectiterator.next();

            if (structurestart.isSizeableStructure() && structurestart.isValidForPostProcess(chunkCoord) && structurestart.getBoundingBox().intersectsWith(i, j, i + 15, j + 15)) {
                structurestart.generateStructure(worldIn, randomIn, new StructureBoundingBox(i, j, i + 15, j + 15));
                structurestart.notifyPostProcessAt(chunkCoord);
                flag = true;
                this.setStructureStart(structurestart.getChunkPosX(), structurestart.getChunkPosZ(), structurestart);
            }
        }

        return flag;
    }

    public boolean isInsideStructure(BlockPos pos) {
        if (this.world == null) {
            return false;
        } else {
            this.initializeStructureData(this.world);
            return this.getStructureAt(pos) != null;
        }
    }

    @Nullable
    protected StructureStart getStructureAt(BlockPos pos) {
        ObjectIterator objectiterator = this.structureMap.values().iterator();
        label31:

        while (objectiterator.hasNext()) {
            StructureStart structurestart = (StructureStart) objectiterator.next();

            if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().isVecInside(pos)) {
                Iterator<StructureComponent> iterator = structurestart.getComponents().iterator();

                while (true) {
                    if (!iterator.hasNext()) {
                        continue label31;
                    }

                    StructureComponent structurecomponent = iterator.next();

                    if (structurecomponent.getBoundingBox().isVecInside(pos)) {
                        break;
                    }
                }

                return structurestart;
            }
        }

        return null;
    }

    public boolean isPositionInStructure(World worldIn, BlockPos pos) {
        this.initializeStructureData(worldIn);
        ObjectIterator objectiterator = this.structureMap.values().iterator();

        while (objectiterator.hasNext()) {
            StructureStart structurestart = (StructureStart) objectiterator.next();

            if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().isVecInside(pos)) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public abstract BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored);

    protected void initializeStructureData(World worldIn) {
        if (this.structureData == null && worldIn != null) {
            this.structureData = (MapGenStructureData) worldIn.getPerWorldStorage().getOrLoadData(MapGenStructureData.class, this.getStructureName());

            if (this.structureData == null) {
                this.structureData = new MapGenStructureData(this.getStructureName());
                worldIn.getPerWorldStorage().setData(this.getStructureName(), this.structureData);
            } else {
                NBTTagCompound nbttagcompound = this.structureData.getTagCompound();

                for (String s : nbttagcompound.getKeySet()) {
                    NBTBase nbtbase = nbttagcompound.getTag(s);

                    if (nbtbase.getId() == 10) {
                        NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbtbase;

                        if (nbttagcompound1.hasKey("ChunkX") && nbttagcompound1.hasKey("ChunkZ")) {
                            int i = nbttagcompound1.getInteger("ChunkX");
                            int j = nbttagcompound1.getInteger("ChunkZ");
                            StructureStart structurestart = MapGenStructureIO.getStructureStart(nbttagcompound1, worldIn);

                            if (structurestart != null) {
                                this.structureMap.put(ChunkPos.asLong(i, j), structurestart);
                            }
                        }
                    }
                }
            }
        }
    }

    private void setStructureStart(int chunkX, int chunkZ, StructureStart start) {
        this.structureData.writeInstance(start.writeStructureComponentsToNBT(chunkX, chunkZ), chunkX, chunkZ);
        this.structureData.markDirty();
    }

    protected abstract boolean canSpawnStructureAtCoords(int chunkX, int chunkZ);

    protected abstract StructureStart getStructureStart(int chunkX, int chunkZ);

    protected static BlockPos findNearestStructurePosBySpacing(World worldIn, MapGenStructureTranslated genStructure, BlockPos pos, int max_dist, int min_dist, int seedFactor, boolean useSmoothFinding, int range, boolean findUnexplored) {
        int xChunkPosStart = pos.getX() >> 4;
        int zChunkPosStart = pos.getZ() >> 4;
        int index = 0;

        for (Random random = new Random(); index <= range; ++index) {
            for (int xGroup = -index; xGroup <= index; ++xGroup) {
                boolean xAtBound = xGroup == -index || xGroup == index;

                for (int zGroup = -index; zGroup <= index; ++zGroup) {
                    boolean zAtBound = zGroup == -index || zGroup == index;

                    if (xAtBound || zAtBound) {
                        int xChunkPos = xChunkPosStart + max_dist * xGroup;
                        int zChunkPos = zChunkPosStart + max_dist * zGroup;

                        if (xChunkPos < 0) {
                            xChunkPos -= max_dist - 1;
                        }

                        if (zChunkPos < 0) {
                            zChunkPos -= max_dist - 1;
                        }

                        int xGroupPos = xChunkPos / max_dist;
                        int zGroupPos = zChunkPos / max_dist;
                        Random random1 = worldIn.setRandomSeed(xGroupPos, zGroupPos, seedFactor);
                        xGroupPos = xGroupPos * max_dist;
                        zGroupPos = zGroupPos * max_dist;
                        //from now on, groupPos becomesChunkPos. Here I rename it to make it more readable.
                        xChunkPos = xGroupPos;
                        zChunkPos = zGroupPos;

                        if (useSmoothFinding) {
                            xChunkPos = xChunkPos + (random1.nextInt(max_dist - min_dist) + random1.nextInt(max_dist - min_dist)) / 2;
                            zChunkPos = zChunkPos + (random1.nextInt(max_dist - min_dist) + random1.nextInt(max_dist - min_dist)) / 2;
                        } else {
                            xChunkPos = xChunkPos + random1.nextInt(max_dist - min_dist);
                            zChunkPos = zChunkPos + random1.nextInt(max_dist - min_dist);
                        }

                        MapGenBase.setupChunkSeed(worldIn.getSeed(), random, xChunkPos, zChunkPos);
                        random.nextInt();

                        if (genStructure.canSpawnStructureAtCoords(xChunkPos, zChunkPos)) {
                            if (!findUnexplored || !worldIn.isChunkGeneratedAt(xChunkPos, zChunkPos)) {
                                return new BlockPos((xChunkPos << 4) + 8, 64, (zChunkPos << 4) + 8);
                            }
                        } else if (index == 0) {
                            break;
                        }
                    }
                }

                if (index == 0) {
                    break;
                }
            }
        }

        return null;
    }

}
