package com.somebody.idlframewok.recipe.special;

import com.somebody.idlframewok.item.skills.ItemSkillBase;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

import static com.somebody.idlframewok.util.IDLSkillNBT.SetLevel;
import static com.somebody.idlframewok.util.IDLSkillNBT.getLevel;

public class SkillUpgrade extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		ItemStack stack1 = ItemStack.EMPTY, stack2 = ItemStack.EMPTY;
		int skillCount = 0;
		int lv1 = 0, lv2 = 0;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemSkillBase)
				{
					skillCount++;
					if (skillCount > 2)
					{
						return false;
					}

					if (skillCount == 1)
					{
						stack1 = stack;
						ItemSkillBase itemSkillBase = (ItemSkillBase)(stack.getItem());
						lv1 = getLevel(stack);
						if (lv1 >= itemSkillBase.maxLevel)
						{
							return false;
						}
					}
					else {
						stack2 = stack;
						lv2 = getLevel(stack);
					}
				}
				else
				{
					//DWeapons.logger.warn("Find useless components:[{}], instanceof", stack.getItem().getUnlocalizedName());
					return false; //Found other.
				}
			}
		}

		return skillCount == 2 && (stack1.getItem() == stack2.getItem()) && lv1 == lv2;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		ItemStack stack1 = ItemStack.EMPTY, stack2 = ItemStack.EMPTY;
		ItemStack stackResult = ItemStack.EMPTY;
		int skillCount = 0;
		int lv1 = 0, lv2 = 0;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemSkillBase)
				{
					skillCount++;
					if (skillCount > 2)
					{
						return stackResult;
					}

					if (skillCount == 1)
					{
						ItemSkillBase itemSkillBase = (ItemSkillBase)(stack.getItem());
						stack1 = stack;
						lv1 = getLevel(stack);
						stackResult = stack1.copy();
						SetLevel(stackResult, lv1 + 1);
					}
				}
				else
				{
					return stackResult; //Found other.
				}
			}
		}

		if (skillCount == 2 && (stack1.getItem() == stack2.getItem()))
		{

		}

		return stackResult;
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

