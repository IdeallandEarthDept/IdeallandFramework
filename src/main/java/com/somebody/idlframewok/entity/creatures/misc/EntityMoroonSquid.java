package com.somebody.idlframewok.entity.creatures.misc;

import com.somebody.idlframewok.entity.creatures.ideallandTeam.EntityIdeallandUnitBase;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.EntityUtil;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import static com.somebody.idlframewok.util.Reference.MOD_ID;

//not instance of EntityWaterMob
@Mod.EventBusSubscriber(modid = MOD_ID)
public class EntityMoroonSquid extends EntitySquidBase implements IMob {


    public EntityMoroonSquid(World worldIn) {
        super(worldIn);
        isMoroon = true;
        isIdealland = false;
        spawn_without_moroon_ground = true;
        spawn_without_darkness = true;
    }

    protected void applyGeneralAI()
    {
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, false, false, EntityUtil.InWater));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIdeallandUnitBase.class, 10, false, false, EntityUtil.InWater));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityZombie.class, 10, false, false, EntityUtil.InWater));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntityVillager.class, 10, false, false, EntityUtil.InWater));

        ((PathNavigateGround)this.getNavigator()).setEnterDoors(true);
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);

        if (wasRecentlyHit) {
            if (getRNG().nextFloat() < 0.1f * (1 + lootingModifier))
            {
                dropItem (ModItems.ANTENNA, 1);
            }

            if (getRNG().nextFloat() < 0.1f * (1 + lootingModifier))
            {
                dropItem (ModItems.ITEM_IDL_ORDER_1, 1);
            }

            dropItem(ModItems.MOR_FRAG, 2 + rand.nextInt(2 + lootingModifier));
            if (rand.nextFloat() < ModConfig.GeneralConf.SKILL_RATE * getLevel() / 5f)
            {
                dropItem(ModItems.RANDOM_SKILL, 1);
            }

            if (rand.nextFloat() < 0.1f * getLevel())
            {
                dropItem(ModItems.itemNanoMender_16, 1 + rand.nextInt(2 + lootingModifier));
            }

            if (rand.nextFloat() < 0.2f * getLevel())
            {
                dropItem(ModItems.FIGHT_BREAD, 1 + rand.nextInt(2 + lootingModifier));
            }

            if (rand.nextFloat() < ModConfig.GeneralConf.SKILL_RATE * getLevel() / 5f)
            {
                dropItem(ModItems.itemNanoMender_128, 1);
            }
        }
    }


//    @SubscribeEvent
//    public static void onSpawn(LivingSpawnEvent event)
//    {
//        if (event.getWorld().isRemote)
//        {
//            return;
//        }
//
//        if (event.getEntity() instanceof EntityMoroonSquid)
//        {
//            event.setResult(Event.Result.ALLOW);
//            //seems to be in air
//            //Idealland.Log("Spawn mod squid @%s", new Vec3d(event.getX(), event.getY(), event.getZ()));
//        }
//
//        if (event.getEntity() instanceof EntitySquid)
//        {
//            //Idealland.Log("Spawn squid @%s", new Vec3d(event.getX(), event.getY(), event.getZ()));
//        }
//    }
}
