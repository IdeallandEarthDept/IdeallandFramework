package com.somebody.idlframewok.blocks.uprising.citadel.util;

import com.somebody.idlframewok.blocks.uprising.citadel.ICitadelBuilding;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static com.somebody.idlframewok.util.EntityUtil.IS_CITADEL_BUILDING;

public class CitadelUtil {
    //the position is of the zone itself. Not above it.
    public static EnumSingleZoneState checkZoneState(World worldIn, BlockPos pos) {
        List<EntityLivingBase> livingBases = EntityUtil.getEntitiesWithinAABB(worldIn, EntityLivingBase.class, pos.add(0, 1, 0), 0.5f, IS_CITADEL_BUILDING);
        if (livingBases.size() == 0) {
            return EnumSingleZoneState.CLEAR;
        } else {
            //It normally won't be possible to have two types of buildings at once.
            ICitadelBuilding building = (ICitadelBuilding) livingBases.get(0);
            if (!building.isPlayerTeam()) {
                return EnumSingleZoneState.HAS_ENEMY_BUILDING;
            } else {
                return EnumSingleZoneState.HAS_PLAYER_BUILDING;
            }
        }
    }

    enum EnumZoneState {
        NONE,
        EMPTY_BUT_ENEMY,
        EMPTY_AND_OURS,
        OCCUPIED_BUT_ENEMY,
        OCUUPIED_AND_SELF
    }

    public enum EnumSingleZoneState {
        CLEAR,
        HAS_PLAYER_BUILDING,
        HAS_ENEMY_BUILDING,
        ERR_NO_CITADEL
    }
}
