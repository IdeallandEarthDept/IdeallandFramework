package com.somebody.idlframewok.blocks.blockDungeon;

import com.somebody.idlframewok.blocks.stateEnums.EnumActive;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDungeonStepLight extends BlockDungeonWall {
    final int ACTIVE_LIGHT_LEVEL;
    final int INACTIVE_LIGHT_LEVEL;

    public static final PropertyEnum<EnumActive> SWITCH = PropertyEnum.create("active", EnumActive.class);

    public BlockDungeonStepLight(String name, Material material, int lightOff, int lightOn) {
        super(name, material);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(SWITCH, EnumActive.INACTIVE));
        ACTIVE_LIGHT_LEVEL = lightOn;
        INACTIVE_LIGHT_LEVEL = lightOff;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        //We don't need 2 blocks. Redstone light is stupid.
        return state.getValue(SWITCH) == EnumActive.ACTIVE ? ACTIVE_LIGHT_LEVEL : INACTIVE_LIGHT_LEVEL;
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(SWITCH).ordinal();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, SWITCH);
    }


    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);
        if (!worldIn.isRemote) {
            IBlockState state = worldIn.getBlockState(pos);
            if (entityIn instanceof EntityPlayer && state.getValue(SWITCH) == EnumActive.INACTIVE) {
                worldIn.setBlockState(pos, getDefaultState().withProperty(SWITCH, EnumActive.ACTIVE));
            }
        }
    }
}
