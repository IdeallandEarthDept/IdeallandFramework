package com.somebody.idlframewok.util;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import static com.somebody.idlframewok.util.MessageDef.TP_BADLUCK;

public class Teleport implements net.minecraftforge.common.util.ITeleporter {
    enum TeleportMode{
        FORCED,
        FIND_GROUND
    }

    private final WorldServer worldServer;
    private double x,y,z;

    public Teleport(WorldServer worldServer, double x, double y, double z) {
        this.worldServer = worldServer;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    static void exceptionOrLog(boolean throwException, String msg)
    {
        if (throwException)
        {
            throw new IllegalArgumentException(msg);
        }
        else {
            Idealland.LogWarning(msg);
        }
    }

    public static void teleportPlayerToDim(EntityPlayer player, int dimension, double x, double y, double z, boolean throwExecption)
    {
        teleportToDim(player, dimension, x, y, z, throwExecption, TeleportMode.FIND_GROUND);
    }

    public static void teleportPlayerToDim(EntityPlayer player, World dimension, double x, double y, double z, boolean throwExecption)
    {
        teleportPlayerToDim(player, dimension.provider.getDimensionType().getId(), x, y, z, throwExecption);
    }

    public static void teleportToDim(Entity entity, int dimension, double x, double y, double z)
    {
        teleportToDim(entity, dimension, x, y, z, false, TeleportMode.FIND_GROUND);
    }

    public static void teleportToDim(Entity entity, int dimension, double x, double y, double z, boolean throwExecption, TeleportMode mode)
    {
        boolean isPlayer = entity instanceof EntityPlayerMP;
        MinecraftServer server = entity.getEntityWorld().getMinecraftServer();

        if (server == null)
        {
            if (isPlayer)
            {
                exceptionOrLog(throwExecption, "Player status incorrect");
            }
            else {
                Idealland.LogWarning("Teleport fail: %s status incorrect", entity.getName());
            }
            return;
        }

        WorldServer worldServerNew = server.getWorld(dimension);
        if (worldServerNew == null)
        {
            if (isPlayer)
            {
                exceptionOrLog(throwExecption, String.format("[IDL]Teleporting dimension failed: %d does not exist", dimension));
            }
            else {
                Idealland.LogWarning("[IDL]Teleporting dimension failed: %d does not exist, from %s", dimension, entity.getName());
            }
            return;
        }

        y = findGroundXYZ(worldServerNew, (int)x, (int)y, (int)z, entity).getY();

        if (isPlayer)
        {
            worldServerNew.getMinecraftServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP) entity, dimension, new Teleport(worldServerNew, x,y,z));
        }
        else {
            WorldServer worldServerOld = (WorldServer) entity.world;
            worldServerNew.getMinecraftServer().getPlayerList().transferEntityToWorld(
                    entity,
                    worldServerOld.provider.getDimensionType().getId(),
                    worldServerOld,
                    worldServerNew,
                    new Teleport(worldServerNew, x,y,z));
        }

        entity.setPositionAndUpdate(x,y,z);
        Idealland.Log("TP %s to %s for teleportation", entity.getName(), new BlockPos(x, y, z));
    }

    static BlockPos findGroundXYZ(World world, int x, int y, int z, Entity entity)
    {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        boolean ok = false;
        int yAir = -1;
        for (y = world.getActualHeight() - 1; y >= 0; --y) {
            //first we find a block of air
            if (WorldGenUtil.notSolid(world, blockpos$mutableblockpos.setPos(x, y, z))) {

                //then we find a block of GROUND under the air
                //two blocks to be exact, for handling a player's size
                y--;
                while (y > 0 && WorldGenUtil.notSolid(world, blockpos$mutableblockpos.setPos(x, y - 1, z))) {
                    yAir = y;
                    y--;
                }

                if (y > 0)
                {
                    ok = true;
                    break;
                }
            }
        }

        if (!ok)
        {
            if (yAir < 0)
            {
                //found no air block
                return new BlockPos(x,world.getActualHeight()+1, z);
            }
            else {

                //found a air block, but not high enough or no GROUND under it
                world.setBlockState(blockpos$mutableblockpos.setPos(x, yAir + 1, z), Blocks.AIR.getDefaultState());
                world.setBlockState(blockpos$mutableblockpos.setPos(x, yAir, z), Blocks.AIR.getDefaultState());

                for (int dx = -1; dx <= 1; dx++)
                {
                    for (int dz = -1; dz <= 1; dz++)
                    {
                        world.setBlockState(blockpos$mutableblockpos.setPos(x + dx, yAir - 1, z + dz), Blocks.DIRT.getDefaultState());
                    }
                }

                world.setBlockState(blockpos$mutableblockpos.setPos(x, yAir - 1, z), ModBlocks.RANDOM_TP.getDefaultState());



                Idealland.LogWarning("Set %s to random_tp for teleportation", blockpos$mutableblockpos);
                if (entity instanceof EntityPlayerMP)
                {
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, (EntityLivingBase) entity, TP_BADLUCK);
                }
                return new BlockPos(x,yAir, z);
            }
        }
        return new BlockPos(x, y, z);
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        worldServer.getBlockState(new BlockPos(x,y,z));
        entity.setPosition(x,y,z);
        entity.motionX = 0f;
        entity.motionY = 0f;
        entity.motionZ = 0f;
    }
}
