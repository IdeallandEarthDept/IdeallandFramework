package com.somebody.idlframewok.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class ModRecipeBase extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
        //must place the first sign in first line
       return false;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
        return ItemStack.EMPTY;
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
