package com.deeplake.idealland.item.misc.GuaCasters;

import com.deeplake.idealland.item.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

//spent elements to place water
public class ItemGuaWaterBucket extends ItemGuaCasterBase {
    public ItemGuaWaterBucket(String name) {
        super(name);
        validItems = new Item[]{ModItems.GUA[2]};

    }
}
