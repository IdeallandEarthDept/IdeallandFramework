package com.somebody.idlframewok.recipe.special;

import com.somebody.idlframewok.item.goblet.ItemDigGoblet;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class GobletDigBlockAssign extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		//before finding the weapon, we dont know whats the max pearl accepted
		boolean foundGoblet = false;
		boolean foundBlock = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemDigGoblet)
				{
					if (foundGoblet) {
						//IdlFramework.Log("Found more than one goblet item");
						return false;//only one goblwt at a time
					}
					foundGoblet = true;
				}
				else if ((stack.getItem() instanceof ItemBlock) && !foundBlock)
				{//found a block
					foundBlock = true;
				}
				else
				{
					return false; //Found an other.
				}
			}
		}

		return foundBlock && foundGoblet;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		ItemStack cup = ItemStack.EMPTY;

		boolean foundGoblet = false;
		boolean foundBlock = false;
		ItemBlock block = null;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemDigGoblet)
				{
					if (foundGoblet) {
						//IdlFramework.Log("Found more than one goblet item");
						return ItemStack.EMPTY;//only one goblwt at a time
					}
					cup = stack;
					foundGoblet = true;
				}
				else if ((stack.getItem() instanceof ItemBlock) && !foundBlock)
				{//found a block
					foundBlock = true;
					block = (ItemBlock) stack.getItem();
				}
				else
				{
					return ItemStack.EMPTY; //Found an other.
				}
			}
		}

		ItemStack result = cup.copy();
		if (foundGoblet && foundBlock)
		{
			ItemDigGoblet goblet = (ItemDigGoblet) result.getItem();
			goblet.SetStoredBlockName(result, block.getBlock());
		}

		return result;
	}

	@Override
	public boolean canFit(int width, int height) {
		return true;
	}

	@Nonnull
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
}

