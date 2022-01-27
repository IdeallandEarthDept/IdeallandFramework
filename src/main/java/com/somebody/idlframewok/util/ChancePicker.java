package com.somebody.idlframewok.util;

import com.somebody.idlframewok.Idealland;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ChancePicker {
    List<ChanceTuple> chanceTupleList = new LinkedList<ChanceTuple>();

    int totalWeight = 0;

    public void AddTuple(ChanceTuple tuple)
    {
        chanceTupleList.add(tuple);
        totalWeight+=tuple.weight;
    }

    public void AddTuple(int weight,ItemStack stack)
    {
        chanceTupleList.add(new ChanceTuple(weight, stack));
        totalWeight+=weight;
    }

    public void AddTuple(int weight, Item item)
    {
        AddTuple(weight, item, 1, 1);
    }

    public void AddTuple(int weight, Item item, int count)
    {
        AddTuple(weight, item, count, count);
    }

    public void AddTuple(int weight, Item item, int count, int max)
    {
        chanceTupleList.add(new ChanceTuple(weight, item, count, max));
        totalWeight+=weight;
    }

    public ItemStack GetItem(Random random)
    {
        if (totalWeight <= 0)
        {
            return ItemStack.EMPTY;
        }
        int weight = random.nextInt(totalWeight);
        for (ChanceTuple tuple:
             chanceTupleList) {
            weight -= tuple.weight;
            if (weight <= 0)
            {
                return tuple.getStack(random);
            }
        }
        Idealland.LogWarning("tuple bugged: did not find item");
        return ItemStack.EMPTY;

    }
}
