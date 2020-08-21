package com.deeplake.idealland.item.misc;

import com.deeplake.idealland.Idealland;
import com.deeplake.idealland.entity.creatures.buildings.EntityIdlBuildingRoom;
import com.deeplake.idealland.item.IGuaEnhance;
import com.deeplake.idealland.item.ItemBase;
import com.deeplake.idealland.util.IDLSkillNBT;
import com.deeplake.idealland.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.deeplake.idealland.util.Reference.MOD_ID;
import static net.minecraft.entity.EntityList.createEntityByIDFromName;

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
