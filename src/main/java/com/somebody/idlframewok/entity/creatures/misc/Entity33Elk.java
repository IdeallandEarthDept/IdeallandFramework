package com.somebody.idlframewok.entity.creatures.misc;

import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import net.minecraft.world.World;

//OKO OKO OKO OKO OKO OKO OKO OKO OKO
//OKO OKO OKO OKO OKO OKO OKO OKO OKO
//OKO OKO OKO OKO OKO OKO OKO OKO OKO
public class Entity33Elk extends EntityModUnit {
    public Entity33Elk(World worldIn) {
        super(worldIn);
        experienceValue = 3;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16,0.3f,3,0,33);
    }
}
