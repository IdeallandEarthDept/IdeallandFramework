package com.somebody.idlframewok.entity.creatures.moroon;

import com.somebody.idlframewok.blocks.blockMoroon.BlockMoroonBase;
import com.somebody.idlframewok.entity.creatures.misc.EntityHiredSkeleton;
import com.somebody.idlframewok.entity.creatures.EntityModUnit;
import com.somebody.idlframewok.blocks.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntitySpawnTower extends EntityModUnit {

    int minTowerHeight = 2;
    int maxTowerHeight = 6;
    IBlockState towerBase = ModBlocks.MORON_BLOCK.getDefaultState();

    public EntitySpawnTower(World worldIn) {
        super(worldIn);
        spawn_without_darkness = false;
        spawn_without_moroon_ground = false;
    }

    public Entity spawnTowerTop(Vec3d pos)
    {
        EntityHiredSkeleton result = new EntityHiredSkeleton(world);
        result.setPosition(pos.x, pos.y, pos.z);
        world.spawnEntity(result);
        result.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        result.enablePersistence();

        world.setBlockState(new BlockPos(pos.x, pos.y+3, pos.z), towerBase);
        return result;
    }

    public void SetArgs()
    {
        minTowerHeight = 2;
        maxTowerHeight = 6;
    }


    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        return livingdata;
    }

    @Override
    public void onEntityUpdate() {
        //Idealland.Log(String.format("onEntityUpdate = %s", world.isRemote) );
        //Idealland.Log(String.format("onEntityUpdate Position = %s", getPosition()) );
        super.onEntityUpdate();
        if (!world.isRemote)
        {
            SetArgs();
            int bound = maxTowerHeight - minTowerHeight;
            //Idealland.Log(String.format("bound = %d", bound) );
            int towerHeight = rand.nextInt(maxTowerHeight - minTowerHeight) + minTowerHeight;
            boolean isVacant = true;
            for (int i = 0; i < towerHeight; i++)
            {
                //Idealland.Log(String.format("loop = %s", world.getBlockState(getPosition().add(0, i,0))) );
                if (world.getBlockState(getPosition().add(0, i,0)).getBlock() != Blocks.AIR)
                {
                    //Idealland.Log(String.format("i = %d not vacant, stopping", i) );
                    isVacant = false;
                    //break;
                }
            }

            if (isVacant)
            {
                for (int i = 0; i < towerHeight; i++) {
                    world.setBlockState(getPosition().add(0, i, 0), towerBase);
                }

                spawnTowerTop(getPositionVector().add(new Vec3d(0f, (float)towerHeight, 0f)));
                //Idealland.Log(String.format("height = %d done", towerHeight) );
            }

            this.setDead();
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.world.getBlockState((new BlockPos(this)).down()).getBlock() instanceof BlockMoroonBase;
    }
}
