package com.deeplake.idealland.item.edicts;

import com.deeplake.idealland.blocks.ModBlocks;
import com.deeplake.idealland.util.CommonFunctions;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBuildSimpleHouse extends ItemEdictBase {
    public float XZRangeRadius = 10f;
    public float YRangeRadius = 10f;

//    public ItemBuildSimpleHouse()
//    {
//        super();
//    }

    public ItemBuildSimpleHouse(String name)
    {
        super(name);
    }


    public void OnCastSuccess(ItemStack stack, World world, EntityLivingBase caster, int time)
    {
        if (!world.isRemote) {
            BlockPos origin = caster.getPosition().add(0,-1,0);//origin is under feet

            int floorReach = 3;//radius - 1
            int wallHeight = 3;
            int windowHeight = 1;
            int windowWidth = 1;

            IBlockState floorMaterial =  Blocks.DOUBLE_STONE_SLAB.getDefaultState();
            IBlockState wallMaterial =  Blocks.CONCRETE.getDefaultState();
            IBlockState roofMaterial =  Blocks.CONCRETE.getDefaultState();
            IBlockState windowMaterial =  Blocks.GLASS.getDefaultState();
            IBlockState lightMaterial = ModBlocks.IDEALLAND_LIGHT_BASIC.getDefaultState();

            //Space Inside
            CommonFunctions.FillWithBlockCentered(world, origin.add(0,floorReach,0), floorReach, 3, floorReach, Blocks.AIR.getDefaultState());
            //Floor
            CommonFunctions.FillWithBlockCentered(world, origin.add(0,0,0), floorReach, 0, floorReach, floorMaterial);
            CommonFunctions.FillWithBlockCentered(world, origin.add(0,-1,0), floorReach + 1, 0, floorReach + 1,floorMaterial);
            //Window
            CommonFunctions.BuildWallWithBlockCentered(world, origin.add(floorReach,1,0), 0, wallHeight, floorReach,wallMaterial);
            CommonFunctions.BuildWallWithBlockCentered(world, origin.add(-floorReach,1,0), 0, wallHeight, floorReach, wallMaterial);
            CommonFunctions.BuildWallWithBlockCentered(world, origin.add(0,1,floorReach), floorReach, wallHeight, 0, wallMaterial);
            CommonFunctions.BuildWallWithBlockCentered(world, origin.add(0,1,-floorReach), floorReach, wallHeight, 0, wallMaterial);
            //Roof
            CommonFunctions.FillWithBlockCentered(world, origin.add(0,wallHeight + 1,0), floorReach, 0, floorReach, roofMaterial);
            //Windows
            CommonFunctions.BuildWallWithBlockCentered(world, origin.add(floorReach,2,0), 0, windowHeight, 1, windowMaterial);
            CommonFunctions.BuildWallWithBlockCentered(world, origin.add(-floorReach,2,0), 0, windowHeight, 1, windowMaterial);
            CommonFunctions.BuildWallWithBlockCentered(world, origin.add(0,2,floorReach), 1, windowHeight, 0, windowMaterial);
            CommonFunctions.BuildWallWithBlockCentered(world, origin.add(0,2,-floorReach), 1, windowHeight, 0, windowMaterial);

            //Light
            world.setBlockState(origin.add(0,wallHeight + 1,0), lightMaterial);
            //Furnitures
            world.setBlockState(origin.add(floorReach - 1,1,floorReach - 1), Blocks.CRAFTING_TABLE.getDefaultState());
            world.setBlockState(origin.add(floorReach - 1,2,floorReach - 1), Blocks.FURNACE.getDefaultState());

            world.setBlockState(origin.add(1 - floorReach ,1,floorReach - 1), Blocks.CHEST.getDefaultState());
            world.setBlockState(origin.add(1 - floorReach ,1,floorReach - 2), Blocks.CHEST.getDefaultState());
            world.setBlockState(origin.add(1 - floorReach ,1,floorReach - 3), Blocks.ANVIL.getDefaultState());

            //Door
            world.setBlockState(origin.add(0 ,1,floorReach), Blocks.BIRCH_DOOR.getDefaultState());
            world.setBlockState(origin.add(0 ,2,floorReach), Blocks.BIRCH_DOOR.getDefaultState().withProperty(BlockDoor.HALF,  BlockDoor.EnumDoorHalf.UPPER));
        }
        super.OnCastSuccess(stack, world, caster, time);
    }
}
