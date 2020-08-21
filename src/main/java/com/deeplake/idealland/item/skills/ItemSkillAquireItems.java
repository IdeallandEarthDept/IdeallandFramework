package com.deeplake.idealland.item.skills;

import com.deeplake.idealland.item.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.deeplake.idealland.util.CommonDef.GUA_TYPES;

public class ItemSkillAquireItems extends ItemSkillBase {
    public Item[] validItems = new Item[]{ModItems.YANG_SIGN, ModItems.YIN_SIGN};

    public ItemSkillAquireItems(String name, Item[] validItems) {
        super(name);
        maxLevel = 1;
        showDamageDesc = false;
        this.validItems = validItems;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote)
        {
            playerIn.addItemStackToInventory(new ItemStack(validItems[validItems.length]));
            playerIn.playSound(SoundEvents.BLOCK_NOTE_HARP, 1f, 3f);
            activateCoolDown(playerIn, stack);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));

    }
}
