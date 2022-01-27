package com.somebody.idlframewok.entity.creatures.misc.mentor;

import net.minecraft.init.MobEffects;
import net.minecraft.world.World;

public class EntityMentorHaste extends EntityMentorBase {
    public EntityMentorHaste(World worldIn) {
        super(worldIn);
        giftPotion = MobEffects.HASTE;
        attackPotion = MobEffects.MINING_FATIGUE;
    }
}
