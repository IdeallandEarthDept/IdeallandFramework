package com.somebody.idlframewok.entity.creatures.ideallandTeam;

import com.somebody.idlframewok.entity.creatures.ai.EntityAITurretAttack;
import com.somebody.idlframewok.item.misc.ItemNanoMender;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityIdlTurret extends EntityIdeallandUnitBase {
    public EntityIdlTurret(World worldIn) {
        super(worldIn);
        is_mechanic = true;
        is_pinned_on_ground = true;
        setSize(0.5f, 0.5f);
    }

    @Override
    protected void firstTickAI() {
        super.firstTickAI();
        applyAttackingAI();
        //this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLivingBase.class, 4.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));

        applyTargetingAI();
    }

    protected void applyAttackingAI()
    {
        this.tasks.addTask(1, new EntityAITurretAttack(this, 0.01f));
    }

    protected void applyTargetingAI()
    {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, EntityIdlTurret.class));
        this.targetTasks.addTask(2, new ModAIAttackNearest<>(this, EntityLivingBase.class,
                0, true, true, EntityUtil.HostileToIdl
        ));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16.0D, 0D, 3.0D, 1.0D, 20.0D);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        //ApplyGeneralLevelBoost(difficulty, livingdata);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void onUpdate() {
        if ((world.getWorldTime() % TICK_PER_SECOND == 0) &&
                this.getAttackTarget() != null)
        {
            //prevent the turrets from firing at each other
            if (EntityUtil.getAttitude(this, this.getAttackTarget()) == EntityUtil.EnumAttitude.FRIEND)
            {
                setAttackTarget(null);
            }
        }

        super.onUpdate();
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!player.getEntityWorld().isRemote && getHealth() < getMaxHealth())
        {
            ItemStack stack = player.getHeldItem(hand);
            Item item = stack.getItem();
            if (stack.getItem() == Items.IRON_INGOT)
            {
                if (!player.getCooldownTracker().hasCooldown(item))
                {
                    heal(2f);
                    player.getCooldownTracker().setCooldown(item, TICK_PER_SECOND);

                }
            }
            else if (stack.getItem() instanceof ItemNanoMender)
            {
                float wound = getMaxHealth() - getHealth();
                int dura = stack.getMaxDamage()-stack.getItemDamage();
                if (wound > dura)
                {
                    heal(dura);
                    stack.shrink(1);
                }
                else {
                    heal(wound);
                    stack.damageItem(dura, this);
                }
            }
        }
        return super.processInteract(player, hand);
    }
}
