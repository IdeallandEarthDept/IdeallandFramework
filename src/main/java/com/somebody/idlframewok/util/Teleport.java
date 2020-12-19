package com.somebody.idlframewok.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class Teleport extends Teleporter {
    private final WorldServer worldServer;
    private double x,y,z;

    public Teleport(WorldServer worldServer, double x, double y, double z) {
        super(worldServer);
        this.worldServer = worldServer;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
        worldServer.getBlockState(new BlockPos(x,y,z));
        entityIn.setPosition(x,y,z);
        entityIn.motionX = 0f;
        entityIn.motionY = 0f;
        entityIn.motionZ = 0f;

    }

    public static void teleportToDim(EntityPlayer player, int dimension, double x, double y, double z)
    {
        int oldDim = player.getEntityWorld().provider.getDimension();
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        MinecraftServer server = player.getEntityWorld().getMinecraftServer();
        if (server == null)
        {
            throw  new IllegalArgumentException("Player status incorrect");
        }

        WorldServer worldServerNew = server.getWorld(dimension);
        if (worldServerNew == null)
        {
            throw new IllegalArgumentException(String.format("[IDL]Teleporting dimension: %d does not exist", dimension));
        }

        worldServerNew.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new Teleport(worldServerNew, x,y,z));
        player.setPositionAndUpdate(x,y,z);
    }
}
