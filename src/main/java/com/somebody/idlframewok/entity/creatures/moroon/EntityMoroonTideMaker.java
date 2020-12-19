package com.somebody.idlframewok.entity.creatures.moroon;

import com.somebody.idlframewok.util.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EntityMoroonTideMaker extends EntityMoroonUnitBase {

    public EntityMoroonTideMaker(World worldIn) {
        super(worldIn);
        experienceValue = 10;
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.WATER_BUCKET));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        setAttr(16.0D, 0.5D, 5.0D, 0.0D, 64.0D);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));

        //this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityGolem.class, 8.0F, 0.6D, 0.6D));

        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        //this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        applyGeneralAI();
    }

    //wont be affected by water flow
    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @SubscribeEvent
    public static void onCreatureDied(final LivingDeathEvent ev) {
        World world = ev.getEntityLiving().world;
        EntityLivingBase diedOne = ev.getEntityLiving();
    }



    @Override
    public void onUpdate() {
        super.onUpdate();
        //IdlFramework.Log("Tick");
        if (!this.world.isRemote)
        {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY);
            int k = MathHelper.floor(this.posZ);

            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this))
            {
                return;
            }
            //Creates water alongside
            for (int l = 0; l < 4; ++l)
            {
                i = MathHelper.floor(this.posX + (double)((float)(l % 2 * 2 - 1) * 0.25F));
                j = MathHelper.floor(this.posY);
                k = MathHelper.floor(this.posZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos = new BlockPos(i, j, k);

                if ((this.world.getBlockState(blockpos).getMaterial() == Material.AIR ||
                        this.world.getBlockState(blockpos).getBlock() == Blocks.FLOWING_WATER)
                        &&
                        this.world.getBlockState(blockpos.add(0, -1 ,0)).getMaterial() != Material.AIR)
                {
                    this.world.setBlockState(blockpos, Blocks.WATER.getDefaultState());
                }
            }
        }
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();

    }
}
