package com.somebody.idlframewok.blocks.tileEntity.builder.builderAction;

import com.somebody.idlframewok.IdlFramework;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;

public class BuilderActionSummonEntity extends BuilderActionBase {

    Vec3d relative_pos;
    public boolean complete = false;

    protected Class<? extends Entity> toSummon;

//    BuilderActionSummonEntity(float x, float y, float z){
//        SetRelativePos(x,y,z);
//    }
//
//    BuilderActionSummonEntity(BlockPos blockPos){
//        SetRelativePos(blockPos.getX(),blockPos.getY(),blockPos.getZ());
//    }

    public BuilderActionSummonEntity(BlockPos blockPos, Class<? extends Entity> toSummon){
        super(blockPos);
        SetRelativePos(blockPos.getX(),blockPos.getY(),blockPos.getZ());
        this.toSummon = toSummon;
    }

    public boolean Execute(World world, BlockPos ori_pos){
        if (world.isRemote)
        {
            return true;
        }

        try {
            Entity summoned = toSummon.getConstructor(World.class).newInstance(world);
            summoned.setPosition(ori_pos.getX() + relative_pos.x + 0.5f,
                    ori_pos.getY() + relative_pos.y + 0.5f,
                    ori_pos.getZ() + relative_pos.z + 0.5f);
            world.spawnEntity(summoned);

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            IdlFramework.LogWarning("A building process failed to summon creature");
        }
        return super.Execute(world, ori_pos);
    }

    public void SetRelativePos(float x, float y, float z){
        relative_pos = new Vec3d(x,y,z);
    }
}
