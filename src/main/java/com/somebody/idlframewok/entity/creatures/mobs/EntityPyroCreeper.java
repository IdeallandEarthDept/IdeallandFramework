package com.somebody.idlframewok.entity.creatures.mobs;

import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityPyroCreeper extends EntityExploderBase {
    final int HARD_BUFF_DURA = TICK_PER_SECOND * 5;
    final int NORMAL_BUFF_DURA = TICK_PER_SECOND * 3;

    public EntityPyroCreeper(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onFirstTickInLife() {
        super.onFirstTickInLife();
        Init16Gods.setBelief(this, Color16Def.LAVA, 100);
        Init16Gods.setBelief(this, Color16Def.FIRE, 100);
    }

    @Override
    public void preSpawnCloud() {
        int dura = world.getDifficulty() == EnumDifficulty.HARD ? HARD_BUFF_DURA : NORMAL_BUFF_DURA;
        EntityUtil.ApplyBuff(this, ModPotions.BURN, 1, dura);
    }

    int particleCount = 20;
    public void onClientExplode()
    {
        float theta = 0f;
        float phi = 0f;
        float velocity = 0.2f;//need adjusting
        float dTheta = (float) (Math.PI / particleCount * 2f);
        while (particleCount-- > 0)
        {
            float dx = (float) (velocity * Math.cos(theta) * Math.cos(phi));
            float dy = (float) (velocity * Math.sin(theta) * Math.cos(phi));
            float dz = (float) (velocity * Math.sin(phi));
            world.spawnParticle(EnumParticleTypes.FLAME, posX+dx, posY+dy, posZ+dz, dx,dy,dz);
            phi = (float) CommonFunctions.flunctate(0, Math.PI/2, rand);
            theta += dTheta;
        }
    }
}
