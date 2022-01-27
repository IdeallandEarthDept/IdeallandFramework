package com.somebody.idlframewok.entity.creatures.mobs.skyland;

import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.enchantments.ModEnchantmentInit;
import com.somebody.idlframewok.entity.creatures.EntityGeneralMob;
import com.somebody.idlframewok.world.biome.GodBelieverSingle;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.UUID;

import static com.somebody.idlframewok.util.Color16Def.EARTH;

public class EntityDarkElemental extends EntityGeneralMob {
    protected static final UUID ELEM_BONUS_UUID = UUID.fromString("3ff73966-00a0-4741-bf7e-648f92cbc82f");

    public EntityDarkElemental(World worldIn) {
        super(worldIn);
        experienceValue = 40;
        is_elemental = true;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        if (wasRecentlyHit)
        {
            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
            int mode = rand.nextInt(3);
            Enchantment enchantment;
            switch(mode)
            {
                case 0:
                    enchantment = ModEnchantmentInit.INV_HEIGHT_ATK;
                    break;
                case 1:
                    enchantment = ModEnchantmentInit.INV_HEIGHT_DEF;
                    break;
                default:
                    enchantment = Enchantments.PROTECTION;
                    break;
            }
            ItemEnchantedBook.addEnchantment(book, new EnchantmentData(enchantment, 1 + rand.nextInt(enchantment.getMaxLevel())));
            entityDropItem(book, 1f);
        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16, 0.24, 0, 0, 40);
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_VEX_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_VEX_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 1.5F, 0.8F);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!world.isRemote)
        {
            AttributeModifier LEVEL_BONUS_MODIFIER = (new AttributeModifier(ELEM_BONUS_UUID, "elemental bonus",
                    15 - world.getLight(getPosition()), 1));

            if (this.getEntityAttribute(SharedMonsterAttributes.ARMOR).hasModifier(LEVEL_BONUS_MODIFIER)) {
                this.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ELEM_BONUS_UUID);
            }

            this.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(LEVEL_BONUS_MODIFIER);
        }
        return super.attackEntityFrom(source, amount);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        AttributeModifier LEVEL_BONUS_MODIFIER = (new AttributeModifier(ELEM_BONUS_UUID, "elemental bonus",
                15 - world.getLight(getPosition()), 1));

        if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(LEVEL_BONUS_MODIFIER)) {
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(ELEM_BONUS_UUID);
        }
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(LEVEL_BONUS_MODIFIER);

        boolean result = super.attackEntityAsMob(entityIn);
        if (result)
        {
            this.applyEnchantments(this, entityIn);
        }

        this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0F, 0.8F);
        return result;
    }

    protected void applyTargetAI()
    {
        super.applyTargetAI();
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, true, Init16Gods.NOT_GOD_BEILIVER[EARTH]));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIdeallandUnitBase.class, true));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityZombie.class, false));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote)
        {
            Biome biome = world.getBiome(getPosition());
            if (biome instanceof GodBelieverSingle) {
                if ((((GodBelieverSingle) biome).getGodIndex() == EARTH) && onGround)
                {
                    heal(0.2f);
                }
            }
        }
    }
}
