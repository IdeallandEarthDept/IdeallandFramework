package com.somebody.idlframewok.blocks;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.IMetaName;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.enumGroup.EnumVariants;
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

public class BlockVariantBasic extends BlockBase implements IMetaName
{
	//quite useless as meta will be discarded in 1.13
	public static final PropertyEnum<EnumVariants> VARIANT = PropertyEnum.create("variant", EnumVariants.class);

	final int maxVariants;
	public BlockVariantBasic(String name, Material material, int maxUsedVariants) {
		super(name, material);
		if (maxUsedVariants < 0 || maxUsedVariants > 16)
		{
			Idealland.Log("Illegal variants for %s:%d", getUnlocalizedName() ,maxUsedVariants);
			maxUsedVariants = 1;
		}
		this.maxVariants = maxUsedVariants;
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumVariants.V_0));
	}

	public void addToItems()
	{
		ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}

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
		return this.getDefaultState().withProperty(VARIANT, EnumVariants.byMetadata(meta));
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
		int i = 0;
		for (EnumVariants variant : EnumVariants.values())
		{
			if (i >= maxVariants)
			{
				break;
			}
			i++;
			items.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}

	@Override
	public String getSpecialName(ItemStack stack) {
		return EnumVariants.values()[stack.getItemDamage()].getName();
	}

	@Override
	public void registerModels() {
		for (int i = 0; i < EnumVariants.values().length; i++) {
			Idealland.proxy.registerItemRenderer(Item.getItemFromBlock(this), i, getUnlocalizedName() + "_" + EnumVariants.values()[i].getName(),
					IDLNBTDef.NAME_INVENTORY);
		}
	}
}
