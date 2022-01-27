package com.somebody.idlframewok.entity.creatures.ai;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowSomething extends EntityAIBase
{
    private final IHasOwner follower;
    private final EntityLiving followerAsLiving;
    private EntityLivingBase ownerAsLiving;
    World world;
    private final double followSpeed;
    private final PathNavigate petPathfinder;
    private int timeToRecalcPath;
    float maxDist;
    float minDist;
    private float oldWaterCost;

    public EntityAIFollowSomething(EntityLiving autoCreature, double followSpeedIn, float minDistIn, float maxDistIn)
    {
        if (!(autoCreature.getNavigator() instanceof PathNavigateGround)
                && !(autoCreature.getNavigator() instanceof PathNavigateFlying))
        {
            throw new IllegalArgumentException("Unsupported mob navigator type for FollowOwner");
        }

        if (!(autoCreature instanceof IHasOwner)) {
            throw new IllegalArgumentException("autoCreature must implement IHasOwner");
        }

        follower = (IHasOwner)autoCreature;
        followerAsLiving = autoCreature;
        world = autoCreature.getEntityWorld();
        followSpeed = followSpeedIn;
        petPathfinder = autoCreature.getNavigator();
        minDist = minDistIn;
        maxDist = maxDistIn;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase owner = follower.getOwner();

        if (owner == null)
        {
            return false;
        }
        else if (owner instanceof EntityPlayer && ((EntityPlayer)owner).isSpectator())
        {
            return false;
        }
        else if (followerAsLiving.getDistanceSq(owner) < (double)(minDist * minDist))
        {
            return false;
        }
        else
        {
            ownerAsLiving = owner;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return !petPathfinder.noPath() && followerAsLiving.getDistanceSq(ownerAsLiving) > (double)(maxDist * maxDist);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        timeToRecalcPath = 0;
        oldWaterCost = followerAsLiving.getPathPriority(PathNodeType.WATER);
        followerAsLiving.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        ownerAsLiving = null;
        petPathfinder.clearPath();
        followerAsLiving.setPathPriority(PathNodeType.WATER, oldWaterCost);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        followerAsLiving.getLookHelper().setLookPositionWithEntity(ownerAsLiving, 10.0F, (float)followerAsLiving.getVerticalFaceSpeed());

        if (--timeToRecalcPath <= 0)
        {
            timeToRecalcPath = 10;

            if (!petPathfinder.tryMoveToEntityLiving(ownerAsLiving, followSpeed))
            {

                    if (followerAsLiving.getDistanceSq(ownerAsLiving) >= 144.0D)
                    {
                        int i = MathHelper.floor(ownerAsLiving.posX) - 2;
                        int j = MathHelper.floor(ownerAsLiving.posZ) - 2;
                        int k = MathHelper.floor(ownerAsLiving.getEntityBoundingBox().minY);

                        for (int l = 0; l <= 4; ++l)
                        {
                            for (int i1 = 0; i1 <= 4; ++i1)
                            {
                                if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && isTeleportFriendlyBlock(i, j, k, l, i1))
                                {
                                    followerAsLiving.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F),
                                            followerAsLiving.rotationYaw, followerAsLiving.rotationPitch);
                                    petPathfinder.clearPath();
                                    return;
                                }
                            }
                        }
                    }

            }
        }
    }

    protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_)
    {
        BlockPos blockpos = new BlockPos(x + p_192381_4_, y - 1, p_192381_2_ + p_192381_5_);
        IBlockState iblockstate = world.getBlockState(blockpos);
        return iblockstate.getBlockFaceShape(world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn(followerAsLiving)
                && world.isAirBlock(blockpos.up())
                && world.isAirBlock(blockpos.up(2));
    }
}