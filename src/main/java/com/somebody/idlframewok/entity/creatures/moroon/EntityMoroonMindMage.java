package com.somebody.idlframewok.entity.creatures.moroon;

import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.IDLGeneral;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class EntityMoroonMindMage extends EntityMoroonUnitBase implements IRangedAttackMob {

    public EntityMoroonMindMage(World worldIn) {
        super(worldIn);
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Blocks.LIT_PUMPKIN));
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        //this.tasks.addTask(2, new EntityAIAttackRanged((IRangedAttackMob) this, 1.0D, 60, 10.0F));
       // this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 16.0F, 0.6D, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityMoroonUnitBase.class}));
        applyGeneralAI();
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
//        if (rand.nextFloat() < 0.1f * getLevel())
//        {
//            dropItem(ModItems.ITEM_HELM_SANITY, 1 + rand.nextInt(2 + lootingModifier));
//        }
//
//        if (rand.nextFloat() < 0.01f * getLevel() * (1+lootingModifier))
//        {
//            dropItem(ModItems.ITEM_EL_PSY_CONGROO, 1);
//        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(32.0D, 0.33000000417232513D, 3.0D, 0.0D, 14.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        //IdlFramework.Log("Tick");
        if (!this.world.isRemote)
        {
            //if (world.getWorldTime() % TICK_PER_SECOND == 1 && getActivePotionEffect(ModPotions.INTERFERENCE) == null)
            {
                float range = (float) getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();

                if (getRank() > 1)
                {
                    //strong mind mages resists mind magic, won't attack allies
                    if (EntityUtil.getAttitude(this, getAttackTarget()) == EntityUtil.ATTITUDE.FRIEND)
                    {
                        setAttackTarget(null);
                    }
                }

                List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class,
                        IDLGeneral.ServerAABB(getPositionEyes(0f), range));

                for (EntityLivingBase living:
                        entities
                     ) {
                    if (living == this)
                        continue;

                    if (living instanceof EntityPlayer)
                    {
                        //ignore creative players
                        if (((EntityPlayer)living).capabilities.isCreativeMode)
                        {
                            continue;
                        }
                    }

                    EntityUtil.ATTITUDE attitude = EntityUtil.getAttitude(this, living);
                    if (attitude == EntityUtil.ATTITUDE.FRIEND) {
                        ApplyMindControlToFriend(living);
                    }
                    else if (attitude == EntityUtil.ATTITUDE.HATE || living == getAttackTarget())
                    {
                        ApplyMindControlToEnemy(living);
                    }
                    else if (living instanceof EntityLiving)
                    {
                        EntityLivingBase theirTarget = ((EntityLiving) living).getAttackTarget();
                        if (EntityUtil.getAttitude(this, theirTarget) == EntityUtil.ATTITUDE.FRIEND)
                        {
                            ApplyMindControlToEnemy(living);
                        }
                    }
                }
            }
        }
        else {
            //remote
        }
    }

    public float getSeconds()
    {
        return getLevel();
    }

    public int getBuffLevel()
    {
        return getRank() - 1;
    }


    public void ApplyMindControlToEnemy(EntityLivingBase livingBase)
    {
//        if (livingBase.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemHelmSanity)
//        {
//            return;
//        }

        if (getAttackTarget() == null)
        {
            Path path = getNavigator().getPathToEntityLiving(livingBase);
            if (path != null)
            {
                setAttackTarget(livingBase);
            }
            else {
                getLookHelper().setLookPosition(livingBase.posX, livingBase.posY, livingBase.posZ, 0.1f, 0.1f);
            }
        }

        int rank = getRank();

        EntityUtil.TryRemoveGivenBuff(livingBase, MobEffects.INVISIBILITY);
        EntityUtil.TryRemoveGivenBuff(livingBase, MobEffects.NIGHT_VISION);

        //EntityUtil.ApplyBuff(livingBase, ModPotions.CRIT_CHANCE_MINUS, getBuffLevel(), getSeconds());
        EntityUtil.ApplyBuff(livingBase, MobEffects.GLOWING, 0, getLevel());
        if (rank >= 2)
        {
            EntityUtil.ApplyBuff(livingBase, MobEffects.NAUSEA, getBuffLevel(), getSeconds());
            EntityUtil.ApplyBuff(livingBase, MobEffects.WEAKNESS, getBuffLevel(), getSeconds());
            if (rank >= 3)
            {
                EntityUtil.ApplyBuff(livingBase, MobEffects.BLINDNESS,0, getSeconds());
                EntityUtil.ApplyBuff(livingBase, MobEffects.SLOWNESS, getBuffLevel(), getSeconds());
            }
        }

        TryChangeTargetToEnemy(livingBase);
    }

    public void ApplyMindControlToFriend(EntityLivingBase livingBase)
    {
//        if (livingBase.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemHelmSanity)
//        {
//            return;
//        }

        int level = getLevel();
        int rank = getRank();

        EntityUtil.ApplyBuff(livingBase, MobEffects.INVISIBILITY, getBuffLevel(), getSeconds());
        EntityUtil.TryRemoveGivenBuff(livingBase, MobEffects.GLOWING);
        EntityUtil.TryRemoveGivenBuff(livingBase, MobEffects.BLINDNESS);
        EntityUtil.TryRemoveGivenBuff(livingBase, MobEffects.NAUSEA);

        if (rank >= 2)
        {
            //EntityUtil.ApplyBuff(livingBase, ModPotions.CRIT_CHANCE_PLUS, getBuffLevel(), getSeconds());
            if (rank >= 3)
            {
                EntityUtil.ApplyBuff(livingBase, MobEffects.HASTE, getBuffLevel(), getSeconds());
            }
        }

        TryChangeTargetToEnemy(livingBase);
    }

    public void TryChangeTargetToEnemy(EntityLivingBase livingBase)
    {
        if (livingBase instanceof EntityLiving)
        {
            //change their targets
            EntityLiving entityLiving = (EntityLiving) livingBase;
            EntityLivingBase theirTarget = ((EntityLiving) livingBase).getAttackTarget();
            if (EntityUtil.getAttitude(this, theirTarget) != EntityUtil.ATTITUDE.HATE)
            {
                //try to make them attack only if the path ready
                EntityLivingBase myTarget = getAttackTarget();
                if (myTarget != null)
                {
                    Path path = entityLiving.getNavigator().getPathToEntityLiving(myTarget);
                    if (path != null)
                    {
                        setAttackTarget(myTarget);
                    }
                    else {
                        //  target not available for mind-control subject, just cancel their current friendly fire
                        //and warn them of the target. Note this will weaken the disruption to enemy, too.
                        getLookHelper().setLookPosition(livingBase.posX, livingBase.posY, livingBase.posZ, 0.1f, 0.1f);
                        setAttackTarget(null);
                    }
                }
                else {
                    setAttackTarget(null);
                }
            }
        }
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
//            if (getRank() <= 2)
//            {
//                return;
//            }
//
//            double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
//            double d1 = target.posX + target.motionX - this.posX;
//            double d2 = d0 - this.posY;
//            double d3 = target.posZ + target.motionZ - this.posZ;
//            float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
//            PotionType potiontype = PotionTypes.HARMING;
//
//            if (f >= 8.0F && !target.isPotionActive(MobEffects.SLOWNESS))
//            {
//                potiontype = PotionTypes.SLOWNESS;
//            }
//            else if (f <= 3.0F && !target.isPotionActive(MobEffects.WEAKNESS) && this.rand.nextFloat() < 0.25F)
//            {
//                potiontype = PotionTypes.WEAKNESS;
//            }
//
//            EntityPotion entitypotion = new EntityPotion(this.world, this, PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potiontype));
//            entitypotion.rotationPitch -= -20.0F;
//            entitypotion.shoot(d1, d2 + (double)(f * 0.2F), d3, 0.75F, 8.0F);
//            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
//            this.world.spawnEntity(entitypotion);

    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }
}
