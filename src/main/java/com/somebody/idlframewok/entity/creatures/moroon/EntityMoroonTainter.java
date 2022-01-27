package com.somebody.idlframewok.entity.creatures.moroon;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.potion.ModPotions;
import com.somebody.idlframewok.util.IDLGeneral;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityMoroonTainter extends EntityMoroonUnitBase {

    public float taintChance = 0.03f;//per tick

    public EntityMoroonTainter(World worldIn) {
        super(worldIn);
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(ModBlocks.MORON_BLOCK));
        spawn_without_moroon_ground = true;
    }

    protected void firstTickAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityMoroonTainter.class}));
        applyGeneralAI();
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(35.0D, 0.33000000417232513D, 3.0D, 0.0D, 14.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        //Idealland.Log("Tick");
        if (!this.world.isRemote)
        {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY);
            int k = MathHelper.floor(this.posZ);

            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this))
            {
                return;
            }
            //Creates MoronBase block under foot
            for (int l = 0; l < 4; ++l)
            {
                i = MathHelper.floor(this.posX + (double)((float)(l % 2 * 2 - 1) * 0.25F));
                j = MathHelper.floor(this.posY) - 1;
                k = MathHelper.floor(this.posZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos = new BlockPos(i, j, k);

                if (rand.nextFloat() < taintChance * (2 - getHealth() / getMaxHealth()) &&
                        legalTransformBlockstate(this.world.getBlockState(blockpos)))
                {
                    this.world.setBlockState(blockpos, ModBlocks.MORON_BLOCK.getDefaultState());
                }
            }
        }
    }

    public boolean legalTransformBlockstate(IBlockState state)
    {
        return state.getMaterial() != Material.AIR && state.getBlock() != Blocks.MOB_SPAWNER;
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    public boolean getCanSpawnHere()
    {
        if (ModConfig.SPAWN_CONF.SPAWN_TAINTER_REQ_BUFF)
        {
            boolean foundValidPlayer = false;
            float range = ModConfig.SPAWN_CONF.SPAWN_TAINTER_RANGE;

            Vec3d basePos = this.getPositionVector();
            List<EntityPlayer> entities = world.getEntitiesWithinAABB(EntityPlayer.class, IDLGeneral.ServerAABB(basePos.addVector(-range, -range, -range), basePos.addVector(range, range, range)));
            for (EntityPlayer living: entities
            ) {
                if (living.getActivePotionEffect(ModPotions.NOTICED_BY_MOR) != null)
                {
                    foundValidPlayer = true;
                    break;
                }
            }
            return super.getCanSpawnHere() && foundValidPlayer;
        }else {
            return super.getCanSpawnHere();
        }
    }
}
