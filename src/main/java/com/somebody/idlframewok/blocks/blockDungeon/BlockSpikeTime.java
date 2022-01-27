package com.somebody.idlframewok.blocks.blockDungeon;

import com.somebody.idlframewok.blocks.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class BlockSpikeTime extends BlockBase {
    int period = TICK_PER_SECOND * 2;
    int safeAfter = TICK_PER_SECOND;
    int initPhase = 0;
    float damage = 1f;

    int curPhase = 0;

    public BlockSpikeTime(String name, Material material) {
        super(name, material);
        curPhase = initPhase;
        this.setTickRandomly(true);//todo:need not random
    }

    /**
     * Called when the given entity walks on this BlockPhasingOre
     */
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if (curPhase <= safeAfter)
        {
            if (entityIn instanceof EntityLivingBase)
            {
                entityIn.attackEntityFrom(DamageSource.GENERIC, 1.0F);
            }
        }

        super.onEntityWalk(worldIn, pos, entityIn);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        curPhase++;
        curPhase %= period;

        BlockPos blockpos = pos.up();
        IBlockState iblockstate = worldIn.getBlockState(blockpos);

        if (curPhase == safeAfter)
        {
            worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
        }

        if (curPhase == 0)
        {
            worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_ANVIL_STEP, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
        }

//        if (worldIn instanceof WorldServer)
//        {
//            ((WorldServer)worldIn).spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D);
//        }
    }

    public boolean canEntitySpawn(IBlockState state, Entity entityIn)
    {
        return false;
    }
}
