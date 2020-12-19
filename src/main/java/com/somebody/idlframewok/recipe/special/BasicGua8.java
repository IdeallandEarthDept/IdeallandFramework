package com.somebody.idlframewok.recipe.special;

import com.somebody.idlframewok.item.misc.ItemBasicBinary;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class BasicGua8 extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		//must place the first sign in first line
		int width = var1.getWidth();
		int usedCol = -1;
		for (int i = 0; i < width; i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				Item itemType = stack.getItem();
				if (itemType instanceof ItemBasicBinary) {
					usedCol = i;
					break;
				}else
				{
					return false;
				}
			}
		}

		if (usedCol < 0) {
			return false;
		}

		int itemCount = 0;
		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				itemCount++;
				if (i % width != usedCol) {
					return false;//wrong shape
				}
				else {
					Item itemType = stack.getItem();
					if (!(itemType instanceof ItemBasicBinary)) {
						return false;
					}
				}
			}
		}

		return itemCount == 3;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		int guaValue = 0;
		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			Item itemType = stack.getItem();
			if(!stack.isEmpty() && (itemType instanceof ItemBasicBinary)) {
				guaValue <<= 1;
				guaValue += ((ItemBasicBinary) itemType).getValue();
			}
		}

//		if (guaValue >= 0 && guaValue <= ModItems.GUA.length) {
//			return new ItemStack(ModItems.GUA[guaValue]);
//		}

		return ItemStack.EMPTY;
	}

	@Override
	public boolean canFit(int width, int height) {
		return height >= 3;
	}

	@Nonnull
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
}

