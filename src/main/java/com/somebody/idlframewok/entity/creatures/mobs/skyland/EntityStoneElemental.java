package com.somebody.idlframewok.entity.creatures.mobs.skyland;

import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.enchantments.ModEnchantmentInit;
import com.somebody.idlframewok.entity.creatures.EntityGeneralMob;
import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.world.biome.godlands.skylands.BiomeSkylandBase;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static com.somebody.idlframewok.util.Color16Def.STONE;

public class EntityStoneElemental extends EntityGeneralMob {
    public EntityStoneElemental(World worldIn) {
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
                    enchantment = ModEnchantmentInit.KB_REFLECT;
                    break;
                case 1:
                    enchantment = Enchantments.FEATHER_FALLING;
                    break;
                default:
                    enchantment = Enchantments.PROTECTION;
                    break;
            }
            ItemEnchantedBook.addEnchantment(book, new EnchantmentData(enchantment, 1 + rand.nextInt(enchantment.getMaxLevel())));
            entityDropItem(book, 1f);
        }
        entityDropItem(new ItemStack(Blocks.STONE, 4), 1f);

    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(16, 0.2, 6, 10, 40);
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_IRONGOLEM_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_IRONGOLEM_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!world.isRemote)
        {
            if ((source == DamageSource.FALL))
            {
                float range = 5f;
                Vec3d mypos = getPositionEyes(0f);
                //Damage nearby entities
                List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, IDLGeneral.ServerAABB(mypos.addVector(-range, -range, -range), mypos.addVector(range, range, range)));
                for (EntityLivingBase creature : list) {
                    if (creature != this && creature.onGround && (Init16Gods.NOT_GOD_BEILIVER[STONE].apply(creature) )) {
                        creature.attackEntityFrom(DamageSource.causeMobDamage(this), amount * Math.max(1 - creature.getDistance(this) / range, 0f));
                    }
                }
                return false;
            }
        }

        if ((world.getBiome(getPosition()) instanceof BiomeSkylandBase)) {
            return super.attackEntityFrom(source, amount);
        }
        else {
            return super.attackEntityFrom(source, amount / 2f);
        }
    }

    @Override
    public void knockBack(Entity source, float strength, double xRatio, double zRatio) {
        if ((world.getBiome(getPosition()) instanceof BiomeSkylandBase)) {
            super.knockBack(source, strength, xRatio * 2, zRatio * 2);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean result = super.attackEntityAsMob(entityIn);
        if (result)
        {
            entityIn.motionY += 0.4000000059604645D;
            this.applyEnchantments(this, entityIn);
        }

        this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
        return result;
    }

    protected void applyTargetAI()
    {
        super.applyTargetAI();
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, true, Init16Gods.NOT_GOD_BEILIVER[STONE]));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIdeallandUnitBase.class, true));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityZombie.class, false));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
    }
}
