package com.somebody.idlframewok.recipe.special;

import com.somebody.idlframewok.blocks.ModBlocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class TeleporterC16 extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		boolean foundCore = false;
		Map<Integer, Boolean> map = new HashMap<>();

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() == Items.IRON_INGOT)
				{
					if (foundCore) {
						return false;//only one sword at a time
					}
					foundCore = true;
				}
				else if (stack.getItem() == Items.DYE)
				{//found a dye item
					int variant = stack.getMetadata();
					if (!map.containsKey(variant))
					{
						map.put(variant, true);
					}
					else {
						//duplicate
						return false;
					}
				}
				else
				{
					return false;
				}
			}
		}
		return map.size() == 8 && foundCore;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		return new ItemStack(ModBlocks.TELEPORTER_DIM_C16);
	}

	@Override
	public boolean canFit(int width, int height) {
		return width >= 3 && height >= 3;
	}

	@Nonnull
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
}

