package com.somebody.idlframewok.util;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.uprising.citadel.ICitadelBuilding;
import com.somebody.idlframewok.enchantments.ModEnchantmentLover;
import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.entity.creatures.ICustomFaction;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.google.common.base.Predicate;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import javax.annotation.Nullable;
import java.util.*;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.EVILNESS;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.FORCE_VANILLA;
import static net.minecraft.entity.SharedMonsterAttributes.*;
import static net.minecraftforge.fml.common.gameevent.TickEvent.Type.WORLD;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EntityUtil {

    public static final String TRYING_TO_APPLY_ILLEGAL_POTION = "Trying to apply illegal potion";

    public static DamageSource attack(EntityLivingBase source)
    {
        if (source instanceof EntityPlayer)
        {
            return DamageSource.causePlayerDamage((EntityPlayer) source);
        }
        else {
            return DamageSource.causeMobDamage(source);
        }
    }

    static final float cos45 = 1.414f / 2;
    public static boolean canSee(EntityLivingBase seer, Entity target)
    {
        //copied from enderman, modified
        Vec3d vec3d = seer.getLook(1.0F).normalize();
        Vec3d vec3d1 = new Vec3d(target.posX - seer.posX, target.getEntityBoundingBox().minY + (double)target.getEyeHeight() - (seer.posY + (double)seer.getEyeHeight()), target.posZ - seer.posZ);
        //double d0 = vec3d1.lengthVector();
        vec3d1 = vec3d1.normalize();
        double d1 = vec3d.dotProduct(vec3d1);
        return d1 > cos45 && seer.canEntityBeSeen(target);
    }

    public static boolean isSeeing(EntityLivingBase seer, Entity target)
    {
        //copied from enderman
        Vec3d vec3d = seer.getLook(1.0F).normalize();
        Vec3d vec3d1 = new Vec3d(target.posX - seer.posX, target.getEntityBoundingBox().minY + (double)target.getEyeHeight() - (seer.posY + (double)seer.getEyeHeight()), target.posZ - seer.posZ);
        double d0 = vec3d1.lengthVector();
        vec3d1 = vec3d1.normalize();
        double d1 = vec3d.dotProduct(vec3d1);
        return d1 > 1.0D - 0.025D / d0 && seer.canEntityBeSeen(target);
    }

    static Map<Class, Boolean> notHumanlike = new HashMap<>();
    public static void init()
    {
        notHumanlike.put(EntityCaveSpider.class, true);
        notHumanlike.put(EntitySpider.class, true);
        notHumanlike.put(EntitySilverfish.class, true);
        notHumanlike.put(EntityEndermite.class, true);
        notHumanlike.put(EntityCreeper.class, true);
        notHumanlike.put(EntityEnderman.class, true);
        notHumanlike.put(EntitySnowman.class, true);
        notHumanlike.put(EntityWitch.class, true);
        notHumanlike.put(EntityBlaze.class, true);
        notHumanlike.put(EntitySlime.class, true);
        notHumanlike.put(EntityGhast.class, true);
        notHumanlike.put(EntityMagmaCube.class, true);
        notHumanlike.put(EntitySquid.class, true);
        notHumanlike.put(EntityVillager.class, true);
        notHumanlike.put(EntityIronGolem.class, true);
        notHumanlike.put(EntityBat.class, true);
        notHumanlike.put(EntityGuardian.class, true);
        notHumanlike.put(EntityElderGuardian.class, true);
        notHumanlike.put(EntityShulker.class, true);
        notHumanlike.put(EntityEvoker.class, true);
        notHumanlike.put(EntityIllusionIllager.class, true);
        notHumanlike.put(EntityVindicator.class, true);
        notHumanlike.put(EntityDragon.class, true);
        notHumanlike.put(EntityWither.class, true);
    }

    public static final Predicate<EntityLivingBase> NOT_WEAR_HELM = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase entity)
        {
            if (entity == null)
            {
                return false;
            }

            return ((entity instanceof EntityLiving) || (entity instanceof EntityPlayer))
                    && entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty();
        }
    };

    public static final Predicate<EntityLivingBase> NOT_UNDER_EMP = new Predicate<EntityLivingBase>() {
        public boolean apply(@Nullable EntityLivingBase entity) {
            if (entity == null) {
                return false;
            }

            return ((entity instanceof EntityLiving) || (entity instanceof EntityPlayer))
                    && entity.getActivePotionEffect(ModPotions.INTERFERENCE) == null;
        }
    };

    public static final Predicate<EntityLivingBase> IS_HUMANOID = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase entity)
        {
            if (entity == null)
            {
                return false;
            }

            return (entity instanceof EntityPlayer) ||
                    ((entity instanceof EntityLiving) && !(entity instanceof EntityAnimal) && !notHumanlike.containsKey(entity.getClass()));
        }
    };

    public static String getRegName(EntityLivingBase livingBase)
    {
        String s = EntityList.getEntityString(livingBase);

        if (s == null)
        {
            s = "generic";
        }

        return I18n.translateToLocal("entity." + s + ".name");
    }


    public static void simpleKnockBack(float power, EntityLivingBase source, EntityLivingBase target)
    {
        target.knockBack(source, power, (source.posX - target.posX), (source.posZ - target.posZ));
    }

    public static boolean giveIfEmpty(EntityLivingBase livingBase, ItemStack stack, EntityEquipmentSlot slot)
    {
        return giveIfEmpty(livingBase, slot, stack);
    }

    public static boolean giveIfEmpty(EntityLivingBase livingBase, EntityEquipmentSlot slot, ItemStack stack)
    {
        if (livingBase.getItemStackFromSlot(slot).isEmpty())
        {
            livingBase.setItemStackToSlot(slot, stack);
            return true;
        }
        return false;
    }

    public static void TryRemoveDebuff(EntityLivingBase livingBase)
    {
        //washes away debuff
        Collection<PotionEffect> activePotionEffects = livingBase.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion().isBadEffect()){
                livingBase.removePotionEffect(buff.getPotion());
            }
        }
    }

    public static boolean TryRemoveGivenBuff(EntityLivingBase livingBase, Potion potion)
    {
        //washes away debuff
        Collection<PotionEffect> activePotionEffects = livingBase.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() == potion){
                livingBase.removePotionEffect(buff.getPotion());
                return true;
            }
        }

        return false;
    }

    //0 = buff I, 1 = buff II
    public static boolean ApplyBuff(EntityLivingBase livingBase, Potion potion, int level, float seconds) {
        if (livingBase == null || potion == null) {
            Idealland.LogWarning(TRYING_TO_APPLY_ILLEGAL_POTION);
            return false;
        }
        livingBase.addPotionEffect(new PotionEffect(potion, (int) (seconds * TICK_PER_SECOND), level));
        return true;
    }

    //Note: this returns 0 if no buff.
    public static int getBuffLevelIDL(EntityLivingBase livingBase, Potion potion) {
        if (livingBase == null || potion == null) {
            Idealland.LogWarning(TRYING_TO_APPLY_ILLEGAL_POTION);
            return 0;
        }
        PotionEffect effect = livingBase.getActivePotionEffect(potion);
        if (effect == null) {
            return 0;
        } else {
            return effect.getAmplifier() + 1;
        }
    }

    public static String getModName(EntityLivingBase creature)
    {
        if (creature instanceof EntityPlayer || creature == null)
        {
            return "minecraft";
        }
        EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(creature.getClass(), true);
        if (er == null)
        {
            //Vanilla creatures don't have ER
            return "minecraft";
        }
        return er.getContainer().getModId();
    }

    //Player is not vanilla
    public static boolean isVanillaResident(EntityLivingBase creature)
    {
        if (creature instanceof EntityPlayer || creature == null)
        {
            return false;
        }

        EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(creature.getClass(), true);
        if (er == null)
        {
            return true;
        }

        if (IDLNBTUtil.GetIntAuto(creature, FORCE_VANILLA, 0) > 0)
        {
            return true;
        }

        String modid = er.getContainer().getModId();
        return modid.equals("minecraft");
    }

    //Player is not vanilla
    public static boolean isCubixCreature(EntityLivingBase creature)
    {
        if (creature instanceof EntityModUnit)
        {
            return ((EntityModUnit) creature).is_cubix;
        }
        return false;
    }

    //Player is not otherWorld
    public static boolean isOtherworldAggression(EntityLivingBase creature)
    {
        if (creature instanceof EntityPlayer || creature == null || isIdeallandTeam(creature))
        {
            return false;
        }

        EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(creature.getClass(), true);
        if (er == null)
        {
            //Normally this will be enough. Vanilla creatures don't have ER
            return false;
        }
        String modid = er.getContainer().getModId();
        //Idealland.Log("Atk ER.modid is %s, name is %s", modid, er.getRegistryName());

        return !modid.equals("minecraft");
    }

    public static boolean isIdeallandTeam(EntityLivingBase creature)
    {
        return (creature instanceof EntityModUnit && ((EntityModUnit) creature).isIdealland);
    }

    public static boolean isMoroonTeam(EntityLivingBase creature)
    {
        return (creature instanceof EntityModUnit && ((EntityModUnit) creature).isMoroon);
    }

    public static boolean isMechanical(EntityLivingBase creature)
    {
        return (creature instanceof EntityModUnit && ((EntityModUnit) creature).is_mechanic) || creature instanceof EntityGolem;
    }

    public static boolean isElemental(EntityLivingBase creature)
    {
        return (creature instanceof EntityModUnit && ((EntityModUnit) creature).is_elemental);
    }

    public static boolean isAOA3Creature(EntityLivingBase creature)
    {
        if (!MetaUtil.isLoaded_AOA3)
        {
            return false;
        }
        return getModName(creature).equals(CommonDef.MOD_NAME_AOA3);
    }

    public static boolean isGOGCreature(EntityLivingBase creature)
    {
        if (!MetaUtil.isLoaded_GOG)
        {
            return false;
        }
        return getModName(creature).equals(CommonDef.MOD_NAME_AOA3);
    }

    public static <T extends Entity> List<T> getEntitiesWithinAABB(World world, Class <? extends T > clazz, AxisAlignedBB aabb, @Nullable Predicate <? super T > filter)
    {
        return world.getEntitiesWithinAABB(clazz, aabb, filter);
    }

    public static <T extends Entity> List<T> getEntitiesWithinAABB(World world, Class <? extends T > clazz, Vec3d center, float range, @Nullable Predicate <? super T > filter)
    {
        return world.getEntitiesWithinAABB(clazz, IDLGeneral.ServerAABB(center.addVector(-range, -range, -range), center.addVector(range, range, range)) , filter);
    }

    public static boolean isAlone(World world, EntityLivingBase entity, float range) {
        List<EntityLivingBase> nearbyAtk = EntityUtil.getEntitiesWithinAABB(world, EntityLivingBase.class, entity.getPositionVector(), range, null);
        for (EntityLivingBase creature : nearbyAtk) {
            if (creature == entity) {
                continue;
            }

            if (EntityUtil.getAttitude(entity, creature) == EnumAttitude.FRIEND) {
                return false;
            }
        }
        return true;
    }
    
    public static <T extends Entity> List<T> getEntitiesWithinAABB(World world, Class <? extends T > clazz, BlockPos center, float range, @Nullable Predicate <? super T > filter)
    {
        Vec3d vec3d = CommonFunctions.getVecFromBlockPos(center);
        return world.getEntitiesWithinAABB(clazz, IDLGeneral.ServerAABB(vec3d.addVector(-range, -range, -range), vec3d.addVector(range, range, range)) , filter);
    }

    public static EntityLivingBase FindLover(EntityLivingBase source, float range, ModEnchantmentLover enchant)
    {
        if (EnchantmentHelper.getMaxEnchantmentLevel(enchant, source) == 0)
        {
            return null;
        }

        EntityLivingBase result = null;
        Vec3d sourcePos = source.getPositionEyes(0f);
        source.playSound(SoundEvents.ENTITY_LIGHTNING_IMPACT, 1f, 1f);
        //Damage nearby entities
        List<EntityLivingBase> list = source.world.getEntitiesWithinAABB(EntityLivingBase.class, IDLGeneral.ServerAABB(sourcePos.addVector(-range, -range, -range), sourcePos.addVector(range, range, range)));
        for (EntityLivingBase creature : list) {
            if (creature != source) {
                if (EnchantmentHelper.getMaxEnchantmentLevel(enchant, creature) > 0)
                {
                    if (result != null)
                    {
                        //more than one lover found
                        return null;
                    }
                    else {
                        result = creature;
                    }
                }
            }
        }

        return result;
    }

    public static Vec3d GetRandomAroundUnderfoot(EntityLivingBase entity, float radius)
    {
        float angle = entity.getRNG().nextFloat() * 6.282f;
        return new Vec3d(entity.posX + Math.sin(angle),  entity.posY, entity.posZ + Math.cos(angle));
    }

    public static Vec3d GetRandomAroundPos(Vec3d pos, float radius, Random rng)
    {
        float angle = rng.nextFloat() * 6.282f;
        return new Vec3d(pos.x + radius * Math.sin(angle), pos.y, pos.z + radius * Math.cos(angle));
    }

    public static void SpawnParticleAround(EntityLivingBase entity, EnumParticleTypes particleTypes)
    {
        Vec3d pos = GetRandomAroundUnderfoot(entity,1f);
        entity.world.spawnParticle(particleTypes, pos.x, pos.y, pos.z, 0,0,0);
    }

    public static void SpawnParticleAround(EntityLivingBase entity, EnumParticleTypes particleTypes, int count)
    {
        for (int i = 0; i < count; i++)
        {
            SpawnParticleAround(entity, particleTypes);
        }
    }

    public static void createTeleportEffect(EntityLivingBase livingBase)
    {
        if (livingBase == null)
        {
            return;
        }

        World worldIn = livingBase.world;
        if (worldIn.isRemote)
        {
            Vec3d oriPos = livingBase.getPositionEyes(0);
            Random random = livingBase.getRNG();
            AxisAlignedBB bb = livingBase.getRenderBoundingBox();
            double radiusX = bb.maxX - bb.minX;
            double radiusY = bb.maxY - bb.minY;
            double radiusZ = bb.maxZ - bb.minZ;

            for (int i = 0; i <= 10; i++)
            {
                worldIn.spawnParticle(EnumParticleTypes.PORTAL,
                        CommonFunctions.flunctate(oriPos.x, radiusX, random),
                        CommonFunctions.flunctate(oriPos.y, radiusY, random),
                        CommonFunctions.flunctate(oriPos.z, radiusZ, random),
                        random.nextFloat(),
                        random.nextFloat(),
                        random.nextFloat()
                );
            }

            worldIn.playSound(oriPos.x, oriPos.y, oriPos.z, SoundEvents.ENTITY_ENDERMEN_TELEPORT, null, 1f, 1.3f, false);
        }
    }

    static float angle = 0f;

    @SubscribeEvent
    static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.type == WORLD )
        {
            angle += ModConfig.DEBUG_CONF.HALO_OMEGA;
            angle %= 6.282f;
        }
    }

    public static void spawnCubeParticleAround(EntityLivingBase entity, EnumParticleTypes particleTypes, float radius)
    {
        for (int i = 0; i < 10; i++)
        {
            Vec3d pos = entity.getPositionVector();
            Random random = entity.getRNG();
            float flunc = (float) CommonFunctions.flunctate(0, radius, random);
            entity.world.spawnParticle(particleTypes, pos.x + radius, pos.y, pos.z + flunc, 0,0,0);
            entity.world.spawnParticle(particleTypes, pos.x - radius, pos.y, pos.z + flunc, 0,0,0);
            entity.world.spawnParticle(particleTypes, pos.x + flunc, pos.y, pos.z + radius, 0,0,0);
            entity.world.spawnParticle(particleTypes, pos.x + flunc, pos.y, pos.z - radius, 0,0,0);
        }
    }

    public static void spawnHaloParticleAround(EntityLivingBase entity, EnumParticleTypes particleTypes, float radius)
    {
        for (int i = 0; i < 10; i++)
        {
            float deltaOmega = ModConfig.DEBUG_CONF.HALO_OMEGA * i;
            Vec3d pos = new Vec3d(entity.posX + radius * Math.sin(angle + deltaOmega),  entity.posY + 0.1f * entity.getRNG().nextFloat(), entity.posZ + radius * Math.cos(angle + deltaOmega));
            entity.world.spawnParticle(particleTypes, pos.x, pos.y, pos.z, 0,0,0);
        }
    }

    public static EnumFaction getFaction(EntityLivingBase creature)
    {
        if (creature instanceof ICustomFaction) {
            return ((ICustomFaction) creature).getFaction();
        }

        if (isMoroonTeam(creature))
        {
            return EnumFaction.MOROON;
        } else if (isIdeallandTeam(creature))
        {
            return EnumFaction.IDEALLAND;
        }else if (creature instanceof EntityZombie)
        {
            return EnumFaction.MOB_VAN_ZOMBIE;
        }
        else if (creature instanceof IMob)
        {
            return EnumFaction.MOB_VANILLA;
        }else if (creature instanceof EntityPlayer)
        {
            return EnumFaction.PLAYER;
        }else
        {
            return EnumFaction.CRITTER;
        }
    }

    public static EnumAttitude getAttitude(EntityLivingBase subject, EntityLivingBase object)
    {
        if (subject == null || object == null)
        {
            return EnumAttitude.IGNORE;
        }

        if (subject.isOnSameTeam(object))
        {
            return EnumAttitude.FRIEND;
        }
        return getAttitude(getFaction(subject), getFaction(object));
    }

    public static EnumAttitude getAttitude(EnumFaction subject, EntityLivingBase object)
    {
        return getAttitude(subject, getFaction(object));
    }

    public static EnumAttitude getAttitude(EnumFaction subject, EnumFaction object) {
        if (subject == null || object == null) {
            return EnumAttitude.IGNORE;
        }

        if (subject == object)
        {
            return EnumAttitude.FRIEND;
        }

        if (subject == EnumFaction.CRITTER || object == EnumFaction.CRITTER)
        {
            return EnumAttitude.IGNORE;
        }

        switch (subject)
        {
            case PLAYER:
            case IDEALLAND:
                switch (object)
                {
                    case IDEALLAND:
                        return EnumAttitude.FRIEND;
                    case MOB_VANILLA:
                    case MOB_VAN_ZOMBIE:
                    case MOROON:
                        return EnumAttitude.HATE;
                    default:
                        return EnumAttitude.IGNORE;
                }
            case MOB_VANILLA:
                switch (object)
                {
                    case IDEALLAND:
                    case PLAYER:
                        return EnumAttitude.HATE;
                    case MOB_VAN_ZOMBIE:
                        return EnumAttitude.FRIEND;

                    default:
                        return EnumAttitude.IGNORE;
                }

            case MOB_VAN_ZOMBIE:
                switch (object)
                {
                    case IDEALLAND:
                    case PLAYER:
                        return EnumAttitude.HATE;

                    case MOB_VANILLA:
                        return EnumAttitude.FRIEND;

                    default:
                        return EnumAttitude.IGNORE;
                }

            case MOROON:
                switch (object)
                {
                    case IDEALLAND:
                    case MOB_VAN_ZOMBIE:
                    case PLAYER:
                        return EnumAttitude.HATE;

                    default:
                        return EnumAttitude.IGNORE;
                }
        }
        return EnumAttitude.IGNORE;
    }

    public static Vec3d GetRandomAround(EntityLivingBase entity, float radius)
    {
        float angle = entity.getRNG().nextFloat() * 6.282f;
        return new Vec3d(entity.posX + Math.sin(angle), entity.getEyeHeight() + entity.posY, entity.posZ + Math.cos(angle));
    }

    public static final Predicate<EntityLivingBase> IS_CITADEL_BUILDING = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return p_apply_1_ != null && p_apply_1_ instanceof ICitadelBuilding;
        }
    };

    public static final Predicate<EntityLivingBase> InWater = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return p_apply_1_ != null && p_apply_1_.isInWater();
        }
    };

    public static final Predicate<EntityLivingBase> FriendToIdl = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return p_apply_1_ != null && (getAttitude(EnumFaction.IDEALLAND, p_apply_1_) == EnumAttitude.FRIEND);
        }
    };

    public static final Predicate<EntityLivingBase> HostileToIdl = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return p_apply_1_ != null && (getAttitude(EnumFaction.IDEALLAND, p_apply_1_) == EnumAttitude.HATE) && (p_apply_1_).attackable();
        }
    };

    public static final Predicate<EntityLivingBase> HostileToIdl_AIR = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return p_apply_1_ != null && (getAttitude(EnumFaction.IDEALLAND, p_apply_1_) == EnumAttitude.HATE) && (p_apply_1_).attackable() && !p_apply_1_.onGround;
        }
    };

    public static final Predicate<EntityLivingBase> HostileToMor = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return p_apply_1_ != null && (getAttitude(EnumFaction.MOROON, p_apply_1_) == EnumAttitude.HATE) && (p_apply_1_).attackable();
        }
    };

    public static final Predicate<EntityLivingBase> IsVanilla = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && isVanillaResident((p_apply_1_)) && (p_apply_1_).attackable();
        }
    };

    public static final Predicate<EntityLivingBase> NotVanilla = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && !isVanillaResident((p_apply_1_)) && (p_apply_1_).attackable();
        }
    };

    public static final Predicate<EntityLivingBase> NotCubix = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && !isCubixCreature(p_apply_1_) && (p_apply_1_).attackable();
        }
    };

    public static double getHPMax(EntityLivingBase creature)
    {
        if (creature == null)
        {
            return 0;
        }

        IAttributeInstance attribute = creature.getEntityAttribute(MAX_HEALTH);
        if (attribute == null)
        {
            return 0;
        }
        return attribute.getAttributeValue();
    }

    public static double getAttack(EntityLivingBase creature)
    {
        if (creature == null)
        {
            return 0;
        }

        IAttributeInstance attribute = creature.getEntityAttribute(ATTACK_DAMAGE);
        if (attribute == null)
        {
            return 0;
        }
        return attribute.getAttributeValue();
    }

    public static double getSight(EntityLivingBase creature)
    {
        if (creature == null)
        {
            return 0;
        }

        IAttributeInstance attribute = creature.getEntityAttribute(FOLLOW_RANGE);
        return attribute.getAttributeValue();
    }

    public static double getAtkSpeed(EntityLivingBase creature)
    {
        if (creature == null)
        {
            return 0;
        }

        IAttributeInstance attribute = creature.getEntityAttribute(ATTACK_SPEED);
        return attribute.getAttributeValue();
    }

    public static double getAttr(EntityLivingBase creature, IAttribute attr)
    {
        if (creature == null)
        {
            return 0;
        }

        IAttributeInstance attribute = creature.getEntityAttribute(attr);
        if (attribute == null)
        {
            return 0;
        }
        return attribute.getAttributeValue();
    }

    public static double getAttrBase(EntityLivingBase creature, IAttribute attr)
    {
        if (creature == null)
        {
            return 0;
        }

        IAttributeInstance attribute = creature.getEntityAttribute(attr);
        if (attribute == null)
        {
            return 0;
        }
        return attribute.getBaseValue();
    }

    public static void setAttrModifier(IAttributeInstance iattributeinstance, AttributeModifier modifier)
    {
        if (iattributeinstance.hasModifier(modifier)) {
            //prevent crash
            iattributeinstance.removeModifier(modifier.getID());
        }
        iattributeinstance.applyModifier(modifier);
    }

    public static boolean boostAttr(EntityLivingBase creature, IAttribute attrType, float amountFixed, UUID uuid)
    {
        float val = amountFixed;
        IAttributeInstance attribute = creature.getEntityAttribute(attrType);

        if (attribute == null)
        {
            //this happens on creatures with no attack.
            //will surely happen.
            creature.playSound(SoundEvents.BLOCK_DISPENSER_FAIL, 1f, 1f);
            return false;
        }

        double valueBefore = attribute.getAttributeValue();

        AttributeModifier modifier = attribute.getModifier(uuid);
        if (modifier != null)
        {
            //stack up
            val += modifier.getAmount();
            attribute.removeModifier(modifier);
        }
        attribute.applyModifier(new AttributeModifier(uuid, "pwr up",  val, 0));
        double valueAfter = attribute.getAttributeValue();

        if (modifier == null)
        {
            modifier = attribute.getModifier(uuid);
        }

        //Idealland.Log("Value:%s: %.2f->%.2f", modifier.getName(), valueBefore, valueAfter);
        return true;
    }

    public static boolean boostAttrRatio(EntityLivingBase creature, IAttribute attrType, float amountRatio, UUID uuid)
    {
        float val = amountRatio;
        IAttributeInstance attribute = creature.getEntityAttribute(attrType);

        if (attribute == null)
        {
            //this happens on creatures with no attack.
            //will surely happen.
            //creature.playSound(SoundEvents.BLOCK_DISPENSER_FAIL, 1f, 1f);
            return false;
        }

        //double valueBefore = attribute.getAttributeValue();

        AttributeModifier modifier = attribute.getModifier(uuid);
        if (modifier != null)
        {
            //stack up
            val += modifier.getAmount();
            attribute.removeModifier(modifier);
        }
        attribute.applyModifier(new AttributeModifier(uuid, "pwr up percent",  val, 1));
        //double valueAfter = attribute.getAttributeValue();

        if (modifier == null)
        {
            modifier = attribute.getModifier(uuid);
        }

        //Idealland.Log("Value:%s: %.2f->%.2f", modifier.getName(), valueBefore, valueAfter);
        return true;
    }

    public enum EnumFaction {
        PLAYER((byte) 0),
        IDEALLAND((byte) 1, 1.0f, 1.0f, 0.7f),
        MOB_VANILLA((byte) 2, 1.0f, 0.5f, 0.5f),
        MOB_VAN_ZOMBIE((byte) 3, 0.4f, 1f, 0.4f),
        MOROON((byte) 4, 0.9f, 0.3f, 0.8f),
        CRITTER((byte) 5);

        public final byte index;
        float r = 1.0f, g = 1.0f, b = 1.0f;

        EnumFaction(byte index, float r, float g, float b) {
            this.index = index;
            this.r = r;
            this.g = g;
            this.b = b;
        }

        EnumFaction(byte index) {
            this.index = index;
        }

        public static EnumFaction fromIndex(byte index) {
            for (EnumFaction faction :
                    EnumFaction.values()) {
                if (faction.index == index) {
                    return faction;
                }
            }

            Idealland.LogWarning("Trying to parse non-existing faction : %s", index);
            return CRITTER;
        }

        public void applyColor() {
            GlStateManager.color(r, g, b);
        }
    }

    public enum EnumAttitude {
        HATE,
        IGNORE,
        FRIEND
    }

    public static Biome getBiomeForEntity(Entity entity)
    {
        World world = entity.getEntityWorld();
        return world.getBiomeForCoordsBody(entity.getPosition());
    }

    public static final Predicate<Entity> ALL = new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity entity)
        {
            return entity != null;
        }
    };

    public static final Predicate<Entity> ALL_ALIVE = new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity entity)
        {
            return entity != null && !entity.isDead;
        }
    };

    public static final Predicate<Entity> UNDER_SKY = new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity entity)
        {
            return entity != null && entity.world.canSeeSky(new BlockPos(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ));
        }
    };

    public static boolean isSunlit(Entity entity)
    {
        float f = entity.getBrightness();
        return  f > 0.5F && UNDER_SKY.apply(entity);
    }

    public static boolean isMoonlit(Entity entity)
    {
        if (entity == null)
        {
            return false;
        }

        int tickInDay = (int) (entity.getEntityWorld().getWorldTime() % 24000);
        if (tickInDay > 167 && tickInDay < 11834)
        {
            return false;
        }
        return UNDER_SKY.apply(entity);
    }

    public static final Predicate<EntityLivingBase> LIVING = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase entity)
        {
            return entity != null && !entity.isEntityUndead();
        }
    };

    public static final Predicate<EntityLivingBase> UNDEAD = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase entity)
        {
            return entity != null && entity.isEntityUndead();
        }
    };


    public static final Predicate<EntityLivingBase> LIVING_HIGHER = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase entity)
        {
            return entity != null && !entity.isEntityUndead() && !(entity instanceof EntityAnimal);
        }
    };

    public static final Predicate<EntityLivingBase> USING_MODDED = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase entity)
        {
            if (entity == null)
            {
                return false;
            }

            for (EntityEquipmentSlot slot:
                 EntityEquipmentSlot.values()) {
                ItemStack stack = entity.getItemStackFromSlot(slot);
                if (stack.isEmpty())
                {
                    continue;
                }

                ResourceLocation regName = stack.getItem().getRegistryName();
                if (!regName.getResourceDomain().equals(CommonDef.MINECRAFT))
                {
                    return true;
                }
            }

            return false;
        }
    };

    public static final Predicate<EntityLivingBase> IS_EVIL = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase entity)
        {
            return (entity != null && IDLNBTUtil.GetIntAuto(entity, EVILNESS, 0) > 0);
        }
    };

    public static final Predicate<EntityLivingBase> NON_HALF_CREATURE = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase entity)
        {
            return entity instanceof EntityLiving || entity instanceof EntityPlayer;
        }
    };

    public static float getTemperature(Entity entity)
    {
        World world = entity.world;
        Biome biome = world.getBiome(entity.getPosition());

        return biome.getTemperature(entity.getPosition());
    }

    public static boolean canReflectGaze(EntityLivingBase base)
    {
        if (canReflectGaze(base.getHeldItemMainhand()) || canReflectGaze(base.getHeldItemOffhand()))
        {
            return true;
        }
        return false;
    }

    public static boolean canReflectGaze(ItemStack stack)
    {
//        if (stack.getItem() == ModItems.ITEM_DRESSING_MIRROR)
//        {
//            return true;
//        }
        return false;
    }

    public static void setPosition(Entity entity, BlockPos pos) {
        entity.setPosition(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
    }
}
