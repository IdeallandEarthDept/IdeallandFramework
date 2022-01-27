package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.entity.creatures.buildings.EntityIdlBuildingRoom;
import com.somebody.idlframewok.item.IGuaEnhance;
import com.somebody.idlframewok.util.IDLSkillNBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSummonRoom extends ItemSummon implements IGuaEnhance {

    ResourceLocation entityLocation;

    public ItemSummonRoom(String name) {
        super(name);
    }

    public ItemSummonRoom setEntity(String id)
    {
        useable = true;
        return this;
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand handIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote)
        {
            stack.shrink(1);
            EntityIdlBuildingRoom entity = new EntityIdlBuildingRoom(worldIn);
            entity.setPosition(pos.getX() + 0.5f,pos.getY() + 1f,pos.getZ() + 0.5f);
            Idealland.Log("%s Spawned: %s @ %s [size = %s]", playerIn.getDisplayNameString(), entity.getName(), entity.getPosition(), IDLSkillNBT.GetGuaEnhance(stack, 7));
            worldIn.spawnEntity(entity);
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean acceptGuaIndex(int index) {
        return index == 7;
    }
}
