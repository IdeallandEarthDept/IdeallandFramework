package com.somebody.idlframewok.entity.creatures.misc.mentor;

import net.minecraft.init.MobEffects;
import net.minecraft.world.World;

public class EntityMentorEye extends EntityMentorBase {
    public EntityMentorEye(World worldIn) {
        super(worldIn);
        giftPotion = MobEffects.NIGHT_VISION;
        attackPotion = MobEffects.BLINDNESS;
    }
}
