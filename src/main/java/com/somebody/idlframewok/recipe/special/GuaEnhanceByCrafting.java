package com.somebody.idlframewok.recipe.special;

import com.somebody.idlframewok.item.IGuaEnhance;
import com.somebody.idlframewok.util.IDLGeneral;
import com.somebody.idlframewok.util.IDLSkillNBT;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class GuaEnhanceByCrafting extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		boolean foundMainItem = false;
		boolean foundSecond = false;
		IGuaEnhance guaEnhance = null;
		int guaIndex = -1;
		//currently only one gua at a time

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof IGuaEnhance)
				{
					if (foundMainItem || (IDLSkillNBT.GetGuaEnhanceFree(stack) == 0)) {
						return false;
					}
					foundMainItem = true;
					guaEnhance = (IGuaEnhance) stack.getItem();
				}
				else if (!foundSecond)
				{//found a gua
					int index = IDLGeneral.returnGuaIndex(stack);
					if (index >= 0)
					{
						foundSecond = true;
						guaIndex = index;
					}else {
						return false;
					}

				}
				else
				{
					return false; //Found an other.
				}
			}
		}

		if (guaEnhance != null)
		{
			return foundSecond && guaEnhance.acceptGuaIndex(guaIndex);
		}
		else {
			return false;
		}
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		//wont check gua index here.
		ItemStack goaEnhanceable = ItemStack.EMPTY;

		boolean foundMain = false;
		boolean foundSecond = false;
		int guaIndex = -1;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof IGuaEnhance)
				{
					if (foundMain) {
						return ItemStack.EMPTY;//only one goblwt at a time
					}
					goaEnhanceable = stack;
					foundMain = true;
				}
				else if (IDLGeneral.returnGuaIndex(stack) >= 0 && !foundSecond)
				{//found a gua
					foundSecond = true;
					guaIndex = IDLGeneral.returnGuaIndex(stack);
				}
				else
				{
					return ItemStack.EMPTY; //Found an other.
				}
			}
		}

		ItemStack result = goaEnhanceable.copy();
		if (foundMain && foundSecond)
		{
			int freeSockets = IDLSkillNBT.GetGuaEnhanceFree(goaEnhanceable);
			IDLSkillNBT.AddGuaEnhance(result, guaIndex, 1);
			IDLSkillNBT.SetGuaEnhanceFree(result, freeSockets - 1);
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

