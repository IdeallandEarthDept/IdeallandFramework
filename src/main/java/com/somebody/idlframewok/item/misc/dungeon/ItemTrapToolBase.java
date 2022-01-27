package com.somebody.idlframewok.item.misc.dungeon;

import com.somebody.idlframewok.blocks.tileEntity.dungeon.TileEntityTrapTickBase;
import com.somebody.idlframewok.init.ModCreativeTabsList;
import com.somebody.idlframewok.item.ItemBase;
import com.somebody.idlframewok.item.misc.ILinearModule;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTDef;
import com.somebody.idlframewok.util.NBTStrDef.IDLNBTUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemTrapToolBase extends ItemBase implements ILinearModule {

    int defaultVal;

    public ItemTrapToolBase(String name, int defaultVal) {
        super(name);
        this.defaultVal = defaultVal;
        setCreativeTab(ModCreativeTabsList.IDL_DUNGEON);
    }

    public int getState(ItemStack stack)
    {
        return IDLNBTUtil.GetInt(stack, IDLNBTDef.STATE, defaultVal);
    }

    public void applyToBlockState(ItemStack stack, TileEntityTrapTickBase trapTickBase)
    {
        applyToBlockState(getState(stack), trapTickBase);

    }

    public abstract void applyToBlockState(int state, TileEntityTrapTickBase trapTickBase);


    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityTrapTickBase)
        {
            if (!world.isRemote)
            {
                applyToBlockState(player.getHeldItem(hand), (TileEntityTrapTickBase) tileEntity);
                player.getCooldownTracker().setCooldown(this, 2);//prevent multi click
                ((TileEntityTrapTickBase) tileEntity).introToPlayer(player);
            }

            return EnumActionResult.SUCCESS;
        }
        else {
            return EnumActionResult.FAIL;
        }


    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return net.minecraft.client.resources.I18n.format(this.getUnlocalizedNameInefficiently(stack)+IDLNBTDef.NAME_POSTFIX, getState(stack)).trim();
    }
}
