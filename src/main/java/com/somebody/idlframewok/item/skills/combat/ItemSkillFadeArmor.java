package com.somebody.idlframewok.item.skills.combat;

import com.somebody.idlframewok.item.fading.ItemFadingArmor;
import com.somebody.idlframewok.item.skills.ItemSkillBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSkillFadeArmor extends ItemSkillBase {
    ItemFadingArmor[] armors;
    public ItemSkillFadeArmor(String name , ItemFadingArmor[] armors) {
        super(name);
        this.armors = armors;

    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return true;
    }


    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack heldItem = playerIn.getHeldItem(handIn);

        ActionResult<ItemStack> result = onGivePackage(armors, playerIn, handIn);
        if (result.getType() == EnumActionResult.SUCCESS)
        {
            activateCoolDown(playerIn, heldItem);
        }
        return result;
    }

    public static ActionResult<ItemStack> onGivePackage(ItemFadingArmor[] armors, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack heldItem = playerIn.getHeldItem(handIn);
        NBTTagList nbttaglist = heldItem.getEnchantmentTagList();

        boolean used = false;
        for (ItemFadingArmor itemFadingArmor: armors
        ) {
            EntityEquipmentSlot entityequipmentslot = itemFadingArmor.armorType;
            ItemStack itemstack1 = playerIn.getItemStackFromSlot(entityequipmentslot);

            //dont replace existing armor
            if (itemstack1.isEmpty())
            {
                if (!playerIn.world.isRemote)
                {
                    //Create Armor
                    ItemStack stack = new ItemStack(itemFadingArmor);
                    //Copy Enchantment
                    for (int j = 0; j < nbttaglist.tagCount(); ++j)
                    {
                        NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
                        int k = nbttagcompound.getShort("layer");
                        int l = nbttagcompound.getShort("lvl");
                        Enchantment enchantment = Enchantment.getEnchantmentByID(k);

                        if (enchantment != null)
                        {
                            stack.addEnchantment(enchantment, l);
                        }
                    }
                    //Wear it
                    playerIn.setItemStackToSlot(entityequipmentslot, stack);
                }

                used = true;
            }
        }

        if (used)
        {
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, heldItem);
        }
    }
}
