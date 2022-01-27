package com.somebody.idlframewok.item.misc.dungeon;

import com.somebody.idlframewok.blocks.tileEntity.dungeon.TileEntityTrapTickBase;
import com.somebody.idlframewok.util.enumGroup.EnumTrapArgTypes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemTrapSetter extends ItemTrapToolBase {
    EnumTrapArgTypes argType;

    public ItemTrapSetter(String name, int defaultVal, EnumTrapArgTypes argType) {
        super(name, defaultVal);
        this.argType = argType;
    }

    public EnumTrapArgTypes getArgType() {
        return argType;
    }

    @Override
    public void applyToBlockState(int state, TileEntityTrapTickBase trapTickBase) {
        trapTickBase.setArg(argType, state);
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            ItemStack basic = new ItemStack(this);
            items.add(basic);

            ItemStack stack = new ItemStack(this);
            setState(stack, defaultVal * 10);
            items.add(stack);

            stack = new ItemStack(this);
            setState(stack, defaultVal * 50);
            items.add(stack);
        }
    }
}
