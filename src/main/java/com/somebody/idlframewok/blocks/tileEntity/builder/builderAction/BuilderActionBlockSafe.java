package com.somebody.idlframewok.blocks.tileEntity.builder.builderAction;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class BuilderActionBlockSafe extends BuilderActionBlock {

    public static Block block_req = Blocks.BRICK_BLOCK;

    public BuilderActionBlockSafe(Block _block, Block _req , int x, int y, int z) {
        super(_block, x, y, z);
        block_req = _req;
    }

    public BuilderActionBlockSafe(Block _block, int x, int y, int z) {
        super(_block, x, y, z);
    }

    public BuilderActionBlockSafe(Block _block, BlockPos blockPos) {
        super(_block, blockPos);
    }

    public BuilderActionBlockSafe(IBlockState _block_state, int x, int y, int z) {
        super(_block_state, x, y, z);
    }

    public BuilderActionBlockSafe(IBlockState _block_state, BlockPos blockPos) {
        super(_block_state, blockPos);
    }

    @Override
    public boolean Execute(World world, BlockPos ori_pos){
        if (relativePos.lengthSquared() < 1) {
            //IdlFramework.LogWarning("Trying to build a block at self-pos.");
            return true;
        }

        BlockPos pos = ori_pos.add(relativePos.x, relativePos.y, relativePos.z);
        if (!world.isBlockLoaded(pos)) {
            return false;
        }

        if (!world.isRemote) {
            if (world.getBlockState(pos).getBlock() == block_req) {
                return super.Execute(world, ori_pos);
            } else {
                float range = 0.5f;
                Vec3d posInFloat = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                AxisAlignedBB aabb = new AxisAlignedBB(posInFloat.x - range, posInFloat.y - range, posInFloat.z - range, posInFloat.x + range, posInFloat.y + range, posInFloat.z + range);
                List<Entity> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
                if (entities.size() > 0) {
                    //building stuck because of entity
                    return false;
                }
                world.setBlockState(pos, block_req.getDefaultState());
            }
        }
        return false;
    }
}
