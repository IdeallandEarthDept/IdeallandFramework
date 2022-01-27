package com.somebody.idlframewok.entity.creatures;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.Entity;

import static com.somebody.idlframewok.entity.creatures.EntityModUnit.FACTION_TEAM;

public interface ICustomFaction {
    default EntityUtil.EnumFaction getFaction() {
        if (this instanceof EntityModUnit) {
            return EntityUtil.EnumFaction.fromIndex(((Entity) this).getDataManager().get(FACTION_TEAM));
        }
        return EntityUtil.EnumFaction.CRITTER;
    }

    default void setFaction(Byte index) {
        setFaction(EntityUtil.EnumFaction.fromIndex(index));
    }

    default void setFaction(EntityUtil.EnumFaction faction) {
        if (this instanceof EntityModUnit) {
            ((Entity) this).getDataManager().set(FACTION_TEAM, faction.index);

            ((EntityModUnit) this).resetAttackFaction();
        } else {
            Idealland.LogWarning("Trying to set faction for wrong type");
        }
    }
}
