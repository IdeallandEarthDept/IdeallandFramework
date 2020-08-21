package com.deeplake.idealland.entity.creatures.ideallandTeam;

import com.deeplake.idealland.blocks.ModBlocks;
import com.deeplake.idealland.meta.MetaUtil;
import com.deeplake.idealland.util.CommonDef;
import com.deeplake.idealland.util.EntityUtil;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityIDLXPWell extends EntityIdeallandUnitBase {
    public EntityIDLXPWell(World worldIn) {
        super(worldIn);
        setBuilding();
        setSize(0.5f, 0.5f);
    }

    float stepCharge = 1f;//once per second
    float curCharge = 0f;
    float maxCharge = 60f;

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        setAttr(4.0D, 0D, 1.0D, 5.0D, 15.0D);
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (world.getWorldTime() % CommonDef.TICK_PER_SECOND == 0)
        {
            if (checkBuildingIntact())
            {
                charge();
            }
            else {
                damageEntity(new DamageSource("breaking").setDamageIsAbsolute().setDamageBypassesArmor(), 1f);
            }
        }

        if (curCharge >= maxCharge)
        {
            EntityUtil.SpawnParticleAround(this, EnumParticleTypes.SPELL);
        }
    }

    public void charge()
    {
        if (world.isRemote)
        {
            //particles
            EntityUtil.SpawnParticleAround(this, EnumParticleTypes.ENCHANTMENT_TABLE, 10);
        }else {
            if (curCharge>=maxCharge)
            {
                if (CheckNeedWater())
                {
                    FillWater();
                    playSound(SoundEvents.ENTITY_PLAYER_SPLASH, 1f, 1f);
                }
            }else {
                curCharge += stepCharge;
                //playSound(SoundEvents.ENTITY_GENERIC_DRINK, 0.5f, 2f);
            }
        }
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (curCharge >= maxCharge)
        {
            curCharge = 0f;
            FillAir();
            player.addExperience(MetaUtil.GetModCount());
            world.playSound(player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1f, 1f, false);
            //player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
            return true;
        }
        else {
            return false;
        }
    }

    float offsetY = -4f;


    boolean CheckNeedWater()
    {
        float x = (float) posX;
        float y = (float) posY + offsetY + 1;
        float z = (float) posZ;
        y -= 1;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dz == 0 && dx == 0)
                {
                    continue;
                }
                if (world.getBlockState(new BlockPos(x + dx, y, z + dz)) != Blocks.WATER.getDefaultState())
                {
                    return true;
                }
            }
        }
        return false;
    }
    void FillWater()
    {
        float x = (float) posX;
        float y = (float) posY + offsetY + 1;
        float z = (float) posZ;
        y -= 1;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dz == 0 && dx == 0)
                {
                    continue;
                }
                world.setBlockState(new BlockPos(x + dx, y, z + dz), Blocks.WATER.getDefaultState());
            }
        }
    }

    void FillAir()
    {
        float x = (float) posX;
        float y = (float) posY + offsetY + 1;
        float z = (float) posZ;
        y -= 1;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dz == 0 && dx == 0)
                {
                    continue;
                }
                world.setBlockState(new BlockPos(x + dx, y, z + dz), Blocks.AIR.getDefaultState());
            }
        }
    }


    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        curCharge = compound.getInteger(IDLNBTDef.BUILDING_CHARGE);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setFloat(IDLNBTDef.BUILDING_CHARGE, curCharge);
        return compound;
    }

    boolean checkBuildingIntact() {
        //Idealland.Log("xp well pos = %s", getPosition());
        int rand = getRNG().nextInt(2);
        float x = (float) posX;
        float y = (float) posY + offsetY;
        float z = (float) posZ;

        switch (rand) {
            case 0:
                //check bottom
                y -= 1;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (world.getBlockState(new BlockPos(x + dx, y, z + dz)) != ModBlocks.GRID_NORMAL.getDefaultState()) {
                            return false;
                        }
                    }
                }
                break;
            case 1:
                //check nearby
                for (int dx = -2; dx <= 2; dx++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        if (dx == 2 || dz == 2 || dx == -2 || dz == -2) {
                            if (world.getBlockState(new BlockPos(x + dx, y, z + dz)) != ModBlocks.GRID_NORMAL.getDefaultState()) {
                                return false;
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }
}
