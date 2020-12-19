package com.somebody.idlframewok.blocks.blockBasic;

import com.somebody.idlframewok.blocks.BlockBase;
import com.somebody.idlframewok.blocks.tileEntity.orbs.TileEntityNullifyOrb;
import com.somebody.idlframewok.blocks.tileEntity.orbs.TileEntityNullifyOrbMor;
import com.somebody.idlframewok.init.ModCreativeTab;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNullifyOrb extends BlockBase implements ITileEntityProvider {

	public boolean isMoroonProof = false;
	public BlockNullifyOrb(String name, Material material) {
		super(name, material);
		
		setSoundType(SoundType.METAL);
		setHardness(5.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 3);
		setLightOpacity(1);
		setCreativeTab(ModCreativeTab.IDL_MISC);
	}

	public BlockNullifyOrb setAdvanced(boolean val)
	{
		isMoroonProof = val;
		return this;
	}


	//optional
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return super.getItemDropped(state, rand, fortune);
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return super.quantityDropped(rand);
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		if (isMoroonProof)
		{
			return new TileEntityNullifyOrbMor();
		}else {
			return new TileEntityNullifyOrb();
		}
	}


}
