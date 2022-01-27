package com.somebody.idlframewok.designs.idealland;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;
import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.CHUNK_SIZE;
import static com.somebody.idlframewok.util.CommonDef.TICK_PER_DAY;
import static com.somebody.idlframewok.util.EntityUtil.NON_HALF_CREATURE;
import static com.somebody.idlframewok.util.MessageDef.*;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.PROTECT_STATUS;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EventsIdeallandProtection {

    public enum EnumProtectionStatus{
        ABSOLUTE,
        RELATIVE,
        ABSENT,
        OTHER;

        public static EnumProtectionStatus fromInt(int value)
        {
            switch (value)
            {
                case 0: return ABSOLUTE;
                case 1: return RELATIVE;
                case 2: return ABSENT;
                default: return OTHER;
            }
        }
    }

    //PlayerContainerEvent


    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (!ModConfig.GeneralConf.SHENMING_PROTECTION) {
            return;
        }

        World world = event.player.world;
        EntityPlayer player = event.player;
        if (world != null && !world.isRemote)
        {
            EnumProtectionStatus status = EnumProtectionStatus.fromInt(IDLNBTUtil.GetIntAuto(player, PROTECT_STATUS, 0));

            switch (status) {
                case ABSOLUTE:
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, player, MSG_LOGIN_ABSOLUTE);

                    break;
                case RELATIVE:
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GREEN, player, MSG_LOGIN_RELATIVE);
                    break;
            }
        }
    }

    @SubscribeEvent
    public static void onKnockBack(LivingKnockBackEvent event) {

        if (!ModConfig.GeneralConf.SHENMING_PROTECTION) {
            return;
        }

        World world = event.getEntityLiving().world;
        EntityLivingBase hurtOne = event.getEntityLiving();
        if (world == null || world.isRemote || !(hurtOne instanceof EntityPlayer)) {
            return;
        }

        EnumProtectionStatus status = EnumProtectionStatus.fromInt(IDLNBTUtil.GetIntAuto(hurtOne, PROTECT_STATUS, 0));
        switch (status) {
            case RELATIVE: case ABSOLUTE:
                event.setCanceled(true);
                break;
        }
    }

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event)
    {
        if (!ModConfig.GeneralConf.SHENMING_PROTECTION) {
            return;
        }

        World world = event.getEntityLiving().world;
        Entity attacker = event.getSource().getTrueSource();

        if (world != null && !world.isRemote && attacker instanceof EntityPlayer) {
            EntityPlayer attackerPlayer = (EntityPlayer) attacker;
            tryRemoveAbsoluteProtection(attackerPlayer);
        }
    }

    public static final Predicate<EntityPlayer> CAN_REPLENISH = new Predicate<EntityPlayer>()
    {
        public boolean apply(@Nullable EntityPlayer entity)
        {
            EnumProtectionStatus status = EnumProtectionStatus.fromInt(IDLNBTUtil.GetIntAuto(entity, PROTECT_STATUS, 0));
            return status == EnumProtectionStatus.ABSENT;
        }
    };

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (!ModConfig.GeneralConf.SHENMING_PROTECTION) {
            return;
        }

        World world = event.world;
        if (!world.isRemote && world.getWorldTime() % TICK_PER_DAY == 0)
        {
            List<EntityPlayer> list = world.getPlayers(EntityPlayer.class, CAN_REPLENISH);

            for (EntityPlayer player:
                 list) {
                IDLNBTUtil.setIntAuto(player, PROTECT_STATUS, EnumProtectionStatus.RELATIVE.ordinal());
                CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GREEN, player, MSG_LOGIN_RELATIVE);
            }
        }
    }

//    @SubscribeEvent
//    public static void onInteract(PlayerInteractEvent event)
//    {
//        World world = event.getEntityLiving().world;
//        EntityPlayer player = event.getEntityPlayer();
//
//        Idealland.Log("Result = %s", event.getCancellationResult());
//        if (!world.isRemote && player != null && event.getCancellationResult() == EnumActionResult.SUCCESS) {
//            tryRemoveAbsoluteProtection(player);
//        }
//    }

//    @SubscribeEvent
//    public static void onInteract(PlayerInteractEvent.RightClickBlock event)
//    {
//        World world = event.getEntityLiving().world;
//        EntityPlayer player = event.getEntityPlayer();
//
//        Idealland.Log("Result = %s", event.getCancellationResult());
//        if (!world.isRemote && player != null && event.getCancellationResult() == EnumActionResult.SUCCESS) {
//            tryRemoveAbsoluteProtection(player);
//        }
//    }

    @SubscribeEvent
    public static void onDig(BlockEvent.BreakEvent event)
    {
        if (!ModConfig.GeneralConf.SHENMING_PROTECTION) {
            return;
        }

        World world = event.getWorld();
        EntityPlayer player = event.getPlayer();

        if (world != null && !world.isRemote && player != null) {
            tryRemoveAbsoluteProtection(player);
        }
    }

    @SubscribeEvent
    public static void onDig(BlockEvent.PlaceEvent event)
    {
        if (!ModConfig.GeneralConf.SHENMING_PROTECTION) {
            return;
        }

        if (event.getPlayer() != null)
        {
            World world = event.getPlayer().world;
            EntityPlayer player = event.getPlayer();

            if (world != null && !world.isRemote && player != null) {
                tryRemoveAbsoluteProtection(player);
            }
        }
    }

    static void tryRemoveAbsoluteProtection(EntityPlayer player)
    {
        if (player.world == null)
        {
            return;
        }

        EnumProtectionStatus status = EnumProtectionStatus.fromInt(IDLNBTUtil.GetIntAuto(player, PROTECT_STATUS, 0));
        if (status == EnumProtectionStatus.ABSOLUTE) {
            IDLNBTUtil.setIntAuto(player, PROTECT_STATUS, EnumProtectionStatus.ABSENT.ordinal());
            CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, player, MSG_LOST_ABSOLUTE);
            player.world.playSound(player, player.getPosition(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 1f, 1f);
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {
        if (!ModConfig.GeneralConf.SHENMING_PROTECTION) {
            return;
        }

        World world = event.getEntityLiving().world;
        EntityLivingBase hurtOne = event.getEntityLiving();
        if (world == null || !(hurtOne instanceof EntityPlayer))
        {
            return;
        }

        EnumProtectionStatus status = EnumProtectionStatus.fromInt(IDLNBTUtil.GetIntAuto(hurtOne, PROTECT_STATUS, 0));
        Entity attacker = event.getSource().getTrueSource();
        switch (status)
        {
            case ABSOLUTE:
                event.setCanceled(true);

                if (attacker instanceof EntityPlayer && !world.isRemote)
                {
                    EntityPlayer player = (EntityPlayer) attacker;
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GRAY, player, MSG_TRIGGER_ABSOLUTE_OPPONENT);
                }
                break;
            case RELATIVE:
                event.setCanceled(true);

                if (world.isRemote)
                {
                    world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, hurtOne.posX, hurtOne.posY, hurtOne.posZ, 0, 0,0);
                }
                else {
                    if (attacker instanceof EntityPlayer)
                    {
                        EntityPlayer player = (EntityPlayer) attacker;
                        CommonFunctions.SafeSendMsgToPlayer(TextFormatting.GRAY, player, MSG_TRIGGER_RELATIVE_OPPONENT);
                    }

                    float buffDuration = 10f;

                    List<EntityLivingBase> list = EntityUtil.getEntitiesWithinAABB(world, EntityLivingBase.class,
                            hurtOne.getPositionVector(), CHUNK_SIZE, NON_HALF_CREATURE);

                    for (EntityLivingBase living:
                            list) {
                        if (living == hurtOne)
                        {
                            continue;
                        }

                        if (living instanceof EntityPlayer)
                        {
                            EnumProtectionStatus victimStatus = EnumProtectionStatus.fromInt(IDLNBTUtil.GetIntAuto(hurtOne, PROTECT_STATUS, 0));
                            if (victimStatus == EnumProtectionStatus.ABSOLUTE || victimStatus == EnumProtectionStatus.RELATIVE)
                            {
                                continue;
                            }
                        }

                        EntityUtil.ApplyBuff(living, MobEffects.SLOWNESS, 3, buffDuration);
                    }

                    EntityUtil.ApplyBuff(hurtOne, MobEffects.SPEED, 3, buffDuration);
                    IDLNBTUtil.setIntAuto(hurtOne, PROTECT_STATUS, EnumProtectionStatus.ABSENT.ordinal());

                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.YELLOW, hurtOne, MSG_TRIGGER_RELATIVE);
                }
                break;
            default:
                break;
        }
    }
}
