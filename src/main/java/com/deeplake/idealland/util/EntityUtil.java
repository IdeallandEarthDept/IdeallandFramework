package com.deeplake.idealland.util;

import com.deeplake.idealland.IdlFramework;
import com.deeplake.idealland.entity.creatures.EntityModUnit;
import com.deeplake.idealland.meta.MetaUtil;
import com.google.common.base.Predicate;
import com.sun.istack.internal.NotNull;
import net.minecraft.enchantment.EnchantmentHelper;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;
import static net.minecraft.entity.SharedMonsterAttributes.*;

public class EntityUtil {
    public static void TryRemoveDebuff(EntityLivingBase livingBase)
    {
        //washes away debuff
        Collection<PotionEffect> activePotionEffects = livingBase.getActivePotionEffects();
        for (int i = 0; i < activePotionEffects.size(); i++) {
            PotionEffect buff = (PotionEffect)activePotionEffects.toArray()[i];
            if (buff.getPotion().isBadEffect()){
                livingBase.removePotionEffect(buff.getPotion());
            }
            else
            {

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
//
//    public static EntityLivingBase FindLover(EntityLivingBase source, float range, ModEnchantmentLover enchant)
//    {
//        if (EnchantmentHelper.getMaxEnchantmentLevel(enchant, source) == 0)
//        {
//            return null;
//        }
//
//        EntityLivingBase result = null;
//        Vec3d sourcePos = source.getPositionEyes(0f);
//        source.playSound(SoundEvents.ENTITY_LIGHTNING_IMPACT, 1f, 1f);
//        //Damage nearby entities
//        List<EntityLivingBase> list = source.world.getEntitiesWithinAABB(EntityLivingBase.class, IDLGeneral.ServerAABB(sourcePos.addVector(-range, -range, -range), sourcePos.addVector(range, range, range)));
//        for (EntityLivingBase creature : list) {
//            if (creature != source) {
//                if (EnchantmentHelper.getMaxEnchantmentLevel(enchant, creature) > 0)
//                {
//                    if (result != null)
//                    {
//                        //more than one lover found
//                        return null;
//                    }
//                    else {
//                        result = creature;
//                    }
//                }
//            }
//        }
//
//        return result;
//    }

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
}
