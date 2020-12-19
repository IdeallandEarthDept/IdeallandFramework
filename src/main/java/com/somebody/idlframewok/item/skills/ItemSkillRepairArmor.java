package com.somebody.idlframewok.item.skills;

import com.somebody.idlframewok.util.CommonFunctions;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemSkillRepairArmor extends ItemSkillBase {
    public ItemSkillRepairArmor(String name) {
        super(name);
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
        NBTTagList nbttaglist = heldItem.getEnchantmentTagList();

        boolean used = false;
        for (EntityEquipmentSlot slot:
                EntityEquipmentSlot.values()){

            if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND)
            {
                continue;//only repairs armor
            }

            ItemStack itemstack1 = playerIn.getItemStackFromSlot(slot);

            //fix and enchant existing armor
            if (!itemstack1.isEmpty())
            {
                if (!playerIn.world.isRemote)
                {
                    //Fix Dura
                    CommonFunctions.RepairItem(itemstack1, (int) getVal(heldItem));

                    //Copy Enchantment
                    for (int j = 0; j < nbttaglist.tagCount(); ++j)
                    {
                        NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
                        int k = nbttagcompound.getShort("layer");
                        int l = nbttagcompound.getShort("lvl");
                        Enchantment enchantment = Enchantment.getEnchantmentByID(k);

                        if (enchantment != null)
                        {
                            itemstack1.addEnchantment(enchantment, l);
                        }
                    }
                }
                used = true;
            }
        }

        if (used)
        {
            activateCoolDown(playerIn, heldItem);
            worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_NOTE_HARP, SoundCategory.PLAYERS, 1f, 3f);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, heldItem);
        }
    }
}
