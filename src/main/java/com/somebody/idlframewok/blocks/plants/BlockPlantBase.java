package com.somebody.idlframewok.blocks.plants;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.init.RegistryHandler;
import com.somebody.idlframewok.util.IHasModel;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockPlantBase extends BlockBush implements net.minecraftforge.common.IPlantable, IHasModel {

    boolean isSolid = true;
    boolean canPickup = false;
    EnumPlantType plantType = EnumPlantType.Plains;

    public BlockPlantBase(String name, Material material, EnumPlantType enumPlantType) {
        this(name, material);
        plantType = enumPlantType;
    }

    public BlockPlantBase(String name, Material material) {
        super(material);
        this.setTickRandomly(true);

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabsList.IDL_WORLD);

        ModBlocks.BLOCKS.add(this);
        addToItems();
    }

    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return plantType;
    }

    public void addToItems() {
        RegistryHandler.addToItems(this);
    }

    public BlockPlantBase setHarvestThis(String toolClass, int level) {
        setHarvestLevel(toolClass, level);
        return this;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public BlockPlantBase setSolid(boolean isSolid) {
        this.isSolid = isSolid;
        return this;
    }

    public BlockPlantBase setPickable(boolean val) {
        this.canPickup = val;
        return this;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (canPickup) {
            if (!worldIn.isRemote) {
                PlayerUtil.giveToPlayer(playerIn, new ItemStack(this, 1, getMetaFromState(state)));
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }

            return true;
        } else {
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        }
    }

    public boolean causesSuffocation(IBlockState state) {
        return isSolid;
    }

    @Deprecated
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        if (isSolid) {
            return blockState.getBoundingBox(worldIn, pos);
        } else {
            return null;
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return super.getItemDropped(state, rand, fortune);
    }

    @Override
    public int quantityDropped(Random rand) {
        return super.quantityDropped(rand);
    }
}
