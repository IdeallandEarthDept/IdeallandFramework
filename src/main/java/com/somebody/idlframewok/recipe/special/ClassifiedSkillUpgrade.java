package com.somebody.idlframewok.recipe.special;

import com.somebody.idlframewok.item.misc.ItemClassEssence;
import com.somebody.idlframewok.item.skills.classfit.EnumSkillClass;
import com.somebody.idlframewok.item.skills.classfit.ItemSkillClassSpecific;
import com.somebody.idlframewok.util.IDLSkillNBT;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class ClassifiedSkillUpgrade extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	//todo: register and write it

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		ItemStack mainStack = ItemStack.EMPTY, stackEssence = ItemStack.EMPTY;
		int lv1;
		EnumSkillClass skillClass = EnumSkillClass.NONE;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemSkillClassSpecific)
				{
					if (mainStack.isEmpty())
					{
						mainStack = stack;
						ItemSkillClassSpecific itemSkillBase = (ItemSkillClassSpecific)(stack.getItem());
						lv1 = IDLSkillNBT.getLevel(stack);
						if (lv1 >= itemSkillBase.maxLevel)
						{
							return false;
						}

						if (skillClass != EnumSkillClass.NONE && skillClass != itemSkillBase.skillClass)
						{
							//wrong class
							return false;
						}
						skillClass = itemSkillBase.skillClass;
					}
					else {
						//too many skills
						return false;
					}
				}
				else if (stack.getItem() instanceof ItemClassEssence)
				{
					if (stackEssence.isEmpty())
					{
						stackEssence = stack;
						ItemClassEssence essence = (ItemClassEssence) stack.getItem();
						if (skillClass != EnumSkillClass.NONE && skillClass != essence.skillClass)
						{
							//wrong class
							return false;
						}
					}
					else {
						//too many essence. one at a time
						return false;
					}
				}
				else
				{
					//DWeapons.logger.warn("Find useless components:[{}], instanceof", stack.getItem().getUnlocalizedName());
					return false; //Found other.
				}
			}
		}

		return !mainStack.isEmpty() && !stackEssence.isEmpty();
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		ItemStack stackResult;
		int level;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemSkillClassSpecific) {
					level = IDLSkillNBT.getLevel(stack);
					stackResult = stack.copy();
					IDLSkillNBT.SetLevel(stackResult, level + 1);
					return stackResult;
				}
			}
		}
		return ItemStack.EMPTY; //Found other.
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

