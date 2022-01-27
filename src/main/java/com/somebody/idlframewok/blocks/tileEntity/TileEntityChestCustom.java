package com.somebody.idlframewok.blocks.tileEntity;

import com.somebody.idlframewok.Idealland;
import com.somebody.idlframewok.blocks.container.ContainerModChest;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityChestCustom extends TileEntityLockableLoot implements ITickable {

    NonNullList<ItemStack> chestContents = NonNullList.withSize(72, ItemStack.EMPTY);

    public int numPlayersUsing, ticksSinceSync;
    public float lidAngle, prevLidAngle;

    static boolean tempAir = false;
    @Override
    public void markDirty() {
        //prevent redstone signal causing cascading world gen
        tempAir = true;
        super.markDirty();
        tempAir = false;
    }

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.chestContents)
        {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public Block getBlockType() {
        return tempAir ? Blocks.AIR : super.getBlockType();
    }

//    static
//    {
//        register("idlframewok:chest_inert", TileEntityChestCustom.class);
//    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return chestContents;
    }


    @Override
    public void update()
    {
        if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + pos.getX() + pos.getY() + pos.getZ()) % 200 == 0)
        {
            this.numPlayersUsing = 0;
            float f = 5.0F;

            for (EntityPlayer entityplayer : this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((double)((float)pos.getX() - 5.0F), (double)((float)pos.getY() - 5.0F), (double)((float)pos.getZ() - 5.0F), (double)((float)(pos.getX() + 1) + 5.0F), (double)((float)(pos.getY() + 1) + 5.0F), (double)((float)(pos.getZ() + 1) + 5.0F))))
            {
                if (entityplayer.openContainer instanceof ContainerModChest)
                {
                    if (((ContainerModChest)entityplayer.openContainer).getChestCustom() == this)
                    {
                        ++this.numPlayersUsing;
                    }
                }
            }
        }

        this.prevLidAngle = this.lidAngle;
        float f1 = 0.1F;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F)
        {
            double d1 = (double)pos.getX() + 0.5D;
            double d2 = (double)pos.getZ() + 0.5D;
            this.world.playSound((EntityPlayer)null, d1, (double)pos.getY() + 0.5D, d2, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
        {
            float f2 = this.lidAngle;
            if (this.numPlayersUsing > 0)
            {
                this.lidAngle += 0.1F;
            }
            else
            {
                this.lidAngle -= 0.1F;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }

            float f3 = 0.5F;

            if (this.lidAngle < 0.5F && f2 >= 0.5F)
            {
                double d3 = (double)pos.getX() + 0.5D;
                double d0 = (double)pos.getZ() + 0.5D;
                this.world.playSound(null, d3, (double)pos.getY() + 0.5D, d0, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerModChest(playerInventory, this, playerIn);
    }

    @Override
    public String getGuiID() {
        return Idealland.MODID + ":custom_chest";
}

    @Override
    public String getName() {
        return hasCustomName() ? customName : Idealland.MODID + ".container.custom_chest";
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        chestContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);

        if (!this.checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, chestContents);
        }

        if (compound.hasKey(IDLNBTDef.CUSTOM_NAME, 8))
        {
            customName = compound.getString(IDLNBTDef.CUSTOM_NAME);
        }
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, chestContents);
        }

        if (hasCustomName())//Ht made this wrong
        {
            compound.setString(IDLNBTDef.CUSTOM_NAME, customName);
        }

        return compound;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        numPlayersUsing++;
        world.addBlockEvent(pos, super.getBlockType(), 1, numPlayersUsing);
        world.notifyNeighborsOfStateChange(pos, super.getBlockType(), false);
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        numPlayersUsing--;
        world.addBlockEvent(pos, super.getBlockType(), 1, numPlayersUsing);
        world.notifyNeighborsOfStateChange(pos, super.getBlockType(), false);
    }
}
