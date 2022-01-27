package com.somebody.idlframewok.entity.creatures.misc.mentor;

import com.somebody.idlframewok.potion.ModPotions;
import net.minecraft.world.World;

public class EntityMentorCritChance extends EntityMentorBase {
    public EntityMentorCritChance(World worldIn) {
        super(worldIn);
        giftPotion = ModPotions.CRIT_CHANCE_PLUS;
        attackPotion =  ModPotions.CRIT_CHANCE_MINUS;
    }
}
