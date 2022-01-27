package com.somebody.idlframewok.blocks.uprising.citadel;

import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.TEAM_MARK;

public interface ICitadelBuilding {
    default boolean isPlayerTeam()
    {
        if (this instanceof EntityLivingBase)
        {
            return IDLNBTUtil.GetInt((Entity) this, TEAM_MARK, UprisingDef.ENEMY_TEAM) == UprisingDef.PLAYER_TEAM;
        }
        return false;
    }
}
