package com.somebody.idlframewok.entity.creatures.misc.mentor;

import com.somebody.idlframewok.potion.ModPotions;
import net.minecraft.init.MobEffects;
import net.minecraft.world.World;

public class EntityMentorFire extends EntityMentorBase {
    public EntityMentorFire(World worldIn) {
        super(worldIn);
        giftPotion = MobEffects.FIRE_RESISTANCE;
        attackPotion = ModPotions.BURN;
    }
}
