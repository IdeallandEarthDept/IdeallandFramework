package com.somebody.idlframewok.blocks;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.IMetaName;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.enumGroup.EnumGodType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockVariantExample extends BlockBase implements IMetaName
{
    public static final PropertyEnum<EnumGodType> VARIANT = PropertyEnum.create("variant", EnumGodType.class);
	protected int typeCount = 1;
	public BlockVariantExample(String name, Material material) {
		super(name, material);
//		if (count < 1)
//		{
//			Idealland.LogWarning("Item %s has less than 1 types.", name);
//		}
//		typeCount = count;

        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumGodType.EARTH));
	}

	public void addToItems()
	{
		ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}
//	@Override
//	public String getUnlocalizedName() {
//		return super.getUnlocalizedName();
//	}
//
//	public String getUnlocalizedName(ItemStack stack)
//	{
//		int i = stack.getMetadata();
//		return super.getUnlocalizedName() + "_" + i;
//	}

	/**
     * Gets the metadata of the item this BlockPhasingOre can drop. This method is called when the block gets destroyed. It
	 * returns the metadata of the dropped item based on the old metadata of the block.
	 */
	public int damageDropped(IBlockState state)
	{
		return state.getValue(VARIANT).getMeta();
	}

	/**
     * Convert the given metadata into a BlockState for this BlockPhasingOre
	 */
	public IBlockState getStateFromMeta(int meta)
	{
        return this.getDefaultState().withProperty(VARIANT, EnumGodType.byMetadata(meta));
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(VARIANT).getMeta();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
        for (EnumGodType variant : EnumGodType.values())
		{
			items.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}

	@Override
	public String getSpecialName(ItemStack stack) {
        return EnumGodType.values()[stack.getItemDamage()].getName();
	}

	@Override
	public void registerModels() {
//		for (int i = 0; i < typeCount; i++)
//		{
//			Idealland.proxy.registerItemRenderer(Item.getItemFromBlock(this), i, IDLNBTDef.NAME_INVENTORY);
//		}

        for (int i = 0; i < EnumGodType.values().length; i++) {
			Idealland.proxy.registerItemRenderer(Item.getItemFromBlock(this), i, getUnlocalizedName() + "_" + EnumGodType.values()[i].getName(), IDLNBTDef.NAME_INVENTORY);
		}
	}
}
