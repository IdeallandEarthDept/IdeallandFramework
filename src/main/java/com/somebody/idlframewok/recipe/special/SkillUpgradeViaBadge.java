package com.somebody.idlframewok.recipe.special;

import com.somebody.idlframewok.init.ModConfig;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.item.skills.ItemSkillBase;
import com.somebody.idlframewok.item.skills.classfit.ItemSkillClassSpecific;
import com.somebody.idlframewok.item.skills.classfit.ItemSkillCore;
import com.somebody.idlframewok.util.IDLSkillNBT;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class SkillUpgradeViaBadge extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		ItemStack stack1 = ItemStack.EMPTY, stack2 = ItemStack.EMPTY;
		int skillCount = 0;
		boolean hasBadge = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemSkillBase)
				{
					if (stack.getItem() instanceof ItemSkillClassSpecific || stack.getItem() instanceof ItemSkillCore)
					{
						//these item use special upgrades
						return false;
					}

					skillCount++;
					if (skillCount > 1)
					{
						return false;
					}

					int lv1 = IDLSkillNBT.getLevel(stack);
					ItemSkillBase itemSkillBase = (ItemSkillBase)(stack.getItem());
					if (lv1 >= itemSkillBase.maxLevel)
					{
						return false;
					}
				}
				else if(stack.getItem() == ModItems.ITEM_LEVEL_UP_BADGE)
				{
					hasBadge = true;
				}
				else
				{
					//DWeapons.logger.warn("Find useless components:[{}], instanceof", stack.getItem().getUnlocalizedName());
					return false; //Found other.
				}
			}
		}

		return skillCount == 1 && hasBadge;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {

		if (!ModConfig.GeneralConf.SKILL_EASY_LV_UP)
		{
			return ItemStack.EMPTY;
		}

		ItemStack stack1 = ItemStack.EMPTY;
		ItemStack stackResult = ItemStack.EMPTY;
		int skillCount = 0;
		int lv1 = 0;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemSkillBase)
				{
					skillCount++;
					if (skillCount > 1)
					{
						return ItemStack.EMPTY;
					}

					if (skillCount == 1)
					{
						stack1 = stack;
						lv1 = IDLSkillNBT.getLevel(stack);
						stackResult = stack1.copy();
						IDLSkillNBT.SetLevel(stackResult, lv1 + 1);
					}
				}
			}
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

