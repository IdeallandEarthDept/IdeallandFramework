package com.deeplake.idealland.blocks.blockBasic;

import com.deeplake.idealland.blocks.BlockBase;
import com.deeplake.idealland.init.ModCreativeTab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class IdeallandLight extends BlockBase {

	public IdeallandLight(String name, Material material) {
		super(name, material);
		
		setSoundType(SoundType.METAL);
		setHardness(5.0F);
		setResistance(1000.0F);
		setHarvestLevel("pickaxe", 1);
		setLightLevel(1f);
		setLightOpacity(1);
		setCreativeTab(ModCreativeTab.IDL_MISC);
	}

	//optional
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return super.getItemDropped(state, rand, fortune);
	}
	
	@Override
	public int quantityDropped(Random rand) {
//		int max = 4;
//		int min = 1;
//		return rand.nextInt(max) + min;
		
		return super.quantityDropped(rand);
	}
}
