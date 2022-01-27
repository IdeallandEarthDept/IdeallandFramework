package com.somebody.idlframewok.entity.creatures.mobs.skyland;

import com.somebody.idlframewok.enchantments.ModEnchantmentInit;
import com.somebody.idlframewok.entity.creatures.EntityGeneralMob;
import com.somebody.idlframewok.util.EntityUtil;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Calendar;

import static com.somebody.idlframewok.util.EntityUtil.USING_MODDED;
import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.FORCE_VANILLA;

public class EntityVanillaZealot extends EntityGeneralMob {
    public EntityVanillaZealot(World worldIn) {
        super(worldIn);
        experienceValue = 5;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16, 0.2, 1, 0, 20);
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        //copy zombie.
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        float f = difficulty.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())
        {
            Calendar calendar = this.world.getCurrentDate();

            if (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
            }
        }

        if (world.getDifficulty() != EnumDifficulty.EASY)
        {
            ItemStack weapon = rand.nextBoolean() ? new ItemStack(Items.IRON_AXE) : new ItemStack(Items.IRON_SWORD);
            if (world.getDifficulty() == EnumDifficulty.HARD)
            {
                weapon.addEnchantment(ModEnchantmentInit.ANTI_MOD, ModEnchantmentInit.ANTI_MOD.getMaxLevel());
            }
            setItemStackToSlot(EntityEquipmentSlot.MAINHAND, weapon);
            setItemStackToSlot(EntityEquipmentSlot.OFFHAND, weapon);
        }
        IDLNBTUtil.setInt(this, FORCE_VANILLA, 1);
        return livingdata;
    }


    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        if (wasRecentlyHit)
        {
            if (rand.nextFloat() < 0.1f)
            {
                ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                int mode = rand.nextInt(2);
                Enchantment enchantment;
                switch(mode)
                {
                    case 0:
                        enchantment = ModEnchantmentInit.ANTI_MOD;
                        ItemEnchantedBook.addEnchantment(book, new EnchantmentData(enchantment, 1 + rand.nextInt(enchantment.getMaxLevel())));
                        break;
                    case 1:
                        enchantment = ModEnchantmentInit.ANTI_MOD_PROTECTION;
                        ItemEnchantedBook.addEnchantment(book, new EnchantmentData(enchantment, 1 + rand.nextInt(enchantment.getMaxLevel())));
                        break;
//                    default:
//                        EnchantmentHelper.addRandomEnchantment(rand, book, 5, false);
//                        break;
                }
                entityDropItem(book, 1f);
            }

            if (rand.nextFloat() < 0.1f)
            {
                entityDropItem(new ItemStack(Items.CAKE, 1), 1f);
            }

            entityDropItem(new ItemStack(Items.BREAD, 1 + rand.nextInt(3)), 1f);
        }
    }


    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_PLAYER_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP, 1.0F, 1.0F);
    }

    protected void applyTargetAI()
    {
        super.applyTargetAI();
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, true, true, EntityUtil.NotVanilla));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, true, USING_MODDED));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIdeallandUnitBase.class, true));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityZombie.class, false));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
    }
}
