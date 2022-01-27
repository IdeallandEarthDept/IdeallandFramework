package com.somebody.idlframewok.recipe.special;

import com.somebody.idlframewok.item.misc.ILinearModule;
import com.somebody.idlframewok.item.misc.dungeon.ItemTrapSetter;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class LinearMerge extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
        ItemStack stack1 = ItemStack.EMPTY;
        ItemStack stack2 = ItemStack.EMPTY;

        for(int i = 0; i < var1.getSizeInventory(); i++) {
            ItemStack stack = var1.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if (stack.getItem() instanceof ILinearModule)
                {
                    if (stack1.isEmpty())
                    {
                        stack1 = stack;
                    }
                    else if (stack2.isEmpty())
                    {
                        stack2 = stack;
                    }
                    else {
                        //too many modules
                        return false;
                    }
                }
                else {
                    //bad ingredient
                    return false;
                }
            }
        }

        return stack1.getItem() == stack2.getItem()
                && !stack1.isEmpty();
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
        ItemStack stack1 = ItemStack.EMPTY;
        ItemStack stack2 = ItemStack.EMPTY;

        for(int i = 0; i < var1.getSizeInventory(); i++) {
            ItemStack stack = var1.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if (stack.getItem() instanceof ILinearModule)
                {
                    if (stack1.isEmpty())
                    {
                        stack1 = stack;
                    }
                    else if (stack2.isEmpty())
                    {
                        stack2 = stack;
                    }
                    else {
                        //too many modules
                        return ItemStack.EMPTY;
                    }
                }
                else {
                    //bad ingredient
                    return ItemStack.EMPTY;
                }
            }
        }

        ItemStack result = stack1.copy();
        result.setCount(1);
        IDLNBTUtil.SetState(result, ((ILinearModule)(stack1.getItem())).getState(stack1)
                + ((ILinearModule)(stack2.getItem())).getState(stack2));

        if (result.getItem() instanceof ItemTrapSetter)
        {
            ItemTrapSetter itemTrapSetter = (ItemTrapSetter) result.getItem();
            switch (itemTrapSetter.getArgType())
            {

                case ACTIVE_TICKS:
                case DAMAGE:
                    //Natural Numbers
                    if (itemTrapSetter.getState(result) < 0)
                    {
                        return ItemStack.EMPTY;
                    }
                    break;
                case PERIOD:
                    //period must >= 0, here actually >= 2
                    if (itemTrapSetter.getState(result) <= 1)
                    {
                        return ItemStack.EMPTY;
                    }
                    break;
                case PHASE:
                    //any number is ok
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + itemTrapSetter.getArgType());
            }

            //does not allow zero period
            return ItemStack.EMPTY;
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