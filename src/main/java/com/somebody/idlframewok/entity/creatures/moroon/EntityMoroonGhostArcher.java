package com.somebody.idlframewok.entity.creatures.moroon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;
import static net.minecraft.entity.SharedMonsterAttributes.ATTACK_DAMAGE;

public class EntityMoroonGhostArcher extends EntityMoroonUnitBase implements IRangedAttackMob {
    private int stationaryCounter = 0;
    private int stationaryToInvisible = TICK_PER_SECOND * 5;

    //A creatures that does ranged attack and becomes invisible
    public EntityMoroonGhostArcher(World worldIn) {
        super(worldIn);
        spawn_without_darkness = true;
        spawn_without_moroon_ground = false;
        MinecraftForge.EVENT_BUS.register(this);
        experienceValue = 15;
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        //setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ModItems.helmetSniper));
        setSneaking(true);
        inflictBerserkBuff = false;
    }

    public void updateAITasks()
    {
        setSneaking(true);
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);

        if (wasRecentlyHit) {
            //if (lootingModifier >= rand.nextInt(10))
//            {
//                dropItem(ModItems.skill_hate_detect_sniper, 1);
//            }
//
//            if (lootingModifier >= rand.nextInt(10))
//            {
//                dropItem(ModItems.helmetSniper, 1);
//            }

            //dropItem(ModItems.skillFireBall, rand.nextInt(1 + lootingModifier));
        }
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackRanged(this, 0.3f, TICK_PER_SECOND * 2, 32.0F));
        //this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        //this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 16.0F, 0.6D, 1D));
//        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
//        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
//        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityMoroonUnitBase.class));
        applyGeneralAI();
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(32, 0.3, 20, 2, 20);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote)
        {
            //if (this.getMoveHelper().isUpdating()) {
                //double d0 = this.getMoveHelper().getSpeed();

               // if ((getAttackTarget() != null) || (this.getActivePotionEffect(ModPotions.INTERFERENCE) != null))
//                {
//                    stationaryCounter=0;
//                    if (this.getActivePotionEffect(MobEffects.INVISIBILITY) != null)
//                    {
//                        EntityUtil.TryRemoveGivenBuff(this, MobEffects.INVISIBILITY);
//                    }
//
//                }else {
//                    stationaryCounter++;
//                    if (stationaryCounter > stationaryToInvisible) {
//                        stationaryCounter = stationaryToInvisible;
//                        if (world.getWorldTime() % TICK_PER_SECOND == 0) {
//                            addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, TICK_PER_SECOND * 2, 0));
//                        }
//                    }
//                }
            //}
        }
    }

    /**
     * sets this entity's combat AI.
     */
//    public void setCombatTask()
//    {
//        if (this.world != null && !this.world.isRemote)
//        {
//            this.tasks.removeTask(this.aiAttackOnCollide);
//            this.tasks.removeTask(this.aiArrowAttack);
//            ItemStack itemstack = this.getHeldItemMainhand();
//
//            if (itemstack.getItem() == Items.BOW)
//            {
//                int i = 20;
//
//                if (this.world.getDifficulty() != EnumDifficulty.HARD)
//                {
//                    i = 40;
//                }
//
//                this.aiArrowAttack.setAttackCooldown(i);
//                this.tasks.addTask(4, this.aiArrowAttack);
//            }
//            else
//            {
//                this.tasks.addTask(4, this.aiAttackOnCollide);
//            }
//        }
//    }

//    @SubscribeEvent
//    public void onHurt(LivingHurtEvent evt)
//    {
//        if (evt.getEntity() == this)
//        {
//            Entity attacker = evt.getSource().getTrueSource();
//            if (attacker instanceof EntityLivingBase)
//            {
//                this.setAttackTarget((EntityLivingBase) attacker);
//            }
//            else {
//
//            }
//        }
//    }

    protected EntityArrow getArrow(float p_190726_1_)
    {
        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, this);
        entitytippedarrow.setEnchantmentEffectsFromEntity(this, p_190726_1_);
        return entitytippedarrow;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        //EntityMoroonBullet entityArrow = new EntityMoroonBullet(world, new ProjectileArgs((float) this.getEntityAttribute(ATTACK_DAMAGE).getAttributeValue()));
        EntityArrow entityArrow = getArrow(distanceFactor);
        double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX - this.posX;
        double d2 = d0 - entityArrow.posY;
        double d3 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.1F;
        entityArrow.shoot(d1, d2 + (double)f, d3, 2F, (float)(f + 7 - this.world.getDifficulty().getDifficultyId() * 2));
        double damage = this.getEntityAttribute(ATTACK_DAMAGE).getAttributeValue();
        if (target.isSneaking())
        {
            damage /= 5f;
        }
        entityArrow.setDamage(damage);
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entityArrow);
    }

    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(EntityMoroonGhostArcher.class, DataSerializers.BOOLEAN);
    @SideOnly(Side.CLIENT)
    public boolean isSwingingArms()
    {
        return ((Boolean)this.dataManager.get(SWINGING_ARMS)).booleanValue();
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        this.dataManager.set(SWINGING_ARMS, Boolean.valueOf(swingingArms));
    }
}
