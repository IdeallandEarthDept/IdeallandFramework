package com.deeplake.idealland.item.consumables;

import com.deeplake.idealland.item.fading.ItemFadingArmor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static com.deeplake.idealland.item.skills.ItemSkillFadeArmor.onGivePackage;

public class ItemFadePackageArmor extends ItemConsumableBase {
    private ItemFadingArmor[] armors;

    public ItemFadePackageArmor(String name, ItemFadingArmor[] armors) {
        super(name);
        this.armors = armors;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack heldItem = playerIn.getHeldItem(handIn);

        ActionResult<ItemStack> result = onGivePackage(armors, playerIn, handIn);
        if (result.getType() == EnumActionResult.SUCCESS)
        {
            heldItem.shrink(1);
        }

        return result;
    }
}
