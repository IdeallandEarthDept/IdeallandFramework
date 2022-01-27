package com.somebody.idlframewok.entity.creatures.misc.mentor;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.somebody.idlframewok.util.EntityUtil.IS_EVIL;
import static com.somebody.idlframewok.util.MessageDef.MSG_BEGIN_EVIL;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.EVILNESS;

@Mod.EventBusSubscriber(modid = Idealland.MODID)
public class EntityMentorBase extends EntityModUnit {
    public Potion giftPotion = MobEffects.NIGHT_VISION;
    public Potion attackPotion = MobEffects.BLINDNESS;

    public float buffMin = 30;//seconds
    public float buffExtend = 30;//seconds;
    public float attackCurseDuration = 5f;//seconds

    public EntityMentorBase(World worldIn) {
        super(worldIn);
        dontDespawn = true;
        setCanPickUpLoot(true);
    }

    protected void firstTickAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 2.0D, false));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityTNTPrimed.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityVillager.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, AbstractIllager.class, true, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, true, IS_EVIL));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDeath(LivingDeathEvent deathEvent)
    {
        EntityLivingBase livingBase = deathEvent.getEntityLiving();
        World world = livingBase.world;
        if (!world.isRemote)
        {
            if (livingBase instanceof EntityMentorBase)
            {
                if (deathEvent.getSource().getTrueSource() instanceof EntityPlayer)
                {
                    IDLNBTUtil.setIntAuto(livingBase, EVILNESS, 1);
                    CommonFunctions.SafeSendMsgToPlayer(TextFormatting.RED, livingBase, MSG_BEGIN_EVIL);
                }
            }
        }
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
//        if (wasRecentlyHit) {
//            ItemStack stack = new ItemStack(ModItems.ITEM_SPELL_SELF);
//            ModItems.ITEM_SPELL_SELF.randomActivate(stack, 1);
//            entityDropItem(stack, 1f);
//        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16.0D, 0.2D, 5.0D, 5.0D, 40.0D);
    }

    public float getBuffLength()
    {
        return getLevel() * (buffMin + buffExtend * rand.nextFloat());
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        boolean result = super.attackEntityAsMob(target);
        if (result && target instanceof EntityLivingBase)
        {
            EntityUtil.ApplyBuff((EntityLivingBase) target, attackPotion, 0, attackCurseDuration);
        }
        return result;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (world.isRemote)
        {
            return true;
        }

        if (player.getActivePotionEffect(giftPotion) == null)
        {
            EntityUtil.ApplyBuff(player, giftPotion, 0, getBuffLength());
            world.playSound(posX,posY,posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 1f, 1f, false);
            return true;
        }
        return false;
    }
}
