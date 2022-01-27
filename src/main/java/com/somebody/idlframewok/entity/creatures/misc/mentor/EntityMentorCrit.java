package com.somebody.idlframewok.entity.creatures.misc.mentor;

import com.somebody.idlframewok.potion.ModPotions;
import net.minecraft.world.World;

public class EntityMentorCrit extends EntityMentorBase {
    public EntityMentorCrit(World worldIn) {
        super(worldIn);
        giftPotion = ModPotions.CRIT_DMG_PLUS;
        attackPotion =  ModPotions.CRIT_DMG_MINUS;
    }
}
