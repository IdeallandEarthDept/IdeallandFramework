package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.item.consumables.ItemConsumableBase;
import com.somebody.idlframewok.util.CommonFunctions;
import com.somebody.idlframewok.util.MessageDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

import static com.somebody.idlframewok.util.CommonDef.TICK_PER_SECOND;

public class ItemTeleport extends ItemConsumableBase {

    public ItemTeleport(String name) {
        super(name);
        setMaxDamage(TICK_PER_SECOND * 30);
        useable = true;
    }

    public ActionResult<ItemStack> onConsume(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack heldItem = playerIn.getHeldItem(handIn);

        BlockPos vec3d = IDLNBTUtil.getMarkedPos(heldItem);

        if (playerIn.attemptTeleport(vec3d.getX(), vec3d.getY(), vec3d.getZ())) {
            EntityPlayer player = worldIn.getPlayerEntityByUUID(UUID.fromString(IDLNBTUtil.GetString(heldItem, IDLNBTDef.STATE_2, "")));
            CommonFunctions.SafeSendMsgToPlayer(player, MessageDef.MSG_TELEPORT_ACCEPTED, playerIn.getName());

            return ActionResult.newResult(EnumActionResult.SUCCESS, heldItem);
        }
        return ActionResult.newResult(EnumActionResult.FAIL, heldItem);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!worldIn.isRemote && entityIn instanceof EntityLivingBase)
        {
            stack.damageItem(1, (EntityLivingBase) entityIn);
        }
    }

    public ItemStack createWithEntity(Entity entity)
    {
        ItemStack stack = new ItemStack(this);
        IDLNBTUtil.markPosToStack(stack, entity.getPosition());
        IDLNBTUtil.SetString(stack, IDLNBTDef.STATE, entity.getName());
        IDLNBTUtil.SetString(stack, IDLNBTDef.STATE_2, entity.getUniqueID().toString());
        return stack;
    }
}
