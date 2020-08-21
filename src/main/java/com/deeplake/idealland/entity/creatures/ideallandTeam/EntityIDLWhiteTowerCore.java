package com.deeplake.idealland.entity.creatures.ideallandTeam;

import com.deeplake.idealland.IdlFramework;
import com.deeplake.idealland.blocks.ModBlocks;
import com.deeplake.idealland.util.EntityUtil;
import com.deeplake.idealland.util.IDLGeneral;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EntityIDLWhiteTowerCore extends EntityIdeallandUnitBase {
    public float rangeX = 32f, rangeZ = 32f, rangeYup = 255f, rangeYdown = 32f;
    public AxisAlignedBB aabb;

    public SoundEvent onLoadSound = SoundEvents.BLOCK_NOTE_CHIME;
    public EnumParticleTypes particleType = EnumParticleTypes.CRIT_MAGIC;

    public EntityIDLWhiteTowerCore(World worldIn) {
        super(worldIn);
        MinecraftForge.EVENT_BUS.register(this);
        is_pinned_on_ground = true;
        setSize(2, 4);
    }
    //---------building


    //----------building end

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4096.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    public void PlaySoundHere()
    {
        world.playSound(null, getPosition(), onLoadSound, SoundCategory.BLOCKS, 0.3F, 1f);
    }

    @SubscribeEvent
    public void onExplode(ExplosionEvent.Start event) {
        Vec3d pos = event.getExplosion().getPosition();
        //IdlFramework.Log(String.format("onExplode:(%s,%s,%s)", pos.x, pos.y, pos.z));
        if (!event.isCanceled() && aabb != null && aabb.contains(pos))
        {
            event.setCanceled(true);
            PlaySoundHere();
            damageEntity(DamageSource.causeExplosionDamage(event.getExplosion()), 4f);
            IdlFramework.Log("Core Stopped an explosion");
        }
    }

    @SubscribeEvent
    public void onSpawn(LivingSpawnEvent.CheckSpawn event) {
        if (event.getWorld().isRemote)
        {
            return;
        }

        if(event.getResult() != Event.Result.DENY &&
                (event.getEntityLiving() instanceof IMob ||
                        event.getEntityLiving() instanceof EntityMob ||
                        EntityUtil.isMoroonTeam(event.getEntityLiving() ))) {
            if (aabb != null && aabb.contains(new Vec3d(event.getX(), event.getY(), event.getZ())))
            {
                event.setResult(Event.Result.DENY);
                //TakeDamage(event.getEntityLiving().getMaxHealth() / 10f);
                //IdlFramework.Log("Stopped spawning:"+event.getEntityLiving().getName());
                return;
            }
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (world.getWorldTime() % 5 == 0) {
            aabb = new AxisAlignedBB(posX - rangeX, posY - rangeYdown, posZ - rangeZ, posX + rangeX, posY + rangeYup, posZ + rangeZ);
            if (!this.world.isRemote) {
                RemoveFire();
                disarmProjectiles();
            }
            KillNearbyEnemies();
            heal(1f);
        }
    }

    void KillNearbyEnemies()
    {
        Vec3d basePos = getPositionVector();
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLiving.class, aabb);
        for (EntityLivingBase living: entities
        ) {
            if (living instanceof IMob || EntityUtil.isMoroonTeam(living))
            {
                if (!this.world.isRemote) {
                    living.setDead();
                    TakeDamage(living.getMaxHealth() / 10f);
                }
                else {
                    world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, living.posX, living.posY, living.posZ, 0.0D, 0.0D, 0.0D);
                }
            }
        }

    }


    public void TakeDamage(float val)
    {
        damageEntity(DamageSource.GENERIC, val);
    }

    void RemoveFire()
    {
        BlockPos origin = getPosition();
        BlockPos target = origin;

        World worldIn = world;

        for (int x = (int) -rangeX; x <= rangeX; x++){
            for (int y = (int) -rangeYdown; y <= rangeYup; y++){
                for (int z = (int) -rangeZ; z <= rangeZ; z++){
                    target = origin.add(x,y,z);
                    IBlockState targetBlock = worldIn.getBlockState(target);
                    Block type =  targetBlock.getBlock();

                    if (//type == Blocks.WATER || type == Blocks.FLOWING_WATER ||
                            type == Blocks.LAVA || type == Blocks.FLOWING_LAVA ||
                            type == Blocks.MAGMA || type == Blocks.FIRE )
                    {
                        if (//type == Blocks.FLOWING_WATER ||
                                type == Blocks.FLOWING_LAVA)
                        {
                            TakeDamage(0.1f);
                            worldIn.setBlockState(target, Blocks.GOLD_BLOCK.getDefaultState());
                            //otherwise the tower will be soon destroyed
                        }
                        else {
                            TakeDamage(1f);
                            worldIn.setBlockState(target, Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    //Remove projectiles and blocks
    private double horizontalRange = 4d;
    private void disarmProjectiles()
    {
        if (world.isRemote) {
            return;
        }

        World worldIn = world;
        Vec3d pos = getPositionEyes(1.0F);

        List<Entity> entities = worldIn.getEntitiesWithinAABB(Entity.class,
                IDLGeneral.ServerAABB(pos.addVector(-horizontalRange, -2, -horizontalRange), pos.addVector(horizontalRange, 2, horizontalRange)));
        for (Entity entity : entities) {
            HandleProjectile(entity);
        }
    }

    private void HandleProjectile(Entity projectile)
    {
        if (projectile.isDead)
        {
            return;
        }

        if (projectile instanceof IProjectile ||
                projectile instanceof EntityFireball ||
                projectile instanceof EntityTNTPrimed ||
                projectile instanceof EntityShulkerBullet) {
            ItemStack result = GetCorrespondingStack(projectile);
            if (result != null) {
                if (projectile instanceof EntityTNTPrimed) {
                    ((EntityTNTPrimed) projectile).setFuse(88);
                }

                projectile.entityDropItem(result, 0.1F);

            }
            projectile.setDead();
        }
        //todo: should handle falling block, and such.
    }

    //Tried to get the arrow from the entity.
    //there is an implemented method, but it's protected.
    //The only way I can think of to support light arrow and tipped arrows are rewriting them from nbt.
    private ItemStack GetArrowStack(EntityArrow arrow) {
        return new ItemStack(Items.ARROW);
    }

    private ItemStack GetCorrespondingStack(Entity projectile)
    {
        if (projectile instanceof IProjectile) {
            if (projectile instanceof EntityArrow) {
                EntityArrow arrow = (EntityArrow) projectile;
                return GetArrowStack(arrow);
            }
        } else if (projectile instanceof EntityFireball)
        {
            return new ItemStack(Items.FIRE_CHARGE);
        }else if  (projectile instanceof EntityTNTPrimed) {
            return new ItemStack(Blocks.TNT.getItemDropped(Blocks.TNT.getDefaultState(), null, 0));
        }
        return ItemStack.EMPTY;
    }



}
