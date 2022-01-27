package com.somebody.idlframewok.entity.creatures.mobs.gargoyle;

import com.somebody.idlframewok.entity.creatures.EntityGeneralMob;
import com.somebody.idlframewok.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityGargoyleBase extends EntityGeneralMob {

    //decides how much dig efficiency affect attack damage.
    public float combatModifier = 0.5f;

    public EntityGargoyleBase(World worldIn) {
        super(worldIn);
        is_building = true;
        is_mechanic = true;
        go_home = true;

        dontDespawn = true;
        dontDrown = true;

        melee_atk = true;
        wander = false;
        can_swim = false;

        avengeful = true;
        rally_on_hurt = false;

        enterDoors = false;

        attack_all_players = 2;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        float result = amount;

        if (source.getTrueSource() instanceof EntityLivingBase) {
            ItemStack item = ((EntityLivingBase) source.getTrueSource()).getHeldItemMainhand();
            result *= combatModifier * item.getDestroySpeed(Blocks.STONE.getDefaultState());

        }
        return super.attackEntityFrom(source, result);
    }

    @Override
    public void knockBack(Entity source, float strength, double xRatio, double zRatio) {
        //immune
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_STONE_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_STONE_BREAK;
    }

    int dropConduitAttempts = 5;
    int dropFactor = 10;

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);

        for (int i = 1; i <= dropConduitAttempts; i++) {
            if (rand.nextInt(dropFactor) < (lootingModifier + 1))
                dropItem(ModItems.BASIC_CONDUIT, 1);
        }

        if (rand.nextInt(dropFactor) < (lootingModifier + 1))
            dropItem(Items.DIAMOND, 1);

        dropItem(Item.getItemFromBlock(Blocks.STONE), 1 + lootingModifier);
    }
}
