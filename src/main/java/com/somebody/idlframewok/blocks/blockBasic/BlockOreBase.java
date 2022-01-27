package com.somebody.idlframewok.blocks.blockBasic;

import com.somebody.idlframewok.init.RegistryHandler;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.IHasModel;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockOreBase extends BlockOre implements IHasModel {
    boolean isSolid = true;
    boolean canPickup = false;

    public BlockOreBase(String name) {
        super();
        CommonFunctions.init(this, name);
        addToItems();
    }

    public void addToItems() {
        RegistryHandler.addToItems(this);
    }

    public BlockOreBase setHarvestThis(String toolClass, int level) {
        setHarvestLevel(toolClass, level);
        return this;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public BlockOreBase setSolid(boolean isSolid) {
        this.isSolid = isSolid;
        return this;
    }

    public BlockOreBase setPickable(boolean val) {
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

}
