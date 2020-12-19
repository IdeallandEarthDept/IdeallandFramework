package com.somebody.idlframewok.recipe.special;

import com.somebody.idlframewok.item.goblet.ItemP2WGoblet;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

import static net.minecraft.init.Items.GOLD_INGOT;

public class GobletFill extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		int foundGoldXP = 0;
		boolean foundCup = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemP2WGoblet)
				{
					if (foundCup) {
						return false;//only one sword at a time
					}
					foundCup = true;
				}
				else if (stack.getItem() == GOLD_INGOT)
				{//found a xp item
					foundGoldXP++;
				}
				else if(stack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.GOLD_BLOCK)))
				{
					foundGoldXP+=9;
				}
				else
				{
					return false;
				}
			}
		}
		return foundGoldXP > 0 && foundCup;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		int payingXP = 0;
		
		ItemStack sword = ItemStack.EMPTY;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemP2WGoblet)
				{
					sword = stack;
					payingXP += ((ItemP2WGoblet)(sword.getItem())).GetPayingEXP(stack);
				}
				else if(stack.getItem() == GOLD_INGOT)
				{
					payingXP++;
				}
				else if(stack.getItem().getUnlocalizedName().equals(ItemBlock.getItemFromBlock(Blocks.GOLD_BLOCK).getUnlocalizedName()))
				{
					payingXP+=9;
				}
				//IdlFramework.Log(stack.getItem().getUnlocalizedName() + " vs " + ItemBlock.getItemFromBlock(Blocks.GOLD_BLOCK).getUnlocalizedName());
			}
		}

		if(sword.isEmpty() || payingXP == 0) {
			return ItemStack.EMPTY;
		}

		ItemStack swordResult = sword.copy();
		((ItemP2WGoblet)(swordResult.getItem())).SetPayingEXP(swordResult, payingXP);

		return swordResult;
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

