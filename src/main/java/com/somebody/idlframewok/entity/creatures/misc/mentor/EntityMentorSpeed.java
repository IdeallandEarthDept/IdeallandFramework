package com.somebody.idlframewok.entity.creatures.misc.mentor;

import net.minecraft.init.MobEffects;
import net.minecraft.world.World;

public class EntityMentorSpeed extends EntityMentorBase {
    public EntityMentorSpeed(World worldIn) {
        super(worldIn);
        giftPotion = MobEffects.SPEED;
        attackPotion = MobEffects.SLOWNESS;
    }
}
