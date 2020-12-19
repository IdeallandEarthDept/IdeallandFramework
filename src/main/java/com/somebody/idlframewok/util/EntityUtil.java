package com.somebody.idlframewok.util;

import com.somebody.idlframewok.IdlFramework;
import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.meta.MetaUtil;
import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static net.minecraft.entity.SharedMonsterAttributes.*;
import static net.minecraftforge.fml.common.gameevent.TickEvent.Type.WORLD;

@Mod.EventBusSubscriber(modid = IdlFramework.MODID)
public class EntityUtil {
    public static void simpleKnockBack(float power, EntityLivingBase source, EntityLivingBase target)
    {
        target.knockBack(source, power, (source.posX - target.posX), (source.posZ - target.posZ));
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

    public static void TryRemoveGivenBuff(EntityLivingBase livingBase, Potion potion)
    {
        //washes away debuff
        Collection<PotionEffect> activePotionEffects = livingBase.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion() == potion){
                livingBase.removePotionEffect(buff.getPotion());
                return;
            }
        }
    }

    public static boolean ApplyBuff(EntityLivingBase livingBase, Potion potion, int level, float seconds)
    {
        if (livingBase == null || potion == null)
        {
            IdlFramework.LogWarning("Trying to apply illegal potion");
            return false;
        }
        livingBase.addPotionEffect(new PotionEffect(potion, (int) (seconds * TICK_PER_SECOND) + 1, level));
        return true;
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
        String modid = er.getContainer().getModId();
        return modid.equals("minecraft");
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
        //IdlFramework.Log("Atk ER.modid is %s, name is %s", modid, er.getRegistryName());

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
        return (creature instanceof EntityModUnit && ((EntityModUnit) creature).is_mechanic);
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

    public static Vec3d GetRandomAroundUnderfoot(EntityLivingBase entity, float radius)
    {
        float angle = entity.getRNG().nextFloat() * 6.282f;
        return new Vec3d(entity.posX + Math.sin(angle),  entity.posY, entity.posZ + Math.cos(angle));
    }

    public static Vec3d GetRandomAroundPos(Vec3d pos, float radius, Random rng)
    {
        float angle = rng.nextFloat() * 6.282f;
        return new Vec3d(pos.x + Math.sin(angle), pos.y, pos.z + Math.cos(angle));
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
            angle += 1.0f;//ModConfig.DEBUG_CONF.HALO_OMEGA;
            angle %= 6.282f;
        }
    }

    public static void spawnHaloParticleAround(EntityLivingBase entity, EnumParticleTypes particleTypes, float radius)
    {
        for (int i = 0; i < 10; i++)
        {
            float deltaOmega = 1.0f * i;//ModConfig.DEBUG_CONF.HALO_OMEGA;
            Vec3d pos = new Vec3d(entity.posX + radius * Math.sin(angle + deltaOmega),  entity.posY + 0.1f * entity.getRNG().nextFloat(), entity.posZ + radius * Math.cos(angle + deltaOmega));
            entity.world.spawnParticle(particleTypes, pos.x, pos.y, pos.z, 0,0,0);
        }
    }

    public static Faction faction(EntityLivingBase creature)
    {
        if (isMoroonTeam(creature))
        {
            return Faction.MOROON;
        } else if (isIdeallandTeam(creature))
        {
            return Faction.IDEALLAND;
        }else if (creature instanceof EntityZombie)
        {
            return Faction.MOB_VAN_ZOMBIE;
        }
        else if (creature instanceof IMob)
        {
            return Faction.MOB_VANILLA;
        }else if (creature instanceof EntityPlayer)
        {
            return Faction.PLAYER;
        }else
        {
            return Faction.CRITTER;
        }
    }

    public static ATTITUDE getAttitude(EntityLivingBase subject, EntityLivingBase object)
    {
        if (subject == null || object == null)
        {
            return ATTITUDE.IGNORE;
        }

        if (subject.isOnSameTeam(object))
        {
            return ATTITUDE.FRIEND;
        }
        return getAttitude(faction(subject), faction(object));
    }

    public static ATTITUDE getAttitude(Faction subject, EntityLivingBase object)
    {
        return getAttitude(subject, faction(object));
    }

    public static ATTITUDE getAttitude(Faction subject, Faction object)
    {
        if (subject == object)
        {
            return ATTITUDE.FRIEND;
        }

        if (subject == Faction.CRITTER || object == Faction.CRITTER)
        {
            return ATTITUDE.IGNORE;
        }

        switch (subject)
        {
            case PLAYER:
            case IDEALLAND:
                switch (object)
                {
                    case IDEALLAND:
                        return ATTITUDE.FRIEND;
                    case MOB_VANILLA:
                    case MOB_VAN_ZOMBIE:
                    case MOROON:
                        return ATTITUDE.HATE;
                    default:
                        return ATTITUDE.IGNORE;
                }
            case MOB_VANILLA:
                switch (object)
                {
                    case IDEALLAND:
                    case PLAYER:
                        return ATTITUDE.HATE;
                    case MOB_VAN_ZOMBIE:
                        return ATTITUDE.FRIEND;

                    default:
                        return ATTITUDE.IGNORE;
                }

            case MOB_VAN_ZOMBIE:
                switch (object)
                {
                    case IDEALLAND:
                    case PLAYER:
                        return ATTITUDE.HATE;

                    case MOB_VANILLA:
                        return ATTITUDE.FRIEND;

                    default:
                        return ATTITUDE.IGNORE;
                }

            case MOROON:
                switch (object)
                {
                    case IDEALLAND:
                    case MOB_VAN_ZOMBIE:
                    case PLAYER:
                        return ATTITUDE.HATE;

                    default:
                        return ATTITUDE.IGNORE;
                }
        }
        return ATTITUDE.IGNORE;
    }

    public static Vec3d GetRandomAround(EntityLivingBase entity, float radius)
    {
        float angle = entity.getRNG().nextFloat() * 6.282f;
        return new Vec3d(entity.posX + Math.sin(angle), entity.getEyeHeight() + entity.posY, entity.posZ + Math.cos(angle));
    }

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
            return  p_apply_1_ != null && (getAttitude(Faction.IDEALLAND, p_apply_1_)==ATTITUDE.FRIEND);
        }
    };

    public static final Predicate<EntityLivingBase> HostileToIdl = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && (getAttitude(Faction.IDEALLAND, p_apply_1_)==ATTITUDE.HATE) && (p_apply_1_).attackable();
        }
    };

    public static final Predicate<EntityLivingBase> HostileToIdl_AIR = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && (getAttitude(Faction.IDEALLAND, p_apply_1_)==ATTITUDE.HATE) && (p_apply_1_).attackable() && !p_apply_1_.onGround;
        }
    };

    public static final Predicate<EntityLivingBase> HostileToMor = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && (getAttitude(Faction.MOROON, p_apply_1_)==ATTITUDE.HATE) && (p_apply_1_).attackable();
        }
    };

    public static final Predicate<EntityLivingBase> IsVanilla = new Predicate<EntityLivingBase>()
    {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_)
        {
            return  p_apply_1_ != null && isVanillaResident((p_apply_1_)) && (p_apply_1_).attackable();
        }
    };
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

        //IdlFramework.Log("Value:%s: %.2f->%.2f", modifier.getName(), valueBefore, valueAfter);
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
        attribute.applyModifier(new AttributeModifier(uuid, "pwr up percent",  val, 1));
        double valueAfter = attribute.getAttributeValue();

        if (modifier == null)
        {
            modifier = attribute.getModifier(uuid);
        }

        //IdlFramework.Log("Value:%s: %.2f->%.2f", modifier.getName(), valueBefore, valueAfter);
        return true;
    }

    public enum Faction{
        PLAYER,
        IDEALLAND,
        MOB_VANILLA,
        MOB_VAN_ZOMBIE,
        MOROON,
        CRITTER,
    }

    public enum ATTITUDE{
        HATE,
        IGNORE,
        FRIEND
    }

    public static Biome getBiomeForEntity(Entity entity)
    {
        World world = entity.getEntityWorld();
        return world.getBiomeForCoordsBody(entity.getPosition());
    }

    public static boolean isSunlit(Entity entity)
    {
        float f = entity.getBrightness();
        return  f > 0.5F && entity.world.canSeeSky(new BlockPos(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ));
    }

    public static boolean isMoonlit(Entity entity)
    {
        int tickInDay = (int) (entity.getEntityWorld().getWorldTime() % 24000);
        if (tickInDay > 167 && tickInDay < 11834)
        {
            return false;
        }
        return entity.world.canSeeSky(new BlockPos(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ));
    }
}
