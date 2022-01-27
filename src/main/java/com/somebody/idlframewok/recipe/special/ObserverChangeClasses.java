package com.somebody.idlframewok.recipe.special;

import com.somebody.idlframewok.item.armor.ItemArmorObserver;
import com.somebody.idlframewok.item.misc.ItemClassEssence;
import com.somebody.idlframewok.item.skills.classfit.EnumSkillClass;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class ObserverChangeClasses extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		boolean foundCore = false;
		boolean foundEss = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemArmorObserver)
				{
					if (foundCore) {
						return false;//only one sword at a time
					}
					foundCore = true;
				}
				else if (stack.getItem() instanceof ItemClassEssence)
				{
					if (foundEss) {
						return false;//only one sword at a time
					}
					foundEss = true;
				}
				else
				{
					return false;
				}
			}
		}
		return foundEss && foundCore;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		ItemStack core = ItemStack.EMPTY;
		EnumSkillClass skillClass = EnumSkillClass.NONE;
		ItemArmorObserver observer = null;
		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemArmorObserver)
				{
					core = stack;
					observer = (ItemArmorObserver) stack.getItem();
				}
				else if (stack.getItem() instanceof ItemClassEssence)
				{
					skillClass = ((ItemClassEssence)stack.getItem()).skillClass;
				}
				else
				{
					return ItemStack.EMPTY;
				}
			}
		}

		if (observer == null)
		{
			return ItemStack.EMPTY;
		}

		//prevent pure waste
		if (observer.getBoostLevel(core, skillClass) > 0)
		{
			return ItemStack.EMPTY;
		}

		ItemStack result = core.copy();
		observer.setBoostLevel(result, skillClass, 1);

		return result;
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

