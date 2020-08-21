package com.deeplake.idealland.recipe.special;

import com.deeplake.idealland.item.ModItems;
import com.deeplake.idealland.item.goblet.ItemDigGoblet;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTDef;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class SaiSwordAnchorSet extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		boolean foundGoblet = false;
		boolean foundBlock = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() == ModItems.ITEM_SAI_SWORD)
				{
					if (foundGoblet) {
						//IdlFramework.Log("Found more than one goblet item");
						return false;//only one goblwt at a time
					}
					foundGoblet = true;
				}
				else if ((stack.getItem() == ModItems.YIN_SIGN) && !foundBlock)
				{//found a block
					foundBlock = true;
				}
				else if ((stack.getItem() == ModItems.YANG_SIGN) && !foundBlock)
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
		ItemStack saiSwordStack = ItemStack.EMPTY;

		boolean foundSai = false;
		boolean foundMaterial = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() == ModItems.ITEM_SAI_SWORD)
				{
					if (foundSai) {
						return ItemStack.EMPTY;
					}
					saiSwordStack = stack.copy();
					foundSai = true;
				}
				else if ((stack.getItem() == ModItems.YIN_SIGN) && !foundMaterial)
				{//found a block
					foundMaterial = true;
					IDLNBTUtil.SetBoolean(saiSwordStack, IDLNBTDef.MARKING_POS_B, true);
				}
				else if ((stack.getItem() == ModItems.YANG_SIGN) && !foundMaterial)
				{//found a block
					foundMaterial = true;
					IDLNBTUtil.SetBoolean(saiSwordStack, IDLNBTDef.MARKING_POS_A, true);
				}
				else
				{
					return ItemStack.EMPTY; //Found an other.
				}
			}
		}

		return saiSwordStack;
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

