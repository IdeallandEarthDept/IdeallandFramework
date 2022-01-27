package com.somebody.idlframewok.entity.creatures.misc;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.item.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityShadeOtherworld extends EntityModUnit {
    public EntityShadeOtherworld(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean isEntityUndead() {
        return true;
    }

    protected void firstTickAI()
    {
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityLivingBase.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(8, 0.5, 0, 0, 20);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source != DamageSource.OUT_OF_WORLD)
        {
            return false;
        }
        else {
            return super.attackEntityFrom(source, amount);
        }
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (player.world.isRemote)
        {
            return true;
        }
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == ModItems.HELL_COIN)
        {
            if (rand.nextFloat() < 0.3f)
            {
                if (world.provider.getDimension() == ModConfig.WORLD_GEN_CONF.DIM_UNIV_ID)
                {
                    entityDropItem(new ItemStack(ModBlocks.TELEPORTER_DIM_OW),0f);
                }else {
                    entityDropItem(new ItemStack(ModBlocks.TELEPORTER_DIM_C16),0f);
                }
            }
            entityDropItem(new ItemStack(ModItems.ITEM_IDL_ORDER_2, 1 + rand.nextInt(3)),0f);
            entityDropItem(new ItemStack(ModBlocks.CRATE, 1 + rand.nextInt(3)),0f);

            world.playSound(posX,posY,posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 1f, 1f, false);
            setDead();
            return true;
        }
        return false;
    }
}
