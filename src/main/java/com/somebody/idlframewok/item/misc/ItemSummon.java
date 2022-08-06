package com.somebody.idlframewok.item.misc;

import com.somebody.idlframewok.IdlFramework;
import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSummon extends ItemBase {

    ResourceLocation entityLocation;

    protected boolean doWarn = true;

    public ItemSummon(String name) {
        super(name);
    }

    public ItemSummon setEntity(String id)
    {
        useable = true;
        this.entityLocation = new ResourceLocation(Reference.MOD_ID,id);

        //this.entityLocation = new ResourceLocation(layer);
        return this;
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand handIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote)
        {
            stack.shrink(1);
            Entity entity = EntityList.createEntityByIDFromName(entityLocation, worldIn);
            if (entity instanceof EntityLivingBase)
            {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                entityLivingBase.setPosition(pos.getX() + 0.5f,pos.getY() + 1f,pos.getZ() + 0.5f);
                IdlFramework.Log("Spawned: %s @ %s", entityLivingBase.getName(), entityLivingBase.getPosition());
                worldIn.spawnEntity(entityLivingBase);
//                if (entityLivingBase instanceof EntityLiving)
//                {
//                    ((EntityLiving)entityLivingBase).spawnExplosionParticle();
//                }
            }
            else {
                if (doWarn)
                    IdlFramework.LogWarning("Trying to summon a non-living entity");
            }
        }

        return super.onItemUse(playerIn, worldIn, pos, handIn, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    }
}
