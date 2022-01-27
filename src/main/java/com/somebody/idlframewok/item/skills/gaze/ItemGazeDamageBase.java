package com.somebody.idlframewok.item.skills.gaze;

import com.somebody.idlframewok.item.skills.ItemSkillEye;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class ItemGazeDamageBase extends ItemSkillEye {

    public Potion potion;

    public ItemGazeDamageBase(String name, Potion potion) {
        super(name);
        setVal(5f, 3f);
        this.potion = potion;
    }

    @Override
    public void applyingEffects(EntityLivingBase source, EntityLivingBase target, SkillEyeArgs args, float modifier) {

        float dmg = getVal(args.damagePlus + args.level);
        if (dmg > 0)
        {
            target.attackEntityFrom(getDamageType(source, target, args, modifier), dmg);
        }
    }
}
