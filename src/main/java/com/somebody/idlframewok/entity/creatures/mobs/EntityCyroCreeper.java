package com.somebody.idlframewok.entity.creatures.mobs;

import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityCyroCreeper extends EntityExploderBase {
    final int HARD_BUFF_DURA = TICK_PER_SECOND * 5;
    final int NORMAL_BUFF_DURA = TICK_PER_SECOND * 3;

    public EntityCyroCreeper(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onFirstTickInLife() {
        super.onFirstTickInLife();
        Init16Gods.setBelief(this, Color16Def.LAVA, 100);
        Init16Gods.setBelief(this, Color16Def.FIRE, -100);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!world.isRemote)
        {
            //copied from snow golem
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY);
            int k = MathHelper.floor(this.posZ);
            for (int l = 0; l < 4; ++l)
            {
                i = MathHelper.floor(this.posX + (double)((float)(l % 2 * 2 - 1) * 0.25F));
                j = MathHelper.floor(this.posY);
                k = MathHelper.floor(this.posZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos = new BlockPos(i, j, k);

                if (this.world.getBlockState(blockpos).getMaterial() == Material.AIR && this.world.getBiome(blockpos).getTemperature(blockpos) < 0.8F && Blocks.SNOW_LAYER.canPlaceBlockAt(this.world, blockpos))
                {
                    this.world.setBlockState(blockpos, Blocks.SNOW_LAYER.getDefaultState());
                }
            }
        }
    }

    @Override
    public void preSpawnCloud() {
        int dura = world.getDifficulty() == EnumDifficulty.HARD ? HARD_BUFF_DURA : NORMAL_BUFF_DURA;
        EntityUtil.ApplyBuff(this, MobEffects.SLOWNESS, 1, dura);
        EntityUtil.ApplyBuff(this, ModPotions.COLD, 1, dura);
    }

    int particleCount = 20;
    public void onClientExplode()
    {
        float theta = 0f;
        float phi = 0f;
        float velocity = 1f;
        float dTheta = (float) (Math.PI / particleCount * 2f);
        while (particleCount-- > 0)
        {
            float dx = (float) (velocity * Math.cos(theta) * Math.cos(phi));
            float dy = (float) (velocity * Math.sin(theta) * Math.cos(phi));
            float dz = (float) (velocity * Math.sin(phi));
            world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, posX+dx, posY+dy, posZ+dz, dx,dy,dz);
            phi = (float) CommonFunctions.flunctate(0, Math.PI/2, rand);
            theta += dTheta;
        }
    }
}
