package com.somebody.idlframewok.world.dimension.hexcube.structure;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import com.somebody.idlframewok.world.dimension.hexcube.HexCubeHelper;
import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class GenCubeSpawner extends GenCubeBase {

    public GenCubeSpawner(boolean notify) {
        super(notify);
    }

    public GenCubeSpawner(boolean notify, int xSize, int ySize, int zSize) {
        super(notify, xSize, ySize, zSize);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos positionOrigin) {
        //positionOrigin is 0,0,0 of the room. the corner of walls
        //if (hasLight)
        {
            int min = 1;
            int max = xSize -1;

            int minZ = 1;
            int maxZ = zSize - 1;

            int minY = 1;
            int maxY = ySize - 1;
            worldIn.setBlockState(positionOrigin.add(min, minY, minZ), Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK));
            worldIn.setBlockState(positionOrigin.add(min, minY, maxZ), Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK));
            worldIn.setBlockState(positionOrigin.add(max, minY, minZ), Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK));
            worldIn.setBlockState(positionOrigin.add(max, minY, maxZ), Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK));
            worldIn.setBlockState(positionOrigin.add(min, maxY, minZ), Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK));
            worldIn.setBlockState(positionOrigin.add(min, maxY, maxZ), Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK));
            worldIn.setBlockState(positionOrigin.add(max, maxY, minZ), Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK));
            worldIn.setBlockState(positionOrigin.add(max, maxY, maxZ), Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK));
        }

        BlockPos position = positionOrigin.add(xSize >> 1, ySize >> 1, zSize >> 1);

        BlockPos chestPos = position.add(0,-1,0);
        worldIn.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 2);

        IInventory container = Blocks.CHEST.getLockableContainer(worldIn, chestPos);
        if (container!=null) {
            ItemStack stack = new ItemStack(ModItems.ITEM_CUBIX_SWORD);
            IDLNBTUtil.SetDouble(stack, IDLNBTDef.DIFFICULTY, HexCubeHelper.getDifficulty(chestPos));
            container.setInventorySlotContents(0, stack);
            //Idealland.Log("Gen loot in box:%s",chestPos);
        }
        else {
            Idealland.LogWarning("Failed to gen loot in box:%s",chestPos);
        }

        worldIn.setBlockState(position, Blocks.MOB_SPAWNER.getDefaultState(), 2);
        TileEntity tileentity = worldIn.getTileEntity(position);

        if (tileentity instanceof TileEntityMobSpawner)
        {
            ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityId(pickMobSpawner(rand));
        }
        else
        {
            Idealland.LogWarning("[Cube Spawner]Failed to fetch mob spawner entity at (%s, %s, %s)", position.getX(), position.getY(), position.getZ());
        }



        return false;
    }

    /**
     * Randomly decides which spawner to use in a dungeon
     */
    private ResourceLocation pickMobSpawner(Random rand)
    {
        return net.minecraftforge.common.DungeonHooks.getRandomDungeonMob(rand);
    }

}
