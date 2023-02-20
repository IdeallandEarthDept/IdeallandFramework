package com.somebody.idlframewok.gui.expOne;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

//https://fmltutor.ustc-zzzz.net/3.4.2-GUI%E7%95%8C%E9%9D%A2%E7%9A%84%E4%B8%AA%E6%80%A7%E5%8C%96%E4%B8%8E%E7%89%A9%E5%93%81%E6%A7%BD%E7%9A%84%E6%B7%BB%E5%8A%A0.html
public class ContainerDemo extends Container {
    private ItemStackHandler items = new ItemStackHandler(4);

    protected Slot goldSlot;
    protected Slot diamondSlot;
    protected Slot emeraldSlot;
    protected Slot ironSlot;

    public ContainerDemo(EntityPlayer player)
    {
        super();

        this.addSlotToContainer(this.goldSlot = new SlotItemHandler(items, 0, 38 + 0 * 32, 20)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return stack != null && stack.getItem() == Items.GOLD_INGOT && super.isItemValid(stack);
            }

            @Override
            public int getItemStackLimit(ItemStack stack)
            {
                return 16;
            }
        });

        //2nd
        this.addSlotToContainer(this.diamondSlot = new SlotItemHandler(items, 1, 38 + 1 * 32, 20)
        {
            {
                this.putStack(new ItemStack(Items.DIAMOND, 64));
            }

            @Override
            public boolean canTakeStack(EntityPlayer playerIn)
            {
                return false;
            }
        });

        //3rd
        this.addSlotToContainer(this.emeraldSlot = new SlotItemHandler(items, 2, 38 + 2 * 32, 20)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return stack != null && stack.getItem() == Items.EMERALD && super.isItemValid(stack);
            }

            @Override
            public void onSlotChanged()
            {
                ItemStack stack = this.getStack();
                int amount = stack.isEmpty() ? 64 : 64 - stack.getCount();
                ContainerDemo.this.diamondSlot.putStack(amount == 0 ? ItemStack.EMPTY : new ItemStack(Items.DIAMOND, amount));
                super.onSlotChanged();
            }
        });

        //4th
        this.addSlotToContainer(this.ironSlot = new SlotItemHandler(items, 3, 38 + 3 * 32, 20)
        {
            {
                this.putStack(new ItemStack(Items.IRON_INGOT, 64));
            }

            @Override
            public boolean canTakeStack(EntityPlayer playerIn)
            {
                return false;
            }
        });

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 109));
        }


    }

    public Slot getIronSlot()
    {
        return this.ironSlot;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
        //return playerIn.getHeldItemOffhand().getItem() == ModItems.DEBUG_ITEM_3;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        //if the gui does not connects a tile entity, this must be manually rewritten
        super.onContainerClosed(playerIn);

        if (playerIn.isServerWorld())
        {
            ItemStack goldStack = this.goldSlot.getStack();
            if (goldStack != ItemStack.EMPTY)
            {
                playerIn.addItemStackToInventory(goldStack);
                this.goldSlot.putStack(ItemStack.EMPTY);
            }
            ItemStack emeraldStack = this.emeraldSlot.getStack();
            if (emeraldStack != ItemStack.EMPTY)
            {
                playerIn.addItemStackToInventory(emeraldStack);
                this.emeraldSlot.putStack(ItemStack.EMPTY);
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        Slot slot = inventorySlots.get(index);

        if (slot == null || !slot.getHasStack())
        {
            return null;
        }

        ItemStack newStack = slot.getStack(), oldStack = newStack.copy();

        //---------------------
        boolean isMerged = false;

        if (index == 0 || index == 2)
        {
            isMerged = mergeItemStack(newStack, 4, 40, true);
        }
        else if (index >= 4 && index < 31)
        {
            isMerged = !goldSlot.getHasStack() && newStack.getCount() <= 16 && mergeItemStack(newStack, 0, 1, false)
                    || !emeraldSlot.getHasStack() && mergeItemStack(newStack, 2, 3, false)
                    || mergeItemStack(newStack, 31, 40, false);
        }
        else if (index >= 31 && index < 40)
        {
            isMerged = !goldSlot.getHasStack() && newStack.getCount() <= 16 && mergeItemStack(newStack, 0, 1, false)
                    || !emeraldSlot.getHasStack() && mergeItemStack(newStack, 2, 3, false)
                    || mergeItemStack(newStack, 4, 31, false);
        }

        if (!isMerged)
        {
            return ItemStack.EMPTY;
        }

        //---------------------

        if (!isMerged)
        {
            return ItemStack.EMPTY;
        }

        if (newStack.getCount() == 0)
        {
            slot.putStack(ItemStack.EMPTY);
        }
        else
        {
            slot.onSlotChanged();
        }

        slot.onTake(playerIn, newStack);

        return oldStack;
    }
}
