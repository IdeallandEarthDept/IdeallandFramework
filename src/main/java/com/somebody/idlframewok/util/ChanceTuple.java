package com.somebody.idlframewok.util;

import com.somebody.idlframewok.Idealland;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ChanceTuple {
    public int weight = 0;
    ItemStack stack;
    public int randDelta = 0;

    public ChanceTuple(int weight, ItemStack stack) {
        this.weight = weight;
        this.stack = stack;
    }

    public ChanceTuple(int weight, Item item, int count) {
        this.weight = weight;
        this.stack = new ItemStack(item, count);
    }

    public ChanceTuple(int weight, Item item, int countMin, int countMax) {
        if (countMax < countMin)
        {
            Idealland.LogWarning("Wrong Tuple count for %s: %d~%d",item.getUnlocalizedName(), countMin, countMax);
            countMax = countMin;
        }

        this.weight = weight;
        this.stack = new ItemStack(item, countMin);
        randDelta = countMax - countMin + 1;
    }

    public ItemStack getStack(Random random) {
        ItemStack result = stack.copy();
        if (randDelta != 0)
        {
            result.setCount(stack.getCount() + random.nextInt(randDelta));
        }
        return result;
    }
}
