package com.somebody.idlframewok.entity.creatures.moroon;

import net.minecraft.world.World;

public class EntityMorTainterSP extends EntityMoroonTainter {
    public EntityMorTainterSP(World worldIn) {
        super(worldIn);
        spawn_without_moroon_ground = true;
        spawn_without_darkness = true;
    }
}
