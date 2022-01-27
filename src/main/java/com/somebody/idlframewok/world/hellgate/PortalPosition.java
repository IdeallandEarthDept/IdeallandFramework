package com.somebody.idlframewok.world.hellgate;

import net.minecraft.util.math.BlockPos;

public class PortalPosition extends BlockPos {
    /**
     * The worldtime at which this PortalPosition was last verified
     */
    public long lastUpdateTime;

    public PortalPosition(BlockPos pos, long lastUpdate) {
        super(pos.getX(), pos.getY(), pos.getZ());
        this.lastUpdateTime = lastUpdate;
    }
}
