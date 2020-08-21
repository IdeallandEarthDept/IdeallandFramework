package com.deeplake.idealland.item.misc;

import com.deeplake.idealland.item.ItemBase;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.ASSIGNED_BLOCK_NAME;

//todo: detects blocks downwards
public class ItemLuoyangShovel extends ItemBase {
    public ItemLuoyangShovel(String name) {
        super(name);
    }

    public String GetStoredBlockName(ItemStack stack)
    {
        NBTTagCompound compound = stack.getTagCompound();
        return compound == null ? "" : compound.getString(ASSIGNED_BLOCK_NAME);
    }

    public void SetStoredBlockName(ItemStack stack, Block block)
    {
        ResourceLocation resourceLocation = block.getRegistryName();
        IDLNBTUtil.SetString(stack, ASSIGNED_BLOCK_NAME, resourceLocation==null ? "" : block.getRegistryName().toString());
    }


}
