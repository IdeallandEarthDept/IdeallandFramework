package com.somebody.idlframewok.entity.creatures.mobs;

import com.somebody.idlframewok.entity.creatures.EntityGeneralMob;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityDemonGodPillar extends EntityGeneralMob {
    public EntityDemonGodPillar(World worldIn) {
        super(worldIn);
        setSize(3f, 16f);
        is_pinned_on_ground = true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.IN_WALL || source == DamageSource.DROWN) {
            return false;
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        setAttr(32f, 0.1f, 20f, 5f, 1024f);
    }
}
