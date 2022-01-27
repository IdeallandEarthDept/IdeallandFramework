package com.somebody.idlframewok.blocks.blockMisc;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.designs.god16.Init16Gods;
import com.somebody.idlframewok.util.Color16Def;
import com.somebody.idlframewok.util.CommonDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockClam extends BlockBase {
    public static final int AGE_MAX = 7;
    public static final PropertyInteger AGE = PropertyInteger.create(IDLNBTDef.TAG_AGE, 0, AGE_MAX);

    protected PropertyInteger getAgeProperty() {
        return AGE;
    }

    public BlockClam(String name, Material material) {
        super(name, material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), 0));
        setTickRandomly(true);
        setHarvestThis(IDLNBTDef.TOOL_PICKAXE, CommonDef.DigLevel.DIAMOND_P.ordinal());
    }

//    @Override
//    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
//        super.randomTick(worldIn, pos, state, random);
//    }

    protected int getAge(IBlockState state) {
        return ((Integer) state.getValue(this.getAgeProperty())).intValue();
    }

    public IBlockState withAge(int age) {
        return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
    }

    public boolean isMaxAge(IBlockState state) {
        return state.getValue(this.getAgeProperty()) >= AGE_MAX;
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if (!worldIn.isAreaLoaded(pos, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light

        if (worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() != Blocks.WATER) {
            return;
        }

        int i = this.getAge(state);

        if (i < AGE_MAX) {
            float f = getGrowthChance(this, worldIn, pos);

            if (rand.nextFloat() < f) {
                grow(worldIn, pos, state);
            }
        }
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        int i = this.getAge(state);
        int j = AGE_MAX;

        if (i > j) {
            i = j;
        }

        worldIn.setBlockState(pos, this.withAge(i), 2);
    }

    protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos) {
        int baseChance = 100;
        if (worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.WATER) {
            baseChance *= 2;
        }
        return baseChance / 10000f;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //todo: give pearl
        if (!worldIn.isRemote) {
            int result = 100;//ten thousand percent
            for (int i = 1; i <= CommonDef.MAX_BUILD_HEIGHT - pos.getY(); i++) {
                if (worldIn.getBlockState(pos.add(0, i, 0)).getBlock() != Blocks.WATER) {
                    break;
                }
                result += 100;
            }

            if (Init16Gods.isOfGod(Color16Def.WATER, worldIn.getBiome(pos))) {
                //todo: big pearl
            }
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    //
//    protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos)
//    {
//        int result = 100;//ten thousand percent
//        for (int i = 1; i <= CommonDef.MAX_BUILD_HEIGHT - pos.getY(); i++)
//        {
//            if (worldIn.getBlockState(pos.add(0,i,0)).getBlock() != Blocks.WATER)
//            {
//                break;
//            }
//            result += 100;
//        }
//
//        if (worldIn.getBiome(pos))
//        return result;
//    }
}
