package com.deeplake.idealland.recipe.special;

import com.deeplake.idealland.item.ModItems;
import com.deeplake.idealland.item.goblet.ItemGobletBase;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class GobletChangeMode extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	enum EnumCupMode
	{
		NONE,
		DIG,
		HEAL,
		FLAME,
		WIND,
        MOUNTAIN,
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		//before finding the weapon, we dont know whats the max pearl accepted
		boolean foundGoblet = false;
		EnumCupMode mode = EnumCupMode.NONE;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemGobletBase)
				{
					if (foundGoblet) {
						//IdlFramework.Log("Found more than one goblet item");
						return false;//only one goblwt at a time
					}
					foundGoblet = true;
				}
				else if (stack.getItem()== ModItems.GUA[0] ||
						stack.getItem() instanceof ItemPickaxe)
				{//found a xp item
					if (mode == EnumCupMode.NONE)
					{
						mode = EnumCupMode.DIG;
					}
					else {
						//IdlFramework.Log("Found more than one non-goblet item");
						return false;
					}
				}
				else if (stack.getItem()== ModItems.GUA[3] ||
						stack.getItem() instanceof ItemFood &&
						((ItemFood)(stack.getItem())).getHealAmount(stack) > 0)
				{//found a mode setter item
					if (mode == EnumCupMode.NONE)
					{
						mode = EnumCupMode.HEAL;
					}
					else {//more than one item
						//IdlFramework.Log("Found more than one non-goblet item");
						return false;
					}
				}
				else if (stack.getItem()== ModItems.GUA[6])
				{//found a mode setter item
					if (mode == EnumCupMode.NONE)
					{
						mode = EnumCupMode.WIND;
					}
					else {//more than one item
						//IdlFramework.Log("Found more than one non-goblet item");
						return false;
					}
				}
				else if (stack.getItem()== ModItems.GUA[5])
				{//found a mode setter item
					if (mode == EnumCupMode.NONE)
					{
						mode = EnumCupMode.FLAME;
					}
					else {//more than one item
						//IdlFramework.Log("Found more than one non-goblet item");
						return false;
					}
				}
				else if (stack.getItem()== ModItems.GUA[4])
				{//found a mode setter item
					if (mode == EnumCupMode.NONE)
					{
						mode = EnumCupMode.MOUNTAIN;
					}
					else {//more than one item
						//IdlFramework.Log("Found more than one non-goblet item");
						return false;
					}
				}
				else
				{
					return false; //Found an other.
				}
			}
		}

		return true;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		boolean foundGoblet = false;
		EnumCupMode mode = EnumCupMode.NONE;
		ItemStack cup = ItemStack.EMPTY;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() instanceof ItemGobletBase)
				{
					if (foundGoblet) {
						//IdlFramework.Log("Found more than one goblet item");
						return ItemStack.EMPTY;//only one sword at a time
					}
					cup = stack;
					foundGoblet = true;
				}
				else if (stack.getItem()== ModItems.GUA[0] ||
                        stack.getItem() instanceof ItemPickaxe)
				{//found a mode setter item
					if (mode == EnumCupMode.NONE)
					{
						mode = EnumCupMode.DIG;
					}
					else {//more than one item
						//IdlFramework.Log("Found more than one non-goblet item");
						return ItemStack.EMPTY;
					}
				}
				else if (stack.getItem()== ModItems.GUA[3] ||
                        stack.getItem() instanceof ItemFood &&
						((ItemFood)(stack.getItem())).getHealAmount(stack) > 0)
				{//found a mode setter item
					if (mode == EnumCupMode.NONE)
					{
						mode = EnumCupMode.HEAL;
					}
					else {//more than one item
						//IdlFramework.Log("Found more than one non-goblet item");
						return ItemStack.EMPTY;
					}
				}
				else if (stack.getItem()== ModItems.GUA[6])
				{//found a mode setter item
					if (mode == EnumCupMode.NONE)
					{
						mode = EnumCupMode.WIND;
					}
					else {//more than one item
						//IdlFramework.Log("Found more than one non-goblet item");
						return ItemStack.EMPTY;
					}
				}
				else if (stack.getItem()== ModItems.GUA[5])
				{//found a mode setter item
					if (mode == EnumCupMode.NONE)
					{
						mode = EnumCupMode.FLAME;
					}
					else {//more than one item
						//IdlFramework.Log("Found more than one non-goblet item");
						return ItemStack.EMPTY;
					}
				}
                else if (stack.getItem()== ModItems.GUA[4])
                {//found a mode setter item
                    if (mode == EnumCupMode.NONE)
                    {
                        mode = EnumCupMode.MOUNTAIN;
                    }
                    else {//more than one item
                        //IdlFramework.Log("Found more than one non-goblet item");
                        return ItemStack.EMPTY;
                    }
                }
				else
				{
					//IdlFramework.Log("Found other");
					return ItemStack.EMPTY; //Found other.
				}
			}
		}


		ItemStack result = cup;
		if (foundGoblet)
		{
			//IdlFramework.Log("Gobletfound");
			switch (mode)
			{
				case DIG:
					result = new ItemStack(ModItems.GOBLET_DIG);
					ModItems.GOBLET_DIG.SetCacheEXP(result,  ItemGobletBase.GetCacheEXP(cup));
					break;
				case HEAL:
					result = new ItemStack(ModItems.GOBLET_HEAL);
					ModItems.GOBLET_HEAL.SetCacheEXP(result,  ItemGobletBase.GetCacheEXP(cup));
					break;
				case FLAME:
					result = new ItemStack(ModItems.GOBLET_FLAME);
					ModItems.GOBLET_FLAME.SetCacheEXP(result,  ItemGobletBase.GetCacheEXP(cup));
					break;
				case WIND:
					result = new ItemStack(ModItems.GOBLET_WIND);
					ModItems.GOBLET_WIND.SetCacheEXP(result,  ItemGobletBase.GetCacheEXP(cup));
					break;
                case MOUNTAIN:
					result = new ItemStack(ModItems.GOBLET_MOUNTAIN);
					ModItems.GOBLET_MOUNTAIN.SetCacheEXP(result,  ItemGobletBase.GetCacheEXP(cup));
					break;
				case NONE:
					result = new ItemStack(ModItems.P_2_W_GOBLET);
					ModItems.P_2_W_GOBLET.SetCacheEXP(result,  ItemGobletBase.GetCacheEXP(cup));
					break;
			}
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

