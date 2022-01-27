package com.somebody.idlframewok.entity.creatures.moroon;

import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.potion.ModPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityMoroonEliteMartialist extends EntityMoroonUnitBase {
    private float dodgeCooldown = 0;
    private float dodgeDiameter = 4f;
    private float cooldownPerDamage = 0.2f;

    //A creatures that can doge attacks
    public EntityMoroonEliteMartialist(World worldIn) {
        super(worldIn);
        spawn_without_darkness = true;
        spawn_without_moroon_ground = false;
        MinecraftForge.EVENT_BUS.register(this);
        experienceValue = 40;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);

        if (wasRecentlyHit) {
//            dropItem(ModItems.skillAttack1, rand.nextInt(1 + lootingModifier));
//            dropItem(ModItems.skillMartialSlam, rand.nextInt(1 + lootingModifier));
        }
    }

    protected void firstTickAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        //this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityMoroonUnitBase.class}));
        applyGeneralAI();
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16,0.45,6,2,25);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        //Idealland.Log("Tick");
        if (!this.world.isRemote)
        {
            if (dodgeCooldown > 0)
            {
                dodgeCooldown -= 1f/TICK_PER_SECOND;
            }
        }
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent evt)
    {
        if (evt.isCanceled())
        {
            return;
        }

        if (!this.world.isRemote) {
            if (evt.getEntity() == this)
            {
                Entity attacker = evt.getSource().getTrueSource();
                if (attacker instanceof EntityLivingBase)
                {
                    this.setAttackTarget((EntityLivingBase) attacker);
                    if (dodgeCooldown <= 0f)
                    {
                        fiveRandomTeleport();
                        evt.setCanceled(true);
                        this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
                        dodgeCooldown += 5 * cooldownPerDamage;
                    }
                    addPotionEffect(new PotionEffect(ModPotions.KB_RESIST, TICK_PER_SECOND, 1));
                }
            }

            if (evt.getSource().getTrueSource() == this)
            {
                dodgeCooldown += 5 * cooldownPerDamage / 2;
            }
        }
    }

    private void fiveRandomTeleport()
    {
        int trial = 30;
        while(trial > 0)
        {
            if (teleportRandomly())
            {
                break;
            }
            trial--;
        }
    }

    /**
     * Teleport the enderman to a random nearby position
     */
    protected boolean teleportRandomly()
    {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * dodgeDiameter;
        double d1 = this.posY + (this.rand.nextDouble() - 0.5D) * dodgeDiameter;
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * dodgeDiameter;
        return this.teleportTo(d0, d1, d2);
    }

    /**
     * Teleport the enderman to another entity
     */
    protected boolean teleportToEntity(Entity p_70816_1_)
    {
        Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - p_70816_1_.posY + (double)p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3d = vec3d.normalize();
        double d0 = 16.0D;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.x * 16.0D;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3d.y * 16.0D;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.z * 16.0D;
        return this.teleportTo(d1, d2, d3);
    }

    /**
     * Teleport the enderman
     */
    private boolean teleportTo(double x, double y, double z)
    {
        net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
        if (MinecraftForge.EVENT_BUS.post(event)) return false;
        boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());

        if (flag)
        {
            this.world.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);

        }

        return flag;
    }
}
