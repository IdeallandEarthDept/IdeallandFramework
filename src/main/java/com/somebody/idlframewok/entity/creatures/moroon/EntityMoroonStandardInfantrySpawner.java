package com.somebody.idlframewok.entity.creatures.moroon;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.world.World;

public class EntityMoroonStandardInfantrySpawner extends EntityMoroonStandardInfantry implements IRangedAttackMob {

    public EntityMoroonStandardInfantrySpawner(World worldIn) {
        super(worldIn);
        spawn_without_moroon_ground = true;
        spawn_without_darkness = true;
    }
}
