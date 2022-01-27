package com.somebody.idlframewok.world.worldgen.structure.dungeon;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.util.WorldGenUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum EnumDungeonTrap {
    NONE,
    HOLE,
    //pillars are bad idea as they blocks the road
    //SWELL,
    //PILLAR_2,
    //PILLAR_4,
    SPIKE_PIT,
    SPIKE_PIT_2,
    SPIKE_PIT_3,
    SPIKE_PIT_POISON,
    FLAME_PIT,
    FLAME_PIT_2,
    FLAME_PIT_3,
    SPIKE,
    SPIKE_2,
    SPIKE_3,
    SPIKE_POISON,
    FLAME,
    FLAME_2,
    FLAME_3;
    ;

    public static final IBlockState GRAVEL = Blocks.GRAVEL.getDefaultState();

    public static void buildBrick(World world, BlockPos posGround)
    {
        int num = world.rand.nextInt(100);
        if (num < 20)
        {
            world.setBlockState(posGround, ModBlocks.DUNGEON_WALL_CRACKED.getDefaultState());
        } else if (num < 40)
        {
            world.setBlockState(posGround, ModBlocks.DUNGEON_WALL_MOSS.getDefaultState());
        }
        else {
            world.setBlockState(posGround, ModBlocks.DUNGEON_WALL.getDefaultState());
        }
    }

    public void build(World world, BlockPos posGround)
    {
        switch (this)
        {
            case NONE:
                buildBrick(world, posGround);
                break;
            case HOLE:
                world.setBlockState(posGround, WorldGenUtil.AIR);
                break;
//            case SWELL:
//                world.setBlockState(posGround.add(0,1,0), GRAVEL);
//                break;
//            case PILLAR_2:
//                world.setBlockState(posGround.add(0,1,0), GRAVEL);
//                world.setBlockState(posGround.add(0,2,0), GRAVEL);
//                break;
//            case PILLAR_4:
//                //consider rewriting as this may block the road
//                world.setBlockState(posGround.add(0,1,0), GRAVEL);
//                world.setBlockState(posGround.add(0,2,0), GRAVEL);
//                world.setBlockState(posGround.add(0,3,0), GRAVEL);
//                world.setBlockState(posGround.add(0,4,0), GRAVEL);
//                break;
            case SPIKE_PIT:
                world.setBlockState(posGround, WorldGenUtil.AIR);
                world.setBlockState(posGround.add(0,-1,0), ModBlocks.TRAP_SPIKE.getDefaultState());
                break;
            case SPIKE_PIT_2:
                world.setBlockState(posGround, WorldGenUtil.AIR);
                world.setBlockState(posGround.add(0,-1,0), ModBlocks.TRAP_SPIKE_2.getDefaultState());
                break;
            case SPIKE_PIT_3:
                world.setBlockState(posGround, WorldGenUtil.AIR);
                world.setBlockState(posGround.add(0,-1,0), ModBlocks.TRAP_SPIKE_3.getDefaultState());
                break;
            case SPIKE_PIT_POISON:
                world.setBlockState(posGround, WorldGenUtil.AIR);
                world.setBlockState(posGround.add(0,-1,0), ModBlocks.TRAP_SPIKE_POISON.getDefaultState());
                break;
            case FLAME_PIT:
                world.setBlockState(posGround, WorldGenUtil.AIR);
                world.setBlockState(posGround.add(0,-1,0), ModBlocks.TRAP_FLAME.getDefaultState());
                break;
            case FLAME_PIT_2:
                world.setBlockState(posGround, WorldGenUtil.AIR);
                world.setBlockState(posGround.add(0,-1,0), ModBlocks.TRAP_FLAME_2.getDefaultState());
                break;
            case FLAME_PIT_3:
                world.setBlockState(posGround, WorldGenUtil.AIR);
                world.setBlockState(posGround.add(0,-1,0), ModBlocks.TRAP_FLAME_3.getDefaultState());
                break;
            case SPIKE:
                world.setBlockState(posGround.add(0,0,0), ModBlocks.TRAP_SPIKE.getDefaultState());
                break;
            case SPIKE_2:
                world.setBlockState(posGround.add(0,0,0), ModBlocks.TRAP_SPIKE_2.getDefaultState());
                break;
            case SPIKE_3:
                world.setBlockState(posGround.add(0,0,0), ModBlocks.TRAP_SPIKE_3.getDefaultState());
                break;
            case SPIKE_POISON:
                world.setBlockState(posGround.add(0,0,0), ModBlocks.TRAP_SPIKE_POISON.getDefaultState());
                break;
            case FLAME:
                world.setBlockState(posGround.add(0,0,0), ModBlocks.TRAP_FLAME.getDefaultState());
                break;
            case FLAME_2:
                world.setBlockState(posGround.add(0,0,0), ModBlocks.TRAP_FLAME_2.getDefaultState());
                break;
            case FLAME_3:
                world.setBlockState(posGround.add(0,0,0), ModBlocks.TRAP_FLAME_3.getDefaultState());
                break;
            default:
                //throw new IllegalStateException("Unexpected value: " + this);
                break;
        }
    }
}
