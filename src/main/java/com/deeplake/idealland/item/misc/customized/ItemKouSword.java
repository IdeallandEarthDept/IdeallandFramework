package com.deeplake.idealland.item.misc.customized;

import com.deeplake.idealland.item.ItemSwordBase;
import com.deeplake.idealland.util.EntityUtil;
import com.deeplake.idealland.util.IDLGeneral;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.List;

import static com.deeplake.idealland.util.CommonDef.TICK_PER_SECOND;
import static com.deeplake.idealland.util.NBTStrDef.IDLNBTDef.KILL_COUNT_ITEM;
import static net.minecraft.init.Items.GOLD_INGOT;
import static net.minecraftforge.fml.common.registry.ForgeRegistries.ITEMS;

public class ItemKouSword extends ItemSwordBase {
    public ItemKouSword(String name, ToolMaterial material) {
        super(name, material);
        //shiftToShowDesc = true;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (worldIn.isRemote)
        {
            ItemStack stack1 = playerIn.getHeldItemOffhand();
            if (stack1.getItem() == GOLD_INGOT)
            {
                stack1.shrink(1);
                Collection<Item> items = ITEMS.getValuesCollection();
                Item itemType = (Item) items.toArray()[playerIn.getRNG().nextInt(items.size())];
                playerIn.addItemStackToInventory(new ItemStack(itemType));
                stack.damageItem(10, playerIn);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

}
