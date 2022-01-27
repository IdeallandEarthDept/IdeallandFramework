package com.somebody.idlframewok.entity.creatures.misc.mentor;

import net.minecraft.init.MobEffects;
import net.minecraft.world.World;

public class EntityMentorStalk extends EntityMentorBase {
    public EntityMentorStalk(World worldIn) {
        super(worldIn);
        giftPotion = MobEffects.INVISIBILITY;
        attackPotion = MobEffects.GLOWING;
    }
}
