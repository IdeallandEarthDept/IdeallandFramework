package com.somebody.idlframewok.entity.creatures.moroon;

import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityMorBlindingAssassin extends EntityMoroonUnitBase {
    int stealthNeedTick = TICK_PER_SECOND * 10;
    int stealthCounter = 0;
    //A creatures that teleports whenever its hit
    public EntityMorBlindingAssassin(World worldIn) {
        super(worldIn);
        spawn_without_darkness = false;
        spawn_without_moroon_ground = false;

        inflictBerserkBuff =false;

        experienceValue = 30;
        //MinecraftForge.EVENT_BUS.register(this);
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.FLINT_AND_STEEL));
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        //this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        //wont move through village
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityMoroonUnitBase.class}));
        applyGeneralAI();
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        setAttr(64, 0.4, 7, 2, 16);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        //IdlFramework.Log("Tick");
        if (!this.world.isRemote)
        {
            if (stealthCounter >= stealthNeedTick || this.world.isRainingAt(getPosition()))
            {
                if (world.getWorldTime() % TICK_PER_SECOND == 3) {
                    addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, TICK_PER_SECOND * 2, 0));

                }
                stealthCounter++;
            }
        }
    }

//    @SubscribeEvent
//    public void onHit(LivingHurtEvent evt)
//    {
//        Entity attacker = evt.getSource().getTrueSource();
//        if (attacker == this)
//        {
//
//
//        }
//    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean result = super.attackEntityAsMob(entityIn);
        stealthCounter = 0;
        EntityUtil.TryRemoveGivenBuff(this, MobEffects.INVISIBILITY);
        if (result)
        {
            if (entityIn instanceof EntityLivingBase)
            {
                EntityLivingBase target = (EntityLivingBase) entityIn;
                target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS,
                        (int) (TICK_PER_SECOND * this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()),
                        0));
                fiveRandomTeleport();
            }
        }

        return result;
    }

    private void fiveRandomTeleport()
    {
        int trial = 5;
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
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
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
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
        }

        return flag;
    }
}
