package com.somebody.idlframewok.designs.events.design.mjds;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.blockDungeon.BlockMJDS;
import com.somebody.idlframewok.util.sounds.ModSoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.designs.events.design.mjds.UtilMJDS.getJumpFactor;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsJumpHelp {
    private static BlockPos[] posDeltaList = {
            BlockPos.ORIGIN.east(),
            BlockPos.ORIGIN.west(),
            BlockPos.ORIGIN.south(),
            BlockPos.ORIGIN.north(),
            BlockPos.ORIGIN.east().north(),
            BlockPos.ORIGIN.east().south(),
            BlockPos.ORIGIN.west().north(),
            BlockPos.ORIGIN.west().south(),
    };


    //Is this working? needs tests
    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        World world = event.getEntity().getEntityWorld();
//        if (!world.isRemote)
        {
            EntityLivingBase livingEntity = event.getEntityLiving();

//            if (livingEntity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof EgoArmor)
//            {
//                //Popolon and Artemis boots prevents fall damage, even outside of MJDS
//                event.setDamageMultiplier(0f);
//                return;
//            }

            //takes no damage if near MJDS

            for (BlockPos pos : posDeltaList) {
                if (UtilMJDS.isWithOffsetMJDS(world, pos, livingEntity)) {
                    event.setDamageMultiplier(0f);
                    world.playSound(null, pos, ModSoundHandler.MJDS_FALL, SoundCategory.BLOCKS, 1f, 1f);
                    return;
                }
            }
        }
    }

    //todo: rounding needs testing
    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        World world = event.getEntity().getEntityWorld();
        if (world.isRemote)//Yep. Client for players
        {
            EntityLivingBase livingEntity = event.getEntityLiving();
            if (!livingEntity.onGround) {
                return;
            }

            float jumpFactorMax = getJumpFactor(world.getBlockState(livingEntity.getPosition()));
            float jumpFactorMaxInit = jumpFactorMax;

            for (BlockPos pos : posDeltaList) {
                float max = getFactor(world, pos, livingEntity);
                if (max > jumpFactorMax) {
                    jumpFactorMax = max;
                }
            }

            if (jumpFactorMax >= BlockMJDS.JUMP_FACTOR_MJDS) {
                event.getEntity().playSound(ModSoundHandler.MJDS_JUMP, 1f, 1f);
            }

            //jumpFactorMax -= jumpFactorMaxInit;

//            if (livingEntity instanceof PlayerEntity)
//            {
//                IdlFramework.Log("d-jumpFactorMax = %s", jumpFactorMax);
//            }

            jumpFactorMax *= 0.42f;//const

            if (livingEntity.isPotionActive(MobEffects.JUMP_BOOST)) {
                jumpFactorMax += 0.1F * (float) (livingEntity.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1);
            }

            livingEntity.setVelocity(livingEntity.motionX, jumpFactorMax, livingEntity.motionZ);
        } else {
//            if (isInMJDS(event.getEntity()))
//            {
//                event.getEntity().playSound(SoundEvents.BLOCK_NOTE_BELL, 1f, 1f);
//            }
        }
    }

    //copied from net.minecraft.entity.Entity
    public static BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity) {
        return new BlockPos(entity.getPosition().getX(), entity.getEntityBoundingBox().minY - 0.5000001D, entity.getPosition().getZ());
    }

    public static float getFactor(World world, BlockPos pos, Entity entity) {
        float f = getJumpFactor(world.getBlockState(entity.getPosition().add(pos)));
        float f1 = getJumpFactor(world.getBlockState(getBlockPosBelowThatAffectsMyMovement(entity).add(pos)));
        return f > f1 ? f : f1;
    }
}
