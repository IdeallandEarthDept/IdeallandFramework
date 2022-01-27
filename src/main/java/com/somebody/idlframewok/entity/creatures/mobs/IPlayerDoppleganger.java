package com.somebody.idlframewok.entity.creatures.mobs;

import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public interface IPlayerDoppleganger {
    public UUID getPlayerUUID();
    public EntityPlayer getPlayer();
}
