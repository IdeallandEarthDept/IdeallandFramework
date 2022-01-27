package com.somebody.idlframewok.entity.creatures.misc.mentor;

import net.minecraft.init.MobEffects;
import net.minecraft.world.World;

public class EntityMentorLife extends EntityMentorBase {
    public EntityMentorLife(World worldIn) {
        super(worldIn);
        giftPotion = MobEffects.REGENERATION;
        attackPotion = MobEffects.POISON;
    }
}
