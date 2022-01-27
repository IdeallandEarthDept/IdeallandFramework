package com.somebody.idlframewok.blocks.blockBasic;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.ModBlocks;
import com.somebody.idlframewok.blocks.tileEntity.TileEntityChestCustom;
import com.somebody.idlframewok.gui.ModGuiElementLoader;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.IHasModel;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockCustomChest extends BlockContainer implements IHasModel {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected static final AxisAlignedBB NOT_CONNECTED_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);

    public BlockCustomChest(String name, Material materialIn) {
        super(materialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        ModBlocks.BLOCKS.add(this);
        addToItems();
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return NOT_CONNECTED_AABB;
    }

//rotationIn
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @SuppressWarnings("deprecation")
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }


    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    /**
     * Returns the blockstate with the given mirrorIn of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
    //---------end rotationIn

    public void addToItems() {
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(Idealland.instance, ModGuiElementLoader.GUI_CUSTOM_CHEST, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityChestCustom tileEntityChest = (TileEntityChestCustom) worldIn.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(worldIn, pos, tileEntityChest);
        super.breakBlock(worldIn, pos, state);
    }


    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
            state = state.withProperty(FACING, enumfacing);
            worldIn.setBlockState(pos, state, 3);

            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if (tileEntity instanceof TileEntityChestCustom && stack.hasDisplayName()) {
                ((TileEntityChestCustom) tileEntity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityChestCustom();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(FACING).build());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), IDLNBTDef.NAME_INVENTORY));
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(this), 0, TileEntityChestCustom.class);
    }

}
