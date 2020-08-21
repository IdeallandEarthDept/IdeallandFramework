package com.deeplake.idealland.entity.creatures.moroon;

import com.deeplake.idealland.blocks.ModBlocks;
import com.deeplake.idealland.item.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;

//with 10% chance to spawn full-suited. full suited will certain drop armor.
//otherwise 10% chance to drop.
public class EntityMoroonBastionWalker extends EntityMoroonUnitBase {

    boolean withFullSuit = false;
    public EntityMoroonBastionWalker(World worldIn) {
        super(worldIn);
        setSize(1.6f, 1.6f);
        spawn_without_darkness = true;
        spawn_without_moroon_ground = false;
        setSize(2, 2);
        experienceValue = 100;
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.SHIELD));

        autoArmor = true;
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        if (getRNG().nextFloat() < 0.1f)
        {
            withFullSuit = true;
        }
        if (withFullSuit)
        {
            setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ModItems.MOR_GENERAL_ARMOR[0]));
            setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(ModItems.MOR_GENERAL_ARMOR[1]));
            setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(ModItems.MOR_GENERAL_ARMOR[2]));
            setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(ModItems.MOR_GENERAL_ARMOR[3]));
        }
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);

        if (wasRecentlyHit) {
            dropItem(ModItems.skillMartialSlam, rand.nextInt(1 + lootingModifier));

            if (withFullSuit || getRNG().nextFloat() < 0.1f * (1 + lootingModifier))
            {
                dropItem (ModItems.MOR_GENERAL_ARMOR[getRNG().nextInt(4)], 1);
            }
        }

        if (rand.nextFloat() < 0.01f * getLevel() * (1+lootingModifier))
        {
            dropItem(ModItems.ITEM_EL_PSY_CONGROO, 1);
        }
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMoroonBombBeacon.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityMoroonUnitBase.class}));
        applyGeneralAI();
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        setAttr(15, 0.1, 10, 5, 140);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote)
        {
            if (world.getWorldTime() % TICK_PER_SECOND == 0) {
                int i = MathHelper.floor(this.posX);
                int j = MathHelper.floor(this.posY);
                int k = MathHelper.floor(this.posZ);

                if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                    return;
                }
                //Creates MoronBase block under foot
                for (int l = 0; l < 4; ++l) {
                    i = MathHelper.floor(this.posX + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
                    j = MathHelper.floor(this.posY) - 1;
                    k = MathHelper.floor(this.posZ + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
                    BlockPos blockpos = new BlockPos(i, j, k);

                    if (legalTransformBlockstate(this.world.getBlockState(blockpos))) {
                        this.world.setBlockState(blockpos, ModBlocks.MORON_BLOCK.getDefaultState());
                    }
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
}
