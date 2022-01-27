package com.somebody.idlframewok.entity.creatures.moroon;

import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.potion.ModPotions;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;


import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class EntityMoroonBombBeacon extends EntityMoroonUnitBase {
    float bombInterval = 40f;
    float bombCounter = 40f;
    public float bombPower = 6f;

    public EntityMoroonBombBeacon(World worldIn) {
        super(worldIn);
        setCanPickUpLoot(false);
        is_mechanic = true;
        is_pinned_on_ground = true;
        experienceValue = 6;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote)
        {
            if (getActivePotionEffect(ModPotions.INTERFERENCE) != null)
            {
                return;
            }

            if (world.getWorldTime() % TICK_PER_SECOND == 0)
            {
                addPotionEffect(new PotionEffect(MobEffects.GLOWING, TICK_PER_SECOND << 1,0));
                playSound(SoundEvents.BLOCK_NOTE_BELL, 3, (10 - bombCounter) / 10f);
                if (!this.world.canSeeSky(getPosition()))
                {
                    //slows when cannot see sky
                    this.bombCounter += 0.5f / TICK_PER_SECOND;
                }
            }

            if (bombCounter <= 0)
            {
                bombCounter = bombInterval;
                playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 5f, 1f);
                Bomb();
            }
            bombCounter -= 1f / TICK_PER_SECOND;
            //Idealland.Log("Bomb counter:" + bombCounter);
        }
    }

    public void Bomb()
    {
        EntityFireball result = new EntityLargeFireball(world);
        EntityFireball entityBullet;

        entityBullet = new EntityLargeFireball(world, this, 0, -100, 0);
        ((EntityLargeFireball)entityBullet).explosionPower = (int)bombPower;
        entityBullet.posX = posX;
        entityBullet.posY = posY + 100f;
        entityBullet.posZ = posZ;
        world.spawnEntity(entityBullet);

        //Idealland.Log("Bomb");
    }

//    @Nullable
//    protected ResourceLocation getLootTable()
//    {
//        return ModLootList.M_O_B;
//    }
//
//    /**
//     * Returns the item ID for the item the mob drops on death.
//     * @return
//     */
//    @Override
//    protected Item getDropItem()
//    {
//        Idealland.Log("getDropItem called");
//        return Items.IRON_INGOT;
//    }


    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        dropItem(ModItems.MOR_FRAG, 1 + rand.nextInt(2 + lootingModifier));

        if (wasRecentlyHit) {
            if (rand.nextInt(4) + lootingModifier >= 3)
            {
                dropItem(ModItems.ANTENNA, 1);
            }
        }
    }

}
