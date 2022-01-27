package com.somebody.idlframewok.item;

import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.util.IHasModel;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.PlayerUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import static com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef.ENTITY_TAG;

public class ItemAdvancedSpawnEgg extends ItemMonsterPlacer implements IHasModel, ILogNBT, IWIP {

    public ItemAdvancedSpawnEgg(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabsList.IDL_MISC);

        ModItems.ITEMS.add(this);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }


    //    compound.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY, this.posZ));
//            compound.setTag("Motion", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
//            compound.setTag("Rotation", this.newFloatNBTList(this.rotationYaw, this.rotationPitch));

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        ResourceLocation location = getNamedIdFrom(stack);
        if (location == null && PlayerUtil.isCreative(playerIn))
        {
            stack = playerIn.getHeldItem(hand);
            if (!playerIn.world.isRemote)
            {
                NBTTagCompound tag = target.serializeNBT();
                tag.removeTag(IDLNBTDef.ENTITY_TAG_POS);
                tag.removeTag(IDLNBTDef.ENTITY_TAG_MOTION);
                tag.removeTag(IDLNBTDef.ENTITY_TAG_UUID_MOST);
                tag.removeTag(IDLNBTDef.ENTITY_TAG_UUID_LEAST);

                stack.setTagInfo(ENTITY_TAG, tag);
                //EntityTag:
                //-id
                //-nbt
            }

            return true;
//            }
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    //overrides that of ItemMonsterPlacer
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            items.add(new ItemStack(this));
        }
    }
}
