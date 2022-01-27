package com.somebody.idlframewok.blocks.blockDungeon.traps;

import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.blocks.stateEnums.EnumActive;
import com.somebody.idlframewok.blocks.tileEntity.dungeon.TileEntityTrapTickBase;
import com.somebody.idlframewok.designs.combat.ModDamageSourceList;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockDungeonCustomizedTrap extends BlockDungeonTrapBase implements ITileEntityProvider {

    public static final PropertyEnum<EnumActive> ACTIVE_PROPERTY_ENUM = PropertyEnum.create("active", EnumActive.class);

    public static boolean keepTileEntity = false;

    public BlockDungeonCustomizedTrap(String name, Material material) {
        super(name, material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE_PROPERTY_ENUM, EnumActive.ACTIVE));
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);
        if (!worldIn.isRemote) {
            if (worldIn.getTileEntity(pos) instanceof TileEntityTrapTickBase) {
                TileEntityTrapTickBase tileEntityTrapTickBase = (TileEntityTrapTickBase) worldIn.getTileEntity(pos);
                if (entityIn instanceof EntityLivingBase && tileEntityTrapTickBase.isActiveNow())
                {
                    entityIn.attackEntityFrom(ModDamageSourceList.TRAP_ABSOLUTE,
                            tileEntityTrapTickBase.getDamageRatio() * ((EntityLivingBase) entityIn).getMaxHealth());
                }
            }
        }
    }

    //TE
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTrapTickBase();
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepTileEntity)
        {
            super.breakBlock(worldIn, pos, state);
        }
    }

    //shifting states
    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
//        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepTileEntity = true;

        if (active)
        {
            worldIn.setBlockState(pos, ModBlocks.BASE_TICK_TRAP.getDefaultState().withProperty(ACTIVE_PROPERTY_ENUM, EnumActive.ACTIVE), 3);
        }
        else
        {
            worldIn.setBlockState(pos, ModBlocks.BASE_TICK_TRAP.getDefaultState().withProperty(ACTIVE_PROPERTY_ENUM, EnumActive.INACTIVE), 3);
        }

        keepTileEntity = false;

//        if (tileentity != null)
//        {
//            tileentity.validate();
//            worldIn.setTileEntity(pos, tileentity);
//        }
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {ACTIVE_PROPERTY_ENUM});
    }

    /**
     * Convert the given metadata into a BlockState for this BlockPhasingOre
     */
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(ACTIVE_PROPERTY_ENUM, meta > 0 ? EnumActive.ACTIVE : EnumActive.INACTIVE);
        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ACTIVE_PROPERTY_ENUM) == EnumActive.ACTIVE ? 1 : 0;
    }
}
