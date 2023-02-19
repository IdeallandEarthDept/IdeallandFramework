package com.somebody.idlframework.blocks;

import com.somebody.idlframework.IdlFramework;
import com.somebody.idlframework.init.ModCreativeTab;
import com.somebody.idlframework.item.ModItems;
import com.somebody.idlframework.util.CommonFunctions;
import com.somebody.idlframework.util.IDLSkillNBT;
import com.somebody.idlframework.util.IHasModel;
import com.somebody.idlframework.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframework.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockBase extends Block implements IHasModel
{
	protected boolean showGuaSocketDesc = false;
	protected boolean shiftToShowDesc = false;
	protected boolean use_flavor = false;
	protected boolean logNBT = false;



	public BlockBase(String name, Material material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModCreativeTab.IDL_MISC);;
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

		setHardness(5.0F);
		//setResistance(1000.0F);
		//setHarvestLevel("pickaxe", 1);
		//setLightLevel(1f);
		setLightOpacity(1);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return super.getItemDropped(state, rand, fortune);
	}

	@Override
	public int quantityDropped(Random rand) {
		return super.quantityDropped(rand);
	}
	
	@Override
	public void registerModels() {
		IdlFramework.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}



	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {

		IDLSkillNBT.addInformation(stack,world,tooltip,flag,shiftToShowDesc, showGuaSocketDesc, use_flavor,
				getMainDesc(stack,world,tooltip,flag));

		if (logNBT)
		{
			tooltip.add(IDLNBTUtil.getNBT(stack).toString());
		}
	}

	@SideOnly(Side.CLIENT)
	public String descGetKey(ItemStack stack, World world, boolean showFlavor)
	{
		return showFlavor ? (stack.getUnlocalizedName() + IDLNBTDef.FLAVOR_KEY)
				: (stack.getUnlocalizedName() + IDLNBTDef.DESC_COMMON);
	}

	@SideOnly(Side.CLIENT)
	public String getMainDesc(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		if (CommonFunctions.isShiftPressed() || !shiftToShowDesc)
		{
			String key = descGetKey(stack,world,false);
			if (I18n.hasKey(key))
			{
				return I18n.format(key);
			}
			else
			{
				return "";
			}
		}

		if (!CommonFunctions.isShiftPressed() && use_flavor)
		{
			String key = descGetKey(stack,world,true);
			if (I18n.hasKey(key))
			{
				return I18n.format(key);
			}
			else
			{
				return "";
			}
		}
		return "";
	}
}
