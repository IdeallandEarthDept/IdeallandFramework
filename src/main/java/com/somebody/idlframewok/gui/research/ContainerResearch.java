package com.somebody.idlframewok.gui.research;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.designs.research.IDLResearchNode;
import com.somebody.idlframewok.designs.research.IDLResearchTree;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.inventory.Container;


public class ContainerResearch extends Container {
    private ItemStackHandler items = new ItemStackHandler(1);

    protected Slot curItem;

    public IDLResearchNode curNode;

    public ContainerResearch(EntityPlayer player) {
        super();

        this.addSlotToContainer(curItem = new SlotItemHandler(items, 0, 80, 49) {
            @Override
            public boolean canTakeStack(EntityPlayer playerIn)
            {
                return false;
            }
        });

        curNode = IDLResearchTree.getInstance().getNodeFromID(0,0);
        refreshSlotWithCurNode();
    }

    public void refreshSlotWithCurNode()
    {
        curItem.putStack(curNode.grantItemStack());
        Idealland.Log("stack changed to %s", curNode.grantItemStack().getUnlocalizedName());
    }

    public void tryMoveToChild(int index)
    {
        IDLResearchNode nextNode;
        nextNode = curNode.children[index];
        if (nextNode != null && nextNode != curNode)
        {
            curNode = nextNode;
            refreshSlotWithCurNode();
        }
    }

    public void tryMoveToParent()
    {
        IDLResearchNode nextNode;
        nextNode = curNode.parent;
        if (nextNode != null && nextNode != curNode)
        {
            curNode = nextNode;
            refreshSlotWithCurNode();
        }
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

        }
    }
}
