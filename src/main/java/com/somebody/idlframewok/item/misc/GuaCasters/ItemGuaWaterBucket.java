package com.somebody.idlframewok.item.misc.GuaCasters;

import com.somebody.idlframewok.item.ModItems;
import net.minecraft.item.Item;

//spent elements to place water
public class ItemGuaWaterBucket extends ItemGuaCasterBase {
    public ItemGuaWaterBucket(String name) {
        super(name);
        validItems = new Item[]{ModItems.GUA[2]};

    }
}
