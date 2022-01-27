package com.somebody.idlframewok.blocks.tileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityBase extends TileEntity {
    public boolean isReady = false;//this only works at server
    public void onLoad()
    {
        init();
    }

    public Vec3d getPosInFloat()
    {
        BlockPos myPos = this.pos;
        Vec3d posInFloat = new Vec3d(myPos.getX() + 0.5, myPos.getY() + 0.5, myPos.getZ() + 0.5);
        return posInFloat;
    }

    public void init()
    {
        MinecraftForge.EVENT_BUS.register(this);
        isReady = true;
    }

    @Override
    public void invalidate() {
        MinecraftForge.EVENT_BUS.unregister(this);
        super.invalidate();
    }

    public void playsoundhere(SoundEvent soundEvent)
    {
        world.playSound(null, this.pos, soundEvent, SoundCategory.BLOCKS, 0.3F, 1f);
    }

}
