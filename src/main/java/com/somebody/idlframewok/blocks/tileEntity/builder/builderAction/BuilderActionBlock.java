package com.somebody.idlframewok.blocks.tileEntity.builder.builderAction;

import com.somebody.idlframewok.IdlFramework;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class BuilderActionBlock extends BuilderActionBase {

    private Block block;
    protected IBlockState blockState;

    public BuilderActionBlock(Block _block, int x, int y, int z){
        super((float) x,(float) y,(float) z);
        block = _block;
        blockState = _block.getDefaultState();
    }

    public BuilderActionBlock(IBlockState _block_state, int x, int y, int z){
        super((float) x,(float) y,(float) z);
        block = _block_state.getBlock();
        blockState = _block_state;
    }

    public BuilderActionBlock(Block _block, BlockPos blockPos){
        super(blockPos);
        block = _block;
        blockState = _block.getDefaultState();
    }

    public BuilderActionBlock(IBlockState _block_state, BlockPos blockPos){
        super(blockPos);
        block = _block_state.getBlock();
        blockState = _block_state;
    }

    @Override
    public boolean Execute(World world, BlockPos ori_pos){
        if (relativePos.lengthSquared() < 1) {
            IdlFramework.LogWarning("Trying to build a block at self-pos.");
            return true;
        }

        BlockPos pos = ori_pos.add(relativePos.x, relativePos.y, relativePos.z);
        if (!world.isBlockLoaded(pos)) {
            return false;
        }
        float range = 0.5f;
        Vec3d posInFloat = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        AxisAlignedBB aabb = new AxisAlignedBB(posInFloat.x - range, posInFloat.y - range, posInFloat.z - range, posInFloat.x + range, posInFloat.y + range, posInFloat.z + range);
        List<Entity> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
        if (entities.size() > 0) {
            //building stuck because of entity
            return false;
        }

        if (!world.isRemote) {
            //should drop the original blocks
            //dig time should be considered
            if (world.getBlockState(pos) != blockState) {
                world.setBlockState(pos, blockState);
            }
        }
        return super.Execute(world, ori_pos);
    }
}
