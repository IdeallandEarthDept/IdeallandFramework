package com.somebody.idlframewok.item.dualline;

import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNotes extends ItemBase {

    //todo
    int index = 0;

    public ItemNotes(String name) {
        super(name);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

//    public String getUnlocalizedName(ItemStack stack)
//    {
//        int i = stack.getMetadata();
//        return super.getUnlocalizedName() + "_" + i;
//    }

//    /**
//     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
//     */
//    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
//    {
//        if (this.isInCreativeTab(tab))
//        {
//            for (int i = 0; i < 32; ++i)
//            {
//                items.add(new ItemStack(this, 1, i));
//            }
//        }
//    }

    @SideOnly(Side.CLIENT)
    public String descGetKey(ItemStack stack, World world, boolean showFlavor)
    {
        return showFlavor ? (stack.getUnlocalizedName() + IDLNBTDef.FLAVOR_KEY)
                : String.format("%s%s.%d", stack.getUnlocalizedName(), IDLNBTDef.DESC_COMMON, getMetadata(stack));
    }
}
