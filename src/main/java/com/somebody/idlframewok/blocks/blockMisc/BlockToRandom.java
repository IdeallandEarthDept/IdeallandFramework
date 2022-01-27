package com.somebody.idlframewok.blocks.blockMisc;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static com.somebody.idlframewok.world.dimension.universal.ChunkGenUniversal.sky_depth;

public class BlockToRandom extends BlockBase {
    public BlockToRandom(String name, Material material) {
        super(name, material);
        setLightLevel(0.5f);
        setHardness(8f);
        setHarvestThis("none", 8);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (!worldIn.isRemote)
        {
            int trial = 999;
            float range = 20f;
            double yMax = worldIn.getActualHeight()- sky_depth;
            Random random = playerIn.getRNG();
            while (true)
            {
                double x = CommonFunctions.flunctate(playerIn.posX, range, random);
                double z = CommonFunctions.flunctate(playerIn.posZ, range, random);
                if (playerIn.attemptTeleport(x, yMax, z) && trial > 0)
                {
                    Idealland.Log("Player %s teleported to %s from %s from random block", playerIn.getDisplayNameString(), playerIn.getPosition(), pos);
                    EntityUtil.ApplyBuff(playerIn, ModPotions.INVINCIBLE, 0, 3f);
                    EntityUtil.ApplyBuff(playerIn, MobEffects.SLOWNESS, 1, 3f);
                    trial--;
                    break;
                }
            }

            worldIn.playSound(playerIn, pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.BLOCKS, 1f, 2f);
        }

        return true;
    }
}
