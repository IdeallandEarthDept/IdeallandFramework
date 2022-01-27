package com.somebody.idlframewok.blocks.blockWof;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.entity.projectiles.EntityIdlProjectile;
import com.somebody.idlframewok.entity.projectiles.ProjectileArgs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class BlockFleshEye extends BlockBase {
    public BlockFleshEye(String name, Material material) {
        super(name, material);
        setTickRandomly(true);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);
        if (!worldIn.isRemote)
        {
//            worldIn.setBlockState(pos, ModBlocks.FLESH_BLOCK_SCAR.getDefaultState());
            entityIn.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1f,1f);
        }
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(worldIn, pos, state, random);
        if (!worldIn.isRemote)
        {
            if (random.nextInt(5) == 0)
            {
                for (EnumFacing facing:
                        EnumFacing.values()) {
                    if (worldIn.getBlockState(pos.offset(facing)).getMaterial() == Material.AIR)
                    {
                        EntityIdlProjectile bullet = new EntityIdlProjectile(worldIn, new ProjectileArgs(3f).setTTL(TICK_PER_SECOND), pos, facing.getDirectionVec().getX(),facing.getDirectionVec().getY(),facing.getDirectionVec().getZ(), 0.1f);
                        worldIn.spawnEntity(bullet);
                    }
                }
            }
        }
    }
}
