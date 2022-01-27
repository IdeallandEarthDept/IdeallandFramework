package com.somebody.idlframewok.entity.creatures.ideallandTeam;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.entity.creatures.ai.EntityAIFollowSomething;
import com.somebody.idlframewok.entity.creatures.ai.IHasOwner;
import com.somebody.idlframewok.entity.creatures.mobs.IPlayerDoppleganger;
import com.somebody.idlframewok.item.skills.classfit.EnumSkillClass;
import com.somebody.idlframewok.item.skills.classfit.SkillClassUtil;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static com.somebody.idlframewok.util.CommonDef.UUID_DEFAULT;

public class EntityAlterego extends EntityIdeallandUnitBase implements IPlayerDoppleganger, IHasOwner {

    public enum EnumActionMode {
        NONE,
        ATTACK,
        DEFEND,
        FOLLOW;

        public static EnumActionMode fromInt(int val)
        {
           val = CommonFunctions.clamp(val, 0, EnumActionMode.values().length-1);
           return EnumActionMode.values()[val];
        }
    }

    EnumActionMode currentMode = EnumActionMode.DEFEND;

    final int FOLLOW_PRIORITY = 4;
    final int COMBAT_PRIORITY = 5;

    float loseHealthRate = 0.1f;//when the player is offline, drain health at 2/sec

    float recoverRate = 1f/TICK_PER_SECOND;
    float maxRecoverDist = 8f;

    float magic_damage_factor = 1.2f;
    float non_magic_damage_factor = 0.8f;

    float maxDefendDistSqr = 64f;

    Predicate<EntityLiving> ATTACK_PREDICATE = p_apply_1_ -> p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper);;

    private final EntityAIAttackMelee AI_MELEE = new EntityAIAttackMelee(this, 1.5D, true);
    private final EntityAIFollowSomething AI_FOLLOW = new EntityAIFollowSomething(this, 1.5D, 4, 32);
    private final EntityAIHurtByTarget TARGET_REVENGE = new EntityAIHurtByTarget(this, false);
    private final EntityAINearestAttackableTarget TARGET_SEEKING = new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, false, true,
            ATTACK_PREDICATE);


    public EntityPlayer player;
    protected static final DataParameter<String> PLAYER_UUID = EntityDataManager.<String>createKey(EntityAlterego.class, DataSerializers.STRING);

    public EntityAlterego(World worldIn) {
        super(worldIn);
        experienceValue = 0;
        dontDespawn = true;
        melee_atk = false;
        setAlwaysRenderNameTag(true);
    }

    public void imitatePlayer(EntityPlayer player)
    {
        imitateLiving(player);
        this.player = player;
        this.dataManager.set(PLAYER_UUID, player.getUniqueID().toString());
        this.setHealth(player.getHealth());
        clearEquips();
        setDropItemsWhenDead(false);
    }

    @Override
    public EntityLivingBase getOwner() {
        return player;
    }

    public UUID getPlayerUUID()
    {
        try {
            return UUID.fromString(this.dataManager.get(PLAYER_UUID));
        }
        catch (Exception e)
        {
            return UUID_DEFAULT;
        }
    }

    protected void entityInit()
    {
        this.dataManager.register(PLAYER_UUID, UUID_DEFAULT.toString());
        super.entityInit();
    }

    @Override
    public EntityPlayer getPlayer() {
        return player;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return super.attackEntityFrom(source,
                (source.isMagicDamage() ? magic_damage_factor : non_magic_damage_factor) * amount);
    }

//    void imitateEquip()
//    {
//        if (!world.isRemote)
//        {
//            int level = SkillClassUtil.getClassLevelForLiving(player, EnumSkillClass.EGO_TWIN);
//            if (level >= 2)
//            {
//                ItemStack handItem = player.getHeldItemMainhand().copy();
//                if (!getHeldItemMainhand().isItemEqual(handItem))
//                {
//                    //this enchantment prevents the player from looting from its alterego to copy items.
//                    handItem.addEnchantment(ModEnchantmentInit.FADING, 10);
//                    handItem.addEnchantment(Enchantments.VANISHING_CURSE, 1);
//                    setHeldItem(EnumHand.MAIN_HAND, handItem);
//                }
//
//                //optimization. Armor and potion are more costly.
//                if (CommonFunctions.isSecondTick(world))
//                {
//                    if (level >= 3)
//                    {
//                        for (EntityEquipmentSlot slot: EntityEquipmentSlot.values()
//                             ) {
//                            if (slot.getSlotType() == EntityEquipmentSlot.Type.ARMOR)
//                            {
//                                if (!getItemStackFromSlot(slot).isItemEqual(player.getItemStackFromSlot(slot)))
//                                {
//                                    setItemStackToSlot(slot, player.getItemStackFromSlot(slot));
//                                }
//                            }
//                        }
//
//                        if (level >= 4)
//                        {
//                            clearActivePotions();
//                            Collection<PotionEffect> ownerEffects = player.getActivePotionEffects();
//                            for (PotionEffect effect: ownerEffects
//                                 ) {
//                                if (!effect.getPotion().isBadEffect())
//                                {
//                                    addPotionEffect(effect);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        if (!world.isRemote)
        {
            int level = SkillClassUtil.getClassLevelForLiving(player, EnumSkillClass.EGO_TWIN);
            if (level >= 5)
            {
                List<EntityLiving> livings = EntityUtil.getEntitiesWithinAABB(world,
                        EntityLiving.class, IDLGeneral.ServerAABB(player.getPositionVector(), 8f), ATTACK_PREDICATE);

                //Livings does not include players
                for (EntityLiving target: livings) {
                    EntityUtil.ApplyBuff(target, MobEffects.SLOWNESS, 1, 5f);
                }
            }
        }
    }

    int PARTICLE_PER_TICK = 3;
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (world.isRemote)
        {
            //EntityUtil.spawnHaloParticleAround(this, EnumParticleTypes.FLAME, 1);
            if (player != null)
            {
                float factorBase = (float)(world.getWorldTime() % TICK_PER_SECOND) / TICK_PER_SECOND;
                for (int i = 0; i < PARTICLE_PER_TICK; i++)
                {
                    //lerp
                    float factor = factorBase + getRNG().nextFloat() * 0.1f;
                    factor = CommonFunctions.clamp(factor,0f,1f);
                    Vec3d pos = (player.getPositionEyes(0).scale(factor))
                            .add(getPositionEyes(0).scale(1 - factor));
                    world.spawnParticle(EnumParticleTypes.SPELL, pos.x, pos.y, pos.z,0,0.1,0);
                }
            }
        }
        else {
            if (player == null)
            {
                player = world.getPlayerEntityByUUID(UUID.fromString(this.dataManager.get(PLAYER_UUID)));
                if (player == null && !world.isRemote)
                {
                    setHealth(getHealth() - loseHealthRate);
                }
            }
            else {
                float distToOwner = getDistance(player);
                //Idealland.Log("dist to ownwer:%s",distToOwner);
                if (distToOwner < maxRecoverDist)
                {
                    //recovers if owner is nearby
                    heal((1 - distToOwner / maxRecoverDist) * recoverRate);
                }

                if (currentMode == EnumActionMode.DEFEND)
                {
                    if (getAttackTarget() == null)
                    {
                        List<EntityLiving> livings = EntityUtil.getEntitiesWithinAABB(world,
                                EntityLiving.class, IDLGeneral.ServerAABB(player.getPositionVector(), 8f), ATTACK_PREDICATE);

                        //first, attack those who are attacking the player
                        for (EntityLiving target: livings) {
                            if (target.getAttackTarget() == player)
                            {
                                setAttackTarget(target);
                                break;
                            }
                        }

                        //second, attack any are too close around the player
                        if (getAttackTarget() == null)
                        {
                            if (livings.size() > 0)
                            {
                                setAttackTarget(livings.get(0));
                            }
                        }
                    }
                    else {//if already has a target
                        EntityLivingBase target = getAttackTarget();
                        if (target.getDistanceSq(player) > maxDefendDistSqr)
                        {
                            if (target instanceof EntityLiving && ((EntityLiving) target).getAttackTarget() == null)
                            {
                                //give up if it's too far and not attacking anything.
                                setAttackTarget(null);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16.0D, 0.33000000417232513D, 1.0D, 0, 20.0D);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setBehaviorMode(EnumActionMode.fromInt(compound.getInteger(IDLNBTDef.STATE)));
        this.dataManager.set(PLAYER_UUID, compound.getString(IDLNBTDef.OWNER_UUID));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger(IDLNBTDef.STATE, currentMode.ordinal());
        //use random to prevent bad format exception
        compound.setString(IDLNBTDef.OWNER_UUID, player == null ? UUID.randomUUID().toString() : player.getUniqueID().toString());
        super.writeEntityToNBT(compound);
    }

    public void setBehaviorMode(EnumActionMode action)
    {
        currentMode = action;
        this.targetTasks.removeTask(TARGET_REVENGE);
        this.targetTasks.removeTask(TARGET_SEEKING);
        switch (action)
        {
            case NONE:
                Idealland.Log("Set mode to none");
                this.tasks.addTask(FOLLOW_PRIORITY, AI_FOLLOW);
                this.tasks.addTask(COMBAT_PRIORITY, AI_MELEE);
                break;
            case ATTACK:
                this.tasks.removeTask(AI_FOLLOW);
                this.tasks.addTask(COMBAT_PRIORITY, AI_MELEE);
                this.targetTasks.addTask(1, TARGET_SEEKING);
                break;
            case DEFEND:
                this.tasks.addTask(FOLLOW_PRIORITY, AI_FOLLOW);
                this.tasks.addTask(COMBAT_PRIORITY, AI_MELEE);
                this.targetTasks.addTask(1, TARGET_REVENGE);
                break;
            case FOLLOW:
                this.tasks.addTask(FOLLOW_PRIORITY, AI_FOLLOW);
                this.tasks.removeTask(AI_MELEE);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
    }

//    protected void firstTickAI()
//    {
//        this.tasks.addTask(0, new EntityAISwimming(this));
//
//        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTNTPrimed.class, 8.0F, 0.6D, 0.6D));
//
//        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
//        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
//        this.tasks.addTask(8, new EntityAILookIdle(this));
//        this.applyGeneralAI();
//    }

    protected void applyGeneralAI()
    {
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, false, true,
//                (Predicate<EntityLiving>) p_apply_1_ -> p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper)));

        ((PathNavigateGround)this.getNavigator()).setEnterDoors(true);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 0xcccccc;
    }
}
