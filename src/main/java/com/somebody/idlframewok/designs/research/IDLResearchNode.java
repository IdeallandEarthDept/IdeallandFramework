package com.somebody.idlframewok.designs.research;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.item.ModItems;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IDLResearchNode {
    public IDLResearchNode parent;
    public Item grantsItem;
    public int grantsCount = 1;

    public int researchTime = 10;//in seconds
    public int produceTime = 5;//in seconds

    public int pathID1 = 0;
    public int pathIDLayer = 3;

    public IDLResearchNode[] children;

    public int getPathIDFull()
    {
        return pathIDLayer + pathID1 * IDLResearchTree.OFFSET;
    }

    public IDLResearchNode(Item grantsItem, int researchTime)
    {
        this.grantsItem = grantsItem;
        this.researchTime = researchTime;
        this.produceTime = researchTime;
        children = new IDLResearchNode[2];
    }

    public IDLResearchNode(Item grantsItem, int researchTime, int produceTime)
    {
        this.grantsItem = grantsItem;
        this.researchTime = researchTime;
        this.produceTime = produceTime;
        children = new IDLResearchNode[2];
    }

    public IDLResearchNode(int pathIDLayer, int pathID1, Item grantsItem, int researchTime) {
        this.grantsItem = grantsItem;
        this.researchTime = researchTime;
        this.produceTime = researchTime;
        this.pathID1 = pathID1;
        this.pathIDLayer = pathIDLayer;
        children = new IDLResearchNode[2];
    }

    public IDLResearchNode setParent(IDLResearchNode parent)
    {
        this.parent = parent;
        return this;
    }

    public IDLResearchNode setChild(IDLResearchNode child, int index)
    {
        if (index < 0 || index > 1)
        {
            Idealland.LogWarning("tring to set non-existing index");
            return this;
        }
        children[index] = child;
        child.setParent(this);
        //Idealland.Log("%s -[%d]-> %s", grantsItem.getUnlocalizedName(), index, child.grantsItem.getUnlocalizedName());
        return this;
    }

    public ItemStack grantItemStack()
    {
        if (grantsItem == ItemStack.EMPTY.getItem())
        {
            ItemStack result = new ItemStack(ModItems.ITEM_FUTURE_PACKGE);
            IDLNBTUtil.setInt(result, IDLNBTDef.PACK_CODE, getPathIDFull());
            Idealland.LogWarning("not legal item");
            return result;
        }
        else {
            return new ItemStack(grantsItem, grantsCount);
        }
    }



    //
//    public IDLResearchNode setTime(int researchTime, int produceTime)
//    {
//
//    }

}
