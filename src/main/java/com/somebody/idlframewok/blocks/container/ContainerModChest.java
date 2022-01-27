package com.somebody.idlframewok.blocks.container;

import com.somebody.idlframewok.blocks.tileEntity.TileEntityChestCustom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerModChest extends Container {
    final int numRows;
    final TileEntityChestCustom chestCustom;
    static final int PER_ROW = 9;

    //can be optimized : less param
    public ContainerModChest(InventoryPlayer inventoryPlayer, TileEntityChestCustom chestTE, EntityPlayer player)
    {
        chestCustom = chestTE;
        numRows = chestTE.getSizeInventory() / 9;
        chestCustom.openInventory(player);

        int offset = (this.numRows - 4) * 18;

        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                this.addSlotToContainer(new Slot(chestTE, j + i * PER_ROW, 8 + j * 18, 18 + i * 18));
            }
        }

        for (int y = 0; y < 3; ++y)
        {
            for (int x = 0; x < 9; ++x)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + offset));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i1, 8 + i1 * 18, 161 + offset));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.chestCustom.isUsableByPlayer(playerIn);
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        chestCustom.closeInventory(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        Slot slot = inventorySlots.get(index);

        if (slot == null || !slot.getHasStack())
        {
            return null;
        }

        ItemStack stackNew1 = slot.getStack(), oldStack = stackNew1.copy();

        if (index < this.numRows * PER_ROW)
        {
            if (!this.mergeItemStack(stackNew1, this.numRows * PER_ROW, inventorySlots.size(), true))
            {
                return ItemStack.EMPTY;
            }
        }else if (!this.mergeItemStack(stackNew1, 0, this.numRows * PER_ROW, false))
        {
            return ItemStack.EMPTY;
        }

        if (stackNew1.isEmpty())
        {
            slot.putStack(ItemStack.EMPTY);
        }
        else {
            slot.onSlotChanged();
        }

        return oldStack;
    }

    public TileEntityChestCustom getChestCustom()
    {
        return chestCustom;
    }
}
