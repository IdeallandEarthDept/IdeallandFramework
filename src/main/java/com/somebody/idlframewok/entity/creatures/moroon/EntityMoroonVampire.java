package com.somebody.idlframewok.entity.creatures.moroon;

import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityIdeallandUnitBase;
import com.somebody.idlframewok.util.IDLGeneral;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityMoroonVampire extends EntityMoroonUnitBase {
    //Summons bats
    //Attacking summons bats
    //Consume bats to revive
    //Become bats when killed (can be used for other vampires)
    int summonBatCount = 4;
    float summonReqMP = 60f;


    float reviveConsumeRange = 16;
    int reviveConsumeCount = 2;


    @Override
    public boolean isEntityUndead() {
        return true;
    }

    public EntityMoroonVampire(World worldIn) {
        super(worldIn);

        spawn_without_darkness = false;
        spawn_without_moroon_ground = true;
        MinecraftForge.EVENT_BUS.register(this);
        experienceValue = 40;

        MPMax = 100f;
        MP = MPMax;

        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.MUTTON));
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);

//        if (wasRecentlyHit) {
//            dropItem(ModItems.skillAttack1, rand.nextInt(1 + lootingModifier));
//        }
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        //this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 8.0F, 0.6D, 1d));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        //this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityMoroonVampire.class}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIdeallandUnitBase.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityZombie.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));

        ((PathNavigateGround)this.getNavigator()).setEnterDoors(true);
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntitySheep.class, false));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityCow.class, false));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        setAttr(32, 0.4, 6.0, 2, 24);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        //IdlFramework.Log("Tick");
        if (!this.world.isRemote)
        {
            if (world.getWorldTime() % TICK_PER_SECOND == 0) {
                if (getAttackTarget() != null && trySpendMana(summonReqMP))
                {
                    float angle = getRNG().nextFloat() * 6.28f;
                    for (int i = 1; i <= summonBatCount; i++)
                    {
                        EntityBat bat = new EntityBat(world);
                        angle += 6.28f / summonBatCount;
                        bat.setPosition(posX + Math.cos(angle), posY, posZ + Math.sin(angle));
                        bat.spawnExplosionParticle();
                        world.spawnEntity(bat);
                    }
                    setHealth(0.8f * getHealth());

                }
            }
        }
    }

    public boolean attackEntityAsMob(Entity target) {
        boolean result = super.attackEntityAsMob(target);
        if (result)
        {
            this.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 3*TICK_PER_SECOND, 0));
            heal(1f);
        }
        return result;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void OnDeath(LivingDeathEvent ev)
    {
        if (!world.isRemote)
        {
            EntityLivingBase diedOne = ev.getEntityLiving();
            if (diedOne == this)
            {
                List<EntityBat> entities = world.getEntitiesWithinAABB(EntityBat.class,
                        IDLGeneral.ServerAABB(getPositionEyes(0f), reviveConsumeRange));

                if (entities.size() >= reviveConsumeCount)
                {
                    ev.setCanceled(true);
                    for (int i = 0; i < reviveConsumeCount; i++)
                    {
                        EntityBat entityBat = entities.get(i);
                        this.heal(entityBat.getHealth() / 2);
                        entityBat.setDead();
                        this.spawnRunningParticles();
                        this.playSound(SoundEvents.ENTITY_WITCH_AMBIENT, 1f, 1f);
                    }
                }
                else {
                    float angle = getRNG().nextFloat() * 6.28f;
                    for (int i = 1; i <= summonBatCount; i++)
                    {
                        EntityBat bat = new EntityBat(world);
                        angle += 6.28f / summonBatCount;
                        bat.setPosition(posX + Math.cos(angle), posY, posZ + Math.sin(angle));
                        bat.spawnExplosionParticle();
                        world.spawnEntity(bat);
                    }
                }
            }

            DamageSource source = ev.getSource();
            if (source != null)
            {
                Entity killer = source.getTrueSource();
                if (killer == this)
                {
                    EntityBat bat = new EntityBat(world);
                    bat.setPosition(diedOne.posX , diedOne.posY, diedOne.posZ);
                    bat.spawnExplosionParticle();
                    world.spawnEntity(bat);
                    heal(diedOne.getMaxHealth() / 10f);
                }
            }
        }

    }


}
